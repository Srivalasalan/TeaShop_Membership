package com.teashop.Membership.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "otp")
public class Otp {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY	)
	private Long id;
	private String phone;
	private String otpCode;
	@CreationTimestamp
	private LocalDateTime generatedAt;
	private boolean isUsed;

}
