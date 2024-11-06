package com.csr.csrwebapplication.ServiceImpl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.csr.csrwebapplication.Model.CollectrateEmailDetialsModel;
import com.csr.csrwebapplication.Model.CompanyModel;
import com.csr.csrwebapplication.Model.LoginRequest;
import com.csr.csrwebapplication.Model.LoginResponse;
import com.csr.csrwebapplication.Model.RegistrationRequest;
import com.csr.csrwebapplication.Model.UsersModel;
import com.csr.csrwebapplication.Repo.UserModelRepository;
import com.csr.csrwebapplication.Service.CollectrateEmailDetialsService;
import com.csr.csrwebapplication.Service.CompanyModelService;
import com.csr.csrwebapplication.Service.EmailService;
import com.csr.csrwebapplication.Service.UserModelService;
import com.csr.csrwebapplication.Utils.IdGenerator;
import com.csr.csrwebapplication.jwt.JwtUtil;
import com.csr.csrwebapplication.jwt.SecureAES;

@Service
public class UserModelServiceImpl implements UserModelService {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private CollectrateEmailDetialsService collectrateEmailDetialsService;

	@Autowired
	private CompanyModelService companyService;

	@Autowired
	private UserModelRepository userRepository;

	@Autowired
	private EmailService emailService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void save(UsersModel user) {
		userRepository.save(user);
	}

	@Override
	public UsersModel findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public UsersModel findByEmailAndPassword(String email, String password) {
		return userRepository.findByEmailAndPassword(email, password);
	}

	@Override
	public ResponseEntity<String> registerUser(RegistrationRequest request) {
		// Check if email already exists for companies
		if ("Companies".equals(request.getUserType())) {
			CompanyModel existingCompany = companyService.findByCompanyEmail(request.getCompanyEmail());
			if (existingCompany != null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Company email already registered");
			}
			CompanyModel existingCompanyByCIN = companyService.findByCinNumber(request.getCinNumber());
			if (existingCompanyByCIN != null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CIN number already registered");
			}

			// Proceed with company registration
			CompanyModel company = new CompanyModel();
			company.setCompanyId(IdGenerator.generateCompanyId());
			company.setUserType(request.getUserType());
			company.setCompanyName(request.getCompanyName());
			company.setCompanyEmail(request.getCompanyEmail());
			company.setCinNumber(request.getCinNumber());
			company.setRegistrationDateTime(new Date());
			company.setCompanyPassword(request.getCompanyPassword());
			companyService.save(company);
			emailService.sendUserEmail(company.getCompanyName(), company.getCompanyEmail());

			return ResponseEntity.ok().body("Company registration successful");
		} else {
			return ResponseEntity.badRequest().body("Invalid user type");
		}
	}

	@Override
	public ResponseEntity<?> loginUser(LoginRequest request) {
		String email = request.getEmail();
		String password = request.getPassword();

		// Check in Company table
		CompanyModel company = companyService.findByCompanyEmailAndCompanyPassword(email, password);
		if (company != null) {
			String jwtToken = jwtUtil.generateToken(email);
			return buildResponseWithToken(jwtToken, "companies");
		}

		// Check in CollerateTable for email match
		CollectrateEmailDetialsModel collerateUser = collectrateEmailDetialsService.findByColletorateEmails(email);
		if (collerateUser != null && passwordEncoder.matches(password, collerateUser.getColletoratesPass())) {
			String jwtToken = jwtUtil.generateToken(email);
			return buildResponseWithToken(jwtToken, collerateUser.getUserType());
		}

		// Default case: login failed
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
	}

	private ResponseEntity<?> buildResponseWithToken(String jwtToken, String userType) {
		String encryptedToken = null;

		try {
			// Encrypt the JWT token
			encryptedToken = SecureAES.encrypt(jwtToken);
		} catch (Exception e) {
			// Log the error and return a server error response
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error encrypting token");
		}

		// Return the response with the encrypted token and user type
		return ResponseEntity.ok().body(new LoginResponse(userType, encryptedToken));
	}

}
