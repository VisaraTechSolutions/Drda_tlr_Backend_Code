package com.csr.csrwebapplication.ServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.csr.csrwebapplication.Model.ApplicationEstimateDTO;
import com.csr.csrwebapplication.Model.CollectrateEmailDetialsModel;
import com.csr.csrwebapplication.Model.CompanyModel;
import com.csr.csrwebapplication.Model.FullRequestApplicationDTO;
import com.csr.csrwebapplication.Model.RequestApplication;
import com.csr.csrwebapplication.Repo.CollectrateEmailDetialsRepository;
import com.csr.csrwebapplication.Repo.CompanyModelRepository;
import com.csr.csrwebapplication.Repo.RequestApplicationRepository;
import com.csr.csrwebapplication.Service.EmailService;
import com.csr.csrwebapplication.Service.RequestApplicationService;
import com.csr.csrwebapplication.Utils.IdGenerator;

import jakarta.persistence.EntityNotFoundException;

@Service
public class RequestApplicationServiceImpl implements RequestApplicationService {

	@Autowired
	private RequestApplicationRepository requestApplicationRepository;

	@Autowired
	private CompanyModelRepository companyModelRepository;

	@Autowired
	private EmailService emailService;

	@Autowired
	private CollectrateEmailDetialsRepository collectrateEmailDetialsRepository;

	/*--------------------------------------------------------------------------------------------------------------------------*/

	/**
	 * Processes the application by validating the user and sending notifications.
	 * 
	 * @param email              The email of the user.
	 * @param requestApplication The application data to be processed.
	 * @return A success message including the application ID.
	 * @throws IllegalArgumentException If the user is not found.
	 */
	@Override
	public String processApplication(String email, RequestApplication requestApplication) {
		// Retrieves the user by email
		CollectrateEmailDetialsModel user = collectrateEmailDetialsRepository.findByColletorateEmails(email);
		if (user == null) {
			throw new IllegalArgumentException("User with the provided email not found.");
		}

		// Sets user and requester information in the application
		String userType = user.getName();
		requestApplication.setUser(user);
		requestApplication.setRequester(userType);

		// Saves the application and sends notifications
		RequestApplication savedRequestApplication = saveRequest(requestApplication);
		String applicationId = savedRequestApplication.getApplicationId();

		// Sends thank-you and notification emails
		emailService.thankingForApplicationSubmitThrowEmail(userType, email, applicationId);
		emailService.applicationSubmitByUserSendNotificationToCsroffice(userType, applicationId);

		return "{\"message\": \"Application submitted successfully with ID: " + applicationId + "\"}";
	}

	/*--------------------------------------------------------------------------------------------------------------------------*/

	/**
	 * Registers a new application by validating the user and saving the
	 * application.
	 * 
	 * @param representatorId    The ID of the user.
	 * @param requestApplication The application data to be registered.
	 * @return The saved RequestApplication.
	 * @throws IllegalArgumentException If the user is not found.
	 */
	@Override
	public RequestApplication registerApplication(String representatorId, RequestApplication requestApplication) {
		// Retrieves the user by ID
		Optional<CollectrateEmailDetialsModel> optionalUser = collectrateEmailDetialsRepository
				.findById(representatorId);

		// Check if the user is present
		if (!optionalUser.isPresent()) {
			throw new IllegalArgumentException("User with the provided ID not found."); // Throws an exception if user
																						// not found
		}

		// Get the user from the Optional
		CollectrateEmailDetialsModel user = optionalUser.get();

		// Sets user and requester information in the application
		String userType = user.getName();
		requestApplication.setUser(user);
		requestApplication.setRequester(userType);

		// Saves the application and returns the saved application
		return saveRequest(requestApplication);
	}
	/*--------------------------------------------------------------------------------------------------------------------------*/

	/**
	 * Saves the request application to the repository.
	 * 
	 * @param requestApplication The application data to be saved.
	 * @return The saved RequestApplication with an assigned ID.
	 */
	@Override
	public RequestApplication saveRequest(RequestApplication requestApplication) {
		// Generates a custom application ID and sets the registration date
		String customApplicationId = IdGenerator.generateApplicationId();
		requestApplication.setApplicationId(customApplicationId);
		requestApplication.setRegistrationDateTime(new Date());
		return requestApplicationRepository.save(requestApplication); // Saves to the database
	}

	/*--------------------------------------------------------------------------------------------------------------------------*/

