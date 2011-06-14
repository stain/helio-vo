package eu.heliovo.api;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

import org.junit.Ignore;
import org.junit.Test;

import condor.classad.Constant;
import condor.classad.RecordExpr;
import eu.heliovo.shared.common.cis.hit.HIT;
import eu.heliovo.shared.common.cis.hit.preferences.PreferencesUtilities;
import eu.heliovo.shared.common.cis.tags.HELIOTags;
import eu.heliovo.shared.common.utilities.ClassAdUtilities;
import eu.heliovo.shared.common.utilities.ClassAdUtilitiesException;

public class CisInterfaceTest 
{
	CisInterface			cisInterface	=	new CisInterface();
	PreferencesUtilities	prUtilities		=	new PreferencesUtilities();
	ClassAdUtilities		cadUtilities	=	new ClassAdUtilities();
	/*
	 * Users for tests
	 */
	String userNameA				=	"name_user_a";
	String userPwdA					=	"pwd_user_a";
	String userDpasPreferencesA		=	"provider3, provider2, provider1";
	String userNameB				=	"name_user_b";
	String userPwdB					=	"pwd_user_b";

	/*
	 * This simple example shows how to create and validate a user.
	 * (It is supposed that the user already exists to validate it)
	 */
	@Test
	public void testCreateUser() throws ClassAdUtilitiesException 
	{
		/*
		 * Check if user A already exists
		 */
		try 
		{
			if(cisInterface.isUserPresent(userNameA) && cisInterface.validateUser(userNameA, userPwdA))
			{
				/*
				 * Removes user A
				 */
				cisInterface.removeUser(userNameA, userPwdA);
				assertFalse(cisInterface.isUserPresent(userNameA));
				assertFalse(cisInterface.validateUser(userNameA, userPwdA));
			}
			/*
			 * Now adds the user A.
			 */
			cisInterface.addUserWithStandardProfile(userNameA, userPwdA);
			assertTrue(cisInterface.isUserPresent(userNameA));
			assertTrue(cisInterface.validateUser(userNameA, userPwdA));
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			assertTrue(false);
		}		
	}

	@Test
	public void testUserPreferences() throws ClassAdUtilitiesException 
	{
		try 
		{
				/*
				 * Retrieves the standard preferences for user A
				 */
				HIT	hit	=	cisInterface.getLowSecurityHITFor(userNameA, userPwdA);	
//				System.out.println(hit);
				System.out.println("Preferences for DPAS for " + userNameA +  " : " + 
						prUtilities.getElementFor(
						hit.getHitInfo().getProfile(), 
						HELIOTags.dpas, 
						HELIOTags.dpas_data_providers));
				/*
				 * Sets preferences for user A
				 */				
				cisInterface.setStringPreferencesFor(userNameA, userPwdA, 
						HELIOTags.dpas, 
						HELIOTags.dpas_data_providers, userDpasPreferencesA);
				/*
				 * Test that the preferences for user A are those just set
				 */				
				hit	=	cisInterface.getLowSecurityHITFor(userNameA, userPwdA);	
//				System.out.println(hit);
				System.out.println("Preferences for DPAS for " + userNameA + " : " +
						prUtilities.getElementFor(
						hit.getHitInfo().getProfile(), 
						HELIOTags.dpas, 
						HELIOTags.dpas_data_providers));				
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				fail("Exception raised !");
			}
	}
//			if(cisInterface.isUserPresent(userNameA) && cisInterface.validateUser(userNameA, userPwdA))
//			{
//				/*
//				 * Removes user A
//				 */
//				cisInterface.removeUser(userNameA, userPwdA);
//				assertFalse(cisInterface.isUserPresent(userNameA));
//				assertFalse(cisInterface.validateUser(userNameA, userPwdA));
//			}
//			/*
//			 * Now adds the user A.
//			 */
//			cisInterface.addUserWithStandardProfile(userNameA, userPwdA);
//			assertTrue(cisInterface.isUserPresent(userNameA));
//			assertTrue(cisInterface.validateUser(userNameA, userPwdA));
//		} 
//		catch (Exception e) 
//		{
//			e.printStackTrace();
//			assertTrue(false);
//		}		
	
//	@Ignore @Test
//	public void testSimple() throws ClassAdUtilitiesException 
//	{
//		RecordExpr	cadExpr	=	new RecordExpr();
//		cadExpr.insertAttribute("element_a", Constant.getInstance("value_of_element_a"));
//		cadExpr.insertAttribute("element_b", Constant.Undef);
//
//		System.out.println(cadUtilities.exprToReadeableString(cadExpr));
//		String		strExpr	=	cadUtilities.expr2String(cadExpr);
//		RecordExpr	cadCopy	=	(RecordExpr) cadUtilities.string2Expr(strExpr);
//		System.out.println(cadUtilities.exprToReadeableString(cadCopy));
//	}
//	
//	@Test
//	public void testValidateUser() 
//	{
//		String 	user	=	"user_a";
//		String	pwd		=	"pwd_for_user_a";
//		
//		try 
//		{
//			if(cisInterface.validateUser(user, pwd))
//				System.out.println(user + " is authorized !");
//			else
//				System.out.println(user + " is NOT authorized !");				
//		} 
//		catch (Exception e) 
//		{
//			e.printStackTrace();
//			fail("Exception raised !");
//		}
//		
//		try 
//		{
//			if(cisInterface.isUserPresent(user))
//				System.out.println(user + " is already present, cannot add it !");
//			else
//			{
//				System.out.println("Adding user " + user + " ...");
//				cisInterface.addUserWithStandardProfile(user, pwd);
//				System.out.println("... done !");
//			}
//		} 
//		catch (CisInterfaceException e1) 
//		{
//			e1.printStackTrace();
//		}
//
//		try 
//		{
//			if(cisInterface.validateUser(user, pwd))
//				System.out.println(user + " is authorized !");
//			else
//				System.out.println(user + " is NOT authorized !");				
//		} 
//		catch (Exception e) 
//		{
//			e.printStackTrace();
//			fail("Exception raised !");
//		}
//		
//		try 
//		{
//			HIT	hit	=	cisInterface.getLowSecurityHITFor(user, pwd);	
//			System.out.println(hit);
//			System.out.println(prUtilities.getElementFor(
//					hit.getHitInfo().getProfile(), 
//					HELIOTags.dpas, 
//					HELIOTags.dpas_data_providers));
//		} 
//		catch (Exception e) 
//		{
//			e.printStackTrace();
//			fail("Exception raised !");
//		}
//
//		try 
//		{
//			cisInterface.setStringPreferencesFor(user, 
//					pwd, 
//					HELIOTags.dpas, 
//					HELIOTags.dpas_data_providers, 
//					"provider3, provider2, provider1");
//		} 
//		catch (Exception e) 
//		{
//			e.printStackTrace();
//		}
//		
//		try 
//		{
//			HIT	hit	=	cisInterface.getLowSecurityHITFor(user, pwd);	
//			System.out.println(hit);
//			System.out.println(prUtilities.getElementFor(
//					hit.getHitInfo().getProfile(), 
//					HELIOTags.dpas, 
//					HELIOTags.dpas_data_providers));
//		} 
//		catch (Exception e) 
//		{
//			e.printStackTrace();
//			fail("Exception raised !");
//		}
//
//	}
}
