/*
 * Copyright (c) 2024-2025 Unknown Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.core.controller;

import io.github.yingzhuo.showcase.web.api.ApiResult;
import io.github.yingzhuo.showcase.web.controller.ControllerSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class PingController extends ControllerSupport {

	@GetMapping("/ping")
	public Object ping() {
		return ApiResult.builder()
			.dataEntry("response", "pong")
			.build();
	}

}
