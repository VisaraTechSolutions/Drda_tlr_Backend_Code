package com.csr.csrwebapplication.Controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.csr.csrwebapplication.Model.ApplicationEstimateDTO;
import com.csr.csrwebapplication.Model.FullRequestApplicationDTO;
import com.csr.csrwebapplication.Model.RequestApplication;
import com.csr.csrwebapplication.Service.RequestApplicationService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api")
public class RequestApplicationController {

	@Autowired
	private RequestApplicationService requestApplicationService;

	/*--------------------------------------------------------------------------------------------------------------------------*/

	/**
	 * Handles POST requests to submit an application.
	 * 
	 * @param email              The email of the user submitting the application.
	 * @param requestApplication The application data to be processed.
	 * @return A ResponseEntity with a status and message based on the operation
	 *         outcome.
	 */
	@PostMapping("/application-submit")
	public ResponseEntity<String> createRequest(@RequestBody RequestApplication requestApplication) {
		// Get the authenticated username (email) from the SecurityContext
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();

		try {
			// Delegates processing of the application to the service layer
			String result = requestApplicationService.processApplication(email, requestApplication);
			return ResponseEntity.ok(result); // Returns success message
		} catch (IllegalArgumentException e) {
			// Ensure the message is JSON-compatible
			return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
		} catch (Exception e) {
			// Log the exception (e.g., using a logger)
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("{\"error\": \"An error occurred while processing the application.\"}");
		}
	}

	/*--------------------------------------------------------------------------------------------------------------------------*/

