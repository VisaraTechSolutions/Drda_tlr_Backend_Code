package com.csr.csrwebapplication.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.csr.csrwebapplication.Model.FileModel;
import com.csr.csrwebapplication.Service.FileService;

@RestController
@RequestMapping("/files")
public class FileController {

	@Autowired
	private FileService fileService;

	/**
	 * Endpoint to get files associated with a folder.
	 *
	 * @param id The ID of the folder.
	 * @return List of FileModel.
	 */
	@GetMapping("/{id}/files")
	public List<FileModel> getFiles(@PathVariable Long id) {
		// Delegate to service layer
		return fileService.getFiles(id);
	}

	/**
	 * Endpoint to upload a file to a folder.
	 *
	 * @param file     The file to be uploaded.
	 * @param folderId The ID of the folder where the file will be saved.
	 * @return ResponseEntity with the saved FileModel or an error message.
	 */
	@PostMapping("/upload")
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file,
			@RequestParam("folderId") Long folderId) throws IOException {
		try {
			// Delegate to service layer
			FileModel savedFile = fileService.uploadFile(file, folderId);
			return ResponseEntity.ok(savedFile);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	/**
	 * Endpoint to get a file by its ID.
	 *
	 * @param id The ID of the file.
	 * @return ResponseEntity with file content or a not found status.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
		try {
			// Delegate to service layer
			FileModel file = fileService.getFileById(id);
			byte[] fileContent = file.getFileData();

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); // Set content type based on file type
			headers.setContentDisposition(ContentDisposition.builder("inline").filename(file.getFileName()).build());
			return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

	/**
	 * Endpoint to delete a file by its ID.
	 *
	 * @param fileId The ID of the file to be deleted.
	 * @return ResponseEntity with status message.
	 */
	@DeleteMapping("/{fileId}/delete")
	public ResponseEntity<String> deleteFile(@PathVariable("fileId") Long fileId) {
		try {
			// Delegate to service layer
			fileService.deleteFile(fileId);
			return ResponseEntity.ok("File deleted successfully");
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting file");
		}
	}

}
