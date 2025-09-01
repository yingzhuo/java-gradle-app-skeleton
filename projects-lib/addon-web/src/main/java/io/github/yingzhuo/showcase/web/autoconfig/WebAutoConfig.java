/*
 * Copyright (c) 2024-2025 Unknown Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.web.autoconfig;

import io.github.yingzhuo.showcase.web.advice.SmartControllerAdvice;
import io.github.yingzhuo.showcase.web.exceptionhandling.ExceptionHandlerAdvice;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;

@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class WebAutoConfig {

	@Bean
	public ExceptionHandlerAdvice exceptionHandlerAdvice() {
		return new ExceptionHandlerAdvice();
	}

	@Bean
	public SmartControllerAdvice smartControllerAdvice() {
		return new SmartControllerAdvice();
	}

}
