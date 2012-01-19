package eu.heliovo.cis.service.repository;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import eu.heliovo.shared.cis.HELIORoles;
import eu.heliovo.shared.common.utilities.LogUtilities;
import eu.heliovo.shared.common.utilities.SecurityUtilities;
import eu.heliovo.shared.common.utilities.SecurityUtilitiesException;

public class SimpleAccountTest 
{
	/*
	 * Utilities
	 */
	SecurityUtilities	secUtilities		=	new SecurityUtilities();
	LogUtilities		logUtilities		=	new LogUtilities();
	/*
	 * Default Preferences
	 */
	DefaultPreferences	defaultPreferences	=	new DefaultPreferences();
	/*
	 * Values of the default preferences
	 */
	String				dpas_service	=	"dpas";
	String				dpas_field_1	=	"dpas_field_1";
	String				dpas_value_1	=	"dpas_value_1";
	String				dpas_field_2	=	"dpas_field_2";
	String				dpas_value_2	=	"dpas_value_2";
	String				dpas_field_3	=	"dpas_field_3";
	String				dpas_value_3	=	"dpas_value_3";
	String				hfe_service		=	"hfe";
	String				hfe_field_1		=	"hfe_field_1";
	String				hfe_value_1		=	"hfe_value_1";
	String				hfe_field_2		=	"hfe_field_2";
	String				hfe_value_2		=	"hfe_value_2";
	String				hfe_field_3		=	"hfe_field_3";
	String				hfe_value_3		=	"hfe_value_3";

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
	
	String				dpas_service_c	=	"dpas_C";
	String				dpas_field_1_c	=	"dpas_field_1_C";
	String				dpas_value_1_c	=	"dpas_value_1_C";
	String				dpas_field_2_c	=	"dpas_field_2_C";
	String				dpas_value_2_c	=	"dpas_value_2_C";
	String				dpas_field_3_c	=	"dpas_field_3_C";
	String				dpas_value_3_c	=	"dpas_value_3_C";
	String				hfe_service_c	=	"hfe_C";
	String				hfe_field_1_c	=	"hfe_field_1_C";
	String				hfe_value_1_c	=	"hfe_value_1_C";
	String				hfe_field_2_c	=	"hfe_field_2_C";
	String				hfe_value_2_c	=	"hfe_value_2_C";
	String				hfe_field_3_c	=	"hfe_field_3_C";
	String				hfe_value_3_c	=	"hfe_value_3_C";

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
	
