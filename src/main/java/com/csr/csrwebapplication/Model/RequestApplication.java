package com.csr.csrwebapplication.Model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class RequestApplication {

	@Id
	@Column(name = "application_id")
	private String applicationId;

	@Column(nullable = false)
	private String requester;

	private String sponsor;

	@Column(nullable = false)
	private String request;

	@Column(nullable = false)
	private String sector;

	@Column(nullable = false)
	private String tor;

	@Column(nullable = false)
	private String district;

	@Column(nullable = false)
	private String block;

	@Column(nullable = false)
	private String village;

	@Column(nullable = false)
	private String habitation;

	@Column(nullable = false)
	private String street;

//	@Column(name = "road_length", nullable = true)
//	private String roadLength;

	private String requestComments;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "registration_date_time")
	private Date registrationDateTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "selectedDateFromCommonpool")
	private Date selectedDateFromCommonpool;

	@Lob
	@Column(name = "current_road_image", columnDefinition = "LONGBLOB")
	private byte[] roadImage;

	@Column(name = "drda_csr_office_status")
	private String drdaCSROfficeStatus;

	@Column(name = "bdo_status")
	private String bdoStatus;

	@Column(name = "district_collectorate_status")
	private String districtCollectorateStatus;

	@Column(name = "sponsor_status")
	private String sponsorStatus;

	@Column(name = "board_status")
	private String boardStatus;

	@Column(name = "work_progress_status")
	private String workProgressStatus;

	@Column(name = "funding_arrangement_status")
	private String fundingStatus;

	@Column(name = "as_certificate_status")
	private String asCertificateStatus;

	@Column(name = "work_completed_certificate_status")
	private String workCompletedCertificateStatus;

	@Column(name = "utilization_certificate_status")
	private String utilizationCertificateStatus;

	@Column(name = "document_status")
	private String documentStatus;

	@Column(name = "estimate_amount")
	private String estimateAmount;

	// Document fields
	@Lob
	@Column(name = "as_certificate", columnDefinition = "LONGBLOB")
	private byte[] asCertificate;

	@Lob
	@Column(name = "detailed_estimate_copy", columnDefinition = "LONGBLOB")
	private byte[] detailedEstimateCopy;

	@Lob
	@Column(name = "land_documents", columnDefinition = "LONGBLOB")
	private byte[] landDocuments;

	@Lob
	@Column(name = "technical_clearance_documents", columnDefinition = "LONGBLOB")
	private byte[] technicalClearanceDocuments;

	@Lob
	@Column(name = "consent_officer_approval_documents", columnDefinition = "LONGBLOB")
	private byte[] consentOfficerApprovalDocuments;

	@Lob
	@Column(name = "related_photos", columnDefinition = "LONGBLOB")
	private byte[] relatedPhotos;

	@Lob
	@Column(name = "misc", columnDefinition = "LONGBLOB")
	private byte[] misc;

	@Lob
	@Column(name = "work_completed_certificate", columnDefinition = "LONGBLOB")
	private byte[] workCompletedCertificate;

	@Lob
	@Column(name = "utilization_certificate", columnDefinition = "LONGBLOB")
	private byte[] utilizationCertificate;

