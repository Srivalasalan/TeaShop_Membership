package com.teashop.Membership.dto.request;

import lombok.Data;

@Data
public class SubscriptionRequestDTO {
	
	private String phone;
	private Long planId;

}
