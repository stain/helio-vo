package eu.heliovo.cis.service;

import java.util.HashMap;
import java.util.Set;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import eu.heliovo.cis.service.repository.AccountsException;

@WebService
public interface CisService 
{
	/**
	 * A simple test to see if the service is up and running
	 *
	 * @param a string parameter
	 */
	@WebMethod
	@WebResult(name="testResult")
    public String test(@WebParam(name="parameter") String parameter); 

	/**
	 * Validate user.
	 *
	 * @param user the user
	 * @param pwd the pwd
	 * @return true, if successful
	 */

	@WebMethod
	@WebResult(name="validateUserResult")
	public	boolean	validateUser(String name, String pwdHash);

	
	/**
	 * Adds a user.
	 *
	 * @param name the name of the user
	 * @param pwdHash the hash of the password for the user
     *
	 * @throws CisServiceException 
	 */
	@WebMethod
	public void addUser(String name, String pwdHash) throws CisServiceException;
	/**
	 * Adds a user with an email.
	 *
	 * @param name the name of the user
	 * @param email the email of the user
	 * @param pwdHash the hash of the password for the user
     *
	 * @throws CisServiceException 
	 */
	@WebMethod
	public void addUserWithEmail(String name, String email, String pwdHash) throws CisServiceException;
	/**
	 * Removes the user.
	 *
	 * @param name the name of the user
	 * @param pwdHash the hash of the password for the user
	 * 
	 * @throws CisServiceException 
	 */
	@WebMethod
	public void removeUser(String name, String pwdHash) throws CisServiceException;
	/**
	 * Removes another user (only for administrators).
	 *
	 * @param user the name of the user to be removed
	 * @param requester the name of the administrator who requests the operation
	 * @param requesterPwdHash the hash of the password for the administrator who requests the operation
	 * 
	 * @throws CisServiceException 
	 */
	@WebMethod
	public void removeAnotherUser(String user, String requester, String requesterPwdHash)
	throws CisServiceException;

	/**
	 * Returns true if the user defined by name is present.
	 *
	 * @param name the name of the user
	 * 
	 * @return true, if the user is present, false otherwise
	 * @throws CisServiceException 
	 */
	@WebMethod
	@WebResult(name="isUserPresentResult")
	public boolean isUserPresent(String name);
	
	
	/**
	 * Changes the password of a user.
	 * 
	 * @param name the name of the user
	 * @param oldPwd the oldPw. Will be hashed by this implementation.
	 * @param newPwd the newPw. Will be hashed by this implementation.
     *
	 * @throws CisClientException if the oldPwd does not match.
	 */
	@WebMethod
	public void changePwdHashForUser(String name, String oldPwdHash, String newPwdHash) throws CisServiceException;
	/**
	 * Gets the preference for the user.
	 *
	 * @param user the user
	 * @param service the service 
	 * @param field the field 
	 * 
	 * @return The value of the preferences
	 * @throws CisServiceException 
	 */
	@WebMethod
	@WebResult(name="getPreferenceForUserResult")
	public String getPreferenceForUser(String user, String service, String field) throws CisServiceException;
	/**
	 * Sets the preference for the user.
	 *
	 * @param name the name of the user
	 * @param pwdHash the password hash for the user
	 * @param service the service 
	 * @param field the field 
	 * @param value the value 
	 * 
	 * @throws CisServiceException 
	 */
	@WebMethod
	void setPreferenceForUser(String name, String pwdHash, String service,
			String field, String value) throws CisServiceException;

	/**
	 * Sets the standard preference.
	 *
	 * @param user the user
	 * @param pwd the password for the user
	 * @param service the service 
	 * @param field the field 
	 * @param value the value 
	 * 
	 * @throws CisServiceException 
	 * @throws AccountsException 
	 */
	@WebMethod
	public void setStandardPreference(String userName, String computeHashOf,
	String prefService, String prefField, String prefValue) throws CisServiceException;

	/**
	 * Removes an entire service from the standard preference.
	 *
	 * @param user the user
	 * @param pwd the password for the user
	 * @param service the service 
	 * 
	 * @throws CisServiceException 
	 */
	public void removeServiceInStandardPreference(String userName, String computeHashOf,
			String prefService) throws CisServiceException;

