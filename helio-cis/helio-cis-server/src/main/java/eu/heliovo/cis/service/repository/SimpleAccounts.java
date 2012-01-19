package eu.heliovo.cis.service.repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import eu.heliovo.shared.cis.HELIORoles;
import eu.heliovo.shared.cis.HELIOUsers;
import eu.heliovo.shared.common.utilities.LogUtilities;

public class SimpleAccounts implements Accounts
{
	/*
	 * This map holds the information of all accounts
	 */
	HashMap<String, Account>		accounts		=	new HashMap<String, Account>();
	/*
	 * The standard users, this is loaded if the file is lost
	 */
	DefaultUsers					defUsers		=	new	DefaultUsers();			
	/*
	 * Various Utilities	 
	 */
	LogUtilities					logUtilities	=	new LogUtilities();
	/*
	 * File used to store the information	 
	 */
	String						statusFile		=	"/tmp/CIS.repository";
	File						f				=	new File(statusFile);
	
	public SimpleAccounts() 
	{
		super();
		logUtilities.printShortLogEntry(this.getClass(), "SimpleAccounts()");
		try 
		{
			if(f.exists())
				loadStatus();
			else
			{
				addStandardAccounts();
				saveStatus();
			}
		} 
		catch (AccountsException e) 
		{
			addStandardAccounts();
		}
		logUtilities.printLongLogEntry(allDetailsAsString());		
	}

	private void addStandardAccounts() 
	{
		logUtilities.printShortLogEntry(this.getClass(), "addStandardAccounts()");
		accounts.putAll(defUsers.getUsers());
	}

	@Override
	public void addUser(String name, String pwdHash) throws AccountsException
	{		
//		logUtilities.printShortLogEntry(this.getClass(), "addUser("+name+","+ pwdHash+")");
		
		if(accounts.containsKey(HELIOUsers.HelioStandardUser))
		{
			/*
			 * Make a copy of the preferences here ! 
			 */
			accounts.put(name, 
					new SimpleAccount(name, pwdHash, 
					copyStandardPreferences()));
			saveStatus();
		}
		else
			throw new AccountsException();
	}

	@Override
	public void addUser(String name, String email, String pwdHash) throws AccountsException
	{
//		logUtilities.printShortLogEntry(this.getClass(), "addUser("+name+","+email+","+ pwdHash+")");

		if(accounts.containsKey(HELIOUsers.HelioStandardUser))
		{
			accounts.put(name, 
					new SimpleAccount(name, email, pwdHash, 
							copyStandardPreferences()));
			saveStatus();
		}
		else
			throw new AccountsException();
	}

	@Override
	public void removeUser(String userName) throws AccountsException 
	{
//		logUtilities.printShortLogEntry(this.getClass(), "removeUser("+userName+")");
		
		/*
		 * You cannot remove the standard user
		 */
		if(userName.equals(HELIOUsers.HelioStandardUser))
			throw new AccountsException();
		
		if(!isUserPresent(userName))
			throw new AccountsException();
		else
		{
			accounts.remove(userName);
			saveStatus();
		}
	}
	
	@Override
	public void changePwdHashForUser(
			String name, 
			String oldPwdHash,
			String newPwdHash) throws AccountsException 
	{
		if(!accounts.containsKey(name))
			throw new AccountsException();
		else
		{
			accounts.get(name).setUserPwdHash(newPwdHash);
			saveStatus();
		}
	}

	@Override
	public boolean validateUser(String user, String pwdHash) throws AccountsException 
	{
		if(user == null || pwdHash == null)
			throw new AccountsException();
		
		if((user.length() == 0) || (pwdHash.length() == 0))
			throw new AccountsException();
		
		if(!isUserPresent(user))
			return false;
		else
		{
			return (pwdHash.equals(getPwdHashFor(user)));
		}
	}

	@Override
	public Set<String> getAllUserNames() 
	{
		return accounts.keySet();
	}

	@Override
	public boolean isUserPresent(String userName)
	{
//		System.out.println(accounts);
		return accounts.containsKey(userName);
	}

//	@Override
//	public String toString() 
//	{
//		return accounts.toString();
//	}

