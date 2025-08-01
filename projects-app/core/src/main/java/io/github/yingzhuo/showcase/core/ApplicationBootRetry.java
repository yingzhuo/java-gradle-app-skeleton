/*
 * Copyright (c) 2024-2025 ABC Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Slf4j
@RequiredArgsConstructor
@EnableRetry
@Configuration
public class ApplicationBootRetry {

	@Bean
	public RetryTemplate retryTemplate() {
		var backOffPolicy = new FixedBackOffPolicy();
		backOffPolicy.setBackOffPeriod(300L);

		var retryPolicy = new SimpleRetryPolicy();
		retryPolicy.setMaxAttempts(4);

		var bean = new RetryTemplate();
		bean.setBackOffPolicy(backOffPolicy);
		bean.setRetryPolicy(retryPolicy);
		return bean;
	}

}
