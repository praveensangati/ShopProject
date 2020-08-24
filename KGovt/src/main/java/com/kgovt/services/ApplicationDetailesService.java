package com.kgovt.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kgovt.models.ApplicationDetailes;
import com.kgovt.repositories.ApplicationDetailesRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ApplicationDetailesService {

	@Autowired
	private ApplicationDetailesRepository applicationDetailesRepository;
		
	public ApplicationDetailes saveApplicationDetailes(ApplicationDetailes applicationDetailes)  {
		applicationDetailes = applicationDetailesRepository.save(applicationDetailes);
		return applicationDetailes;
	}
	
	public ApplicationDetailes findByApplicantNumber(String applicantNumber) {
		return applicationDetailesRepository.findByApplicantNumber(applicantNumber);
	}
	
	public Long countByMobile(Long mobile) {
		return applicationDetailesRepository.countByMobile(mobile);
	}
	
	public Long max() {
		return applicationDetailesRepository.max();
	}
	
	
}
