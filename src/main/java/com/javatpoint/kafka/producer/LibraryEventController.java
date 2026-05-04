package com.javatpoint.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.javatpoint.model.LibraryEvent;


@RestController
public class LibraryEventController {
	
	@Autowired
	private LibraryEventProducerUtil libraryEventProducer;

	@PostMapping("v1/libraryevent")
public ResponseEntity<LibraryEvent> postLibraryEvent(@RequestBody LibraryEvent libraryEvent) throws JsonProcessingException{
		
		System.out.println("Library Event:"+libraryEvent+":");
		libraryEventProducer.sendLibraryEventAsyn(libraryEvent);
		return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
	
}

}
