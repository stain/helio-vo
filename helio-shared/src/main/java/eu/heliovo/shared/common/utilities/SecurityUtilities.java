package eu.heliovo.shared.common.utilities;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.crypto.codec.Base64;


public class SecurityUtilities 
{
	/*
	 * Generic Utilities	
	 */
	private	SerializationUtilities	serUtilities	=	new SerializationUtilities();
	/*
	 * Other Utilities
	 */
	private String secret 		= 	"${%^|/#<>+;'@#!*"; // secret key length must be 16
//	private String secret 		= 	"tvnw63ufg9gh5392"; // secret key length must be 16
	private SecretKey key		=	null;
	private Cipher cipher		=	null;
	private Base64 coder		=	null;
	
	public SecurityUtilities() 
	{
		super();
		try 
		{
			key = new SecretKeySpec(secret.getBytes(), "AES");
			cipher = Cipher.getInstance("AES/ECB/PKCS5Padding", "SunJCE");
			coder = new Base64();
		} 
		catch (NoSuchAlgorithmException e) 
		{
			e.printStackTrace();
		} 
		catch (NoSuchProviderException e) 
		{
			e.printStackTrace();
		} 
		catch (NoSuchPaddingException e) 
		{
			e.printStackTrace();
		}
		coder = new Base64();
	}

	public String computeHashOf(String s) throws SecurityUtilitiesException 
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

	public String encrypt(String plainText)
			throws Exception 
	{
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] cipherText = cipher.doFinal(plainText.getBytes());
		return new String(coder.encode(cipherText));
	}

	public String decrypt(String codedText)
			throws Exception 
	{
		byte[] encypted = coder.decode(codedText.getBytes());
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] decrypted = cipher.doFinal(encypted);
		return new String(decrypted);
	}

//	public boolean hasLocalGridAuthorization(String hitAsString, String role) 
//	{
//		String	gridName	=	null;
//		String	gridPwd		=	null;		
//		/*
//		 * Getting the information from the HIT
//		 */
//		try 
//		{
//			HIT hit = (HIT) serUtilities.fromString(hitAsString);
//			gridName	=	getName(hit.getInfo());	
//			System.out.println("[SecurityUtilities] - Checking if " + gridName + " has role " + role );
//			gridPwd		=	getPassword(hit.getInfo());	
//		} 
//		catch (IOException e) 
//		{
//			e.printStackTrace();
//		} 
//		catch (ClassNotFoundException e) 
//		{
//			e.printStackTrace();
//		} 
//		catch (Exception e) 
//		{
//			e.printStackTrace();
//		}		
//		return true;
//	}

	public String getProxyNameFromHITAsString(String hitAsString) throws Exception 
	{
//		HIT hit	=	(HIT) serUtilities.fromString(hitAsString);
//		logUtilities.printShortLogEntry(" 1 : " + hit.toString());
//		logUtilities.printShortLogEntry(" 2 : " + hit.getInfo());
//		String	gridName	=	getName(hit.getInfo());	
//		logUtilities.printShortLogEntry(" 3 : " + gridName);
//		return gridName;
		return getName(((HIT) serUtilities.fromString(hitAsString)).getInfo());	
	}
	
	public String getProxyPwdFromHITAsString(String hitAsString) throws Exception 
	{
//		HIT hit	=	(HIT) serUtilities.fromString(hitAsString);
//		logUtilities.printShortLogEntry(" 1 : " + hit.toString());
//		logUtilities.printShortLogEntry(" 2 : " + hit.getInfo());
//		String	gridPwd	=	getPassword(hit.getInfo());	
//		logUtilities.printShortLogEntry(" 3 : " + gridPwd);
//		return gridPwd;
		return getPassword(((HIT) serUtilities.fromString(hitAsString)).getInfo());	
	}

	public String getUserNameFromHITAsString(String hitAsString) throws IOException, ClassNotFoundException 
	{
//		HIT hit	=	(HIT) serUtilities.fromString(hitAsString);
//		logUtilities.printShortLogEntry(hit.toString());
//		String 	userName	=	(String) hit.getPrincipal();
//		return 	userName;
		return (String) ((HIT) serUtilities.fromString(hitAsString)).getPrincipal();	
	}
}
