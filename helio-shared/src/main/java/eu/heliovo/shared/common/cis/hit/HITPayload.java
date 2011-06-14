package eu.heliovo.shared.common.cis.hit;

public class HITPayload 
{
	/** The credential
	 * 
	 * It consists of a grid proxy certificate
	 *  
	 *  */
	String				credential		=	null;
	/** The HITInfo - The Information of the HIT
	 * 
	 * It contains all the information that is not contained
	 * in the high security component
	 *  
	 *  */
	String				information		=	null;
	
	public String getCredential() 
	{
		return credential;
	}
	public void setCredential(String credential) 
	{
		this.credential = credential;
	}
	public String getInformation() 
	{
		return information;
	}
	public void setInformation(String information) 
	{
		this.information = information;
	}
}
