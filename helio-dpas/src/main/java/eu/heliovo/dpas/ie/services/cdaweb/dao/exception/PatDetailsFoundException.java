/* #ident	"%W%" */
package eu.heliovo.dpas.ie.services.cdaweb.dao.exception;

public class PatDetailsFoundException extends Exception {
	private static final long serialVersionUID = 1L;
	String error="";
	public PatDetailsFoundException()
	{
		super();             
		error = "unknown";
	}

	public PatDetailsFoundException(String err)
	{
		super(err);   
		error = err;  
	}
	public PatDetailsFoundException(String err,Throwable tr)
	{
		super(err,tr);   
		error = err;  
	}	

	public String getError()
	{
		return error;
	}
}
