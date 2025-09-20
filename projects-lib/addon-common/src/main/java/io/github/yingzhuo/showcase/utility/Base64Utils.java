/*
 * Copyright (c) 2024-2025 Unknown Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Base64Utils {

	public static String encodeToString(String text) {
		Assert.hasText(text, "text must not be blank");
		return Base64.getUrlEncoder()
			.withoutPadding()
			.encodeToString(text.getBytes(UTF_8));
	}

	public static String decodeToString(String text) {
		Assert.hasText(text, "text must not be blank");
		return new String(Base64.getUrlDecoder().decode(text.getBytes(UTF_8)), UTF_8);
	}

	public static byte[] decodeBase64(String text) {
		Assert.hasText(text, "text must not be blank");
		return Base64.getUrlDecoder().decode(text.getBytes(UTF_8));
	}

}
