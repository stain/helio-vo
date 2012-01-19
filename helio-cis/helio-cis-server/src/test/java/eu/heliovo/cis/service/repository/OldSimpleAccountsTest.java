package eu.heliovo.cis.service.repository;

import static org.junit.Assert.*;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import eu.heliovo.shared.cis.HELIOUsers;
import eu.heliovo.shared.common.utilities.LogUtilities;
import eu.heliovo.shared.common.utilities.SecurityUtilities;
import eu.heliovo.shared.common.utilities.SecurityUtilitiesException;

public class OldSimpleAccountsTest 
{
	/*
	 * If this is true, the repository file file is deleted
	 * at the beginning of the test
	 */
	boolean				deleteRepository	=	true;
	/*
	 * Utilities
	 */
	SecurityUtilities	secUtilities		=	new SecurityUtilities();
	LogUtilities		logUtilities		=	new LogUtilities();
	/*
	 * The file the stores the accounts
	 */
	String				statusFile			=	"/tmp/CIS.repository";
	/*
	 * The user accounts
	 */
	SimpleAccounts		accounts				=	new SimpleAccounts();
	/*
	 * Local vectors that keep track of what accounts are present in the cis
	 */
	Set<String>		presentUsers			=	new HashSet<String>();
	Set<String>		absentUsers				=	new HashSet<String>();
	/*
	 * Accounts for the tests: A, B, C and D
	 */
	/*
	 * Account A : Defined with name and password.
	 * No email and standard preferences
	 * Standard role
	 */
	String				name_a				=	"A";
	String				pwd_a				=	"pwd_4_A";
	SimpleAccount		account_a			=	null;
	/*
	 * Account B : Defined with name, email and password.
	 * Standard preferences
	 * Standard role
	 */
	String				name_b				=	"B";
	String				email_b				=	"B@helio.eu";
	String				pwd_b				=	"pwd_4_B";
	SimpleAccount		account_b			=	null;
	/*
	 * Account C : Defined with name and password.
	 * User-defined preferences
	 * Standard role
	 */
	String				name_c				=	"C";
	String				email_c				=	"C@helio.eu";
	String				pwd_c				=	"pwd_4_C";
	HashMap<String, HashMap<String, String>>
						pref_c				=	new HashMap<String, HashMap<String, String>>();
	SimpleAccount		account_c			=	null;
	/*
	 * Account D : Defined with name, email and password.
	 * User-defined preferences
	 * Standard role
	 */
	String				name_d				=	"D";
	String				email_d				=	"D@helio.eu";
	String				pwd_d				=	"pwd_4_D";
	HashMap<String, HashMap<String, String>>
						pref_d				=	new HashMap<String, HashMap<String, String>>();
	SimpleAccount		account_d			=	null;
	/*
	 * Account admin : Defined with name, email and password.
	 * Standard preferences
	 * Standard and admin roles
	 */
	String				name_admin			=	"ADMIN";
	String				email_admin			=	"ADMIN@helio.eu";
	String				pwd_admin			=	"pwd_4_ADMIN";
	SimpleAccount		account_admin		=	null;
	/*
	 * Default accounts
	 */
	String				default_admin		=	HELIOUsers.HelioDefaultAdministrator;
	String				pwd_default_admin	=	"pwd4HelioAdministrator";
	
	@Before
	public void setUp() throws Exception 
	{
		if(deleteRepository)
			removeRepository();
		
		logUtilities.printShortLogEntry("SimpleAccountsTest.setUp()");
		accounts	=	new SimpleAccounts();
		updateUserPresence();
		logUtilities.printLongLogEntry(accounts.allDetailsAsString());			
		testUsersPresence();
		if(isRepositoryFilePresent())
		{
			logUtilities.printLongLogEntry("Repository file exhists !");			
		}
		else
		{
			logUtilities.printLongLogEntry("Repository file does not exhists ! Creating default accounts...");			
			addTestUsers();
			logUtilities.printLongLogEntry(accounts.allDetailsAsString());			
		}
		testUsersPresence();
		logUtilities.printLongLogEntry(accounts.allDetailsAsString());
	}

	private void updateUserPresence() 
	{
		presentUsers.addAll(accounts.getAllUserNames());
	}

	private void removeRepository() 
	{
		File	f	=	new File(statusFile);	
		f.delete();
	}

	private boolean isRepositoryFilePresent() 
	{
		File	f	=	new File(statusFile);	
		return	f.exists();
	}

