package com.skillForge.payPilot.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record Merchant(String id, @NotBlank(message = "Name is a required field") String name,
		@NotBlank(message = "Email is a required field") @Email(message = "Invalid email address") String email,
		KycStatus keycStatus) {

}