	String				dpas_service_d	=	"dpas_D";
	String				dpas_field_1_d	=	"dpas_field_1_D";
	String				dpas_value_1_d	=	"dpas_value_1_D";
	String				dpas_field_2_d	=	"dpas_field_2_D";
	String				dpas_value_2_d	=	"dpas_value_2_D";
	String				dpas_field_3_d	=	"dpas_field_3_D";
	String				dpas_value_3_d	=	"dpas_value_3_D";
	String				hfe_service_d	=	"hfe_D";
	String				hfe_field_1_d	=	"hfe_field_1_D";
	String				hfe_value_1_d	=	"hfe_value_1_D";
	String				hfe_field_2_d	=	"hfe_field_2_D";
	String				hfe_value_2_d	=	"hfe_value_2_D";
	String				hfe_field_3_d	=	"hfe_field_3_D";
	String				hfe_value_3_d	=	"hfe_value_3_D";

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
	
	
	@Before
	public void setUp() throws Exception 
	{
		logUtilities.printShortLogEntry("SimpleAccountTest.setUp()");
		/*
		 * Account A : Defined with name and password.
		 * Standard preferences
		 * Standard role
		 */
		account_a			=	new SimpleAccount(name_a, secUtilities.computeHashOf(pwd_a));
		/*
		 * Account B : Defined with name, email and password.
		 * Standard preferences
		 * Standard role
		 */
		account_b			=	new SimpleAccount(name_b, email_b, secUtilities.computeHashOf(pwd_b));
		/*
		 * Account C : Defined with name and password.
		 * User-defined preferences
		 * Standard role
		 */
		HashMap<String, String>	p	=	new HashMap<String, String>();		
		p.put(dpas_field_1_c, dpas_value_1_c);
		p.put(dpas_field_2_c, dpas_value_2_c);
		p.put(dpas_field_3_c, dpas_value_3_c);
		pref_c.put(dpas_service_c, p);	
		p	=	new HashMap<String, String>();		
		p.put(hfe_field_1_c, hfe_value_1_c);
		p.put(hfe_field_2_c, hfe_value_2_c);
		p.put(hfe_field_3_c, hfe_value_3_c);
		pref_c.put(hfe_service_c, p);
		account_c			=	new SimpleAccount(name_c, secUtilities.computeHashOf(pwd_c), pref_c);
		/*
		 * Account D : Defined with name, email and password.
		 * User-defined preferences
		 * Standard role
		 */
		String				dpas_service_d	=	"dpas_D";
		String				dpas_field_1_d	=	"dpas_field_1_D";
		String				dpas_value_1_d	=	"dpas_value_1_D";
		String				dpas_field_2_d	=	"dpas_field_2_D";
		String				dpas_value_2_d	=	"dpas_value_2_D";
		String				dpas_field_3_d	=	"dpas_field_3_D";
		String				dpas_value_3_d	=	"dpas_value_3_D";
		p	=	new HashMap<String, String>();		
		p.put(dpas_field_1_d, dpas_value_1_d);
		p.put(dpas_field_2_d, dpas_value_2_d);
		p.put(dpas_field_3_d, dpas_value_3_d);
		pref_d.put(dpas_service_d, p);	
		String				hfe_service_d	=	"hfe_D";
		String				hfe_field_1_d	=	"hfe_field_1_D";
		String				hfe_value_1_d	=	"hfe_value_1_D";
		String				hfe_field_2_d	=	"hfe_field_2_D";
		String				hfe_value_2_d	=	"hfe_value_2_D";
		String				hfe_field_3_d	=	"hfe_field_3_D";
		String				hfe_value_3_d	=	"hfe_value_3_D";
		p	=	new HashMap<String, String>();		
		p.put(hfe_field_1_d, hfe_value_1_d);
		p.put(hfe_field_2_d, hfe_value_2_d);
		p.put(hfe_field_3_d, hfe_value_3_d);
		pref_d.put(hfe_service_d, p);
		account_d			=	new SimpleAccount(name_d, email_d, secUtilities.computeHashOf(pwd_d), pref_d);
		/*
		 * Account admin : Defined with name, email and password.
		 * Standard preferences
		 * Standard and admin roles
		 */
		account_admin			=	new SimpleAccount(name_admin, email_admin, secUtilities.computeHashOf(pwd_admin));
		HashSet<String>	adminRoles	=	new HashSet<String>();
		adminRoles.add(HELIORoles.simpleUser);
		adminRoles.add(HELIORoles.administrator);
		account_admin.setUserRoles(adminRoles);
	}

	@After
	public void tearDown() throws Exception 
	{
	
	}

	@Ignore @Test
	public void testSimpleAccountStringString() 
	{
		logUtilities.printShortLogEntry("SimpleAccountTest.testSimpleAccountStringString()");
		try 
		{
			account_a	=	new	SimpleAccount(name_a, secUtilities.computeHashOf(pwd_a));
		} 
		catch (SecurityUtilitiesException e) 
		{
			e.printStackTrace();
			assertTrue(false);
		}
		testAccountWithStandardPreferences(account_a, name_a, pwd_a);
	}

	
	@Ignore @Test
	public void testSimpleAccountStringStringString() 
	{
		logUtilities.printShortLogEntry("SimpleAccountTest.testSimpleAccountStringStringString()");
		try 
		{
			account_b	=	new	SimpleAccount(name_b, email_b, secUtilities.computeHashOf(pwd_b));
		} 
		catch (SecurityUtilitiesException e) 
		{
			e.printStackTrace();
			assertTrue(false);
		}
		testAccountWithStandardPreferences(account_b, name_b, email_b, pwd_b);
	}

	@Ignore @Test
	public void testSimpleAccountStringStringHashMapOfStringHashMapOfStringString() 
	{
		logUtilities.printShortLogEntry("SimpleAccountTest.testSimpleAccountStringStringHashMapOfStringHashMapOfStringString()");
		try 
		{
			account_c	=	new	SimpleAccount(name_c, secUtilities.computeHashOf(pwd_c), pref_c);
		} 
		catch (SecurityUtilitiesException e) 
		{
			e.printStackTrace();
			assertTrue(false);
		}
		testAccountWithUserPreferences(account_c, name_c, pwd_c, pref_c);
	}

