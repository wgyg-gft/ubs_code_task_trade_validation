package com.gft.codetest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gft.codetest.util.ISO4217Currency;

@Configuration
public class CurrencyConfig {

	@Bean
	public ISO4217Currency currencyList() {
		return new ISO4217Currency();
	}
}
