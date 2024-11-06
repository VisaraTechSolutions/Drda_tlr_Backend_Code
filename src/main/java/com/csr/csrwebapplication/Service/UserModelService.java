package com.csr.csrwebapplication.Service;

import org.springframework.http.ResponseEntity;

import com.csr.csrwebapplication.Model.LoginRequest;
import com.csr.csrwebapplication.Model.RegistrationRequest;
import com.csr.csrwebapplication.Model.UsersModel;

public interface UserModelService {
	void save(UsersModel user);

	UsersModel findByEmail(String email);

	UsersModel findByEmailAndPassword(String email, String password);

	ResponseEntity<?> loginUser(LoginRequest request);

	ResponseEntity<String> registerUser(RegistrationRequest request);
}
