package com.miracle.labs.microservice.transform.generated;

import com.miracle.labs.transform.generated.tools.JsonConvertor;

public class Response
{
	public Object message;
	

	public Object getMessage() {
		return message;
	}

	public <T> Response setMessage(T message) {
		this.message = message;
		return this;
	}
	
	public String toJson()
	{
		return JsonConvertor.getJsonString(this);
	}
	
}