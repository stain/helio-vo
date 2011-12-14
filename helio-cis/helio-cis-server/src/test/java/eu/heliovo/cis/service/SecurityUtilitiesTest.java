package eu.heliovo.cis.service;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.heliovo.shared.common.utilities.SecurityUtilities;
import eu.heliovo.shared.common.utilities.SecurityUtilitiesException;

public class SecurityUtilitiesTest 
{
	SecurityUtilities	secUtils	=	new SecurityUtilities();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testComputeHashOf() 
	{
		String	pwd	=	"pwd_4_HelioAdministrator";
		
		try 
		{
			System.out.println(pwd + " --> " + secUtils.computeHashOf(pwd));
		} 
		catch (SecurityUtilitiesException e) 
		{
			e.printStackTrace();
		}
	}
}
