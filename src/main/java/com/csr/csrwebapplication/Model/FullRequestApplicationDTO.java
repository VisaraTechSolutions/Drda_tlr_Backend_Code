package com.csr.csrwebapplication.Model;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FullRequestApplicationDTO {
	private String applicationId;
	private String requester;
	private String sponsor;
	private String request;
	private String sector;
	private String tor;
	private String district;
	private String block;
	private String village;
	private String habitation;
	private String street;
	private String requestComments;
	private Date registrationDateTime;
	private Date selectedDateFromCommonpool;
	private byte[] roadImage;
	private String drdaCSROfficeStatus;
	private String bdoStatus;
	private String districtCollectorateStatus;
	private String sponsorStatus;
	private String boardStatus;
	private String workProgressStatus;
	private String fundingStatus;
	private String asCertificateStatus;
	private String workCompletedCertificateStatus;
	private String utilizationCertificateStatus;
	private String documentStatus;
	private String estimateAmount;
	private byte[] asCertificate;
	private byte[] detailedEstimateCopy;
	private byte[] landDocuments;
	private byte[] technicalClearanceDocuments;
	private byte[] consentOfficerApprovalDocuments;
	private byte[] relatedPhotos;
	private byte[] misc;
	private byte[] workCompletedCertificate;
	private byte[] utilizationCertificate;

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

	public Date getSelectedDateFromCommonpool() {
		return selectedDateFromCommonpool;
	}

	public void setSelectedDateFromCommonpool(Date selectedDateFromCommonpool) {
		this.selectedDateFromCommonpool = selectedDateFromCommonpool;
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
