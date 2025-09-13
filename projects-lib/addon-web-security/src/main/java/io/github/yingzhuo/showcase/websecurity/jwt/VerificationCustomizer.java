/*
 * Copyright (c) 2024-2025 Unknown Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.websecurity.jwt;

import com.auth0.jwt.interfaces.Verification;

import java.util.function.Function;

@FunctionalInterface
public interface VerificationCustomizer extends Function<Verification, Verification> {
}
