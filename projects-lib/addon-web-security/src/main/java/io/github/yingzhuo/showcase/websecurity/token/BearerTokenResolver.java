/*
 * Copyright (c) 2024-2025 Unknown Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.websecurity.token;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public final class BearerTokenResolver extends HeaderTokenResolver {

	public BearerTokenResolver() {
		super(AUTHORIZATION, "Bearer ");
	}

}
