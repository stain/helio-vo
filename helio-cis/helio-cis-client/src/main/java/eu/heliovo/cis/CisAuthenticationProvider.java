package eu.heliovo.cis;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;

import eu.heliovo.shared.common.utilities.LogUtilities;
import eu.heliovo.shared.common.utilities.SecurityUtilities;
import eu.heliovo.shared.common.utilities.SecurityUtilitiesException;

public class CisAuthenticationProvider implements AuthenticationProvider
{
	/*
	 * Utilities
	 */
	LogUtilities			logUtilities	=	new LogUtilities();
	SecurityUtilities		secUtilities	=	new SecurityUtilities();
	/*
	 * This is the client to the Cis Server
	 */
	CisClient				cis				=	new CisClient();

	@Override
	public Authentication authenticate(Authentication auth) 
	{
		String	userName	=	auth.getPrincipal().toString();
		String	userPwd		=	auth.getCredentials().toString();
		
			try {
				if(cis.validateUser(userName, userPwd))
				{
					OldCisAuthenticationToken	res	=	new OldCisAuthenticationToken();
					res.setAuthenticated(true);
					res.setName(userName);
					res.setPassword(userPwd);
					return res;
				}
				else
					throw new CisAuthenticationException(userName + " IS NOT AUTHORIZED !");
			} 
			catch (IllegalArgumentException e) 
			{
//				e.printStackTrace();
				throw new CisAuthenticationException("CIS raised and exception trying to authenticate " + auth.getName());
			} 
			catch (CisClientException e) 
			{
//				e.printStackTrace();
				throw new CisAuthenticationException("CIS raised and exception trying to authenticate " + auth.getName());
			} 
	}

	@Override
	public boolean supports(Class<? extends Object> arg0) 
	{
		if(arg0.equals(OldCisAuthenticationToken.class))
			return true;
		else
			return false;	}
}