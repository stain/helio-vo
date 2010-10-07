package eu.heliovo.dpas.ie.services.directory.dao.exception;


public class NewPathException extends Exception
{
	private static final long serialVersionUID = 8718807175092721588L;
	
	// added vy Vineeth
	String error="";
	public NewPathException()
	{
		super();             
		error = "unknown";
	}

	public NewPathException(String err)
	{
		super(err);   
		error = err;  
	}
	public NewPathException(String err,Throwable tr)
	{
		super(err,tr);   
		error = err;  
	}	

	public String getError()
	{
		return error;
	}
}
