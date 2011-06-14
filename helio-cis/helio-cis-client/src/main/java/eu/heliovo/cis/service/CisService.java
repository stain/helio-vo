package eu.heliovo.cis.service;

import javax.jws.WebMethod;
import javax.jws.WebService;

import eu.heliovo.shared.common.cis.hit.HITPayload;

@WebService
public interface CisService 
{
	/**
	 * A simple test to see if the service is up and running
	 *
	 * @param a string parameter
	 */
	@WebMethod
    public String test(String param); 
	/**
	 * Validate user.
	 *
	 * @param user the user
	 * @param pwd the pwd
	 * @return true, if successful
	 */
	@WebMethod
	public	boolean	validateUser(String user, String pwdHash);
	/**
	 * Adds a user with a standard profile.
	 *
	 * @param user the user
	 * @param pwd the pwd
	 * @throws CisServiceException 
	 * @throws eu.heliovo.cis.service.CisServiceException 
	 */
	@WebMethod
	public void addUser(String user, String computeHashOf) throws CisServiceException;
	/**
	 * Adds a user with a standard profile.
	 *
	 * @param user the user
	 * @param pwd the pwd
	 * @throws CisServiceException 
	 * @throws eu.heliovo.cis.service.CisServiceException 
	 */
	@WebMethod
	public void removeUser(String user, String pwdHash) throws CisServiceException;
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
	public boolean isUserPresent(String user) throws CisServiceException;
	/**
	 * Returns the low security HIT for the specified user
	 *
	 * @param user    the user
	 * @param pwdHash the password hash
	 * @return true, if the user is present, false otherwise
	 * @throws CisServiceException 
	 * @throws HITRepositoryException 
	 */
	@WebMethod
	public HITPayload getLowSecurityHITFor(String user, String pwdHash) throws CisServiceException;
	/**
	 * Sets the preferences (a String element) for the defined user, the defined service and 
	 * the defined element. It contacts directly the CIS server to save
	 * the information
	 *
	 * Example : user    = user_a
	 *           service = dpas
	 *           element = data_provider_preferences
	 *           
	 * @param user 		the user
	 * @param pwd  		the password for the user
	 * @param service  	the service you want the preferences of
	 * @param element   the element of the preferences
	 * 
	 * @returns true if the operation was successful
	 * @throws Exception 
	 */
	public	boolean	setStringPreferencesFor(String user, 
			String	password,
			String 	service, 
			String  element,
			String 	value) throws CisServiceException;
}
