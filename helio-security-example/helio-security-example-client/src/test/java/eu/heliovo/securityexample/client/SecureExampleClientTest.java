package eu.heliovo.securityexample.client;

import static org.junit.Assert.*;

import org.junit.Test;

public class SecureExampleClientTest 
{
	SecureClient	client	=	new SecureClient();
	String			param	=	"parameter";

	@Test
	public void testMethod_a() 
	{
		try 
		{
			client.logInAs("simple_anonymous", "changeme");
			System.out.println("ExampleService.method_a("+param+") = " + client.method_a(param));
			assertTrue(true);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			assertTrue(false);
		}		
	}

	@Test
	public void testMethod_b() 
	{
		try 
		{
			client.logInAs("helio_power_user", "changeme");
			System.out.println("ExampleService.method_b("+param+") = " + client.method_b(param));
			assertTrue(true);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			assertTrue(false);
		}		
	}

	@Test
	public void testMethod_c() 
	{
		try 
		{
			client.logInAs("power_anonymous", "changeme");
			System.out.println("ExampleService.method_c("+param+") = " + client.method_c(param));
			assertTrue(true);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			assertTrue(false);
		}		
	}

	@Test
	public void testMethod_d() 
	{
		try 
		{
			client.logInAs("administrator", "changeme");
			System.out.println("ExampleService.method_d("+param+") = " + client.method_d(param));
			assertTrue(true);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			assertTrue(false);
		}		
	}
}
