package eu.heliovo.securityexample.client;

import static org.junit.Assert.*;

import org.junit.Test;

public class ExampleClientTest 
{
	ExampleClient	client	=	new ExampleClient();
	String			param	=	"parameter";

	@Test
	public void testMethod_a() 
	{
		try 
		{
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
