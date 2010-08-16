package eu.heliovo.dpas.ie.providers;

public class DataProviderManagerException extends Exception 
{
	private static final long serialVersionUID = -3780010931569627981L;
	
	// added by Vineeth
	String error="";
	public DataProviderManagerException()
	{
		super();             
		error = "unknown";
	}

	public DataProviderManagerException(String err)
	{
		super(err);   
		error = err;  
	}
	public DataProviderManagerException(String err,Throwable tr)
	{
		super(err,tr);   
		error = err;  
	}	

	public String getError()
	{
		return error;
	}
}
