package com.csr.csrwebapplication.Service;

import java.util.List;

import com.csr.csrwebapplication.Model.CompanyModel;

public interface CompanyModelService {
	void save(CompanyModel company);

	CompanyModel findByCompanyEmail(String companyEmail);

	CompanyModel findByCompanyEmailAndCompanyPassword(String email, String password);

	// List<RequestApplication> getApplicationsByCompanyId(String email);

	CompanyModel findByCompanyName(String companyName);

	List<CompanyModel> getAllSponsors();

	CompanyModel findByCinNumber(String cinNumber);

	String getCompanyNameByEmail(String companyEmail);

}
