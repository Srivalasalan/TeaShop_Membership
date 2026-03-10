package com.teashop.Membership.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MemberLoginDTO {
	@NotBlank(message = "Phone is required")
    @Size(max = 10,min = 10,message = "Not Exceed 10 digits")
    @Positive
	 private String phone;
	@NotBlank(message = "PIN is required")
    @Size(min = 4, max = 4, message = "PIN must be 4 digits")
	 private String pin;
}
