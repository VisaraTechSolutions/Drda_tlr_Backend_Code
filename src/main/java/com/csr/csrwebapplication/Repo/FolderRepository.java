package com.csr.csrwebapplication.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.csr.csrwebapplication.Model.FolderModel;

public interface FolderRepository extends JpaRepository<FolderModel, Long> {
	List<FolderModel> findByParentId(Long parentId);

}
