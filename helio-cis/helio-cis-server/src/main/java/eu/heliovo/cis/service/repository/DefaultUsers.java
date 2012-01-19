package eu.heliovo.cis.service.repository;

import java.io.Serializable;
import java.util.HashMap;

import eu.heliovo.shared.cis.HELIORoles;
import eu.heliovo.shared.cis.HELIOUsers;

public class DefaultUsers implements Serializable
{
	private static final long serialVersionUID = 2761954027462207359L;
	/*
	 * This map holds the information on all default accounts
	 */
	private 	HashMap<String, Account>		users		=	new HashMap<String, Account>();

	public DefaultUsers() 
	{
		super();
//		System.out.println(users);
		/*
		 * Standard HELIO user - This is used to store all the standard information
		 */
		users.put(HELIOUsers.HelioStandardUser, 
				new SimpleAccount(HELIOUsers.HelioStandardUser, 
						"98f6bcd4621d373cade4e832627b4f6"));
//		System.out.println(users);
		/*
		 * Standard HELIO administrator
		 */
		SimpleAccount	account 	=	new SimpleAccount(
				HELIOUsers.HelioDefaultAdministrator, 
				"helio-admin@cs.tcd.ie", 
				"ef22451caa4aef9367977194c1178b64");
		try 
		{
			account.addUserRole(HELIORoles.administrator);
			users.put(HELIOUsers.HelioDefaultAdministrator, account);
		} 
		catch (AccountException e) 
		{
			e.printStackTrace();
		}		
//		System.out.println(users);
				

//		addUser(HELIOUsers.HelioDefaultAdministrator, "helio-admin@cs.tcd.ie", "7251428c2492edff4640cb0e9f1facce");
//		accounts.get(CISValues.HelioDefaultAdministrator).addUserRoles(UserRoles.administrator);
//		/*
//		 * Default HELIO user
//		 */
//		addUser(CISValues.HelioDefaultUser, "helio-user@cs.tcd.ie", "5c9a2c3c5c007a8b76b74072c6a2f989");
//		/*
//		 * Test HELIO user
//		 */
//		addUser(CISValues.HelioTestUser, "test-user@cs.tcd.ie", "98f6bcd4621d373cade4e832627b4f6");
		
	}

	public HashMap<String, Account> getUsers()
	{
		return users;
	}
}
