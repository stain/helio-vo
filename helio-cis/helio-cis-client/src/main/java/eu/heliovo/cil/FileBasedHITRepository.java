/*
 * 
 */
package eu.heliovo.cil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.HashMap;


import condor.classad.Constant;
import condor.classad.RecordExpr;
import eu.heliovo.cil.common.ClassAdUtilities;
import eu.heliovo.cil.common.ClassAdUtilitiesException;
import eu.heliovo.cil.common.SecurityUtilities;
import eu.heliovo.cil.common.SecurityUtilitiesException;
import eu.heliovo.hit.info.CISValues;

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
	String							statusFile			=	"HITRepository.data";
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


	private void defineDefaultProfile() 
	{
		defaultUserProfile	=	new RecordExpr();
		/*
		 * Define a default HEC
		 */
		RecordExpr	hecDefaultUserProfile	=	new RecordExpr();
		hecDefaultUserProfile.insertAttribute("value_1", Constant.Undef);
		hecDefaultUserProfile.insertAttribute("value_2", Constant.Undef);

		defaultUserProfile.insertAttribute(HELIOTags.hec, hecDefaultUserProfile);
		/*
		 * Define a default DPAS
		 */
		RecordExpr	dpasDefaultUserProfile	=	new RecordExpr();
		dpasDefaultUserProfile.insertAttribute("value_1", Constant.Undef);
		dpasDefaultUserProfile.insertAttribute("value_2", Constant.Undef);

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
		cInfo.insertAttribute(UserTags.passwdHash, Constant.Undef);
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
	public void addUser(String user, String pwd, String profile)
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
}
