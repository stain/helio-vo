package eu.heliovo.cis.service.hit.repository;

import java.util.HashSet;
import java.util.Set;

public interface HITRepository 
{
	/**
	 * Checks if is user present.
	 *
	 * @param user the user
	 * @return true, if is user present
	 * @throws HITRepositoryException the hIT repository exception
	 */
	public abstract boolean isUserPresent(String user)
			throws HITRepositoryException;

	/**
	 * Validate user.
	 *
	 * @param userName the name of the user
	 * @param pwdHash the hash of the password
	 * @return true, if successful
	 * @throws HITRepositoryException the HIT repository exception
	 */
	public abstract boolean validateUser(String user, String pwdHash)
			throws HITRepositoryException;

	/**
	 * Gets the profile for the specified user.
	 *
	 * @param user the user
	 * @return the user profile for
	 * @throws HITRepositoryException the simple hit repository exception
	 */
	public abstract String getUserProfileFor(String user)
			throws HITRepositoryException;

	/**
	 * Adds the user.
	 *
	 * @param user the user
	 * @param pwd the pwd
	 * @param profile the profile
	 * @throws HITRepositoryException 
	 */
	public abstract void addUser(String user, String pwd, String profile)
			throws HITRepositoryException;

	/**
	 * Checks if is load successful.
	 *
	 * @return true, if is load successful
	 */
	public boolean isLoadSuccessful();

	/**
	 * Gets the user profile entity.
	 *
	 * @param user the user
	 * @param service the service
	 * @param entity the entity
	 * @return the user profile entity
	 * @throws HITRepositoryException 
	 */
	public abstract String getUserProfileEntity(String user, String service,
			String entity) throws HITRepositoryException;

	/**
	 * Sets the user profile entity.
	 *
	 * @param user the user
	 * @param service the service
	 * @param entity the entity
	 * @param value 
	 * @throws HITRepositoryException 
	 */
	public abstract void setUserProfileEntity(String user, String service,
			String entity, String value) throws HITRepositoryException;

	/**
	 * Adds the user.
	 *
	 * @param user the user
	 * @param pwd the pwd
	 * @throws HITRepositoryException 
	 */
	public abstract void addUser(String user, String pwd) throws HITRepositoryException;
	/**
	 * Removes the user.
	 *
	 * @param user the user
	 * @param pwd the pwd
	 * @throws HITRepositoryException 
	 */
	public abstract void removeUser(String user) throws HITRepositoryException;
	/**
	 * Returns a set with all the users.
	 *
	 * @throws HITRepositoryException 
	 */
	public abstract Set<String> getAllUserNames();
	/**
	 * Changes the password for the user.
	 *
	 * @param user the user
	 * @param oldPwdHash the hash for the old password
	 * @param newPwdHash the hash for the new password
	 * @throws HITRepositoryException 
	 */
	public abstract void changePwdForUser(String user, String oldPwdHash,
			String newPwdHash) throws HITRepositoryException;
	/**
	 * Gets the preferences for the user
	 *
	 * @throws HITRepositoryException 
	 */
	public abstract String getPreferenceForUser(String user, String service,
			String field) throws HITRepositoryException;

	/**
	 * Sets the preferences for the user
	 *
	 * @throws HITRepositoryException 
	 */
	void setPreferenceForUser(String user, String service, String field,
			String value) throws HITRepositoryException;
	
	/**
	 * Gets the roles for the user
	 *
	 * @throws HITRepositoryException 
	 */
	public abstract Set<String> getRolesForUser(String user) throws HITRepositoryException;
	/**
	 * Returns true if the user has a certain role
	 *
	 * @throws HITRepositoryException 
	 */
	public boolean hasRole(String user) throws HITRepositoryException;
	/**
	 * Sets the roles for the user
	 *
	 * @throws HITRepositoryException 
	 */
	void setRolesForUser(String user, Set<String> roles) throws HITRepositoryException;

	/**
	 * Validates a user with for a given role
	 *
	 * @param userName the name of the user
	 * @param pwdHash the hash of the password
	 * @param role the hash of the password
	 * @return true, if successful
	 * @throws HITRepositoryException the HIT repository exception
	 */
	boolean validateUser(String user, String pwdHash, String role)
			throws HITRepositoryException;
}