/*
 * Copyright (c) 2024-2025 Unknown Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.websecurity.autoconfig;

import com.auth0.jwt.algorithms.Algorithm;
import io.github.yingzhuo.showcase.websecurity.filter.JwtAuthenticationFilter;
import io.github.yingzhuo.showcase.websecurity.jwt.VerificationCustomizer;
import io.github.yingzhuo.showcase.websecurity.token.TokenResolver;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.Optional;

@Slf4j
@NoArgsConstructor
public class WebSecurityDSL extends AbstractHttpConfigurer<WebSecurityDSL, HttpSecurity> {

	@Override
	public void configure(HttpSecurity http) {
		var applicationContext = http.getSharedObject(ApplicationContext.class);

		http.addFilterAfter(
			createJwtAuthFilter(applicationContext),
			BasicAuthenticationFilter.class
		);
	}

	private JwtAuthenticationFilter createJwtAuthFilter(ApplicationContext applicationContext) {
		var resolver = applicationContext.getBean(TokenResolver.class);
		var algorithm = applicationContext.getBean(Algorithm.class);

		var filter = new JwtAuthenticationFilter(resolver, algorithm);
		getVerificationCustomizer(applicationContext).ifPresent(filter::setVerificationCustomizer);
		return filter;
	}

	private Optional<VerificationCustomizer> getVerificationCustomizer(ApplicationContext applicationContext) {
		try {
			return Optional.of(applicationContext.getBean(VerificationCustomizer.class));
		} catch (NoUniqueBeanDefinitionException e) {
			throw e;
		} catch (NoSuchBeanDefinitionException e) {
			return Optional.empty();
		}
	}

}
