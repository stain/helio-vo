/* #ident	"%W%" */
package eu.heliovo.dpas.ie.services.directory.dao.exception;

public class UrlNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;
	String error="";
	public UrlNotFoundException()
	{
		super();             
		error = "unknown";
	}

	public UrlNotFoundException(String err)
	{
		super(err);   
		error = err;  
	}
	public UrlNotFoundException(String err,Throwable tr)
	{
		super(err,tr);   
		error = err;  
	}	

	public String getError()
	{
		return error;
	}
}
