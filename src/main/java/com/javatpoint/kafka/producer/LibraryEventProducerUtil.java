package com.javatpoint.kafka.producer;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javatpoint.model.LibraryEvent;

@Component
//@ConfigurationProperties(prefix = "app.kafka.topic")
public class LibraryEventProducerUtil {

	private KafkaTemplate<Integer, String> kafkaTemplate;
	private ObjectMapper objectMapper;

	@Value("${app.kafka.topics.payment}")
	private String topic;

	public LibraryEventProducerUtil(KafkaTemplate<Integer, String> kafkaTemplate, ObjectMapper objectMapper) {
		this.kafkaTemplate = kafkaTemplate;
		this.objectMapper = objectMapper;
	}

	// Recommended approach
	public void sendLibraryEventAsyn(LibraryEvent libraryEvent) throws JsonProcessingException {
		Integer key = libraryEvent.getLibraryEventId();
		String libraryEventType = libraryEvent.getLibraryEventType();
		String value = objectMapper.writeValueAsString(libraryEvent);
		CompletableFuture<SendResult<Integer, String>> completableFuture = kafkaTemplate.send(topic,key, value);

		completableFuture.whenComplete((sendResult, throwable) -> {

			if (throwable == null)
				throw new RuntimeException("Error while send message to kafa");
			else {
				handleSuccess(key, value, sendResult);
			}

		});

	}

	// Block and wait untill message sent to kafka
	public void sendLibraryEventSynchronous(LibraryEvent libraryEvent)
			throws JsonProcessingException, InterruptedException, ExecutionException, TimeoutException {
		Integer key = libraryEvent.getLibraryEventId();
		String libraryEventType = libraryEvent.getLibraryEventType();
		String value = objectMapper.writeValueAsString(libraryEvent);
		SendResult sendResult = kafkaTemplate.send(topic, key, value).get(3, TimeUnit.SECONDS);

	}
	
	public void sendLibraryEventAsRecordApproach3(LibraryEvent libraryEvent) throws JsonProcessingException {
		Integer key=libraryEvent.getLibraryEventId();
		String libraryEventType=libraryEvent.getLibraryEventType();
		String value=objectMapper.writeValueAsString(libraryEvent);	

		List<Header> recordHeadr=List.of(new RecordHeader("event-source","scanner".getBytes()));
		ProducerRecord<Integer,String> producerRecord=new ProducerRecord<Integer, String>(topic,null,key, value,recordHeadr);
		CompletableFuture<SendResult<Integer, String>> completableFuture=kafkaTemplate.send(producerRecord);
		
		completableFuture.whenComplete((sendResult,throwable)->{
			
			  if (throwable ==null)
				  throw new RuntimeException("Error while send message to kafa");
			  else
			  {
				  handleSuccess(key,value,sendResult);
			  }
			
		});
	
		
		
	}

	private void handleSuccess(Integer key, String value, SendResult<Integer, String> sendResult) {

		System.out.println(key + ":" + value + "Send Successfully" + sendResult.toString());

	}
}
