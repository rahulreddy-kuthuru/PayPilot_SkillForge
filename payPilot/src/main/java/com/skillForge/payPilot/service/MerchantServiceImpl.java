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
import com.skillForge.payPilot.exception.InvalidKycTransitionException;
import com.skillForge.payPilot.exception.MerchantNotFoundException;

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
	    if (merchant == null) {
	        throw new MerchantNotFoundException("Merchant with ID " + id + " not found");
	    }
	    if (merchant.keycStatus() == KycStatus.VERIFIED && status == KycStatus.PENDING) {
	        throw new InvalidKycTransitionException("Cannot convert a verified merchant into PENDING status");
	    }
	    Merchant updatedMerchant = new Merchant(merchant.merchantId(), merchant.name(), merchant.email(), status);
	    repository.put(id, updatedMerchant);
	    return updatedMerchant;
	}

	@Override
	public Merchant createMerchant(String id, String name, String email, KycStatus status) {
	    var merchant = new Merchant(id, name, email, KycStatus.PENDING);
	    repository.put(merchant.merchantId(), merchant);
	    return merchant;
	}

	@Override
	public Merchant getMerchantById(String id) {
	    Merchant merchant = repository.get(id);
	    if (merchant == null) {
	        throw new MerchantNotFoundException("Merchant with ID " + id + " not found");
	    }
	    return merchant;
	}

	@Override
	public List<KycHistory> getKycHistory(String id) {
	    return historyLog.stream()
	            .filter(log -> log.merchantId().equals(id))
	            .sorted((a, b) -> b.timestamp().compareTo(a.timestamp()))
	            .collect(Collectors.toList());
	}

}
