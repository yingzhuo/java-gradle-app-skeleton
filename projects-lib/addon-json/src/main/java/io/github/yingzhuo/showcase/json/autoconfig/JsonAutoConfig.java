/*
 * Copyright (c) 2024-2025 Unknown Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.json.autoconfig;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import java.util.ServiceLoader;

@Slf4j
@RequiredArgsConstructor
public class JsonAutoConfig {

	@Autowired
	public void setupObjectMapper(ObjectMapper mapper) {
		ServiceLoader.load(Module.class)
			.stream()
			.filter(p -> p.type().getName().startsWith("io.github.yingzhuo.showcase"))
			.map(ServiceLoader.Provider::get)
			.forEach(mapper::registerModule);
	}

	@Bean
	@ConditionalOnMissingBean
	public Configuration jsonPathConfiguration(ObjectMapper mapper) {
		return Configuration.builder()
			.jsonProvider(new JacksonJsonProvider(mapper))
			.mappingProvider(new JacksonMappingProvider(mapper))
			.build();
	}

}
