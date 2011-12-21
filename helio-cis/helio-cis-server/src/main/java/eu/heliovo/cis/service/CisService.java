package eu.heliovo.cis.service;

import java.util.HashSet;
import java.util.Set;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

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
	public	boolean	validateUser(String user, String pwdHash);
	/**
	 * Adds a user with a standard profile.
	 *
	 * @param user the name of the user
	 * @param pwdHash the hash of the password for the user
	 * @throws CisServiceException 
	 */

	@WebMethod
	public void addUser(String user, String computeHashOf) throws CisServiceException;
	/**
	 * Adds a user with a standard profile.
	 *
	 * @param user the name of the user
	 * @param pwdHash the hash of the password for the user
	 * @throws CisServiceException 
	 */
	@WebMethod
	public void removeUser(String user, String pwdHash) throws CisServiceException;
	/**
	 * Removes another user (only for administrators).
	 *
	 * @param user the name of the user to be removed
	 * @param requester the name of the administrator who requests the operation
	 * @param requesterPwdHash the hash of the password for the administrator who requests the operation
	 * @throws CisServiceException 
	 */
	@WebMethod
	public void removeAnotherUser(String user, String requester, String requesterPwdHash)
	throws CisServiceException;

	/**
	 * Removes the user if it is present and the password matches.
	 *
	 * @param user the user
	 * @param pwdHash the password hash for that user
	 * 
	 * @return true, if the user is present, false otherwise
	 * @throws CisServiceException 
	 */
	@WebMethod
	@WebResult(name="isUserPresentResult")
	public boolean isUserPresent(String user) throws CisServiceException;
	/**
	 * Changes the password of a user.
	 * 
	 * @param userName the name of the user
	 * @param oldPwd the oldPw. Will be hashed by this implementation.
	 * @param newPwd the newPw. Will be hashed by this implementation.
	 * @return true if the password has been changed, false otherwise (because the old pw did not match).
	 * @throws CisClientException if the oldPwd does not match.
	 */
	@WebMethod
	public void changePwdForUser(String user, String oldPwdHash, String newPwdHash) throws CisServiceException;
	/**
	 * Gets the preference for the user.
	 *
	 * @param user the user
	 * @param service the service 
	 * @param field the field 
	 * 
	 * @return String, if the user is present, false otherwise
	 * @throws CisServiceException 
	 */
	@WebMethod
	@WebResult(name="getPreferenceForUserResult")
	public String getPreferenceForUser(String user, String service, String field) throws CisServiceException;
	/**
	 * Sets the preference for the user.
	 *
	 * @param user the user
	 * @param pwd the password for the user
	 * @param service the service 
	 * @param field the field 
	 * 
	 * @return String, if the user is present, false otherwise
	 * @throws CisServiceException 
	 */
	@WebMethod
	void setPreferenceForUser(String user, String pwdHash, String service,
			String field, String value) throws CisServiceException;

	/**
	 * Gets the roles for the user.
	 *
	 * @param user the user
	 * 
	 * @return Set, the set of roles given to the user
	 * @throws CisServiceException 
	 */
	@WebMethod
	public Set<String> getRolesForUser(String user_a) throws CisServiceException;

	/**
	 * Validates the user given a certain role.
	 *
	 * @param user the user
	 * @param pwd the has of the user pwd
	 * @param role the role of the user we want to validate
	 * 
	 * @return true if the user is validated for that role
	 * @throws CisServiceException 
	 */
	@WebMethod
	public boolean validateUserAndRole(String userName, 
			String pwdHash,
			String userRole) 
					throws CisServiceException;

	/**
	 * Return all users.
	 * 
	 * @return hashset with all the usernames
	 * @throws CisServiceException 
	 */
	@WebMethod
	public Set<String> getAllUserNames();
	/**
	 * Sets the preferences for another user.
	 * 
	 * @throws CisServiceException 
	 */
	@WebMethod
	public void setPreferenceForAnotherUser(
			String user, 
			String requester,
			String requesterPwdHash, 
			String service, String field, String value)
			throws CisServiceException;

	
//	/**
//	 * Returns the low security HIT for the specified user
//	 *
//	 * @param user    the user
//	 * @param pwdHash the password hash
//	 * @return true, if the user is present, false otherwise
//	 * @throws CisServiceException 
//	 * @throws HITRepositoryException 
//	 */	
//	@WebMethod
//	public NewHit getHITFor(String user, String pwdHash) throws CisServiceException;

//	/**
//	 * Sets the preferences (a String element) for the defined user, the defined service and 
//	 * the defined element. It contacts directly the CIS server to save
//	 * the information
//	 *
//	 * Example : user    = user_a
//	 *           service = dpas
//	 *           element = data_provider_preferences
//	 *           
//	 * @param user 		the user
//	 * @param pwd  		the password for the user
//	 * @param service  	the service you want the preferences of
//	 * @param element   the element of the preferences
//	 * 
//	 * @returns true if the operation was successful
//	 * @throws Exception 
//	 */

//	@WebMethod
//	public HITPayload getLowSecurityHITFor(String user, String pwdHash) throws CisServiceException;
//	/**
//	 * Sets the preferences (a String element) for the defined user, the defined service and 
//	 * the defined element. It contacts directly the CIS server to save
//	 * the information
//	 *
//	 * Example : user    = user_a
//	 *           service = dpas
//	 *           element = data_provider_preferences
//	 *           
//	 * @param user 		the user
//	 * @param pwd  		the password for the user
//	 * @param service  	the service you want the preferences of
//	 * @param element   the element of the preferences
//	 * 
//	 * @returns true if the operation was successful
//	 * @throws Exception 
//	 */
//	public	boolean	setStringPreferencesFor(String user, 
//			String	password,
//			String 	service, 
//			String  element,
//			String 	value) throws CisServiceException;
}
