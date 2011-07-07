package eu.heliovo.securityexample.server;

import org.springframework.security.access.annotation.Secured;

public class ExampleServiceImpl implements ExampleService 
{	
	@Override
	@Secured( {"ROLE_ANONYMOUS"} )
	public String method_a(String arg) 
	{
		return "ExampleServiceImpl.method_a processed " + arg;
	}

	@Override
	@Secured( {"ROLE_POWER_USER"} )
	public String method_b(String arg) 
	{
		return "ExampleServiceImpl.method_b processed " + arg;
	}

	@Override
	@Secured( {"ROLE_ANONYMOUS_POWER_USER"} )
	public String method_c(String arg) 
	{
		return "ExampleServiceImpl.method_c processed " + arg;
	}

	@Override
	@Secured( {"ROLE_ADMINISTRATOR"} )
	public String method_d(String arg) 
	{
		return "ExampleServiceImpl.method_d processed " + arg;
	}
}
