package com.javatpoint.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//@ControllerAdvice
public class ExceptionHandlerController {
	
	//@ExceptionHandler
	public String getData() {
		
		return "Controller Advice" ;
	}

}
