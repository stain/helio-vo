/*
 * 
 */
package eu.heliovo.cil;

import eu.heliovo.hit.info.CISValues;
import eu.heliovo.hit.info.HIT;
import eu.heliovo.hit.info.HITException;

/**
 * The Class CommunityInteractionService.
 */
public class CommunityInteractionService 
{
	HITRepository		repository	=	new FileBasedHITRepository();
	
	public CommunityInteractionService() 
	{
		super();
		System.out.println(repository.toString());
	}

	/**
	 * Validate user.
	 *
	 * @param user the user
	 * @param pwd the pwd
	 * @return true, if successful
	 */
	public	boolean	validateUser(String user, String pwd)
	{
		try 
		{
			return repository.validateUser(user, pwd);
		} 
		catch (HITRepositoryException e) 
		{
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Gets the low security hit for.
	 *
	 * @param user the user
	 * @return the low security hit for
	 */
	private	HIT	getLowSecurityHITFor(String user)
	{
		HIT		idToken		=	null;
		try 
		{
			idToken		=	new HIT(false, user);
			idToken.addProfile(repository.getUserProfileFor(user));
		} 
		catch (HITRepositoryException e) 
		{
			e.printStackTrace();
		} 
		catch (HITException e) 
		{
			e.printStackTrace();
		}
		return idToken;
	}

	/**
	 * Gets the low security hit for.
	 *
	 * @param user the user
	 * @return the low security hit for
	 */
	public	HIT	getLowSecurityHITFor(String user, String pwd)
	{
		HIT		idToken		=	null;
		try 
		{
			idToken		=	new HIT(false, user);
			idToken.addProfile(repository.getUserProfileFor(user));
		} 
		catch (HITRepositoryException e) 
		{
			e.printStackTrace();
		} 
		catch (HITException e) 
		{
			e.printStackTrace();
		}
		return idToken;
	}

	/**
	 * Gets the low security hit for the anonymous user
	 *
	 * @return the low security hit for
	 */
	public	HIT	getLowSecurityHITForAnonymousUser()
	{
		return getLowSecurityHITFor(CISValues.HelioAnonymousUser);
	}

	/**
	 * Gets the high security hit for the specified user.
	 *
	 * @param user the user
	 * @return the high security hit for
	 */
	public	HIT	getHighSecurityHITFor(String user, String pwd)
	{
		HIT		idToken		=	null;

		try 
		{
			idToken		=	new HIT(true, user);
			System.out.println(idToken);
			idToken.setCertId(user);
			System.out.println(idToken);
			idToken.addProfile(repository.getUserProfileFor(user));
			System.out.println(idToken);
		} 
		catch (HITRepositoryException e) 
		{
			e.printStackTrace();
		} 
		catch (HITException e) 
		{
			e.printStackTrace();
		}
		return idToken;
	}

	/**
	 * Gets the high security hit for the specified user.
	 *
	 * @param user the user
	 * @return the high security hit for
	 */
	public	HIT	getHighSecurityHITForAnonymousUser()
	{
		HIT		idToken		=	null;

		try 
		{
			idToken		=	new HIT(true, CISValues.HelioAnonymousUser);
			System.out.println(idToken);
			idToken.setCertId(CISValues.HelioAnonymousUser);
			System.out.println(idToken);
			idToken.addProfile(repository.getUserProfileFor(CISValues.HelioAnonymousUser));
			System.out.println(idToken);
		} 
		catch (HITRepositoryException e) 
		{
			e.printStackTrace();
		} 
		catch (HITException e) 
		{
			e.printStackTrace();
		}
		return idToken;
	}

	/**
	 * Adds a user with a profile.
	 *
	 * @param user the user
	 * @param pwd the password of the user
	 * @throws CommunityInteractionServiceException 
	 */
	public	void	addUserWithStandardProfile(String user, String pwd) throws CommunityInteractionServiceException
	{
		try 
		{
			repository.addUser(user, pwd);
		} 
		catch (HITRepositoryException e) 
		{
			e.printStackTrace();
			throw new CommunityInteractionServiceException();
		}
	}

	/**
	 * Adds a user with a specificied profile.
	 *
	 * @param user the user
	 * @param pwd the password of the user
	 * @param profile the profile of the user
	 * @throws HITRepositoryException 
	 */
	public	void	addUserWithSpecificProfile(String user, String pwd, String profile) throws HITRepositoryException
	{
		repository.addUser(user, pwd, profile);
	}
	
	/**
	 * Gets the user profile.
	 *
	 * @param user the user
	 * @param service the HELIO service that must be retrieved
	 * @param entity the entity that must be retrieved
	 * @return the user profile
	 * @throws CommunityInteractionServiceException 
	 */
	public	String	getUserProfileEntity(String user, String service, String entity) throws CommunityInteractionServiceException
	{
		try 
		{
			return repository.getUserProfileEntity(user, service, entity);
		} 
		catch (HITRepositoryException e) 
		{
			e.printStackTrace();
			throw new CommunityInteractionServiceException();
		}
	}
	
	/**
	 * Sets the user profile.
	 *
	 * @param user the user 
	 * @param service the HELIO service that must be modified
	 * @param entity the entity that must be modified
	 * @param value 
	 * @throws CommunityInteractionServiceException 
	 */
	public	void	setUserProfileEntity(String user, String service, String entity, String value) throws CommunityInteractionServiceException
	{
		try 
		{
			repository.setUserProfileEntity(user, service, entity, value);
		} 
		catch (HITRepositoryException e) 
		{
			e.printStackTrace();
			throw new CommunityInteractionServiceException();
		}
	}
}