	/**
	 * Retrieves a RequestApplication by ID from the repository.
	 * 
	 * @param id The ID of the request application to retrieve.
	 * @return An Optional containing the RequestApplication if found, or an empty
	 *         Optional.
	 */
	@Override
	public Optional<RequestApplication> getRequestApplicationById(String id) {
		return requestApplicationRepository.findById(id); // Retrieves from the repository
	}
	/*--------------------------------------------------------------------------------------------------------------------------*/

	/**
	 * Retrieves all RequestApplications from the repository.
	 * 
	 * @return A list of all RequestApplications.
	 */
	@Override
	public List<RequestApplication> getAllApplications() {
		return requestApplicationRepository.findAll(); // Retrieves from the repository
	}

	/*--------------------------------------------------------------------------------------------------------------------------*/
    /**
     * Retrieves filtered request applications for the BDO office dashboard page.
     *
     * @return A list of FilteredApplicationDTO objects representing the required fields:
   
     */
	@Override
    public List<FullRequestApplicationDTO> getBdoOfficeDashboardFilteredRequestApplications() {
        List<RequestApplication> applications = requestApplicationRepository.findAll(); // Adjust if needed
        return applications.stream()
            .filter(application -> 
                "CSR-Office-Qualified".equals(application.getDrdaCSROfficeStatus()) || 
                "DRDA-CSR-Approved".equals(application.getDrdaCSROfficeStatus())
            )
            .map(this::convertToBdoOfiiceFilteredDto)
            .collect(Collectors.toList());
    }

    private FullRequestApplicationDTO convertToBdoOfiiceFilteredDto(RequestApplication application) {
    	FullRequestApplicationDTO dto = new FullRequestApplicationDTO();
        dto.setApplicationId(application.getApplicationId());
        dto.setRequester(application.getRequester());  
        dto.setRequest(application.getRequest());
        dto.setSector(application.getSector());
        dto.setBdoStatus(application.getBdoStatus());
        dto.setDrdaCSROfficeStatus(application.getDrdaCSROfficeStatus());
        return dto;
    }
    /*--------------------------------------------------------------------------------------------------------------------------*/
    
    /**
     * Retrieves filtered request applications for the BDO office dashboard final approved page.
     *
     * @return A list of FilteredApplicationDTO objects representing the required fields.
     */
    public List<FullRequestApplicationDTO> getBdoOfficeFinalApprovedFilteredRequestApplications() {
        List<RequestApplication> applications = requestApplicationRepository.findAll(); // Adjust if needed
        return applications.stream()
            .filter(application -> "DRDA-CSR-Approved".equals(application.getDrdaCSROfficeStatus()))
            .map(this::convertToBdoOfficeFinalApprovedFilteredDto)
            .collect(Collectors.toList());
    }

    private FullRequestApplicationDTO convertToBdoOfficeFinalApprovedFilteredDto(RequestApplication application) {
    	FullRequestApplicationDTO dto = new FullRequestApplicationDTO();
        dto.setApplicationId(application.getApplicationId());
        dto.setRequest(application.getRequest());
        dto.setSponsor(application.getSponsor());
        dto.setEstimateAmount(application.getEstimateAmount());
        dto.setDrdaCSROfficeStatus(application.getDrdaCSROfficeStatus());
        dto.setDocumentStatus(application.getDocumentStatus());
        dto.setWorkProgressStatus(application.getWorkProgressStatus());
        dto.setAsCertificate(application.getAsCertificate());
        dto.setDrdaCSROfficeStatus(application.getDrdaCSROfficeStatus());
        dto.setWorkCompletedCertificateStatus(application.getWorkCompletedCertificateStatus());
        return dto;
    }
	/*--------------------------------------------------------------------------------------------------------------------------*/

	/**
	 * Retrieves a list of specific request applications for the CSR Office
	 * dashboard.
	 *
	 * @return A list of TableSpecificRequestApplicationDTO containing relevant
	 *         fields such as application ID, requester, request description,
	 *         sector, and DRDA CSR office status.
	 */
	@Override
	public List<FullRequestApplicationDTO> getCsrOfficeDashboardRequestApplications() {
		// Retrieve all request applications from the repository
		List<RequestApplication> applications = requestApplicationRepository.findAll();

		// Create a list to hold the FullRequestApplicationDTO objects
		List<FullRequestApplicationDTO> fullRequestApplicationDTOList = new ArrayList<>();

		// Iterate through the applications and map to FullRequestApplicationDTO using
		// setters
		for (RequestApplication app : applications) {
			// Create a new instance of FullRequestApplicationDTO
			FullRequestApplicationDTO dto = new FullRequestApplicationDTO();

			// Set individual fields using setters
			dto.setApplicationId(app.getApplicationId()); // Set application ID
			dto.setRequester(app.getRequester()); // Set requester name
			dto.setRequest(app.getRequest()); // Set request description
			dto.setSector(app.getSector()); // Set sector
			dto.setDrdaCSROfficeStatus(app.getDrdaCSROfficeStatus()); // Set DRDA CSR office status

			// Add the dto to the list
			fullRequestApplicationDTOList.add(dto);
		}

		// Return the list of mapped DTOs
		return fullRequestApplicationDTOList;
	}
	/*--------------------------------------------------------------------------------------------------------------------------*/
	
