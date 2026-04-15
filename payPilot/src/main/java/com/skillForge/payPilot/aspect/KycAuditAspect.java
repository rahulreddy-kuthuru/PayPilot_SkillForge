package com.skillForge.payPilot.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class KycAuditAspect {

	@Around("@annotation(com.skillForge.payPilot.aspect.AuditKycChange)")
	public Object logChange(ProceedingJoinPoint jointPoint) throws Throwable{
		Object[] args = jointPoint.getArgs();
		log.info("KYC update initiated for Merchant id {}.", args[0]);
		return jointPoint.proceed();
	}
}
