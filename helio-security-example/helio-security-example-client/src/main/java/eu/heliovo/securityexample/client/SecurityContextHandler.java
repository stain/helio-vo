package eu.heliovo.securityexample.client;

import javax.security.cert.X509Certificate;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

public class SecurityContextHandler 
{
	public	void setSecurityContext(String userName, String userPwd) throws SecureClientException
	{
		final ApplicationContext context	=	new ClassPathXmlApplicationContext("client/spring-http-client-config.xml");
		SecurityContextImpl sc = new SecurityContextImpl();
		Authentication auth = new UsernamePasswordAuthenticationToken(userName,	userPwd);

		sc.setAuthentication(auth);
		SecurityContextHolder.setContext(sc);
	}

	public	void setSecurityContext(String helioUserName, String helioPwd,
			String gridUserName, String gridPwd, String myProxyServer) throws SecureClientException
	{
		throw new SecureClientException("Method not implemented yet !");
		
		/*
		final ApplicationContext context	=	new ClassPathXmlApplicationContext("client/spring-http-client-config.xml");
		*/
		/*
		SecurityContextImpl sc = new SecurityContextImpl();
		Authentication auth = new UsernamePasswordAuthenticationToken(userName,	userPwd);

		sc.setAuthentication(auth);
		SecurityContextHolder.setContext(sc);
		*/	
	}

	public	void setSecurityContext(String certificateFileLocation) throws SecureClientException
	{
		throw new SecureClientException("Method not implemented yet !");
		
		/*
		final ApplicationContext context	=	new ClassPathXmlApplicationContext("client/spring-http-client-config.xml");
		*/
		/*
		SecurityContextImpl sc = new SecurityContextImpl();
		Authentication auth = new UsernamePasswordAuthenticationToken(userName,	userPwd);

		sc.setAuthentication(auth);
		SecurityContextHolder.setContext(sc);
		*/	
	}

	public	void setSecurityContext(X509Certificate certificate) throws SecureClientException
	{
		throw new SecureClientException("Method not implemented yet !");
		/*
		final ApplicationContext context	=	new ClassPathXmlApplicationContext("client/spring-http-client-config.xml");
		*/
		/*
		SecurityContextImpl sc = new SecurityContextImpl();
		Authentication auth = new UsernamePasswordAuthenticationToken(userName,	userPwd);

		sc.setAuthentication(auth);
		SecurityContextHolder.setContext(sc);
		*/	
	}
}