	/**
	 * Handles POST requests to register an application.
	 * 
	 * @param representatorId    The ID of the user.
	 * @param requestApplication The application data to be registered.
	 * @return A ResponseEntity with the saved RequestApplication or an error
	 *         status.
	 */
	@PostMapping("/application-register")
	public ResponseEntity<RequestApplication> registerRequest(@RequestParam String representatorId,
			@RequestBody RequestApplication requestApplication) {

		try {
			// Delegates the registration process to the service layer
			RequestApplication savedRequestApplication = requestApplicationService.registerApplication(representatorId,
					requestApplication);
			return ResponseEntity.ok(savedRequestApplication); // Returns the saved application
		} catch (IllegalArgumentException e) {
			// Returns a bad request response with an error message if the user is not found
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		} catch (Exception e) {
			// Returns an internal server error response for any other exceptions
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	/*--------------------------------------------------------------------------------------------------------------------------*/

	/**
	 * Handles GET requests to retrieve a RequestApplication by ID.
	 * 
	 * @param id The ID of the request application to retrieve.
	 * @return A ResponseEntity with the RequestApplication if found, or a 404 Not
	 *         Found status.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<RequestApplication> getRequestApplicationById(@PathVariable String id) {
		Optional<RequestApplication> requestApplication = requestApplicationService.getRequestApplicationById(id);

		// Returns the RequestApplication if found, otherwise returns a 404 Not Found
		// status
		return requestApplication.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}
	/*--------------------------------------------------------------------------------------------------------------------------*/

	/**
	 * Handles GET requests to retrieve all RequestApplications.
	 * 
	 * @return A list of all RequestApplications.
	 */
	@GetMapping("/applications")
	public List<RequestApplication> getAllApplications() {
		return requestApplicationService.getAllApplications(); // Delegates to the service layer
	}
	/*--------------------------------------------------------------------------------------------------------------------------*/
	   /**
     * Retrieves filtered request applications for the BDO office dashboard page.
     *
     * @return A list of FilteredApplicationDTO objects representing the required fields:
     *         - applicationId: Unique identifier for the application
     *         - requester: Name of the person making the request
     *         - request: Details of the request
     *         - sector: Sector associated with the application
     *         - bdoStatus: Current status of the application as seen by the BDO office
     */
    @GetMapping("/applications/bdoofficedashboard")
    public ResponseEntity<List<FullRequestApplicationDTO>> getBdoOfficeDashboardFilteredRequestApplications() {
        List<FullRequestApplicationDTO> filteredApplications = requestApplicationService.getBdoOfficeDashboardFilteredRequestApplications();
        return new ResponseEntity<>(filteredApplications, HttpStatus.OK);
    }
    /*--------------------------------------------------------------------------------------------------------------------------*/
    /**
     * Gets filtered applications for the BDO office dashboard final approved page.
     *
     * @return A ResponseEntity containing a list of FilteredApplicationDTO objects.
     * The returned objects contain the following fields:
     * - applicationId: The unique identifier of the application.
     * - request: The description of the application request.
     * - sponsor: The sponsor associated with the application.
     * - estimateAmount: The estimated amount for the application.
     * - drdaCSROfficeStatus: The current status of the application in the DRDA CSR office.
     * - documentStatus: The status of the documents associated with the application (defaulted to "Pending" if null).
     * - workProgressStatus: The current work progress status of the application.
     * - asCertificate: The URL or path to the certificate associated with the application, if available.
     */
    @GetMapping("/applications/bdoofficedashboardfinalapproved")
    public ResponseEntity<List<FullRequestApplicationDTO>> getBdoOfficeFinalApprovedFilteredApplications() {
        List<FullRequestApplicationDTO> applications = requestApplicationService.getBdoOfficeFinalApprovedFilteredRequestApplications();
        return ResponseEntity.ok(applications);
    }

	/*--------------------------------------------------------------------------------------------------------------------------*/

	/**
	 * Handles GET requests to retrieve specific request application fields for the
	 * CSR Office dashboard.
	 *
	 * @return a list of CsrOfficeDashboardRequestApplicationDTO containing fields
	 *         relevant for the CSR Office dashboard applications table. This
	 *         includes application ID, requester, request description, sector, and
	 *         DRDA CSR office status.
	 */
	@GetMapping("/applications/csrofficedashboard")
	public List<FullRequestApplicationDTO> getCsrOfficeDashboardFilteredApplications() {
		// Call the service to fetch the specific request applications
		return requestApplicationService.getCsrOfficeDashboardRequestApplications();
	}
	/*--------------------------------------------------------------------------------------------------------------------------*/
	/**
	 * Retrieves a list of request applications that have been approved by the CSR office.
	 * 
	 * @return ResponseEntity<List<FullRequestApplicationDTO>> - A response entity containing a list of 
	 * FullRequestApplicationDTO objects with the following fields:
	 * 
	 * - applicationId: The unique identifier of the application.
	 * - request: The description of the request.
	 * - sponsor: The name of the sponsor associated with the application.
	 * - estimateAmount: The estimated amount for the request.
	 * - drdaCSROfficeStatus: The status of the application with respect to the CSR office.
	 * - documentStatus: The status of the submitted documents.
	 * - asCertificateStatus: The status of the AS Certificate associated with the application.
	 * - workProgressStatus: The status of the work progress for the application.
	 * - workCompletedCertificate: The certificate indicating that work has been completed.
	 * - utilizationCertificate: The certificate for utilization associated with the application.
	 * - estimate documents
	 * - land documents
	 * - technical clearance documents
	 * - consent officer approval documents
	 * - related photos
	 * - misc
	 * 
	 * Each field provides necessary information for understanding the details of the request applications 
	 * approved by the CSR office.
	 */
	@GetMapping("/applications/csrofficedashboardfinalapproved")
	public ResponseEntity<List<FullRequestApplicationDTO>> getCsrOfficeFinalApprovedFilteredRequestApplications() {
	    List<FullRequestApplicationDTO> filteredRequestApplications = requestApplicationService.getCsrOfficeFinalApprovedFilteredRequestApplications();
	    return new ResponseEntity<>(filteredRequestApplications, HttpStatus.OK);
	}

	/*--------------------------------------------------------------------------------------------------------------------------*/

	/**
	 * Handles POST requests to save estimate data.
	 * 
	 * @param data The application estimate data to be saved.
	 * @return A ResponseEntity indicating success or failure.
	 */
	@PostMapping("/saveEstimateData")
	public ResponseEntity<String> saveApplicationData(@RequestBody ApplicationEstimateDTO data) {
		try {
			// Calls the service method to save estimate data
			requestApplicationService.saveEstimateData(data);
			return ResponseEntity.ok().body("{\"message\": \"Data saved successfully\"}");
		} catch (Exception e) {
			// Returns an error response if an exception occurs
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("{\"error\": \"Error saving data: " + e.getMessage() + "\"}");
		}
	}
	/*--------------------------------------------------------------------------------------------------------------------------*/

	/**
	 * Handles POST requests to save CSR office status.
	 * 
	 * @param requestData A map containing application ID and CSR office status.
	 * @return A ResponseEntity indicating success or failure.
	 */
	@PostMapping("/saveCsrOfficeStatus")
	public ResponseEntity<String> saveCsrOfficeStatus(@RequestBody Map<String, String> requestData) {
		try {
			String applicationId = requestData.get("applicationId");
			String csrOfficeStatus = requestData.get("csrOfficeStatus");

			// Call the service method to save CSR office status
			requestApplicationService.saveCsrOfficeStatus(applicationId, csrOfficeStatus);
			return ResponseEntity.ok().body("{\"message\": \"Data saved successfully\"}");
		} catch (Exception e) {
			// Returns an error response if an exception occurs
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("{\"error\": \"Error saving data: " + e.getMessage() + "\"}");
		}
	}
	/*--------------------------------------------------------------------------------------------------------------------------*/

	/**
	 * Handles POST requests to update DRDA CSR office status.
	 * 
	 * @param applicationId The ID of the application to update.
	 * @param csrStatus     The new CSR office status to set.
	 * @return A ResponseEntity containing the updated RequestApplication or an
	 *         error message.
	 */
	@PostMapping("/request-application/drdaCSROfficeStatus/Update")
	public ResponseEntity<RequestApplication> updateDRDAOfficeStatus(@RequestParam String applicationId,
			@RequestParam String csrStatus) {
		RequestApplication updatedRequestApplication = requestApplicationService.updateDRDAOfficeStatus(applicationId,
				csrStatus);
		if (updatedRequestApplication == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		return ResponseEntity.ok(updatedRequestApplication);
	}
	/*--------------------------------------------------------------------------------------------------------------------------*/

	/**
	 * Handles POST requests to update the sponsor status.
	 * 
	 * @param applicationId The ID of the application to update.
	 * @param status        The new sponsor status to set.
	 * @return A ResponseEntity containing the success status.
	 */
	@PostMapping("/request-application/sponsorStatusUpdate")
	public ResponseEntity<Map<String, Boolean>> updateSponsorStatus(@RequestParam String applicationId,
			@RequestParam String status) {
		boolean success = requestApplicationService.updateSponsorStatus(applicationId, status);
		return ResponseEntity.ok(Collections.singletonMap("success", success));
	}
	/*--------------------------------------------------------------------------------------------------------------------------*/

	/**
	 * Handles POST requests to update the work status for an application.
	 * 
	 * @param applicationId The ID of the application to update.
	 * @param workStatus    The new work status to set.
	 * @return A ResponseEntity with a success message or an error message.
	 */
	@PostMapping("/updateWorkStatusForApplication")
	public ResponseEntity<String> updateWorkStatusForApplication(@RequestParam String applicationId,
			@RequestParam String workStatus) {
		try {
			// Call service layer to update application status
			requestApplicationService.updateWorkStatusForApplication(applicationId, workStatus);
			return ResponseEntity.ok("Work status updated successfully.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error updating work status: " + e.getMessage());
		}
	}
	/*--------------------------------------------------------------------------------------------------------------------------*/

	/**
	 * Handles POST requests to update the funding status for an application.
	 * 
	 * @param applicationId The ID of the application to update.
	 * @param fundingStatus The new funding status to set.
	 * @return A ResponseEntity with a success message or an error message.
	 */
	@PostMapping("/updateFundingStatusForApplication")
	public ResponseEntity<String> updateFundingStatusForApplication(@RequestParam String applicationId,
			@RequestParam String fundingStatus) {
		try {
			// Call service layer to update application status
			requestApplicationService.updateFundStatusForApplication(applicationId, fundingStatus);
			return ResponseEntity.ok("Funding status updated successfully.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error updating funding status: " + e.getMessage());
		}
	}
	/*--------------------------------------------------------------------------------------------------------------------------*/

	/**
	 * Handles GET requests to fetch requests that are qualified but not selected,
	 * or have funding status not fully funded.
	 * 
	 * @return A ResponseEntity containing a list of RequestApplication or an error
	 *         message.
	 */
//	//Before Using DTO
//	@GetMapping("/qualified-and-not-selected")
//	public ResponseEntity<List<RequestApplication>> getQualifiedAndNotSelectedRequests() {
//		try {
//			List<RequestApplication> requests = requestApplicationService.getQualifiedAndNotSelectedRequests();
//			return ResponseEntity.ok(requests);
//		} catch (RuntimeException e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//		}
//	}
	//After Using DTO
	@GetMapping("/qualified-and-not-selected")
	public ResponseEntity<List<FullRequestApplicationDTO>> getQualifiedAndNotSelectedRequests() {
	    try {
	        List<FullRequestApplicationDTO> requests = requestApplicationService.getQualifiedAndNotSelectedRequests();
	        return ResponseEntity.ok(requests);
	    } catch (RuntimeException e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}

	/*--------------------------------------------------------------------------------------------------------------------------*/

	/**
	 * Handles POST requests to set sponsor status as selected.
	 *
	 * @param payload Contains application IDs and status.
	 * @return A ResponseEntity with the updated applications or an error message.
	 */
	@PostMapping("/request-application/sponsorStatus")
	public ResponseEntity<?> updateSponsorStatusSelected(@RequestBody Map<String, Object> payload) {
		try {
			// Get the authenticated username (email) from the SecurityContext
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String email = authentication.getName();

			@SuppressWarnings("unchecked")
			List<String> applicationIds = (List<String>) payload.get("applicationIds");
			String status = (String) payload.get("status");

			if (applicationIds == null || status == null) {
				return ResponseEntity.badRequest().body("Invalid payload: Missing required fields");
			}

			// Update sponsor status with email from SecurityContext
			List<RequestApplication> updatedApplications = requestApplicationService
					.updateSponsorStatusSelected(applicationIds, email, status);
			return ResponseEntity.ok(updatedApplications);
		} catch (ClassCastException e) {
			return ResponseEntity.badRequest().body("Invalid payload: Incorrect data types");
		} catch (Exception e) {
			// Log the exception (e.g., using a logger)
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while updating sponsor status");
		}
	}

	/*--------------------------------------------------------------------------------------------------------------------------*/

	/**
	 * Sets the sponsor status to 'Qualified'.
	 * 
	 * @param applicationId The ID of the application to update.
	 * @param status        The new sponsor status.
	 * @return A ResponseEntity with a success or error message.
	 */
	@PostMapping("/request-application/sponsorStatusQualified")
	public ResponseEntity<String> updateSponsorStatusQualified(@RequestParam("applicationId") String applicationId,
			@RequestParam("status") String status) {
		try {
			requestApplicationService.updateSponsorStatusQualified(applicationId, status);
			return ResponseEntity.ok("Statuses updated successfully");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	/*--------------------------------------------------------------------------------------------------------------------------*/

	/**
	 * Gets all request applications for the specified company email.
	 * 
	 * @param companyEmail The email of the company to retrieve applications for.
	 * @return A ResponseEntity containing the list of request applications or an
	 *         error message.
	 */
     // Before using DTO
//	@GetMapping("/getRequestApplicationByCompanyName")
//	public ResponseEntity<?> getRequestApplicationsByCompanyEmail() {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		String companyEmail = authentication.getName();
//		try {
//			List<RequestApplication> applications = requestApplicationService
//					.findApplicationsByCompanyEmail(companyEmail);
//			return ResponseEntity.ok(applications);
//		} catch (IllegalArgumentException ex) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Company not found for email: " + companyEmail);
//		} catch (Exception ex) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//					.body("Error retrieving applications: " + ex.getMessage());
//		}
//	}
	
	// After using DTO
	@GetMapping("/getRequestApplicationByCompanyName")
	public ResponseEntity<?> getRequestApplicationsByCompanyEmail() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String companyEmail = authentication.getName();
	    try {
	        List<FullRequestApplicationDTO> applications = requestApplicationService
	                .findApplicationsByCompanyEmail(companyEmail);
	        return ResponseEntity.ok(applications);
	    } catch (IllegalArgumentException ex) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Company not found for email: " + companyEmail);
	    } catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Error retrieving applications: " + ex.getMessage());
	    }
	}


	/*--------------------------------------------------------------------------------------------------------------------------*/
	/**
	 * Updates the board status for a given application.
	 * 
	 * @param request A map containing the application ID and the new board status.
	 * @return A ResponseEntity indicating the result of the operation.
	 */
	@PostMapping("/updateBoardStatus")
	public ResponseEntity<?> updateBoardStatus(@RequestBody Map<String, String> request) {
		String applicationId = request.get("applicationId");
		String boardStatus = request.get("boardStatus");

		if (applicationId == null || boardStatus == null) {
			return ResponseEntity.badRequest().body("Application ID and board status must be provided.");
		}

		try {
			requestApplicationService.updateBoardStatus(applicationId, boardStatus);
			return ResponseEntity.ok("Board status updated successfully.");
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update board status.");
		}
	}
	/*--------------------------------------------------------------------------------------------------------------------------*/

	/**
	 * Retrieves all applications where the sponsor has submitted to CSR.
	 * 
	 * @return A list of RequestApplication instances that are sponsor-submitted to
	 *         CSR.
	 */
//	@GetMapping("/applications/sponsor-submittedToCsr")
//	public ResponseEntity<List<RequestApplication>> getSponsorSubmittedApplications() {
//		try {
//			List<RequestApplication> applications = requestApplicationService.getSponsorSubmittedApplications();
//			return ResponseEntity.ok(applications);
//		} catch (Exception e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//		}
//	}
	@GetMapping("/applications/sponsor-submittedToCsr")
    public ResponseEntity<List<FullRequestApplicationDTO>> getSponsorSubmittedApplications() {
        try {
            // Call the service method to return the DTOs
            List<FullRequestApplicationDTO> applications = requestApplicationService.getSponsorSubmittedApplications();
            return ResponseEntity.ok(applications);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
	/*--------------------------------------------------------------------------------------------------------------------------*/

	/**
	 * Retrieves all applications for a user by their email.
	 * 
	 * @param userGmail The user's email address.
	 * @return A list of RequestApplication instances associated with the user.
	 */
	@GetMapping("/getUserApplicationsByEmail")
	public ResponseEntity<List<RequestApplication>> getUserApplicationsByEmail() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userGmail = authentication.getName();
		try {
			List<RequestApplication> applications = requestApplicationService.fetchApplicationsByUserEmail(userGmail);
			return ResponseEntity.ok(applications);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	/*--------------------------------------------------------------------------------------------------------------------------*/

	/**
	 * Handles file uploads for an application.
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
	 * @return A response indicating success or failure of the upload.
	 */
	@PostMapping("/uploadDocuments/{applicationId}")
	public ResponseEntity<?> handleFileUpload(@PathVariable("applicationId") String applicationId,
			@RequestParam(name = "detailedEstimateCopy", required = false) MultipartFile detailedEstimateCopy,
			@RequestParam(name = "landDocuments", required = false) MultipartFile landDocuments,
			@RequestParam(name = "technicalClearanceDocuments", required = false) MultipartFile technicalClearanceDocuments,
			@RequestParam(name = "consentOfficerApprovalDocuments", required = false) MultipartFile consentOfficerApprovalDocuments,
			@RequestParam(name = "relatedPhotos", required = false) MultipartFile relatedPhotos,
			@RequestParam(name = "misc", required = false) MultipartFile misc) {

		try {
			requestApplicationService.saveApplicationWithFiles(applicationId, detailedEstimateCopy, landDocuments,
					technicalClearanceDocuments, consentOfficerApprovalDocuments, relatedPhotos, misc);

			Map<String, Object> response = new HashMap<>();
			response.put("message", "Files uploaded successfully");
			return ResponseEntity.ok(response);

		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Failed to upload files: " + e.getMessage());
		}
	}
	/*--------------------------------------------------------------------------------------------------------------------------*/

	/**
	 * Handles the upload of work completed certificate and updates the application.
	 *
	 * @param applicationId            The ID of the application to update.
	 * @param workCompletedCertificate The work completed certificate document
	 *                                 (optional).
	 * @return ResponseEntity with a message or error status.
	 */
	@PostMapping("/uploadWorkCompletedDocument/{applicationId}")
	public ResponseEntity<?> handleWorkCompletedFileUpload(@PathVariable("applicationId") String applicationId,
			@RequestParam(name = "workCompletedCertificate") MultipartFile workCompletedCertificate) {

		try {
			// Call service method to save the uploaded file
			requestApplicationService.saveApplicationWithWorkCompletedCopy(applicationId, workCompletedCertificate);

			// Prepare and return success response
			Map<String, Object> response = new HashMap<>();
			response.put("message", "Work Completed Copy uploaded successfully");
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			e.printStackTrace();
			// Return error response
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	/*--------------------------------------------------------------------------------------------------------------------------*/

	/**
	 * Handles the upload of utilization certificate and updates the application.
	 *
	 * @param applicationId          The ID of the application to update.
	 * @param utilizationCertificate The utilization certificate document
	 *                               (optional).
	 * @return ResponseEntity with a message or error status.
	 */
	@PostMapping("/uploadUtilizationCertificate/{applicationId}")
	public ResponseEntity<?> handleUtilizationCertificateFileUpload(@PathVariable("applicationId") String applicationId,
			@RequestParam(name = "utilizationCertificate") MultipartFile utilizationCertificate) {

		try {
			// Call service method to save the uploaded file
			requestApplicationService.saveApplicationWithUtilizationCertificate(applicationId, utilizationCertificate);

			// Prepare and return success response
			Map<String, Object> response = new HashMap<>();
			response.put("message", "Utilization Certificate uploaded successfully");
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			e.printStackTrace();
			// Return error response
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	/*--------------------------------------------------------------------------------------------------------------------------*/

	/**
	 * Handles the upload of AS certificate and updates the application.
	 *
	 * @param applicationId The ID of the application to update.
	 * @param asCertificate The AS certificate document (optional).
	 * @return ResponseEntity with a message or error status.
	 */
	@PostMapping("/uploadAScertificate/{applicationId}")
	public ResponseEntity<?> asCertificateFileUpload(@PathVariable("applicationId") String applicationId,
			@RequestParam(name = "AS_Certificate", required = false) MultipartFile asCertificate) {

		try {
			// Call service method to save the uploaded file
			requestApplicationService.saveApplicationWithAScertificate(applicationId, asCertificate);

			// Prepare and return success response
			Map<String, Object> response = new HashMap<>();
			response.put("message", "AS-Certificate uploaded successfully");
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			e.printStackTrace();
			// Return error response
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	/*--------------------------------------------------------------------------------------------------------------------------*/

}