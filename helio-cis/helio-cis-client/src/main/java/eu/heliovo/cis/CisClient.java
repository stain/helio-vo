package eu.heliovo.cis;

import javax.xml.ws.BindingProvider;

import eu.heliovo.cis.service.CisService;
import eu.heliovo.cis.service.CisServiceException_Exception;
import eu.heliovo.cis.service.CisServiceService;
import eu.heliovo.shared.common.utilities.LogUtilities;
import eu.heliovo.shared.common.utilities.SecurityUtilities;
import eu.heliovo.shared.common.utilities.SecurityUtilitiesException;

public class CisClient 
{
	/*
	 * Service address
	 */
	String				cisServiceAddress			=	null;
	/*
	 * The Service Stubs
	 */
	CisServiceService	cisSS			=	new CisServiceService();
	CisService			cisService		=	null;
	/*
	 * Local address
	 */
	String	defaultCisServiceAddress	=	"http://localhost:8080/helio-cis-server/cisService";	
	/*
	 * Remote address
	 */
//	String	defaultCisServiceAddress	=	"http://cagnode58.cs.tcd.ie:8080/helio-cis-server/cisService";
	/*
	 * Utilities
	 */
	LogUtilities			logUtilities	=	new LogUtilities();
	SecurityUtilities		secUtilities	=	new SecurityUtilities();
	
	public CisClient() 
	{
		super();
		logUtilities.printShortLogEntry("[CisClient] - Default constructor...");
		this.cisServiceAddress = this.defaultCisServiceAddress;
		/*
		 * Creating stubs for the CIS Service
		 */
		cisService	=	cisSS.getCisServicePort();			
		((BindingProvider)cisService).getRequestContext().put(
				BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
				cisServiceAddress);
		logUtilities.printShortLogEntry("[CisClient] - ... done");
	}

//	public CisClient(String cisServiceAddress) 
//	{
//		super();
//		this.cisServiceAddress = cisServiceAddress;
//	}

	public boolean isRunning() 
	{
		return (cisService.test("test") != null);
	}

	public boolean isUserPresent(String userName) throws CisClientException 
	{
		try 
		{
			return cisService.isUserPresent(userName);
		} 
		catch (CisServiceException_Exception e) 
		{
			throw new CisClientException();
		}
	}

	public void addUser(String userName, String userPwd) throws CisClientException 
	{
		try 
		{
			cisService.addUser(userName, secUtilities.computeHashOf(userPwd));
			return;
		} 
		catch (CisServiceException_Exception e) 
		{
			throw new CisClientException();
		} 
		catch (SecurityUtilitiesException e) 
		{
			throw new CisClientException();
		}		
	}

	public void removeUser(String userName, String userPwd) throws CisClientException 
	{
		try 
		{
			cisService.removeUser(userName, secUtilities.computeHashOf(userPwd));
			return;
		} 
		catch (CisServiceException_Exception e) 
		{
			throw new CisClientException();
		} 
		catch (SecurityUtilitiesException e) 
		{
			throw new CisClientException();
		}				
	}

	public boolean validateUser(String userName, String userPwd) throws CisClientException 
	{
		try 
		{
			return cisService.validateUser(userName, secUtilities.computeHashOf(userPwd));
		} 
		catch (SecurityUtilitiesException e) 
		{
			throw new CisClientException();
		}						
	}
}
