package com.csr.csrwebapplication.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csr.csrwebapplication.Model.CompanyModel;
import com.csr.csrwebapplication.Repo.CompanyModelRepository;
import com.csr.csrwebapplication.Service.CompanyModelService;

@Service
public class CompanyModelServiceImpl implements CompanyModelService {

	@Autowired
	private CompanyModelRepository companyRepository;

	@Override
	public void save(CompanyModel company) {
		companyRepository.save(company);
	}

	@Override
	public CompanyModel findByCompanyEmail(String companyEmail) {
		return companyRepository.findByCompanyEmail(companyEmail);
	}

	@Override
	public CompanyModel findByCompanyEmailAndCompanyPassword(String email, String password) {
		return companyRepository.findByCompanyEmailAndCompanyPassword(email, password);
	}

	@Override
	public CompanyModel findByCompanyName(String companyName) {
		return companyRepository.findByCompanyName(companyName);
	}

	@Override
	public String getCompanyNameByEmail(String companyEmail) {
		CompanyModel companyModel = companyRepository.findByCompanyEmail(companyEmail);
		if (companyModel == null) {
			throw new IllegalArgumentException("Company not found with email: " + companyEmail);
		}
		return companyModel.getCompanyName();
	}

	@Override
	public List<CompanyModel> getAllSponsors() {
		return companyRepository.findAll(); // Adjust method as per your repository
	}

	@Override
	public CompanyModel findByCinNumber(String cinNumber) {
		return companyRepository.findByCinNumber(cinNumber);
	}
}
