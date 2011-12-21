package eu.heliovo.cis.service.hit.repository;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import condor.classad.Constant;
import condor.classad.Expr;
import condor.classad.ListExpr;
import condor.classad.RecordExpr;
import eu.heliovo.shared.common.cis.hit.info.CISValues;
import eu.heliovo.shared.common.cis.hit.info.HITInfoTags;
import eu.heliovo.shared.common.cis.hit.info.UserTags;
import eu.heliovo.shared.common.cis.hit.info.UserValues;
import eu.heliovo.shared.common.cis.hit.preferences.PreferencesUtilities;
import eu.heliovo.shared.common.cis.tags.HELIOTags;
import eu.heliovo.shared.common.utilities.ClassAdUtilities;
import eu.heliovo.shared.common.utilities.ClassAdUtilitiesException;
import eu.heliovo.shared.common.utilities.SecurityUtilities;
import eu.heliovo.shared.common.utilities.SecurityUtilitiesException;
import eu.heliovo.shared.common.utilities.SystemUtilities;

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
	String							statusFile			=	"/tmp/HITRepository.test";
//	String							statusFile			=	"c:\\Temporary Folder\\HITRepository.test";
	/*
	 * ClassAd Utilities
	 */
	ClassAdUtilities				cadUtils			=	new ClassAdUtilities();
	/*
	 * Preferences Utilities
	 */
	PreferencesUtilities			preferencesUtils	=	new PreferencesUtilities();
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
			{
				addAnonymousUser();
				addDefaultAdministrator();
				addDefaultUser();
			}
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


	private void addDefaultAdministrator() throws HITRepositoryException 
	{
		if(users.containsKey(CISValues.HelioDefaultAdministrator))
			throw new HITRepositoryException();
		/*
		 * Create the description of the user
		 */			
		RecordExpr		cInfo		=	new RecordExpr();
		/*
		 * Roles
		 */
		ListExpr		roles		=	new ListExpr();
		roles.add(Constant.getInstance(UserValues.standardRole));
		roles.add(Constant.getInstance(UserValues.administratorRole));
		
		cInfo.insertAttribute(UserTags.userRoles, roles);
		cInfo.insertAttribute(UserTags.userName, Constant.getInstance(CISValues.HelioDefaultAdministrator));
		cInfo.insertAttribute(UserTags.createdOn, Constant.getInstance(new Date()));
		cInfo.insertAttribute(UserTags.userProfile, defaultUserProfile);
		cInfo.insertAttribute(UserTags.passwdHash, Constant.getInstance("7251428c2492edff4640cb0e9f1facce"));
		users.put(CISValues.HelioDefaultAdministrator, cInfo.toString());		
		/*
		 * Save the status to the file
		 */
		saveStatus();				
	}

	private void addDefaultUser() throws HITRepositoryException 
	{
		if(users.containsKey(CISValues.HelioDefaultUser))
			throw new HITRepositoryException();
		/*
		 * Create the description of the user
		 */			
		RecordExpr		cInfo		=	new RecordExpr();
		/*
		 * Roles
		 */
		ListExpr		roles		=	new ListExpr();
		roles.add(Constant.getInstance(UserValues.standardRole));
		
		cInfo.insertAttribute(UserTags.userRoles, roles);
		cInfo.insertAttribute(UserTags.userName, Constant.getInstance(CISValues.HelioDefaultAdministrator));
		cInfo.insertAttribute(UserTags.createdOn, Constant.getInstance(new Date()));
		cInfo.insertAttribute(UserTags.userProfile, defaultUserProfile);
		cInfo.insertAttribute(UserTags.passwdHash, Constant.getInstance("5c9a2c3c5c007a8b76b74072c6a2f989"));
		users.put(CISValues.HelioDefaultUser, cInfo.toString());		
		/*
		 * Save the status to the file
		 */
		saveStatus();				
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
		 * Define a default HFE
		 */
		RecordExpr	hfeDefaultUserProfile	=	new RecordExpr();
		hfeDefaultUserProfile.insertAttribute(HELIOTags.hfe_field_1, Constant.getInstance(HELIOTags.hfe_field_1_std_value));
		hfeDefaultUserProfile.insertAttribute(HELIOTags.hfe_field_2, Constant.getInstance(HELIOTags.hfe_field_2_std_value));
		defaultUserProfile.insertAttribute(HELIOTags.hfe, hfeDefaultUserProfile);
		/*
		 * Define a default HEC
		 */
		RecordExpr	hecDefaultUserProfile	=	new RecordExpr();
		hecDefaultUserProfile.insertAttribute(HELIOTags.hec_field_1, Constant.getInstance(HELIOTags.hec_field_1_std_value));
		hecDefaultUserProfile.insertAttribute(HELIOTags.hec_field_2, Constant.getInstance(HELIOTags.hec_field_2_std_value));
		defaultUserProfile.insertAttribute(HELIOTags.hec, hecDefaultUserProfile);
		/*
		 * Define a default DPAS
		 */
		RecordExpr	dpasDefaultUserProfile	=	new RecordExpr();
		dpasDefaultUserProfile.insertAttribute(HELIOTags.dpas_data_providers, Constant.getInstance(HELIOTags.dpas_data_providers_std_value));
		dpasDefaultUserProfile.insertAttribute(HELIOTags.dpas_field_1, Constant.getInstance(HELIOTags.dpas_field_1_std_value));
		dpasDefaultUserProfile.insertAttribute(HELIOTags.dpas_field_2, Constant.getInstance(HELIOTags.dpas_field_2_std_value));

		defaultUserProfile.insertAttribute(HELIOTags.dpas, dpasDefaultUserProfile);
	}


	private void addAnonymousUser() throws HITRepositoryException 
	{
		if(users.containsKey(CISValues.HelioAnonymousUser))
			throw new HITRepositoryException();
		/*
		 * Create the description of the user
		 */			
		RecordExpr		cInfo		=	new RecordExpr();
		/*
		 * Roles
		 */
		ListExpr		roles		=	new ListExpr();
		roles.add(Constant.getInstance(UserValues.standardRole));
		
		cInfo.insertAttribute(UserTags.userRoles, roles);
		cInfo.insertAttribute(UserTags.userName, Constant.getInstance(CISValues.HelioAnonymousUser));
		cInfo.insertAttribute(UserTags.createdOn, Constant.getInstance(new Date()));
		cInfo.insertAttribute(UserTags.userProfile, defaultUserProfile);
		cInfo.insertAttribute(UserTags.passwdHash, Constant.undefined(null));
		users.put(CISValues.HelioAnonymousUser, cInfo.toString());		
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
		 */			
		RecordExpr		cInfo		=	new RecordExpr();
		/*
		 * Roles
		 */
		ListExpr		roles		=	new ListExpr();
		roles.add(Constant.getInstance(UserValues.standardRole));
		
		cInfo.insertAttribute(UserTags.userRoles, roles);
		cInfo.insertAttribute(UserTags.userName, Constant.getInstance(user));
		cInfo.insertAttribute(UserTags.createdOn, Constant.getInstance(new Date()));
		cInfo.insertAttribute(UserTags.userProfile, Constant.getInstance(profile));
		cInfo.insertAttribute(UserTags.passwdHash, Constant.getInstance(pwdHash));
		users.put(user, cInfo.toString());		
		System.out.println(user + " ==> " + pwdHash);
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
	public boolean validateUser(String user, String pwdHash)
			throws HITRepositoryException 
	{
		if(user == null || pwdHash == null)
			throw new HITRepositoryException();
		
		if((user.length() == 0) || (pwdHash.length() == 0))
			throw new HITRepositoryException();
		
		if(!isUserPresent(user))
			return false;
		else
		{
			System.out.println(pwdHash + " <--> " + getHashFor(user));
			return (pwdHash.equals(getHashFor(user)));
		}
	}

	@Override
	public boolean validateUser(String user, String pwdHash, String role) throws HITRepositoryException 
	{
		/*
		 * If the user is not present, return false
		 */
		if(!validateUser(user, pwdHash))
			return false;
		
		/*
		 * If the user is present, get the roles.
		 */
		else
		{	
			System.out.println(getRolesForUser(user) + " <--> " + role);
			return getRolesForUser(user).contains(role);
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
		 * TODO : Add here the standard roles
		 */			
		RecordExpr		cInfo		=	new RecordExpr();
		/*
		 * Roles
		 */
		ListExpr		roles		=	new ListExpr();
		roles.add(Constant.getInstance(UserValues.standardRole));
		
		cInfo.insertAttribute(UserTags.userRoles, roles);

		cInfo.insertAttribute(UserTags.userName, Constant.getInstance(user));
		cInfo.insertAttribute(UserTags.createdOn, Constant.getInstance(new Date()));
		cInfo.insertAttribute(UserTags.userProfile, defaultUserProfile);
		//			cInfo.insertAttribute(UserTags.passwdHash, Constant.getInstance(secUtils.computeHashOf(pwd)));
		cInfo.insertAttribute(UserTags.passwdHash, Constant.getInstance(pwd));
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


	@Override
	public Set<String> getAllUserNames() 
	{
		return users.keySet();
	}


	@Override
	public void changePwdForUser(String user, String oldPwdHash,
			String newPwdHash) throws HITRepositoryException 
	{
		if(!users.containsKey(user))
			throw new HITRepositoryException();
		try 
		{
			RecordExpr		userRecord	=	(RecordExpr) cadUtils.string2Expr(users.get(user));
			userRecord.insertAttribute(UserTags.passwdHash, Constant.getInstance(newPwdHash));
			users.put(user, userRecord.toString());		
		} 
		catch (ArithmeticException e) 
		{
			e.printStackTrace();
		} 
		catch (ClassAdUtilitiesException e) 
		{
			e.printStackTrace();
		}
		saveStatus();				
	}

	@Override
	public String getPreferenceForUser(String user, String service, String field) throws HITRepositoryException 
	{
		if(!users.containsKey(user))
			throw new HITRepositoryException();
		/*
		 * Retrieve the preferences for the user
		 */
		try 
		{
			/*
			 * Getting the user information
			 */
			RecordExpr		cInfo		=	(RecordExpr) cadUtils.string2Expr(users.get(user));
//			System.out.println(cadUtils.exprToReadeableString(cInfo));
			/*
			 * Getting the user profile
			 */
			RecordExpr		prefs		=	(RecordExpr) cadUtils.evaluate(UserTags.userProfile, cInfo);
//			System.out.println(cadUtils.exprToReadeableString(prefs));
			/*
			 * Getting the user profile for the service
			 */
			RecordExpr		uPrefs		=	(RecordExpr) cadUtils.evaluate(service, prefs);
//			System.out.println(cadUtils.exprToReadeableString(uPrefs));
			/*
			 * Getting the user value
			 */
			Expr			value		=	cadUtils.evaluate(field, uPrefs);
//			System.out.println(cadUtils.exprToReadeableString(value));
			return 			value.toString();
		} 
		catch (ClassAdUtilitiesException e) 
		{
			throw new HITRepositoryException();
		}
	}

	@Override
	public void setPreferenceForUser(String user, String service, String field, String value) throws HITRepositoryException 
	{
		if(!users.containsKey(user))
			throw new HITRepositoryException();
		/*
		 * Retrieve the preferences for the user
		 */
		try 
		{
			/*
			 * Getting the user information
			 */
			RecordExpr		cInfo		=	(RecordExpr) cadUtils.string2Expr(users.get(user));
			System.out.println(cadUtils.exprToReadeableString(cInfo));
			/*
			 * Getting the user profile
			 */
			RecordExpr		prefs		=	(RecordExpr) cadUtils.evaluate(UserTags.userProfile, cInfo);
			System.out.println(cadUtils.exprToReadeableString(prefs));
			/*
			 * Getting the user profile for the service
			 */
			RecordExpr		uPrefs		=	(RecordExpr) cadUtils.evaluate(service, prefs);
			System.out.println(cadUtils.exprToReadeableString(uPrefs));
			/*
			 * Setting the value
			 */
			System.out.println("Inserting " + value + " into " + field);
			uPrefs.insertAttribute(field, Constant.getInstance(value));
			System.out.println(cadUtils.exprToReadeableString(uPrefs));
			/*
			 * Setting the value in the preferences
			 */
			prefs.insertAttribute(service, uPrefs);
			/*
			 * Setting the preferences in the user's profile
			 */
			cInfo.insertAttribute(UserTags.userProfile, prefs);
			/*
			 * Setting the preferences in the user's profile back into the repository
			 */
			users.put(user, cadUtils.expr2String(cInfo));
			saveStatus();
		} 
		catch (ClassAdUtilitiesException e) 
		{
			throw new HITRepositoryException();
		}		
	}


	@Override
	public Set<String> getRolesForUser(String user)
			throws HITRepositoryException 
	{
		Set<String> res	=	new HashSet<String>();
		
		if(!users.containsKey(user))
			throw new HITRepositoryException();
		/*
		 * Retrieve the preferences for the user
		 */
		try 
		{
			/*
			 * Getting the user information
			 */
			RecordExpr		cInfo		=	(RecordExpr) cadUtils.string2Expr(users.get(user));
//			System.out.println(cadUtils.exprToReadeableString(cInfo));
			/*
			 * Getting the user profile
			 */
			ListExpr		roles		=	(ListExpr) cadUtils.evaluate(UserTags.userRoles, cInfo);
//			System.out.println(cadUtils.exprToReadeableString(roles));
			
			Iterator<?> 		iter	=	roles.iterator();
			while(iter.hasNext())
			{
				Constant	currRole	=	(Constant) iter.next();
				res.add(currRole.toString());
			}
			return res;
		} 
		catch (ClassAdUtilitiesException e) 
		{
			throw new HITRepositoryException();
		}	
	}


	@Override
	public boolean hasRole(String user) throws HITRepositoryException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void setRolesForUser(String user, Set<String> roles)
			throws HITRepositoryException {
		// TODO Auto-generated method stub
		
	}	
}
