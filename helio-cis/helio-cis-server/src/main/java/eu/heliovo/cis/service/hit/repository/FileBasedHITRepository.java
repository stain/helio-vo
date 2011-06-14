/*
 * 
 */
package eu.heliovo.cis.service.hit.repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.cxf.transport.servlet.ServletController;

import condor.classad.Constant;
import condor.classad.RecordExpr;
import eu.heliovo.cis.service.common.SystemUtilities;
import eu.heliovo.cis.service.hit.info.CISValues;
import eu.heliovo.cis.service.hit.info.UserTags;
import eu.heliovo.cis.service.utilities.ClassAdUtilities;
import eu.heliovo.cis.service.utilities.ClassAdUtilitiesException;
import eu.heliovo.cis.service.utilities.SecurityUtilities;
import eu.heliovo.cis.service.utilities.SecurityUtilitiesException;
import eu.heliovo.shared.common.cis.tags.HELIOTags;

/**
 * The Class FileBasedHITRepository.
 * 
 * The information of each user in the CIL is structured in the following schema:
 * 
 * userName			The identity of the user
 * cratedOn			The date when the user was created
 * passwdHash		The hash map of the password of the user
 * useProfile		The user profile
 * 
 * 
 */
public class FileBasedHITRepository implements HITRepository 
{
	String							statusFile			=	"c:\\tmp\\HITRepository.data";
	/*
	 * ClassAd utilities
	 */
	ClassAdUtilities				cadUtils			=	new ClassAdUtilities();
	/*
	 * The description of the HIT in a hashmap of ClassAds
	 */
	HashMap<String, String>			users				=	new HashMap<String, String>();
	/*
	 * The hashing algorithm for the password
	 */
	SecurityUtilities				secUtils			=	new SecurityUtilities();
	/*
	 * It is true if the load was successfull
	 */
	boolean							loadSuccessful		=	false;
	/*
	 * Default User Profile
	 */
	RecordExpr						defaultUserProfile	=	null;
	/*
	 * System Utilities
	 */
	SystemUtilities					sysUtils			=	new SystemUtilities();
	
	public FileBasedHITRepository() 
	{
		try 
		{
			defineDefaultProfile();
			loadStatus();
			if(users.keySet().isEmpty())
				addAnonymousUser();
		} 
		catch (HITRepositoryException e) 
		{
			loadSuccessful	=	false;
			/*
			 * If the load was not successful, add the anonymous user
			 * Add the anonymous user
			 */
		}
		loadSuccessful	=	true;
	}


	public FileBasedHITRepository(String repositoryLocation) 
	{
		statusFile			=	repositoryLocation;

		try 
		{
			defineDefaultProfile();
			loadStatus();
			if(users.keySet().isEmpty())
				addAnonymousUser();
		} 
		catch (HITRepositoryException e) 
		{
			loadSuccessful	=	false;
			/*
			 * If the load was not successful, add the anonymous user
			 * Add the anonymous user
			 */
		}
		loadSuccessful	=	true;
	}


	private void defineDefaultProfile() 
	{
		defaultUserProfile	=	new RecordExpr();
		/*
		 * Define a default HEC
		 */
		RecordExpr	hecDefaultUserProfile	=	new RecordExpr();
		hecDefaultUserProfile.insertAttribute(HELIOTags.hec_value_1, Constant.undefined(null));
		hecDefaultUserProfile.insertAttribute(HELIOTags.hec_value_2, Constant.undefined(null));

		defaultUserProfile.insertAttribute(HELIOTags.hec, hecDefaultUserProfile);
		/*
		 * Define a default DPAS
		 */
		RecordExpr	dpasDefaultUserProfile	=	new RecordExpr();
		dpasDefaultUserProfile.insertAttribute(HELIOTags.dpas_data_providers, Constant.getInstance("provider1, provider2, provider3"));
		dpasDefaultUserProfile.insertAttribute(HELIOTags.dpas_value_2, Constant.undefined(null));

		defaultUserProfile.insertAttribute(HELIOTags.dpas, dpasDefaultUserProfile);
	}


	private void addAnonymousUser() throws HITRepositoryException 
	{
		if(users.containsKey(CISValues.HelioAnonymousUser))
			throw new HITRepositoryException();
		/*
		 * Create the description of the user
		 * TODO : Convert the profile into a recordExpression
		 */			
		RecordExpr		cInfo		=	new RecordExpr();
		cInfo.insertAttribute(UserTags.userName, Constant.getInstance(CISValues.HelioAnonymousUser));
		cInfo.insertAttribute(UserTags.createdOn, Constant.getInstance(new Date()));
		cInfo.insertAttribute(UserTags.userProfile, defaultUserProfile);
		cInfo.insertAttribute(UserTags.passwdHash, Constant.undefined(null));
		users.put(CISValues.HelioAnonymousUser, cInfo.toString());
		
//		System.out.println(users.toString());
		/*
		 * Save the status to the file
		 */
		saveStatus();		
	}


