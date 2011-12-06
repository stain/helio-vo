package eu.heliovo.cis;

public class CisApi 
{
	/*
	 * This is the client to the Cis Server
	 */
	CisClient					cis				=	new CisClient();
	/*
	 * This is the spring-compliant authenticationProvider
	 */
	CisAuthenticationProvider	authProvider	=	new CisAuthenticationProvider();

	/*
	 * Methods to create and remove a user
	 */	
	public boolean isCisRunning()
	{
		return cis.isRunning();
	}
	/*
	 * Methods to create and remove a user
	 */
	public void addUser(String userName, String userPwd) throws CisApiException
	{
		try 
		{
			cis.addUser(userName, userPwd);
		} 
		catch (CisClientException e) 
		{
			e.printStackTrace();
			throw new CisApiException();
		}
	}
	public void removeUser(String userName, String userPwd) throws CisApiException
	{
		try 
		{
			cis.removeUser(userName, userPwd);
		} 
		catch (CisClientException e) 
		{
			e.printStackTrace();
			throw new CisApiException();
		}
	}
	public OldCisAuthenticationToken authenticateUser(String userName, String userPwd) 
	{		
		OldCisAuthenticationToken	authToken	=	new OldCisAuthenticationToken();
		authToken.setName(userName);
		authToken.setPassword(userPwd);
		return (OldCisAuthenticationToken) authProvider.authenticate(authToken);
	}
	public boolean validateUser(String userName, String userPwd) throws CisApiException 
	{				
		try 
		{
			return cis.validateUser(userName, userPwd);
		} 
		catch (CisClientException e) 
		{
			e.printStackTrace();
			throw new CisApiException();
		}
	}

	/*
	 * Methods to get and set a preference field
	 */
	public void getPreference(String userName, String field, String value)
	{
		return;
	}
	public void setPreference(String userName, String userPwd, String field, String value)
	{
		return;
	}
	
	public boolean isUserPresent(String key) throws CisApiException 
	{
		try 
		{
			return cis.isUserPresent(key);
		} 
		catch (CisClientException e) 
		{
			e.printStackTrace();
			throw new CisApiException();
		}
	}
	
	public void changePwdForUser(String userName, String userPwd,
			String newUserPwd) throws CisApiException 
	{
		try 
		{
			cis.changePassword(userName, userPwd, newUserPwd);
		} 
		catch (CisClientException e) 
		{
			e.printStackTrace();
			throw new CisApiException();
		}		
	}
}
