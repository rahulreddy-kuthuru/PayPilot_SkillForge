package com.skillForge.payPilot.service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.skillForge.payPilot.dto.PaymentRequest;
import com.skillForge.payPilot.dto.PaymentResponse;

@Service
public class PaymentServiceImpl implements PaymentService{

	private final Map<String, PaymentResponse> intentRepo = new ConcurrentHashMap<>();
	
	@Override
	public PaymentResponse createPaymentIntent(PaymentRequest req) {
		
		PaymentResponse response = new PaymentResponse(req.merchantId(), req.amount(), req.referenceId(), req.currency());
		intentRepo.put(req.merchantId(), response);
		return response;
	}

	@Override
	public PaymentResponse getStatusByIntentId(String id) {
		return intentRepo.get(id);
	}

}
