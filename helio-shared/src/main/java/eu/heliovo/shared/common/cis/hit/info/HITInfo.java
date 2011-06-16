package eu.heliovo.shared.common.cis.hit.info;

import java.util.Date;

import condor.classad.Constant;
import condor.classad.RecordExpr;
import eu.heliovo.shared.common.utilities.ClassAdUtilities;
import eu.heliovo.shared.common.utilities.ClassAdUtilitiesException;
import eu.heliovo.shared.common.utilities.UniqueIdentifierUtilities;

/*
 * The information of the HIT is structured in the following schema:
 * 
 * info 				-	Information specific to the hit
 * info.type			-	Whether it is high or low security
 * info.uid				-	Unique identifier of this HIT
 * info.dates			- 	Date Information of this HIT
 * info.createdOn		-	Creation date of this HIT
 * info.validUntil		-	Validity date of this HIT (Not used yet)
 * 
 * identity 			- 	The identity of the user
 * identity.helio		-	The HELIO identity of the user
 * identity.cert		-	The Certificate identity
 * 
 * myproxy 				- 	Information of myproxy
 * myproxy.server		-	The myproxy server
 * myproxy.port			-	The myproxy port
 *
 * preferences			- 	The user preferences
 * 
 */

public class HITInfo 
{
	/*
	 * UID utilities
	 */
	UniqueIdentifierUtilities	uidUtils		=	new UniqueIdentifierUtilities();
	/*
	 * ClassAd utilities
	 */
	ClassAdUtilities			cadUtils		=	new ClassAdUtilities();
	/*
	 * The description of the HIT in ClassAd
	 */
	RecordExpr					cadDescription	=	null;
		
	public HITInfo() 
	{
		super();
		initialize(null);
	}

	public HITInfo(String id) 
	{
		super();
		initialize(id);
	}

	public HITInfo(String id, String server, int port) 
	{
		super();
		initialize(id, server, port);
	}

	/*
	 * Creates the skeleton of the information for the identity token
	 */
	private	void	initialize(String id)
	{
		cadDescription	=	new RecordExpr();
		/*
		 * Creating the information structure
		 */
		RecordExpr	cInfo	=	new RecordExpr();
		cInfo.insertAttribute(HITInfoTags.uid, Constant.getInstance(uidUtils.createUID()));
		cInfo.insertAttribute(HITInfoTags.createdOn, Constant.getInstance(new Date()));
		cadDescription.insertAttribute(HITInfoTags.info, cInfo);
		/*
		 * Creating the myproxy structure
		 */
		RecordExpr	mpInfo	=	new RecordExpr();
		mpInfo.insertAttribute(HITInfoTags.server, Constant.getInstance("cagraidsvr20.cs.tcd.ie"));
		mpInfo.insertAttribute(HITInfoTags.port, Constant.getInstance(7512));
		cadDescription.insertAttribute(HITInfoTags.myproxy, mpInfo);
		/*
		 * Creating the identity structure
		 */
		RecordExpr	idInfo	=	new RecordExpr();
		if(id == null)
		{
			idInfo.insertAttribute(HITInfoTags.helio, Constant.Undef);
			idInfo.insertAttribute(HITInfoTags.cert, Constant.Undef);
		}
		else
		{
			idInfo.insertAttribute(HITInfoTags.helio, Constant.getInstance(id));
			idInfo.insertAttribute(HITInfoTags.cert, Constant.getInstance(id));			
		}
		cadDescription.insertAttribute(HITInfoTags.identity, idInfo);
		/*
		 * Creating the profile structure
		 */
		cadDescription.insertAttribute(HITInfoTags.profile, Constant.Undef);
	}

	/*
	 * Creates the skeleton of the information for the identity token, proxy server, and proxy port
	 */
	private	void	initialize(String id, String proxyServer, int proxyPort)
	{
		cadDescription	=	new RecordExpr();
		/*
		 * Creating the information structure
		 */
		RecordExpr	cInfo	=	new RecordExpr();
		cInfo.insertAttribute(HITInfoTags.uid, Constant.getInstance(uidUtils.createUID()));
		cInfo.insertAttribute(HITInfoTags.createdOn, Constant.getInstance(new Date()));
		cadDescription.insertAttribute(HITInfoTags.info, cInfo);
		/*
		 * Creating the myproxy structure
		 */
		RecordExpr	mpInfo	=	new RecordExpr();
		mpInfo.insertAttribute(HITInfoTags.server, Constant.getInstance(proxyServer));
		mpInfo.insertAttribute(HITInfoTags.port, Constant.getInstance(proxyPort));
		cadDescription.insertAttribute(HITInfoTags.myproxy, mpInfo);
		/*
		 * Creating the identity structure
		 */
		RecordExpr	idInfo	=	new RecordExpr();
		if(id == null)
		{
			idInfo.insertAttribute(HITInfoTags.helio, Constant.Undef);
			idInfo.insertAttribute(HITInfoTags.cert, Constant.Undef);
		}
		else
		{
			idInfo.insertAttribute(HITInfoTags.helio, Constant.getInstance(id));
			idInfo.insertAttribute(HITInfoTags.cert, Constant.getInstance(id));			
		}
		cadDescription.insertAttribute(HITInfoTags.identity, idInfo);
		/*
		 * Creating the profile structure
		 */
		cadDescription.insertAttribute(HITInfoTags.profile, Constant.Undef);
	}

