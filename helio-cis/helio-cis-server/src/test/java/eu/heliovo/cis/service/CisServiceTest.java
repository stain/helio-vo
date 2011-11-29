package eu.heliovo.cis.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.xml.ws.BindingProvider;

import org.junit.Ignore;
import org.junit.Test;

import eu.heliovo.shared.common.utilities.LogUtilities;
import eu.heliovo.shared.common.utilities.SecurityUtilities;
import eu.heliovo.shared.common.utilities.SecurityUtilitiesException;

public class CisServiceTest 
{
	/*
	 * The Service Stubs
	 */
	CisService			cisService		=	new CisServiceImpl();
	/*
	 * Local instance of the CIS
	 */
	String				serviceAddress	=	"http://localhost:8080/helio-cis-server/cisService";
	/*
	 * Remote instance of the CIS
	 */
//	String  			serviceAddress	=	"http://cagnode58.cs.tcd.ie:8080/helio-hps-server/hpsService";

	
	//	HITUtilities			hitUtilities	=	new HITUtilities();	
	//	CisService				cisService		=	new CisServiceImpl();
	//	SecurityUtilities		secUtils		=	new SecurityUtilities();
	//	PreferencesUtilities	prUtilities		=	new PreferencesUtilities();
	/*
	 * Utilities
	 */
	LogUtilities			logUtilities	=	new LogUtilities();
	SecurityUtilities		secUtilities	=	new SecurityUtilities();
	/*
	 * Users and passwords for testing
	 */
	/*
	 * This user is never added
	 */
	String					no_such_user	=	"no_such_user";
	String					no_such_pwd		=	"pwd_4_no_such_user";
	/*
	 * These users are added at startup
	 */
	String					user_a			=	"test_user_a";
	String					pwd_a			=	"pwd_4_test_user_a";
	String					user_b			=	"test_user_b";
	String					pwd_b			=	"pwd_4_test_user_b";
	/*
	 * These users are added during tests
	 */
	String					user_c			=	"test_user_c";
	String					pwd_c			=	"pwd_4_test_user_c";
	String					user_d			=	"test_user_d";
	String					pwd_d			=	"pwd_4_test_user_d";
	/*
	 * This keeps track of the users present in the CIS.
	 */
	Set<String>				old				=	new HashSet<String>();
	HashMap<String, String>	presentUsers	=	new HashMap<String, String>();
	HashMap<String, String>	absentUsers		=	new HashMap<String, String>();
	
	/*
	 * Default Contructor, also adds user_a and user_b if not already present in the CIS
	 */
	public CisServiceTest() 
	{
		super();
		logUtilities.printShortLogEntry("[CIS-SERVICE-TEST] - Invoking default constructor...");
		/*
		 * Creating stubs for the CIS Service
		 */
		logUtilities.printShortLogEntry("[CIS-SERVICE-TEST] - Setting up repository ...");
		try 
		{
			initializeCIS();
		} 
		catch (CisServiceException e) 
		{
			e.printStackTrace();
		} 
		catch (SecurityUtilitiesException e) 
		{
			e.printStackTrace();
		}
		logUtilities.printShortLogEntry("[CIS-SERVICE-TEST] - ...done");		
	}

	/*
	 * This sets an startup CIS (and client) with users a and b
	 */
	private void initializeCIS() throws CisServiceException, SecurityUtilitiesException
	{
		logUtilities.printShortLogEntry("[CIS-SERVICE-TEST] - Setting up repository ...");
		/*
		 * Users that must be present : a and b
		 */
			if(isUserPresent(user_a))
				presentUsers.put(user_a, pwd_a);
			else
				addUser(user_a, pwd_a);

			if(isUserPresent(user_b))
				presentUsers.put(user_b, pwd_b);
			else
				addUser(user_b, pwd_b);
			
			if(isUserPresent(user_c))
				removeUser(user_c, pwd_c);
			else
				absentUsers.put(user_c, pwd_c);

			if(isUserPresent(user_d))
				removeUser(user_d, pwd_d);
			else
				absentUsers.put(user_d, pwd_d);
			
			if(isUserPresent(no_such_user))
				removeUser(no_such_user, no_such_pwd);
			else
				absentUsers.put(no_such_user, no_such_pwd);

			printStatus();
			logUtilities.printShortLogEntry("[CIS-SERVICE-TEST] - ...done");		
	}

