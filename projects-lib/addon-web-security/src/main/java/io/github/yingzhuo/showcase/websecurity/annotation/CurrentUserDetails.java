/*
 * Copyright (c) 2024-2025 Unknown Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.websecurity.annotation;

import io.github.yingzhuo.showcase.websecurity.user.UserPrincipal;
import org.springframework.core.annotation.AliasFor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.*;

/**
 * @see org.springframework.security.core.userdetails.UserDetails
 * @see UserPrincipal
 */
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@AuthenticationPrincipal(expression = "#root")
public @interface CurrentUserDetails {

	@AliasFor(annotation = AuthenticationPrincipal.class, attribute = "errorOnInvalidType")
	public boolean errorOnInvalidType() default true;

}
