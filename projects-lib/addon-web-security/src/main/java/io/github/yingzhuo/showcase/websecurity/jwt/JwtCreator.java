/*
 * Copyright (c) 2024-2025 ABC Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.websecurity.jwt;

@FunctionalInterface
public interface JwtCreator {

	public String create(JwtData data);

}
