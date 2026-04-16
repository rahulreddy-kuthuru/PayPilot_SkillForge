package com.skillForge.payPilot.dto;

import java.math.BigDecimal;

public record PaymentResponse(
		
		String intentId,
		BigDecimal amount,
		String status,
		String referenceId
		
		) {

}