	/**
	 * Retrieves a list of request applications that are approved by the CSR office.
	 * 
	 * @return A list of FullRequestApplicationDTO objects representing applications with the status "DRDA-CSR-Approved".
	 */
	@Override
	public List<FullRequestApplicationDTO> getCsrOfficeFinalApprovedFilteredRequestApplications() {
	    List<RequestApplication> applications = requestApplicationRepository.findAll(); // Retrieve all applications
	    return applications.stream()
	        .filter(application -> "DRDA-CSR-Approved".equals(application.getDrdaCSROfficeStatus())) // Filter by status
	        .map(this::convertToFilteredDto)
	        .collect(Collectors.toList());
	}

	/**
	 * Converts a RequestApplication to a FullRequestApplicationDTO.
	 *
	 * @param application The RequestApplication to convert.
	 * @return A FullRequestApplicationDTO containing relevant fields from the application.
	 */
	private FullRequestApplicationDTO convertToFilteredDto(RequestApplication application) {
	    FullRequestApplicationDTO dto = new FullRequestApplicationDTO();
	    dto.setApplicationId(application.getApplicationId());
	    dto.setRequest(application.getRequest());
	    dto.setSponsor(application.getSponsor());
	    dto.setEstimateAmount(application.getEstimateAmount());
	    dto.setDrdaCSROfficeStatus(application.getDrdaCSROfficeStatus());
	    dto.setDocumentStatus(application.getDocumentStatus());
	    dto.setAsCertificateStatus(application.getAsCertificateStatus());
	    dto.setWorkProgressStatus(application.getWorkProgressStatus());
	    dto.setWorkCompletedCertificate(application.getWorkCompletedCertificate());
	    dto.setUtilizationCertificate(application.getUtilizationCertificate());
	    dto.setDetailedEstimateCopy(application.getDetailedEstimateCopy());
	    dto.setLandDocuments(application.getLandDocuments());
	    dto.setTechnicalClearanceDocuments(application.getTechnicalClearanceDocuments());
	    dto.setConsentOfficerApprovalDocuments(application.getConsentOfficerApprovalDocuments());
	    dto.setRelatedPhotos(application.getRelatedPhotos());
	    dto.setMisc(application.getMisc());
	    return dto;
	}


	/*--------------------------------------------------------------------------------------------------------------------------*/

	/**
	 * Saves estimate data for an application.
	 * 
	 * @param data The application estimate data to be saved.
	 * @throws IllegalArgumentException If the application is not found.
	 */
	@Transactional
	@Override
	public void saveEstimateData(ApplicationEstimateDTO data) {
		// Retrieves the application entity from the database
		Optional<RequestApplication> optionalApplication = requestApplicationRepository
				.findById(data.getApplicationId());
		if (optionalApplication.isPresent()) {
			RequestApplication application = optionalApplication.get();

			// Updates the application entity with the estimate amount and status
			application.setEstimateAmount(data.getAmount());
			application.setBdoStatus(data.getBdoStatus());

			// Sends an email notification
			emailService.applicationNoticeThrowEmail(data.getApplicationId());

			// Saves the updated application entity
			requestApplicationRepository.save(application);
		} else {
			// Throws an exception if the application is not found
			throw new IllegalArgumentException("Application not found with id: " + data.getApplicationId());
		}
	}
	/*--------------------------------------------------------------------------------------------------------------------------*/

	/**
	 * Saves CSR office status for an application.
	 * 
	 * @param applicationId   The ID of the application.
	 * @param csrOfficeStatus The CSR office status to be set.
	 * @throws IllegalArgumentException If the application is not found.
	 */
	@Transactional
	@Override
	public void saveCsrOfficeStatus(String applicationId, String csrOfficeStatus) {
		// Retrieve the application entity from the repository
		Optional<RequestApplication> optionalApplication = requestApplicationRepository.findById(applicationId);

		if (optionalApplication.isPresent()) {
			RequestApplication application = optionalApplication.get();

			// Updates the CSR office status
			application.setDrdaCSROfficeStatus(csrOfficeStatus);

			// Save the updated application entity
			requestApplicationRepository.save(application);

			// Sends an email if the status is "CSR-Office-Qualified"
			if ("CSR-Office-Qualified".equals(application.getDrdaCSROfficeStatus())) {
				emailService.applicationApprovedByCsrOffice(applicationId);
			}
		} else {
			// Throws an exception if the application is not found
			throw new IllegalArgumentException("Application not found with id: " + applicationId);
		}
	}
	/*--------------------------------------------------------------------------------------------------------------------------*/

