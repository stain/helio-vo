package eu.heliovo.cis;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class OldCisAuthenticationToken implements Authentication 
{
	private static final long 			serialVersionUID 	= 5908115257139075651L;
	private	boolean						authenticated		=	false;
	private	Object						principal			=	null;
	private String 						name				=	null;
	private String 						password			=	null;
	private	List<GrantedAuthority> 		AUTHORITIES			=	null;

	@Override
	public String getName() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getCredentials() 
	{
		return password;
	}

	@Override
	public Object getDetails() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getPrincipal() 
	{
		return name;
	}

	@Override
	public boolean isAuthenticated() 
	{
		return authenticated;
	}

	@Override
	public void setAuthenticated(boolean arg0) throws IllegalArgumentException 
	{
		authenticated	=	arg0;
	}
	
	public void setName(String userName) 
	{
		this.name		=	userName;
	}

	public void setPassword(String userPwd) 
	{
		this.password	=	userPwd;
	}

}
