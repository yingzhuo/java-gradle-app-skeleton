/*
 * Copyright (c) 2024-2025 Unknown Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.websecurity.util;

import io.github.yingzhuo.showcase.utility.UUIDUtils;
import io.github.yingzhuo.showcase.websecurity.jwt.JwtCreator;
import io.github.yingzhuo.showcase.websecurity.jwt.JwtData;
import io.github.yingzhuo.showcase.websecurity.user.UserPrincipal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.time.Duration;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtCreatorUtils {

	private static final Duration EXPIRATION_TIME = Duration.ofDays(365L);

	public static String createToken(JwtCreator creator, UserPrincipal userPrincipal) {
		Assert.notNull(creator, "creator must not be null");
		Assert.notNull(userPrincipal, "userDetailsExt must not be null");

		var jwtData = JwtData.newInstance()
			.addHeaderKeyId(UUIDUtils.randomUUID32())
			.addPayload("userId", userPrincipal.getId())
			.addPayload("username", userPrincipal.getUsername())
			.addPayload("authorities",
				userPrincipal.getAuthorities()
					.stream()
					.map(Object::toString)
					.toList()
			)
			.addPayloadIssuer("io.github.yingzhuo.showcase")
			.addPayloadExpiresAtFuture(EXPIRATION_TIME)
			.addPayloadIssuedAtNow();
		return creator.create(jwtData);
	}

}