	/**
	 * Updates the DRDA CSR office status for an application.
	 * 
	 * @param applicationId The ID of the application to update.
	 * @param csrStatus     The new CSR office status to set.
	 * @return The updated RequestApplication, or null if not found.
	 */
	@Transactional
	@Override
	public RequestApplication updateDRDAOfficeStatus(String applicationId, String csrStatus) {
		// Retrieve the application entity from the repository
		RequestApplication requestApplication = requestApplicationRepository.findById(applicationId).orElse(null);

		if (requestApplication == null) {
			// Return null if the application is not found
			return null;
		}

		// Update the CSR office status
		requestApplication.setDrdaCSROfficeStatus(csrStatus);

		// Retrieve company details for notification
		String sponsorName = requestApplication.getSponsor();
		CompanyModel companyModel = companyModelRepository.findByCompanyName(sponsorName);
		String companyEmail = companyModel.getCompanyEmail();

		// Notify the company of the project approval
		emailService.notifyCompanyOfProjectApproval(sponsorName, applicationId, companyEmail, csrStatus);

		// Save the updated application entity
		return requestApplicationRepository.save(requestApplication);
	}
	/*--------------------------------------------------------------------------------------------------------------------------*/

	/**
	 * Updates the sponsor status for an application.
	 * 
	 * @param applicationId The ID of the application to update.
	 * @param status        The new sponsor status to set.
	 * @return True if the update was successful, otherwise false.
	 */
	@Transactional
	@Override
	public boolean updateSponsorStatus(String applicationId, String status) {
		// Retrieve the application entity from the repository
		RequestApplication application = requestApplicationRepository.findById(applicationId).orElse(null);

		if (application != null) {
			// Update the sponsor status
			application.setSponsorStatus(status);

			// Save the updated application entity
			requestApplicationRepository.save(application);
			return true;
		}
		return false;
	}
	/*--------------------------------------------------------------------------------------------------------------------------*/

	/**
	 * Updates the work status for an application and sends notification emails.
	 * 
	 * @param applicationId The ID of the application to update.
	 * @param workStatus    The new work status to set.
	 */
	@Transactional
	@Override
	public void updateWorkStatusForApplication(String applicationId, String workStatus) {
		// Retrieve the application entity from the repository
		RequestApplication application = requestApplicationRepository.findById(applicationId)
				.orElseThrow(() -> new RuntimeException("Application not found for id: " + applicationId));

		// Update the work status
		application.setWorkProgressStatus(workStatus);

		// Retrieve user and company details
		String userEmail = application.getUser().getColletorateEmails();
		String userName = application.getUser().getUserType();
		CompanyModel companyModel = companyModelRepository.findByCompanyName(application.getSponsor());
		String companyEmail = companyModel.getCompanyEmail();

		// Notify the relevant parties
		emailService.notifyWorkInitiation(application.getSponsor(), companyEmail, applicationId, userEmail, userName,
				workStatus);

		// Save the updated application
		requestApplicationRepository.save(application);
	}
	/*--------------------------------------------------------------------------------------------------------------------------*/

	/**
	 * Updates the funding status for an application.
	 * 
	 * @param applicationId The ID of the application to update.
	 * @param fundingStatus The new funding status to set.
	 */
	@Transactional
	@Override
	public void updateFundStatusForApplication(String applicationId, String fundingStatus) {
		// Retrieve the application entity from the repository
		RequestApplication application = requestApplicationRepository.findById(applicationId)
				.orElseThrow(() -> new RuntimeException("Application not found for id: " + applicationId));

		// Update the funding status
		application.setFundingStatus(fundingStatus);

		// Save the updated application entity
		requestApplicationRepository.save(application);
	}
	/*--------------------------------------------------------------------------------------------------------------------------*/

