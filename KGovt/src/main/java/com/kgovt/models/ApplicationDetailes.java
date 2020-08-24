package com.kgovt.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@Table(name = "APPLICATION_DETAILS")
public class ApplicationDetailes implements Serializable, Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "record_id")
	private Long recordId;
	
	@Column(name = "address_file_name")
	private String addressFileName;
	
	@Column(name = "address_proof_type")
	private String addressProofType;
	
	private Integer age;
	
	@Column(name = "applicant_number")
	public long applicantNumber = 1000L;
	
	@Column(name = "application_status")
	private String applicationStatus;
	
	private String caste;
	
	private String designation;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date doa;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date dob;
	
	private String email;
	
	private Long mobile;
	
	@Column(name = "farther_name")
	private String fartherName;
	
	@Column(name = "mother_name")
	private String motherName;
	
	private String nationality;
	
	public String name;
	
	@Column(name = "noc_file_name")
	public String nocFileName;
	
	@Column(name = "pg_file_name")
	public String pgFileName;
	
	@Column(name = "pg_institution")
	public String pgInstitution;
	
	@Column(name = "pg_marks")
	public String pgMarks;
	
	@Column(name = "pg_passing_year_month")
	public String pgPassingYearMonth;
	
	@Column(name = "pg_percentage")
	public String pgPercentage;
	
	@Column(name = "photo_file_name")
	public String photoFileName;
	
	public int pincode;

	@Column(name = "pre_of_center")
	public String preOfCenter;
	
	@Column(name = "puc_file_name")
	public String pucFileName;
	
	@Column(name = "puc_institution")
	public String pucInstitution;
	
	@Column(name = "puc_marks")
	public String pucMarks;
	
	@Column(name = "puc_passing_year_month")
	public String pucPassingYearMonth;
	
	@Column(name = "puc_percentage")
	public String pucPercentage;
	
	public String religion;
	
	@Column(name = "res_address")
	public String resAddress;
	
	@Column(name = "service_cert_file_name")
	public String serviceCertFileName;
	
	public String gender;
	
	@Column(name = "signature_file_name")
	public String signatureFileName;
	
	@Column(name = "society_address")
	public String societAddress;
	
	@Column(name = "society_district")
	public String societyDistrict;
	
	@Column(name = "society_email")
	public String societyEmail;
	
	@Column(name = "society_name")
	public String societyName;
	
	@Column(name = "society_pincode")
	public String societyPincode;
	
	@Column(name = "society_post")
	public String societyPost;
	
	@Column(name = "society_pre_of_service")
	public String societyPreOfService;
	
	@Column(name = "society_taluk")
	public String societyTaluk;
	
	@Column(name = "society_tel_number")
	public String societyTelNumber;
	
	public String sponsor;
	
	@Column(name = "sslc_file_name")
	public String sslcFileName;
	
	@Column(name = "sslc_institution")
	public String sslcInstitution;
	
	@Column(name = "sslc_marks")
	public String sslcMarks;
	
	@Column(name = "sslc_passing_year_month")
	public String sslcPassingYearMonth;
	
	@Column(name = "sslc_percentage")
	public String sslcPercentage;
	
	@Column(name = "ug_file_name")
	public String ugFileName;
	
	@Column(name = "ug_institution")
	public String ugInstitution;
	
	@Column(name = "ug_marks")
	public String ugMarks;
	
	@Column(name = "ug_passing_year_month")
	public String ugPassingYearMonth;
	
	@Column(name = "ug_percentage")
	public String ugPercentage;
	
	public Boolean declarationAccepted = false;
	
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name = "creation_date")
	public Date creationDate;
	
	private String other;
	
	@Transient
	private MultipartFile sslcFile;
	@Transient
	private MultipartFile pucFile;
	@Transient
	private MultipartFile ugFile;
	@Transient
	private MultipartFile pgFile;
	@Transient
	private MultipartFile photoFile;
	@Transient
	private MultipartFile addressFile;
	@Transient
	private MultipartFile certificateFile;


}
