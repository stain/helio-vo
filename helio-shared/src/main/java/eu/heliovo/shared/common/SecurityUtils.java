package eu.heliovo.shared.common;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityUtils 
{
	static final String key = "HelioTestKey"; 

	public SecurityUtils() 
	{
		super();		
	}

	public String computeHashOf(String s) throws SecurityUtilsException 
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
			throw new SecurityUtilsException();
		}
	}

	public String prepare(String name, String pwd)	throws Exception 
	{
		return encrypt(name + "=" + pwd);
	}

	public String getName(String coded) throws Exception 
	{
		String decoded = decrypt(coded);
		return decoded.substring(0, decoded.indexOf("="));
	}

	public String getPassword(String coded)
			throws Exception {
		String decoded = decrypt(coded);
		return decoded.substring(decoded.indexOf("=") + 1, decoded.length());
	}

	public String encrypt(String str)
	{
	      StringBuffer sb = new StringBuffer (str);

	      int lenStr = str.length();
	      int lenKey = key.length();
		   
	      for ( int i = 0, j = 0; i < lenStr; i++, j++ ) 
	      {
	         if ( j >= lenKey ) j = 0;
	         sb.setCharAt(i, (char)(str.charAt(i) ^ key.charAt(j))); 
	      }
	      return sb.toString();
	   }
	   
	   public String decrypt(String str)
	   {
	      return encrypt(str);
	   }
	
	public String getProxyNameFromHITAsString(String hitAsString) throws Exception 
	{
		return getName(((HIT) SerializationUtils.fromString(hitAsString)).getInfo());	
	}
	
	public String getProxyPwdFromHITAsString(String hitAsString) throws Exception 
	{
		return getPassword(((HIT) SerializationUtils.fromString(hitAsString)).getInfo());	
	}

	public String getUserNameFromHITAsString(String hitAsString) throws IOException, ClassNotFoundException 
	{
		return (String) ((HIT) SerializationUtils.fromString(hitAsString)).getPrincipal();	
	}
	
	public String getpwdHashFromHITAsString(String hitAsString) throws IOException, ClassNotFoundException 
	{
		return (String) ((HIT) SerializationUtils.fromString(hitAsString)).getCredentials();	
	}
}