	public	String	getUID()
	{
		try 
		{
			return  cadUtils.evaluate(HITInfoPaths.uid, cadDescription).stringValue();
		} 
		catch (ArithmeticException e) 
		{
			e.printStackTrace();
			return "Error";
		} 
		catch (ClassAdUtilitiesException e) 
		{
			e.printStackTrace();
			return "Error";
		}
	}
	
	public	String	getCreatedOn()
	{
		try 
		{
			return  cadUtils.evaluate(HITInfoPaths.createdOn, cadDescription).toString();
		} 
		catch (ArithmeticException e) 
		{
			e.printStackTrace();
			return "Error";
		} 
		catch (ClassAdUtilitiesException e) 
		{
			e.printStackTrace();
			return "Error";
		}	
	}
	
	public	String	getProxyServer()
	{
		try 
		{
			return  cleanString(cadUtils.evaluate(HITInfoPaths.myProxyServer, cadDescription).toString());
		} 
		catch (ArithmeticException e) 
		{
			e.printStackTrace();
			return "Error";
		} 
		catch (ClassAdUtilitiesException e) 
		{
			e.printStackTrace();
			return "Error";
		}	
	}

	public	int	getProxyPort()
	{
		try 
		{
			return  cadUtils.evaluate(HITInfoPaths.myProxyPort, cadDescription).intValue();
		} 
		catch (ArithmeticException e) 
		{
			e.printStackTrace();
			return -1;
		} 
		catch (ClassAdUtilitiesException e) 
		{
			e.printStackTrace();
			return -1;
		}	
	}

	public	String	getHelioId()
	{
		try 
		{
			return  cleanString(cadUtils.evaluate(HITInfoPaths.idHelio, cadDescription).toString());
		} 
		catch (ArithmeticException e) 
		{
			e.printStackTrace();
			return "Error";
		} 
		catch (ClassAdUtilitiesException e) 
		{
			e.printStackTrace();
			return "Error";
		}	
	}

	public	String	getCertId()
	{
		try 
		{
			return  cleanString(cadUtils.evaluate(HITInfoPaths.idCert, cadDescription).toString());
		} 
		catch (ArithmeticException e) 
		{
			e.printStackTrace();
			return "Error";
		} 
		catch (ClassAdUtilitiesException e) 
		{
			e.printStackTrace();
			return "Error";
		}	
	}

	public	void	setCertId(String certId)
	{
		RecordExpr	idInfo	=	(RecordExpr) cadDescription.lookup(HITInfoTags.identity);
		idInfo.insertAttribute(HITInfoTags.cert, Constant.getInstance(certId));		
		cadDescription.insertAttribute(HITInfoTags.identity, idInfo);
	}
	
	public String toString() 
	{
		return cadUtils.exprToReadeableString(cadDescription);
	}

	public RecordExpr getProfile() throws HITInfoException, ClassAdUtilitiesException
	{
		return  (RecordExpr) cadUtils.evaluate(HITInfoTags.profile, cadDescription);
	}

	public void addProfile(String userProfile) throws HITInfoException
	{
		/*
		 * First check that the userProfile is a valid ClassAd expression
		 */
		try 
		{
			cadDescription.insertAttribute(HITInfoTags.profile, cadUtils.string2Expr(userProfile));
		} 
		catch (ClassAdUtilitiesException e) 
		{
			e.printStackTrace();
			throw new HITInfoException();
		}
	}

	private String cleanString(String str)
	{
		return str.replaceAll("\"", "");
	}

	public void buildInfoFromString(String information) throws HITInfoException 
	{
		try 
		{
			cadDescription	=	(RecordExpr) cadUtils.string2Expr(information);
		} 
		catch (ClassAdUtilitiesException e) 
		{
			e.printStackTrace();
			throw new HITInfoException();
		}
	}
}
