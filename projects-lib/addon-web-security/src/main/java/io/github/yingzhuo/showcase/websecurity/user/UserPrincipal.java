/*
 * Copyright (c) 2024-2025 Unknown Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.websecurity.user;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

public class UserPrincipal implements UserDetails, Serializable {

	@Getter
	private final Long id;

	@Getter
	private final String username;

	@Getter
	private final String password;

	@Getter
	private final Collection<? extends GrantedAuthority> authorities;

	public UserPrincipal(Long id, String username, String password, String authoritiesString) {
		this(id, username, password, Arrays.asList(authoritiesString.split(",")));
	}

	public UserPrincipal(Long id, String username, String password, Collection<String> authorities) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.authorities = authorities.stream()
			.filter(StringUtils::hasText)
			.map(String::trim)
			.distinct()
			.map(SimpleGrantedAuthority::new)
			.toList();
	}

}
