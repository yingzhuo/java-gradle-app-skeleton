/*
 * Copyright (c) 2024-2025 Unknown Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BytesUtils {

	/**
	 * 拼接多个字节数组
	 *
	 * @param bytesArray 字节数组的数组
	 * @return 拼接结果
	 */
	public static byte[] concat(@Nullable byte[]... bytesArray) {
		if (bytesArray == null || bytesArray.length == 0) {
			return new byte[0];
		}

		var count = Stream.of(bytesArray)
			.map(it -> it.length)
			.reduce(0, Integer::sum);

		var combined = new byte[count];
		var buffer = ByteBuffer.wrap(combined);
		Stream.of(bytesArray)
			.forEach(buffer::put);
		return buffer.array();
	}

	/**
	 * 拼接多个字节数组
	 *
	 * @param bytesCollection 字节数组的数组
	 * @return 拼接结果
	 */
	public static byte[] concat(@Nullable Collection<byte[]> bytesCollection) {
		if (bytesCollection == null || bytesCollection.isEmpty()) {
			return new byte[0];
		}

		var count = Stream.of(bytesCollection)
			.map(Collection::size)
			.reduce(0, Integer::sum);

		var combined = new byte[count];
		var buffer = ByteBuffer.wrap(combined);
		bytesCollection.forEach(buffer::put);
		return buffer.array();
	}

}
