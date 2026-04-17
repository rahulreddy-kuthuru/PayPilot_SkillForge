package com.skillForge.payPilot.controller;

import java.util.List;
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

import com.skillForge.payPilot.dto.KycHistory;
import com.skillForge.payPilot.dto.KycStatus;
import com.skillForge.payPilot.dto.Merchant;
import com.skillForge.payPilot.service.MerchantService;

@RestController
@RequestMapping("/api/v1/merchants")
public class MerchantController {
	
	@Autowired
	private MerchantService merchantService;
	
	@PostMapping
	public ResponseEntity<Merchant> createMerchant(@Valid @RequestBody Merchant merchantReq) {
	    Merchant merchant = merchantService.createMerchant(
	        merchantReq.merchantId(),
	        merchantReq.name(),
	        merchantReq.email(),
	        KycStatus.PENDING
	    );
	    return ResponseEntity.status(HttpStatus.CREATED).body(merchant);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Merchant> getMerchantById(@PathVariable String id) {
	    Merchant merchant = merchantService.getMerchantById(id);
	    return ResponseEntity.ok(merchant);
	}

	@PatchMapping("{id}/kyc")
	public ResponseEntity<Merchant> updateKycStatus(@PathVariable String id, @RequestParam KycStatus status) {
	    Merchant updatedMerchant = merchantService.updateKycStatus(id, status);
	    return ResponseEntity.ok(updatedMerchant);
	}

	@GetMapping("{id}/kyc/history")
	public ResponseEntity<List<KycHistory>> getKycHistory(@PathVariable String id) {
	    List<KycHistory> history = merchantService.getKycHistory(id);
	    if (history.isEmpty()) {
	        return ResponseEntity.noContent().build();
	    }
	    return ResponseEntity.ok(history);
	}

	
}
