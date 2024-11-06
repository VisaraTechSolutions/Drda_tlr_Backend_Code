package com.csr.csrwebapplication.Model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class UsersModel {

	@Id
	private String userId;

	@Column(nullable = false)
	private String userType;

	@Column(nullable = false)
	private String name;

//	@Column(nullable = false)
//	private String dob;

	@Column(nullable = false)
	private String aadhar;

	@Column(nullable = false)
	private String mobile;

	@Column(nullable = false)
	private String address;

//	@Column(nullable = false)
//	private String street;
//
//	@Column(nullable = false)
//	private String district;
//
//	@Column(nullable = false)
//	private String state;

	@Column(nullable = false)
	private String pincode;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date registrationDateTime;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLoginDateTime;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	public String getDob() {
//		return dob;
//	}
//
//	public void setDob(String dob) {
//		this.dob = dob;
//	}

	public String getAadhar() {
		return aadhar;
	}

	public void setAadhar(String aadhar) {
		this.aadhar = aadhar;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

//	public String getHouseNumber() {
//		return houseNumber;
//	}
//
//	public void setHouseNumber(String houseNumber) {
//		this.houseNumber = houseNumber;
//	}
//
//	public String getStreet() {
//		return street;
//	}
//
//	public void setStreet(String street) {
//		this.street = street;
//	}
//
//	public String getDistrict() {
//		return district;
//	}
//
//	public void setDistrict(String district) {
//		this.district = district;
//	}
//
//	public String getState() {
//		return state;
//	}
//
//	public void setState(String state) {
//		this.state = state;
//	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
