package com.javatpoint.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClientController {
	
	@Bean
	public RestTemplate getRestTemplate() {
		
		return new RestTemplate();
	}

}