	/**
	 * Fetches requests where BDO status is qualified and sponsor status is not
	 * selected, or funding status is not fully funded.
	 * 
	 * @return A list of RequestApplication matching the criteria.
	 */
	//Before using DTO
//	@Override
//	public List<RequestApplication> getQualifiedAndNotSelectedRequests() {
//		try {
//			return requestApplicationRepository.findQualifiedAndNotSelectedOrFundingNotFullyFunded();
//		} catch (Exception e) {
//			throw new RuntimeException("Error fetching the requests", e);
//		}
//	}
	//After using DTO
	@Override
	public List<FullRequestApplicationDTO> getQualifiedAndNotSelectedRequests() {
	    try {
	        List<RequestApplication> requests = requestApplicationRepository.findQualifiedAndNotSelectedOrFundingNotFullyFunded();
	        
	        // Map the entity to DTO
	        List<FullRequestApplicationDTO> dtos = new ArrayList<>();
	        for (RequestApplication request : requests) {
	        	FullRequestApplicationDTO dto = new FullRequestApplicationDTO();
	            dto.setApplicationId(request.getApplicationId());
	            dto.setSector(request.getSector());
	            dto.setRequest(request.getRequest());
	            dto.setBlock(request.getBlock());
	            dto.setVillage(request.getVillage());
	            dto.setEstimateAmount(request.getEstimateAmount());
	            dto.setFundingStatus(request.getFundingStatus());
	            dto.setDrdaCSROfficeStatus(request.getDrdaCSROfficeStatus());
	            dto.setBdoStatus(request.getBdoStatus());
	            dtos.add(dto);
	        }
	        
	        return dtos;
	    } catch (Exception e) {
	        throw new RuntimeException("Error fetching the requests", e);
	    }
	}

	/*--------------------------------------------------------------------------------------------------------------------------*/

	/**
	 * Updates the sponsor status, sponsor name, and selected date for a list of
	 * applications.
	 *
	 * @param applicationIds List of application IDs to update.
	 * @param email          The email of the company to set as the sponsor.
	 * @param status         The status to set for the sponsor.
	 * @return The list of updated RequestApplication entities.
	 */
	@Override
	@Transactional
	public List<RequestApplication> updateSponsorStatusSelected(List<String> applicationIds, String email,
			String status) {
		// Fetch the company name using the email
		CompanyModel company = companyModelRepository.findByCompanyEmail(email);
		if (company == null) {
			throw new RuntimeException("Company not found for email: " + email);
		}
		String companyName = company.getCompanyName();

		// Update the sponsor status, sponsor name, and selected date
		List<RequestApplication> applications = requestApplicationRepository.findAllById(applicationIds);
		applications.forEach(application -> {
			application.setSponsor(companyName);
			application.setSponsorStatus(status);
			application.setSelectedDateFromCommonpool(new Date());
		});

		// Send notification email
		emailService.companySelectedProjectsFromCommonPool(companyName, applicationIds);

		// Save all updated applications
		return requestApplicationRepository.saveAll(applications);
	}

	/*--------------------------------------------------------------------------------------------------------------------------*/
	/**
	 * Updates the sponsor status of a request application to 'Qualified'.
	 * 
	 * @param applicationId The ID of the application to update.
	 * @param status        The new sponsor status.
	 */
	@Override
	@Transactional
	public void updateSponsorStatusQualified(String applicationId, String status) {
		// Find the request application by ID
		RequestApplication requestApplication = requestApplicationRepository.findById(applicationId)
				.orElseThrow(() -> new RuntimeException("Application not found"));

		// Update the sponsor status
		requestApplication.setSponsorStatus(status);

		// Save the updated request application
		requestApplicationRepository.save(requestApplication);

		// Send a notification email
		emailService.notifyCsrOfficeSponsorSubmittedApplicationForApproval(requestApplication.getSponsor(),
				requestApplication.getFundingStatus(), applicationId);
	}

