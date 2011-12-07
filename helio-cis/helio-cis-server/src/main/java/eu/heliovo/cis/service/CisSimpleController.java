package eu.heliovo.cis.service;

import eu.heliovo.shared.common.utilities.SecurityUtilities;
import eu.heliovo.shared.common.utilities.SecurityUtilitiesException;

public class CisSimpleController 
{
	String 		userName	=	"gab";
	String		userPwd		=	"gab";

	CisService				cis			=	new CisServiceImpl();
	SecurityUtilities		secUtils	=	new SecurityUtilities();
	
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
}
