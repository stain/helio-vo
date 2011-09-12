package eu.heliovo.example.server;

import javax.jws.WebService;

import org.springframework.security.access.annotation.Secured;

@WebService(endpointInterface="eu.heliovo.example.server.ExampleService", serviceName = "ExampleService")
public class ExampleServiceImpl implements ExampleService 
{
	@Override
	@Secured("ROLE_heliouser")
	public TestResult testOK(String testParameter) 
	{
		return new TestResult("Test Performed Correctly");
	}

	@Override
	public TestResult testKO(String testParameter) throws TestException 
	{
		throw new TestException("Test Failed");
	}
}
