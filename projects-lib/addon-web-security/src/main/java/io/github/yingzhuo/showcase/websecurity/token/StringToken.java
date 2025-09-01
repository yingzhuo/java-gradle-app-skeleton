/*
 * Copyright (c) 2024-2025 Unknown Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.websecurity.token;

import org.springframework.util.Assert;

import java.util.Objects;

public record StringToken(String value) implements Token {

	public StringToken {
		Assert.notNull(value, "value must not be null");
	}

	@Override
	public String asString() {
		return value;
	}

	@Override
	public String toString() {
		return value;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		StringToken that = (StringToken) o;
		return Objects.equals(value, that.value);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(value);
	}

}
