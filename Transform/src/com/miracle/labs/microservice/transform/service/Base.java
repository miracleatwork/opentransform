package com.miracle.labs.microservice.transform.service;

import java.io.File;

import com.miracle.labs.microservice.transform.model.Attributes;

/**
 * 1) It reads the monolith code
 * 2) It accepts a class name to transform. It is termed as dependee class
 * @author miracle
 *
 */

public class Base {

	
	protected Attributes attributes;
	
	
	public Base(Attributes attribute) {
		super();
		this.attributes = attribute;
	}

	protected void loadMonolithCode(){
		
	}
	
	protected void introduceProxy(){
		
	}
	
	protected void replaceDependee(){
		
	}
	
	protected void prepareNewMonilithCode(){
		
	}
	
	protected void prepareNewMicroserviceCode(){
		
	}
	
}
