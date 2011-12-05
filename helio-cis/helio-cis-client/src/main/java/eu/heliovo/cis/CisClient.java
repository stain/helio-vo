package eu.heliovo.cis;

import java.net.URL;

import javax.xml.ws.BindingProvider;

import org.apache.log4j.Logger;

import eu.heliovo.cis.service.CisService;
import eu.heliovo.cis.service.CisServiceException_Exception;
import eu.heliovo.cis.service.CisServiceService;
import eu.heliovo.shared.common.utilities.SecurityUtilities;
import eu.heliovo.shared.common.utilities.SecurityUtilitiesException;
import eu.heliovo.shared.props.HelioFileUtil;

/**
 * Create a CIS client to access the CIS service. 
 * This class acts as facade to the underlying {@link CisServiceService}.
 * @author MarcoSoldati
 *
 */
public class CisClient 
{
	/**
	 * The service address that will be used by the client.
	 */
	private final URL cisServiceAddress;
	
	/**
	 * The Service Stub
	 */
	private final CisServiceService cisSS;
	
	/**
	 * The port to access the service.
	 */
	private final CisService cisService;
	
	/**
	 * Local address
	 */
	//private final static URL DEFAULT_CIS_SERVICE_ADDRESS = HelioFileUtil.asURL("http://localhost:8080/helio-cis-server/cisService");	

	/**
	 * Remote address
	 */
	private final static URL DEFAULT_CIS_SERVICE_ADDRESS = HelioFileUtil.asURL("http://cagnode58.cs.tcd.ie:8080/helio-cis-server/cisService");
	
	/**
	 * a happy little logger
	 */
	private static final Logger LOGGER = Logger.getLogger(CisClient.class);
	
	/**
	 * Security utilities
	 */
	SecurityUtilities secUtilities = new SecurityUtilities();
	
	/**
	 * Create a CIS client and use the default service address.
	 */
	public CisClient() {
	    this(null);
	}

	/**
	 * Create a CIS client and use the submitted service address. 
	 * @param cisServiceAddress the address to use. if null, the default service address will be used.
	 */
	public CisClient(URL cisServiceAddress) {
	    super();
	    if (LOGGER.isTraceEnabled()) {
	        LOGGER.trace("Entering Constructor");
	    }
        this.cisServiceAddress = cisServiceAddress == null ? DEFAULT_CIS_SERVICE_ADDRESS : cisServiceAddress;
        
        //Creating stubs for the CIS Service
        cisSS = new CisServiceService(this.cisServiceAddress);
        cisService = cisSS.getCisServicePort();          
        ((BindingProvider)cisService).getRequestContext().put(
                BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
                cisServiceAddress);
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Exiting Constructor");
        }
	}

	/**
	 * Test if the connected remote service is running.
	 * @return true if the service is running
	 */
	public boolean isRunning() 
	{
		return (cisService.test("test") != null);
	}

	/**
	 * Test if a user with the given name exists.
	 * @param userName the users name
	 * @return true if the user exists.
	 * @throws CisClientException
	 */
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

	/**
	 * Create a new user.
	 * @param userName name of the user.
	 * @param userPwd password of the user. Will be hashed before transfered to the endpoint.
	 * @throws CisClientException
	 */
	public void addUser(String userName, String userPwd) throws CisClientException 
	{
		try 
		{
			cisService.addUser(userName, secUtilities.computeHashOf(userPwd));
			return;
		} 
		catch (CisServiceException_Exception e) 
		{
			throw new CisClientException(e);
		} 
		catch (SecurityUtilitiesException e) 
		{
			throw new CisClientException(e);
		}		
	}

	/**
	 * Remove an existing user.
	 * @param userName name of the user
	 * @param userPwd password of the user. Will be hashed before being sent to the server.
	 * @throws CisClientException
	 */
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

	/**
	 * Validate a users password.
	 * @param userName the name of the user
	 * @param userPwd the password of the user.
	 * @return true if the password is valid.
	 * @throws CisClientException 
	 */
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
	
	/**
	 * Change the password of a user. TODO: implement
	 * @param userName the name of the user
	 * @param oldPwd the oldPw. Will be hashed by this implementation.
	 * @param newPwd the newPw. Will be hashed by this implementation.
	 * @return true if the password has been changed, false otherwise (because the old pw did not match).
	 * @throws CisClientException if the oldPwd does not match.
	 */
	public boolean changePassword(String userName, String oldPwd, String newPwd) throws CisClientException {
	    return false;
	}
}
