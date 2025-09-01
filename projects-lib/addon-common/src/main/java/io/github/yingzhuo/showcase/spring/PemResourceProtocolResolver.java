/*
 * Copyright (c) 2024-2025 Unknown Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.spring;

import org.springframework.core.io.ProtocolResolver;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

public class PemResourceProtocolResolver implements ProtocolResolver {

	private static final Pattern PATTERN = Pattern.compile("^pem:(.+?)(?:\\?keypass=(.*))?$");

	/**
	 * {@inheritDoc}
	 */
	@Nullable
	@Override
	public Resource resolve(String location, ResourceLoader resourceLoader) {
		var matcher = PATTERN.matcher(location);
		if (matcher.find()) {
			var resourceLocation = matcher.group(1);  // 必选
			var keypass = matcher.group(2); // 可选

			if (!StringUtils.hasText(resourceLocation)) {
				return null;
			}

			if (keypass == null || keypass.isEmpty()) {
				keypass = null;
			}

			var delegatingResource = resourceLoader.getResource(resourceLocation);
			return new PemResource(delegatingResource, keypass);
		}
		return null;
	}
}
