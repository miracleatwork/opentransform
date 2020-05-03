/**
 * This Test generates all the bundles which are generated by Transform.
 * User can refer this class to gain understanding of the code.
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
import com.miracle.labs.microservice.transform.service.FileOperations;
import com.miracle.labs.microservice.transform.service.RESTWrapper;
import com.miracle.labs.microservice.transform.service.SimpleProxyConfiguration;

/**
 * This test class pushes and validates the generated files to Monolith & Microservice project folder.
 * This is a best attempt automation of Deploying Instructions given within newMicroservice and newMonolith directory.
 * 
 * @author miracle
 *
 */
public class TestPushFiles {

	Manager manager;
	Attributes attributes; 
	private FileOperations operations;

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
		operations = new FileOperations();
	}
	
	/**
	 * Test for the new code required to execute microservice
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	//@Test
	public void testWrapperCreation() throws IOException, ClassNotFoundException {
		manager.createWrapperClassFile();
		File bridgeFile = new File (attributes.getTargetMicroserviceCodeDirectory(), attributes.getBridgeClassName()+".java");
		File targetDirectory = new File(attributes.getTargetMicroserviceCodeDirectory()+"/com/miracle/labs/microservice/transform/generated");
		File controllerFile = new File(targetDirectory, attributes.getTargetMicroserviceControllerFileName());
		Assert.assertTrue(bridgeFile.exists());
		Assert.assertTrue(controllerFile.exists());
	}

	
	@Test
	public void attemptPushToNewMonolithProject() throws IOException
	{
		// Source of generated Monolith Files
		String sourcePath = "./newMonolith";
		String sourceComPath = sourcePath+"/com/miracle/labs/transform/generated/tools";
		// NewMonolith Project 
		String monolithRootDirectoryPath = "../NewMonolith";
		String monolithComDirectoryPath = monolithRootDirectoryPath+ "/src/com/miracle/labs/transform/generated/tools";
		
		operations.copyFolder(sourceComPath, monolithComDirectoryPath);
		
		File proxyFile = new File(monolithComDirectoryPath, "RESTClient.java");
		Assert.assertTrue(proxyFile.exists());
		
		String sourceDependeeClassPath=sourcePath+"/DependeeClass.java";
		String monolithDependeeClassPath= monolithRootDirectoryPath+"/src/com/monolith/legacy/DependeeClass.java";
		operations.copyFile(sourceDependeeClassPath, monolithDependeeClassPath);
		
		File dependeeFile = new File(monolithDependeeClassPath);
		Assert.assertTrue(dependeeFile.exists());
		
	}
	
	@Test
	public void attemptPushToNewMicroserviceProject() throws IOException
	{
		// Source of generated Monolith Files
		String sourcePath = "./newMicroservice";
		String sourceComPath = sourcePath+"/com/miracle/labs/microservice/transform/generated";
		// NewMonolith Project 
		String microserviceRootDirectoryPath = "../ThorntailDemo";
		String microserviceComDirectoryPath = microserviceRootDirectoryPath+ "/src/main/java/com/miracle/labs/microservice/transform/generated";
		
		operations.copyFolder(sourceComPath, microserviceComDirectoryPath);
		
		File controllerFile = new File(microserviceComDirectoryPath, "DependeeClassController.java");
		Assert.assertTrue(controllerFile.exists());
		
		File responseFile = new File(microserviceComDirectoryPath, "Response.java");
		Assert.assertTrue(responseFile.exists());
		
		File responseBuilderFile = new File(microserviceComDirectoryPath, "ResponseBuilder.java");
		Assert.assertTrue(responseBuilderFile.exists());
		
		
		String sourceDependeeBridgeClassPath = sourcePath+"/DependeeClassBridge.java";

		String monolithCodeWithinMicroservicePath=microserviceRootDirectoryPath+ "/src/main/java/com/monolith/legacy";
		String targetMonolithDependeeClassPath= monolithCodeWithinMicroservicePath+"/DependeeClassBridge.java";
		operations.copyFile(sourceDependeeBridgeClassPath, targetMonolithDependeeClassPath);
		
		File dependeeBridgeFile = new File(targetMonolithDependeeClassPath);
		Assert.assertTrue(dependeeBridgeFile.exists());

	}
}
