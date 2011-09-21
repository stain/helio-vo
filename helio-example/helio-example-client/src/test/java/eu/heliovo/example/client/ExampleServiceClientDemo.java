package eu.heliovo.example.client;

import javax.xml.ws.BindingProvider;

import eu.heliovo.example.server.ExampleService;
import eu.heliovo.example.server.ExampleService_Service;
import eu.heliovo.example.server.TestException_Exception;

public class ExampleServiceClientDemo 
{
	ExampleService_Service	ess	=	new ExampleService_Service();
	
	public static void main(String[] args) 
	{
		ExampleServiceClientDemo	demo	=	new ExampleServiceClientDemo();
		demo.perform();
	}

	private void perform() 
	{		
		ExampleService	exService	=	ess.getExampleServiceImplPort();
		((BindingProvider)exService).getRequestContext().put(
				BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
				"http://localhost:8080/helio-example-server/exampleService");
//				"http://cagnode58.cs.tcd.ie:8080/helio-example-server/exampleService");

		System.out.println(exService.testOK("Hello there !!").getTestComment());
		try 
		{
			exService.testKO("Hello there !!");
		} 
		catch (TestException_Exception e) 
		{
			e.printStackTrace();
		}
	}
}