	public boolean isLoadSuccessful() 
	{
		return loadSuccessful;
	}

	@Override
	public void addUser(String user, String pwdHash, String profile)
			throws HITRepositoryException 
	{
		if(users.containsKey(user))
			throw new HITRepositoryException();
		/*
		 * Create the description of the user
		 * TODO : Convert the profile into a recordExpression
		 */			
		RecordExpr		cInfo		=	new RecordExpr();
		cInfo.insertAttribute(UserTags.userName, Constant.getInstance(user));
		cInfo.insertAttribute(UserTags.createdOn, Constant.getInstance(new Date()));
		cInfo.insertAttribute(UserTags.userProfile, Constant.getInstance(profile));
		cInfo.insertAttribute(UserTags.passwdHash, Constant.getInstance(pwdHash));
		users.put(user, cInfo.toString());		
		/*
		 * Save the status to the file
		 */
		saveStatus();
	}

	private void saveStatus() throws HITRepositoryException 
	{
		ObjectOutputStream obj	=	null;
		
		try 
		{
			obj = new ObjectOutputStream(new FileOutputStream(statusFile));
			obj.writeObject(users);
			obj.close();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
			throw new HITRepositoryException();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			throw new HITRepositoryException();
		}
	}

	@SuppressWarnings("unchecked")
	private void loadStatus() throws HITRepositoryException 
	{	
		ObjectInputStream obj	=	null;
		try 
		{
			obj = new ObjectInputStream(new FileInputStream(statusFile));
			users = (HashMap<String, String>)obj.readObject();
		} 
		catch (FileNotFoundException e) 
		{
			loadSuccessful	=	false;
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			throw new HITRepositoryException();
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
			throw new HITRepositoryException();
		}
	}

	@Override
	public String getUserProfileFor(String user)
			throws HITRepositoryException 
			{
		if(user == null)
			throw new HITRepositoryException();
		
		if(user.length() == 0)
			throw new HITRepositoryException();
		
		if(!isUserPresent(user))
			throw new HITRepositoryException();
		
		return	getProfileFor(user);
	}

	private RecordExpr getCadProfileFor(String user) throws HITRepositoryException 
	{
		if(!isUserPresent(user))
			throw new HITRepositoryException();
		
		try 
		{
			/*
			 * Get the user's record
			 */
			RecordExpr		userRecord	=	(RecordExpr) cadUtils.string2Expr(users.get(user));
			return  (RecordExpr) cadUtils.evaluate(UserTags.userProfile, userRecord);
		} 
		catch (ArithmeticException e) 
		{
			e.printStackTrace();
			return null;
		} 
		catch (ClassAdUtilitiesException e) 
		{
			e.printStackTrace();
			return null;
		}
	}

