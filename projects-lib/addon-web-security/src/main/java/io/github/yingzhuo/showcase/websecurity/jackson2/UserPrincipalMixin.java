/*
 * Copyright (c) 2024-2025 Unknown Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.websecurity.jackson2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @see io.github.yingzhuo.showcase.websecurity.user.UserPrincipal
 */
@JsonIgnoreProperties({
	"password",
	"enabled",
	"accountNonExpired",
	"accountNonLocked",
	"credentialsNonExpired"
})
public abstract class UserPrincipalMixin {
}