	@After
	public void tearDown() throws Exception 
	{
		logUtilities.printShortLogEntry("SimpleAccountsTest.tearDown()");
	}

	@Ignore @Test
	public void testSimpleAccounts() 
	{
		logUtilities.printShortLogEntry("SimpleAccountsTest.testSimpleAccounts()");
		logUtilities.printLongLogEntry(accounts.allDetailsAsString());
		
		try 
		{
			assertTrue(accounts.validateUser(default_admin, pwd_default_admin));
		} 
		catch (AccountsException e) 
		{
			assertTrue(false);
		}
	}

	@Ignore @Test
	public void testAddUserStringString() 
	{
		String	name	=	"name";
		String	pwd		=	"password";
		
		logUtilities.printShortLogEntry("SimpleAccountsTest.testAddUserStringString()");
		logUtilities.printLongLogEntry(accounts.allDetailsAsString());
		try 
		{
			addUser(name, secUtilities.computeHashOf(pwd));
			logUtilities.printLongLogEntry(accounts.allDetailsAsString());
			testUsersPresence();
			removeUser(name);
			logUtilities.printLongLogEntry(accounts.allDetailsAsString());
			testUsersPresence();
		} 
		catch (AccountsException e) 
		{
			e.printStackTrace();
		} 
		catch (SecurityUtilitiesException e) 
		{
			e.printStackTrace();
		}
		logUtilities.printLongLogEntry(accounts.allDetailsAsString());
	}

	@Ignore @Test
	public void testAddUserStringStringString() 
	{
		String	name	=	"name";
		String	email	=	"name@helio.eu";
		String	pwd		=	"password";

		logUtilities.printShortLogEntry("SimpleAccountsTest.testAddUserStringStringString()");
		logUtilities.printLongLogEntry(accounts.allDetailsAsString());
		try 
		{
			addUser(name, email, secUtilities.computeHashOf(pwd));
			testUsersPresence();
			logUtilities.printLongLogEntry(accounts.allDetailsAsString());
			removeUser(name);
			testUsersPresence();
			logUtilities.printLongLogEntry(accounts.allDetailsAsString());
		} 
		catch (AccountsException e) 
		{
			e.printStackTrace();
		} 
		catch (SecurityUtilitiesException e) 
		{
			e.printStackTrace();
		}
		logUtilities.printLongLogEntry(accounts.allDetailsAsString());
	}

	@Ignore @Test
	public void testRemoveUser() 
	{
		logUtilities.printShortLogEntry("SimpleAccountsTest.testAddUserStringStringString()");
		logUtilities.printLongLogEntry(accounts.allDetailsAsString());
		try 
		{
			addTestUsers();			
			testUsersPresence();
			removeUser(name_a);
			testUsersPresence();
			removeUser(name_b);			
			testUsersPresence();
			/*
			 * Re-instate the test users
			 */
			addTestUsers();
		} 
		catch (AccountsException e) 
		{
			e.printStackTrace();
		} 
		logUtilities.printLongLogEntry(accounts.allDetailsAsString());
	}

	
	/*
	 * These are utilities used for testing
	 */
	private void removeUser(String name) throws AccountsException 
	{
		accounts.removeUser(name);
		presentUsers.remove(name);
		absentUsers.add(name);
	}
	
	private void addTestUsers() throws AccountsException 
	{
		if(!accounts.isUserPresent(name_a))
			addUser(name_a, pwd_a);
		if(!accounts.isUserPresent(name_b))
		addUser(name_b, email_b, pwd_b);
	}

	private void testUsersPresence() 
	{
		logUtilities.printLongLogEntry(accounts.allDetailsAsString());			
		logUtilities.printShortLogEntry("Testing present users: " + presentUsers);

		Iterator<String>	i	=	presentUsers.iterator();		
		while(i.hasNext())
		{
			String	currUser	=	i.next();
			assertTrue(accounts.isUserPresent(currUser));
		}

		logUtilities.printLongLogEntry("Testing absent users: " + absentUsers);

		i	=	absentUsers.iterator();		
		while(i.hasNext())
		{
			String	currUser	=	i.next();
			assertFalse(accounts.isUserPresent(currUser));
		}
	}


	private void addUser(String name, String email, String pwd) throws AccountsException 
	{
		accounts.addUser(name, email, pwd);
		presentUsers.add(name);
		if(absentUsers.contains(name))
			absentUsers.remove(name);
	}

