/*
 * Copyright (c) 2024-2025 Unknown Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.web.exceptionhandling;

import io.github.yingzhuo.showcase.web.api.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler implements ErrorController {

	// 404
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		var api = ApiResult.builder()
			.errorMessage("No such API")
			.code("404")
			.build();
		return new ResponseEntity<>(api, null, HttpStatus.NOT_FOUND.value());
	}

	// 404
	@Override
	protected ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		var api = ApiResult.builder()
			.errorMessage("No such API")
			.code("404")
			.build();
		return new ResponseEntity<>(api, null, HttpStatus.NOT_FOUND.value());
	}

}
