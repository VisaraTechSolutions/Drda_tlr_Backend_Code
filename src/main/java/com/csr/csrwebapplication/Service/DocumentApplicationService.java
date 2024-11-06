package com.csr.csrwebapplication.Service;

import java.util.List;

import com.csr.csrwebapplication.Model.DocumentApplication;

public interface DocumentApplicationService {

	/**
	 * Save a document to the database.
	 *
	 * @param email           The email associated with the document.
	 * @param documentsEntity The document entity to be saved.
	 */
	void saveDocument(String email, DocumentApplication documentsEntity);

	/**
	 * Get all document applications.
	 *
	 * @return List of DocumentApplication entities.
	 */
	List<DocumentApplication> getAllDocuments();

	/**
	 * Update the status of a document application.
	 *
	 * @param documentId The ID of the document to update.
	 * @param newStatus  The new status to set.
	 */
	void updateDocumentStatus(String documentId, String newStatus);
}
