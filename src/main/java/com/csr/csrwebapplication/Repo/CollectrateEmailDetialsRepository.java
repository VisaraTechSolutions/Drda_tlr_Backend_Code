package com.csr.csrwebapplication.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.csr.csrwebapplication.Model.CollectrateEmailDetialsModel;

@Repository
public interface CollectrateEmailDetialsRepository extends JpaRepository<CollectrateEmailDetialsModel, String> {

	CollectrateEmailDetialsModel findByColletorateEmails(String email);

	CollectrateEmailDetialsModel findByCollectrateId(String id);

}
