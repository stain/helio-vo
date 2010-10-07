package eu.heliovo.dpas.ie.services.directory.dao.exception;


public class PathBuilderException extends Exception 
{
	private static final long serialVersionUID = -2749068623136385293L;
	
	// added vy Vineeth
	String error="";
	public PathBuilderException()
	{
		super();             
		error = "unknown";
	}

	public PathBuilderException(String err)
	{
		super(err);   
		error = err;  
	}
	public PathBuilderException(String err,Throwable tr)
	{
		super(err,tr);   
		error = err;  
	}	

	public String getError()
	{
		return error;
	}
}
