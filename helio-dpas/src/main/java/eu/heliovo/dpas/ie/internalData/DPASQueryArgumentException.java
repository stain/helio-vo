package eu.heliovo.dpas.ie.internalData;

public class DPASQueryArgumentException extends Exception
{
	private static final long serialVersionUID = 3433773331624045552L;
	
	// added by Vineeth
	String error="";
	public DPASQueryArgumentException()
	{
		super();             
		error = "unknown";
	}

	public DPASQueryArgumentException(String err)
	{
		super(err);   
		error = err;  
	}
	public DPASQueryArgumentException(String err,Throwable tr)
	{
		super(err,tr);   
		error = err;  
	}	

	public String getError()
	{
		return error;
	}
}
