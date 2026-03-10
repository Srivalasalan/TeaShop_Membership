package com.teashop.Membership.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AdminRequestDTO {

	@NotBlank(message = "UserName Cannot Be Blank")
	private String userName;
	@NotBlank(message = "Password Cannot Be Blank")
	private String password;
}
