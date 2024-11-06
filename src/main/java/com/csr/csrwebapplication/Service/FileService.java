package com.csr.csrwebapplication.Service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.csr.csrwebapplication.Model.FileModel;

public interface FileService {

	FileModel saveFile(FileModel fileModel);

	/**
	 * Get all files associated with a folder.
	 *
	 * @param folderId The ID of the folder.
	 * @return List of FileModel.
	 */
	List<FileModel> getFiles(Long folderId);

	/**
	 * Save a file entity to the database.
	 *
	 * @param file     The file to be uploaded.
	 * @param folderId The ID of the folder where the file will be saved.
	 * @return The saved FileModel.
	 * @throws IOException If an error occurs while handling the file.
	 */
	FileModel uploadFile(MultipartFile file, Long folderId) throws IOException;

	/**
	 * Get a file by its ID.
	 *
	 * @param id The ID of the file.
	 * @return The FileModel if found, otherwise null.
	 */
	FileModel getFileById(Long id);

	/**
	 * Delete a file by its ID.
	 *
	 * @param fileId The ID of the file to be deleted.
	 */
	void deleteFile(Long fileId);
}
