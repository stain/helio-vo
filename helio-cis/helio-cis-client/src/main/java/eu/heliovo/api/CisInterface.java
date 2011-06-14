package eu.heliovo.api;

import eu.heliovo.cis.CisClient;
import eu.heliovo.shared.common.cis.hit.HIT;
import eu.heliovo.shared.common.cis.hit.HITPayload;
import eu.heliovo.shared.common.cis.hit.HITPayload;
import eu.heliovo.shared.common.cis.hit.HITUtilities;
import eu.heliovo.shared.common.cis.hit.preferences.PreferencesUtilities;
import eu.heliovo.shared.common.cis.tags.HELIOTags;
import eu.heliovo.shared.common.utilities.ClassAdUtilities;

public class CisInterface 
{
	ClassAdUtilities		cadUtilities	=	new ClassAdUtilities();
	HITUtilities			hitUtilities	=	new HITUtilities();
	PreferencesUtilities	prUtilities		=	new PreferencesUtilities();
	
	CisClient			client			=	new CisClient();
	
	/**
	 * Validates a user.
	 * (Returns true if the user is present in the key store and if the password matches)
	 *
	 * @param  user the user
	 * @param  password the password of the user
	 * @return true if the user is present in the keystore and if the password matches
	 * @throws Exception 
	 */
	public	boolean	validateUser(String user, String password) throws Exception
	{
		return client.validateUser(user, password);
	}

	/**
	 * Adds a user with a profile.
	 *
	 * @param user the user
	 * @param pwd the password of the user
	 * @throws CisInterfaceException 
	 */
	public	void	addUserWithStandardProfile(String user, String pwd) throws CisInterfaceException 
	{
		try 
		{
			client.addUser(user, pwd);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new CisInterfaceException();
		}
	}

	/**
	 * Checks if a user is already present.
	 * (Returns true if the user is present in the key store)
	 *
	 * @param  user the user
	 * @return true if the user is present in the keystore.
	 */
	public boolean isUserPresent(String user) 
	{
		return client.isUserPresent(user);
	}	
	/**
	 * Gets the low security hit for the specified user.
	 *
	 * @param user the user
	 * @param pwd  the password for the user
	 * 
	 * @return the low security hit for
	 * @throws Exception 
	 */
	public	HIT	getLowSecurityHITFor(String user, String pwd) throws Exception
	{
		HITPayload	hitPayload	=	 client.getLowSecurityHITFor(user, pwd);
		System.out.println(hitPayload.getInformation());
		
		return 	hitUtilities.buildHITFromPayload(hitPayload);
	}
	/**
	 * Gets the preferences for the defined user, the defined service and 
	 * the defined element. It contacts directly the CIS server to obtain
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
	 * @return the service preference element defined for that user, that service
	 * @throws Exception 
	 */
	public	String	getPreferencesFor(String user, 
			String	password,
			String 	service, 
			String  element) throws Exception
	{
		/*
		 * TODO : What kind of HIT do we have to use ? 
		 *
		 * Return the HIT for the user and then parses it
		 */
		return this.getPreferencesFor(this.getLowSecurityHITFor(user, password), 
				service, 
				element);
	}
	/**
	 * Gets the preferences from the passed HIT for the defined service and 
	 * the defined element. 
	 *
	 * Example : service = dpas
	 *           element = data_provider_preferences
	 *           
	 * @param hit 		the Helio Identity Token to be used
	 * @param service  	the service you want the preferences of
	 * @param element   the element of the preferences
	 * 
	 * @return the service preference element contained in the hit defined for 
	 * that service
	 * @throws Exception 
	 */
	public	String	getPreferencesFor(HIT hit,
			String service, 
			String element) throws Exception
	{
		return 	prUtilities.getElementFor(hit.getHitInfo().getProfile(), service, element);
	}
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
			String 	value) throws Exception
	{
		return client.setStringPreferencesFor(user, password, service, element, value);
	}
	
	/**
	 * Removes the defined user (it succeeds only if the password is correct
	 * for that user)
	 *           
	 * @param user 		the user
	 * @param pwd  		the password for the user
	 * 
	 * @returns true if the operation was successful
	 * @throws Exception 
	 */
	public	boolean	removeUser(String user, 
			String	password) throws Exception
	{	
		return client.removeUser(user, password);
	}

//	/**
//	 * Gets the low security hit for the anonymous user
//	 *
//	 * @return the low security hit for
//	 */
//	public	HIT	getLowSecurityHITForAnonymousUser()
//	{
//		return getLowSecurityHITFor(CISValues.HelioAnonymousUser);
//	}
//
//	/**
//	 * Gets the high security hit for the specified user.
//	 *
//	 * @param user the user
//	 * @return the high security hit for
//	 */
//	public	HIT	getHighSecurityHITFor(String user, String pwd)
//	{
//		HIT		idToken		=	null;
//
//		try 
//		{
//			idToken		=	new HIT(true, user);
//			System.out.println(idToken);
//			idToken.setCertId(user);
//			System.out.println(idToken);
//			idToken.addProfile(repository.getUserProfileFor(user));
//			System.out.println(idToken);
//		} 
//		catch (HITRepositoryException e) 
//		{
//			e.printStackTrace();
//		} 
//		catch (HITException e) 
//		{
//			e.printStackTrace();
//		}
//		return idToken;
//	}
//
//	/**
//	 * Gets the high security hit for the specified user.
//	 *
//	 * @param user the user
//	 * @return the high security hit for
//	 */
//	public	HIT	getHighSecurityHITForAnonymousUser()
//	{
//		HIT		idToken		=	null;
//
//		try 
//		{
//			idToken		=	new HIT(true, CISValues.HelioAnonymousUser);
//			System.out.println(idToken);
//			idToken.setCertId(CISValues.HelioAnonymousUser);
//			System.out.println(idToken);
//			idToken.addProfile(repository.getUserProfileFor(CISValues.HelioAnonymousUser));
//			System.out.println(idToken);
//		} 
//		catch (HITRepositoryException e) 
//		{
//			e.printStackTrace();
//		} 
//		catch (HITException e) 
//		{
//			e.printStackTrace();
//		}
//		return idToken;
//	}

}
