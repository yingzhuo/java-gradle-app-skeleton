/*
 * Copyright (c) 2024-2025 Unknown Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.websecurity.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtCreatorImpl implements JwtCreator {

	private final Algorithm algorithm;

	@Override
	public String create(JwtData data) {
		return JWT.create()
			.withHeader(data.getHeaderMap())
			.withPayload(data.getPayloadMap())
			.sign(algorithm);
	}

}
