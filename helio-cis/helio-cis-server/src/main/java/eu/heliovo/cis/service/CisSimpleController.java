package eu.heliovo.cis.service;

import java.util.HashSet;
import java.util.Iterator;

import eu.heliovo.shared.common.cis.hit.info.CISValues;
import eu.heliovo.shared.common.cis.hit.info.UserTags;
import eu.heliovo.shared.common.cis.hit.info.UserValues;
import eu.heliovo.shared.common.cis.tags.HELIOTags;
import eu.heliovo.shared.common.utilities.SecurityUtilities;
import eu.heliovo.shared.common.utilities.SecurityUtilitiesException;

public class CisSimpleController 
{
	String 		userName	=	null;
	String		userPwd		=	null;
	/*
	 * These fields are used to change the password
	 */
	String 		newPwd1		=	null;
	String		newPwd2		=	null;
	/*
	 * These fields are used to change the preferences
	 */
	String 		prefService	=	null;
	String 		prefField	=	null;
	String 		prefValue	=	null;

	String 		anotherAccount	=	null;

	CisService				cis			=	new CisServiceImpl();
	SecurityUtilities		secUtils	=	new SecurityUtilities();

	public boolean createNewUser()
	{
		try 
		{
			cis.addUser(userName, secUtils.computeHashOf(newPwd1));
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

	public boolean newPwdOk()
	{
		return newPwd1.equals(newPwd2);
	}

	public void changePreferences()
	{
		try 
		{
			cis.setPreferenceForUser(userName, 
					secUtils.computeHashOf(userPwd), 
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

	public void changeStandardPreferences()
	{
		try 
		{
			cis.setPreferenceForAnotherUser(CISValues.HelioAnonymousUser, 
					userName, secUtils.computeHashOf(userPwd), 
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

	public String printAllAccounts()
	{
		return	cis.getAllUserNames().toString();
	}
	
	public String printAllPreferences()
	{
		String	res	=	new String();
		/*
		 * Adding the DPAS Data Providers
		 */
		String	service	=	HELIOTags.dpas;
		String	field	=	HELIOTags.dpas_data_providers;
		res	+=	"(";
		res	+=	service;
		res	+=	",";
		res	+=	field;
		res	+=	") -> ";
		try 
		{
			res	+=	cis.getPreferenceForUser(userName, service, field);
		} 
		catch (CisServiceException e) 
		{
			res	+=	" ERROR ";
		}
		res	+=	"<BR>";
		/*
		 * Adding the DPAS field1
		 */
		service	=	HELIOTags.dpas;
		field	=	HELIOTags.dpas_field_1;
		res	+=	"(";
		res	+=	service;
		res	+=	",";
		res	+=	field;
		res	+=	") -> ";
		try 
		{
			res	+=	cis.getPreferenceForUser(userName, service, field);
		} 
		catch (CisServiceException e) 
		{
			res	+=	" ERROR ";
		}
		res	+=	"<BR>";
		/*
		 * Adding the DPAS field2
		 */
		service	=	HELIOTags.dpas;
		field	=	HELIOTags.dpas_field_2;
		res	+=	"(";
		res	+=	service;
		res	+=	",";
		res	+=	field;
		res	+=	") -> ";
		try 
		{
			res	+=	cis.getPreferenceForUser(userName, service, field);
		} 
		catch (CisServiceException e) 
		{
			res	+=	" ERROR ";
		}
		res	+=	"<BR>";
		/*
		 * Adding the HEC field1
		 */
		service	=	HELIOTags.hec;
		field	=	HELIOTags.hec_field_1;
		res	+=	"(";
		res	+=	service;
		res	+=	",";
		res	+=	field;
		res	+=	") -> ";
		try 
		{
			res	+=	cis.getPreferenceForUser(userName, service, field);
		} 
		catch (CisServiceException e) 
		{
			res	+=	" ERROR ";
		}
		res	+=	"<BR>";
		/*
		 * Adding the HEC field2
		 */
		service	=	HELIOTags.hec;
		field	=	HELIOTags.hec_field_2;
		res	+=	"(";
		res	+=	service;
		res	+=	",";
		res	+=	field;
		res	+=	") -> ";
		try 
		{
			res	+=	cis.getPreferenceForUser(userName, service, field);
		} 
		catch (CisServiceException e) 
		{
			res	+=	" ERROR ";
		}
		res	+=	"<BR>";
		/*
		 * Adding the HFE field1
		 */
		service	=	HELIOTags.hfe;
		field	=	HELIOTags.hfe_field_1;
		res	+=	"(";
		res	+=	service;
		res	+=	",";
		res	+=	field;
		res	+=	") -> ";
		try 
		{
			res	+=	cis.getPreferenceForUser(userName, service, field);
		} 
		catch (CisServiceException e) 
		{
			res	+=	" ERROR ";
		}
		res	+=	"<BR>";
		/*
		 * Adding the HFE field2
		 */
		service	=	HELIOTags.hfe;
		field	=	HELIOTags.hfe_field_2;
		res	+=	"(";
		res	+=	service;
		res	+=	",";
		res	+=	field;
		res	+=	") -> ";
		try 
		{
			res	+=	cis.getPreferenceForUser(userName, service, field);
		} 
		catch (CisServiceException e) 
		{
			res	+=	" ERROR ";
		}
		res	+=	"<BR>";

		return res;
	}

	public String printAllStandardPreferences()
	{
		String	res	=	new String();
		/*
		 * Adding the DPAS Data Providers
		 */
		String	service	=	HELIOTags.dpas;
		String	field	=	HELIOTags.dpas_data_providers;
		res	+=	"(";
		res	+=	service;
		res	+=	",";
		res	+=	field;
		res	+=	") -> ";
		try 
		{
			res	+=	cis.getPreferenceForUser(CISValues.HelioAnonymousUser, service, field);
		} 
		catch (CisServiceException e) 
		{
			res	+=	" ERROR ";
		}
		res	+=	"<BR>";
		/*
		 * Adding the DPAS field1
		 */
		service	=	HELIOTags.dpas;
		field	=	HELIOTags.dpas_field_1;
		res	+=	"(";
		res	+=	service;
		res	+=	",";
		res	+=	field;
		res	+=	") -> ";
		try 
		{
			res	+=	cis.getPreferenceForUser(CISValues.HelioAnonymousUser, service, field);
		} 
		catch (CisServiceException e) 
		{
			res	+=	" ERROR ";
		}
		res	+=	"<BR>";
		/*
		 * Adding the DPAS field2
		 */
		service	=	HELIOTags.dpas;
		field	=	HELIOTags.dpas_field_2;
		res	+=	"(";
		res	+=	service;
		res	+=	",";
		res	+=	field;
		res	+=	") -> ";
		try 
		{
			res	+=	cis.getPreferenceForUser(CISValues.HelioAnonymousUser, service, field);
		} 
		catch (CisServiceException e) 
		{
			res	+=	" ERROR ";
		}
		res	+=	"<BR>";
		/*
		 * Adding the HEC field1
		 */
		service	=	HELIOTags.hec;
		field	=	HELIOTags.hec_field_1;
		res	+=	"(";
		res	+=	service;
		res	+=	",";
		res	+=	field;
		res	+=	") -> ";
		try 
		{
			res	+=	cis.getPreferenceForUser(CISValues.HelioAnonymousUser, service, field);
		} 
		catch (CisServiceException e) 
		{
			res	+=	" ERROR ";
		}
		res	+=	"<BR>";
		/*
		 * Adding the HEC field2
		 */
		service	=	HELIOTags.hec;
		field	=	HELIOTags.hec_field_2;
		res	+=	"(";
		res	+=	service;
		res	+=	",";
		res	+=	field;
		res	+=	") -> ";
		try 
		{
			res	+=	cis.getPreferenceForUser(CISValues.HelioAnonymousUser, service, field);
		} 
		catch (CisServiceException e) 
		{
			res	+=	" ERROR ";
		}
		res	+=	"<BR>";
		/*
		 * Adding the HFE field1
		 */
		service	=	HELIOTags.hfe;
		field	=	HELIOTags.hfe_field_1;
		res	+=	"(";
		res	+=	service;
		res	+=	",";
		res	+=	field;
		res	+=	") -> ";
		try 
		{
			res	+=	cis.getPreferenceForUser(CISValues.HelioAnonymousUser, service, field);
		} 
		catch (CisServiceException e) 
		{
			res	+=	" ERROR ";
		}
		res	+=	"<BR>";
		/*
		 * Adding the HFE field2
		 */
		service	=	HELIOTags.hfe;
		field	=	HELIOTags.hfe_field_2;
		res	+=	"(";
		res	+=	service;
		res	+=	",";
		res	+=	field;
		res	+=	") -> ";
		try 
		{
			res	+=	cis.getPreferenceForUser(CISValues.HelioAnonymousUser, service, field);
		} 
		catch (CisServiceException e) 
		{
			res	+=	" ERROR ";
		}
		res	+=	"<BR>";

		return res;
	}

	public void changePwd()
	{
		try 
		{
			cis.changePwdForUser(userName, 
					secUtils.computeHashOf(userPwd),
					secUtils.computeHashOf(newPwd1));
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

	/*
	 * These methods interface the cis
	 */
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
	/*
	 * These methods interface the cis
	 */
	public boolean adminUser()
	{
		try 
		{
			return cis.validateUserAndRole(userName, 
					secUtils.computeHashOf(userPwd), 
					UserValues.administratorRole);
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
