/*
 * Copyright (c) 2024-2025 ABC Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.websecurity.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class LoggingFilter extends OncePerRequestFilter {

	private static final String BAR = "-".repeat(80);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		log.debug("-".repeat(80));
		log.debug("[request path]: '{}'", request.getRequestURI());
		log.debug("[request method]: {}]", request.getMethod());
		log.debug("-".repeat(80));
		filterChain.doFilter(request, response);
	}

}
