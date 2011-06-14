package eu.heliovo.shared.common.utilities;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityUtilities 
{
	public	String	computeHashOf(String s) throws SecurityUtilitiesException
	{
		try 
		{
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(s.getBytes());
		BigInteger hash = new BigInteger(1, md5.digest());
		return hash.toString(16);
		} 
		catch (NoSuchAlgorithmException nsae) 
		{
			throw new SecurityUtilitiesException();
		}
	}
}