	@Ignore @Test
	public void testSimpleAccountStringStringStringHashMapOfStringHashMapOfStringString() 
	{
		logUtilities.printShortLogEntry("SimpleAccountTest.testSimpleAccountStringStringStringHashMapOfStringHashMapOfStringString()");
		try 
		{
			account_a	=	new	SimpleAccount(name_d, email_d, secUtilities.computeHashOf(pwd_d), pref_d);
		} 
		catch (SecurityUtilitiesException e) 
		{
			e.printStackTrace();
			assertTrue(false);
		}
		testAccountWithUserPreferences(account_d, name_d, pwd_d, email_d, pref_d);
	}

	private void testAccountWithStandardPreferences(
			SimpleAccount account, 
			String name,
			String pwd) 
	{
		logUtilities.printShortLogEntry(account.allDetailsAsString());
		assertNotNull(account);		
		assertTrue(name.equals(account.getUserName()));
		try 
		{
			assertTrue(secUtilities.computeHashOf(pwd).equals(account.getUserPwdHash()));
		} 
		catch (SecurityUtilitiesException e) 
		{
			e.printStackTrace();
			assertTrue(false);			
		}
		assertTrue(defaultPreferences.getPreferences().equals(account.getPreferences()));
	}

	private void testAccountWithStandardPreferences(
			SimpleAccount account,
			String name, 
			String email, 
			String pwd) 
	{
		testAccountWithStandardPreferences(account, name, pwd);
		assertTrue(email.equals(account.getUserEmail()));
	}

	private void testAccountWithUserPreferences(
			SimpleAccount account,
			String name, 
			String pwd,
			HashMap<String, HashMap<String, String>> pref) 
	{
		logUtilities.printShortLogEntry(account.getUserName() + " : " +  account.allDetailsAsString());
		assertNotNull(account);		
		assertTrue(name.equals(account.getUserName()));
		try 
		{
			assertTrue(secUtilities.computeHashOf(pwd).equals(account.getUserPwdHash()));
		} 
		catch (SecurityUtilitiesException e) 
		{
			e.printStackTrace();
			assertTrue(false);			
		}
		assertTrue(pref.equals(account.getPreferences()));		
	}
	
	private void testAccountWithUserPreferences(
			SimpleAccount account,
			String name, 
			String pwd,
			String email,
			HashMap<String, HashMap<String, String>> pref) 
	{
		logUtilities.printShortLogEntry(account.getUserName() + " : " +  account.allDetailsAsString());
		testAccountWithUserPreferences(account, name, pwd, pref);
		assertTrue(email.equals(account.getUserEmail()));		
	}

	@Ignore @Test
	public void testGetUserName() 
	{
		logUtilities.printShortLogEntry("SimpleAccountTest.testGetUserName()");
		
		logUtilities.printShortLogEntry(account_a.getUserName() + " : " +  account_a.getUserName());
		assertTrue(name_a.equals(account_a.getUserName()));
		logUtilities.printShortLogEntry(account_b.getUserName() + " : " +  account_b.getUserName());
		assertTrue(name_b.equals(account_b.getUserName()));
		logUtilities.printShortLogEntry(account_c.getUserName() + " : " +  account_c.getUserName());
		assertTrue(name_c.equals(account_c.getUserName()));
		logUtilities.printShortLogEntry(account_d.getUserName() + " : " +  account_d.getUserName());
		assertTrue(name_d.equals(account_d.getUserName()));
		
		logUtilities.printShortLogEntry(account_admin.getUserName() + " : " +  account_admin.getUserName());
		assertTrue(name_admin.equals(account_admin.getUserName()));
	}

	@Ignore @Test
	public void testSetUserName() 
	{
		logUtilities.printShortLogEntry("SimpleAccountTest.testSetUserName()");
		String	newName	=	"NEW";
		
		account_a.setUserName(newName);
		assertTrue(newName.equals(account_a.getUserName()));
		account_b.setUserName(newName);
		assertTrue(newName.equals(account_b.getUserName()));
		account_c.setUserName(newName);
		assertTrue(newName.equals(account_c.getUserName()));
		account_d.setUserName(newName);
		assertTrue(newName.equals(account_d.getUserName()));		
		account_admin.setUserName(newName);
		assertTrue(newName.equals(account_admin.getUserName()));		
	}