	@Override
	public Set<String> getRolesForUser(String user)
			throws AccountsException 
	{
		if(!accounts.containsKey(user))
			throw new AccountsException();
		/*
		 * Retrieve the preferences for the user
		 */
		return accounts.get(user).getUserRoles();
	}
	
	@Override
	public void addRoleToUser(String name, String role) throws AccountsException
	{
		if(!accounts.containsKey(name))
			throw new AccountsException();

		if(!HELIORoles.isValidRole(role))
			throw new AccountsException();
		else
			try 
			{
				accounts.get(name).addUserRole(HELIORoles.administrator);
				saveStatus();
			} 
			catch (AccountException e) 
			{
				e.printStackTrace();
				throw new AccountsException();
			}
	}

	@Override
	public void removeRoleFromUser(String user, String role) throws AccountsException 
	{
		if(!accounts.containsKey(user))
			throw new AccountsException();

		if(!HELIORoles.isValidRole(role))
			throw new AccountsException();
		else
		{
			accounts.get(user).removeUserRole(HELIORoles.administrator);		
			saveStatus();
		}
	}

	@Override
	public HashMap<String, HashMap<String, String>> getAllPreferencesForUser(String user)
			throws AccountsException 
	{
		if(!accounts.containsKey(user))
			throw new AccountsException();
		/*
		 * Retrieve the preferences for the user
		 */
		return accounts.get(user).getAllPreferences();
	}

	@Override
	public void setPreferenceForUser(
			String name, 
			String service, 
			String field,
			String value) throws AccountsException 
	{
		if(accounts.containsKey(name))
		{
			accounts.get(name).setPreferenceFor(service, field, value);
			saveStatus();
		}
		else
			throw new AccountsException();			
	}

	@Override
	public HashMap<String, HashMap<String, String>> getStandardPreferences()	throws AccountsException 
	{
		if(accounts.containsKey(HELIOUsers.HelioStandardUser))
		{
			return accounts.get(HELIOUsers.HelioStandardUser).getPreferences();
		}
		else
			throw new AccountsException();
	}

	@Override
	public void setStandardPreference(
			String service, 
			String field,
			String value) 
	{
		accounts.get(HELIOUsers.HelioStandardUser).setPreferenceFor(service, field, value);
		saveStatus();
	}
	

	@Override
	public void removeStandardPreference(
			String service, 
			String field) throws AccountsException 
	{
		if(accounts.containsKey(HELIOUsers.HelioStandardUser))
		{
			if(accounts.get(HELIOUsers.HelioStandardUser).
					getPreferences().keySet().contains(service))
			{
				if(accounts.get(HELIOUsers.HelioStandardUser).
						getPreferences().get(service).keySet().contains(field))
						{
							accounts.get(HELIOUsers.HelioStandardUser).
								getPreferences().get(service).remove(field);						}
						}
				else
					throw new AccountsException();				
			}
			else
				throw new AccountsException();				
	
		saveStatus();
	}
	
