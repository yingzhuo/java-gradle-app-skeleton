/*
 * Copyright (c) 2024-2025 Unknown Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.spring;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.Objects;

@SuppressWarnings("unchecked")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SpringUtils {

	private static @Nullable ApplicationContext applicationContext;
	private static @Nullable Environment environment;
	private static @Nullable SpringApplication application;

	public static <T> T getBean(Class<T> clazz) {
		Assert.notNull(clazz, "clazz must not be null");
		return Objects.requireNonNull(applicationContext).getBean(clazz);
	}

	public static <T> T getBean(String beanName, Class<T> clazz) {
		Assert.notNull(beanName, "beanName must not be null");
		Assert.notNull(clazz, "clazz must not be null");
		return Objects.requireNonNull(applicationContext).getBean(beanName, clazz);
	}

	public static <T extends Environment> T getEnvironment() {
		Assert.notNull(environment, "environment must not be null");
		return (T) SpringUtils.environment;
	}

	public static SpringApplication getSpringApplication() {
		Assert.notNull(application, "application must not be null");
		return SpringUtils.application;
	}

	// -----------------------------------------------------------------------------------------------------------------

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	private static class Hook implements ApplicationListener<ApplicationReadyEvent>, EnvironmentPostProcessor {

		@Override
		public void onApplicationEvent(ApplicationReadyEvent event) {
			SpringUtils.applicationContext = event.getApplicationContext();
		}

		@Override
		public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
			SpringUtils.environment = environment;
			SpringUtils.application = application;
		}
	}

}
