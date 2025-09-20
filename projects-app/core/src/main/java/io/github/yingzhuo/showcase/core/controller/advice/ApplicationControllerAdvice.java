/*
 * Copyright (c) 2024-2025 Unknown Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.core.controller.advice;

import io.github.yingzhuo.showcase.databinding.DataBindingException;
import io.github.yingzhuo.showcase.web.api.ApiResult;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice(basePackages = {"io.github.yingzhuo.showcase.core.controller"})
public class ApplicationControllerAdvice extends ResponseEntityExceptionHandler implements ErrorController {

	// 400
	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Object handleWrongRequestParametersException(DataBindingException ex) {
		var joinMsg = String.join(",", ex.getMessages(getRequiredMessageSource()));
		return ApiResult.builder()
			.code("400")
			.errorMessage(joinMsg)
			.build();
	}

	// -----------------------------------------------------------------------------------------------------------------

	@NonNull
	private MessageSource getRequiredMessageSource() {
		var messageSource = super.getMessageSource();
		if (messageSource == null) {
			throw new AssertionError();
		}
		return messageSource;
	}

}
