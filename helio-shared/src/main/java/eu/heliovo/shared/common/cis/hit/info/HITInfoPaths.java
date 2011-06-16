package eu.heliovo.shared.common.cis.hit.info;

public class HITInfoPaths 
{
	public static String	separator		=	".";

	static public String	uid				=	HITInfoTags.info+separator+HITInfoTags.uid;
	public static String 	createdOn		=	HITInfoTags.info+separator+HITInfoTags.createdOn;

	public static String 	myProxyServer	=	HITInfoTags.myproxy+separator+HITInfoTags.server;
	public static String 	myProxyPort		=	HITInfoTags.myproxy+separator+HITInfoTags.port;

	public static String 	idHelio			=	HITInfoTags.identity+separator+HITInfoTags.helio;
	public static String 	idCert			=	HITInfoTags.identity+separator+HITInfoTags.cert;
}
