package eu.heliovo.shared.common;

public class LocalSystemConfiguration 
{
	/*
	 * Repositories locations
	 * 
	 * TODO : Load them from beans or from configuration file
	 */
	private static	String	scriptRepository		=	"/usr/share/apache-tomcat-7.0.16/tomcat/scripts/";
//	private static	String	userProxyRepository		=	"/tmp/userProxyes/";
	private static	String	userProxyRepository		=	"/tmp/userProxyes/";
//	private static	String	userProxyRepository		=	"/tmp/";
	private static	String	userFileRepository		=	"/tmp/userFiles/";
//	private static	String	userFileRepository		=	"/tmp/userFiles/";
	private static	String	userExecutionArea		=	"/tmp/userFiles/";
//	private static	String	userExecutionArea		=	"/usr/share/apache-tomcat-7.0.16/tomcat/";
	private static	String	outputStageRepository	=	"/var/www/html/output_dir/";
	private static	String	outputStageURL			=	"http://cagnode58.cs.tcd.ie/output_dir/";

	public static String getProxyRepository() 
	{
		return userProxyRepository;
	}

	public static String getScriptRepository() 
	{
		return scriptRepository;
	}

	public static String getUserFileRepository() 
	{
		return userFileRepository;
	}

	public static String getLocalOutputArea() 
	{
		return outputStageRepository;
	}

	public static String getLocalOutputURL() 
	{
		return outputStageURL;
	}

	public static String getLocalExecutionArea() 
	{
		return userExecutionArea;
	}
}
