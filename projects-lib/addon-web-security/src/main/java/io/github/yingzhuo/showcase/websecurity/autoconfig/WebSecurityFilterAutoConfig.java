/*
 * Copyright (c) 2024-2025 Unknown Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.websecurity.autoconfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.yingzhuo.showcase.websecurity.exceptionhandling.SecurityExceptionHanding;
import io.github.yingzhuo.showcase.websecurity.token.BearerTokenResolver;
import io.github.yingzhuo.showcase.websecurity.token.CompositeTokenResolver;
import io.github.yingzhuo.showcase.websecurity.token.HeaderTokenResolver;
import io.github.yingzhuo.showcase.websecurity.token.TokenResolver;
import io.github.yingzhuo.showcase.websecurity.user.MapUserDetailsService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

public class WebSecurityFilterAutoConfig {

	@Bean
	@ConditionalOnMissingBean
	public WebSecurityCustomizer webSecurityCustomizer(Environment environment) {
		return web ->
			web.debug(environment.matchesProfiles("!prod"));
	}

	@Bean
	@ConditionalOnMissingBean
	public TokenResolver jwtResolver() {
		return new CompositeTokenResolver(
			new BearerTokenResolver(),
			new HeaderTokenResolver("X-Auth-Token")
		);
	}

	@Bean
	@ConditionalOnMissingBean(UserDetailsService.class)
	public UserDetailsService userDetailsService() {
		return new MapUserDetailsService();
	}

	@Bean
	@ConditionalOnMissingBean(RoleHierarchy.class)
	public RoleHierarchy roleHierarchy() {
		return RoleHierarchyImpl.fromHierarchy("ROLE_ADMIN > ROLE_USER > ROLE_ANONYMOUS");
	}

	@Bean
	@ConditionalOnMissingBean(GrantedAuthoritiesMapper.class)
	public GrantedAuthoritiesMapper authorityMapper() {
		var bean = new SimpleAuthorityMapper();
		bean.setPrefix("ROLE_");
		bean.setConvertToUpperCase(true);
		bean.setConvertToLowerCase(false);
		bean.setDefaultAuthority("ROLE_USER");
		return bean;
	}

	@Bean
	@ConditionalOnMissingBean(HttpFirewall.class)
	public HttpFirewall httpFirewall() {
		var bean = new StrictHttpFirewall();
		bean.setAllowSemicolon(true);
		return bean;
	}

	@Bean
	public SecurityExceptionHanding securityExceptionHanding(ObjectMapper objectMapper) {
		return new SecurityExceptionHanding(objectMapper);
	}

}
