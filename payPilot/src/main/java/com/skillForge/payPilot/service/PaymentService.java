package com.skillForge.payPilot.service;

import javax.validation.Valid;

import com.skillForge.payPilot.dto.PaymentRequest;
import com.skillForge.payPilot.dto.PaymentResponse;

public interface PaymentService {

	PaymentResponse createPaymentIntent(PaymentRequest req);
	
	PaymentResponse getStatusByIntentId(String id);

}
