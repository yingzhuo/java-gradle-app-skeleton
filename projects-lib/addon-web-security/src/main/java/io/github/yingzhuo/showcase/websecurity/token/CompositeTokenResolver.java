/*
 * Copyright (c) 2024-2025 Unknown Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.websecurity.token;

import org.springframework.core.OrderComparator;
import org.springframework.lang.Nullable;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.*;

public final class CompositeTokenResolver implements TokenResolver {

	private final List<TokenResolver> resolvers = new ArrayList<>(2);

	public CompositeTokenResolver(@Nullable TokenResolver... resolvers) {
		if (resolvers == null || resolvers.length == 0) {
			return;
		}

		Arrays.stream(resolvers)
			.filter(Objects::nonNull)
			.sorted(OrderComparator.INSTANCE)
			.forEach(this.resolvers::add);
	}

	@Override
	public Optional<Token> resolve(NativeWebRequest request) {
		for (TokenResolver it : resolvers) {
			Optional<Token> op = doResolve(it, request);
			if (op.isPresent()) {
				return op;
			}
		}
		return Optional.empty();
	}

	private Optional<Token> doResolve(@Nullable TokenResolver resolver, NativeWebRequest request) {
		try {
			if (resolver != null) {
				return resolver.resolve(request);
			} else {
				return Optional.empty();
			}
		} catch (Throwable ignored) {
			return Optional.empty();
		}
	}

}
