package com.skillForge.payPilot.interceptior;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class IdempotencyInterceptor implements HandlerInterceptor{
	
	private final Map<String, Object> requestCache = new ConcurrentHashMap<>();
	
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) {
		String key = req.getHeader("X-Idempotency-Key");
		if(key != null && requestCache.containsKey(key)) {
			return false;
		}
		return true;
	}
	
}
