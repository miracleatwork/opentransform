package com.miracle.labs.microservice.transform.service;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;










import com.miracle.labs.microservice.transform.model.Attributes;
import com.miracle.labs.transform.generated.tools.JsonConvertor;
import com.monolith.legacy.ClassA;


/**
 * New Microservice:  Wrappers over dependee class available over rest endpoint. 
 * @author miracle
 *
 */

public class RESTWrapper {

	private StringBuilder newClass = new StringBuilder();	
	private StringBuilder newBridge = new StringBuilder();	
	
	protected Attributes attributes;
	
	
	public RESTWrapper(Attributes attribute) {
		super();
		this.attributes = attribute;
	}
	
	public StringBuilder getGeneratedCode()
	{
		return newClass;
	}
	
	public StringBuilder getGeneratedBridgeCode()
	{
		return newBridge;
	}
	
	public void createRESTWrapperClass() throws IOException, ClassNotFoundException{
		
		inspect();
	}
	 
	private void inspect() {
		ClassStructure newClass = new ClassStructure();
		Class cls = this.attributes.getDependeeClass();
		newClass.setClassName(cls.getSimpleName());
		newClass.setPackageName(cls.getPackage().getName());
		newClass.setMethods(cls.getDeclaredMethods());
		newClass.setBridge(cls.getDeclaredMethods());
		
	}


	
	
	private class ClassStructure
	{
		String packageName;
		String className;
		List<String> methods = new ArrayList<>();
		List<String> ignoredMethods ;
		List<Integer> ignoredModifiers ;
		
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
			ignoredMethods.add(" private ");
			
			
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
		
		private String getImportStatements()
		{
			return "import javax.ws.rs.*; "
					+ "import javax.ws.rs.core.*;"
					+ "import javax.enterprise.context.ApplicationScoped;";
			
			
		}
		
		public void setMethods(Method[] methods) {
			
			final StringBuilder methodString = newClass.append("\n");
			
			methodString.append("package ").append(attributes.getGeneratedMicroservicePackage()).append(";").append("\n");
			
			methodString.append(getImportStatements()).append("\n");
			methodString.append("@").append("ApplicationScoped").append("\n").append("@").append("Path").append("(").append("\"").append("/")
				.append(getClassName()).append("\"").append(")").append("\n");
			methodString.append("public").append(" ").append("class").append(" ").append(attributes.getTargetMicroserviceControllerName()).append("{").append("\n");
			
			methodString.append(getPackageName()).append(".").append(attributes.getBridgeClassName()).append(" ").append("target")
			.append(" ").append("=").append(" ").append("new").append(" ").append(getPackageName()).append(".").append(attributes.getBridgeClassName()).append("()").append(";")
			.append("\n");
			
			
			Arrays.stream(methods)
			.forEach(x->{ 
				if(ignoredMethods.contains(x.getName()))
				{
					return;
				}
				int m = x.getModifiers();
				if(Modifier.isPrivate(m)|| Modifier.isTransient(m)|| Modifier.isVolatile(m))
				{
					return;
				}
				
				final StringBuilder uriParams = new StringBuilder("");
				final StringBuilder methodDefinitionParams = new StringBuilder("");
				final StringBuilder targetCallParams = new StringBuilder("");
				int counter=0;			
				for( Parameter y:x.getParameters())
				{
					if(attributes.isGetMethod(x.getName()))
					{
						uriParams.append("{").append(y.getName()).append("}").append("/");
						methodDefinitionParams.append("@PathParam(\"").append(y.getName()).append("\") ");
					}
					else
					{
						methodDefinitionParams.append("@FormParam(\"").append(counter++).append("\") ");
					}
					//methodDefinitionParams.append(y.getParameterizedType().getTypeName()).append(" ").append(y.getName()).append(" ,");	
					
					methodDefinitionParams.append("String").append(" ").append(y.getName()).append(" ,");
					targetCallParams.append(y.getName()).append(",");
				}			
				if(x.getParameters().length>0)
				{
					if(attributes.isGetMethod(x.getName()))
					{
						uriParams.deleteCharAt(uriParams.length()-1);
					}
					
					methodDefinitionParams.deleteCharAt(methodDefinitionParams.length()-1);
					targetCallParams.deleteCharAt(targetCallParams.length()-1);
				}
				
				// create annotations
				if(x.getName().startsWith("set"))
				{
					methodString.append("@POST").append("\n");
					
							
				}
				else
				{// consider get
					methodString.append("@GET").append("\n");
				}
				
				methodString.append("@Path").append("(").append("\"").append("/").append(x.getName().substring(3));
				if(uriParams.length()>0)
				{	
					methodString.append("/").append(uriParams.toString());
				}
				methodString.append("\"").append(")").append("\n");
				
				methodString.append("@Produces").append("(").append("MediaType.APPLICATION_JSON").append(")").append("\n");
				methodString.append("@Consumes(\"application/x-www-form-urlencoded\")").append("\n");
				
				
				methodString.append("public").append(" ").append("String").append(" ").append(x.getName()).append("(")
				.append(methodDefinitionParams.toString())
				.append(")").append("{").append("\n");
				// call the method
			
				methodString.append("\t").append("return").append(" ").append("target.").append(x.getName()) .append("(")
				.append(targetCallParams)
				.append(")")
				.append(";").append("\n");
				methodString.append("}").append("\n");
				
						
				
			});
			
			methodString.append("}").append("\n");
		}
		
