/*
 * Copyright (c) 2024-2025 Unknown Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.websecurity.autoconfig;

import com.auth0.jwt.algorithms.Algorithm;
import io.github.yingzhuo.showcase.websecurity.filter.LoggingFilter;
import io.github.yingzhuo.showcase.websecurity.jwt.GenericAlgorithm;
import io.github.yingzhuo.showcase.websecurity.jwt.JwtCreator;
import io.github.yingzhuo.showcase.websecurity.jwt.JwtCreatorImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.io.IOException;
import java.util.HashMap;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@SuppressWarnings("deprecation")
public class WebSecurityBeanAutoConfig {

	@Bean
	public GenericAlgorithm genericAlgorithm(ResourceLoader resourceLoader) throws IOException {
		var resource = resourceLoader.getResource("classpath:META-INF/secret/jwt-ecdsa.pem");
		return new GenericAlgorithm(resource.getContentAsString(UTF_8), "123456");
	}

	@Bean
	public JwtCreator jwtCreator(Algorithm algorithm) {
		return new JwtCreatorImpl(algorithm);
	}

	@Bean
	@ConditionalOnMissingBean(PasswordEncoder.class)
	public PasswordEncoder passwordEncoder() {
		String encodingId = "bcrypt";
		var encoders = new HashMap<String, PasswordEncoder>();
		encoders.put(encodingId, new BCryptPasswordEncoder());
		encoders.put("ldap", new LdapShaPasswordEncoder());
		encoders.put("MD4", new Md4PasswordEncoder());
		encoders.put("MD5", new MessageDigestPasswordEncoder("MD5"));
		encoders.put("noop", NoOpPasswordEncoder.getInstance());
		encoders.put("pbkdf2", Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8());
		encoders.put("scrypt", SCryptPasswordEncoder.defaultsForSpringSecurity_v5_8());
		encoders.put("SHA-1", new MessageDigestPasswordEncoder("SHA-1"));
		encoders.put("SHA-256", new MessageDigestPasswordEncoder("SHA-256"));
		encoders.put("sha256", new StandardPasswordEncoder());
		encoders.put("argon2", Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8());

		var encoder = new DelegatingPasswordEncoder(encodingId, encoders);
		encoder.setDefaultPasswordEncoderForMatches(encoders.get("MD5"));
		return encoder;
	}

	@Bean
	@FilterRegistration(name = "loggingFilter", urlPatterns = "/*", order = HIGHEST_PRECEDENCE)
	public LoggingFilter loggingFilter() {
		return new LoggingFilter();
	}

}
