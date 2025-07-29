/*
 * Copyright (c) 2024-2025 ABC Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.core;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ApplicationBoot {

	public static void main(String[] args) {
		System.setProperty("spring.devtools.restart.enabled", "true");
		new SpringApplicationBuilder(ApplicationBoot.class)
			.run(args);
	}

}
