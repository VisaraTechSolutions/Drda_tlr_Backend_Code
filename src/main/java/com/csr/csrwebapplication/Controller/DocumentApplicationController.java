package com.csr.csrwebapplication.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.csr.csrwebapplication.Model.DocumentApplication;
import com.csr.csrwebapplication.Service.DocumentApplicationService;

/**
 * Controller for handling document-related requests.
 */
@RestController
@RequestMapping("/api")
public class DocumentApplicationController {

	@Autowired
	private DocumentApplicationService documentApplicationService;

	/**
	 * Endpoint to save a document.
	 *
	 * @param email The email associated with the document.
	 * @param file  The document file to be saved.
	 * @return ResponseEntity with status message.
	 */
	@PostMapping("/saveDocuments")
	public ResponseEntity<String> saveDocument(@RequestParam("file") MultipartFile file) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		try {
			DocumentApplication documentsEntity = new DocumentApplication();
			documentsEntity.setImageSrc(file.getBytes());
			documentApplicationService.saveDocument(email, documentsEntity);
			return new ResponseEntity<>("Document saved successfully", HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Endpoint to get all document applications.
	 *
	 * @return ResponseEntity with a list of documents.
	 */
	@GetMapping("/document-applications")
	public ResponseEntity<List<DocumentApplication>> getAllDocuments() {
		List<DocumentApplication> documentsEntities = documentApplicationService.getAllDocuments();
		return new ResponseEntity<>(documentsEntities, HttpStatus.OK);
	}

	/**
	 * Endpoint to update the status of a document application.
	 *
	 * @param documentId The ID of the document to update.
	 * @return ResponseEntity with status message.
	 */
	@PostMapping("/updateStatusForDocumentApplication")
	public ResponseEntity<String> updateDocumentStatus(@RequestParam String documentId) {
		try {
			documentApplicationService.updateDocumentStatus(documentId, "Application Applied");
			return ResponseEntity.ok("Document Application Status updated successfully");
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
}