	public HashMap<String, HashMap<String, String>> copyStandardPreferences() throws AccountsException 
	{
		HashMap<String, HashMap<String, String>>	preferences	=	new HashMap<String, HashMap<String, String>>();
		
		if(accounts.containsKey(HELIOUsers.HelioStandardUser))
		{
			/*
			 * Cycle on all the services
			 */
			Iterator<String>	i	=	accounts.get(HELIOUsers.HelioStandardUser).getAllPreferences().keySet().iterator();
			while(i.hasNext())
			{
				String	currService	=	i.next();
				HashMap<String, String>	servicePreferences	=	new HashMap<String, String>();				
				/*
				 * Cycle on all the fields of the service
				 */
				Iterator<String>	j	=	accounts.get(HELIOUsers.HelioStandardUser).getAllPreferences().get(currService).keySet().iterator();
				while(j.hasNext())
				{
					String	currField	=	j.next();
					String	currValue	=	accounts.get(HELIOUsers.HelioStandardUser).getAllPreferences().get(currService).get(currField);
					servicePreferences.put(currField, currValue);
				}
				preferences.put(currService, servicePreferences);
			}
		}
		else
			throw new AccountsException();
		
		return preferences;
	}

	
	@Override
	public String getStandardPreference(String service, String field) throws AccountsException 
	{
		if(accounts.containsKey(HELIOUsers.HelioStandardUser))
		{
			if(accounts.get(HELIOUsers.HelioStandardUser).
					getPreferences().keySet().contains(service))
			{
				if(accounts.get(HELIOUsers.HelioStandardUser).
						getPreferences().get(service).keySet().contains(field))
						{
							return accounts.get(HELIOUsers.HelioStandardUser).
									getPreferences().get(service).get(field);
						}
				else
					throw new AccountsException();				
			}
			else
				throw new AccountsException();				
		}
		else
			throw new AccountsException();
	}


//	public void addRoleToUser(String user, String role) throws AccountsException
//	{
//		if(!accounts.containsKey(user))
//			throw new AccountsException();
//
//		if(!HELIORoles.isValidRole(role))
//			throw new AccountsException();
//		else
//			try 
//			{
//				accounts.get(user).addUserRoles(HELIORoles.administrator);
//				saveStatus();
//			} 
//			catch (AccountException e) 
//			{
//				e.printStackTrace();
//				throw new AccountsException();
//			}
//	}
//
//	public void removeRoleFromUser(String user, String role) throws AccountsException, AccountException 
//	{
//		if(!accounts.containsKey(user))
//			throw new AccountsException();
//
//		if(!HELIORoles.isValidRole(role))
//			throw new AccountsException();
//		else
//		{
//			accounts.get(user).removeUserRoles(HELIORoles.administrator);		
//			saveStatus();
//		}
//	}

	public void removeStandardPreference(String service) 
	{
		accounts.get(HELIOUsers.HelioStandardUser).getAllPreferences().remove(service);
		saveStatus();
	}


	public void loadStatus() throws AccountsException 
	{
		logUtilities.printShortLogEntry(this.getClass(), "loadStatus()");
	
		ObjectInputStream obj	=	null;
		try 
		{
			obj = new ObjectInputStream(new FileInputStream(statusFile));
			accounts = (HashMap<String, Account>)obj.readObject();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
			throw new AccountsException();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			throw new AccountsException();
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
			throw new AccountsException();
		}	
	}

	public void saveStatus()
	{
//		logUtilities.printShortLogEntry(this.getClass(), "saveStatus()");
		
		ObjectOutputStream obj	=	null;
		try 
		{
			obj = new ObjectOutputStream(new FileOutputStream(this.statusFile));
			obj.writeObject(accounts);
			obj.close();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public String	 	allDetailsAsString()
	{	
		String				result	=	new String();
		Iterator<String>	i		=	accounts.keySet().iterator();
		
		while(i.hasNext())
		{
			String	currUser	=	i.next();
			result	+=	accounts.get(currUser).allDetailsAsString();
			result	+=	"\n";
		}
		return result;
	}

	//	@Override
	//	public void changePwdHashFor(String user, String oldPwdHash,
	//			String newPwdHash) throws AccountsException 
	//	{
	//		if(!accounts.containsKey(user))
	//			throw new AccountsException();
	//		else
	//		{
	//			accounts.get(user).setUserPwdHash(newPwdHash);
	//			saveStatus();
	//		}
	//	}
	
		private String getPwdHashFor(String user) throws AccountsException 
		{
			if(!isUserPresent(user))
				throw new AccountsException();
			
			try 
			{
				return accounts.get(user).getUserPwdHash();
			} 
			catch (ArithmeticException e) 
			{
				e.printStackTrace();
				throw new AccountsException();
			} 
		}

		@Override
		public String getPreferenceForUser(
				String user, 
				String service,
				String field) throws AccountsException 
		{
			if(!isUserPresent(user))
				throw new AccountsException();
			
			if(!getAllPreferencesForUser(user).keySet().contains(service))
				throw new AccountsException();

			if(!getAllPreferencesForUser(user).get(service).keySet().contains(field))
				throw new AccountsException();
			
			return getAllPreferencesForUser(user).get(service).get(field);			
		}
}
