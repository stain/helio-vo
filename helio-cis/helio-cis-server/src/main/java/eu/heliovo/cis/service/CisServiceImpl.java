package eu.heliovo.cis.service;

import javax.jws.WebService;

import eu.heliovo.cis.service.hit.info.HIT;
import eu.heliovo.cis.service.hit.info.HITException;
import eu.heliovo.cis.service.hit.repository.FileBasedHITRepository;
import eu.heliovo.cis.service.hit.repository.HITRepository;
import eu.heliovo.cis.service.hit.repository.HITRepositoryException;
import eu.heliovo.shared.common.cis.hit.HITPayload;

@WebService(endpointInterface = "eu.heliovo.cis.service.CisService")
public class CisServiceImpl implements CisService 
{
	HITRepository		repository	=	null;

	public CisServiceImpl() 
	{
		super();
		repository	=	new FileBasedHITRepository();
	}

	public CisServiceImpl(String repositoryLocation) 
	{
		super();
//		System.out.println(repositoryLocation);
		repository	=	new FileBasedHITRepository(repositoryLocation);
	}
	
    public String test(String name) 
    {
        return "Test performed with parameter " + name + "!";        
    }

	@Override
	public boolean validateUser(String user, String pwdHash) 
	{
		try 
		{
			return repository.validateUser(user, pwdHash);
		} 
		catch (HITRepositoryException e) 
		{
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void addUser(String user, String pwdHash) throws CisServiceException 
	{
//		System.out.println(sysUtils.sysExec("dir"));
		try 
		{
			repository.addUser(user, pwdHash);
		} 
		catch (HITRepositoryException e) 
		{
			e.printStackTrace();
			throw new CisServiceException();
		}
	}

	@Override
	public boolean isUserPresent(String user) throws CisServiceException 
	{
		try 
		{
			return repository.isUserPresent(user);
		} 
		catch (HITRepositoryException e) 
		{
			throw new CisServiceException();
		}
	}

	@Override
	public HITPayload getLowSecurityHITFor(String user, String pwdHash) throws CisServiceException 
	{	
		/*
		 * Check that the user is present and it is valid
		 */
		if(!isUserPresent(user))
			throw new CisServiceException();
		
		if(!validateUser(user, pwdHash))
			throw new CisServiceException();
			
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
		
		HITPayload	hitPayload	=	new HITPayload();
		
		hitPayload.setInformation(idToken.getInfo());
		hitPayload.setCredential("credential");
		
		return hitPayload;
	}

	@Override
	public boolean setStringPreferencesFor(String user, 
			String passwordHash,
			String service, 
			String element, 
			String value) throws CisServiceException 
	{
		try 
		{
			repository.setUserProfileEntity(user, service, element, value);
		} 
		catch (HITRepositoryException e) 
		{
			e.printStackTrace();
			return false;
		}
		return false;
	}

	@Override
	public void removeUser(String user, String pwdHash)
			throws CisServiceException 
	{
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
	}
}