	@Ignore @Test
	public void testGetUserEmail() 
	{
		logUtilities.printShortLogEntry("SimpleAccountTest.testGetUserEmail()");

		logUtilities.printShortLogEntry(account_a.getUserName() + " : " +  account_a.getUserEmail());
		assertTrue(account_a.getUserEmail().equals("Undefined"));
		logUtilities.printShortLogEntry(account_b.getUserName() + " : " +  account_b.getUserEmail());
		assertTrue(account_b.getUserEmail().equals(email_b));
		logUtilities.printShortLogEntry(account_c.getUserName() + " : " +  account_c.getUserEmail());
		assertTrue(account_c.getUserEmail().equals("Undefined"));
		logUtilities.printShortLogEntry(account_d.getUserName() + " : " +  account_d.getUserEmail());
		assertTrue(account_d.getUserEmail().equals(email_d));

		logUtilities.printShortLogEntry(account_admin.getUserName() + " : " +  account_admin.getUserEmail());
		assertTrue(account_admin.getUserEmail().equals(email_admin));
	}

	@Ignore @Test
	public void testSetUserEmail() 
	{
		logUtilities.printShortLogEntry("SimpleAccountTest.testSetUserEmail()");
		String	newEmail	=	"new-email@helio.eu";
		
		account_a.setUserEmail(newEmail);
		logUtilities.printShortLogEntry(account_a.getUserName() + " : " +  account_a.getUserEmail());
		assertTrue(newEmail.equals(account_a.getUserName()));
		account_b.setUserEmail(newEmail);
		logUtilities.printShortLogEntry(account_a.getUserName() + " : " +  account_a.getUserEmail());
		assertTrue(newEmail.equals(account_b.getUserName()));
		account_c.setUserEmail(newEmail);
		logUtilities.printShortLogEntry(account_a.getUserName() + " : " +  account_a.getUserEmail());
		assertTrue(newEmail.equals(account_c.getUserName()));
		account_d.setUserEmail(newEmail);
		logUtilities.printShortLogEntry(account_a.getUserName() + " : " +  account_a.getUserEmail());
		assertTrue(newEmail.equals(account_d.getUserName()));		
		account_admin.setUserEmail(newEmail);
		logUtilities.printShortLogEntry(account_a.getUserName() + " : " +  account_a.getUserEmail());
		assertTrue(newEmail.equals(account_admin.getUserName()));		
	}

	@Ignore @Test
	public void testGetUserPwdHash() 
	{
		logUtilities.printShortLogEntry("SimpleAccountTest.testGetUserPwdHash()");

		try 
		{
			logUtilities.printShortLogEntry(account_a.getUserName() + " : " +  account_a.getUserPwdHash());
			assertTrue(account_a.getUserPwdHash().equals(secUtilities.computeHashOf(pwd_a)));
			logUtilities.printShortLogEntry(account_b.getUserName() + " : " +  account_b.getUserPwdHash());
			assertTrue(account_b.getUserPwdHash().equals(secUtilities.computeHashOf(pwd_b)));
			logUtilities.printShortLogEntry(account_c.getUserName() + " : " +  account_c.getUserPwdHash());
			assertTrue(account_c.getUserPwdHash().equals(secUtilities.computeHashOf(pwd_c)));
			logUtilities.printShortLogEntry(account_d.getUserName() + " : " +  account_d.getUserPwdHash());
			assertTrue(account_d.getUserPwdHash().equals(secUtilities.computeHashOf(pwd_d)));

			logUtilities.printShortLogEntry(account_admin.getUserName() + " : " +  account_admin.getUserPwdHash());
			assertTrue(account_admin.getUserPwdHash().equals(secUtilities.computeHashOf(pwd_admin)));
		} 
		catch (SecurityUtilitiesException e) 
		{
			e.printStackTrace();
		}
	}

