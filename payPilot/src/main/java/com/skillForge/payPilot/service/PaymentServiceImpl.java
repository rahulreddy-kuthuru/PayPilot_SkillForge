package com.skillForge.payPilot.service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.skillForge.payPilot.dto.PaymentRequest;
import com.skillForge.payPilot.dto.PaymentResponse;

public class PaymentServiceImpl implements PaymentService{

	private final Map<String, PaymentResponse> intentRepo = new ConcurrentHashMap<>();
	
	@Override
	public PaymentResponse createPaymentIntent(PaymentRequest req) {
		
		String intentId = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
		PaymentResponse response = new PaymentResponse(intentId, req.amount(), "CREATED", req.referenceId());
		intentRepo.put(intentId, response);
		return response;
	}

	@Override
	public PaymentResponse getStatusByIntentId(String id) {
		return intentRepo.get(id);
	}

}