		public void setBridge(Method[] methods) {
			
			final StringBuilder methodString = newBridge.append("\n");
			
			
			methodString.append("package ").append(getPackageName()).append(";").append("\n");
			
			methodString.append("import").append(" ").append("com.miracle.labs.microservice.transform.generated.ResponseBuilder").append(";")
			.append("\n").append("import javax.ws.rs.FormParam").append(";").append("\n")
			.append("import com.miracle.labs.transform.generated.tools.JsonConvertor;").append("\n");
			 

			methodString.append("public").append(" ").append("class").append(" ").append(attributes.getBridgeClassName()).append("{").append("\n");
			
			methodString.append(getClassName()).append(" ").append("target")
			.append(" ").append("=").append(" ").append("new").append(" ").append(getClassName()).append("()").append(";")
			.append("\n");
			
			
			Arrays.stream(methods)
			.forEach(x->{ 
				if(ignoredMethods.contains(x.getName()))
				{
					return;
				}
				int m = x.getModifiers();
				if(Modifier.isPrivate(m)|| Modifier.isTransient(m)|| Modifier.isVolatile(m))
				{
					return;
				}
				final StringBuilder methodBodyAdditions = new StringBuilder("");	
				final StringBuilder uriParams = new StringBuilder("");
				final StringBuilder methodDefinitionParams = new StringBuilder("");
				final StringBuilder targetCallParams = new StringBuilder("");
				int counter=0;			
				for( Parameter y:x.getParameters())
				{
					uriParams.append("{").append(y.getName()).append("}").append("/");
					methodDefinitionParams
						//.append(y.getParameterizedType().getTypeName()).append(" ").append(y.getName()).append(" ,");
						.append("String").append(" ").append(y.getName()).append(" ,");
					
//					if( y.getT instanceof Object)
//					{
//						methodBodyAdditions.append(y.getParameterizedType().getTypeName()).append(" ").append("a"+counter).append("=").append("null").append(";").append("\n")
//						.append("a"+counter).append(" = ").append("JsonConvertor.getClassFromJsonString(").append(y.getName()).append(", ")
//						.append("a"+counter).append(".getClass()").append(");").append("\n");		
//					}
//					else
					{
						methodBodyAdditions.append(y.getParameterizedType().getTypeName()).append(" ").append("a"+counter)
						.append(" = ").append("JsonConvertor.getClassFromJsonString(").append(y.getName()).append(", ")
						.append(unGenerifyClassName(y.getParameterizedType().getTypeName())).append(".class").append(");").append("\n");
					}
					
					
					targetCallParams.append("a"+counter).append(",");
					counter++;
				}			
				if(x.getParameters().length>0)
				{
					uriParams.deleteCharAt(uriParams.length()-1);
					methodDefinitionParams.deleteCharAt(methodDefinitionParams.length()-1);
					targetCallParams.deleteCharAt(targetCallParams.length()-1);
				}
				
				
				
				
				
				
				methodString.append("public").append(" ").append("String").append(" ").append(x.getName()).append("(")
				.append(methodDefinitionParams.toString())
				.append(")").append("{").append("\n");
				
				methodString.append("\n").append(methodBodyAdditions.toString()).append("\n");
				
				if(x.getReturnType().equals(void.class))
				{// returns void
					methodString.append("\t").append(" ").append("target.").append(x.getName()) .append("(")
					.append(targetCallParams)
					.append(")")
					.append(";").append("\n")
					.append("return").append(" ").append("ResponseBuilder").append(".").append("getDefaultResponseJsonString").append("()")
					.append(";").append("\n");
				}
				else
				{
					
					
					methodString.append("\t").append("return").append(" ").append("ResponseBuilder").append(".").append("getMessageResponseJsonString")
					.append("(")
					.append(" ").append("target.").append(x.getName()) .append("(")
					.append(targetCallParams)
					.append(")").append(")")
					.append(";").append("\n");
					

				}
				
				
				// call the method
				
				
				methodString.append("}").append("\n");
				
						
				
			});
			
			methodString.append("}").append("\n");
		}
		
	}	
	
	private String unGenerifyClassName(String name)
	{
		int start = name.indexOf("<");
		if(start>0)
		{	
			return name.substring(0, start);
		}
		return name;
	}
}
