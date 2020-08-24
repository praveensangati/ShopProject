package com.kgovt.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.kgovt.models.ApplicationDetailes;
import com.kgovt.services.ApplicationDetailesService;
import com.kgovt.utils.AppConstants;
import com.kgovt.utils.AppUtilities;

@Controller
public class AppicationController extends AppConstants{
	
	private static final Logger logger = LoggerFactory
			.getLogger(AppicationController.class);
	
	@Autowired
	private ApplicationDetailesService appicationService;
	
	@GetMapping(SEPERATOR)
	public String getApplicationHome(Model model) {
		return "index";
	}
	
	@GetMapping(SEPERATOR+COMMON_INDEX)
	public String indexPage() {
		return "index";
	}
	
	@GetMapping(SEPERATOR+COMMON_NEW)
	public String applicationNew(Model model) {
		ApplicationDetailes appDetails = new ApplicationDetailes();
		model.addAttribute("applicationDetailes" , appDetails);
		return "form";
	}
	
	@GetMapping("/test")
	public String applicationNew2(Model model) {
		ApplicationDetailes appDetails = new ApplicationDetailes();
		model.addAttribute("applicationDetailes" , appDetails);
		return "form2";
	}
	
	@GetMapping("/validateMobile")
	public String checkMobileExiastes(String mobileNumber) {
		Long mobileCount = appicationService.countByMobile(Long.valueOf(mobileNumber));
		if(mobileCount > 0) {
			return "1";
		}else {
			return "0";
		}
	}
	
	@PostMapping(SEPERATOR+COMMON_SAVE)
	public String saveApplication(Model model, ApplicationDetailes applicationDetailes, HttpServletRequest request,
			@RequestParam("sslcFile") MultipartFile sslcFile, @RequestParam("pucFile") MultipartFile pucFile, @RequestParam("ugFile") MultipartFile ugFile
			, @RequestParam("pgFile") MultipartFile pgFile, @RequestParam("photoFile") MultipartFile photoFile
			, @RequestParam("addressFile") MultipartFile addressFile, @RequestParam("certificateFile") MultipartFile certificateFile) {
		try {
			Long mobileCount = appicationService.countByMobile(applicationDetailes.getMobile());
			if(mobileCount > 0) {
				model.addAttribute("validation" , "Mobile Number already registered");
				return "form";
			}
			
			if(!sslcFile.isEmpty()) {
				String sslcFilePath = fileUploadAndReturn(sslcFile, String.valueOf(applicationDetailes.getMobile()), "SSLC");
				if(AppUtilities.isNotNullAndNotEmpty(sslcFilePath)) {
					applicationDetailes.setSslcFileName(sslcFile.getName());
					System.out.print(sslcFilePath);
				}	
			}
			
			if(!pucFile.isEmpty()) {
				String filePath = fileUploadAndReturn(sslcFile, String.valueOf(applicationDetailes.getMobile()), "PUC");
				if(AppUtilities.isNotNullAndNotEmpty(filePath)) {
					applicationDetailes.setPucFileName(pucFile.getName());
					System.out.print(filePath);
				}	
			}
			
			if(!ugFile.isEmpty()) {
				String filePath = fileUploadAndReturn(sslcFile, String.valueOf(applicationDetailes.getMobile()), "UG");
				if(AppUtilities.isNotNullAndNotEmpty(filePath)) {
					applicationDetailes.setUgFileName(ugFile.getName());
					System.out.print(filePath);
				}	
			}
			
			if(!pgFile.isEmpty()) {
				String filePath = fileUploadAndReturn(sslcFile, String.valueOf(applicationDetailes.getMobile()), "PG");
				if(AppUtilities.isNotNullAndNotEmpty(filePath)) {
					applicationDetailes.setPgFileName(pgFile.getName());
					System.out.print(filePath);
				}	
			}
			
			if(!photoFile.isEmpty()) {
				String filePath = fileUploadAndReturn(sslcFile, String.valueOf(applicationDetailes.getMobile()), "PHOTO");
				if(AppUtilities.isNotNullAndNotEmpty(filePath)) {
					applicationDetailes.setPhotoFileName(photoFile.getName());
					System.out.print(filePath);
				}	
			}
			
			if(!addressFile.isEmpty()) {
				String filePath = fileUploadAndReturn(sslcFile, String.valueOf(applicationDetailes.getMobile()), "ADDRESS");
				if(AppUtilities.isNotNullAndNotEmpty(filePath)) {
					applicationDetailes.setAddressFileName(addressFile.getName());
					System.out.print(filePath);
				}	
			}
			
			if(!certificateFile.isEmpty()) {
				String filePath = fileUploadAndReturn(sslcFile, String.valueOf(applicationDetailes.getMobile()), "CERTIFICATE");
				if(AppUtilities.isNotNullAndNotEmpty(filePath)) {
					applicationDetailes.setServiceCertFileName(certificateFile.getName());
					System.out.print(filePath);
				}	
			}
			
			Long applicantNumber = appicationService.max();
			if(null == applicantNumber) {
				applicantNumber = 100001l;
				applicationDetailes.setApplicantNumber(applicantNumber);
				System.out.print(applicantNumber);
			}else {
				applicationDetailes.setApplicantNumber(applicantNumber+1);
				System.out.print(applicantNumber+1);
			}
			
			System.out.print(applicationDetailes.getDob());
			applicationDetailes.setAddressProofType("PAN");
			applicationDetailes.setApplicationStatus("1");
			applicationDetailes.setCreationDate(new Date());
			applicationDetailes = appicationService.saveApplicationDetailes(applicationDetailes);
			model.addAttribute("applicantNumber" , applicationDetailes.getApplicantNumber());
			model.addAttribute("successMessage" , "Application Submitted successfully !");
			
		}catch(Exception e) {
			logger.error("Exception while saving aapplication", e);	
		}
		return "success";
	}
	