	@Ignore @Test
	public void testSetUserPwdHash() 
	{
		logUtilities.printShortLogEntry("SimpleAccountTest.testSetUserPwdHash()");
		String	newPwd	=	"newpassword";
		
		try 
		{
			account_a.setUserPwdHash(secUtilities.computeHashOf(newPwd));
			logUtilities.printShortLogEntry(account_a.getUserName() + " : " +  account_a.getUserPwdHash());
			assertTrue(account_a.getUserPwdHash().equals(secUtilities.computeHashOf(newPwd)));
			account_b.setUserPwdHash(secUtilities.computeHashOf(newPwd));
			logUtilities.printShortLogEntry(account_b.getUserName() + " : " +  account_b.getUserPwdHash());
			assertTrue(account_b.getUserPwdHash().equals(secUtilities.computeHashOf(newPwd)));
			account_c.setUserPwdHash(secUtilities.computeHashOf(newPwd));
			logUtilities.printShortLogEntry(account_c.getUserName() + " : " +  account_c.getUserPwdHash());
			assertTrue(account_c.getUserPwdHash().equals(secUtilities.computeHashOf(newPwd)));
			account_d.setUserPwdHash(secUtilities.computeHashOf(newPwd));
			logUtilities.printShortLogEntry(account_d.getUserName() + " : " +  account_d.getUserPwdHash());
			assertTrue(account_d.getUserPwdHash().equals(secUtilities.computeHashOf(newPwd)));

			account_admin.setUserPwdHash(secUtilities.computeHashOf(newPwd));
			logUtilities.printShortLogEntry(account_admin.getUserName() + " : " +  account_admin.getUserPwdHash());
			assertTrue(account_admin.getUserPwdHash().equals(secUtilities.computeHashOf(newPwd)));
		} 
		catch (SecurityUtilitiesException e) 
		{
			e.printStackTrace();
		}

	}

	@Ignore @Test
	public void testGetCreatedOn() 
	{
		logUtilities.printShortLogEntry("SimpleAccountTest.testGetCreatedOn()");

		logUtilities.printShortLogEntry(account_a.getUserName() + " : " +  account_a.getCreatedOn().toString());
		logUtilities.printShortLogEntry(account_b.getUserName() + " : " +  account_b.getCreatedOn().toString());
		logUtilities.printShortLogEntry(account_c.getUserName() + " : " +  account_c.getCreatedOn().toString());
		logUtilities.printShortLogEntry(account_d.getUserName() + " : " +  account_d.getCreatedOn().toString());

		logUtilities.printShortLogEntry(account_admin.getUserName() + " : " +  account_admin.getCreatedOn().toString());
	}

	@Ignore @Test
	public void testAddUserRole() 
	{
		logUtilities.printShortLogEntry("SimpleAccountTest.testAddUserRole()");

		try 
		{
			account_a.addUserRole(HELIORoles.administrator);
			assertTrue(account_a.getUserRoles().contains(HELIORoles.administrator));
			account_b.addUserRole(HELIORoles.administrator);
			assertTrue(account_b.getUserRoles().contains(HELIORoles.administrator));
			account_c.addUserRole(HELIORoles.administrator);
			assertTrue(account_c.getUserRoles().contains(HELIORoles.administrator));
			account_d.addUserRole(HELIORoles.administrator);
			assertTrue(account_d.getUserRoles().contains(HELIORoles.administrator));
		} 
		catch (AccountException e) 
		{
			e.printStackTrace();
			assertTrue(false);
		}
	}

//	@Ignore @Test
//	public void testAddUserRoles() 
//	{
//		logUtilities.printShortLogEntry("SimpleAccountTest.testAddUserRole()");
//
//		try 
//		{
//			account_a.addUserRole(UserRoles.administrator);
//			assertTrue(account_a.getUserRoles().contains(UserRoles.administrator));
//			account_b.addUserRole(UserRoles.administrator);
//			assertTrue(account_b.getUserRoles().contains(UserRoles.administrator));
//			account_c.addUserRole(UserRoles.administrator);
//			assertTrue(account_c.getUserRoles().contains(UserRoles.administrator));
//			account_d.addUserRole(UserRoles.administrator);
//			assertTrue(account_d.getUserRoles().contains(UserRoles.administrator));
//		} 
//		catch (AccountException e) 
//		{
//			e.printStackTrace();
//			assertTrue(false);
//		}
//	}

	@Ignore @Test
	public void testRemoveUserRole() 
	{
		logUtilities.printShortLogEntry("SimpleAccountTest.testAddUserRole()");

		account_admin.removeUserRole(HELIORoles.administrator);
		assertFalse(account_admin.getUserRoles().contains(HELIORoles.administrator));
	}

//	@Ignore @Test
//	public void testRemoveUserRoles() 
//	{
//		fail("Not yet implemented");
//	}

