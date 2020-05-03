package com.miracle.labs.microservice.transform.service;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class FileOperations {
	
	private final int size=4000;
	
	
	public void replaceFile(File existingFile, String newContent) throws IOException
	{
		if(!existingFile.exists())
		{
			existingFile.createNewFile();
		}
		FileWriter fw = new FileWriter(existingFile);
		fw.write(newContent);
		fw.close();
	}
	
	public void createFile(File path, String name, String newContent) throws IOException
	{
		
		 createFile(new File(path,name),newContent);
	}
	
	public void createFile(File name, String newContent) throws IOException
	{
		if(!name.exists())
		{
			File parent = name.getParentFile();
			if(!parent.exists())
			{
				parent.mkdirs();
			}
			name.createNewFile();
		}
		
		FileWriter fw = new FileWriter(name);
		fw.write(newContent);
		fw.close();
		System.out.println("Created File "+ name.getAbsolutePath());
	}
	
	public void copyFile(String source, String destination) throws IOException
	{
		copyFile(new File(source),new File(destination));
	}
	
	public void copyFile(File source, String destination) throws IOException
	{
		copyFile(source,new File(destination));
	}
	
	public void copyFile(File source, File destination) throws IOException
	{
		if(!source.exists() || !source.isFile())
		{
			throw new RuntimeException("source should be an existing file.");
		}	
		
		if(!destination.exists())
		{	
			File parent = destination.getParentFile();
			if(!parent.exists())
			{
				parent.mkdirs();
			}
			destination.createNewFile();
			
		}
		
		
		try
		(
		   FileReader fr = new FileReader(source);FileWriter fw = new FileWriter(destination);  
		)
		{
			char[] buffer = new char[size];
			int read = 1;
			while((read = fr.read(buffer))>0)
			{
				fw.write(buffer,0, read);
			}	
			System.out.println("Copied File "+ destination.getAbsolutePath());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		
	}
	
	public void copyFolder(String existingPath, String newPath)
	{
		File fl = new File(existingPath);
		File newfl = new File(newPath);
		
		if(!fl.exists() && !fl.isDirectory())
		{
			throw new RuntimeException("existingPath should be a directory");
		}
		
		newfl.mkdirs();
		
		File[] f = fl.listFiles();
		Arrays.stream(f).forEach(x ->{
			File newFile = new File(newfl, x.getName());
			try {
				copyFile(x, newFile);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
	}
	
	public void createFolder(String newFolder)
	{
		File fl = new File(newFolder);
		fl.mkdirs();
	
	}
	
	public void createFolder(File newFolder)
	{
		
		newFolder.mkdirs();
	
	}
}
