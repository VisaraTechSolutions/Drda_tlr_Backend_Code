package com.csr.csrwebapplication.Model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "folders")
public class FolderModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(name = "create_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	@OneToMany(mappedBy = "folder", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private Set<FileModel> files = new HashSet<>();

	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private Set<FolderModel> children = new HashSet<>();

	@ManyToOne
	@JoinColumn(name = "parent_id")
	@JsonBackReference
	private FolderModel parent;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Set<FileModel> getFiles() {
		return files;
	}

	public void setFiles(Set<FileModel> files) {
		this.files = files;
	}

	public Set<FolderModel> getChildren() {
		return children;
	}

	public void setChildren(Set<FolderModel> children) {
		this.children = children;
	}

	public FolderModel getParent() {
		return parent;
	}

	public void setParent(FolderModel parent) {
		this.parent = parent;
	}

	// Getters and setters

}