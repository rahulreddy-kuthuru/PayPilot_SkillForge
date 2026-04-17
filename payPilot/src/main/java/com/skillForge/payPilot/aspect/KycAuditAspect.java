package com.skillForge.payPilot.aspect;

import java.time.LocalDateTime;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skillForge.payPilot.dto.KycHistory;
import com.skillForge.payPilot.dto.KycStatus;
import com.skillForge.payPilot.dto.Merchant;
import com.skillForge.payPilot.service.MerchantService;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class KycAuditAspect {
	
	@Autowired
	private MerchantService merchantService;

	@Around("@annotation(com.skillForge.payPilot.aspect.AuditKycChange) && args(id, newStatus)")
	public Object logChange(ProceedingJoinPoint jointPoint, String id, KycStatus newStatus) throws Throwable{
		KycStatus oldStatus = merchantService.getMerchantById(id).keycStatus();
		Object result = jointPoint.proceed();
		merchantService.addLog(new KycHistory(id, oldStatus, newStatus, "ADMIN", LocalDateTime.now()));
		Object[] args = jointPoint.getArgs();
		log.info("KYC update initiated for Merchant id {}.", args[0]);
		return result;
	}
}
