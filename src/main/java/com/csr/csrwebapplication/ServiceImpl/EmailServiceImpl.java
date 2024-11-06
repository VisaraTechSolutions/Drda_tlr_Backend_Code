package com.csr.csrwebapplication.ServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.csr.csrwebapplication.Model.CompanyModel;
import com.csr.csrwebapplication.Repo.CompanyModelRepository;
import com.csr.csrwebapplication.Service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	CompanyModelRepository companyRepository;

	@Override
	public void sendSimpleMessage(String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();

		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		emailSender.send(message);
	}

	@Override
	public void sendUserEmail(String username, String to) {
		String subject = "Registration Sucessful";

		String text = "Dear " + username + ",\n\n"
				+ "Congratulations! Your registration was successful. Welcome to the Thiruvallur District CSR/CER-portal!\n\n"
				+ "We are delighted to have you join our community dedicated to promoting and supporting social responsibility initiatives within our district. Your involvement is crucial in driving meaningful change and making a positive impact on our community.\n\n"
				+ "As a member, you will have access to a range of resources and opportunities to participate in various CSR initiatives. Our portal is designed to help you stay informed about ongoing projects, upcoming events, and collaboration opportunities with other members and organizations. Here are some key features you can explore:\n"
				+ "1. **Project Listings**: Browse and participate in a variety of social responsibility projects.\n"
				+ "2. **Event Calendar**: Stay updated with upcoming CSR events and activities.\n"
				+ "3. **Collaboration Opportunities**: Connect and collaborate with other members and organizations.\n"
				+ "4. **Resource Center**: Access a wealth of resources and materials to support your CSR efforts.\n\n"
				+ "We encourage you to explore the portal and make the most of the available features. Should you have any questions or need assistance navigating the portal, please do not hesitate to contact us at info@thiruvallurdistrictcsr.com. Our support team is always ready to help and provide any guidance you may need.\n\n"
				+ "Once again, welcome to our community. We look forward to supporting you on your journey and working together to achieve our shared goals of social responsibility and community development.\n\n"
				+ "Best regards,\n" + "Thiruvallur District CSR/CER-portal Support Team\n"
				+ "Official Email: info@thiruvallurdistrictcsr.com";

		sendSimpleMessage(to, subject, text);

	}

	/**
	 * Thanking for application submit to user
	 */
	@Override
	public void thankingForApplicationSubmitThrowEmail(String username, String to, String applicationId) {
		String subject = "Application Submitted Successfully";

		String text = "Dear " + username + ",\n\n" + "Congratulations! Your application with the ID " + applicationId
				+ " has been successfully submitted to the Thiruvallur District CSR/CER portal.\n\n"
				+ "We are pleased to acknowledge the receipt of your application. Our dedicated team will thoroughly review the details and take the necessary steps to ensure your application is processed promptly and efficiently.\n\n"
				+ "In the meantime, you can track the status of your application at any time through our website. Simply log in to your account to view the latest updates and progress reports regarding your submission.\n\n"
				+ "If you need any assistance or have any questions, please do not hesitate to contact us at info@thiruvallurdistrictcsr.com. Our support team is always ready to provide you with the help and information you need. We are here to support you every step of the way.\n\n"
				+ "Thank you for your commitment to making a positive impact in our community through your CSR efforts. We look forward to working together to achieve our shared goals.\n\n"
				+ "Best regards,\n" + "Thiruvallur District CSR/CER-portal Support Team\n"
				+ "Official Email: info@thiruvallurdistrictcsr.com";

		sendSimpleMessage(to, subject, text);
	}

	/**
	 * Notify CSR office when a user submits a new application via application form.
	 */
	@Override
	public void applicationSubmitByUserSendNotificationToCsroffice(String username, String applicationId) {
		String subject = "New Application Submitted";
		//String to = "dcsro@thiruvallurdistrictcsr.com";
		String to = "drda@drdatlr.com";

		String text = "Dear CSR Office,\n\n"
				+ "We have received a new application submitted via the application form on our portal.\n\n"
				+ "Application Details:\n" + "Username: " + username + "\n" + "Application ID: " + applicationId
				+ "\n\n"
				+ "Please review the application and take the necessary steps to proceed. Your prompt attention and action on this application will be highly appreciated.\n\n"
				+ "You can view and review the complete details of the application by logging into our website at https://www.thiruvallurdistrictcsr.com.\n\n"
				+ "If you have any questions or require further assistance, please do not hesitate to contact us at our official email address: info@thiruvallurdistrictcsr.com. Our support team is always available to provide any help you may need.\n\n"
				+ "Thank you for your cooperation and support.\n\n" + "Best regards,\n"
				+ "Thiruvallur District CSR/CER-portal Support Team";

		sendSimpleMessage(to, subject, text);
	}

	/**
	 * Notify CSR office when a user submits a new application via Document.
	 */
	@Override
	public void applicationSubmitByUserSendNotificationToCsrofficeIfDocument(String username, String applicationId) {
		String subject = "New Application Submitted";
		//String to = "dcsro@thiruvallurdistrictcsr.com";
		String to = "drda@drdatlr.com";

		String text = "Dear CSR Office,\n\n"
				+ "We have received a new application submitted by a user via document on our portal.\n\n"
				+ "Application Details:\n" + "Username: " + username + "\n" + "Application ID: " + applicationId
				+ "\n\n"
				+ "Please review the application and take the necessary steps to proceed. Your prompt attention and action on this application will be highly appreciated.\n\n"
				+ "You can view and review the complete details of the application by logging into our website at https://www.thiruvallurdistrictcsr.com.\n\n"
				+ "If you have any questions or require further assistance, please do not hesitate to contact us at our official email address: info@thiruvallurdistrictcsr.com. Our support team is always available to provide any help you may need.\n\n"
				+ "Thank you for your cooperation and support.\n\n" + "Best regards,\n"
				+ "Thiruvallur District CSR/CER-portal Support Team";

		sendSimpleMessage(to, subject, text);
	}

	/**
	 * Notify BDO office when an application is approved by the CSR office.
	 */
	@Override
	public void applicationApprovedByCsrOffice(String applicationId) {
		String subject = "Application Approved by CSR Office";
		//String to = "bdo@thiruvallurdistrictcsr.com";	
		String to = "bdo@drdatlr.com";

		String text = "Dear BDO Office,\n\n"
				+ "We are pleased to inform you that an application submitted by a user has been successfully approved by the CSR office and now requires your esteemed office's further action.\n\n"
				+ "Application ID: " + applicationId + "\n\n"
				+ "As part of our commitment to ensuring the highest standards of community support and development, we request you to review the application details and take the necessary steps to proceed. Your prompt attention to this matter is crucial in advancing our collective efforts towards community betterment.\n\n"
				+ "You can access and review the full details of the application through our dedicated portal. Please log in to our website at: https://thiruvallurdistrictcsr.com using your credentials to view all relevant information.\n\n"
				+ "We greatly appreciate your cooperation and dedication to our community initiatives. Your role in this process is vital, and we value the support and expertise your office brings to these projects.\n\n"
				+ "Should you have any questions or require further assistance, please do not hesitate to contact us. Our team is here to support you and ensure a seamless process.\n\n"
				+ "Thank you for your continued collaboration and commitment to making a positive impact.\n\n"
				+ "Best regards,\n" + "Thiruvallur District CSR/CER-portal Support Team\n"
				+ "Official Email: info@thiruvallurdistrictcsr.com";

		sendSimpleMessage(to, subject, text);
	}

	/**
	 * If bdo qualified sent mail to all companies
	 */
	@Override
	public void applicationNoticeThrowEmail(String applicationId) {

		List<CompanyModel> companyModels = companyRepository.findAll();

		Map<String, String> companyEmailMap = new HashMap<>();

		for (CompanyModel company : companyModels) {
			companyEmailMap.put(company.getCompanyName(), company.getCompanyEmail());
		}

		String subject = "New Application Found";

		for (Map.Entry<String, String> entry : companyEmailMap.entrySet()) {
			String username = entry.getKey();
			String email = entry.getValue();

			String text = "Dear " + username + ",\n\n"
					+ "We are pleased to inform you that a new application has been submitted from the BDO office to our common pool on the website. The details of the application are as follows:\n\n"
					+ "Application ID: " + applicationId + "\n\n"
					+ "We kindly request you to review the application on our portal at your earliest convenience. Your prompt attention to this matter will greatly aid in the swift processing and handling of the application.\n\n"
					+ "For any further information or assistance, please do not hesitate to reach out to us at info@thiruvallurdistrictcsr.com. Our support team is always ready to assist you with any queries or concerns you may have.\n\n"
					+ "Thank you for your cooperation and continued support.\n\n" + "Best regards,\n"
					+ "Thiruvallur District CSR/CER-portal Support Team";

			sendSimpleMessage(email, subject, text);
		}
	}

	/**
	 * Notify CSR office when a company selects projects from the common pool.
	 */
	@Override
	public void companySelectedProjectsFromCommonPool(String companyName, List<String> projectIds) {
		String subject = "Projects Selected by Company";
		//String to = "dcsro@thiruvallurdistrictcsr.com";
		String to = "drda@drdatlr.com";
		StringBuilder projectIdsText = new StringBuilder();
		for (String projectId : projectIds) {
			projectIdsText.append("Application Id- ").append(projectId).append("\n");
		}
		System.out.println(projectIdsText.toString());
		String text = "Dear CSR Office,\n\n" + "We are pleased to inform you that the company " + companyName
				+ " has selected the following projects from the common pool for further action. These projects have been carefully chosen based on their alignment with the company’s corporate social responsibility goals and their potential impact on the community.\n\n"
				+ "Selected Application IDs:\n" + projectIdsText.toString() + "\n\n"
				+ "We kindly request you to review the details of these projects and take the necessary steps to proceed with their implementation. Your prompt attention to this matter will greatly contribute to the timely and effective execution of these initiatives.\n\n"
				+ "You can find comprehensive information and detailed descriptions of each project on our website. Please visit the following link to access the project details:\n"
				+ "Website: https://thiruvallurdistrictcsr.com\n\n"
				+ "If you have any questions or require further assistance, please do not hesitate to contact us at info@thiruvallurdistrictcsr.com. Our support team is ready to provide any additional information or clarification you may need.\n\n"
				+ "Thank you for your cooperation and commitment to making a positive impact in our community through these CSR initiatives.\n\n"
				+ "Best regards,\n" + "Thiruvallur District CSR/CER-portal Support Team";

		sendSimpleMessage(to, subject, text);
	}

	/**
	 * Notify CSR office when a company approve the application and submit to csr
	 * approval.
	 */
	@Override
	public void notifyCsrOfficeSponsorSubmittedApplicationForApproval(String companyName, String fundingStatus,
			String applicationId) {
		String subject = "Sponsor Submitted For Approval";
		//String to = "dcsro@thiruvallurdistrictcsr.com"; 
		String to = "drda@drdatlr.com";

		String text = "Dear CSR Office,\n\n" + "We are pleased to inform you that the company " + companyName
				+ " has reviewed and approved the application with the ID " + applicationId
				+ " for further processing.\n\n" + "Application Details:\n" + "Company Name: " + companyName + "\n"
				+ "Application ID: " + applicationId + "\n" + "Funding Status: " + fundingStatus + "\n\n"
				+ "The next step involves obtaining your approval to proceed with the approved funding. We kindly request you to review the application details and provide your confirmation to move forward.\n\n"
				+ "You can access the full application details and make your approval decision through our website at https://www.thiruvallurdistrictcsr.com.\n\n"
				+ "If you have any questions or need additional information, please feel free to contact us at info@thiruvallurdistrictcsr.com. Our team is here to assist you with any queries or support you may require.\n\n"
				+ "Thank you for your attention to this matter. We look forward to your prompt response and approval.\n\n"
				+ "Best regards,\n" + "Thiruvallur District CSR/CER-portal Support Team";

		sendSimpleMessage(to, subject, text);
	}

	/**
	 * Notify a company when a project is approved by the CSR office.
	 */
	@Override
	public void notifyCompanyOfProjectApproval(String companyName, String projectId, String companyEmail,
			String csrStatus) {
		String subject = "Application Approval Notification";
		String subject1 = "Application Rejection Notification";
		//String to = "bdo@thiruvallurdistrictcsr.com";
		String to = "bdo@drdatlr.com";

		if (csrStatus.equals("DRDA-CSR-Approved")) {
			String text = "Dear BDO Office,\n\n" + "We are pleased to inform you that application with ID " + projectId
					+ " has been successfully approved by the CSR office.\n\n"
					+ "The CSR office has reviewed and approved application submission, and we commend your efforts in promoting social responsibility.\n\n"
					+ "Please proceed with the next steps as per the guidelines provided. You can view the application status and additional details on our website.\n\n"
					+ "Should you have any questions or require further assistance, please do not hesitate to contact us.\n\n"
					+ "Thank you for your co-operation and continued commitment to our shared goals.\n\n"
					+ "Best regards,\n" + "Thiruvallur District CSR/CER-portal Support Team\n"
					+ "Official Email: info@thiruvallurdistrictcsr.com";

			String text1 = text.replace("BDO Office", companyName);
			sendSimpleMessage(to, subject, text);
			sendSimpleMessage(companyEmail, subject, text1);

		} else if (csrStatus.equals("DRDA-CSR-Rejected")) {
			String text = "Dear BDO Office,\n\n" + "We regret to inform you that application with ID " + projectId
					+ " has unfortunately been rejected by the CSR office.\n\n"
					+ "After careful consideration, the CSR office found that the application did not meet the necessary criteria for approval. We encourage you to review the feedback provided and make any necessary revisions before resubmission.\n\n"
					+ "You can check the application status and obtain further details through our website.\n\n"
					+ "We apologize for any inconvenience this may cause and appreciate your understanding and efforts in this regard.\n\n"
					+ "Best regards,\n" + "Thiruvallur District CSR/CER-portal Support Team\n"
					+ "Official Email: info@thiruvallurdistrictcsr.com";

			String text1 = text.replace("BDO Office", companyName);
			sendSimpleMessage(to, subject1, text);
			sendSimpleMessage(companyEmail, subject1, text1);
		}
	}

	/**
	 * Notify a company when AS certificate is issued by the CSR office.
	 */
	@Override
	public void certificateIssuedByCsrOffice(String companyName, String applicationId, String email) {
		String subject = "Administrative Sanction Copy Issued by CSR Office";
		//String to = "bdo@thiruvallurdistrictcsr.com";
		String to = "bdo@drdatlr.com";

		String text = "Dear BDO Office,\n\n"
				+ "We are pleased to inform you that a certificate has been issued by the CSR office for your application.\n\n"
				+ "Application ID: " + applicationId + "\n\n" + "Certificate Details:\n"
				+ "The certificate titled 'Administrative Sanction Copy' confirms the approval and completion of the necessary requirements for your application. Please review the details of the certificate to ensure all information is accurate and complete.\n\n"
				+ "Next Steps:\n" + "1. Verify the certificate details and ensure all information is correct.\n"
				+ "2. Take any required actions as per the instructions mentioned in the certificate.\n"
				+ "3. If there are any discrepancies or additional information required, please contact us at your earliest convenience.\n\n"
				+ "You can check the certificate details and further information through our website at https://www.thiruvallurdistrictcsr.com. Our online portal provides comprehensive details about your application and certificate status.\n\n"
				+ "If you have any questions or need further assistance, please do not hesitate to reach out. We are here to support you throughout this process.\n\n"
				+ "Thank you for your co-operation and prompt attention to this matter.\n\n" + "Best regards,\n"
				+ "Thiruvallur District CSR/CER-portal Support Team\n"
				+ "Official Email: info@thiruvallurdistrictcsr.com";

		String text1 = text.replaceAll("BDO Office", companyName);
		sendSimpleMessage(email, subject, text1);
		sendSimpleMessage(to, subject, text);
	}

 
	@Override
	public void notifyWorkInitiation(String companyName, String companyEmail, String applicationId, String userEmail,
			String username, String workStatus) {
		String subject = "Work Status Updated For: " + applicationId;
		//String to = "dcsro@thiruvallurdistrictcsr.com"; // CSR email
		String to = "drda@drdatlr.com";

		// Base content for the notification
		String baseText = "Dear [Recipient],\n\n"
				+ "We are writing to update you on the status of the work related to the application mentioned below.\n\n"
				+ "Application ID: " + applicationId + "\n\n"
				+ "Please find the status update and further details below:\n\n" + "Status: " + workStatus + "\n\n"
				+ "You can check the detailed status and additional information through our website at https://www.thiruvallurdistrictcsr.com.\n\n"
				+ "If you have any questions or require further assistance, please do not hesitate to contact us. We are here to support you throughout this process.\n\n"
				+ "Thank you for your attention and co-operation.\n\n" + "Best regards,\n"
				+ "Thiruvallur District CSR/CER-portal Support Team\n"
				+ "Official Email: info@thiruvallurdistrictcsr.com";

		// Content for each recipient
		if (workStatus.equals("Work Initiated")) {
			String companyText = baseText.replace("[Recipient]", companyName).replace("Status: Work Initiated",
					"Status: Work Initiated\n\n"
							+ "The BDO office has started the work as per the application requirements.\n\n"
							+ "Please ensure to review any forthcoming instructions and updates related to this work.\n\n"
							+ "We will keep you informed about further developments.");
			String userText = baseText.replace("[Recipient]", username).replace("Status: Work Initiated",
					"Status: Work Initiated\n\n"
							+ "The work on your application has been initiated by the BDO office.\n\n"
							+ "Please be attentive to any updates or instructions related to your application.\n\n"
							+ "We will notify you of any further progress or actions required.");

			sendSimpleMessage(companyEmail, subject, companyText);
			sendSimpleMessage(to, subject, baseText.replace("[Recipient]", "CSR Office"));
			sendSimpleMessage(userEmail, subject, userText);
		} else if (workStatus.equals("Work Completed")) {
			String companyText = baseText.replace("[Recipient]", companyName).replace("Status: Work Initiated",
					"Status: Work Completed\n\n"
							+ "We are pleased to inform you that the work related to the application has been successfully completed.\n\n"
							+ "Please review the completed work and ensure all requirements have been met.\n\n"
							+ "If there are any issues or further actions required, please contact us.");
			String userText = baseText.replace("[Recipient]", username).replace("Status: Work Initiated",
					"Status: Work Completed\n\n" + "The work on your application has been completed.\n\n"
							+ "Please review the outcome and let us know if any further actions are needed.\n\n"
							+ "We appreciate your patience and cooperation throughout this process.");
			String csrText = baseText.replace("Status: Work Initiated", "Status: Work Completed\n\n"
					+ "The work related to the application has been completed. Please review the details and ensure that all procedures have been followed.\n\n"
					+ "If additional steps are required or if there are any discrepancies, please address them accordingly.");

			sendSimpleMessage(companyEmail, subject, companyText);
			sendSimpleMessage(to, subject, csrText);
			sendSimpleMessage(userEmail, subject, userText);
		}
	}

	/**
	 * Notify company about work completed certificate upload by BDO office
	 */
	@Override
	public void notifyWorkCompletedToCompany(String companyName, String companyEmail, String applicationId) {
		String subject = "Work Completed Certificate Uploaded";

		String text = "Dear " + companyName + ",\n\n"
				+ "We are pleased to inform you that the certificate of completion for your application with ID "
				+ applicationId + " has been successfully uploaded by the BDO office.\n\n" + "Certificate Details:\n"
				+ "The certificate issued confirms that all required work and documentation for your application have been completed and reviewed. The certificate includes verification of compliance with all necessary standards and regulations.\n\n"
				+ "Next Steps:\n"
				+ "1. Please review the uploaded certificate to ensure that all details are correct and complete.\n"
				+ "2. If you find any discrepancies or have any questions regarding the certificate, please contact us at your earliest convenience.\n"
				+ "3. You can access and download the certificate through our online portal at https://www.thiruvallurdistrictcsr.com. Navigate to the 'My Applications' section to view and download your certificate.\n\n"
				+ "For any further assistance or additional information, please do not hesitate to reach out to our support team. We are here to help you with any queries or issues you may have.\n\n"
				+ "Thank you for your co-operation and continued engagement with our CSR program.\n\n"
				+ "Best regards,\n" + "Thiruvallur District CSR/CER-portal Support Team\n"
				+ "Official Email: info@thiruvallurdistrictcsr.com";

		sendSimpleMessage(companyEmail, subject, text);
	}

	/**
	 * Notify company about utility certificate upload by CSR office
	 */
	@Override
	public void notifyUtilityCertificateUploaded(String companyName, String companyEmail, String applicationId) {
		String subject = "Utilization Certificate Uploaded";

		String text = "Dear " + companyName + ",\n\n"
				+ "We are pleased to inform you that the utility certificate for your application with ID "
				+ applicationId + " has been successfully uploaded by the CSR office.\n\n" + "Certificate Details:\n"
				+ "The utility certificate confirms that all required utility-related requirements for your application have been met and verified. This document serves as proof of compliance with the necessary utility standards and regulations.\n\n"
				+ "Next Steps:\n"
				+ "1. Please review the uploaded utility certificate to ensure that all information is accurate and complete.\n"
				+ "2. You can access and download the certificate through our online portal at https://www.thiruvallurdistrictcsr.com. Navigate to the 'My Applications' section to view and download your certificate.\n"
				+ "3. If you encounter any issues or have questions regarding the certificate, please reach out to us for assistance.\n\n"
				+ "Thank you for your attention to this matter and for your continued co-operation.\n\n"
				+ "Best regards,\n" + "Thiruvallur District CSR/CER-portal Support Team\n"
				+ "Official Email: info@thiruvallurdistrictcsr.com";

		sendSimpleMessage(companyEmail, subject, text);
	}

}
