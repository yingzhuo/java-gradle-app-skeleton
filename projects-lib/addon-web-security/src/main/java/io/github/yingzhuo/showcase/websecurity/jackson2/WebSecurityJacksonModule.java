/*
 * Copyright (c) 2024-2025 Unknown Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.websecurity.jackson2;

import com.fasterxml.jackson.databind.module.SimpleModule;
import io.github.yingzhuo.showcase.websecurity.user.UserPrincipal;

public class WebSecurityJacksonModule extends SimpleModule {

	@Override
	public void setupModule(SetupContext context) {
		context.setMixInAnnotations(UserPrincipal.class, UserPrincipalMixin.class);
	}

}
