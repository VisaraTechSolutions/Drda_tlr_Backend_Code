package com.csr.csrwebapplication.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csr.csrwebapplication.Model.LoginRequest;
import com.csr.csrwebapplication.Model.RegistrationRequest;
import com.csr.csrwebapplication.Service.UserModelService;

@RestController
@RequestMapping("/api")
public class UserModelController {

	@Autowired
	private UserModelService UserModelService;

	@PostMapping("/signup")
	public ResponseEntity<String> registerUser(@RequestBody RegistrationRequest request) {
		return UserModelService.registerUser(request);
	}

	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody LoginRequest request) {
		return UserModelService.loginUser(request);
	}

}
