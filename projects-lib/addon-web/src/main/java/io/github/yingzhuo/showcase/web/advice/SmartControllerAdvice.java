/*
 * Copyright (c) 2024-2025 Unknown Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.web.advice;

import io.github.yingzhuo.showcase.databinding.MultiMessageSourceResolvable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.PropertyAccessException;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.validation.BindingErrorProcessor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DefaultBindingErrorProcessor;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

@Slf4j
@ControllerAdvice
public class SmartControllerAdvice {

	@InitBinder
	public void initDataBinder(WebDataBinder binder) {
		binder.setBindingErrorProcessor(SmartBindingErrorProcessor.INSTANCE);
	}

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	private static class SmartBindingErrorProcessor extends DefaultBindingErrorProcessor implements BindingErrorProcessor {

		private static final SmartBindingErrorProcessor INSTANCE = new SmartBindingErrorProcessor();

		@Override
		public void processPropertyAccessException(PropertyAccessException ex, BindingResult bindingResult) {
			if (ex.getRootCause() instanceof MessageSourceResolvable rootResolvable) {
				bindingResult.addError(
					new ObjectError(
						bindingResult.getObjectName(),
						rootResolvable.getCodes(),
						rootResolvable.getArguments(),
						rootResolvable.getDefaultMessage()
					)
				);
				return;
			}

			if (ex.getRootCause() instanceof MultiMessageSourceResolvable multiMessageSourceResolvable) {
				for (var resolvable : multiMessageSourceResolvable) {
					if (resolvable != null) {
						bindingResult.addError(
							new ObjectError(
								bindingResult.getObjectName(),
								resolvable.getCodes(),
								resolvable.getArguments(),
								resolvable.getDefaultMessage()
							)
						);
					}
				}
				return;
			}

			super.processPropertyAccessException(ex, bindingResult);
		}

	}

}
