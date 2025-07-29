/*
 * Copyright (c) 2024-2025 ABC Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.websecurity.jwt.predefined;

import org.springframework.beans.factory.annotation.Value;

import java.lang.annotation.*;

@Inherited
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Value("#{new org.springframework.core.io.ClassPathResource('META-INF/secret/jwt-ecdsa.pem').getContentAsString(T(java.nio.charset.StandardCharsets).UTF_8)}")
public @interface JwtEcdsaPemContent {
}
