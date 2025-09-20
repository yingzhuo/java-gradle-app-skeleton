/*
 * Copyright (c) 2024-2025 Unknown Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.websecurity.token;

import org.springframework.core.Ordered;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

@FunctionalInterface
public interface TokenResolver extends Ordered {

	public Optional<Token> resolve(NativeWebRequest webRequest);

	@Override
	public default int getOrder() {
		return 0;
	}

}
