package eu.heliovo.cis.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import eu.heliovo.shared.cis.HELIORoles;
import eu.heliovo.shared.cis.HELIOUsers;
import eu.heliovo.shared.common.utilities.SecurityUtilities;
import eu.heliovo.shared.common.utilities.SecurityUtilitiesException;


public class CisSimpleController 
{
	String 					userName		=	null;
	String 					userEmail		=	null;
	String					userPwd			=	null;
	/*
	 * These fields are used to change the password
	 */
	String 					newPwd1			=	null;
	String					newPwd2			=	null;
	/*
	 * These fields are used to change the preferences
	 */
	String 					prefService		=	null;
	String 					prefField		=	null;
	String 					prefValue		=	null;

	String 					anotherAccount	=	null;

	CisService				cis				=	new CisServiceImpl();
	SecurityUtilities		secUtils		=	new SecurityUtilities();

	public boolean createNewUser()
	{
		try 
		{
			if(userEmail == null)
				cis.addUser(userName, secUtils.computeHashOf(newPwd1));
			else
				cis.addUserWithEmail(userName, userEmail, secUtils.computeHashOf(newPwd1));
				
			return true;
		} 
		catch (CisServiceException e) 
		{
			e.printStackTrace();
			return false;
		} 
		catch (SecurityUtilitiesException e) 
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public void removeAccount()
	{
		try 
		{
			cis.removeUser(userName, secUtils.computeHashOf(userPwd));
		} 
		catch (CisServiceException e) 
		{
			e.printStackTrace();
		} 
		catch (SecurityUtilitiesException e) 
		{
			e.printStackTrace();
		}
	}

	public void removeAnotherAccount()
	{
		try 
		{
			cis.removeAnotherUser(anotherAccount, userName, secUtils.computeHashOf(userPwd));
		} 
		catch (CisServiceException e) 
		{
			e.printStackTrace();
		} 
		catch (SecurityUtilitiesException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void promoteAccount()
	{
		try 
		{
			cis.promoteAnotherUserToAdministrator(userName, 
					secUtils.computeHashOf(userPwd),
					anotherAccount);
		} 
		catch (CisServiceException e) 
		{
			e.printStackTrace();
		} 
		catch (SecurityUtilitiesException e) 
		{
			e.printStackTrace();
		}
	}

	public void demoteAccount()
	{
		try 
		{
			cis.demoteAnotherUserFromAdministrator(userName, secUtils.computeHashOf(userPwd),
					anotherAccount);
		} 
		catch (CisServiceException e) 
		{
			e.printStackTrace();
		} 
		catch (SecurityUtilitiesException e) 
		{
			e.printStackTrace();
		}
	}


	public boolean nameOk()
	{
		return !cis.isUserPresent(userName);
	}

	public boolean pwdOk()
	{
		return newPwd1.equals(newPwd2);
	}

	public boolean canChangePreferences()
	{
		HashMap<String, HashMap<String, String>> preferences	=	null;
		try 
		{
			preferences = cis.getAllPreferencesForUser(userName);
		} 
		catch (CisServiceException e) 
		{
			e.printStackTrace();
			return false;
		}

		if(preferences.containsKey(prefService))
			if(preferences.get(prefService).containsKey(prefField))
				return true;
		
		return false;
	}
	
	public boolean changePreferences()
	{
		try 
		{
			cis.setPreferenceForUser(
					userName, 
					secUtils.computeHashOf(userPwd), 
					prefService, prefField, prefValue);
			return	true;
		} 
		catch (CisServiceException e) 
		{
			e.printStackTrace();
			return false;
		} 
		catch (SecurityUtilitiesException e) 
		{
			e.printStackTrace();
			return false;
		}
	}

	public void changeStandardPreferences()
	{
		try 
		{
			cis.setStandardPreference(userName, secUtils.computeHashOf(userPwd), 
					prefService, prefField, prefValue);
		} 
		catch (CisServiceException e) 
		{
			e.printStackTrace();
		} 
		catch (SecurityUtilitiesException e) 
		{
			e.printStackTrace();
		} 
	}

	public void removeStandardPreferences()
	{
		try 
		{
			if(prefField == null)
				cis.removeServiceInStandardPreference(userName, secUtils.computeHashOf(userPwd), 
						prefService);
			else
				cis.removeFieldInStandardPreference(userName, secUtils.computeHashOf(userPwd), 
						prefService, prefField);			
		} 
		catch (CisServiceException e) 
		{
			e.printStackTrace();
		} 
		catch (SecurityUtilitiesException e) 
		{
			e.printStackTrace();
		} 		
	}

//	public String printAllAccounts()
//	{
//		return	cis.getAllUserNames().toString();
//	}

	public String printAllRemovableAccounts()
	{
		Set<String>	removableUsers	=	new HashSet<String>();		
		removableUsers.addAll(cis.getAllUserNames());
		removableUsers.remove(HELIOUsers.HelioStandardUser);
		return removableUsers.toString();
	}

	public String printAllNormalAccounts()
	{
		try 
		{
			Set<String>	normalUsers	=	new HashSet<String>();
			normalUsers.addAll(cis.getAllUserNamesWithRole(HELIORoles.simpleUser));
			normalUsers.remove(HELIOUsers.HelioStandardUser);
			
			Set<String>	adminUsers	=	new HashSet<String>();
			adminUsers.addAll(cis.getAllUserNamesWithRole(HELIORoles.administrator));
			adminUsers.remove(HELIOUsers.HelioStandardUser);

			Set<String>	users	=	new HashSet<String>();
			users.addAll(normalUsers);
			users.removeAll(adminUsers);
			users.remove(HELIOUsers.HelioStandardUser);

			return users.toString();
		} 
		catch (CisServiceException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public String printAllAdministratorAccounts()
	{
		try 
		{
			return	cis.getAllUserNamesWithRole(HELIORoles.administrator).toString();
		} 
		catch (CisServiceException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public String printAllPreferences()
	{
		String	result	=	new String();

		HashMap<String, HashMap<String, String>>	preferences	=	null;
		
		try 
		{
			preferences	=	cis.getAllPreferencesForUser(userName);
		} 
		catch (CisServiceException e) 
		{
			e.printStackTrace();
		}
		Iterator<String> 	i	=	preferences.keySet().iterator();		
		while(i.hasNext())
		{
			String	service	=	i.next();
			if(service != "null")
			{
				result	+=	"------------------------------------------------<BR>";				
				Iterator<String> j	= preferences.get(service).keySet().iterator();
				{
					while(j.hasNext())
					{
						String	field	=	j.next();
						if(field != "null")
						{
							String	value	=	preferences.get(service).get(field);
//							System.out.println(service + " - " + field + " --> " + value);
							result	+=	service + " - " + field + " -> " + value + "<BR>";				
						}
					}
				}
			}
		}	
		result	+=	"------------------------------------------------<BR>";				
		return result;
	}
	
	
	public String printAllStandardPreferences()
	{
		String	result	=	new String();

		HashMap<String, HashMap<String, String>>	preferences	=	null;
		try 
		{
			preferences	=	cis.getAllStandardPreferences();
		} 
		catch (CisServiceException e) 
		{
			e.printStackTrace();
		}
		Iterator<String> 	i	=	preferences.keySet().iterator();		
		while(i.hasNext())
		{
			String	service	=	i.next();
//			System.out.println(service);
			if(service != "null")
			{
				Iterator<String> j	= preferences.get(service).keySet().iterator();
				{
					while(j.hasNext())
					{
						String	field	=	j.next();
	//					System.out.println(service + " - " + field);
						if(field != "null")
						{
							String	value	=	preferences.get(service).get(field);
//							System.out.println(service + " - " + field + " --> " + value);
							result	+=	service + " - " + field + " -> " + value + "<BR>";				
						}
					}
				}
			}
		}		
		return result;
	}

	
////	public String printAllPreferences()
////	{
////		String	res	=	new String();
////		/*
////		 * Adding the DPAS Data Providers
////		 */
////		String	service	=	HELIOTags.dpas;
////		String	field	=	HELIOTags.dpas_data_providers;
////		res	+=	"(";
////		res	+=	service;
////		res	+=	",";
////		res	+=	field;
////		res	+=	") -> ";
////		try 
////		{
////			res	+=	cis.getPreferenceForUser(userName, service, field);
////		} 
////		catch (CisServiceException e) 
////		{
////			res	+=	" ERROR ";
////		}
////		res	+=	"<BR>";
////		/*
////		 * Adding the DPAS field1
////		 */
////		service	=	HELIOTags.dpas;
////		field	=	HELIOTags.dpas_field_1;
////		res	+=	"(";
////		res	+=	service;
////		res	+=	",";
////		res	+=	field;
////		res	+=	") -> ";
////		try 
////		{
////			res	+=	cis.getPreferenceForUser(userName, service, field);
////		} 
////		catch (CisServiceException e) 
////		{
////			res	+=	" ERROR ";
////		}
////		res	+=	"<BR>";
////		/*
////		 * Adding the DPAS field2
////		 */
////		service	=	HELIOTags.dpas;
////		field	=	HELIOTags.dpas_field_2;
////		res	+=	"(";
////		res	+=	service;
////		res	+=	",";
////		res	+=	field;
////		res	+=	") -> ";
////		try 
////		{
////			res	+=	cis.getPreferenceForUser(userName, service, field);
////		} 
////		catch (CisServiceException e) 
////		{
////			res	+=	" ERROR ";
////		}
////		res	+=	"<BR>";
////		/*
////		 * Adding the HEC field1
////		 */
////		service	=	HELIOTags.hec;
////		field	=	HELIOTags.hec_field_1;
////		res	+=	"(";
////		res	+=	service;
////		res	+=	",";
////		res	+=	field;
////		res	+=	") -> ";
////		try 
////		{
////			res	+=	cis.getPreferenceForUser(userName, service, field);
////		} 
////		catch (CisServiceException e) 
////		{
////			res	+=	" ERROR ";
////		}
////		res	+=	"<BR>";
////		/*
////		 * Adding the HEC field2
////		 */
////		service	=	HELIOTags.hec;
////		field	=	HELIOTags.hec_field_2;
////		res	+=	"(";
////		res	+=	service;
////		res	+=	",";
////		res	+=	field;
////		res	+=	") -> ";
////		try 
////		{
////			res	+=	cis.getPreferenceForUser(userName, service, field);
////		} 
////		catch (CisServiceException e) 
////		{
////			res	+=	" ERROR ";
////		}
////		res	+=	"<BR>";
////		/*
////		 * Adding the HFE field1
////		 */
////		service	=	HELIOTags.hfe;
////		field	=	HELIOTags.hfe_field_1;
////		res	+=	"(";
////		res	+=	service;
////		res	+=	",";
////		res	+=	field;
////		res	+=	") -> ";
////		try 
////		{
////			res	+=	cis.getPreferenceForUser(userName, service, field);
////		} 
////		catch (CisServiceException e) 
////		{
////			res	+=	" ERROR ";
////		}
////		res	+=	"<BR>";
////		/*
////		 * Adding the HFE field2
////		 */
////		service	=	HELIOTags.hfe;
////		field	=	HELIOTags.hfe_field_2;
////		res	+=	"(";
////		res	+=	service;
////		res	+=	",";
////		res	+=	field;
////		res	+=	") -> ";
////		try 
////		{
////			res	+=	cis.getPreferenceForUser(userName, service, field);
////		} 
////		catch (CisServiceException e) 
////		{
////			res	+=	" ERROR ";
////		}
////		res	+=	"<BR>";
////
////		return res;
////	}
////
////	public String printAllStandardPreferences()
////	{
////		String	res	=	new String();
////		/*
////		 * Adding the DPAS Data Providers
////		 */
////		String	service	=	HELIOTags.dpas;
////		String	field	=	HELIOTags.dpas_data_providers;
////		res	+=	"(";
////		res	+=	service;
////		res	+=	",";
////		res	+=	field;
////		res	+=	") -> ";
////		try 
////		{
////			res	+=	cis.getPreferenceForUser(CISValues.HelioAnonymousUser, service, field);
////		} 
////		catch (CisServiceException e) 
////		{
////			res	+=	" ERROR ";
////		}
////		res	+=	"<BR>";
////		/*
////		 * Adding the DPAS field1
////		 */
////		service	=	HELIOTags.dpas;
////		field	=	HELIOTags.dpas_field_1;
////		res	+=	"(";
////		res	+=	service;
////		res	+=	",";
////		res	+=	field;
////		res	+=	") -> ";
////		try 
////		{
////			res	+=	cis.getPreferenceForUser(CISValues.HelioAnonymousUser, service, field);
////		} 
////		catch (CisServiceException e) 
////		{
////			res	+=	" ERROR ";
////		}
////		res	+=	"<BR>";
////		/*
////		 * Adding the DPAS field2
////		 */
////		service	=	HELIOTags.dpas;
////		field	=	HELIOTags.dpas_field_2;
////		res	+=	"(";
////		res	+=	service;
////		res	+=	",";
////		res	+=	field;
////		res	+=	") -> ";
////		try 
////		{
////			res	+=	cis.getPreferenceForUser(CISValues.HelioAnonymousUser, service, field);
////		} 
////		catch (CisServiceException e) 
////		{
////			res	+=	" ERROR ";
////		}
////		res	+=	"<BR>";
////		/*
////		 * Adding the HEC field1
////		 */
////		service	=	HELIOTags.hec;
////		field	=	HELIOTags.hec_field_1;
////		res	+=	"(";
////		res	+=	service;
////		res	+=	",";
////		res	+=	field;
////		res	+=	") -> ";
////		try 
////		{
////			res	+=	cis.getPreferenceForUser(CISValues.HelioAnonymousUser, service, field);
////		} 
////		catch (CisServiceException e) 
////		{
////			res	+=	" ERROR ";
////		}
////		res	+=	"<BR>";
////		/*
////		 * Adding the HEC field2
////		 */
////		service	=	HELIOTags.hec;
////		field	=	HELIOTags.hec_field_2;
////		res	+=	"(";
////		res	+=	service;
////		res	+=	",";
////		res	+=	field;
////		res	+=	") -> ";
////		try 
////		{
////			res	+=	cis.getPreferenceForUser(CISValues.HelioAnonymousUser, service, field);
////		} 
////		catch (CisServiceException e) 
////		{
////			res	+=	" ERROR ";
////		}
////		res	+=	"<BR>";
////		/*
////		 * Adding the HFE field1
////		 */
////		service	=	HELIOTags.hfe;
////		field	=	HELIOTags.hfe_field_1;
////		res	+=	"(";
////		res	+=	service;
////		res	+=	",";
////		res	+=	field;
////		res	+=	") -> ";
////		try 
////		{
////			res	+=	cis.getPreferenceForUser(CISValues.HelioAnonymousUser, service, field);
////		} 
////		catch (CisServiceException e) 
////		{
////			res	+=	" ERROR ";
////		}
////		res	+=	"<BR>";
////		/*
////		 * Adding the HFE field2
////		 */
////		service	=	HELIOTags.hfe;
////		field	=	HELIOTags.hfe_field_2;
////		res	+=	"(";
////		res	+=	service;
////		res	+=	",";
////		res	+=	field;
////		res	+=	") -> ";
////		try 
////		{
////			res	+=	cis.getPreferenceForUser(CISValues.HelioAnonymousUser, service, field);
////		} 
////		catch (CisServiceException e) 
////		{
////			res	+=	" ERROR ";
////		}
////		res	+=	"<BR>";
////
////		return res;
////	}

	public void changePwd()
	{
		try 
		{
			cis.changePwdHashForUser(userName, 
					secUtils.computeHashOf(userPwd),
					secUtils.computeHashOf(newPwd1));
			userPwd	=	newPwd1;
		} 
		catch (CisServiceException e) 
		{
			e.printStackTrace();
		} 
		catch (SecurityUtilitiesException e) 
		{
			e.printStackTrace();
		}		
	}

	
	public boolean validUser()
	{
		try 
		{
			return cis.validateUser(userName, secUtils.computeHashOf(userPwd));
		} 
		catch (SecurityUtilitiesException e) 
		{
			return false;
		}
	}
	
	public boolean adminUser()
	{
		try 
		{
			return cis.validateUserAndRole(userName, 
					secUtils.computeHashOf(userPwd), 
					HELIORoles.administrator);
		} 
		catch (SecurityUtilitiesException e) 
		{
			return false;
		} 
		catch (CisServiceException e) 
		{
			return false;
		}
	}


	

	/*
	 * Getters and Setters
	 */
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	public String getNewPwd1() {
		return newPwd1;
	}
	public void setNewPwd1(String newPwd1) {
		this.newPwd1 = newPwd1;
	}
	public String getNewPwd2() {
		return newPwd2;
	}
	public void setNewPwd2(String newPwd2) {
		this.newPwd2 = newPwd2;
	}

	public String getPrefService() {
		return prefService;
	}

	public void setPrefService(String prefService) {
		this.prefService = prefService;
	}

	public String getPrefField() {
		return prefField;
	}

	public void setPrefField(String prefField) {
		this.prefField = prefField;
	}

	public String getPrefValue() {
		return prefValue;
	}

	public void setPrefValue(String prefValue) {
		this.prefValue = prefValue;
	}
	
	public String getAnotherAccount() {
		return anotherAccount;
	}

	public void setAnotherAccount(String anotherAccount) {
		this.anotherAccount = anotherAccount;
	}
}
