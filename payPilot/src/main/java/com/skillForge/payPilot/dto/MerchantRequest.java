package com.skillForge.payPilot.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record MerchantRequest(
		
		@NotBlank(message = "Business name is mandatory")
		String businessName,
		
		@Email(message = "Invalid email format")
		@NotBlank(message = "email is mandatory")
		String email
		
		) {

}
