package eu.heliovo.cis.service;

import org.junit.Test;

import eu.heliovo.shared.common.utilities.LogUtilities;

public class CisServiceTest 
{
//	HITUtilities			hitUtilities	=	new HITUtilities();	
	CisService				cisService		=	new CisServiceImpl();
//	SecurityUtilities		secUtils		=	new SecurityUtilities();
//	PreferencesUtilities	prUtilities		=	new PreferencesUtilities();
	LogUtilities			logUtilities	=	new LogUtilities();
	
//
//	
	@Test
	public void testUserCreation() 
	{
		logUtilities.printShortLogEntry("Invoking test on the CIS...");
		logUtilities.printShortLogEntry(cisService.test("test-param"));
		logUtilities.printShortLogEntry("...done");		
	}
	
//	@Test
//	public void testUserCreation() 
//	{
//		String 	user	=	"test_user";
//		String	pwd		=	"pwd_for_test_user";
//		
//		try 
//		{
//			/*
//			 * If the test user is present, remove it
//			 */
//			if(cisInterface.isUserPresent(user))
//			{
//				/*
//				 * Validates the user and, if valid removes it
//				 */
//				if(cisInterface.validateUser(user, secUtils.computeHashOf(pwd)))
//				{
//					cisInterface.removeUser(user, secUtils.computeHashOf(pwd));
//					assertFalse(cisInterface.isUserPresent(user));
//				}
//			}
//			/*
//			 * Now, add the user
//			 */
//			cisInterface.addUser(user, secUtils.computeHashOf(pwd));
//			assertTrue(cisInterface.isUserPresent(user));
//			assertTrue(cisInterface.validateUser(user, secUtils.computeHashOf(pwd)));
//		} 
//		catch (CisServiceException e1) 
//		{
//			e1.printStackTrace();
//		} 
//		catch (SecurityUtilitiesException e1) 
//		{
//			e1.printStackTrace();
//		}
//	}
//	
//
//	@Test
//	public void testUserPreferences() 
//	{
//		String 	user	=	"test_user";
//		String	pwd		=	"pwd_for_test_user";
//		
//		try 
//		{
//			/*
//			 * If the test user is present, remove it
//			 */
//			if(cisInterface.isUserPresent(user))
//			{
//				/*
//				 * Validates the user and, if valid removes it
//				 */
//				if(cisInterface.validateUser(user, secUtils.computeHashOf(pwd)))
//				{
//					cisInterface.removeUser(user, secUtils.computeHashOf(pwd));
//					assertFalse(cisInterface.isUserPresent(user));
//				}
//			}
//			/*
//			 * Now, add the user
//			 */
//			cisInterface.addUser(user, secUtils.computeHashOf(pwd));
//			assertTrue(cisInterface.isUserPresent(user));
//			assertTrue(cisInterface.validateUser(user, secUtils.computeHashOf(pwd)));
//			/*
//			 * Retrieve the preferences for the following service
//			 * Service : dpas
//			 * Element : dpas_data_providers
//			 */
//			HITPayload	hitPayload	=	 cisInterface.getLowSecurityHITFor(user, secUtils.computeHashOf(pwd));
//			System.out.println(hitPayload.getInformation());
//			HIT	hit	= 	hitUtilities.buildHITFromPayload(hitPayload);
//			
//			System.out.println(hit);
//			System.out.println(prUtilities.getElementFor(
//					hit.getHitInfo().getProfile(), 
//					HELIOTags.dpas, 
//					HELIOTags.dpas_data_providers));
//			/*
//			 * Setting the preferences
//			 */
//			String preferences	= "provider3, provider2, provider1";
//			
//			cisInterface.setStringPreferencesFor(user, 
//					secUtils.computeHashOf(pwd), 
//					HELIOTags.dpas, 
//					HELIOTags.dpas_data_providers, 
//					preferences);
//
//			hitPayload	=	 cisInterface.getLowSecurityHITFor(user, secUtils.computeHashOf(pwd));
//			System.out.println(hitPayload.getInformation());
//			hit	= 	hitUtilities.buildHITFromPayload(hitPayload);
//			
//			System.out.println(prUtilities.getElementFor(
//					hit.getHitInfo().getProfile(), 
//					HELIOTags.dpas, 
//					HELIOTags.dpas_data_providers));
//
////			assertTrue(prUtilities.getElementFor(
////					hit.getHitInfo().getProfile(), 
////					HELIOTags.dpas, 
////					HELIOTags.dpas_data_providers).equals(preferences));			
//		} 
//		catch (CisServiceException e1) 
//		{
//			e1.printStackTrace();
//		} 
//		catch (SecurityUtilitiesException e1) 
//		{
//			e1.printStackTrace();
//		} 
//		catch (HITUtilitiesException e) 
//		{
//			e.printStackTrace();
//		} 
//		catch (HITInfoException e) 
//		{
//			e.printStackTrace();
//		} 
//		catch (ClassAdUtilitiesException e) 
//		{
//			e.printStackTrace();
//		}
//	}
//
//	
//	
////	@Test
////	public void testValidateUser() 
////	{
////		String 	user	=	"user_a";
////		String	pwd		=	"pwd_for_user_a";
////		
////		try 
////		{
////			if(cisInterface.validateUser(user, secUtils.computeHashOf(pwd)))
////				System.out.println(user + " is authorized !");
////			else
////				System.out.println(user + " is NOT authorized !");				
////		} 
////		catch (Exception e) 
////		{
////			e.printStackTrace();
////			fail("Exception raised !");
////		}
////		
////		try 
////		{
////			cisInterface.addUser(user, secUtils.computeHashOf(pwd));
////		} 
////		catch (Exception e1) 
////		{
////			System.out.println("Cannot add " + user + ", it is probably already present");
////		}
////
////		try 
////		{
////			if(cisInterface.validateUser(user, secUtils.computeHashOf("pwd_for_user_a")))
////				System.out.println(user + " is authorized !");
////			else
////				System.out.println(user + " is NOT authorized !");				
////		} 
////		catch (Exception e) 
////		{
////			e.printStackTrace();
////			fail("Exception raised !");
////		}
////		
////		try 
////		{
////			System.out.println("HIT for " + user + cisInterface.getLowSecurityHITFor("user_a", secUtils.computeHashOf(pwd)));
////		} 
////		catch (Exception e) 
////		{
////			e.printStackTrace();
////			fail("Exception raised !");
////		}
////	}
////	
////	@Test
////	public void testUserPreferences() 
////	{
////		String 	user	=	"user_a";
////		String	pwd		=	"pwd_for_user_a";
////			
////	try 
////	{
////		if(cisInterface.validateUser(user, secUtils.computeHashOf(pwd)))
////			System.out.println(user + " is authorized !");
////		else
////			System.out.println(user + " is NOT authorized !");				
////	} 
////	catch (Exception e) 
////	{
////		e.printStackTrace();
////		fail("Exception raised !");
////	}
////	
////	try 
////	{
////		if(cisInterface.isUserPresent(user))
////			System.out.println(user + " is already present, cannot add it !");
////		else
////		{
////			System.out.println("Adding user " + user + " ...");
////			cisInterface.addUser(user, secUtils.computeHashOf(pwd));
////			System.out.println("... done !");
////		}
////	} 
////	catch (CisServiceException e1) 
////	{
////		e1.printStackTrace();
////	} 
////	catch (SecurityUtilitiesException e) 
////	{
////		e.printStackTrace();
////	}
////
////	try 
////	{
////		if(cisInterface.validateUser(user, secUtils.computeHashOf(pwd)))
////			System.out.println(user + " is authorized !");
////		else
////			System.out.println(user + " is NOT authorized !");				
////	} 
////	catch (Exception e) 
////	{
////		e.printStackTrace();
////		fail("Exception raised !");
////	}
////	
////
////	try 
////	{
////		HITPayload	hitPayload	=	 cisInterface.getLowSecurityHITFor(user, secUtils.computeHashOf(pwd));
////		System.out.println(hitPayload.getInformation());
////		HIT	hit	= 	hitUtilities.buildHITFromPayload(hitPayload);
////		
////		System.out.println(hit);
////		System.out.println(prUtilities.getElementFor(
////				hit.getHitInfo().getProfile(), 
////				HELIOTags.dpas, 
////				HELIOTags.dpas_data_providers));
////	} 
////	catch (Exception e) 
////	{
////		e.printStackTrace();
////		fail("Exception raised !");
////	}
////	
////	try 
////	{
////		cisInterface.setStringPreferencesFor(user, 
////				secUtils.computeHashOf(pwd), 
////				HELIOTags.dpas, 
////				HELIOTags.dpas_data_providers, 
////				"provider3, provider2, provider1");
////	} 
////	catch (Exception e) 
////	{
////		e.printStackTrace();
////	}
////	try 
////	{
////		HITPayload	hitPayload	=	 cisInterface.getLowSecurityHITFor(user, secUtils.computeHashOf(pwd));
////		System.out.println(hitPayload.getInformation());
////		HIT	hit	= 	hitUtilities.buildHITFromPayload(hitPayload);
////		
////		System.out.println(hit);
////		System.out.println(prUtilities.getElementFor(
////				hit.getHitInfo().getProfile(), 
////				HELIOTags.dpas, 
////				HELIOTags.dpas_data_providers));
////	} 
////	catch (Exception e) 
////	{
////		e.printStackTrace();
////		fail("Exception raised !");
////	}
////
////	}
//	
//	
}
