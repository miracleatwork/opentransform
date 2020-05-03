package com.miracle.labs.microservice.transform.service;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.json.bind.JsonbException;

import com.miracle.labs.microservice.transform.model.Attributes;

public class SimpleProxyConfiguration  {

protected Attributes attributes;
	
	private StringBuilder newClass = new StringBuilder(); 
	
	public SimpleProxyConfiguration(Attributes attribute) {
		super();
		this.attributes = attribute;
	}
	
		public void createProxyClass() throws IOException, ClassNotFoundException{
		
		inspect(this.attributes.getDependeeClassCode());
	}
	 
	public StringBuilder getGeneratedCode()
	{
		return newClass;
	}
	
	private void inspect(StringBuilder code) {
		newClass=code;
		ClassStructure newClass = new ClassStructure();
		Class cls = this.attributes.getDependeeClass();
		newClass.setClassName(cls.getSimpleName());
		newClass.setPackageName(cls.getPackage().getName());
		newClass.processPackageAndImports(code);
		newClass.processMethods(cls.getDeclaredMethods(),code);
		
		
		
	}


private class ClassStructure
{
	String packageName;
	String className;
	List<String> methods = new ArrayList<>();
	List<String> ignoredMethods ;
	
	
	ClassStructure()
	{
		ignoredMethods = new ArrayList();
		ignoredMethods.add("notify");
		ignoredMethods.add("notifyAll");
		ignoredMethods.add("wait");
		ignoredMethods.add("hashCode");
		ignoredMethods.add("toString");
		ignoredMethods.add("equals");
		ignoredMethods.add("getClass");
		
	}
	
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public List<String> getMethods() {
		return methods;
	}
	
	public void processPackageAndImports(StringBuilder code) {
		StringBuilder text = new StringBuilder();
		//TODO multiple appends
		//text.append("package "+ attributes.getGeneratedMonolithPackage()).append(";").append("\n")
		text.append("package "+ getPackageName()).append(";").append("\n")
		.append("import java.io.UnsupportedEncodingException;").append("\n")
		.append("import java.util.concurrent.ExecutionException;").append("\n")
		.append("import javax.json.bind.JsonbException;").append("\n")
		.append("import java.util.Collections;").append("\n")
		.append("import java.util.HashMap;").append("\n")
		.append("import java.lang.reflect.InvocationTargetException;").append("\n")
		.append("import java.util.Map;").append("\n")
		.append("import com.miracle.labs.transform.generated.tools.RESTClient;").append("\n");
		

		
		
		code.replace(code.indexOf("package"), code.indexOf(";"), text.toString());
		

	}	
	public void processMethods(Method[] methods, StringBuilder code) {
		Arrays.stream(methods)
		
		//TODO : fixed now - This will fail for inner / local scope within the method {{ }} 
		.forEach(x->{
			String methodName = x.getName();
			int nameIndex = code.indexOf(methodName);
			int start = code.indexOf("{", nameIndex+methodName.length())+1;
			
			int end = code.indexOf("}", start+1);
			
			String oldString = code.substring(start,end);
			int oldStart = 1;
			// check if there is a local scope
			while((oldStart=oldString.indexOf("{",oldStart))>0)
			{
				end = code.indexOf("}", end+1);
				oldString = code.substring(start,end);
				oldStart=oldStart+1;
				
			}
			//System.out.println("Old "+ oldString);
			
			code.replace(start, end, getToCallCode(x,code.substring(nameIndex, start),attributes.isGetMethod(methodName)));
			code.insert(start-1, "throws UnsupportedEncodingException, JsonbException, InterruptedException, ExecutionException, InstantiationException, IllegalAccessException, InvocationTargetException");
			System.out.println("completed Proxy method " + code);
		});
		
		System.out.println(" Code "+ code +" \n" );
		//TODO -- Create bridge for private methods.
	}
	
	

	private String getToCallCode(Method x,String params,boolean getMethod)
	{  
		   
		    StringBuilder response = new StringBuilder("\n");
		    if(!getMethod)
		    {
		    	response.append("Map <String,Object> map = new HashMap<>();").append("\n ");	
		    }
		    else
		    {
		    	response.append("Map<String,Object> map = Collections.EMPTY_MAP;").append("\n ");	
		    	
		    }
		    if(!x.getReturnType().equals(void.class))
		    {
		    	response.append("return").append(" ");	
		    }
		    
		    StringBuilder paramString = new StringBuilder();
			
		    if(x.getParameters().length>0)
		    {
		    	int start = params.indexOf("(");
			    int end = params.indexOf(")");
			    params = params.substring(start+1, end);
			    String[] parameters = params.split(",");
			    int counter=0;
			    for(String param:parameters)
			    {
			    	if(param.length()<2)
			    	{
			    		continue;
			    	}
			    	String paramName = param.trim().split(" ")[1];
			    	paramString.append(paramName).append("+");
			    	if(!getMethod)
					{
					  	response.append("map.put(\"").append(counter++).append("\"").append(",").append(paramName).append(")").append(";").append("\n ");	
					}
			    }
			    
			    
		    }
		    else
		    {
		    	
		    	paramString.append("\"").append("\"");
		    }
		
		    response.append("new").append(" ").append("RESTClient").append("()")
			.append(".").append("executeRemote").append("(").append("\"").append(getClassName()).append("\"")
			.append(",").append("\"").append(x.getName()).append("\"").append(",").append(x.getReturnType().getSimpleName()).append(".").append("class")
			.append(",").append(paramString.toString());
			
		    
			if(x.getParameters().length>0)
			{
				response.deleteCharAt(response.length()-1);
			}
			
			
				response.append(",").append("map").append(")").append(";").append("\n");	
			
			
			
			// values
				
			return response.toString();
	}
	
	
	
}	
	
}