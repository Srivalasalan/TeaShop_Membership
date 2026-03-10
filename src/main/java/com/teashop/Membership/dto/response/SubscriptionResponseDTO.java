package com.teashop.Membership.dto.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class SubscriptionResponseDTO {

	private Long id;
	private String planName;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private int totalTeas;
	private int teasUsed;
	private int remainingTeas;
	private double amountPaid;
	private String status;
}
