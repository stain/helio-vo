package eu.heliovo.cis;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import eu.heliovo.cis.service.CisService;
import eu.heliovo.cis.service.CisServiceException;
import eu.heliovo.shared.common.cis.hit.HITPayload;
import eu.heliovo.shared.common.utilities.SecurityUtilities;
import eu.heliovo.shared.common.utilities.SecurityUtilitiesException;
import eu.heliovo.shared.util.FileUtil;

public class CisClient
{
	String						configurationFile	=	"cisClient.conf.txt";
	
	SecurityUtilities			secUtilities	=	new SecurityUtilities();
	FileUtil					fileUtilities	=	new FileUtil();
	
	public 	String				serviceAddress	=	"http://localhost:8080/helio-cis-server/cisService";
	public	Class<CisService>	serviceClass	=	CisService.class;
	
	
	public CisClient() 
	{
		super();
		
		String cwd;
		try {
			cwd = new java.io.File( "." ).getCanonicalPath();
			System.out.println(cwd);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try 
		{
		    BufferedReader in = new BufferedReader(new FileReader(configurationFile));
			serviceAddress = in.readLine();
		    in.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
	}

	public boolean validateUser(String user, String pwd) throws Exception 
	{
	   	JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
    	factory.setServiceClass(serviceClass);
    	factory.setAddress(serviceAddress);
    	CisService client = (CisService) factory.create();
    	return	client.validateUser(user, secUtilities.computeHashOf(pwd));    	
	}

	public void addUser(String user, String pwd) throws Exception 
	{
	   	JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
    	factory.setServiceClass(serviceClass);
    	factory.setAddress(serviceAddress);
    	CisService client = (CisService) factory.create();
    	client.addUser(user, secUtilities.computeHashOf(pwd));    			
	}

	public boolean isUserPresent(String user) 
	{
	   	JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
    	factory.setServiceClass(serviceClass);
    	factory.setAddress(serviceAddress);
    	CisService client = (CisService) factory.create();
    	try 
    	{
			return	client.isUserPresent(user);
		} 
    	catch (Exception e) 
    	{
			return false;
		} 
	}

	public HITPayload getLowSecurityHITFor(String user, String pwd) throws Exception 
	{
	   	JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
    	factory.setServiceClass(serviceClass);
    	factory.setAddress(serviceAddress);
    	CisService client = (CisService) factory.create();
    	return client.getLowSecurityHITFor(user, secUtilities.computeHashOf(pwd));    			
	}

	public boolean setStringPreferencesFor(String user, 
			String	password,
			String 	service, 
			String  element,
			String 	value) throws Exception
	{
	   	JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
    	factory.setServiceClass(serviceClass);
    	factory.setAddress(serviceAddress);
    	CisService client = (CisService) factory.create();
    	return client.setStringPreferencesFor(user, 
    			secUtilities.computeHashOf(password),
    			service,
    			element,
    			value);    			
	}

	public boolean removeUser(String user, String password) 
	{
	   	JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
    	factory.setServiceClass(serviceClass);
    	factory.setAddress(serviceAddress);
    	CisService client = (CisService) factory.create();
    	try 
    	{
			client.removeUser(user,secUtilities.computeHashOf(password));
		} 
    	catch (CisServiceException e) 
    	{
			e.printStackTrace();
			return false;
		} 
    	catch (SecurityUtilitiesException e) 
    	{
			e.printStackTrace();
			return false;
		}    			
    	return true;
	}
}