//	@ManyToOne
//	@JoinColumn(name = "user_id")
//	private UsersModel user;

	@ManyToOne
	@JoinColumn(name = "collectrate_Id")
	private CollectrateEmailDetialsModel user;

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getRequester() {
		return requester;
	}

	public void setRequester(String requester) {
		this.requester = requester;
	}

	public String getSponsor() {
		return sponsor;
	}

	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getTor() {
		return tor;
	}

	public void setTor(String tor) {
		this.tor = tor;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public String getHabitation() {
		return habitation;
	}

	public void setHabitation(String habitation) {
		this.habitation = habitation;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

//	public String getRoadLength() {
//		return roadLength;
//	}
//
//	public void setRoadLength(String roadLength) {
//		this.roadLength = roadLength;
//	}

	public String getRequestComments() {
		return requestComments;
	}

	public void setRequestComments(String requestComments) {
		this.requestComments = requestComments;
	}

	public Date getRegistrationDateTime() {
		return registrationDateTime;
	}

	public void setRegistrationDateTime(Date registrationDateTime) {
		this.registrationDateTime = registrationDateTime;
	}

	public byte[] getRoadImage() {
		return roadImage;
	}

	public void setRoadImage(byte[] roadImage) {
		this.roadImage = roadImage;
	}

	public String getDrdaCSROfficeStatus() {
		return drdaCSROfficeStatus;
	}

	public void setDrdaCSROfficeStatus(String drdaCSROfficeStatus) {
		this.drdaCSROfficeStatus = drdaCSROfficeStatus;
	}

	public String getBdoStatus() {
		return bdoStatus;
	}

	public void setBdoStatus(String bdoStatus) {
		this.bdoStatus = bdoStatus;
	}

	public String getDistrictCollectorateStatus() {
		return districtCollectorateStatus;
	}

	public void setDistrictCollectorateStatus(String districtCollectorateStatus) {
		this.districtCollectorateStatus = districtCollectorateStatus;
	}

	public String getSponsorStatus() {
		return sponsorStatus;
	}

	public void setSponsorStatus(String sponsorStatus) {
		this.sponsorStatus = sponsorStatus;
	}

	public String getBoardStatus() {
		return boardStatus;
	}

	public void setBoardStatus(String boardStatus) {
		this.boardStatus = boardStatus;
	}

	public String getWorkProgressStatus() {
		return workProgressStatus;
	}

	public void setWorkProgressStatus(String workProgressStatus) {
		this.workProgressStatus = workProgressStatus;
	}

	public String getFundingStatus() {
		return fundingStatus;
	}

	public void setFundingStatus(String fundingStatus) {
		this.fundingStatus = fundingStatus;
	}

	public String getAsCertificateStatus() {
		return asCertificateStatus;
	}

	public void setAsCertificateStatus(String asCertificateStatus) {
		this.asCertificateStatus = asCertificateStatus;
	}

	public String getDocumentStatus() {
		return documentStatus;
	}

	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}

	public String getEstimateAmount() {
		return estimateAmount;
	}

	public void setEstimateAmount(String estimateAmount) {
		this.estimateAmount = estimateAmount;
	}

	public byte[] getAsCertificate() {
		return asCertificate;
	}

	public void setAsCertificate(byte[] asCertificate) {
		this.asCertificate = asCertificate;
	}

	public byte[] getDetailedEstimateCopy() {
		return detailedEstimateCopy;
	}

	public void setDetailedEstimateCopy(byte[] detailedEstimateCopy) {
		this.detailedEstimateCopy = detailedEstimateCopy;
	}

	public byte[] getLandDocuments() {
		return landDocuments;
	}

	public void setLandDocuments(byte[] landDocuments) {
		this.landDocuments = landDocuments;
	}

	public byte[] getTechnicalClearanceDocuments() {
		return technicalClearanceDocuments;
	}

	public void setTechnicalClearanceDocuments(byte[] technicalClearanceDocuments) {
		this.technicalClearanceDocuments = technicalClearanceDocuments;
	}

	public byte[] getConsentOfficerApprovalDocuments() {
		return consentOfficerApprovalDocuments;
	}

	public void setConsentOfficerApprovalDocuments(byte[] consentOfficerApprovalDocuments) {
		this.consentOfficerApprovalDocuments = consentOfficerApprovalDocuments;
	}

	public byte[] getRelatedPhotos() {
		return relatedPhotos;
	}

	public void setRelatedPhotos(byte[] relatedPhotos) {
		this.relatedPhotos = relatedPhotos;
	}

	public byte[] getMisc() {
		return misc;
	}

	public void setMisc(byte[] misc) {
		this.misc = misc;
	}

	public CollectrateEmailDetialsModel getUser() {
		return user;
	}

	public void setUser(CollectrateEmailDetialsModel user) {
		this.user = user;
	}

	public Date getSelectedDateFromCommonpool() {
		return selectedDateFromCommonpool;
	}

	public void setSelectedDateFromCommonpool(Date selectedDateFromCommonpool) {
		this.selectedDateFromCommonpool = selectedDateFromCommonpool;
	}

	public String getWorkCompletedCertificateStatus() {
		return workCompletedCertificateStatus;
	}

	public void setWorkCompletedCertificateStatus(String workCompletedCertificateStatus) {
		this.workCompletedCertificateStatus = workCompletedCertificateStatus;
	}

	public String getUtilizationCertificateStatus() {
		return utilizationCertificateStatus;
	}

	public void setUtilizationCertificateStatus(String utilizationCertificateStatus) {
		this.utilizationCertificateStatus = utilizationCertificateStatus;
	}

	public byte[] getWorkCompletedCertificate() {
		return workCompletedCertificate;
	}

	public void setWorkCompletedCertificate(byte[] workCompletedCertificate) {
		this.workCompletedCertificate = workCompletedCertificate;
	}

	public byte[] getUtilizationCertificate() {
		return utilizationCertificate;
	}

	public void setUtilizationCertificate(byte[] utilizationCertificate) {
		this.utilizationCertificate = utilizationCertificate;
	}

}
