/**
 * This Class is the place holder class which serves with important configs for Transform processes to execute.
 * Since we are working with older applications, we tried to keep it as simple as possible.
 * User can refer this class to gain understanding of the code.
 * 
 * Created By:Miracle
 * 
 */
package com.miracle.labs.microservice.transform.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;

public class Attributes {

	/**
	 * The Root Folder of this project file. 
	 */
	protected File baseCodeDirectory;
	/**
	 * The Directory where we will put Generated Class file bundles for Monolith Application
	 */
	protected File targetMonlithCodeDirectory;
	/**
	 * The src folder of Monolith Source Code
	 */
	protected File sourceDirectoryOfMonlithCode;
	/**
	 * The Directory where we will put Generated Class file bundles for Microservice Application
	 */
	protected File targetMicroserviceCodeDirectory;
	/**
	 * 
	 */
	/**
	 * The Class Name of the Dependee class selected for Transformation.
	 */
	protected String dependeeClassName;
	/**
	 * The .java class file name of the selected Dependee class for Transformation.
	 */
	protected File dependeeClassFile;
	/**
	 * The package name declaration of the selected Dependee class for Transformation.
	 */
	protected String dependeeClassPackageName;
	/**
	 * The Class object of the dependeeClass, available at runtime
	 */
	protected Class dependeeClass;
	/**
	 * The code of the DependeeClass file in Text.
	 */
	protected StringBuilder dependeeClassCode;
	/**
	 * Package declaration for Microservice code being generated. 
	 */
	protected String GEN_MICRO_PACKAGE="com.miracle.labs.microservice.transform.generated";
	/**
	 * Package declaration for Monolith code being generated. 
	 */
	protected String GEN_MONOLITH_PACKAGE="com.miracle.labs.monolith.transform.generated";
	/**
	 * The name of Microservice Controller Class, by default its it DependeeClassName +"Controller"
	 */
	protected String targetMicroserviceControllerName;
	/**
	 * The name of Microservice Controller Class File, by default its it DependeeClassName +"Controller.java"
	 */
	protected String targetMicroserviceControllerFileName;
	
	public File getBaseCodeDirectory() {
		return baseCodeDirectory;
	}
	
	
	
	public File getSourceDirectoryOfMonlithCode() {
		return sourceDirectoryOfMonlithCode;
	}



	public void setsourceDirectoryOfMonlithCode(File sourceMonlithCodeDirectory) {
		this.sourceDirectoryOfMonlithCode = sourceMonlithCodeDirectory;
	}



	public String getTargetMicroserviceControllerName() {
		return targetMicroserviceControllerName;
	}


	


	public String getTargetMicroserviceControllerFileName() {
		return targetMicroserviceControllerFileName;
	}


	

	protected void setDependeeClassCode() throws IOException, ClassNotFoundException {
		
		FileInputStream code = new FileInputStream(getDependeeClassFile());
		StringBuilder sbr = new StringBuilder();
		byte[] fileBytes = Files.readAllBytes(getDependeeClassFile().toPath());
		sbr.append(new String(fileBytes));
		dependeeClassCode = sbr;
		
	}
	
	public StringBuilder getDependeeClassCode()
	{
		return this.dependeeClassCode;
	}
	
	public void setBaseCodeDirectory(File baseCodeDirectory) {
		this.baseCodeDirectory = baseCodeDirectory;
	}

	public File getTargetMonlithCodeDirectory() {
		return targetMonlithCodeDirectory;
	}
	public void setTargetMonlithCodeDirectory(File targetMonlithCodeDirectory) {
		this.targetMonlithCodeDirectory = targetMonlithCodeDirectory;
	}
	public File getTargetMicroserviceCodeDirectory() {
		return targetMicroserviceCodeDirectory;
	}
	public void setTargetMicroserviceCodeDirectory(
			File targetMicroserviceCodeDirectory) {
		this.targetMicroserviceCodeDirectory = targetMicroserviceCodeDirectory;
	}

	public String getDependeeClassName() {
		return dependeeClassName;
	}
	
	
	public Class getDependeeClass() {
		return dependeeClass;
	}
	
	public File getDependeeClassFile()
	{
		return this.dependeeClassFile;
	}
	
	public String getGeneratedMicroservicePackage()
	{
		return GEN_MICRO_PACKAGE;
	}
	
	public String getGeneratedMonolithPackage()
	{
		return GEN_MONOLITH_PACKAGE;
	}
	
	
	public void setDependeeClassFile(String dependeeClassFile) throws ClassNotFoundException, IOException {
		this.dependeeClassFile = new File(dependeeClassFile);
		if(! this.dependeeClassFile.exists())
		{
			throw new FileNotFoundException("Unable to read file "+ dependeeClassFile);
		}

		
		URL url = this.dependeeClassFile.toURI().toURL();          
	    URL[] urls = new URL[]{url};

	    setDependeeClassCode();
	    setDependeeClassName();
	    setDependeeClassPackageName();
	    try
	    {
	    	dependeeClass = this.getClass().getClassLoader().loadClass(getDependeeClassPackageName()+"."+getDependeeClassName());
	    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	    
	}
	
	public String getDependeeClassPackageName()
	{
		return dependeeClassPackageName;
	}
	
	protected String setDependeeClassPackageName()
	{
		int start = dependeeClassCode.indexOf("package");
		int end = dependeeClassCode.indexOf("\n",start+1);
		dependeeClassPackageName =  dependeeClassCode.substring(start+ "package".length(),end-1).trim();
		return dependeeClassPackageName;
	}
	
	protected String setDependeeClassName()
	{
		String fileName = this.getDependeeClassFile().getName();
		fileName = fileName.split("\\.")[0];
		dependeeClassName = fileName;
		targetMicroserviceControllerName=dependeeClassName +"Controller";
		targetMicroserviceControllerFileName=targetMicroserviceControllerName+".java";
		return fileName;
	}
	
	public Attributes(File baseCodeDirectory, File targetMonlithCodeDirectory,
			File targetMicroserviceCodeDirectory,
			File targetMicroserviceCodeTemplateDirectory,
			String dependeeClassName, Class dependeeClass) {
		super();
		this.baseCodeDirectory = baseCodeDirectory;
		this.targetMonlithCodeDirectory = targetMonlithCodeDirectory;
		this.targetMicroserviceCodeDirectory = targetMicroserviceCodeDirectory;
		this.dependeeClassName = dependeeClassName;
		this.dependeeClass = dependeeClass;
	}
	
	
	public Attributes() {
		super();
		
	}

	public String getBridgeClassName() {
		return getDependeeClassName()+"Bridge";
	}
	
	public boolean isGetMethod(String methodName) {
		return methodName.startsWith("get")?true:false;
	}


	
}
