package com.csr.csrwebapplication.Service;

import java.util.List;

public interface EmailService {

	void sendSimpleMessage(String to, String subject, String text);

	void sendUserEmail(String username, String to);

	/**
	 * Thanking for application submit to user
	 */
	void thankingForApplicationSubmitThrowEmail(String username, String to, String applicationId);

	/**
	 * Notify CSR office when a user submits a new application via application form.
	 */
	void applicationSubmitByUserSendNotificationToCsroffice(String username, String applicationId);

	/**
	 * Notify BDO office when an application is approved by the CSR office.
	 */
	void applicationApprovedByCsrOffice(String applicationId);

	/**
	 * If bdo qualified sent mail to all companies
	 */
	void applicationNoticeThrowEmail(String applicationId);

	/**
	 * Notify CSR office when a company selects projects from the common pool.
	 */
	void companySelectedProjectsFromCommonPool(String companyName, List<String> projectIds);

	/**
	 * Notify a company when a project is approved by the CSR office.
	 */
	void notifyCompanyOfProjectApproval(String companyName, String projectId, String companyEmail, String csrStatus);

	/**
	 * Notify a company when a certificate is issued by the CSR office.
	 */
	void certificateIssuedByCsrOffice(String companyName, String applicationId, String email);

	/**
	 * Notify a company when a certificate is issued by the CSR office.
	 */
	void notifyWorkInitiation(
			String companyName, String companyEmail, String applicationId, String userEmail,
			String username, String workStatus);

	/**
	 * Notify company about work completed certificate upload by BDO office
	 */
	void notifyWorkCompletedToCompany(String companyName, String companyEmail, String applicationId);

	/**
	 * Notify company about utility certificate upload by CSR office
	 */
	void notifyUtilityCertificateUploaded(String companyName, String companyEmail, String applicationId);

	/**
	 * Notify CSR office when a user submits a new application via Document.
	 */
	void applicationSubmitByUserSendNotificationToCsrofficeIfDocument(String username, String applicationId);

	void notifyCsrOfficeSponsorSubmittedApplicationForApproval(
			String companyName, String fundingStatus,
			String applicationId);
}
