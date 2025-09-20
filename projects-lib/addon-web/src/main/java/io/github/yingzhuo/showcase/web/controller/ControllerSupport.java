/*
 * Copyright (c) 2024-2025 Unknown Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.web.controller;

import io.github.yingzhuo.showcase.databinding.DataBindingException;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

public abstract class ControllerSupport {

	protected final void checkDataBinding(@Nullable BindingResult bindingResult) {
		checkDataBinding((Errors) bindingResult);
	}

	protected final void checkDataBinding(@Nullable Errors errors) {
		if (errors != null && errors.hasErrors()) {
			throw new DataBindingException(errors.getAllErrors());
		}
	}

}
