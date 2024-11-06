package com.csr.csrwebapplication.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.csr.csrwebapplication.Model.DocumentApplication;

@Repository
public interface DocumentsApplicationRepository extends JpaRepository<DocumentApplication, String> {

	DocumentApplication findByDocumentId(String documentId);

}