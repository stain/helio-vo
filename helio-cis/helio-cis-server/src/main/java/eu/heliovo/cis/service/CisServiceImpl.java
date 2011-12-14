package eu.heliovo.cis.service;

import java.util.Iterator;
import java.util.Set;

import javax.jws.WebService;

import eu.heliovo.cis.service.hit.repository.FileBasedHITRepository;
import eu.heliovo.cis.service.hit.repository.HITRepository;
import eu.heliovo.cis.service.hit.repository.HITRepositoryException;
import eu.heliovo.shared.common.cis.hit.info.UserValues;
import eu.heliovo.shared.common.utilities.LogUtilities;

@WebService(endpointInterface = "eu.heliovo.cis.service.CisService", serviceName = "CisService", portName="CisServicePort" )
public class CisServiceImpl implements CisService 
{
	/*
	 * Various utilities
	 */
	LogUtilities		logUtilities	=	new LogUtilities();

	HITRepository		repository		=	new FileBasedHITRepository();
//	HITUtilities		hitUtilities	=	new HITUtilities();
	
	public CisServiceImpl() 
	{
		super();
//		repository	=	new FileBasedHITRepository();
	}

	public CisServiceImpl(String repositoryLocation) 
	{
		super();
//		repository	=	new FileBasedHITRepository(repositoryLocation);
	}
	
    public String test(String name) 
    {
		logUtilities.printShortLogEntry("[CIS-SERVER] - Executing test()...");
		logUtilities.printShortLogEntry("[CIS-SERVER] ... DONE !");

        return "Test performed with parameter " + name + "!";        
    }

