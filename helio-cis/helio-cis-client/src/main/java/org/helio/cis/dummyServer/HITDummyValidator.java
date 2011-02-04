
package org.helio.cis.dummyServer;

import org.globus.gsi.GlobusCredential;
import org.globus.gsi.GlobusCredentialException;
import org.helio.cis.common.utilities.TimeUtilities;
import org.helio.cis.hit.HelioIdentityToken;
import org.helio.cis.hit.HelioIdentityTokenException;

public class HITDummyValidator implements HITValidator 
{
	TimeUtilities			tUtils	=	new TimeUtilities();
	
	public boolean isValid(HelioIdentityToken hit) 
	{
		return isHSCValid(hit) && isLSCValid(hit);
	}

	public boolean isValid(HelioIdentityToken hit, boolean strongValidation) 
	{
		if(strongValidation)	
			return isValid(hit);
		else
			return isLSCValid(hit);
	}

	private boolean isLSCValid(HelioIdentityToken hit) 
	{
		return true;
	}

	private boolean isHSCValid(HelioIdentityToken hit) 
	{
		GlobusCredential	gcTest 		=	null;
		boolean				hscValidity	=	false;
		
		/*
		 * Getting the HSC component of the HIT
		 */
		try 
		{
			gcTest 	=	hit.getHSC();
		} 
		catch (HelioIdentityTokenException e1) 
		{
			System.out.println("[" + tUtils.getShortStamp() + "] --> HSC component not present in this HIT ...");
		}

		
		try 
		{
			gcTest.verify();
			hscValidity	=	true;
		} 
		catch (Exception e) 
		{
			System.out.println("[" + tUtils.getShortStamp() + "] --> Proxy cannot be verified for the following reason :");
			System.out.println("------------------------------------------------------------------------------------");
			e.printStackTrace();
			System.out.println("------------------------------------------------------------------------------------");
			/*
			 * Now detailing which kind of exception happened...
			 */
			GlobusCredentialException	ge	=	(GlobusCredentialException)e;
			System.out.println("[" + tUtils.getShortStamp() + "] --> Error code : " + ge.getErrorCode());
			
		}

		return hscValidity;
	}
	
	public String	print(HelioIdentityToken hit)
	{
		String	result	=	new String();
		result	=	"Low Security Component : " + hit.getLSC();
		result  +=  "\n";
		result  +=  "User Profile : " + hit.getUserProfile();
		result  +=  "\n";
		if(hit.isHighSecurity())
		{
			result  += "The HIT has the following high security component";
			result  +=  "\n";
			GlobusCredential	gcTest 		=	null;
			try 
			{
				gcTest 	=	hit.getHSC();
			} 
			catch (HelioIdentityTokenException e1) 
			{
				System.out.println("[" + tUtils.getShortStamp() + "] --> HSC component not present in this HIT ...");
			}
			result += gcTest.toString();
		}

		return result;
	}
}
