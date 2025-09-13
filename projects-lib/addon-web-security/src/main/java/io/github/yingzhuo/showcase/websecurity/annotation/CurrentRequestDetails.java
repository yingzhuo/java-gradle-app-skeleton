/*
 * Copyright (c) 2024-2025 Unknown Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.websecurity.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.security.core.annotation.CurrentSecurityContext;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@CurrentSecurityContext(expression = "#root.authentication.details")
public @interface CurrentRequestDetails {

	@AliasFor(annotation = CurrentSecurityContext.class, attribute = "errorOnInvalidType")
	public boolean errorOnInvalidType() default true;

}