	@Override
	public boolean validateUser(String user, String pwdHash) 
	{
		try 
		{
			logUtilities.printShortLogEntry("[CIS-SERVER] - Executing validateUser("+user+","+pwdHash+")");
			return repository.validateUser(user, pwdHash);
		} 
		catch (HITRepositoryException e) 
		{
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean validateUserAndRole(String userName, String pwdHash,
			String userRole) throws CisServiceException 
	{
		logUtilities.printShortLogEntry("[CIS-SERVER] - Executing validateUserAndRole("+userName+","+pwdHash+","+userRole+")");
		try 
		{
			if(!isUserPresent(userName))
				return false;

			if(!validateUser(userName, pwdHash))
				return false;

			logUtilities.printShortLogEntry("[CIS-SERVER] - roles for "+ userName + " : " + getRolesForUser(userName));
			logUtilities.printShortLogEntry("[CIS-SERVER] - role required "+ userRole);

			if(contains(getRolesForUser(userName),userRole))
				logUtilities.printShortLogEntry("[CIS-SERVER] - " + getRolesForUser(userName) + " includes " + userRole);
			else
				logUtilities.printShortLogEntry("[CIS-SERVER] - " + getRolesForUser(userName) + " DOES NOT include " + userRole);
				
			return	contains(getRolesForUser(userName), userRole);
		} 
		catch (CisServiceException e) 
		{
			throw new CisServiceException();
		}
	}

	private boolean contains(Set<String> rolesForUser, String userRole) 
	{
		Iterator<String>	i	=	rolesForUser.iterator();
		
		while(i.hasNext())
			if(i.next().contains(userRole))
				return true;
		
		
		return false;
	}

	@Override
	public void addUser(String user, String pwdHash) throws CisServiceException 
	{
		printStatus();
		logUtilities.printShortLogEntry("[CIS-SERVER] - Executing addUser("+user+","+pwdHash+")");
		try 
		{
			repository.addUser(user, pwdHash);
			printStatus();
		} 
		catch (HITRepositoryException e) 
		{
			throw new CisServiceException();
		}
		logUtilities.printShortLogEntry("[CIS-SERVER] - ... DONE !");
	}

	@Override
	public boolean isUserPresent(String user) throws CisServiceException 
	{
		logUtilities.printShortLogEntry("[CIS-SERVER] - Executing isUserPresent("+user+")");		
		try 
		{
			return repository.isUserPresent(user);
		} 
		catch (HITRepositoryException e) 
		{
			throw new CisServiceException();
		}
	}

//	@Override
//	public CIS-SERVERAuthenticationToken authenticate(String user, String pwdHash) throws CIS-SERVERServiceException 
//	{	
//		/*
//		 * Check that the user is present and it is valid
//		 */
//		if(!isUserPresent(user))
//			throw new CIS-SERVERServiceException();
//		if(!validateUser(user, pwdHash))
//			throw new CIS-SERVERServiceException();
//	
//		
//		NewHit		hit		=	null;
//		return 		hit;
//	}
	
//	@Override
//	public NewHit getHITFor(String user, String pwdHash) throws CIS-SERVERServiceException 
//	{	
//		/*
//		 * Check that the user is present and it is valid
//		 */
//		if(!isUserPresent(user))
//			throw new CIS-SERVERServiceException();
//		if(!validateUser(user, pwdHash))
//			throw new CIS-SERVERServiceException();
//	
//		
//		NewHit		hit		=	null;
//		return 		hit;
//		
////		idToken		=	new HIT();
////		HITInfo	hitInfo	=	new HITInfo(user);
////		try 
////		{
////			hitInfo.addProfile(repository.getUserProfileFor(user).toString());
////		} 
////		catch (HITInfoException e) 
////		{
////			e.printStackTrace();
////		} 
////		catch (HITRepositoryException e) 
////		{
////			e.printStackTrace();
////		}
////		idToken.setHitInfo(hitInfo);
////		
////		HITPayload	hitPayload	=	new HITPayload();
////		
////		
//////		hitPayload.setInformation(idToken.getHitInfo().toString());
////		hitPayload.setCredential("credential");
////		
////		return hitPayload;
//	}

	
//	@Override
//	public HITPayload getLowSecurityHITFor(String user, String pwdHash) throws CIS-SERVERServiceException 
//	{	
//		/*
//		 * Check that the user is present and it is valid
//		 */
//		if(!isUserPresent(user))
//			throw new CIS-SERVERServiceException();
//		if(!validateUser(user, pwdHash))
//			throw new CIS-SERVERServiceException();
//			
//		HIT		idToken		=	null;
//		idToken		=	new HIT();
//		HITInfo	hitInfo	=	new HITInfo(user);
//		try 
//		{
//			hitInfo.addProfile(repository.getUserProfileFor(user).toString());
//		} 
//		catch (HITInfoException e) 
//		{
//			e.printStackTrace();
//		} 
//		catch (HITRepositoryException e) 
//		{
//			e.printStackTrace();
//		}
//		idToken.setHitInfo(hitInfo);
//		
//		HITPayload	hitPayload	=	new HITPayload();
//		
//		
//		hitPayload.setInformation(idToken.getHitInfo().toString());
//		hitPayload.setCredential("credential");
//		
//		return hitPayload;
//	}
//
//	@Override
//	public boolean setStringPreferencesFor(String user, 
//			String passwordHash,
//			String service, 
//			String element, 
//			String value) throws CIS-SERVERServiceException 
//	{
//		try 
//		{
//			repository.setUserProfileEntity(user, service, element, value);
//		} 
//		catch (HITRepositoryException e) 
//		{
//			e.printStackTrace();
//			return false;
//		}
//		return false;
//	}
//
	@Override
	public void removeUser(String user, String pwdHash)	throws CisServiceException 
	{
		logUtilities.printShortLogEntry("[CIS-SERVER] - Executing removeUser("+user+","+pwdHash+")");
		if(!isUserPresent(user))
			throw new CisServiceException();
		if(!validateUser(user, pwdHash))
			throw new CisServiceException();
			
		try 
		{
			repository.removeUser(user);
		} 
		catch (HITRepositoryException e) 
		{
			e.printStackTrace();
			throw new CisServiceException();
		}
		logUtilities.printShortLogEntry("[CIS-SERVER] - ... DONE !");
	}

	@Override
	public void removeAnotherUser(String user,
			String requester, String requesterPwdHash)	throws CisServiceException 
	{
		logUtilities.printShortLogEntry("[CIS-SERVER] - Executing removeUser("+user+","+requester+","+requesterPwdHash+")");
		/*
		 * Validate that the requester has administrative role
		 */
		if(!validateUserAndRole(requester, requesterPwdHash, UserValues.administratorRole))
			throw new CisServiceException();			
		if(!isUserPresent(user))
			throw new CisServiceException();
			
		try 
		{
			repository.removeUser(user);
		} 
		catch (HITRepositoryException e) 
		{
			e.printStackTrace();
			throw new CisServiceException();
		}
		logUtilities.printShortLogEntry("[CIS-SERVER] - ... DONE !");
	}

	@Override
	public void changePwdForUser(String user, String oldPwdHash,
			String newPwdHash) throws CisServiceException 
	{
		logUtilities.printShortLogEntry("[CIS-SERVER] - Executing removeUser("+user+","+oldPwdHash+","+newPwdHash+")");
		if(!isUserPresent(user))
			throw new CisServiceException();
		if(!validateUser(user, oldPwdHash))
			throw new CisServiceException();
			
		try 
		{
			repository.changePwdForUser(user, oldPwdHash, newPwdHash);
		} 
		catch (HITRepositoryException e) 
		{
			e.printStackTrace();
			throw new CisServiceException();
		}
		logUtilities.printShortLogEntry("[CIS-SERVER] - ... DONE !");
	}

	@Override
	public String getPreferenceForUser(String user, String service, String field) throws CisServiceException 
	{
		logUtilities.printShortLogEntry("[CIS-SERVER] - Executing getPreferenceForUser(" + user + ", " + service + ", " + field + ")");
		if(!isUserPresent(user))
			throw new CisServiceException();			
		try 
		{
			return repository.getPreferenceForUser(user, service, field);
		} 
		catch (HITRepositoryException e) 
		{
			e.printStackTrace();
			throw new CisServiceException();
		}
	}

	@Override
	public void setPreferenceForUser(String user, String pwdHash, String service,
			String field, String value) throws CisServiceException 
	{
		logUtilities.printShortLogEntry("[CIS-SERVER] - Executing getPreferenceForUser(" + user + ", " + service + ", " + field + ")");
		if(!isUserPresent(user))
			throw new CisServiceException();			
		if(!validateUser(user, pwdHash))
			throw new CisServiceException();			
		try 
		{
			repository.setPreferenceForUser(user, service, field, value);
		} 
		catch (HITRepositoryException e) 
		{
			e.printStackTrace();
			throw new CisServiceException();
		}
	}

	@Override
	public Set<String> getRolesForUser(String user) throws CisServiceException 
	{
		logUtilities.printShortLogEntry("[CIS-SERVER] - Executing getRolesForUser(" + user + ")");
		if(!isUserPresent(user))
			throw new CisServiceException();			
		try 
		{
			return repository.getRolesForUser(user);
		} 
		catch (HITRepositoryException e) 
		{
			e.printStackTrace();
			throw new CisServiceException();
		}
	}

	private void printStatus() 
	{
		logUtilities.printShortLogEntry("[CIS-SERVER] - " + repository.getAllUserNames().toString());
		logUtilities.printShortLogEntry("[CIS-SERVER] - " + repository.getAllUserNames().toString());
	}
}
