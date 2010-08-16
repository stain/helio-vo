package eu.heliovo.dpas.ie.components;

public class DpasOutputFormatterException extends Exception 
{
	private static final long serialVersionUID = 8683539053190688606L;
	
	// added vy Vineeth
	String error="";
	public DpasOutputFormatterException()
	{
		super();             
		error = "unknown";
	}

	public DpasOutputFormatterException(String err)
	{
		super(err);   
		error = err;  
	}
	public DpasOutputFormatterException(String err,Throwable tr)
	{
		super(err,tr);   
		error = err;  
	}	

	public String getError()
	{
		return error;
	}
}
