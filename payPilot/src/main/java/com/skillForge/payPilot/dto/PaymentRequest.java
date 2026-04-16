package com.skillForge.payPilot.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;


public record PaymentRequest(
		
		@NotNull @DecimalMin("0.01")
		BigDecimal amount,
		
		@NotBlank(message = "merchantId is a required field") String merchantId,
		
		@NotBlank(message = "referenceId is a required field") String referenceId,
		
		String currency
		) {

}