	private String fileUploadAndReturn(MultipartFile file, String mobile, String fileFoler) {
		String path = null;
		try {
			if (!file.isEmpty()) {
				try {
					byte[] bytes = file.getBytes();
					// Creating the directory to store file
					String rootPath = System.getProperty("catalina.home");
					File dir = new File(rootPath + File.separator + "tmpFiles" + File.separator + mobile+ File.separator + fileFoler);
					if (!dir.exists())
						dir.mkdirs();

					// Create the file on server
					File serverFile = new File(dir.getAbsolutePath()
							+ File.separator + file.getName());
					BufferedOutputStream stream = new BufferedOutputStream(
							new FileOutputStream(serverFile));
					stream.write(bytes);
					stream.close();

					logger.info("Server File Location="
							+ serverFile.getAbsolutePath());
					path = serverFile.getAbsolutePath();
					return path;
				} catch (Exception e) {
					return null;
				}
			} else {
				return null;
			}
		}catch(Exception e) {
			
		}
		return path;
	}
	
	@PostMapping("/testSave")
	public String savetest(Model model, ApplicationDetailes applicationDetailes, HttpServletRequest request,
			@RequestParam("sslcFile") MultipartFile sslcFile) {
		
		Long mobileCount = appicationService.countByMobile(applicationDetailes.getMobile());
		if(mobileCount > 0) {
			model.addAttribute("validation" , "Mobile Number already registered");
			return "form";
		}
		
		if(!sslcFile.isEmpty()) {
			String sslcFilePath = fileUploadAndReturn(sslcFile, String.valueOf(applicationDetailes.getMobile()), "SSLC");
			if(AppUtilities.isNotNullAndNotEmpty(sslcFilePath)) {
				//applicationDetailes.setSslcFilePath(sslcFilePath);
				System.out.print(sslcFilePath);
			}	
		}
		
		
		Long applicantNumber = appicationService.max();
		if(null == applicantNumber) {
			applicantNumber = 100001l;
			applicationDetailes.setApplicantNumber(applicantNumber);
			System.out.print(applicantNumber);
		}else {
			applicationDetailes.setApplicantNumber(applicantNumber+1);
			System.out.print(applicantNumber+1);
		}
		
		System.out.print(applicationDetailes.getDob());
		applicationDetailes = appicationService.saveApplicationDetailes(applicationDetailes);
		model.addAttribute("applicantNumber" , applicationDetailes.getApplicantNumber());
		model.addAttribute("applicantNumber" , "Application Submitted successfully !");
		return "success";
	}

	
}
