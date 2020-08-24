package com.kgovt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kgovt.models.ApplicationDetailes;

public interface ApplicationDetailesRepository extends JpaRepository<ApplicationDetailes, Integer> {

	ApplicationDetailes findByApplicantNumber(String applicantNumber);
	
	Long countByMobile(Long mobile);
	
	@Query(value = "SELECT max(applicantNumber) from ApplicationDetailes")
	Long max();
	

}
