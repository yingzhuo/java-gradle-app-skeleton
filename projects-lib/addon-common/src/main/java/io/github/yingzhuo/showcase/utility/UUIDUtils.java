/*
 * Copyright (c) 2024-2025 Unknown Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UUIDUtils {

	public static String randomUUID32() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static String randomUUID36() {
		return UUID.randomUUID().toString();
	}

}
