package com.csr.csrwebapplication.ServiceImpl;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.csr.csrwebapplication.Model.FileModel;
import com.csr.csrwebapplication.Model.FolderModel;
import com.csr.csrwebapplication.Repo.FileRepository;
import com.csr.csrwebapplication.Service.FileService;
import com.csr.csrwebapplication.Service.FolderService;

@Service
public class FileServiceImpl implements FileService {

	@Autowired
	private FileRepository fileRepository;

	@Autowired
	private FolderService folderService;

	public FileModel saveFile(FileModel fileModel) {
		return fileRepository.save(fileModel);
	}

	@Override
	public List<FileModel> getFiles(Long folderId) {
		// Return files associated with the given folderId
		List<FileModel> files = fileRepository.findByFolderId(folderId);
		return files.isEmpty() ? Collections.emptyList() : files;
	}

	@Override
	public FileModel uploadFile(MultipartFile file, Long folderId) throws IOException {
		Optional<FolderModel> folderOptional = folderService.findById(folderId);
		if (!folderOptional.isPresent()) {
			throw new RuntimeException("Folder not found.");
		}

		FolderModel folder = folderOptional.get();

		FileModel fileModel = new FileModel();
		fileModel.setFileName(file.getOriginalFilename());
		fileModel.setCreateDate(new Date());
		fileModel.setFileData(file.getBytes());
		fileModel.setFolder(folder);

		return fileRepository.save(fileModel);
	}

	@Override
	public FileModel getFileById(Long id) {
		return fileRepository.findById(id).orElse(null); // Returns the file if found, else null
	}

	@Override
	@Transactional
	public void deleteFile(Long fileId) {
		if (!fileRepository.existsById(fileId)) {
			throw new RuntimeException("File not found with id: " + fileId);
		}
		fileRepository.deleteById(fileId);
	}
}