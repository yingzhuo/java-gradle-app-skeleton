/*
 * Copyright (c) 2024-2025 Unknown Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.websecurity.token;

import org.springframework.lang.Nullable;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Objects;
import java.util.Optional;

public class HeaderTokenResolver implements TokenResolver {

	protected final String headerName;
	protected final String prefix;
	protected final int prefixLen;

	public HeaderTokenResolver(String headerName) {
		this(headerName, "");
	}

	public HeaderTokenResolver(String headerName, @Nullable String prefix) {
		prefix = Objects.requireNonNullElse(prefix, "");

		this.headerName = headerName;
		this.prefix = prefix;
		this.prefixLen = prefix.length();
	}

	@Override
	public Optional<Token> resolve(NativeWebRequest request) {
		String headerValue = request.getHeader(headerName);

		if (headerValue == null || !headerValue.startsWith(prefix)) {
			return Optional.empty();
		}

		headerValue = headerValue.substring(prefixLen);

		if (headerValue.isBlank()) {
			return Optional.empty();
		}

		return Optional.of(Token.ofString(headerValue));
	}

}
