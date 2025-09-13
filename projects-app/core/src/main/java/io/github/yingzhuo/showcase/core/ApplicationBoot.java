/*
 * Copyright (c) 2024-2025 Unknown Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 */
@SpringBootApplication
public class ApplicationBoot {

	/**
	 * 启动方法
	 *
	 * @param args 启动参数
	 */
	public static void main(String[] args) {
		System.setProperty("spring.devtools.restart.enabled", "true");
		SpringApplication.run(ApplicationBoot.class, args);
	}

}