	@Ignore @Test
	public void testSetUserRoles() 
	{
		logUtilities.printShortLogEntry("SimpleAccountTest.testSetUserEmail()");

		HashSet<String>	testRoles	=	new HashSet<String>();
		testRoles.add(HELIORoles.simpleUser);
		testRoles.add(HELIORoles.administrator);

		account_a.setUserRoles(testRoles);
		assertTrue(testRoles.equals(account_a.getUserRoles()));
		account_b.setUserRoles(testRoles);
		assertTrue(testRoles.equals(account_b.getUserRoles()));
		account_c.setUserRoles(testRoles);
		assertTrue(testRoles.equals(account_c.getUserRoles()));
		account_d.setUserRoles(testRoles);
		assertTrue(testRoles.equals(account_d.getUserRoles()));

		account_admin.setUserRoles(testRoles);
		assertTrue(testRoles.equals(account_admin.getUserRoles()));
	}

	@Ignore @Test
	public void testGetUserRoles() 
	{
		logUtilities.printShortLogEntry("SimpleAccountTest.testGetUserRoles()");
		HashSet<String>	simpleRoles	=	new HashSet<String>();
		simpleRoles.add(HELIORoles.simpleUser);
		HashSet<String>	adminRoles	=	new HashSet<String>();
		adminRoles.add(HELIORoles.simpleUser);
		adminRoles.add(HELIORoles.administrator);
		
		logUtilities.printShortLogEntry(account_a.getUserName() + " : " +  account_a.getUserRoles());
		assertTrue(simpleRoles.equals(account_a.getUserRoles()));
		logUtilities.printShortLogEntry(account_b.getUserName() + " : " +  account_b.getUserRoles());
		assertTrue(simpleRoles.equals(account_b.getUserRoles()));
		logUtilities.printShortLogEntry(account_c.getUserName() + " : " +  account_c.getUserRoles());
		assertTrue(simpleRoles.equals(account_c.getUserRoles()));
		logUtilities.printShortLogEntry(account_d.getUserName() + " : " +  account_d.getUserRoles());
		assertTrue(simpleRoles.equals(account_d.getUserRoles()));
		
		logUtilities.printShortLogEntry(account_admin.getUserName() + " : " +  account_admin.getUserRoles());
		assertTrue(adminRoles.equals(account_admin.getUserRoles()));
	}

	@Ignore @Test
	public void testHasRole() 
	{
		logUtilities.printShortLogEntry("SimpleAccountTest.testHasRole()");

		HashSet<String>	simpleRoles	=	new HashSet<String>();
		simpleRoles.add(HELIORoles.simpleUser);
		HashSet<String>	adminRoles	=	new HashSet<String>();
		adminRoles.add(HELIORoles.simpleUser);
		adminRoles.add(HELIORoles.administrator);
		
		logUtilities.printShortLogEntry(account_a.getUserName() + " : " +  account_a.getUserRoles());
		assertTrue(account_a.hasRole(HELIORoles.simpleUser));
		assertFalse(account_a.hasRole(HELIORoles.administrator));
		logUtilities.printShortLogEntry(account_b.getUserName() + " : " +  account_b.getUserRoles());
		assertTrue(account_b.hasRole(HELIORoles.simpleUser));
		assertFalse(account_b.hasRole(HELIORoles.administrator));
		logUtilities.printShortLogEntry(account_c.getUserName() + " : " +  account_c.getUserRoles());
		assertTrue(account_c.hasRole(HELIORoles.simpleUser));
		assertFalse(account_c.hasRole(HELIORoles.administrator));
		logUtilities.printShortLogEntry(account_d.getUserName() + " : " +  account_d.getUserRoles());
		assertTrue(account_d.hasRole(HELIORoles.simpleUser));
		assertFalse(account_d.hasRole(HELIORoles.administrator));

		logUtilities.printShortLogEntry(account_admin.getUserName() + " : " +  account_admin.getUserRoles());
		assertTrue(account_admin.hasRole(HELIORoles.simpleUser));
		assertTrue(account_admin.hasRole(HELIORoles.administrator));
	}

//	@Test
//	public void testToString() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testPrintAllDetails() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetAllPreferences() {
//		fail("Not yet implemented");
//	}

