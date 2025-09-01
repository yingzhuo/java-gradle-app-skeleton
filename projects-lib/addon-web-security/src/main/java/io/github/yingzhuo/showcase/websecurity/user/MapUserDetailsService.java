/*
 * Copyright (c) 2024-2025 Unknown Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.websecurity.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Map;

public class MapUserDetailsService implements UserDetailsService {

	private final Map<String, UserPrincipal> cachedUsers;

	public MapUserDetailsService() {
		cachedUsers = Map.of(
			"admin", new UserPrincipal(0L, "admin", "{noop}admin", "ROLE_ADMIN"),
			"yingzhuo", new UserPrincipal(1L, "yingzhuo", "{noop}yingzhuo", "ROLE_USER"),
			"wenjingjing", new UserPrincipal(2L, "wenjingjing", "{noop}wenjingjing", "ROLE_USER")
		);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var user = cachedUsers.get(username);
		if (user == null) {
			throw new UsernameNotFoundException("'%s' not found".formatted(username));
		}
		return user;
	}

}
