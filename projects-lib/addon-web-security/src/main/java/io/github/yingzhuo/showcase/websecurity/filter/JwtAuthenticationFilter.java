/*
 * Copyright (c) 2024-2025 Unknown Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.websecurity.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.github.yingzhuo.showcase.json.util.JsonUtils;
import io.github.yingzhuo.showcase.utility.Base64Utils;
import io.github.yingzhuo.showcase.websecurity.jwt.VerificationCustomizer;
import io.github.yingzhuo.showcase.websecurity.token.TokenResolver;
import io.github.yingzhuo.showcase.websecurity.user.UserPrincipal;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.servlet.handler.DispatcherServletWebRequest;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends AbstractAuthenticationFilter {

	private final TokenResolver tokenResolver;
	private final Algorithm jwtAlgorithm;

	@Setter
	private VerificationCustomizer verificationCustomizer = verification -> verification;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

		if (!authenticationIsRequired()) {
			log.trace("请求已被认证过了，跳过本过滤器逻辑");
			chain.doFilter(request, response);
			return;
		} else {
			log.trace("请求暂时未被认证");
		}

		try {
			var rawTokenOptional = tokenResolver.resolve(new DispatcherServletWebRequest(request, response));
			var rawToken = rawTokenOptional.orElse(null);

			if (rawToken != null) {
				log.trace("解析出令牌: {}", rawToken);
				var userPrincipal = parseUserInfo(rawToken.asString());
				if (userPrincipal != null) {
					var userDetailsExt = new UserPrincipal(
						userPrincipal.getUserId(),
						userPrincipal.getUsername(),
						"[PROJECTED]",
						userPrincipal.getAuthorities()
					);
					var at = new AuthenticationToken(userDetailsExt);
					at.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					setAuthentication(at);
				}
			} else {
				log.trace("未解析出令牌");
			}
		} catch (AuthenticationException e) {
			clearAuthentication();
		}

		chain.doFilter(request, response);
	}

	@Nullable
	private JwtPayloadPojo parseUserInfo(String token) {
		try {
			var verifier = verificationCustomizer.apply(JWT.require(jwtAlgorithm))
				.build();

			var decodedToken = verifier.verify(token);
			var payloadRawString = decodedToken.getPayload();
			var payloadJsonBytes = Base64Utils.decodeToString(payloadRawString);
			return JsonUtils.parseJson(payloadJsonBytes, JwtPayloadPojo.class);
		} catch (Exception e) {
			log.info("令牌不合法", e);
			return null;
		}
	}

	@Getter
	@Setter
	private static class JwtPayloadPojo implements Serializable {
		private Long userId;
		private String username;
		private List<String> authorities;
	}

}
