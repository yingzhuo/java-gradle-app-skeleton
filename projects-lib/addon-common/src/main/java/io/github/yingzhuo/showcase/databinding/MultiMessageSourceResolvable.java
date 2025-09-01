/*
 * Copyright (c) 2024-2025 Unknown Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.databinding;

import org.springframework.context.MessageSourceResolvable;

import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@FunctionalInterface
public interface MultiMessageSourceResolvable extends Iterable<MessageSourceResolvable> {

	@Override
	public Iterator<MessageSourceResolvable> iterator();

	public default Stream<MessageSourceResolvable> stream() {
		return stream(false);
	}

	public default Stream<MessageSourceResolvable> stream(boolean parallel) {
		return StreamSupport.stream(spliterator(), parallel);
	}

}
