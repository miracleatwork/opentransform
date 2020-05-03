package com.miracle.labs.transform.generated.tools;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;



public class JsonConvertor 
{
	private static Jsonb json = JsonbBuilder.create();
	
	public static JsonObject getJsonObject(String jsonString)
	{
		JsonReader jsonReader = Json.createReader(new StringReader(jsonString));
	    JsonObject reply = jsonReader.readObject();
	    return reply;
	}
	public static <T> String getJsonString(T t)
	{
		System.out.println("Getting json for "+ t);
		
		return json.toJson(t);
	}
	
	public static <T> T getClassFromJsonString(String str, Class<T> className)
	{
		T t=null;
		try
		{
			t = json.fromJson(str, className);
		}
		catch(Exception e)
		{
			System.out.println("Error while converting "+ str + " to "+ className);
			throw e;
		}
		return t; 
	}
}
