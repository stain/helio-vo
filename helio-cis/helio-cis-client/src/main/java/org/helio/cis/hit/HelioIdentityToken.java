/*
 * 
 */
package org.helio.cis.hit;

import java.util.Date;

import org.globus.gsi.GlobusCredential;
import org.globus.gsi.GlobusCredentialException;
import org.helio.cis.common.utilities.TimeUtilities;

/**
 * The Class HelioIdentityToken.
 */
public class HelioIdentityToken 
{	
	TimeUtilities	tUtils	=	new TimeUtilities();
	/** The HSC - High Security Component
	 * 
	 * It consists of a grid proxy certificate
	 *  
	 *  */
	GlobusCredential	HSC		=	null;
	/** The LSC - Low Security Component
	 * 
	 * It consists of a string representing a classad expression
	 *  
	 *  */
	String				LSC		=	null;
	/** highSecurity
	 * 
	 * Boolean value that is true only if the HIT has the High Security Component
	 *  
	 *  */		
	boolean				highSecurity	=	false;
	/** The User Profile - User profile - not yet used
	 * 
	 *  It consists of a string representing a classad expression
	 *  
	 *  */
	String				userProfile		=	null;
	/**
	 * Instantiates a new HELIO identity token.
	 *
	 * @param cert the cert
	 * @param uid the uid
	 */
	public HelioIdentityToken(GlobusCredential cert, 
			String lsc, 
			String userProfile) 
	{
		super();
		this.HSC = cert;
		this.LSC = lsc;
		this.highSecurity = true;
		this.userProfile  = userProfile;
	}

	/**
	 * Instantiates a new HELIO identity token.
	 *
	 * @param cert the cert
	 * @param uid the uid
	 */
	public HelioIdentityToken(String lsc, String userProfile) 
	{
		super();
		this.LSC = lsc;
		this.highSecurity = false;
		this.userProfile  = userProfile;
	}
	/**
	 * Gets the hSC.
	 *
	 * @return the hSC
	 * @throws HelioIdentityTokenException the helio identity token exception
	 */
	public GlobusCredential getHSC() throws HelioIdentityTokenException 
	{
		if(!highSecurity)
			throw new HelioIdentityTokenException("No high security component available in this HIT");
		else
			return HSC;
	}
	/**
	 * Gets the lSC.
	 *
	 * @return the lSC
	 */
	public String getLSC() 
	{
		return LSC;
	}	
	/**
	 * Checks if is high security.
	 *
	 * @return true, if is high security
	 */
	public boolean isHighSecurity() 
	{
		return highSecurity;
	}

	/**
	 * Gets the user profile.
	 *
	 * @return the user profile
	 */
	public String getUserProfile() 
	{
		return userProfile;
	}

	/**
	 * Sets the user profile.
	 *
	 * @param userProfile the new user profile
	 */
	public void setUserProfile(String userProfile) 
	{
		this.userProfile = userProfile;
	}

	
	public	String	toShortString()
	{
		if(highSecurity)
			return "HIT(High Security)";
		else
			return "HIT(Low Security)";
	}

	public	String	toLongString()
	{
		return toShortString();
	}

	@Override
	public String toString() 
	{
		return toShortString();
	}	
}
