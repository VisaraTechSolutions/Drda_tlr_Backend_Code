package com.csr.csrwebapplication.Model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

@Entity
public class DocumentApplication {

	@Id
	@Column(name = "Document_id")
	private String documentId;

	@Lob
	@Column(name = "image_src", columnDefinition = "LONGBLOB")
	private byte[] imageSrc;

	@ManyToOne
	@JoinColumn(name = "collectrate_Id")
	private CollectrateEmailDetialsModel collectrateEmailDetialsModel;

	@Column(name = "when_created_date", nullable = false)
	private Date whenCreatedDate;

	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDocumentId() {

		return documentId;

	}

	public void setDocumentId(String documentId) {

		this.documentId = documentId;

	}

	public byte[] getImageSrc() {

		return imageSrc;

	}

	public void setImageSrc(byte[] imageSrc) {

		this.imageSrc = imageSrc;

	}

	public CollectrateEmailDetialsModel getCollectrateEmailDetialsModel() {

		return collectrateEmailDetialsModel;

	}

	public void setCollectrateEmailDetialsModel(CollectrateEmailDetialsModel collectrateEmailDetialsModel) {

		this.collectrateEmailDetialsModel = collectrateEmailDetialsModel;

	}

	public Date getWhenCreatedDate() {

		return whenCreatedDate;

	}

	public void setWhenCreatedDate(Date whenCreatedDate) {

		this.whenCreatedDate = whenCreatedDate;

	}

}
