package eu.heliovo.cis.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.jws.WebService;

import eu.heliovo.cis.service.repository.AccountsException;
import eu.heliovo.cis.service.repository.SimpleAccounts;
import eu.heliovo.shared.cis.HELIORoles;
import eu.heliovo.shared.common.utilities.LogUtilities;

@WebService(endpointInterface = "eu.heliovo.cis.service.CisService", serviceName = "CisService", portName="CisServicePort" )
public class CisServiceImpl implements CisService 
{
	/*
	 * Various utilities
	 */
	LogUtilities		logUtilities	=	new LogUtilities();
	/*
	 * The repository for the accounts
	 */
	SimpleAccounts		repository		=	new SimpleAccounts();

		
	
//	ClassAdFileAccounts	cadRepository		=	new ClassAdFileAccounts();
//	SimpleFileAccounts	simpleRepository	=	new SimpleFileAccounts();
	
//	HITRepository		repository		=	new FileBasedHITRepository();
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
	
    public String test(String p) 
    {
		logUtilities.printShortLogEntry("[CIS-SERVER] - Executing test()...");
        return "Test performed with parameter " + p + "!";        
    }

	@Override
	public void addUser(String name, String pwdHash) throws CisServiceException 
	{
		printStatus();
		logUtilities.printShortLogEntry("[CIS-SERVER] - Executing addUser("+name+","+pwdHash+")");
		try 
		{
			repository.addUser(name, pwdHash);
			printStatus();
		} 
		catch (AccountsException e) 
		{
			throw new CisServiceException();
		}
	}

