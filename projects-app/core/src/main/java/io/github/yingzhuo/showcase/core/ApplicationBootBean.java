/*
 * Copyright (c) 2024-2025 ABC Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;

@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableAsync
@ImportResource("classpath:META-INF/spring/beans.xml")
public class ApplicationBootBean {
}
