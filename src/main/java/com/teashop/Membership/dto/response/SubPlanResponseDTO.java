package com.teashop.Membership.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class SubPlanResponseDTO {

	private Long id;
	@NotBlank
	private String planName;
	@Positive
	private int totalTeas;
	@Positive
	private double amount;
	@Positive
	private int durationDays;
}