	@Override
	public void addUserWithEmail(String name, String email, String pwdHash) throws CisServiceException 
	{
		printStatus();
		logUtilities.printShortLogEntry("[CIS-SERVER] - Executing addUser("+name+","+email+","+pwdHash+")");
		try 
		{
			repository.addUser(name, email, pwdHash);
			printStatus();
		} 
		catch (AccountsException e) 
		{
			throw new CisServiceException();
		}
	}
    
    
	@Override
	public boolean validateUser(String user, String pwdHash) 
	{
		try 
		{
			logUtilities.printShortLogEntry("[CIS-SERVER] - Executing validateUser("+user+","+pwdHash+")");
			return repository.validateUser(user, pwdHash);
		} 
		catch (AccountsException e) 
		{
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean validateUserAndRole(
			String userName, 
			String pwdHash,
			String userRole) throws CisServiceException 
	{
		logUtilities.printShortLogEntry("[CIS-SERVER] - Executing validateUserAndRole("+userName+","+pwdHash+","+userRole+")");
		try 
		{
			if(!isUserPresent(userName))
				return false;

			if(!validateUser(userName, pwdHash))
				return false;

//			logUtilities.printShortLogEntry("[CIS-SERVER] - roles for "+ userName + " : " + getRolesForUser(userName));
//			logUtilities.printShortLogEntry("[CIS-SERVER] - role required "+ userRole);
//
//			if(getRolesForUser(userName).contains(userRole))
//				logUtilities.printShortLogEntry("[CIS-SERVER] - " + getRolesForUser(userName) + " includes " + userRole);
//			else
//				logUtilities.printShortLogEntry("[CIS-SERVER] - " + getRolesForUser(userName) + " DOES NOT include " + userRole);
				
			return	getRolesForUser(userName).contains(userRole);
		} 
		catch (CisServiceException e) 
		{
			throw new CisServiceException();
		}
	}

////	private boolean contains(Set<String> rolesForUser, String userRole) 
////	{
////		Iterator<String>	i	=	rolesForUser.iterator();
////		
////		while(i.hasNext())
////			if(i.next().contains(userRole))
////				return true;
////		
////		
////		return false;
////	}
//
//	@Override
//	public void addUser(String user, String pwdHash) throws CisServiceException 
//	{
//		logUtilities.printShortLogEntry("[CIS-SERVER] - Executing addUser("+user+","+pwdHash+")");
//		try 
//		{
//			printStatus();
//			cadRepository.addUser(user, pwdHash);
//			simpleRepository.addUser(user, pwdHash);
//			printStatus();
//		} 
//		catch (AccountsException e) 
//		{
//			throw new CisServiceException();
//		}
//		logUtilities.printShortLogEntry("[CIS-SERVER] - ... DONE !");
//	}

	@Override
	public boolean isUserPresent(String name)
	{
		logUtilities.printShortLogEntry("[CIS-SERVER] - Executing isUserPresent("+name+")");		
		return repository.isUserPresent(name) ;
	}

////	@Override
////	public CIS-SERVERAuthenticationToken authenticate(String user, String pwdHash) throws CIS-SERVERServiceException 
////	{	
////		/*
////		 * Check that the user is present and it is valid
////		 */
////		if(!isUserPresent(user))
////			throw new CIS-SERVERServiceException();
////		if(!validateUser(user, pwdHash))
////			throw new CIS-SERVERServiceException();
////	
////		
////		NewHit		hit		=	null;
////		return 		hit;
////	}
//	
////	@Override
////	public NewHit getHITFor(String user, String pwdHash) throws CIS-SERVERServiceException 
////	{	
////		/*
////		 * Check that the user is present and it is valid
////		 */
////		if(!isUserPresent(user))
////			throw new CIS-SERVERServiceException();
////		if(!validateUser(user, pwdHash))
////			throw new CIS-SERVERServiceException();
////	
////		
////		NewHit		hit		=	null;
////		return 		hit;
////		
//////		idToken		=	new HIT();
//////		HITInfo	hitInfo	=	new HITInfo(user);
//////		try 
//////		{
//////			hitInfo.addProfile(repository.getUserProfileFor(user).toString());
//////		} 
//////		catch (HITInfoException e) 
//////		{
//////			e.printStackTrace();
//////		} 
//////		catch (HITRepositoryException e) 
//////		{
//////			e.printStackTrace();
//////		}
//////		idToken.setHitInfo(hitInfo);
//////		
//////		HITPayload	hitPayload	=	new HITPayload();
//////		
//////		
////////		hitPayload.setInformation(idToken.getHitInfo().toString());
//////		hitPayload.setCredential("credential");
//////		
//////		return hitPayload;
////	}
//
//	
////	@Override
////	public HITPayload getLowSecurityHITFor(String user, String pwdHash) throws CIS-SERVERServiceException 
////	{	
////		/*
////		 * Check that the user is present and it is valid
////		 */
////		if(!isUserPresent(user))
////			throw new CIS-SERVERServiceException();
////		if(!validateUser(user, pwdHash))
////			throw new CIS-SERVERServiceException();
////			
////		HIT		idToken		=	null;
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
////		hitPayload.setInformation(idToken.getHitInfo().toString());
////		hitPayload.setCredential("credential");
////		
////		return hitPayload;
////	}
////
////	@Override
////	public boolean setStringPreferencesFor(String user, 
////			String passwordHash,
////			String service, 
////			String element, 
////			String value) throws CIS-SERVERServiceException 
////	{
////		try 
////		{
////			repository.setUserProfileEntity(user, service, element, value);
////		} 
////		catch (HITRepositoryException e) 
////		{
////			e.printStackTrace();
////			return false;
////		}
////		return false;
////	}
////
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
		catch (AccountsException e) 
		{
			e.printStackTrace();
			throw new CisServiceException();
		}
	}

	@Override
	public void removeAnotherUser(String user,
			String requester, String requesterPwdHash)	throws CisServiceException 
	{
		logUtilities.printShortLogEntry("[CIS-SERVER] - Executing removeUser("+user+","+requester+","+requesterPwdHash+")");
		/*
		 * Validate that the requester has administrative role
		 */
		if(!validateUserAndRole(requester, requesterPwdHash, HELIORoles.administrator))
			throw new CisServiceException();			
		if(!isUserPresent(user))
			throw new CisServiceException();
			
		try 
		{
			repository.removeUser(user);
		} 
		catch (AccountsException e) 
		{
			e.printStackTrace();
			throw new CisServiceException();
		}
	}

	@Override
	public void changePwdHashForUser(
			String name, 
			String oldPwdHash,
			String newPwdHash) throws CisServiceException 
	{
		logUtilities.printShortLogEntry("[CIS-SERVER] - Executing changePwdForUser("+name+","+oldPwdHash+","+newPwdHash+")");
		if(!isUserPresent(name))
			throw new CisServiceException();
		/*
		 * TODO : Re-enable this test
		 */
		if(!validateUser(name, oldPwdHash))
			throw new CisServiceException();
			
		try 
		{
			repository.changePwdHashForUser(name, oldPwdHash, newPwdHash);
		}
		catch (AccountsException e) 
		{
			e.printStackTrace();
			throw new CisServiceException();
		}
	}

	@Override
	public HashMap<String, HashMap<String, String>> getAllPreferencesForUser(String name) throws CisServiceException 
	{
		logUtilities.printShortLogEntry("[CIS-SERVER] - Executing getAllPreferenceForUser(" + name + ")");
		if(!isUserPresent(name))
			throw new CisServiceException();			
		try 
		{
			return repository.getAllPreferencesForUser(name);
		} 
		catch (AccountsException e) 
		{
			e.printStackTrace();
			throw new CisServiceException();
		}
	}

	@Override
	public HashMap<String, HashMap<String, String>> getAllStandardPreferences() throws CisServiceException 
	{
		try 
		{
			return repository.getStandardPreferences();
		} 
		catch (AccountsException e) 
		{
			e.printStackTrace();
			throw new CisServiceException();
		}
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
		catch (AccountsException e) 
		{
			e.printStackTrace();
			throw new CisServiceException();
		}
	}

	@Override
	public void setPreferenceForUser(String name, 
			String pwdHash, 
			String service,
			String field, 
			String value) throws CisServiceException 
	{
		logUtilities.printShortLogEntry("[CIS-SERVER] - Executing setPreferenceForUser(" + name + ", " + pwdHash + ", " + service + ", " + field + ")");
		if(!isUserPresent(name))
			throw new CisServiceException();			
		if(!validateUser(name, pwdHash))
			throw new CisServiceException();			
		try 
		{
			repository.setPreferenceForUser(name, service, field, value);
		} 
		catch (AccountsException e) 
		{
			e.printStackTrace();
			throw new CisServiceException();
		}
	}

	
	@Override
	public void setStandardPreference(String requester, String requesterPwdHash,
			String prefService, String prefField, String prefValue) throws CisServiceException 
	{
		logUtilities.printShortLogEntry("[CIS-SERVER] - Executing setStandardPreference("
				+ requester +","+ requesterPwdHash +
				","+prefService+","+prefField+","+prefValue+")");
		/*
		 * Validate that the requester has administrative role
		 */
		if(!validateUserAndRole(requester, requesterPwdHash, HELIORoles.administrator))
			throw new CisServiceException();			
			
		repository.setStandardPreference(prefService, prefField, prefValue);
		logUtilities.printShortLogEntry("[CIS-SERVER] - ... DONE !");
	
	}

	@Override
	public void removeServiceInStandardPreference(String requester, 
			String requesterPwdHash,
			String prefService) throws CisServiceException 
	{
		logUtilities.printShortLogEntry("[CIS-SERVER] - Executing removeStandardPreference("+requester+","+requesterPwdHash+","+prefService+")");
		/*
		 * Validate that the requester has administrative role
		 */
		if(!validateUserAndRole(requester, requesterPwdHash, HELIORoles.administrator))
			throw new CisServiceException();			
			

		repository.removeStandardPreference(prefService);
	}

	@Override
	public void removeFieldInStandardPreference(String requester, 
			String requesterPwdHash,
			String prefService, 
			String prefField) throws CisServiceException 
	{
		logUtilities.printShortLogEntry("[CIS-SERVER] - Executing removeStandardPreference("+requester+","+requesterPwdHash+","+prefService+","+prefField+")");
		/*
		 * Validate that the requester has administrative role
		 */
		if(!validateUserAndRole(requester, requesterPwdHash, HELIORoles.administrator))
			throw new CisServiceException();			
			

		try 
		{
			repository.removeStandardPreference(prefService, prefField);
		} 
		catch (AccountsException e) 
		{
			throw new CisServiceException();			
		}		
	}

	
	
////	@Override
////	public void setPreferenceForAnotherUser(String user,
////			String requester, String requesterPwdHash, String service,
////			String field, String value) throws CisServiceException 
////	{
////		logUtilities.printShortLogEntry("[CIS-SERVER] - Executing setPreferenceForAnotherUser("+
////				user+","+requester+","+requesterPwdHash+","+
////				service+","+field+","+value+")");
////		/*
////		 * Validate that the requester has administrative role
////		 */
////		if(!validateUserAndRole(requester, requesterPwdHash, UserValues.administratorRole))
////			throw new CisServiceException();			
////		if(!isUserPresent(user))
////			throw new CisServiceException();
////
////		try 
////		{
////			repository.setPreferenceForUser(user, service, field, value);
////		} 
////		catch (HITRepositoryException e) 
////		{
////			e.printStackTrace();
////			throw new CisServiceException();
////		}
////	}

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
		catch (AccountsException e) 
		{
			e.printStackTrace();
			throw new CisServiceException();
		}
	}

