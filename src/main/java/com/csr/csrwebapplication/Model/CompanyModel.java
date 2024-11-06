package com.csr.csrwebapplication.Model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class CompanyModel {

	@Id
	private String companyId;

	@Column(nullable = false)
	private String userType;

	@Column(nullable = false)
	private String companyName;

	@Column(nullable = false)
	private String companyEmail;

	@Column(nullable = false)
	private String cinNumber;

	@Column(nullable = false)
	private String companyPassword;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date registrationDateTime;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLoginDateTime;

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyEmail() {
		return companyEmail;
	}

	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}

	public String getCinNumber() {
		return cinNumber;
	}

	public void setCinNumber(String cinNumber) {
		this.cinNumber = cinNumber;
	}

	public String getCompanyPassword() {
		return companyPassword;
	}

	public void setCompanyPassword(String password) {
		this.companyPassword = password;
	}

	public Date getRegistrationDateTime() {
		return registrationDateTime;
	}

	public void setRegistrationDateTime(Date registrationDateTime) {
		this.registrationDateTime = registrationDateTime;
	}

	public Date getLastLoginDateTime() {
		return lastLoginDateTime;
	}

	public void setLastLoginDateTime(Date lastLoginDateTime) {
		this.lastLoginDateTime = lastLoginDateTime;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

}
