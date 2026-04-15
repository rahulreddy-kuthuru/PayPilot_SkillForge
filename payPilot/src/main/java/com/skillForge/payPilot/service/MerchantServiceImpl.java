package com.skillForge.payPilot.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.skillForge.payPilot.aspect.AuditKycChange;
import com.skillForge.payPilot.dto.KycStatus;
import com.skillForge.payPilot.dto.Merchant;

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

}
