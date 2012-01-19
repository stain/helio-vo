package eu.heliovo.cis.service.repository;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

public interface Account extends Serializable
{
	public abstract void 		setUserName(String userName);
	public abstract String 		getUserName();
	
	public abstract void 		setUserPwdHash(String userPwdHash);
	public abstract String 		getUserPwdHash();
	
	public abstract void 		setUserEmail(String userEmail);
	public abstract String 		getUserEmail();
	
	public abstract Date 		getCreatedOn();

	public abstract void 		setUserRoles(Set<String> userRoles);
	public abstract	Set<String> getUserRoles();
	boolean hasRole(String role);
	public abstract void		addUserRole(String role) throws AccountException;
//	public abstract	void		addUserRoles(Set<String> userRoles);
	public abstract void		removeUserRole(String role);
//	public abstract void		removeUserRoles(Set<String> roles);	
		
//	public abstract HashMap<String, HashMap<String, String>> getAllPreferences();
	public abstract	String 		getPreferenceFor(String service, String field);
	public abstract	void 		setPreferenceFor(String service, String field, String value);
	
//	public abstract	Set<String> getAllServices();
//	public abstract Set<String> getAllFields(String service);
//	
//	public abstract void 		removeAllPreferencesFor(String service);
//	public abstract void 		removePreference(String service, String field);
//	
//	public abstract void 		removeInPreference(String service);
//	public abstract void 		removeInPreference(String service, String field);
	
	void setPreferences(HashMap<String, HashMap<String, String>> preferences);
	HashMap<String, HashMap<String, String>> getPreferences();
	
	//	
	//	public abstract Date 		getCreatedOn();
	//
	//	public abstract void 		setUserRoles(Set<String> userRoles);
	//	public abstract	Set<String> getUserRoles();
	//	public abstract void		addUserRole(String role) throws AccountException;
	//	public abstract	void		addUserRoles(Set<String> userRoles);
	//	public abstract void		removeUserRole(String role);
	//	public abstract void		removeUserRoles(Set<String> roles);	
	//		
		public abstract HashMap<String, HashMap<String, String>> getAllPreferences();
	//	public abstract	String 		getPreferenceFor(String service, String field);
	//	public abstract	void 		setPreferenceFor(String service, String field, String value);
	//	
	//	public abstract	Set<String> getAllServices();
	//	public abstract Set<String> getAllFields(String service);
	//	
	//	public abstract void 		removeAllPreferencesFor(String service);
	//	public abstract void 		removePreference(String service, String field);
	//	
	//	public abstract void 		removeInPreference(String service);
	//	public abstract void 		removeInPreference(String service, String field);
		
		public abstract String	 	allDetailsAsString();
}
