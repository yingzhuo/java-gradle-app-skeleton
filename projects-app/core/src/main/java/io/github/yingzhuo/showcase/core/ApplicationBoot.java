/*
 * Copyright (c) 2024-2025 ABC Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class ApplicationBoot {

	public static void main(String[] args) {
		System.setProperty("spring.devtools.restart.enabled", "true");
		SpringApplication.run(ApplicationBoot.class, args);
	}

}
