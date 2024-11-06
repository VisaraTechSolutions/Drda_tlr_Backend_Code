package com.csr.csrwebapplication.Service;

import org.springframework.http.ResponseEntity;

import com.csr.csrwebapplication.Model.CollectrateEmailDetialsModel;

public interface CollectrateEmailDetialsService {

	CollectrateEmailDetialsModel findByColletorateEmails(String email);

	CollectrateEmailDetialsModel findById(String id);

	ResponseEntity<String> getOfficeNameByEmail(String email);

	ResponseEntity<String> getUsernameByEmail(String email);

	void init();

	String getUserTypeByUseremail(String email);

}
