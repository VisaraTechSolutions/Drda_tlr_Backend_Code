package com.csr.csrwebapplication.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csr.csrwebapplication.Model.CompanyModel;
import com.csr.csrwebapplication.Service.CompanyModelService;

@RestController
@RequestMapping("/api")
public class CompanyModelController {

	@Autowired
	private CompanyModelService companyModelService;

	@GetMapping("/getCompanyNameByEmail")
	public ResponseEntity<String> getCompanyNameByEmail() {
		// Get the authenticated username (email) from the SecurityContext
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String companyEmail = authentication.getName();
		try {
			String companyName = companyModelService.getCompanyNameByEmail(companyEmail);
			return ResponseEntity.ok().body(companyName);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
		}
	}

	@GetMapping("/sponsors")
	public List<CompanyModel> getAllSponsors() {
		return companyModelService.getAllSponsors();
	}

}