	/*--------------------------------------------------------------------------------------------------------------------------*/
	/**
	 * Finds request applications by company email.
	 * 
	 * @param companyEmail The email of the company.
	 * @return A list of request applications for the specified company.
	 */
	//Before using DTO
//	@Override
//	public List<RequestApplication> findApplicationsByCompanyEmail(String companyEmail) {
//		try {
//			// Find the company by email
//			CompanyModel company = companyModelRepository.findByCompanyEmail(companyEmail);
//
//			if (company != null && company.getCompanyName() != null && !company.getCompanyName().isEmpty()) {
//				// Fetch request applications by company name
//				return requestApplicationRepository.findBySponsor(company.getCompanyName());
//			} else {
//				throw new IllegalArgumentException("Company not found for email: " + companyEmail);
//			}
//		} catch (Exception ex) {
//			throw new RuntimeException("Error fetching applications: " + ex.getMessage());
//		}
//	}
	//After using DTO
	@Override
	public List<FullRequestApplicationDTO> findApplicationsByCompanyEmail(String companyEmail) {
	    try {
	        CompanyModel company = companyModelRepository.findByCompanyEmail(companyEmail);
	        
	        if (company != null && company.getCompanyName() != null && !company.getCompanyName().isEmpty()) {
	            List<RequestApplication> applications = requestApplicationRepository.findBySponsor(company.getCompanyName());
	            // Convert to DTOs
	            List<FullRequestApplicationDTO> dtoList = new ArrayList<>();
	            for (RequestApplication app : applications) {
	            	FullRequestApplicationDTO dto = new FullRequestApplicationDTO();
	                dto.setApplicationId(app.getApplicationId());
	                dto.setSector(app.getSector());
	                dto.setRequest(app.getRequest());
	                dto.setEstimateAmount(app.getEstimateAmount());
	                dto.setBdoStatus(app.getBdoStatus());
	                dto.setFundingStatus(app.getFundingStatus());
	                dto.setSponsorStatus(app.getSponsorStatus());   
	                dto.setDrdaCSROfficeStatus(app.getDrdaCSROfficeStatus());   
	                dto.setWorkProgressStatus(app.getWorkProgressStatus());   
	                dto.setWorkCompletedCertificate(app.getWorkCompletedCertificate());   
	                dto.setUtilizationCertificate(app.getUtilizationCertificate());   
	                dtoList.add(dto);
	            }
	            return dtoList;
	        } else {
	            throw new IllegalArgumentException("Company not found for email: " + companyEmail);
	        }
	    } catch (Exception ex) {
	        throw new RuntimeException("Error fetching applications: " + ex.getMessage());
	    }
	}

	/*--------------------------------------------------------------------------------------------------------------------------*/

	/**
	 * Updates the board status for a given application.
	 * 
	 * @param applicationId The ID of the application to update.
	 * @param boardStatus   The new board status.
	 * @throws EntityNotFoundException If the application with the given ID is not
	 *                                 found.
	 */
	@Transactional
	@Override
	public void updateBoardStatus(String applicationId, String boardStatus) throws EntityNotFoundException {
		RequestApplication application = requestApplicationRepository.findById(applicationId)
				.orElseThrow(() -> new EntityNotFoundException("Application with id " + applicationId + " not found."));

		application.setBoardStatus(boardStatus);

		requestApplicationRepository.save(application);
	}

	/*--------------------------------------------------------------------------------------------------------------------------*/

	/**
	 * Retrieves all applications where the sponsor has submitted to CSR.
	 * 
	 * @return A list of RequestApplication instances that are sponsor-submitted to
	 *         CSR.
	 */
//	@Override
//	public List<RequestApplication> getSponsorSubmittedApplications() {
//		try {
//			return requestApplicationRepository.findSponsorSubmittedApplications();
//		} catch (Exception e) {
//			throw new RuntimeException("Error fetching sponsor-submitted applications", e);
//		}
//	}

	public List<FullRequestApplicationDTO> getSponsorSubmittedApplications() {
		// Retrieve full entities
		List<RequestApplication> applications = requestApplicationRepository
				.findBySponsorStatusAndDrdaCSROfficeStatus("Qualified", "CSR-Office-Qualified");

		// Convert the entities to DTOs
		return applications.stream().map(this::convertToDto).collect(Collectors.toList());
	}

	// Helper method to convert an entity to DTO using setters
	private FullRequestApplicationDTO convertToDto(RequestApplication application) {
		FullRequestApplicationDTO dto = new FullRequestApplicationDTO();

		// Manually set the fields using setter methods
		dto.setApplicationId(application.getApplicationId());
		dto.setSector(application.getSector());
		dto.setRequest(application.getRequest());
		dto.setSponsor(application.getSponsor());
		dto.setBdoStatus(application.getBdoStatus());
		dto.setSponsorStatus(application.getSponsorStatus());
		dto.setFundingStatus(application.getFundingStatus());
		dto.setEstimateAmount(application.getEstimateAmount());

		return dto;
	}

	/*--------------------------------------------------------------------------------------------------------------------------*/

	/**
	 * Retrieves all applications for a user by their email.
	 * 
	 * @param userGmail The user's email address.
	 * @return A list of RequestApplication instances associated with the user.
	 */
	@Override
	public List<RequestApplication> fetchApplicationsByUserEmail(String userGmail) {
		// Step 1: Retrieve user details by email
		CollectrateEmailDetialsModel user = collectrateEmailDetialsRepository.findByColletorateEmails(userGmail);
		if (user == null) {
			throw new RuntimeException("User not found for email: " + userGmail);
		}

		// Step 2: Fetch applications by user ID
		return requestApplicationRepository.findByUserCollectrateId(user.getCollectrateId());
	}
	/*--------------------------------------------------------------------------------------------------------------------------*/

