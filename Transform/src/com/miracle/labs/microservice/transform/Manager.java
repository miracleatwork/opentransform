/**
 * This Class is the entrypoint for Transform processes to execute.
 * Please refer to the Test Class to know how to kickstart the Transform Process.
 * 
 * Created By:Miracle
 * 
 */
package com.miracle.labs.microservice.transform;

import java.io.File;
import java.io.IOException;

import com.miracle.labs.microservice.transform.model.Attributes;
import com.miracle.labs.microservice.transform.service.FileOperations;
import com.miracle.labs.microservice.transform.service.RESTWrapper;
import com.miracle.labs.microservice.transform.service.SimpleProxyConfiguration;



/**
 * This project performs below transformation
 * 1) It reads the monolith code
 * 2) It accepts a class name to transform. It is termed as dependee class
 * 3) It will create two sets of code
 * 		 i) Modified Monolith : Proxy introduced to replace dependee class.
 * 		ii) New Microservice:  Wrappers over dependee class available over rest endpoint. 
 * 
 * @author miracle
 *
 */

public class Manager {

	
	private SimpleProxyConfiguration proxy ;
	private RESTWrapper wrapper ;
	private FileOperations operations;
	private Attributes attributes  = new Attributes();
	
	public Manager(Attributes attributes)
	{
		this.attributes = attributes;
	}
	
	public void setup() throws ClassNotFoundException, IOException
	{
		
		proxy = new SimpleProxyConfiguration(attributes);
		wrapper = new RESTWrapper(attributes);
		operations = new FileOperations();
		operations.createFolder(attributes.getTargetMonlithCodeDirectory());
		operations.createFolder(attributes.getTargetMicroserviceCodeDirectory());
	}
	
	public void createProxyClassFile() throws IOException, ClassNotFoundException {
		proxy.createProxyClass();
		StringBuilder proxyClassCode = proxy.getGeneratedCode();  
		operations.createFile(attributes.getTargetMonlithCodeDirectory(), attributes.getDependeeClassFile().getName(), proxyClassCode.toString());
		
	}

	
	
	public void createWrapperClassFile() throws IOException, ClassNotFoundException {
		wrapper.createRESTWrapperClass(); 
		StringBuilder wrapperClassCode = wrapper.getGeneratedCode();
		StringBuilder bridgeClassCode = wrapper.getGeneratedBridgeCode();
		operations.createFile(attributes.getTargetMicroserviceCodeDirectory(), attributes.getBridgeClassName()+".java", bridgeClassCode.toString());
		File targetDirectory = new File(attributes.getTargetMicroserviceCodeDirectory()+"/com/miracle/labs/microservice/transform/generated");
		operations.createFile(targetDirectory, attributes.getTargetMicroserviceControllerFileName(), wrapperClassCode.toString());
		
	}
	
	
	public void copyExtensionFiles() throws IOException, ClassNotFoundException {
		
		File source = new File("./src/com/miracle/labs/transform/generated/tools/RESTClient.java");
		File targetDirectory = new File(attributes.getTargetMonlithCodeDirectory()+"/com/miracle/labs/transform/generated/tools");
		File target = new File(targetDirectory, "RESTClient.java");
		operations.copyFile(source, target);
		source = new File("./src/com/miracle/labs/transform/generated/tools/JsonConvertor.java");
		target = new File(targetDirectory, "JsonConvertor.java");
		operations.copyFile(source, target);
	}
	
	
	
	public static void main(String args[])
	{
		System.out.println("Loaded");
	}
}
