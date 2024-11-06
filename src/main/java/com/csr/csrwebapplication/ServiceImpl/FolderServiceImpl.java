package com.csr.csrwebapplication.ServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csr.csrwebapplication.Model.FolderModel;
import com.csr.csrwebapplication.Repo.FolderRepository;
import com.csr.csrwebapplication.Service.FolderService;

/**
 * Implementation of FolderService with business logic.
 */
@Service
public class FolderServiceImpl implements FolderService {

	@Autowired
	private FolderRepository folderRepository;

	@Override
	public List<FolderModel> getFolders(Long parentId) {
		return folderRepository.findByParentId(parentId);
	}

	@Override
	public FolderModel createFolder(String name, Long parentId) {
		FolderModel parentFolder = parentId != null ? folderRepository.findById(parentId).orElse(null) : null;
		FolderModel folder = new FolderModel();
		folder.setName(name);
		folder.setCreateDate(new Date());
		folder.setParent(parentFolder);
		return folderRepository.save(folder);
	}

	@Override
	public List<FolderModel> getAllFolders() {
		return folderRepository.findAll();
	}

	@Override
	public Optional<FolderModel> findById(Long folderId) {
		return folderRepository.findById(folderId);
	}

	@Override
	@Transactional
	public void renameFolder(Long folderId, String newName) {
		FolderModel folder = folderRepository.findById(folderId)
				.orElseThrow(() -> new RuntimeException("Folder not found with id: " + folderId));
		folder.setName(newName);
		folderRepository.save(folder);
	}

	@Override
	@Transactional
	public void deleteFolder(Long folderId) {
		if (!folderRepository.existsById(folderId)) {
			throw new RuntimeException("Folder not found with id: " + folderId);
		}
		folderRepository.deleteById(folderId);
	}
}
