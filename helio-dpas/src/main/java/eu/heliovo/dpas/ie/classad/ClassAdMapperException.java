/**
 * 
 */
package eu.heliovo.dpas.ie.classad;

/**
 * @author pierantg
 *
 */
public class ClassAdMapperException extends Exception
{
	private static final long serialVersionUID = -5653162248347058351L;
	// added by Vineeth
	String error="";
	public ClassAdMapperException()
	{
		super();             
		error = "unknown";
	}

	public ClassAdMapperException(String err)
	{
		super(err);   
		error = err;  
	}
	public ClassAdMapperException(String err,Throwable tr)
	{
		super(err,tr);   
		error = err;  
	}	

	public String getError()
	{
		return error;
	}
}
