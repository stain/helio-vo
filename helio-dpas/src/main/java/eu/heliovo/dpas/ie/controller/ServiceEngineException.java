package eu.heliovo.dpas.ie.controller;

/**
 * The Class QueryEngineException.
 */
public class ServiceEngineException extends Exception 
{
	private static final long serialVersionUID = -5653162248347058351L;

	String error="";
	public ServiceEngineException()
	{
		super();             
		error = "unknown";
	}

	public ServiceEngineException(String err)
	{
		super(err);   
		error = err;  
	}
	public ServiceEngineException(String err,Throwable tr)
	{
		super(err,tr);   
		error = err;  
	}	

	public String getError()
	{
		return error;
	}
}
