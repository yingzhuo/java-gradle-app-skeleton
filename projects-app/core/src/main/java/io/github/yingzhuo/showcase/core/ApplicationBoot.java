/*
 * Copyright (c) 2024-2025 ABC Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApplicationBoot {

	public static void main(String[] args) throws Exception {
		init();
		SpringApplication.run(ApplicationBoot.class, args);
	}

	private static void init() throws Exception {
		System.setProperty("spring.devtools.restart.enabled", "true");
	}

}
