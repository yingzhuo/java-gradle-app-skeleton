/*
 * Copyright (c) 2024-2025 Unknown Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.websecurity.filter;

import io.github.yingzhuo.showcase.websecurity.user.UserPrincipal;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

abstract class AbstractAuthenticationFilter extends OncePerRequestFilter {

	@Setter
	private SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();

	protected final boolean authenticationIsRequired() {
		var existingAuth = securityContextHolderStrategy.getContext().getAuthentication();
		if (existingAuth == null || !existingAuth.isAuthenticated()) {
			return true;
		}
		return (existingAuth instanceof AnonymousAuthenticationToken);
	}

	protected final void setAuthentication(Authentication authentication) {
		authentication.setAuthenticated(true);
		securityContextHolderStrategy.getContext().setAuthentication(authentication);
	}

	protected final void clearAuthentication() {
		securityContextHolderStrategy.clearContext();
	}

	// -----------------------------------------------------------------------------------------------------------------

	protected static class AuthenticationToken extends AbstractAuthenticationToken {

		private final UserPrincipal principal;

		public AuthenticationToken(UserPrincipal principal) {
			super(principal.getAuthorities());
			Assert.notNull(principal, "UserDetails must not be null");
			this.principal = principal;
			this.setAuthenticated(true);
		}

		@Override
		public Object getCredentials() {
			return "[PROTECTED]";
		}

		@Override
		public Object getPrincipal() {
			return this.principal;
		}
	}

}