	/**
	 * Saves the uploaded files to the specified application.
	 *
	 * @param applicationId                   The ID of the application to update.
	 * @param detailedEstimateCopy            The detailed estimate document
	 *                                        (optional).
	 * @param landDocuments                   The land documents (optional).
	 * @param technicalClearanceDocuments     The technical clearance documents
	 *                                        (optional).
	 * @param consentOfficerApprovalDocuments The consent officer approval documents
	 *                                        (optional).
	 * @param relatedPhotos                   The related photos (optional).
	 * @param misc                            Any miscellaneous documents
	 *                                        (optional).
	 * @throws RuntimeException if the application is not found or file processing
	 *                          fails.
	 */
	@Transactional
	@Override
	public void saveApplicationWithFiles(String applicationId, MultipartFile detailedEstimateCopy,
			MultipartFile landDocuments, MultipartFile technicalClearanceDocuments,
			MultipartFile consentOfficerApprovalDocuments, MultipartFile relatedPhotos, MultipartFile misc) {

		// Find the application by ID
		RequestApplication application = requestApplicationRepository.findByApplicationId(applicationId)
				.orElseThrow(() -> new RuntimeException("Application not found with id: " + applicationId));

		try {
			// Process and save each file if present
			if (detailedEstimateCopy != null && !detailedEstimateCopy.isEmpty()) {
				byte[] detailedEstimate = detailedEstimateCopy.getBytes();
				application.setDetailedEstimateCopy(detailedEstimate);
			}
			if (landDocuments != null && !landDocuments.isEmpty()) {
				byte[] landDocs = landDocuments.getBytes();
				application.setLandDocuments(landDocs);
			}
			if (technicalClearanceDocuments != null && !technicalClearanceDocuments.isEmpty()) {
				byte[] techClearanceDocs = technicalClearanceDocuments.getBytes();
				application.setTechnicalClearanceDocuments(techClearanceDocs);
			}
			if (consentOfficerApprovalDocuments != null && !consentOfficerApprovalDocuments.isEmpty()) {
				byte[] consentDocs = consentOfficerApprovalDocuments.getBytes();
				application.setConsentOfficerApprovalDocuments(consentDocs);
			}
			if (relatedPhotos != null && !relatedPhotos.isEmpty()) {
				byte[] photos = relatedPhotos.getBytes();
				application.setRelatedPhotos(photos);
			}
			if (misc != null && !misc.isEmpty()) {
				byte[] miscFiles = misc.getBytes();
				application.setMisc(miscFiles);
			}

			// Update the document status of the application
			application.setDocumentStatus("Documents-Submitted");

			// Save the updated application
			requestApplicationRepository.save(application);

		} catch (IOException e) {
			// Throw a runtime exception if file processing fails
			throw new RuntimeException("Failed to process file upload for application ID: " + applicationId, e);
		}
	}
	/*--------------------------------------------------------------------------------------------------------------------------*/

	/**
	 * Saves the uploaded work completed certificate to the specified application.
	 *
	 * @param applicationId            The ID of the application to update.
	 * @param workCompletedCertificate The work completed certificate document
	 *                                 (optional).
	 * @throws RuntimeException if the application is not found or file processing
	 *                          fails.
	 */
	@Transactional
	@Override
	public void saveApplicationWithWorkCompletedCopy(String applicationId, MultipartFile workCompletedCertificate) {

		// Find the application by ID
		RequestApplication application = requestApplicationRepository.findByApplicationId(applicationId)
				.orElseThrow(() -> new RuntimeException("Application not found with id: " + applicationId));

		try {
			// Process and save the work completed certificate if present
			if (workCompletedCertificate != null && !workCompletedCertificate.isEmpty()) {
				byte[] workCompletedCopyBytes = workCompletedCertificate.getBytes();
				application.setWorkCompletedCertificate(workCompletedCopyBytes);
			}

			// Update the status of the work completed certificate
			application.setWorkCompletedCertificateStatus("Work Completed Certificate Submitted");

			// Fetch the company details using the sponsor's name from the application
			String companyName = application.getSponsor();
			CompanyModel companyModel = companyModelRepository.findByCompanyName(companyName);
			if (companyModel == null) {
				throw new RuntimeException("Company not found for name: " + companyName);
			}

			// Notify the company about the work completed certificate submission
			emailService.notifyWorkCompletedToCompany(companyName, companyModel.getCompanyEmail(), applicationId);

			// Save the updated application
			requestApplicationRepository.save(application);

		} catch (IOException e) {
			// Throw a runtime exception if file processing fails
			throw new RuntimeException("Failed to save files for application ID: " + applicationId, e);
		}
	}
	/*--------------------------------------------------------------------------------------------------------------------------*/

