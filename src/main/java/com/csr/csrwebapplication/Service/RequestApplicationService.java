package com.csr.csrwebapplication.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.csr.csrwebapplication.Model.ApplicationEstimateDTO;
import com.csr.csrwebapplication.Model.FullRequestApplicationDTO;
import com.csr.csrwebapplication.Model.RequestApplication;

public interface RequestApplicationService {

	RequestApplication saveRequest(RequestApplication requestApplication);

	Optional<RequestApplication> getRequestApplicationById(String id);

	List<RequestApplication> getAllApplications();

	void saveEstimateData(ApplicationEstimateDTO data);

	boolean updateSponsorStatus(String applicationId, String status);

	void updateWorkStatusForApplication(String applicationId, String status);

	// List<RequestApplication> getQualifiedAndNotSelectedRequests();

	List<FullRequestApplicationDTO> getQualifiedAndNotSelectedRequests();

	List<RequestApplication> updateSponsorStatusSelected(List<String> applicationIds, String email, String status);

	// List<RequestApplication> findApplicationsByCompanyEmail(String companyEmail);

	List<FullRequestApplicationDTO> findApplicationsByCompanyEmail(String companyEmail);

	void updateBoardStatus(String applicationId, String boardStatus);

	// List<RequestApplication> getSponsorSubmittedApplications();

	List<FullRequestApplicationDTO> getSponsorSubmittedApplications();

	RequestApplication updateDRDAOfficeStatus(String applicationId, String csrStatus);

	List<RequestApplication> fetchApplicationsByUserEmail(String userGmail);

	void saveApplicationWithFiles(String applicationId, MultipartFile detailedEstimateCopy, MultipartFile landDocuments,
			MultipartFile technicalClearanceDocuments, MultipartFile consentOfficerApprovalDocuments,
			MultipartFile relatedPhotos, MultipartFile misc);

	void saveApplicationWithAScertificate(String applicationId, MultipartFile asCertificate);

	void saveCsrOfficeStatus(String applicationId, String csrOfficeStatus);

	void updateFundStatusForApplication(String applicationId, String fundingStatus);

	List<RequestApplication> getApplicationsWithSponsorStatusSelected();

	RequestApplication save(RequestApplication application);

	void updateSponsorStatusQualified(String applicationId, String status);

	void saveApplicationWithWorkCompletedCopy(String applicationId, MultipartFile workCompletedCopy);

	void saveApplicationWithUtilizationCertificate(String applicationId, MultipartFile utilizationCertificate);

	String processApplication(String email, RequestApplication requestApplication);

	RequestApplication registerApplication(String representatorId, RequestApplication requestApplication);

	List<FullRequestApplicationDTO> getCsrOfficeDashboardRequestApplications();

	List<FullRequestApplicationDTO> getCsrOfficeFinalApprovedFilteredRequestApplications();

	List<FullRequestApplicationDTO> getBdoOfficeDashboardFilteredRequestApplications();

	List<FullRequestApplicationDTO> getBdoOfficeFinalApprovedFilteredRequestApplications();
}
