package com.miracle.labs.microservice.transform.service;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.miracle.labs.microservice.transform.model.Attributes;

/**
 * This class will add Proxy to the  dependee 
 * 
 * 
 * @author miracle
 *
 */


public class ProxyConfiguration {

protected Attributes attributes;
	
	private StringBuilder newClass = new StringBuilder(); 
	
	public ProxyConfiguration(Attributes attribute) {
		super();
		this.attributes = attribute;
	}

	
	public ProxyConfiguration() {
	
	}


	public void createProxyClass(){
		
		inspect(this.attributes.getDependeeClass());
	}
	
	private void inspect(Class dependeeClass) {
		ClassStructure newClass = new ClassStructure();
		Class cls = dependeeClass;
		newClass.setClassName(cls.getSimpleName());
		newClass.setPackageName(cls.getPackage().getName());
		newClass.setMethods(cls.getDeclaredMethods());
	}


	protected void replaceDependeeWithProxy()
	{
		
	}
}


class ClassStructure
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
	public void setMethods(Method[] methods) {
		Arrays.stream(methods)
		.forEach(x->{ 
			if(ignoredMethods.contains(x.getName()))
			{
				return;
			}
			final StringBuilder methodString = new StringBuilder();
			methodString.append(Modifier.toString(x.getModifiers())) .append(" ")
			.append(x.getReturnType()) .append(" ")
			.append(x.getName()) .append(" (");
			
			for( Parameter y:x.getParameters())
				{
				methodString.append(y.getParameterizedType()).append(" ").append(y.getName()).append(" ,");
			}
			if(x.getParameters().length>0)
			{
				methodString.deleteCharAt(methodString.length()-1);
			}
			methodString.append(")  {");
			methodString.append(" // code ");
			methodString.append("}");
			System.out.println(methodString.toString());			
					
			
		});
		
		
	}
	
	
	
}