	private String getProfileFor(String user) throws HITRepositoryException 
	{
		if(!isUserPresent(user))
			throw new HITRepositoryException();
		
		try 
		{
			/*
			 * Get the user's record
			 */
			RecordExpr		userRecord	=	(RecordExpr) cadUtils.string2Expr(users.get(user));
			return  cadUtils.evaluate(UserTags.userProfile, userRecord).toString();
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

	@Override
	public boolean isUserPresent(String user)
			throws HITRepositoryException 
	{
		if(user == null)
			throw new HITRepositoryException();
		
		if(user.length() == 0)
			throw new HITRepositoryException();
		
		return users.containsKey(user);
	}

	@Override
	public boolean validateUser(String user, String pwd)
			throws HITRepositoryException 
	{
		if(user == null || pwd == null)
			throw new HITRepositoryException();
		
		if((user.length() == 0) || (pwd.length() == 0))
			throw new HITRepositoryException();
		
		if(!isUserPresent(user))
			return false;
		else
		{
			try 
			{
				return (secUtils.computeHashOf(pwd).equals(getHashFor(user)));
			} 
			catch (SecurityUtilitiesException e) 
			{
				e.printStackTrace();
				throw new HITRepositoryException();
			}
		}

	}

	private String getHashFor(String user) throws HITRepositoryException 
	{
		if(!isUserPresent(user))
			throw new HITRepositoryException();
		
		try 
		{
			/*
			 * Get the user's record
			 */
			RecordExpr		userRecord	=	(RecordExpr) cadUtils.string2Expr(users.get(user));
			return  cadUtils.evaluate(UserTags.passwdHash, userRecord).stringValue();
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

	public String toString() 
	{		
		return users.toString();
	}


	@Override
	public String getUserProfileEntity(String user, String service,
			String entity) throws HITRepositoryException 
	{
		if(!isUserPresent(user))
			throw new HITRepositoryException();
		
		try 
		{
			/*
			 * Get the user's RecordExpr
			 */
			RecordExpr		userRecord	=	(RecordExpr) cadUtils.string2Expr(users.get(user));
			/*
			 * Get the user's profile RecordExpr
			 */
			RecordExpr		userProfileRecord		=	  (RecordExpr) cadUtils.evaluate(UserTags.userProfile, userRecord);
			/*
			 * Get the services's profile RecordExpr
			 */
			RecordExpr		serviceProfileRecord	=	  (RecordExpr) cadUtils.evaluate(service, userProfileRecord);
			/*
			 * Get and return the profile item
			 */
			return	  cadUtils.evaluate(entity, serviceProfileRecord).toString();
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


	@Override
	public void setUserProfileEntity(String user, String service, String entity, String value) 
	{
		/*
		 * Get the HIT for the user
		 */
		String	strHit	=	users.get(user);
		System.out.println(strHit);
		/*
		 * Create the ClassAd representation of that HIT.
		 */
		RecordExpr	cadHit	=	null;
		try 
		{
			cadHit	=	(RecordExpr) cadUtils.string2Expr(strHit);
			System.out.println(cadHit);
		} 
		catch (ClassAdUtilitiesException e) 
		{
			e.printStackTrace();
		}
		/*
		 * Get the user profile of that hit
		 */
		RecordExpr	cadPr	=	null;
		try 
		{
			cadPr	=	(RecordExpr) cadUtils.evaluate(UserTags.userProfile, cadHit);
			System.out.println("****************************************");
			System.out.println(cadUtils.exprToReadeableString(cadPr));
			System.out.println("****************************************");
		} 
		catch (ClassAdUtilitiesException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * Get the preferences of the services
		 */
		RecordExpr	cadSvPr	=	null;
		try 
		{
			cadSvPr	=	(RecordExpr) cadUtils.evaluate(service, cadPr);
			System.out.println("****************************************");
			System.out.println(cadUtils.exprToReadeableString(cadSvPr));
			System.out.println("****************************************");
		} 
		catch (ClassAdUtilitiesException e) 
		{
			e.printStackTrace();
		}
		/*
		 * Set the new entity value into the service profile
		 */
		cadSvPr.insertAttribute(entity, Constant.getInstance(value));
		System.out.println("****************************************");
		System.out.println(cadUtils.exprToReadeableString(cadSvPr));
		System.out.println("****************************************");
		/*
		 * Set the modified service profile into the user preferences
		 */
		cadPr.insertAttribute(service, cadSvPr);
		System.out.println("****************************************");
		System.out.println(cadUtils.exprToReadeableString(cadPr));
		System.out.println("****************************************");
		/*
		 * Set the modified user preferences into the HIT
		 */
		cadHit.insertAttribute(UserTags.userProfile, cadPr);
		System.out.println("****************************************");
		System.out.println(cadUtils.exprToReadeableString(cadHit));
		System.out.println("****************************************");
		/*
		 * Update the user information hash map
		 */
		System.out.println("****************************************");
		System.out.println(users);
		System.out.println("****************************************");

		try 
		{
			users.put(user, cadUtils.expr2String(cadHit));
		} 
		catch (ClassAdUtilitiesException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("****************************************");
		System.out.println(users);
		System.out.println("****************************************");
		/*
		 * Save the status to the file
		 */
		try 
		{
			saveStatus();
		} 
		catch (HITRepositoryException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
	}


	@Override
	public void addUser(String user, String pwd) throws HITRepositoryException 
	{
		if(users.containsKey(user))
			throw new HITRepositoryException();
		/*
		 * Create the description of the user
		 * TODO : Convert the profile into a recordExpression
		 */			
		RecordExpr		cInfo		=	new RecordExpr();
		cInfo.insertAttribute(UserTags.userName, Constant.getInstance(user));
		cInfo.insertAttribute(UserTags.createdOn, Constant.getInstance(new Date()));
		cInfo.insertAttribute(UserTags.userProfile, defaultUserProfile);
		try 
		{
			cInfo.insertAttribute(UserTags.passwdHash, Constant.getInstance(secUtils.computeHashOf(pwd)));
		} 
		catch (SecurityUtilitiesException e) 
		{
			e.printStackTrace();
			throw new HITRepositoryException();
		}
		users.put(user, cInfo.toString());
		
//		System.out.println(users.toString());
		/*
		 * Save the status to the file
		 */
		saveStatus();		
	}


	@Override
	public void removeUser(String user) throws HITRepositoryException 
	{
		users.remove(user);
		saveStatus();
	}
}