	@Ignore @Test
	public void testGetPreferenceFor() 
	{
		logUtilities.printShortLogEntry("SimpleAccountTest.testGetPreferenceFor()");

		assertTrue(account_a.getPreferenceFor(dpas_service, dpas_field_1).equals(dpas_value_1));
		assertTrue(account_a.getPreferenceFor(dpas_service, dpas_field_2).equals(dpas_value_2));
		assertTrue(account_a.getPreferenceFor(dpas_service, dpas_field_3).equals(dpas_value_3));
		assertTrue(account_a.getPreferenceFor(hfe_service, hfe_field_1).equals(hfe_value_1));
		assertTrue(account_a.getPreferenceFor(hfe_service, hfe_field_2).equals(hfe_value_2));
		assertTrue(account_a.getPreferenceFor(hfe_service, hfe_field_3).equals(hfe_value_3));

		assertTrue(account_b.getPreferenceFor(dpas_service, dpas_field_1).equals(dpas_value_1));
		assertTrue(account_b.getPreferenceFor(dpas_service, dpas_field_2).equals(dpas_value_2));
		assertTrue(account_b.getPreferenceFor(dpas_service, dpas_field_3).equals(dpas_value_3));
		assertTrue(account_b.getPreferenceFor(hfe_service, hfe_field_1).equals(hfe_value_1));
		assertTrue(account_b.getPreferenceFor(hfe_service, hfe_field_2).equals(hfe_value_2));
		assertTrue(account_b.getPreferenceFor(hfe_service, hfe_field_3).equals(hfe_value_3));

		assertTrue(account_c.getPreferenceFor(dpas_service_c, dpas_field_1_c).equals(dpas_value_1_c));
		assertTrue(account_c.getPreferenceFor(dpas_service_c, dpas_field_2_c).equals(dpas_value_2_c));
		assertTrue(account_c.getPreferenceFor(dpas_service_c, dpas_field_3_c).equals(dpas_value_3_c));
		assertTrue(account_c.getPreferenceFor(hfe_service_c, hfe_field_1_c).equals(hfe_value_1_c));
		assertTrue(account_c.getPreferenceFor(hfe_service_c, hfe_field_2_c).equals(hfe_value_2_c));
		assertTrue(account_c.getPreferenceFor(hfe_service_c, hfe_field_3_c).equals(hfe_value_3_c));

		assertTrue(account_d.getPreferenceFor(dpas_service_d, dpas_field_1_d).equals(dpas_value_1_d));
		assertTrue(account_d.getPreferenceFor(dpas_service_d, dpas_field_2_d).equals(dpas_value_2_d));
		assertTrue(account_d.getPreferenceFor(dpas_service_d, dpas_field_3_d).equals(dpas_value_3_d));
		assertTrue(account_d.getPreferenceFor(hfe_service_d, hfe_field_1_d).equals(hfe_value_1_d));
		assertTrue(account_d.getPreferenceFor(hfe_service_d, hfe_field_2_d).equals(hfe_value_2_d));
		assertTrue(account_d.getPreferenceFor(hfe_service_d, hfe_field_3_d).equals(hfe_value_3_d));

		assertTrue(account_admin.getPreferenceFor(dpas_service, dpas_field_1).equals(dpas_value_1));
		assertTrue(account_admin.getPreferenceFor(dpas_service, dpas_field_2).equals(dpas_value_2));
		assertTrue(account_admin.getPreferenceFor(dpas_service, dpas_field_3).equals(dpas_value_3));
		assertTrue(account_admin.getPreferenceFor(hfe_service, hfe_field_1).equals(hfe_value_1));
		assertTrue(account_admin.getPreferenceFor(hfe_service, hfe_field_2).equals(hfe_value_2));
		assertTrue(account_admin.getPreferenceFor(hfe_service, hfe_field_3).equals(hfe_value_3));
	}

	@Ignore @Test
	public void testSetPreferenceFor() 
	{
		logUtilities.printShortLogEntry("SimpleAccountTest.testSetPreferenceFor()");

		String	newValue	=	"new-value";
		/*
		 * Try to change one field
		 */
		account_a.setPreferenceFor(dpas_service, dpas_field_1, newValue);
		assertTrue(account_a.getPreferenceFor(dpas_service, dpas_field_1).equals(newValue));
	}

//	@Test
//	public void testGetAllServices() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetAllFields() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testRemoveAllPreferencesFor() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testRemovePreference() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testRemoveInPreferenceString() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testRemoveInPreferenceStringString() {
//		fail("Not yet implemented");
//	}

}
