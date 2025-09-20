/*
 * Copyright (c) 2024-2025 Unknown Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.websecurity.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserDetailsServiceUtils {

	@Nullable
	public static UserDetails loadUser(UserDetailsService service, PasswordEncoder passwordEncoder, String username, String password) {
		Assert.notNull(service, "manager must not be null");
		Assert.hasText(username, "username must not be blank");
		Assert.hasText(password, "password must not be blank");
		Assert.notNull(passwordEncoder, "passwordEncoder must not be null");

		try {
			var user = service.loadUserByUsername(username);

			if (!user.isEnabled()) {
				log.debug("用户已被禁止使用");
				return null;
			}

			if (!user.isAccountNonExpired()) {
				log.debug("用户已过期");
				return null;
			}

			if (!user.isAccountNonLocked()) {
				log.debug("用户已被锁定");
				return null;
			}

			if (!user.isCredentialsNonExpired()) {
				log.debug("用户秘钥或密码已被禁用");
				return null;
			}

			if (passwordEncoder.matches(password, user.getPassword())) {
				return user;
			} else {
				log.debug("{}", passwordEncoder.getClass().getSimpleName());
				log.debug("用户与口令不匹配 username: {} | password: {} | encodedPassword: {}", username, password, user.getPassword());
				return null;
			}
		} catch (UsernameNotFoundException e) {
			log.debug("用户名没有找到: {}", username);
			return null;
		}
	}

}
