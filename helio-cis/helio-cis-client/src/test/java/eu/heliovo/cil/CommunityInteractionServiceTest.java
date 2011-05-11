package eu.heliovo.cil;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.ietf.jgss.GSSException;
import org.junit.Ignore;
import org.junit.Test;

import eu.heliovo.cil.common.LogUtilities;
import eu.heliovo.hit.info.HIT;

public class CommunityInteractionServiceTest 
{
	LogUtilities					logUtils	=	new LogUtilities();
	CommunityInteractionService		cis			=	null;
	
	@Test public void testCommunityInteractionService() 
	{
		logUtils.printShortLogEntry("Testing creation of default CIS....");
		cis	=	new CommunityInteractionService();
		/*
		 * Testing that the instance of the CIS is not null
		 */
		assertTrue(cis != null);
		logUtils.printShortLogEntry("....done !");
	}

				
	@Test public void testValidateLogin() 
	{	
		/*
		 * Removing the configuration file...
		 */
		try 
		{
			removeDataFile();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		/*
		 * Instantiating a new Community Interaction Service
		 */
		cis	=	new CommunityInteractionService();
		/*
		 * Testing an empty password repository...
		 */		
		assertFalse(cis.validateUser("user_a", "pwd_for_user_a"));
		assertFalse(cis.validateUser("user_a", "bad_pwd_for_user_a"));

		assertFalse(cis.validateUser("user_b", "pwd_for_user_b"));
		assertFalse(cis.validateUser("user_b", "bad_pwd_for_user_b"));

		assertFalse(cis.validateUser("no_such_user", "pwd_for_no_such_user"));
		assertFalse(cis.validateUser("no_such_user", "bad_pwd_for_no_such_user"));
		/*
		 * Adding user_a
		 */
		try 
		{
			cis.addUserWithStandardProfile("user_a", "pwd_for_user_a");
//			cis.addUserWithSpecificProfile("user_a", "pwd_for_user_a", "profile_for_user_a");
		} 
		catch (CommunityInteractionServiceException e) 
		{
			e.printStackTrace();
			assertFalse(true);
		}
		assertTrue(cis.validateUser("user_a", "pwd_for_user_a"));
		assertFalse(cis.validateUser("user_a", "bad_pwd_for_user_a"));

		assertFalse(cis.validateUser("user_b", "pwd_for_user_b"));
		assertFalse(cis.validateUser("user_b", "bad_pwd_for_user_b"));

		assertFalse(cis.validateUser("no_such_user", "pwd_for_no_such_user"));
		assertFalse(cis.validateUser("no_such_user", "bad_pwd_for_no_such_user"));

		/*
		 * Adding user_b
		 */
		try 
		{
			cis.addUserWithStandardProfile("user_b", "pwd_for_user_b");
//			cis.addUserWithSpecificProfile("user_b", "pwd_for_user_b", "profile_for_user_b");
		} 
		catch (CommunityInteractionServiceException e) 
		{
			e.printStackTrace();
			assertFalse(true);
		}

		assertTrue(cis.validateUser("user_a", "pwd_for_user_a"));
		assertFalse(cis.validateUser("user_a", "bad_pwd_for_user_a"));

		assertTrue(cis.validateUser("user_b", "pwd_for_user_b"));
		assertFalse(cis.validateUser("user_b", "bad_pwd_for_user_b"));

		assertFalse(cis.validateUser("no_such_user", "pwd_for_no_such_user"));
		assertFalse(cis.validateUser("no_such_user", "bad_pwd_for_no_such_user"));
		
		/*
		 * Instantiating a new Community Interaction Service
		 * with the load file present
		 */
		cis	=	new CommunityInteractionService();
		assertTrue(cis.validateUser("user_a", "pwd_for_user_a"));
		assertFalse(cis.validateUser("user_a", "bad_pwd_for_user_a"));

		assertTrue(cis.validateUser("user_b", "pwd_for_user_b"));
		assertFalse(cis.validateUser("user_b", "bad_pwd_for_user_b"));

		assertFalse(cis.validateUser("no_such_user", "pwd_for_no_such_user"));
		assertFalse(cis.validateUser("no_such_user", "bad_pwd_for_no_such_user"));
	}	

	@Test  public	void	testGetLowSecurityHITFor()
	{
		HIT		hit		=	null;
		
		testCommunityInteractionService();

		hit	=	cis.getLowSecurityHITFor("user_a", "pwd_for_user_a");
		assertTrue(hit != null);
		
		logUtils.printLongLogEntry(hit.toString());
	}

	
	@Test public	void	testGetLowSecurityHITForAnonymousUser()
	{
		HIT		hit		=	null;
		
		testCommunityInteractionService();
		
		hit	=	cis.getLowSecurityHITForAnonymousUser();
		assertTrue(hit != null);
		
		logUtils.printLongLogEntry(hit.toString());
	}

	/*
	 * Skipped for now as it requires certificates
	 */
	@Ignore @Test public	void	testGetHighSecurityHITForAnonymousUser()
	{
		HIT		hit		=	null;
		
		testCommunityInteractionService();
		
		hit	=	cis.getHighSecurityHITForAnonymousUser();
		assertTrue(hit != null);
		
		logUtils.printLongLogEntry(hit.toString());
	}

	/*
	 * Skipped for now as it requires certificates
	 */
	@Ignore @Test public	void	testGetHighSecurityHITFor()
	{
		HIT		hit		=	null;

		testCommunityInteractionService();

		try 
		{
			hit	=	cis.getHighSecurityHITFor("user_a", "pwd_for_user_a");
			logUtils.printShortLogEntry(hit.getHSC().getName().toString());
		} 
		catch (GSSException e) 
		{
			e.printStackTrace();
		} 
		logUtils.printLongLogEntry(hit.toString());
	}

	@Test public	void	testSetUserProfileEntity()
	{
		testCommunityInteractionService();

		try 
		{
			cis.setUserProfileEntity("user_a", HELIOTags.hec, "entity_1", "value");
		} 
		catch (CommunityInteractionServiceException e) 
		{
			e.printStackTrace();
		} 
	}

	
//	public void testCreateHIT() 
//	{
//		String	identity	=	"Gab";
//		
//		logUtils.printShortLogEntry("Testing creation of default CIS....");
//		cis	=	new CommunityInteractionService();
//		/*
//		 * Testing that the instance of the CIS is not null
//		 */
//		assertTrue(cis != null);
//		logUtils.printShortLogEntry("....done !");
//		logUtils.printShortLogEntry("Testing creation of a low security HIT for " + identity + " ....");
//		hit	=	cis.createHIT(false, identity);
//		logUtils.printLongLogEntry(hit.toString());
//		logUtils.printShortLogEntry("....done !");
//
//		assertTrue(cis != null);
//		logUtils.printShortLogEntry("....done !");
//		logUtils.printShortLogEntry("Testing creation of a high security HIT for " + identity + " ....");
//		hit	=	cis.createHIT(true, identity);
//		logUtils.printLongLogEntry(hit.toString());
//		logUtils.printShortLogEntry("....done !");
//	}

	
//	public void testCreateHITAndRegister() 
//	{
//		String	identity	=	"Gab";
//		
//		logUtils.printShortLogEntry("Testing creation of default CIS....");
//		cis	=	new CommunityInteractionService();
//		/*
//		 * Testing that the instance of the CIS is not null
//		 */
//		assertTrue(cis != null);
//		logUtils.printShortLogEntry("....done !");
//		logUtils.printShortLogEntry("Testing creation of a low security HIT for " + identity + " ....");
//		hit	=	cis.createHIT(false, identity);
//		logUtils.printLongLogEntry(hit.toString());
//		logUtils.printShortLogEntry("....done !");
//
//		assertTrue(cis != null);
//		logUtils.printShortLogEntry("....done !");
//		logUtils.printShortLogEntry("Testing creation of a high security HIT for " + identity + " ....");
//		hit	=	cis.createHITAndRegister(identity);
//		assertTrue(cis != null);
//		logUtils.printLongLogEntry(hit.toString());
//		logUtils.printShortLogEntry("....done !");
//	}

//	public void testCommunityInteractionServiceString() 
//	{
//		assertTrue(true);
//	}
//
//	public void testCommunityInteractionServiceStringInt() 
//	{
//		assertTrue(true);
//	}
	
//	private CommunityInteractionService initializeCIS()
//	{
//		logUtils.printShortLogEntry("Testing creation of default CIS....");
//		CommunityInteractionService cis	=	new CommunityInteractionService();
//		assertTrue(cis != null);
//		logUtils.printShortLogEntry("....done !");		
//		return cis;
//	}
//
//	private HIT createAndRegisterHIT(CommunityInteractionService cis, String identity)
//	{
//		logUtils.printShortLogEntry("Testing creation and registration of an HIT....");
//		HIT hit	=	cis.createHITAndRegister(identity);
//		assertTrue(hit != null);
//		logUtils.printShortLogEntry("....done !");				
//		return hit;
//	}
//	
//	public void testScenario() 
//	{
//		String	identity	=	"Gab";
//		/*
//		 * Creates an HIT and registers it to my proxy...
//		 */
//		HIT		hit	=	initializeCIS().createHITAndRegister(identity);
//	}
	
//	public void testHashPwd()
//	{
//		logUtils.printShortLogEntry("Testing the hash of password....");
//
//		String	password	=	"test_pwd";
//		
//		MessageDigest digest	=	null;
//		try 
//		{
//			digest = MessageDigest.getInstance("SHA-1");
//			digest.reset();
//			byte[] input = digest.digest(password.getBytes("UTF-8"));
//			for(int n = 0; n< input.length; n++)
//				logUtils.printShortLogEntry(String.valueOf(input[n]));
//				
//		} 
//		catch (NoSuchAlgorithmException e) 
//		{
//			e.printStackTrace();
//		} 
//		catch (UnsupportedEncodingException e) 
//		{
//			e.printStackTrace();
//		}

//		
//			String hashword = null;
//			try {
//			MessageDigest md5 = MessageDigest.getInstance("MD5");
//			md5.update(password.getBytes());
//			BigInteger hash = new BigInteger(1, md5.digest());
//			hashword = hash.toString(16);
//			logUtils.printShortLogEntry(password + "-->" +hashword);
//			} 
//			catch (NoSuchAlgorithmException nsae) 
//			{
//
//			}		
//	}


	private void removeDataFile() {
		String fileName = "HITRepository.data";
		// A File object to represent the filename
		File f = new File(fileName);

		// Make sure the file or directory exists and isn't write protected
		if (f.exists()) {
			if (!f.canWrite())
				throw new IllegalArgumentException("Delete: write protected: "
						+ fileName);

			// If it is a directory, make sure it is empty
			if (f.isDirectory()) {
				String[] files = f.list();
				if (files.length > 0)
					throw new IllegalArgumentException(
							"Delete: directory not empty: " + fileName);
			}

			// Attempt to delete it
			boolean success = f.delete();

			if (!success)
				throw new IllegalArgumentException("Delete: deletion failed");
		}
	}

}
