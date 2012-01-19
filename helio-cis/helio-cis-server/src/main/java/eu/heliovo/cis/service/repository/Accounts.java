package eu.heliovo.cis.service.repository;

import java.util.HashMap;
import java.util.Set;

public interface Accounts 
{
	/**
	 * Adds a user with a standard profile to the repository
	 *
	 * @param name 			the name of the user
	 * @param pwdHash 		the hash code of the password
	 * 
	 * @throws AccountsException 
	 */
	void addUser(String name, String pwdHash) throws AccountsException;
	/**
	 * Adds a user with a standard profile to the repository
	 *
	 * @param name 			the name of the user
	 * @param email 		the email of the user
	 * @param pwdHash 		the hash code of the password
	 * 
	 * @throws AccountsException 
	 */
	void addUser(String name, String email, String pwdHash)	throws AccountsException;
	/**
	 * Removes a user.
	 *
	 * @param name 	the name of the user
	 * @throws AccountsException 
	 */
	public abstract void removeUser(String name) throws AccountsException;
	
		
	/**
	 * Changes the password hash for the user.
	 *
	 * @param name the name of the user
	 * @param oldPwdHash the hash for the old password
	 * @param newPwdHash the hash for the new password
	 * 
	 * @throws HITRepositoryException 
	 */
	public abstract void changePwdHashForUser(
			String name, 
			String oldPwdHash,
			String newPwdHash) throws AccountsException;

	/**
	 * Returns a set with all the user names.
	 *
	 * @return Set<String> the set with all the user names.
	 * 
	 * @throws 	AccountsException 
	 */
	public abstract Set<String> getAllUserNames();

	/**
	 * Gets all the roles of the user
	 *
	 * @throws 	AccountsException 
	 */
	public abstract Set<String> getRolesForUser(String user) throws AccountsException;
	/**
	 * Checks if a user is present.
	 *
	 * @param name the name of the user
	 * 
	 * @return true, if the user is present
	 */
	public abstract boolean isUserPresent(String name);
	/**
	 * Validates the user
	 * 
	 * @param name 			the name of the user
	 * @param pwdHash 		the hash code of the password
	 * 
	 * @throws 	AccountsException 
	 */
	abstract boolean validateUser(String name, String pwdHash) throws AccountsException;
	/**
	 * Returns all the preferences for the user
	 *
	 * @throws 	AccountsException 
	 */
	abstract HashMap<String, HashMap<String, String>> getAllPreferencesForUser(String user) throws AccountsException;
	/**
	 * Returns the specified preferences for the user
	 * 
	 * @param name 			the name of the user
	 * @param service 		the service the preference of which must be changed
	 * @param field 		the field that must be changed
	 *
	 * @return The specified preference
	 * 
	 * @throws 	AccountsException 
	 */
	abstract String getPreferenceForUser(String user, String service, String field) throws AccountsException;
	/**
	 * Sets a specific preference field for the user
	 *
	 * @param name 			the name of the user
	 * @param pwdHash 		the hash code of the password
	 * @param service 		the service the preference of which must be changed
	 * @param field 		the field that must be changed
	 * @param value 		the new value
     * 
	 * @throws 	AccountsException 
	 */
	void setPreferenceForUser(String user, 
			String service, 
			String field,
			String value) throws AccountsException;
	/**
	 * Returns all the standard preferences
	 *
	 * @throws 	AccountsException 
	 */
	HashMap<String, HashMap<String, String>> getStandardPreferences() throws AccountsException;
	/**
	 * Sets a specific standard preference
	 *
	 * @throws 	AccountsException 
	 */
	void setStandardPreference(String service, String field, String value);
	/**
	 * Removes a specific standard preference
	 *
	 * @throws 	AccountsException 
	 */
	void removeStandardPreference(String service, String field) throws AccountsException;
	/**
	 * Returns the specified standard preferences
	 *
	 * @throws 	AccountsException 
	 */
	String getStandardPreference(String service, String field) throws AccountsException;
	/**
	 * Returns all user details as a string
	 */	
	String	 	allDetailsAsString();	
	/**
	 * Adds a role to a user
	 * 
	 * @param name 		the name of the user
	 * @param role 		the new value
     * 
	 * @throws 	AccountsException 
	 */		
	void addRoleToUser(String name, String role) throws AccountsException;
	/**
	 * Removes a role from a user
	 * 
	 * @param name 		the name of the user
	 * @param role 		the new value
     * 
	 * @throws 	AccountsException 
	 */		
	void removeRoleFromUser(String user, String role) throws AccountsException;
}
