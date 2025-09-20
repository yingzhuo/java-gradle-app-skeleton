/*
 * Copyright (c) 2024-2025 Unknown Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.core;

import io.github.yingzhuo.showcase.websecurity.exceptionhandling.SecurityExceptionHanding;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@EnableMethodSecurity
@Configuration
public class ApplicationBootWebSecurity {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		var applicationContext = http.getSharedObject(ApplicationContext.class);
		var securityExceptionHanding = applicationContext.getBean(SecurityExceptionHanding.class);

		return http
			.securityMatcher("/**")
			.anonymous(Customizer.withDefaults())
			.cors(Customizer.withDefaults())
			.sessionManagement(c ->
				c.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			)
			.csrf(AbstractHttpConfigurer::disable)
			.httpBasic(Customizer.withDefaults())
			.jee(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.logout(AbstractHttpConfigurer::disable)
			.passwordManagement(AbstractHttpConfigurer::disable)
			.rememberMe(AbstractHttpConfigurer::disable)
			.requestCache(RequestCacheConfigurer::disable)
			.headers(Customizer.withDefaults())
			.cors(Customizer.withDefaults())
			.exceptionHandling(c ->
				c.authenticationEntryPoint(securityExceptionHanding)
					.accessDeniedHandler(securityExceptionHanding)
			)
			.authorizeHttpRequests(c ->
				c.requestMatchers("/error").permitAll()
					.requestMatchers(HttpMethod.GET, "/favicon.ico").permitAll()
					.requestMatchers(HttpMethod.GET, "/actuator", "/actuator/info", "/actuator/health", "/actuator/beans", "/actuator/env").permitAll()
					.requestMatchers("/actuator/shutdown").denyAll()
					.requestMatchers("/security/login").permitAll()
					.requestMatchers("/ping").permitAll()
					.anyRequest().hasRole("USER")
			)
			.build();
	}

}
