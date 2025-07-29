/*
 * Copyright (c) 2024-2025 ABC Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.websecurity.autoconfig;

import com.auth0.jwt.algorithms.Algorithm;
import io.github.yingzhuo.showcase.websecurity.jwt.GenericAlgorithm;
import io.github.yingzhuo.showcase.websecurity.jwt.JwtCreator;
import io.github.yingzhuo.showcase.websecurity.jwt.JwtCreatorImpl;
import io.github.yingzhuo.showcase.websecurity.jwt.predefined.JwtEcdsaPemContent;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.HashMap;

public class WebSecurityBeanAutoConfig {

	@Bean
	public GenericAlgorithm genericAlgorithm(@JwtEcdsaPemContent String pemContent) {
		return new GenericAlgorithm(pemContent, "123456");
	}

	@Bean
	public JwtCreator jwtCreator(Algorithm algorithm) {
		return new JwtCreatorImpl(algorithm);
	}

	@Bean
	@ConditionalOnMissingBean(PasswordEncoder.class)
	@SuppressWarnings("deprecation")
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

}
