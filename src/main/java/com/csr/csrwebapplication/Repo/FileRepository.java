package com.csr.csrwebapplication.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.csr.csrwebapplication.Model.FileModel;

public interface FileRepository extends JpaRepository<FileModel, Long> {
	List<FileModel> findByFolderId(Long folderId);
}
