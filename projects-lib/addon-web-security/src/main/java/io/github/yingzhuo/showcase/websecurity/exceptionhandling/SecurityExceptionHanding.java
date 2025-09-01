/*
 * Copyright (c) 2024-2025 Unknown Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.websecurity.exceptionhandling;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.yingzhuo.showcase.web.api.ApiResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.security.web.firewall.RequestRejectedHandler;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@SuppressWarnings("LoggingSimilarMessage")
public class SecurityExceptionHanding implements AuthenticationEntryPoint, AccessDeniedHandler, RequestRejectedHandler {

	private final ObjectMapper objectMapper;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
		log.debug("401 认证错误");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		writeJsonBody(
			response,
			ApiResult.builder()
				.code("401")
				.errorMessage("认证错误")
				.build()
		);
	}

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
		log.debug("403 鉴权错误");
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		writeJsonBody(
			response,
			ApiResult.builder()
				.code("403")
				.errorMessage("鉴权错误")
				.build()
		);
	}

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, RequestRejectedException requestRejectedException) throws IOException {
		log.debug("403 鉴权错误");
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		writeJsonBody(
			response,
			ApiResult.builder()
				.code("403")
				.errorMessage("鉴权错误")
				.build()
		);
	}

	private void writeJsonBody(HttpServletResponse response, Object object) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getOutputStream().write(objectMapper.writeValueAsBytes(object));
		response.getOutputStream().flush();
	}

}