	@Override
	public Set<String> getAllUserNames() 
	{
		return repository.getAllUserNames();
	}

	@Override
	public Set<String> getAllUserNamesWithRole(String role) throws CisServiceException 
	{
		Iterator<String>	i	=	repository.getAllUserNames().iterator();
		
		HashSet<String>		result	=	new HashSet<String>();
		while(i.hasNext())
		{
			String	name	=	i.next();
			if(getRolesForUser(name).contains(role))
				result.add(name);
		}
		return result;
	}

	@Override
	public void promoteAnotherUserToAdministrator(
			String requester,
			String requesterPwdHash, 
			String user) throws CisServiceException 
	{
		logUtilities.printShortLogEntry("[CIS-SERVER] - Executing promoteAnotherUserToAdministrator("+requester+","+requesterPwdHash+","+user+")");
		/*
		 * Validate that the requester has administrative role
		 */
		if(!validateUserAndRole(requester, requesterPwdHash, HELIORoles.administrator))
			throw new CisServiceException();			
		if(!isUserPresent(user))
			throw new CisServiceException();
			
		try 
		{
			repository.addRoleToUser(user, HELIORoles.administrator);
		} 
		catch (AccountsException e) 
		{
			e.printStackTrace();
			throw new CisServiceException();
		}
		logUtilities.printShortLogEntry("[CIS-SERVER] - ... DONE !");		
	}

	@Override
	public void demoteAnotherUserFromAdministrator(
			String requester,
			String requesterPwdHash, 
			String user) throws CisServiceException 
	{
		logUtilities.printShortLogEntry("[CIS-SERVER] - Executing promoteAnotherUserToAdministrator("+requester+","+requesterPwdHash+","+user+")");
		/*
		 * Validate that the requester has administrative role
		 */
		if(!validateUserAndRole(requester, requesterPwdHash, HELIORoles.administrator))
			throw new CisServiceException();			
		if(!isUserPresent(user))
			throw new CisServiceException();
			
		try 
		{
			repository.removeRoleFromUser(user, HELIORoles.administrator);
		} 
		catch (AccountsException e) 
		{
			e.printStackTrace();
			throw new CisServiceException();
		} 
	}

	
	private void printStatus() 
	{
		logUtilities.printShortLogEntry("[CIS-SERVER] - " + repository.getAllUserNames().toString());
	}
}
