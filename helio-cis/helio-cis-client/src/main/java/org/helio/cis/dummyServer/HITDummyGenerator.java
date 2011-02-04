package org.helio.cis.dummyServer;

import java.io.File;

import org.globus.gsi.GlobusCredential;
import org.globus.gsi.GlobusCredentialException;
import org.helio.cis.common.utilities.FileUtilities;
import org.helio.cis.common.utilities.FileUtilitiesException;
import org.helio.cis.common.utilities.TimeUtilities;
import org.helio.cis.hit.HelioIdentityToken;

public class HITDummyGenerator implements HITGenerator 
{
	String			defaultProxyFileName	=	"/tmp/dummyProxy";
	String			confFileName			=	"./SimpleCisTest.conf";
	
	TimeUtilities	tUtils					=	new TimeUtilities();
	FileUtilities	fUtils					=	new FileUtilities();

	@Override
	public HelioIdentityToken generate(boolean strongSecurity) throws Exception 
	{		
		if(strongSecurity)
		{
			String	proxyFileName	=	getProxyFileLocation();
			
			return	new HelioIdentityToken(
				createCertFromFile(proxyFileName), 
				"dummy-security-component", 
				"dummy-user-profile");
		}
		else
			return	new HelioIdentityToken("dummy-security-component", 
			"dummy-user-profile");	
	}

	@Override
	public HelioIdentityToken generate(String userName, boolean strongSecurity) throws Exception 
	{	
		if(strongSecurity)
		{
			String	proxyFileName	=	getProxyFileLocation();		
		
			return	new HelioIdentityToken(
				createCertFromFile(proxyFileName), 
				"dummy-security-component", 
				"dummy-user-profile");	
		}
		else
			return	new HelioIdentityToken("dummy-security-component", 
			"dummy-user-profile");	
	}
	
	private	GlobusCredential	createCertFromFile(String filename) throws Exception
	{
		/*
		 * Test if the proxy file exists or not
		 */
		File	proxyFile	=	new File(filename);

		if(!proxyFile.isFile() || !proxyFile.canRead())
		{
			System.out.println(tUtils.getShortStamp() + " File " + 
					filename + 
					" either does not exist or cannot be read; aborting test...");
			throw new Exception();
		}
		GlobusCredential	gcTest 	=	null;

		try 
		{
			gcTest 	=	new GlobusCredential(getProxyFileLocation());
		} 
		catch (GlobusCredentialException e) 
		{
			e.printStackTrace();
		}

		return 	gcTest;
	}
	
	private	String	getProxyFileLocation()
	{
		/*
		 * Open file...
		 */
		File	confFile	=	new File(confFileName);
		/*
		 * If the file exists and can be read
		 */
		if(confFile.isFile() &&  confFile.canRead())
		{
			/*
			 * Open the file 
			 */
			try 
			{
				return 	fUtils.readFromFile(confFile).trim();
			} 
			catch (FileUtilitiesException e) 
			{
				return defaultProxyFileName;
			}
		}
		else
			return defaultProxyFileName;
	}
}
