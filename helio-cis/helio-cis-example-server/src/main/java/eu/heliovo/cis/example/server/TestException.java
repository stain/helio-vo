package eu.heliovo.cis.example.server;

import javax.xml.ws.WebFault;

@WebFault
public class TestException extends Exception 
{
	private static final long serialVersionUID = 4882715104898303383L;

	public TestException(String failureMessage) 
	{
		super(failureMessage);
	}	
}
