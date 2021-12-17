package com.gft.codetest.config;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class JacksonConfig {
	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	public ObjectMapper objectMapper() {
		ObjectMapper om = new ObjectMapper();
		return om;
	}
}
