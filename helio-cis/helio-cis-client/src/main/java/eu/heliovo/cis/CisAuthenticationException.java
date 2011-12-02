package eu.heliovo.cis;

import org.springframework.security.core.AuthenticationException;

public class CisAuthenticationException extends AuthenticationException 
{
	private static final long serialVersionUID = -5420959338671747679L;
	
	public CisAuthenticationException(String string) 
	{
		super(string);
	}
}
