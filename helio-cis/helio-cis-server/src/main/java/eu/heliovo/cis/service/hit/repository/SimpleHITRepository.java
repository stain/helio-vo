/*
 * 
 */
package eu.heliovo.cis.service.hit.repository;

import java.util.HashMap;
import java.util.Set;

import eu.heliovo.cis.service.utilities.SecurityUtilities;
import eu.heliovo.cis.service.utilities.SecurityUtilitiesException;

/**
 * The Class SimpleHITRepository.
 */
public class SimpleHITRepository implements HITRepository 
{
	/*
	 * These are the hash maps that hold the information
	 */
	/** The users. */
	HashMap<String, String>	users		=	null;
	
	/** The profiles. */
	HashMap<String, String>	profiles	=	null;
	/*
	 * The hashing algorithm for the password
	 */
	/** The sec utils. */
	SecurityUtilities		secUtils	=	new SecurityUtilities();
	
	/**
	 * Instantiates a new simple hit repository.
	 */
	public SimpleHITRepository() 
	{
		super();
		initialize();
	}

	/**
	 * Initialize.
	 */
	void	initialize()
	{
		users		=	new HashMap<String, String>();
		profiles	=	new HashMap<String, String>();		
	}	
	
	/* (non-Javadoc)
	 * @see org.helio.cil.HItRepository#isUserPresent(java.lang.String)
	 */
	public	boolean	isUserPresent(String user) throws HITRepositoryException
	{
		if(user == null)
			throw new HITRepositoryException();
		
		if(user.length() == 0)
			throw new HITRepositoryException();
		
		return users.containsKey(user);
	}

	/* (non-Javadoc)
	 * @see org.helio.cil.HItRepository#validateUser(java.lang.String, java.lang.String)
	 */
	public	boolean	validateUser(String user, String pwd) 
		throws HITRepositoryException
	{
		if(user == null || pwd == null)
			throw new HITRepositoryException();
		
		if((user.length() == 0) || (pwd.length() == 0))
			throw new HITRepositoryException();
		
		if(!isUserPresent(user))
			return false;
		else
		{
			try 
			{
				return (secUtils.computeHashOf(pwd).equals(users.get(user)));
			} 
			catch (SecurityUtilitiesException e) 
			{
				e.printStackTrace();
				throw new HITRepositoryException();
			}
		}
	}
	/* (non-Javadoc)
	 * @see org.helio.cil.HItRepository#getUserProfileFor(java.lang.String)
	 */
	public String getUserProfileFor(String user) throws HITRepositoryException 
	{
		if(user == null)
			throw new HITRepositoryException();
		
		if(user.length() == 0)
			throw new HITRepositoryException();
		
		if(!isUserPresent(user))
			throw new HITRepositoryException();
		
		return	profiles.get(user);
	}

	/* (non-Javadoc)
	 * @see org.helio.cil.HITRepository#addUser(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void addUser(String user, String pwd, String profile) throws HITRepositoryException 
	{
		if(users.containsKey(user))
			throw new HITRepositoryException();
		
		try 
		{
			users.put(user, secUtils.computeHashOf(pwd));
		} 
		catch (SecurityUtilitiesException e) 
		{
			e.printStackTrace();
			throw new HITRepositoryException();
		}
		profiles.put(user, profile);
	}

	public boolean isLoadSuccessful() {
		return false;
	}

	@Override
	public String getUserProfileEntity(String user, String service,
			String entity) throws HITRepositoryException 
	{
		throw new HITRepositoryException();
	}

	@Override
	public void addUser(String user, String pwd) throws HITRepositoryException 
	{		
	}

	@Override
	public void setUserProfileEntity(String user, String service,
			String entity, String value) throws HITRepositoryException 
	{
	}

	@Override
	public void removeUser(String user) 
	{	
	}

	@Override
	public Set getAllUserNames() 
	{
		return null;
	}

	@Override
	public void changePwdForUser(String user, String oldPwdHash,
			String newPwdHash) throws HITRepositoryException 
	{
		return;
	}

	@Override
	public String getPreferenceForUser(String user, String service, String field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPreferenceForUser(String user, String service, String field,
			String value) throws HITRepositoryException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRolesForUser(String user, Set<String> roles)
			throws HITRepositoryException 
	{
	}

	@Override
	public Set<String> getRolesForUser(String user)
			throws HITRepositoryException 
	{
		return null;
	}

	@Override
	public boolean hasRole(String user) throws HITRepositoryException 
	{
		return false;
	}

	@Override
	public boolean validateUser(String user, String pwdHash, String role)
			throws HITRepositoryException 
	{
		return false;
	}
}
