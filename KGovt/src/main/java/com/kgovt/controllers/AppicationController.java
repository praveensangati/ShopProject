package com.kgovt.controllers;

import java.security.SignatureException;
import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

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
import com.kgovt.models.PaymentDetails;
import com.kgovt.repositories.PaymentDetailsRepository;
import com.kgovt.services.ApplicationDetailesService;
import com.kgovt.utils.AppConstants;
import com.kgovt.utils.AppUtilities;

@Controller
public class AppicationController extends AppConstants{
	
	private static final Logger logger = LoggerFactory
			.getLogger(AppicationController.class);
	
	@Autowired
	private ApplicationDetailesService appicationService;
	
	@Autowired
	private PaymentDetailsRepository paymentDetailsRepository;
	
	private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";
	
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
			applicationDetailes = appicationService.saveApplicationAction(applicationDetailes,sslcFile,
					 pucFile, ugFile, pgFile, photoFile,addressFile,
					certificateFile);
			if(null == applicationDetailes) {
				model.addAttribute("errorMessage" , "Ooops Unexpected Error occured while saving Application, Please contact System Administrator !");
				return "error";
			}else {
				PaymentDetails paymentDetails = new PaymentDetails();
				paymentDetails.setAmount(PAYMENT1);
				paymentDetails.setMobile(applicationDetailes.getMobile());
				paymentDetails.setEmail(applicationDetailes.getEmail());
				paymentDetails.setAddress(applicationDetailes.getResAddress());
				paymentDetails.setReceiptNo(AppUtilities.generateReceptNo(applicationDetailes));
				paymentDetails.setDescription("A Payment for Application Submission");
				paymentDetails.setApplicantNumber(applicationDetailes.getApplicantNumber());
				paymentDetails = appicationService.proceedForPayment(paymentDetails);
				if(null == paymentDetails) {
					model.addAttribute("errorMessage" , "Ooops Unexpected Error occured while submitting Payment, Please contact System Administrator !");
					return "error";	
				}else {
					model.addAttribute("paymentDetails" , paymentDetails);
					return "payment";
				}
			}
		}catch(Exception e) {
			logger.error("Exception while saving aapplication", e);	
		}
		return "success";
	}
	
	@PostMapping(SEPERATOR+COMMON_FINAL)
	public String finalFlow(Model model,PaymentDetails paymentDetails) {
		String status = null;
		try {
			String data = paymentDetails.getRazorpayOrderId() +"|"+paymentDetails.getRazorpayPaymentId();
			status = calculateRFC2104HMAC(data, SECRET);
		} catch (SignatureException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			status = null;
		}
		if(null != status && paymentDetails.getRazorpaySignature().equals(status)) {
			try {
				paymentDetails.setCreatedDate(new Date());
				paymentDetailsRepository.save(paymentDetails);
				model.addAttribute("paymentDetails" , paymentDetails);
				model.addAttribute("successMessage" , "Application Submitted Successfully");
			}catch(Exception e) {
				logger.error("Exception while saving aapplication", e);	
			}	
		}else {
			model.addAttribute("paymentDetails" , paymentDetails);
			model.addAttribute("successMessage" , "Wrong Details");
		}
		
		return "success";
	}
	
	
	@PostMapping("/testSave")
	public String savetest(Model model, ApplicationDetailes applicationDetailes, HttpServletRequest request,
			@RequestParam("sslcFile") MultipartFile sslcFile) {
		
		/*
		 * Long mobileCount =
		 * appicationService.countByMobile(applicationDetailes.getMobile());
		 * if(mobileCount > 0) { model.addAttribute("validation" ,
		 * "Mobile Number already registered"); return "form"; }
		 * 
		 * if(!sslcFile.isEmpty()) { String sslcFilePath = fileUploadAndReturn(sslcFile,
		 * String.valueOf(applicationDetailes.getMobile()), "SSLC");
		 * if(AppUtilities.isNotNullAndNotEmpty(sslcFilePath)) {
		 * //applicationDetailes.setSslcFilePath(sslcFilePath);
		 * System.out.print(sslcFilePath); } }
		 */
		
		
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

	
	public static String calculateRFC2104HMAC(String data, String secret) throws java.security.SignatureException {
		String result;
		try {

			// get an hmac_sha256 key from the raw secret bytes
			SecretKeySpec signingKey = new SecretKeySpec(secret.getBytes(), HMAC_SHA256_ALGORITHM);

			// get an hmac_sha256 Mac instance and initialize with the signing key
			Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
			mac.init(signingKey);

			// compute the hmac on input data bytes
			byte[] rawHmac = mac.doFinal(data.getBytes());

			// base64-encode the hmac
			result = DatatypeConverter.printHexBinary(rawHmac).toLowerCase();

		} catch (Exception e) {
			throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
		}
		return result;
	}
	
}
