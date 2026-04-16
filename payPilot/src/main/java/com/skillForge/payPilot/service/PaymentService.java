package com.skillForge.payPilot.service;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.skillForge.payPilot.dto.PaymentRequest;
import com.skillForge.payPilot.dto.PaymentResponse;

@Service
public interface PaymentService {

	PaymentResponse createPaymentIntent(PaymentRequest req);
	
	PaymentResponse getStatusByIntentId(String id);

}
