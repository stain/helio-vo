package eu.heliovo.securityexample.server;

import static org.junit.Assert.*;

import org.junit.Test;

public class ExampleServiceImplTest 
{
	ExampleServiceImpl	service	=	new ExampleServiceImpl();
	String				param	=	"parameter";
	
	@Test
	public void testMethod_a() 
	{
		System.out.println("ExampleService.method_a("+param+") = " + service.method_a(param));		
		assertTrue(true);
	}

	@Test
	public void testMethod_b() 
	{
		System.out.println("ExampleService.method_b("+param+") = " + service.method_b(param));		
		assertTrue(true);
	}

	@Test
	public void testMethod_c() 
	{
		System.out.println("ExampleService.method_c("+param+") = " + service.method_c(param));		
		assertTrue(true);
	}

	@Test
	public void testMethod_d() 
	{
		System.out.println("ExampleService.method_d("+param+") = " + service.method_d(param));		
		assertTrue(true);
	}
}
