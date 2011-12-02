package eu.heliovo.cis;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class CisAuthenticationToken implements Authentication 
{
	private static final long serialVersionUID = 5908115257139075651L;
	String	userName	=	null;
	String	userPwd		=	null;
	
	public CisAuthenticationToken(String userName, String userPwd) 
	{
		super();
		this.userName 	= userName;
		this.userPwd 	= userPwd;
	}

	@Override
	public String getName() 
	{
		return userName;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() 
	{
		return null;
	}

	@Override
	public Object getCredentials() 
	{
		return userPwd;
	}

	@Override
	public Object getDetails() 
	{
		return null;
	}

	@Override
	public Object getPrincipal() 
	{
		return null;
	}

	@Override
	public boolean isAuthenticated() 
	{
		return false;
	}

	@Override
	public void setAuthenticated(boolean arg0) throws IllegalArgumentException 
	{
		
	}
}
