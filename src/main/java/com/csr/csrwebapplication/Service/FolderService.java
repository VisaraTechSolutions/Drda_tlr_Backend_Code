package com.csr.csrwebapplication.Service;

import java.util.List;
import java.util.Optional;

import com.csr.csrwebapplication.Model.FolderModel;

/**
 * Service interface for handling folder-related operations.
 */
public interface FolderService {

	/**
	 * Get folders by parent ID.
	 *
	 * @param parentId The ID of the parent folder.
	 * @return List of FolderModel.
	 */
	List<FolderModel> getFolders(Long parentId);

	/**
	 * Create a new folder.
	 *
	 * @param name     The name of the new folder.
	 * @param parentId The ID of the parent folder.
	 * @return The created FolderModel.
	 */
	FolderModel createFolder(String name, Long parentId);

	/**
	 * Get all folders.
	 *
	 * @return List of FolderModel.
	 */
	List<FolderModel> getAllFolders();

	/**
	 * Find a folder by its ID.
	 *
	 * @param folderId The ID of the folder.
	 * @return An Optional containing the FolderModel if found.
	 */
	Optional<FolderModel> findById(Long folderId);

	/**
	 * Rename a folder.
	 *
	 * @param folderId The ID of the folder to rename.
	 * @param name     The new name for the folder.
	 */
	void renameFolder(Long folderId, String name);

	/**
	 * Delete a folder.
	 *
	 * @param folderId The ID of the folder to delete.
	 */
	void deleteFolder(Long folderId);
}