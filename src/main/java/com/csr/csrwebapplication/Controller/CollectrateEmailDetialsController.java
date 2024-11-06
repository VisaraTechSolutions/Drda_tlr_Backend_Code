package com.csr.csrwebapplication.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csr.csrwebapplication.Service.CollectrateEmailDetialsService;

@Component
@RestController
@RequestMapping("/api")
public class CollectrateEmailDetialsController {

	@Autowired
	private CollectrateEmailDetialsService collectrateEmailDetialsService;

	@GetMapping("/run")
	public String initialize() {
		collectrateEmailDetialsService.init();
		return "Initialization completed";
	}

	/**
	 * Handles GET requests to retrieve user information.
	 * 
	 * @return A ResponseEntity containing a map with the user's email and user
	 *         type, or an error status if something goes wrong.
	 */
	@GetMapping("/getUserInfo")
	public ResponseEntity<Map<String, Object>> getUserInfo() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();

		String userType = collectrateEmailDetialsService.getUserTypeByUseremail(email);

		// Prepare the response data in a Map, including the user's email and user type.
		Map<String, Object> userInfo = new HashMap<>();
		userInfo.put("email", email);
		userInfo.put("userType", userType);

		// Return the user information as a JSON response with an HTTP 200 OK status.
		return ResponseEntity.ok(userInfo);
	}

	@GetMapping("/getDrdaCsrOfficeNameByEmail")
	public ResponseEntity<String> getDrdaCsrOfficeNameByEmail() {
		// Get the authenticated username (email) from the SecurityContext
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String drdaCsrOfficeEmail = authentication.getName();

		return collectrateEmailDetialsService.getOfficeNameByEmail(drdaCsrOfficeEmail);
	}

	@GetMapping("/getBdoOfficeNameByEmail")
	public ResponseEntity<String> getBdoOfficeNameByEmail() {
		// Get the authenticated username (email) from the SecurityContext
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String bdoOfficeEmail = authentication.getName();
		return collectrateEmailDetialsService.getOfficeNameByEmail(bdoOfficeEmail);
	}

	@GetMapping("/getUsernameByEmail")
	public ResponseEntity<String> getUsernameByEmail() {
		// Get the authenticated username (email) from the SecurityContext
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		return collectrateEmailDetialsService.getUsernameByEmail(email);
	}

}