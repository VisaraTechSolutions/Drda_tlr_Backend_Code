package com.csr.csrwebapplication.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.csr.csrwebapplication.Model.UsersModel;

@Repository
public interface UserModelRepository extends JpaRepository<UsersModel, String> {

	UsersModel findByEmail(String email);

	UsersModel findByEmailAndPassword(String email, String password);
}