	private void addUser(String name, String pwd) throws AccountsException 
	{
		accounts.addUser(name, pwd);
		presentUsers.add(name);		
		if(absentUsers.contains(name))
			absentUsers.remove(name);
	}


	@Ignore @Test
	public void testChangePwdHashFor() 
	{
		fail("Not yet implemented");
	}

	
//	@Test
//	public void testValidateUser() {
//		fail("Not yet implemented");
//	}

	@Ignore @Test
	public void testGetAllUserNames() 
	{
		fail("Not yet implemented");
	}

	@Ignore @Test
	public void testIsUserPresent() 
	{
		testUsersPresence();
	}

//
//	@Test
//	public void testLoadStatus() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSaveStatus() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testToString() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetRolesForUser() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetAllPreferencesForUser() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetPreferenceForUser() {
//		fail("Not yet implemented");
//	}

	@Ignore @Test
	public void testGetStandardPreferences() 
	{
		logUtilities.printShortLogEntry("SimpleAccountsTest.testGetStandardPreferences()");
		logUtilities.printLongLogEntry(accounts.allDetailsAsString());
		try 
		{
			logUtilities.printShortLogEntry(accounts.getStandardPreferences().toString());
		} 
		catch (AccountsException e) 
		{
			assertTrue(false);
		}		
	}

	@Ignore @Test
	public void testSetStandardPreference() 
	{
		logUtilities.printShortLogEntry("SimpleAccountsTest.testSetStandardPreferences()");
		logUtilities.printLongLogEntry(accounts.allDetailsAsString());

		/*
		 * Changing one value
		 */
		try 
		{
			String	newValue	=	"new-value";
			
			logUtilities.printShortLogEntry(accounts.getStandardPreferences().toString());
			accounts.setStandardPreference(DefaultPreferences.dpas_service, 
					DefaultPreferences.dpas_field_1, 
					newValue);
			logUtilities.printShortLogEntry(accounts.getStandardPreferences().toString());
			assertTrue(accounts.getStandardPreference(
					DefaultPreferences.dpas_service, 
					DefaultPreferences.dpas_field_1).equals(DefaultPreferences.dpas_value_1));
		} 
		catch (AccountsException e) 
		{
			assertTrue(false);
		}				
	}

	@Ignore @Test
	public void testGetStandardPreference() 
	{
		logUtilities.printShortLogEntry("SimpleAccountsTest.testSetStandardPreferences()");
		logUtilities.printLongLogEntry(accounts.allDetailsAsString());

		/*
		 * Testing an exhisting value
		 */
		try 
		{
			logUtilities.printShortLogEntry(accounts.getStandardPreferences().toString());
			assertTrue(accounts.getStandardPreference(
					DefaultPreferences.dpas_service, 
					DefaultPreferences.dpas_field_1).equals(DefaultPreferences.dpas_value_1));
		} 
		catch (AccountsException e) 
		{
			assertTrue(false);
		}				
		
		
		/*
		 * Testing a non-exhisting field
		 */
		try 
		{
			logUtilities.printShortLogEntry(accounts.getStandardPreferences().toString());
			assertFalse(accounts.getStandardPreference(
					DefaultPreferences.dpas_service, 
					"non-exhisting").equals(DefaultPreferences.dpas_value_1));
		} 
		catch (AccountsException e) 
		{
			assertTrue(true);
		}				

		/*
		 * Testing a non-exhisting service
		 */
		try 
		{
			logUtilities.printShortLogEntry(accounts.getStandardPreferences().toString());
			assertFalse(accounts.getStandardPreference(
					"non-exhisting-service", 
					DefaultPreferences.dpas_field_1).equals(DefaultPreferences.dpas_value_1));
		} 
		catch (AccountsException e) 
		{
			assertTrue(true);
		}				

		/*
		 * Testing a non-exhisting service and non-exhisting fields
		 */
		try 
		{
			logUtilities.printShortLogEntry(accounts.getStandardPreferences().toString());
			assertFalse(accounts.getStandardPreference(
					"non-exhisting-service", 
					"non-exhisting-field").equals(DefaultPreferences.dpas_value_1));
		} 
		catch (AccountsException e) 
		{
			assertTrue(true);
		}				
	}

//	@Test
//	public void testAddRoleToUser() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testRemoveRoleFromUser() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testRemoveStandardPreferenceString() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testRemoveStandardPreferenceStringString() {
//		fail("Not yet implemented");
//	}
}
