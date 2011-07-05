package eu.heliovo.securityexample.client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import eu.heliovo.securityexample.server.ExampleService;

public class SecureClient 
{
	String							configurationFile	=	"exampleClient.conf.txt";	
	
	public 	String					serviceAddress	=	"http://localhost:8080/helio-security-example-server/exampleService";
	public	Class<ExampleService>	serviceClass	=	ExampleService.class;
	
	
	public SecureClient() 
	{
		super();
				
		try 
		{
		    BufferedReader in = new BufferedReader(new FileReader(configurationFile));
			serviceAddress = in.readLine();
		    in.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
	} 	

	
	private SecurityContextHandler	secContextHandler	=	new SecurityContextHandler();
	
	public	void	logInAs(String helioUserName, String helioUserPwd) throws SecureClientException
	{
		secContextHandler.setSecurityContext(helioUserName, helioUserPwd);
	}
	
	public String method_a(String param) throws Exception 
	{
	   	JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
    	factory.setServiceClass(serviceClass);
    	factory.setAddress(serviceAddress);
    	ExampleService client = (ExampleService) factory.create();
    	return	client.method_a(param);    	
	}
	
	public String method_b(String param) throws Exception 
	{
	   	JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
    	factory.setServiceClass(serviceClass);
    	factory.setAddress(serviceAddress);
    	ExampleService client = (ExampleService) factory.create();
    	return	client.method_b(param);    	
	}

	public String method_c(String param) throws Exception 
	{
	   	JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
    	factory.setServiceClass(serviceClass);
    	factory.setAddress(serviceAddress);
    	ExampleService client = (ExampleService) factory.create();
    	return	client.method_c(param);    	
	}

	public String method_d(String param) throws Exception 
	{
	   	JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
    	factory.setServiceClass(serviceClass);
    	factory.setAddress(serviceAddress);
    	ExampleService client = (ExampleService) factory.create();
    	return	client.method_d(param);    	
	}

	
}
