/**
 * First Test Class to be executed to Generate required Files.
 * This Test generates all the bundles which are generated by Transform.
 * User can refer this class to gain understanding of the code.
 * 
 * 
 * 
 * Created By:Miracle
 * 
 */
package test.miracle.labs.microservice.transform;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.miracle.labs.microservice.transform.Manager;
import com.miracle.labs.microservice.transform.model.Attributes;
import com.miracle.labs.microservice.transform.service.RESTWrapper;
import com.miracle.labs.microservice.transform.service.SimpleProxyConfiguration;

public class TestGenerateFiles {

	Manager manager;
	Attributes attributes; 
	/**
	 * This method demonstrates use of Attributes Object which is primary place holder with important configurations for Transform process.
	 * Manager is the starting operation class.
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@Before
	public void setup() throws ClassNotFoundException, IOException
	{
		attributes = new Attributes();
		attributes.setsourceDirectoryOfMonlithCode(new File("../Monolith/src"));
		attributes.setDependeeClassFile("../Monolith/src/com/monolith/legacy/DependeeClass.java");
		attributes.setTargetMonlithCodeDirectory(new File("./newMonolith"));
		attributes.setTargetMicroserviceCodeDirectory(new File("./newMicroservice"));
		attributes.setBaseCodeDirectory(new File("."));
		manager = new Manager(attributes);
		manager.setup();
	}
	
	/**
	 * Test for the new code required to execute monolith after transform 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void testProxyCreation() throws IOException, ClassNotFoundException {
		manager.createProxyClassFile();
		manager.copyExtensionFiles();
		File dependeeMonolithClassFile = new File(attributes.getTargetMonlithCodeDirectory(),attributes.getDependeeClassName()+".java");
		Assert.assertTrue(dependeeMonolithClassFile.exists());

		File targetDirectory = new File(attributes.getTargetMonlithCodeDirectory()+"/com/miracle/labs/transform/generated/tools");
		File proxyFile = new File(targetDirectory, "RESTClient.java");
		Assert.assertTrue(proxyFile.exists());
		

		
		File proxyConvertorFile = new File(targetDirectory, "JsonConvertor.java");
		Assert.assertTrue(proxyConvertorFile.exists());
	}

	/**
	 * Test for the new code required to execute microservice
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void testWrapperCreation() throws IOException, ClassNotFoundException {
		manager.createWrapperClassFile();
		File bridgeFile = new File (attributes.getTargetMicroserviceCodeDirectory(), attributes.getBridgeClassName()+".java");
		File targetDirectory = new File(attributes.getTargetMicroserviceCodeDirectory()+"/com/miracle/labs/microservice/transform/generated");
		File controllerFile = new File(targetDirectory, attributes.getTargetMicroserviceControllerFileName());
		Assert.assertTrue(bridgeFile.exists());
		Assert.assertTrue(controllerFile.exists());
	}

	
	
}
