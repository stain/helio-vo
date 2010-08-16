package eu.heliovo.dpas.ie.providers;

public class DPASDataProviderException extends Exception 
{
	private static final long serialVersionUID = -7807443650603064242L;
	
	
	// added by Vineeth
	String error="";
	public DPASDataProviderException()
	{
		super();             
		error = "unknown";
	}

	public DPASDataProviderException(String err)
	{
		super(err);   
		error = err;  
	}
	public DPASDataProviderException(String err,Throwable tr)
	{
		super(err,tr);   
		error = err;  
	}	

	public String getError()
	{
		return error;
	}

}