	/**
	 * Saves the uploaded utilization certificate to the specified application.
	 *
	 * @param applicationId          The ID of the application to update.
	 * @param utilizationCertificate The utilization certificate document
	 *                               (optional).
	 * @throws RuntimeException if the application is not found or file processing
	 *                          fails.
	 */
	@Transactional
	@Override
	public void saveApplicationWithUtilizationCertificate(String applicationId, MultipartFile utilizationCertificate) {

		// Find the application by ID
		RequestApplication application = requestApplicationRepository.findByApplicationId(applicationId)
				.orElseThrow(() -> new RuntimeException("Application not found with id: " + applicationId));

		try {
			// Process and save the utilization certificate if present
			if (utilizationCertificate != null && !utilizationCertificate.isEmpty()) {
				byte[] utilizationCertificateBytes = utilizationCertificate.getBytes();
				application.setUtilizationCertificate(utilizationCertificateBytes);
			}

			// Update the status of the utilization certificate
			application.setUtilizationCertificateStatus("Utilization Certificate Submitted");

			// Fetch the company details using the sponsor's name from the application
			String companyName = application.getSponsor();
			CompanyModel companyModel = companyModelRepository.findByCompanyName(companyName);
			if (companyModel == null) {
				throw new RuntimeException("Company not found for name: " + companyName);
			}

			// Notify the company about the utilization certificate submission
			emailService.notifyUtilityCertificateUploaded(companyName, companyModel.getCompanyEmail(), applicationId);

			// Save the updated application
			requestApplicationRepository.save(application);

		} catch (IOException e) {
			// Throw a runtime exception if file processing fails
			throw new RuntimeException("Failed to save files for application ID: " + applicationId, e);
		}
	}
	/*--------------------------------------------------------------------------------------------------------------------------*/

	/**
	 * Saves the uploaded AS certificate to the specified application.
	 *
	 * @param applicationId The ID of the application to update.
	 * @param asCertificate The AS certificate document (optional).
	 * @throws RuntimeException if the application is not found or file processing
	 *                          fails.
	 */
	@Transactional
	@Override
	public void saveApplicationWithAScertificate(String applicationId, MultipartFile asCertificate) {

		// Find the application by ID
		RequestApplication application = requestApplicationRepository.findByApplicationId(applicationId)
				.orElseThrow(() -> new RuntimeException("Application not found with id: " + applicationId));

		try {
			// Process and save the AS certificate if present
			if (asCertificate != null && !asCertificate.isEmpty()) {
				byte[] asCertificateBytes = asCertificate.getBytes();
				application.setAsCertificate(asCertificateBytes);
			}

			// Update the status of the AS certificate
			application.setAsCertificateStatus("AS-Certificate-Uploaded");

			// Fetch the company details using the sponsor's name from the application
			String companyName = application.getSponsor();
			CompanyModel companyModel = companyModelRepository.findByCompanyName(companyName);
			if (companyModel == null) {
				throw new RuntimeException("Company not found for name: " + companyName);
			}

			// Notify the company about the AS certificate upload
			String email = companyModel.getCompanyEmail();
			emailService.certificateIssuedByCsrOffice(companyName, applicationId, email);

			// Save the updated application
			requestApplicationRepository.save(application);

		} catch (IOException e) {
			// Throw a runtime exception if file processing fails
			throw new RuntimeException("Failed to save AS-Certificate for application ID: " + applicationId, e);
		}
	}

	/*--------------------------------------------------------------------------------------------------------------------------*/
	/**
	 * Retrieves all request applications with the sponsor status set to 'Selected'.
	 * 
	 * @return List of RequestApplication with 'Selected' sponsor status.
	 */
	@Override
	public List<RequestApplication> getApplicationsWithSponsorStatusSelected() {
		return requestApplicationRepository.findBySponsorStatus("Selected");
	}

	/**
	 * Saves the given request application.
	 * 
	 * @param application The RequestApplication entity to be saved.
	 * @return The saved RequestApplication entity.
	 */
	@Override
	public RequestApplication save(RequestApplication application) {
		return requestApplicationRepository.save(application);
	}
	/*--------------------------------------------------------------------------------------------------------------------------*/

}
