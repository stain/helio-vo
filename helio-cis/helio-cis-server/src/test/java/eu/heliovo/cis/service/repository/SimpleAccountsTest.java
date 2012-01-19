package eu.heliovo.cis.service.repository;

import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

import eu.heliovo.shared.cis.HELIOUsers;
import eu.heliovo.shared.common.utilities.LogUtilities;
import eu.heliovo.shared.common.utilities.SecurityUtilities;
import eu.heliovo.shared.common.utilities.SecurityUtilitiesException;

public class SimpleAccountsTest 
{	
	/*
	 * Utilities
	 */
	SecurityUtilities	secUtilities		=	new SecurityUtilities();
	LogUtilities		logUtilities		=	new LogUtilities();
	/*
	 * The user accounts
	 */
	SimpleAccounts		accounts				=	new SimpleAccounts();
	/*
	 * Default accounts
	 */
	String				default_admin		=	HELIOUsers.HelioDefaultAdministrator;
	String				pwd_default_admin	=	"pwd4HelioAdministrator";

	@Ignore @Test
	public void testSimpleAccounts() 
	{
		logUtilities.printShortLogEntry("SimpleAccountsTest.testSimpleAccounts()");
		logUtilities.printLongLogEntry(accounts.allDetailsAsString());
		
		try 
		{
			assertTrue(accounts.validateUser(default_admin, secUtilities.computeHashOf(pwd_default_admin)));
		} 
		catch (AccountsException e) 
		{
			assertTrue(false);
		} 
		catch (SecurityUtilitiesException e) 
		{
			assertTrue(false);
		}
	}
}
