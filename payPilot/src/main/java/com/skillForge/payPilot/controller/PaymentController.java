package com.skillForge.payPilot.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillForge.payPilot.dto.PaymentRequest;
import com.skillForge.payPilot.dto.PaymentResponse;
import com.skillForge.payPilot.service.PaymentService;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;
	
	@PostMapping("/intents")
	public ResponseEntity<PaymentResponse> createPaymentIntent(
			@RequestHeader("X-Idempotency-Key") String idempotencyKey,
			@Valid @RequestBody PaymentRequest req){
		PaymentResponse response = paymentService.createPaymentIntent(req);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("{id}/status")
	public ResponseEntity<PaymentResponse> getStatsByIntentId(@PathVariable String id){
		PaymentResponse res = paymentService.getStatusByIntentId(id);
		if(res == null) {
			return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
}
