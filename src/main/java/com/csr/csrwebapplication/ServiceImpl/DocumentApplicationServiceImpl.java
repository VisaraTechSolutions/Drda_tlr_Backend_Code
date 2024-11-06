package com.csr.csrwebapplication.ServiceImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csr.csrwebapplication.Model.CollectrateEmailDetialsModel;
import com.csr.csrwebapplication.Model.DocumentApplication;
import com.csr.csrwebapplication.Repo.CollectrateEmailDetialsRepository;
import com.csr.csrwebapplication.Repo.DocumentsApplicationRepository;
import com.csr.csrwebapplication.Service.DocumentApplicationService;
import com.csr.csrwebapplication.Service.EmailService;
import com.csr.csrwebapplication.Utils.IdGenerator;

@Service
public class DocumentApplicationServiceImpl implements DocumentApplicationService {

	@Autowired
	private DocumentsApplicationRepository documentsRepository;

	@Autowired
	private CollectrateEmailDetialsRepository collectrateEmailDetialsRepository;

	@Autowired
	private EmailService emailService;

	@Override
	public void saveDocument(String email, DocumentApplication documentsEntity) {
		CollectrateEmailDetialsModel collectrateEmailDetialsModel = collectrateEmailDetialsRepository
				.findByColletorateEmails(email);

		if (collectrateEmailDetialsModel != null) {
			documentsEntity.setDocumentId(IdGenerator.generateDocId());
			documentsEntity.setWhenCreatedDate(new Date());
			documentsEntity.setCollectrateEmailDetialsModel(collectrateEmailDetialsModel);
			documentsRepository.save(documentsEntity);

			String userType = collectrateEmailDetialsModel.getName();
			String applicationId = documentsEntity.getDocumentId();
			emailService.thankingForApplicationSubmitThrowEmail(userType, email, applicationId);
			emailService.applicationSubmitByUserSendNotificationToCsrofficeIfDocument(userType, applicationId);
		} else {
			throw new RuntimeException("Email not found");
		}
	}

	@Override
	public List<DocumentApplication> getAllDocuments() {
		return documentsRepository.findAll();
	}

	@Transactional
	@Override
	public void updateDocumentStatus(String documentId, String newStatus) {
		DocumentApplication document = documentsRepository.findByDocumentId(documentId);
		if (document != null) {
			document.setStatus(newStatus);
			documentsRepository.save(document);
		} else {
			throw new IllegalArgumentException("Document not found with ID: " + documentId);
		}
	}
}