/*
 * Copyright (c) 2024-2025 Unknown Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.util.Assert;

import java.time.Duration;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SleepUtils {

	@SneakyThrows
	public static void sleep(Duration duration) {
		Assert.notNull(duration, "duration must not be null");
		Thread.sleep(duration.toMillis());
	}

	@SneakyThrows
	public static void sleepInMillis(long millis) {
		if (millis > 0) {
			Thread.sleep(millis);
		}
	}

}
