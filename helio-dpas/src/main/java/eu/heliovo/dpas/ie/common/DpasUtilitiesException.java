package eu.heliovo.dpas.ie.common;

public class DpasUtilitiesException extends Exception 
{
	private static final long serialVersionUID = 7794173351994415767L;
	
	// added vy Vineeth
	String error="";
	public DpasUtilitiesException()
	{
		super();             
		error = "unknown";
	}

	public DpasUtilitiesException(String err)
	{
		super(err);   
		error = err;  
	}
	public DpasUtilitiesException(String err,Throwable tr)
	{
		super(err,tr);   
		error = err;  
	}	

	public String getError()
	{
		return error;
	}
}
