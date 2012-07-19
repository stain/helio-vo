package eu.heliovo.shared.common;

public class LocalScriptManager 
{
	/*
	 * MyProxy Related Scripts
	 */
	private static	String	validateProxyScript	=	"grid/validate-proxy.sh";
	private static	String	retrieveProxyScript	=	"grid/retrieve-proxy.sh";
	/*
	 * WMS Related Scripts
	 */
	private static	String	submitGridJobScript	=	"grid/submit-job.sh";
	private static 	String 	statusGridJobScript	=	"grid/get-status-of-job.sh";
	/*
	 * Local Scripts
	 */
	private static	String	publishOutputScript	=	"local/publish-output.sh";
	
	public static String getValidateProxyScript() 
	{
		return LocalSystemConfiguration.getScriptRepository()+validateProxyScript;
	}
	
	public static String getRetrieveProxyScript() 
	{
		return LocalSystemConfiguration.getScriptRepository()+retrieveProxyScript;
	}

	public static String getPublishOutputScript() 
	{
		return LocalSystemConfiguration.getScriptRepository()+publishOutputScript;
	}

	public static String getSubmitGridJobScript() 
	{
		return LocalSystemConfiguration.getScriptRepository()+submitGridJobScript;
	}

	public static String getGridJobStatusScript() 
	{
		return LocalSystemConfiguration.getScriptRepository()+statusGridJobScript;
	}
}
