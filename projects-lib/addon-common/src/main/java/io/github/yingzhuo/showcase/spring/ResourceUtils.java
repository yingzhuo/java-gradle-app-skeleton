/*
 * Copyright (c) 2024-2025 Unknown Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.spring;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.boot.io.ApplicationResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.util.Objects;
import java.util.Properties;

import static java.nio.charset.StandardCharsets.UTF_8;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ResourceUtils {

	public static final ResourceLoader RESOURCE_LOADER = ApplicationResourceLoader.get(ClassUtils.getDefaultClassLoader());

	public static ResourceLoader getResourceLoader() {
		return RESOURCE_LOADER;
	}

	@SuppressWarnings("unchecked")
	public static <T extends Resource> T loadResource(String location) {
		Assert.notNull(location, "resource location must not be null");
		return (T) RESOURCE_LOADER.getResource(location);
	}

	public static InputStream loadResourceAsStream(String location) {
		try {
			return loadResource(location).getInputStream();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static String loadResourceAsText(String location) {
		return loadResourceAsText(location, UTF_8);
	}

	public static String loadResourceAsText(String location, @Nullable Charset charset) {
		Assert.notNull(location, "resource location must not be null");
		charset = Objects.requireNonNullElse(charset, UTF_8);
		try {
			return loadResource(location).getContentAsString(charset);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static byte[] loadResourceAsBytes(String location) {
		Assert.notNull(location, "resource location must not be null");
		try {
			return loadResource(location).getContentAsByteArray();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static Properties loadResourceAsProperties(String location, boolean xmlFormat) {
		Assert.notNull(location, "resource location must not be null");
		try (var inputStream = loadResourceAsStream(location)) {
			var properties = new Properties();
			if (xmlFormat) {
				properties.loadFromXML(inputStream);
			} else {
				properties.load(inputStream);
			}
			return properties;
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

}