	@Ignore @Test
	public void testTest() 
	{
		logUtilities.printShortLogEntry("[CIS-SERVICE-TEST] - Invoking test for test...");
		logUtilities.printShortLogEntry("[CIS-SERVICE-TEST] - "  + cisService.test("test-param"));
		logUtilities.printShortLogEntry("[CIS-SERVICE-TEST] - ...done");		
	}

	@Ignore @Test
	public void testIsUserPresent() 
	{
		/*
		 * Testing users present in the CIS
		 */
		logUtilities.printShortLogEntry("[CIS-SERVICE-TEST] - Invoking test for isUserPresent...");		
		Iterator<String>	iter	=	presentUsers.keySet().iterator();
		while(iter.hasNext())
		{
			String	userName	=	iter.next();
			logUtilities.printShortLogEntry("[CIS-SERVICE-TEST] - " + userName);		
			
			try 
			{
				assertTrue(cisService.isUserPresent(userName));
			} 
			catch (CisServiceException e) 
			{
				e.printStackTrace();
				assertTrue(false);
			} 
		}
		/*
		 * Testing users NOT present in the CIS
		 */
		iter	=	absentUsers.keySet().iterator();
		while(iter.hasNext())
		{
			String	userName	=	iter.next();
			logUtilities.printShortLogEntry("[CIS-SERVICE-TEST] - " + userName);		
			
			try 
			{
				assertFalse(cisService.isUserPresent(userName));
			} 
			catch (CisServiceException e) 
			{
				e.printStackTrace();
				assertTrue(false);
			} 
		}
		logUtilities.printShortLogEntry("[CIS-SERVICE-TEST] - ...done");		
	}


	@Ignore @Test
	public void testValidateUsers() 
	{
		/*
		 * Testing users present in the CIS
		 */
		Iterator<String>	iter	=	presentUsers.keySet().iterator();
		while(iter.hasNext())
		{
			String	userName	=	iter.next();
			
			try 
			{
				assertTrue(cisService.validateUser(userName, secUtilities.computeHashOf(presentUsers.get(userName))));
			} 
			catch (SecurityUtilitiesException e) 
			{
				e.printStackTrace();
				assertTrue(false);
			}
		}
		/*
		 * Testing users NOT present in the CIS
		 */
		iter	=	absentUsers.keySet().iterator();
		while(iter.hasNext())
		{
			String	userName	=	iter.next();
			logUtilities.printShortLogEntry("[CIS-SERVICE-TEST] - " + userName);		
			
			try 
			{
				assertFalse(cisService.validateUser(userName, secUtilities.computeHashOf(absentUsers.get(userName))));
			} 
			catch (SecurityUtilitiesException e) 
			{
				e.printStackTrace();
				assertTrue(false);
			}
		}

		logUtilities.printShortLogEntry("[CIS-SERVICE-TEST] - ...done");		
	}


	@Ignore @Test
	public void testAddUser() 
	{		
		logUtilities.printShortLogEntry("[CIS-SERVICE-TEST] - Invoking test for addUser...");
		try 
		{
			printStatus();
			addUser(user_c, pwd_c);
			printStatus();
			testValidateUsers();
			testIsUserPresent();
			addUser(user_d, pwd_d);
			printStatus();
			testValidateUsers();
			testIsUserPresent();
		} 
		catch (CisServiceException e) 
		{
			e.printStackTrace();
			assertTrue(false);
		} 
		catch (SecurityUtilitiesException e) 
		{
			e.printStackTrace();
			assertTrue(false);
		}
		logUtilities.printShortLogEntry("[CIS-SERVICE-TEST] - ...done");		
	}

