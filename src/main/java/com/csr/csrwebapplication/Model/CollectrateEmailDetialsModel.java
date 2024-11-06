package com.csr.csrwebapplication.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "CollectorateEmailDetials")
public class CollectrateEmailDetialsModel {

	@Id
	private String collectrateId;

	private String name;

	private String colletorateEmails;

	private String colletoratesPass;

	private String userType;

	public String getCollectrateId() {
		return collectrateId;
	}

	public void setCollectrateId(String collectrate_Id) {
		this.collectrateId = collectrate_Id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColletorateEmails() {
		return colletorateEmails;
	}

	public void setColletorateEmails(String colletorateEmails) {
		this.colletorateEmails = colletorateEmails;
	}

	public String getColletoratesPass() {
		return colletoratesPass;
	}

	public void setColletoratesPass(String colletoratesPass) {
		this.colletoratesPass = colletoratesPass;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

}
