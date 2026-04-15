package com.skillForge.payPilot.service;

import com.skillForge.payPilot.dto.KycStatus;
import com.skillForge.payPilot.dto.Merchant;

public interface MerchantService {
	
	Merchant updateKycStatus(String id, KycStatus status);

}
