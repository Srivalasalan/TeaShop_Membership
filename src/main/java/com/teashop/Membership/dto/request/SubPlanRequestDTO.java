package com.teashop.Membership.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class SubPlanRequestDTO {
	
	@NotBlank
	private String planName;
	@Positive
	private int totalTeas;
	@Positive
	private double amount;
	@Positive
	private int durationDays;

}
