package eu.heliovo.shared.common.utilities;

import java.io.Serializable;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class HIT extends UsernamePasswordAuthenticationToken implements Serializable
{
	private static final long serialVersionUID = 3065244006109549256L;
	private	String	info	=	null;
	
	public HIT(Object principal, 
			Object credentials,
			String	info) 
	{
		super(principal, credentials);
		this.info	=	info;
	}

	public String getInfo() 
	{
		return info;
	}

	@Override
	public String toString() 
	{
		return "[HIT : " + getPrincipal() + "," + getCredentials() + ", " + getInfo() + "]"; 
	}	
}
