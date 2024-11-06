package com.csr.csrwebapplication.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.csr.csrwebapplication.Model.CollectrateEmailDetialsModel;
import com.csr.csrwebapplication.Repo.CollectrateEmailDetialsRepository;
import com.csr.csrwebapplication.Service.CollectrateEmailDetialsService;

import jakarta.annotation.PostConstruct;

@Service
public class CollectrateEmailDetialsServiceImpl implements CollectrateEmailDetialsService {

	@Autowired
	private CollectrateEmailDetialsRepository collectrateEmailDetialsRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public CollectrateEmailDetialsModel findByColletorateEmails(String email) {
		return collectrateEmailDetialsRepository.findByColletorateEmails(email);
	}

	@Override
	public CollectrateEmailDetialsModel findById(String id) {
		return collectrateEmailDetialsRepository.findByCollectrateId(id);
	}

	@PostConstruct
	public void init() {
		initializeCollectrateEmails();
	}

	private void initializeCollectrateEmails() {
		CollectrateEmailDetialsModel email1 = new CollectrateEmailDetialsModel();
		email1.setCollectrateId("DRDA001");
		email1.setName("DRDA-CSR OFFICE");
		// email1.setColletorateEmails("dcsro@thiruvallurdistrictcsr.com");
		// email1.setColletoratesPass(passwordEncoder.encode("Dcsro@csr2024"));
		email1.setColletorateEmails("drda@drdatlr.com");
		email1.setColletoratesPass(passwordEncoder.encode("Drda@csr2024"));
		email1.setUserType("CSR-OFFICE");

		CollectrateEmailDetialsModel email2 = new CollectrateEmailDetialsModel();
		email2.setCollectrateId("BDO002");
		email2.setName("BDO-OFFICE");
		// email2.setColletorateEmails("bdo@thiruvallurdistrictcsr.com");
		email2.setColletorateEmails("bdo@drdatlr.com");
		email2.setColletoratesPass(passwordEncoder.encode("Bdo@csr2024"));
		email2.setUserType("BDO-OFFICE");

		// MLA Details
		String[] mlaEmails = { "visaratechsolutions@gmail.com", "mlagummidipoondi@tn.gov.in", "mlaponneri@tn.gov.in",
				"mlatiruttani@tn.gov.in", "mlatiruvallur@tn.gov.in", "mlapoonamallee@tn.gov.in", "mlaavadi@tn.gov.in",
				"mlamaduravoyal@tn.gov.in", "mlaambattur@tn.gov.in", "mlamadavaram@tn.gov.in",
				"mlatiruvottiyur@tn.gov.in" };

		String[] mlaNames = { "Demo MLA", "Gummidipoondi MLA", "Ponneri MLA", "Tiruttani MLA", "Tiruvallur MLA",
				"Ponamallee MLA", "Aavadi MLA", "Maduravoyal MLA", "Ambattur MLA", "Madavaram MLA",
				"Tiruvottiyur MLA" };

		for (int i = 0; i < mlaEmails.length; i++) {
			CollectrateEmailDetialsModel email = new CollectrateEmailDetialsModel();
			email.setCollectrateId(String.format("MLA%03d", i + 3)); // Start IDs from 004 for MLAs
			email.setName(mlaNames[i]);
			email.setColletorateEmails(mlaEmails[i]);
			email.setColletoratesPass(passwordEncoder.encode("MLA@csr2024"));
			email.setUserType("MLA");
			collectrateEmailDetialsRepository.save(email);
		}

		// Save the initial entities in the database
		collectrateEmailDetialsRepository.save(email1);
		collectrateEmailDetialsRepository.save(email2);
	}

	@Override
	public ResponseEntity<String> getOfficeNameByEmail(String email) {
		try {
			CollectrateEmailDetialsModel model = collectrateEmailDetialsRepository.findByColletorateEmails(email);
			if (model == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found");
			}
			return ResponseEntity.ok().body(model.getName());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}
	}

	@Override
	public ResponseEntity<String> getUsernameByEmail(String email) {
		try {
			CollectrateEmailDetialsModel model = collectrateEmailDetialsRepository.findByColletorateEmails(email);
			if (model == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found");
			}
			return ResponseEntity.ok().body(model.getName());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}
	}

	@Override
	public String getUserTypeByUseremail(String email) {
		// Find the user in the Collectrate table by username (email)
		CollectrateEmailDetialsModel collectrate = collectrateEmailDetialsRepository.findByColletorateEmails(email);

		// Return the user type if found, otherwise return null or throw an exception
		if (collectrate != null) {
			return collectrate.getUserType();
		} else {
			throw new IllegalArgumentException("User not found in Collectrate table");
		}
	}

}
