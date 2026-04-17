package com.skillForge.payPilot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.skillForge.payPilot.aspect.AuditKycChange;
import com.skillForge.payPilot.dto.KycHistory;
import com.skillForge.payPilot.dto.KycStatus;
import com.skillForge.payPilot.dto.Merchant;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MerchantServiceImpl implements MerchantService{
	
	private final Map<String, Merchant> repository = new ConcurrentHashMap<>();
	private final List<KycHistory> historyLog = new ArrayList<>();
	
	@Override
	public void addLog(KycHistory history) {
		historyLog.add(history);
	}

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
		Merchant updateMerchant = new Merchant(merchant.merchantId(), merchant.name(),merchant.email(), status);
		repository.put(id, updateMerchant);
		return updateMerchant;
	}

	@Override
	public Merchant createMerchant(String id, String name, String email, KycStatus status) {
		try {			
			var merchant = new Merchant(id, name, email, KycStatus.PENDING);
			repository.put(merchant.merchantId(), merchant);
			return merchant;
		} catch(Exception ex) {
			log.error("Error occured : " + ex);
			return null;
		}
	}

	@Override
	public Merchant getMerchantById(String id) {
		return Optional.ofNullable(repository.get(id)).orElse(null);
	}

	@Override
	public List<KycHistory> getKycHistory(String id) {
	    return historyLog.stream()
	            .filter(log -> log.merchantId().equals(id))
	            .sorted((a, b) -> b.timestamp().compareTo(a.timestamp()))
	            .collect(Collectors.toList());
	}

}
