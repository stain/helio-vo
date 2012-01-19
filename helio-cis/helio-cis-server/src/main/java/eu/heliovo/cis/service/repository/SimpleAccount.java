package eu.heliovo.cis.service.repository;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import eu.heliovo.shared.cis.HELIORoles;

public class SimpleAccount implements Account
{
	private static final long serialVersionUID = 7733267965849087419L;
	/*
	 * This is the basic information for the user
	 */
	protected 	String 			userName 			= 	"Undefined";
	protected 	String 			userPwdHash			= 	"Undefined";
	protected 	String 			userEmail 			= 	"Undefined";
	/*
	 * This is the spring-required information for the account
	 */
	protected	boolean 		enabled				=	false;
	protected	boolean 		accountExpired		=	true;
	protected	boolean 		accountLocked		=	true;
	protected	boolean 		passwordExpired		=	true;
	/*
	 * This is the helio-specific information for the user and account
	 */
	/*
	 * Date in which the account was created
	 */
	protected 	Date 				createdOn 			= 	null;
	/*
	 * Preferences
	 */	
	protected	HashMap<String, HashMap<String, String>>	
									preferences			=	new HashMap<String, HashMap<String, String>>();
	protected	DefaultPreferences	defaultPreferences	=	new DefaultPreferences();
	/*
	 * Roles assigned to the user
	 */		
	protected	Set<String>			userRoles			=	new HashSet<String>();
	
	public SimpleAccount(
			String userName, 
			String userPwdHash) 
	{
		super();
		initialize();		
		setUserName(userName);
		setUserPwdHash(userPwdHash);
		setStandardPreferences();
	}
	
	public SimpleAccount(
			String userName, 
			String userPwdHash,
			HashMap<String,HashMap<String,String>> userPreferences) 
	{
		super();
		initialize();		
		setUserName(userName);
		setUserPwdHash(userPwdHash);
		setPreferences(userPreferences);
	}
	
	public SimpleAccount(
			String userName, 
			String userEmail,
			String userPwdHash) 
	{
		super();
		initialize();		
		setUserName(userName);
		setUserEmail(userEmail);
		setUserPwdHash(userPwdHash);
		setStandardPreferences();
	}

	public SimpleAccount(
			String userName, 
			String userEmail,
			String userPwdHash,
			HashMap<String, HashMap<String, String>> userPreferences) 
	{
		super();
		initialize();		
		setUserName(userName);
		setUserEmail(userEmail);
		setUserPwdHash(userPwdHash);
		setPreferences(userPreferences);
	}

	private void initialize() 
	{
		this.createdOn		=	new Date();
		try 
		{
			addRole(HELIORoles.simpleUser);
		} 
		catch (AccountException e) 
		{
			e.printStackTrace();
		}		
		this.enabled			=	true;
		this.accountLocked		=	false;
		this.passwordExpired	=	false;
	}

	/*
	 * Getters and setters
	 */
	@Override
	public String getUserName() {
		return userName;
	}
	@Override
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Override
	public String getUserEmail() 
	{
		return userEmail;
	}
	@Override
	public void setUserEmail(String userEmail) 
	{
		this.userEmail = userEmail;
	}
	@Override
	public String getUserPwdHash() {
		return userPwdHash;
	}
	@Override
	public void setUserPwdHash(String userPwdHash) {
		this.userPwdHash = userPwdHash;
	}
	
	@Override
	public Date getCreatedOn() 
	{
		return createdOn;
	}

	@Override
	public void addUserRole (String role) throws AccountException 
	{
		try 
		{
			addRole(role);
		} 
		catch (AccountException e) 
		{
			e.printStackTrace();
			throw new AccountException();
		}
	}

//	@Override
//	public void addUserRoles(Set<String> userRoles) 
//	{
//		Iterator<String>	i	=	userRoles.iterator();
//		
//		while(i.hasNext())
//			try 
//			{
//				addRole(i.next());
//			} 
//			catch (AccountException e) 
//			{
//				e.printStackTrace();
//			}
//	}

	@Override
	public void removeUserRole(String role) 
	{
		userRoles.remove(role);
	}

//	@Override
//	public void removeUserRoles(Set<String> roles) 
//	{	
//		userRoles.removeAll(roles);
//	}

	@Override
	public void setUserRoles(Set<String> userRoles) 
	{
		this.userRoles = userRoles;
	}

	@Override
	public Set<String> getUserRoles() 
	{
		return userRoles;
	}

	@Override
	public boolean hasRole(String role) 
	{
		return userRoles.contains(role);
	}
	private void addRole(String role) throws AccountException 
	{
		if(!HELIORoles.isValidRole(role))
			throw new AccountException();
		else
			userRoles.add(role);
	}
	
	@Override
	public void setPreferences(HashMap<String, HashMap<String, String>> preferences) 
	{
		this.preferences = preferences;
	}

	private void setStandardPreferences() 
	{
		setPreferences(defaultPreferences.getPreferences());
	}

	@Override
	public HashMap<String, HashMap<String, String>> getPreferences() 
	{
		return this.preferences;
	}

	@Override
	public String toString() 
	{
		return "["+userName+","+userPwdHash+","+userEmail+","+userRoles+"]";
	}
	
	@Override
	public String allDetailsAsString() 
	{
//		return "["+userName+","
//				+userPwdHash+","
//				+userEmail+","
//				+enabled+","
//				+accountExpired+","
//				+accountLocked+","
//				+passwordExpired+","
//				+createdOn+","
//				+preferences+","
//				+userRoles+","
//				+"]";
		
		return 	"["
		+userName+","
		+userEmail+","
		+userRoles+","
		+userPwdHash+","
		+createdOn+","
		+preferences+"]";
	}

	@Override
	public HashMap<String, HashMap<String, String>> getAllPreferences() 
	{
		return preferences;
	}

	@Override
	public String getPreferenceFor(String service, String field)
	{
		return	preferences.get(service).get(field);
	}

	@Override
	public void setPreferenceFor(String service, String field, String value) 
	{
		if(preferences.containsKey(service))
			preferences.get(service).put(field, value);
		else
		{
			HashMap<String, String>	map	=	new HashMap<String, String>();
			map.put(field, value);
			preferences.put(service, map);
		}
	}

//	@Override
//	public Set<String> getAllServices() 
//	{
//		return preferences.getAllServices();
//	}
//
//	@Override
//	public Set<String> getAllFields(String service) 
//	{
//		return preferences.getAllFields(service);
//	}
//
//	@Override
//	public void removeAllPreferencesFor(String service) 
//	{
//		preferences.removeAllPreferencesFor(service);
//	}
//
//	@Override
//	public void removePreference(String service, String field) 
//	{
//		preferences.removePreference(service, field);
//	}
//
//	@Override
//	public void removeInPreference(String service) 
//	{
//		preferences.removeAllPreferencesFor(service);		
//	}
//
//	@Override
//	public void removeInPreference(String service, String field) 
//	{
//		preferences.removePreference(service, field);		
//	}
}
