package com.kgovt.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

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
@Table(name = "PAYMENT_DETAILS")
public class PaymentDetails implements Serializable, Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "record_id")
	private Long recordId;
	
	private String razorpayPaymentId;

	private String razorpayOrderId;

	@Transient
	private String razorpaySignature;

	private String orderId;
	
	private Date createdDate;
	
	private String receiptNo;
	
	private Long applicantNumber;
	
	private Integer amount;
	
	@Transient
	private String currency;
	
	@Transient
	private String key;
	
	private Long mobile;
	
	@Transient
	private String email;
	
	@Transient
	private String address;
	
	@Transient
	private String description;
	
}
