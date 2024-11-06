package com.csr.csrwebapplication.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csr.csrwebapplication.Model.FolderCreateRequest;
import com.csr.csrwebapplication.Model.FolderModel;
import com.csr.csrwebapplication.Model.RenameFolderRequest;
import com.csr.csrwebapplication.Service.FolderService;

/**
 * Controller for handling folder-related requests.
 */
@RestController
@RequestMapping("/folders")
public class FolderController {

	@Autowired
	private FolderService folderService;

	/**
	 * Endpoint to create a new folder.
	 *
	 * @param request The request body containing folder details.
	 * @return ResponseEntity with the created FolderModel.
	 */
	@PostMapping
	public ResponseEntity<FolderModel> createFolder(@RequestBody FolderCreateRequest request) {
		// Debugging
		System.out.println("Creating folder with name: " + request.getName() + ", parentId: " + request.getParentId());
		FolderModel folder = folderService.createFolder(request.getName(), request.getParentId());
		return ResponseEntity.ok(folder);
	}

	/**
	 * Endpoint to get all folders.
	 *
	 * @return ResponseEntity with a list of FolderModel.
	 */
	@GetMapping
	public ResponseEntity<List<FolderModel>> getAllFolders() {
		List<FolderModel> allFolders = folderService.getAllFolders();
		return ResponseEntity.ok(allFolders);
	}

	/**
	 * Endpoint to rename a folder.
	 *
	 * @param folderId The ID of the folder to be renamed.
	 * @param request  The request body containing the new folder name.
	 * @return ResponseEntity with status message.
	 */
	@PutMapping("/{id}/rename")
	public ResponseEntity<String> renameFolder(@PathVariable("id") Long folderId,
			@RequestBody RenameFolderRequest request) {
		try {
			folderService.renameFolder(folderId, request.getName());
			return ResponseEntity.ok("Folder renamed successfully");
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Folder not found");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to rename folder");
		}
	}

	/**
	 * Endpoint to delete a folder.
	 *
	 * @param folderId The ID of the folder to be deleted.
	 * @return ResponseEntity with status message.
	 */
	@DeleteMapping("/{id}/delete")
	public ResponseEntity<String> deleteFolder(@PathVariable("id") Long folderId) {
		try {
			folderService.deleteFolder(folderId);
			return ResponseEntity.ok("Folder deleted successfully");
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Folder not found or other error occurred");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete folder");
		}
	}
}
