/*
 * Copyright (c) 2024-2025 ABC Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.websecurity.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RequiredArgsConstructor
public class LoggingFilter extends OncePerRequestFilter implements Filter {

	@Override
	@SneakyThrows
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
		log.debug("-".repeat(80));
		log.debug("[request timestamp]: '{}']", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
		log.debug("[request path]: '{}'", request.getRequestURI());
		log.debug("[request method]: '{}']", request.getMethod());
		log.debug("-".repeat(80));
		filterChain.doFilter(request, response);
	}

}