	@Ignore @Test
	public void testRemoveUsers() 
	{
		logUtilities.printShortLogEntry("[CIS-SERVICE-TEST] - Invoking test for removeUser...");
		
		try 
		{
			printStatus();
			addUser(user_c, pwd_c);
			printStatus();
			testValidateUsers();
			testIsUserPresent();
			removeUser(user_c, pwd_c);
			printStatus();
			testValidateUsers();
			testIsUserPresent();
			addUser(user_d, pwd_d);
			printStatus();
			testValidateUsers();
			testIsUserPresent();
			removeUser(user_d, pwd_d);
			printStatus();
			testValidateUsers();
			testIsUserPresent();
		} 
		catch (CisServiceException e) 
		{
			e.printStackTrace();
			assertTrue(false);
		} 
		catch (SecurityUtilitiesException e) 
		{
			e.printStackTrace();
			assertTrue(false);
		}
		logUtilities.printShortLogEntry("[CIS-SERVICE-TEST] - ...done");		
	}


	private void removeUser(String user_name, String user_pwd) throws CisServiceException, SecurityUtilitiesException 
	{
		logUtilities.printShortLogEntry("[CIS-SERVICE-TEST] - removeUser("+user_name+","+user_pwd+")...");
		
		if(cisService.isUserPresent(user_name))			
		{		
			cisService.removeUser(user_name, secUtilities.computeHashOf(user_pwd));
			/*
			 * Add the user to the present users and remove it from the absent users...
			 */
			presentUsers.remove(user_name);
			absentUsers.put(user_name, user_pwd);
		}
		else
		{
			/*
			 * If the user is present in the absent list, remove it.
			 */
			if(!absentUsers.containsKey(user_name))
				absentUsers.put(user_name, user_pwd);
			if(presentUsers.containsKey(user_name))
				presentUsers.remove(user_name);

		}
		logUtilities.printShortLogEntry("[CIS-SERVICE-TEST] - ...done");				
	}


	private void addUser(String user_name, String user_pwd) throws SecurityUtilitiesException, CisServiceException  
	{
		logUtilities.printShortLogEntry("[CIS-SERVICE-TEST] - addUser("+user_name+","+user_pwd+")...");
	
		if(!cisService.isUserPresent(user_name))			
		{		
			cisService.addUser(user_name, secUtilities.computeHashOf(user_pwd));
			/*
			 * Add the user to the present users and remove it from the absent users...
			 */
			presentUsers.put(user_name, user_pwd);
			absentUsers.remove(user_name);
		}
		else
		{
			/*
			 * If the user is present in the absent list, remove it.
			 */
			if(absentUsers.containsKey(user_name))
				absentUsers.remove(user_name);
			if(!presentUsers.containsKey(user_name))
				presentUsers.put(user_name, user_pwd);

		}
		logUtilities.printShortLogEntry("[CIS-SERVICE-TEST] - ...done");				
	}


	private boolean isUserPresent(String user_name) throws CisServiceException 
	{
		return cisService.isUserPresent(user_name);
	}

	private void printStatus() 
	{
//		logUtilities.printShortLogEntry("[CIS-SERVICE-TEST] - Users present on the CIS     : " + presentUsers.keySet());
//		logUtilities.printShortLogEntry("[CIS-SERVICE-TEST] - Users NOT present on the CIS : " + absentUsers.keySet());
		logUtilities.printShortLogEntry("[CIS-SERVICE-TEST] - Users present on the CIS     : " + presentUsers);
		logUtilities.printShortLogEntry("[CIS-SERVICE-TEST] - Users NOT present on the CIS : " + absentUsers);
	}
}
