/*
 * Copyright (c) 2024-2025 Unknown Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.websecurity.token;

import java.io.Serializable;

@FunctionalInterface
public interface Token extends Serializable {

	public static Token ofString(String token) {
		return new StringToken(token);
	}

	public String asString();

}
