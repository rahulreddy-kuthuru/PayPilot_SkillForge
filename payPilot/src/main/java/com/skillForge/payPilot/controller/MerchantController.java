package com.skillForge.payPilot.controller;

import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skillForge.payPilot.dto.KycStatus;
import com.skillForge.payPilot.dto.Merchant;
import com.skillForge.payPilot.service.MerchantService;

@RestController
@RequestMapping("/api/v1/merchants")
public class MerchantController {
	
	@Autowired
	private MerchantService merchantService;
	
	@PostMapping
	public ResponseEntity<Merchant> createMerchant(@Valid @RequestBody Merchant merchantReq){
		var merchant = merchantService.createMerchant(UUID.randomUUID().toString(), merchantReq.name(), merchantReq.email(), KycStatus.PENDING);
		return ResponseEntity.status(HttpStatus.CREATED).body(merchant);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Merchant> getMerchantById(@PathVariable String id){
		return Optional.ofNullable(merchantService.getMerchantById(id)).map(ResponseEntity :: ok).orElse(ResponseEntity.notFound().build());
	}
	
	@PatchMapping("{id}/kyc")
	public ResponseEntity<Merchant> updateKycStatuc(@PathVariable String id, @RequestParam KycStatus status){
		try {
		Merchant updatedMerchant = merchantService.updateKycStatus(id, status);
		return ResponseEntity.ok(updatedMerchant);
		} catch(IllegalStateException ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
}
