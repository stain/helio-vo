package eu.heliovo.cis;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.heliovo.shared.common.utilities.LogUtilities;
import eu.heliovo.shared.common.utilities.SecurityUtilities;

public class CisAuthenticationProviderTest 
{
	/*
	 * The authentication provider
	 */
	CisAuthenticationProvider	authProvider	=	new CisAuthenticationProvider();
	/*
	 * Utilities
	 */
	LogUtilities				logUtilities	=	new LogUtilities();
	SecurityUtilities			secUtilities	=	new SecurityUtilities();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		/*
		 * Create accounts for tests
		 */
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception 
	{
	
	}

	@Test
	public void testAuthenticate() 
	{
		OldCisAuthenticationToken	authToken	=	new OldCisAuthenticationToken();
		authToken	=	(OldCisAuthenticationToken) authProvider.authenticate(authToken);
		assertTrue(authToken.isAuthenticated());
	}

	@Test
	public void testSupports() 
	{
		fail("Not yet implemented");
	}
}
