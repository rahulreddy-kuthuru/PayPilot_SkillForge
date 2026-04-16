package com.skillForge.payPilot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.skillForge.payPilot.interceptior.IdempotencyInterceptor;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer{

	@Autowired
	private IdempotencyInterceptor interceptor;
	
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(interceptor).addPathPatterns("/api/v1/payments/**");
	}
}
