package com.javatpoint.kafka.producer;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AutoCreateConfig_NA {
	
	@Value("${spring.kafka.topic}")
	private String topic;
	
	//@Bean
	public RestTemplate getTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public NewTopic libraryEvents() {
		
		return TopicBuilder.name(topic).replicas(2).partitions(4).build();
		
	}

}