	/**
	 * Removes a field from the standard preference.
	 *
	 * @param user the user
	 * @param pwd the password for the user
	 * @param service the service 
	 * @param field the field 
	 * 
	 * @throws CisServiceException 
	 */
	public void removeFieldInStandardPreference(String userName, String computeHashOf,
			String prefService, String prefField) throws CisServiceException;

	
	
	
	/**
	 * Gets the roles for the user.
	 *
	 * @param name the name of the user
	 * 
	 * @return The set of roles given to the user
	 * 
	 * @throws CisServiceException 
	 */
	@WebMethod
	public Set<String> getRolesForUser(String name) throws CisServiceException;

	/**
	 * Validates the user given a certain role.
	 *
	 * @param name the name of the user
	 * @param pwdHash the hash of the password
	 * @param role the role of the user we want to validate
	 * 
	 * @return true if the user is validated for that role
	 * 
	 * @throws CisServiceException 
	 */
	@WebMethod
	public boolean validateUserAndRole(
			String name, 
			String pwdHash,
			String role) 
					throws CisServiceException;

	
	/**
	 * Returns all the preferences for the specified user.
	 *
	 * @param name the name of the user
	 * 
	 * @return The preferences for that user
	 * @throws CisServiceException 
	 */
	@WebMethod
	public HashMap<String, HashMap<String, String>> getAllPreferencesForUser(String name) throws CisServiceException;

	/**
	 * Returns all the standard preferences.
	 *	 
	 * @return All the standard preferences
	 * @throws CisServiceException 
	 */
	@WebMethod
	public HashMap<String, HashMap<String, String>> getAllStandardPreferences() throws CisServiceException;

	
	/**
	 * Return all names of users.
	 * 
	 * @return The names of all users
	 */
	@WebMethod
	public Set<String> getAllUserNames();
	
	/**
	 * Return all user's names with the specified role
	 * 
	 * @param role the role of the users 
	 *
	 * @return The names of all users with role role
	 * @throws CisServiceException 
	 */
	@WebMethod
	public Set<String> getAllUserNamesWithRole(String role) throws CisServiceException;

	
	/**
	 * Promote the user to administrator
	 *
	 * @param user the user
     *
	 * @throws CisServiceException 
	 */
	@WebMethod
	public void promoteAnotherUserToAdministrator(String userName,
			String computeHashOf, String anotherAccount) throws CisServiceException;

	/**
	 * Demote the user from administrator
	 *
	 * @param user the user
     *
	 * @throws CisServiceException 
	 */
	@WebMethod
	public void demoteAnotherUserFromAdministrator(String userName,
			String computeHashOf, String anotherAccount) throws CisServiceException;

	
//	/**
//	 * Sets the preferences for another user.
//	 * 
//	 * @throws CisServiceException 
//	 */
//	@WebMethod
//	public void setPreferenceForAnotherUser(
//			String user, 
//			String requester,
//			String requesterPwdHash, 
//			String service, String field, String value)
//			throws CisServiceException;
//
//	
////	/**
////	 * Returns the low security HIT for the specified user
////	 *
////	 * @param user    the user
////	 * @param pwdHash the password hash
////	 * @return true, if the user is present, false otherwise
////	 * @throws CisServiceException 
////	 * @throws HITRepositoryException 
////	 */	
////	@WebMethod
////	public NewHit getHITFor(String user, String pwdHash) throws CisServiceException;
//
////	/**
////	 * Sets the preferences (a String element) for the defined user, the defined service and 
////	 * the defined element. It contacts directly the CIS server to save
////	 * the information
////	 *
////	 * Example : user    = user_a
////	 *           service = dpas
////	 *           element = data_provider_preferences
////	 *           
////	 * @param user 		the user
////	 * @param pwd  		the password for the user
////	 * @param service  	the service you want the preferences of
////	 * @param element   the element of the preferences
////	 * 
////	 * @returns true if the operation was successful
////	 * @throws Exception 
////	 */
//
////	@WebMethod
////	public HITPayload getLowSecurityHITFor(String user, String pwdHash) throws CisServiceException;
////	/**
////	 * Sets the preferences (a String element) for the defined user, the defined service and 
////	 * the defined element. It contacts directly the CIS server to save
////	 * the information
////	 *
////	 * Example : user    = user_a
////	 *           service = dpas
////	 *           element = data_provider_preferences
////	 *           
////	 * @param user 		the user
////	 * @param pwd  		the password for the user
////	 * @param service  	the service you want the preferences of
////	 * @param element   the element of the preferences
////	 * 
////	 * @returns true if the operation was successful
////	 * @throws Exception 
////	 */
////	public	boolean	setStringPreferencesFor(String user, 
////			String	password,
////			String 	service, 
////			String  element,
////			String 	value) throws CisServiceException;
}
