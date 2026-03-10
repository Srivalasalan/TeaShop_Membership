package com.teashop.Membership.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MemberRequestDTO {

	@NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Phone is required")
    @Size(max = 10,min = 10,message = "Not Exceed 10 digits")
    @Positive
    private String phone;

    @NotBlank(message = "PIN is required")
    @Size(min = 4, max = 4, message = "PIN must be 4 digits")
    private String pin;
}
