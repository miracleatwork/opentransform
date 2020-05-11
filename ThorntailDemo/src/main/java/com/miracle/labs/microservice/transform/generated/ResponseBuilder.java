package com.miracle.labs.microservice.transform.generated;

public class ResponseBuilder {

	
	public static String getDefaultResponseJsonString()
	{
		return new Response().setMessage("Request was Successfull").toJson();
	}
	
	public static <T> String getMessageResponseJsonString(T message)
	{
		return new Response().setMessage(message).toJson();
	}
	
	public static Response prepareResponse()
	{
		return new Response();
	}
	
	
}

