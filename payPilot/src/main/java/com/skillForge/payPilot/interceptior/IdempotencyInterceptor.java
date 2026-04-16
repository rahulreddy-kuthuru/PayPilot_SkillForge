package com.skillForge.payPilot.interceptior;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class IdempotencyInterceptor implements HandlerInterceptor{
	
	private final Map<String, Object> requestCache = new ConcurrentHashMap<>();
	
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws IOException {
		String key = req.getHeader("X-Idempotency-Key");
		if(key != null && requestCache.containsKey(key)) {
			res.setStatus(HttpServletResponse.SC_OK);
			res.getWriter().write("{\"message\":\"Duplicate request detected\"}");
			res.setContentType("application/json");
			return false;
		}
		return true;
	}
	
	public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object handler, Exception ex) {
		String key = req.getHeader("X-Idempotency-Key");
		if(key !=null && res.getStatus() == 201) {
			requestCache.put(key, "PROCESSED");
		}
	}
}
