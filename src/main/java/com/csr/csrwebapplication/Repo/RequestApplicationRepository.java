package com.csr.csrwebapplication.Repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.csr.csrwebapplication.Model.RequestApplication;

@Repository
public interface RequestApplicationRepository extends JpaRepository<RequestApplication, String> {

	@Query("SELECT r FROM RequestApplication r WHERE r.bdoStatus = 'BDO-Qualified' AND (r.sponsorStatus IS NULL OR r.sponsorStatus = '' OR r.fundingStatus = '50% Partially Funded By Company')")
	List<RequestApplication> findQualifiedAndNotSelectedOrFundingNotFullyFunded();

	List<RequestApplication> findBySponsor(String companyName);

//	@Query("SELECT ra FROM RequestApplication ra WHERE ra.sponsorStatus = 'Qualified' AND ra.drdaCSROfficeStatus = 'CSR-Office-Qualified'")
//	List<RequestApplication> findSponsorSubmittedApplications();

	// Method to get applications based on sponsor status and CSR status
	List<RequestApplication> findBySponsorStatusAndDrdaCSROfficeStatus(String sponsorStatus,
			String drdaCSROfficeStatus);

//	List<RequestApplication> findByUserUserId(String userId);

	Optional<RequestApplication> findByApplicationId(String applicationId);

	List<RequestApplication> findByUserCollectrateId(String userId);

	List<RequestApplication> findBySponsorStatus(String string);

}
