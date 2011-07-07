package eu.heliovo.securityexample.client;

import static org.junit.Assert.*;

import org.junit.Test;

public class SecurityContextTest 
{
	private SecurityContextHandler	secContextHandler	=	new SecurityContextHandler();

	@Test
	public void test_a() 
	{
		String	userName	=	"test";
		String	userPwd		=	"changeme";
				
		try 
		{
			secContextHandler.setSecurityContext(userName, userPwd);		
			assertTrue(true);
		} 
		catch (SecureClientException e) 
		{
			e.printStackTrace();
			assertTrue(false);
		}
	}
}
