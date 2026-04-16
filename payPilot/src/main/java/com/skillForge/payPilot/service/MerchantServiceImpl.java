package com.skillForge.payPilot.service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.skillForge.payPilot.aspect.AuditKycChange;
import com.skillForge.payPilot.dto.KycStatus;
import com.skillForge.payPilot.dto.Merchant;

@Service
public class MerchantServiceImpl implements MerchantService{
	
	private final Map<String, Merchant> repository = new ConcurrentHashMap<>();

	@Override
	@AuditKycChange
	public Merchant updateKycStatus(String id, KycStatus status) {
		Merchant merchant = repository.get(id);
		if(merchant == null) {
			throw new RuntimeException("Merchant not found");
		}
		if(merchant.keycStatus() == KycStatus.VERIFIED && status == KycStatus.PENDING) {
			throw new IllegalStateException("Can not convert a verified merchant into PENDING status");
		}
		Merchant updateMerchant = new Merchant(merchant.id(), merchant.name(),merchant.email(), status);
		repository.put(id, updateMerchant);
		return updateMerchant;
	}

	@Override
	public Merchant createMerchant(String id, String name, String email, KycStatus status) {
		var merchant = new Merchant(UUID.randomUUID().toString(), name, email, KycStatus.PENDING);
		repository.put(merchant.id(), merchant);
		return merchant;
	}

	@Override
	public Merchant getMerchantById(String id) {
		return Optional.ofNullable(repository.get(id)).orElse(null);
	}

}
