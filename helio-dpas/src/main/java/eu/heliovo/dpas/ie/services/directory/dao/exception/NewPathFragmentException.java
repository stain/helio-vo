package eu.heliovo.dpas.ie.services.directory.dao.exception;


public class NewPathFragmentException extends Exception
{
	private static final long serialVersionUID = -8514728056228954311L;
	
	// added vy Vineeth
	String error="";
	public NewPathFragmentException()
	{
		super();             
		error = "unknown";
	}

	public NewPathFragmentException(String err)
	{
		super(err);   
		error = err;  
	}
	public NewPathFragmentException(String err,Throwable tr)
	{
		super(err,tr);   
		error = err;  
	}	

	public String getError()
	{
		return error;
	}
}
