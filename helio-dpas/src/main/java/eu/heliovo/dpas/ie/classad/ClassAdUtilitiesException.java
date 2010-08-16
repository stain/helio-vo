package eu.heliovo.dpas.ie.classad;

public class ClassAdUtilitiesException extends Exception
{
	private static final long serialVersionUID = -916787504330479405L;
	// added by Vineeth
	String error="";
	public ClassAdUtilitiesException()
	{
		super();             
		error = "unknown";
	}

	public ClassAdUtilitiesException(String err)
	{
		super(err);   
		error = err;  
	}
	public ClassAdUtilitiesException(String err,Throwable tr)
	{
		super(err,tr);   
		error = err;  
	}	

	public String getError()
	{
		return error;
	}
}
