/*
 * Copyright (c) 2024-2025 ABC Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.core;

import io.github.yingzhuo.showcase.core.mvc.LoggingFilter;
import org.springframework.boot.web.servlet.FilterRegistration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationBootMvc implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**");
	}

	@Bean
	@FilterRegistration(name = "loggingFilter", urlPatterns = "/*", order = Ordered.HIGHEST_PRECEDENCE)
	public LoggingFilter loggingFilter() {
		return new LoggingFilter();
	}

}
