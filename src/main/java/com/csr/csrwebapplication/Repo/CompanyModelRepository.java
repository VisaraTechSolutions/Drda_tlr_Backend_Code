package com.csr.csrwebapplication.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.csr.csrwebapplication.Model.CompanyModel;

@Repository
public interface CompanyModelRepository extends JpaRepository<CompanyModel, String> {

	CompanyModel findByCompanyEmail(String companyEmail);

	CompanyModel findByCompanyEmailAndCompanyPassword(String email, String password);

	CompanyModel findByCompanyName(String companyName);

	CompanyModel findByCinNumber(String cinNumber);

	 
}
