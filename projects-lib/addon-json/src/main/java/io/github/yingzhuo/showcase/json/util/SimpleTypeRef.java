/*
 * Copyright (c) 2024-2025 Unknown Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.json.util;

import com.jayway.jsonpath.TypeRef;

import java.lang.reflect.Type;

public final class SimpleTypeRef<T> extends TypeRef<T> {

	private final Class<T> clz;

	public SimpleTypeRef(Class<T> clz) {
		this.clz = clz;
	}

	public static <T> TypeRef<T> of(Class<T> clazz) {
		return new SimpleTypeRef<>(clazz);
	}

	@Override
	public Type getType() {
		return this.clz;
	}

}
