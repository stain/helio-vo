package eu.heliovo.cis.service.myproxy;

/*
 *
 * @author Giuseppe LA ROCCA
 * @mail giuseppe.larocca@ct.infn.it
 * @copyright 2010-01-28
 *
 * This class demonstrates a sample client for delegating a credential using
 * a MyProxy server.  This program uses a MyProxy server to delegate a
 * credential from a Java client to a web service.  The credential is read 
 * from a local file.
 * The credential is then delegated to the (same) MyProxy server using a temporary 
 * username and passphrase, plus a short lifetime.  The username/passphrase is then
 * sent to a web service which can use this information to get the delegated
 * credential.
 *	 
 * This class is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for 
 * more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.globus.common.CoGProperties;
import org.globus.myproxy.MyProxy;
import org.gridforum.jgss.ExtendedGSSCredential;
import org.gridforum.jgss.ExtendedGSSManager;
import org.ietf.jgss.GSSCredential;

import eu.heliovo.cis.service.common.LogUtilities;
import eu.heliovo.cis.service.common.PasswordUtilities;
import eu.heliovo.cis.service.hit.info.HITInfo;

public class MyProxyManager {
	/*
	 * Utilities
	 */
	LogUtilities 		logUtils = new LogUtilities();
	PasswordUtilities 	pwdUtils = new PasswordUtilities();

	/*
	 * Registers the default proxy into the proxy server
	 */
	public void registerProxy(HITInfo hitInfo, int duration) 
	{
		registerProxyToMyProxyServer(hitInfo.getCertId(), 
				hitInfo.getProxyServer(), 
				hitInfo.getProxyPort(), 
				duration);
	}

	/*
	 * Registers the proxy into the proxy server
	 */
	public void registerProxy(HITInfo hitInfo, String proxyFile, int duration) 
	{
		registerProxyToMyProxyServer(hitInfo.getCertId(), 
				hitInfo.getProxyServer(), 
				hitInfo.getProxyPort(),
				proxyFile,
				duration);
	}

	private void registerProxyToMyProxyServer(String certId,
			String proxyServer, 
			int proxyPort, 
			String proxyFile, 
			int duration) 
	{
		// Initialize the MyProxy class object
		org.globus.myproxy.MyProxy myProxyServer = new MyProxy();
		// Initialize the credential class object
		org.ietf.jgss.GSSCredential credential = null;

		String MYPROXY_PASSPHRASE = "";
		// Set MyProxy Server hostname
		myProxyServer.setHost(proxyServer);
		// Set MyProxy Server port
		myProxyServer.setPort(proxyPort);

		logUtils.printLongLogEntry(" USER ACCOUNT			: " + certId + "\n" + 
									" MyProxy Server			: " + proxyServer + "\n" +
									" MyProxy Server Port		: " + proxyPort + "\n" +
									" Proxy Lifetime			: " + duration);

		// Get credential from local proxy.
		credential = getCredFromFile();

		// If we successfully got a credential, then put it to MyProxy Server.
		if (credential != null) {
			// MYPROXY_PASSPHRASE =
			// PasswordField.readPassword("Enter MyProxy pass phrase: ");
			MYPROXY_PASSPHRASE = pwdUtils.getPwdFor(certId);

			// Check if the MyProxy pass phrase have been specified
			if (MYPROXY_PASSPHRASE.length() == 0)
				System.out.println(" No MyProxy pass phrase have been specified ");
			// log.info(" No MyProxy pass phrase have been specified ");
			else {
				// Initialize the MyProxy class object
				myProxyServer = new MyProxy();
				// Set MyProxy Server hostname
				myProxyServer.setHost(proxyServer);
				// Set MyProxy Server port
				myProxyServer.setPort(proxyPort);
				try {
					// Register delegated credentails to the MyProxy Server
					// using local credentials
					myProxyServer.put(credential, certId, MYPROXY_PASSPHRASE,
							duration);
					try {

						logUtils.printShortLogEntry(" Using credential    : "
								+ credential.getName());
						logUtils.printShortLogEntry(" A proxy valid for "
								+ credential.getRemainingLifetime()
								+ " sec. now exists on " + proxyServer
								+ " for user " + certId);

					} catch (org.ietf.jgss.GSSException cred) {
						System.out.println(cred.toString());
					}
				} catch (org.globus.myproxy.MyProxyException myproxy) {
					System.out.println(myproxy.toString());
				}
			}
		}		
	}

	/*
	 * Read in a credential from a file. If you do not specify a filename
	 * filename (i.e. pass in null), the default Globus filename will be used
	 * (e.g. "/tmp/x509up_u_msmith").
	 * 
	 * @param filename The name of the file from which to read the proxy
	 * credential. If null, use a default Globus filename.
	 * 
	 * @return A GSS credential read from file if successfully read in, or null
	 * if not.
	 */
	public static GSSCredential getCredFromFile() 
	{
	
		org.ietf.jgss.GSSCredential retcred = null;
	
		String filename = CoGProperties.getDefault().getProxyFile();
	
		if (filename.length() == 0)
	
			System.out.println(" No proxy file specified. "
					+ "Reading proxy from '" + filename + "'");
		// log.info (" No proxy file specified. " + "Reading proxy from '" +
		// filename + "'");
	
		try {
			File inFile = new File(filename);
			byte[] data = new byte[(int) inFile.length()];
			FileInputStream inStream = new FileInputStream(inFile);
			inStream.read(data);
			inStream.close();
	
			ExtendedGSSManager manager = (ExtendedGSSManager) ExtendedGSSManager
					.getInstance();
	
			retcred = manager.createCredential(data,
					ExtendedGSSCredential.IMPEXP_OPAQUE,
					GSSCredential.DEFAULT_LIFETIME, null, // use default
															// mechanism - GSI
					GSSCredential.INITIATE_AND_ACCEPT);
	
		} catch (Exception e) {
			// log.info (" Could not read proxy from '" + filename +
			// "' because " + e.getMessage());
			System.out.println(" Could not read proxy from '" + filename
					+ "' because " + e.getMessage());
	
		}
	
		// Retuen the user credentials to the main program.
		return retcred;
	
	}

	/*
	 * Read in a credential from a file. If you do not specify a filename
	 * filename (i.e. pass in null), the default Globus filename will be used
	 * (e.g. "/tmp/x509up_u_msmith").
	 * 
	 * @param filename The name of the file from which to read the proxy
	 * credential. If null, use a default Globus filename.
	 * 
	 * @return A GSS credential read from file if successfully read in, or null
	 * if not.
	 */
	public GSSCredential getCredFromFile(String fileLocation)
			throws MyProxyManagerException 
	{
		org.ietf.jgss.GSSCredential retcred = null;

		if (fileLocation.length() == 0)
			logUtils.printExceptionEntry(this.getClass(),
					"No proxy file specified. " + "Reading proxy from '"
							+ fileLocation + "'");

		try {
			File inFile = new File(fileLocation);
			if (inFile.canRead() && inFile.exists()) {
				byte[] data = new byte[(int) inFile.length()];
				FileInputStream inStream = new FileInputStream(inFile);
				inStream.read(data);
				inStream.close();

				ExtendedGSSManager manager = (ExtendedGSSManager) ExtendedGSSManager
						.getInstance();

				retcred = manager.createCredential(data,
						ExtendedGSSCredential.IMPEXP_OPAQUE,
						GSSCredential.DEFAULT_LIFETIME, null, // use default
						// mechanism - GSI
						GSSCredential.INITIATE_AND_ACCEPT);
			} else {
				logUtils.printExceptionEntry(this.getClass(), "Proxy file "
						+ fileLocation + " does not exist or cannot be read !");

				throw new MyProxyManagerException("Proxy file " + fileLocation
						+ " does not exist or cannot be read !");
			}
		} catch (Exception e) {
			logUtils.printExceptionEntry(this.getClass(),
					"Could not read proxy from '" + fileLocation + " because "
							+ e.getMessage());
			throw new MyProxyManagerException("Could not read proxy from '"
					+ fileLocation + " because " + e.getMessage());
		}
		return retcred;
	}

	/*
	 * @param MYPROXY_SERVER Hostname of the MyProxy Server to contact.
	 * 
	 * @param MYPROXY_PORT The MyProxy Server port on which the server is
	 * listening.
	 * 
	 * @param MYPROXY_USER_ACCOUNT The user account on the MyProxy Server.
	 * 
	 * @param MYPROXY_PROXYLIFETIME The life time for the user proxy.
	 * 
	 * @param MYPROXY_FILE The file containing the user proxy.
	 * 
	 * @param USERID The Unix ID for the user.
	 */

	public GSSCredential delegateProxyFromMyProxyServer(String id,
			String server, 
			int port) throws MyProxyManagerException 
	{
		// Initialize the MyProxy class object
		org.globus.myproxy.MyProxy myProxyServer = new MyProxy();
		// Initialize the credential class object
		org.ietf.jgss.GSSCredential credential = null;
		// Set MyProxy Server hostname
		myProxyServer.setHost(server);
		// Set MyProxy Server port
		myProxyServer.setPort(port);

		try 
		{
			// Retrieve delegated credentails from MyProxy Server anonymously
			// (without local credentials)

			credential = myProxyServer.get(id, pwdUtils.getPwdFor(id), 86400);

			// credential = myProxyServer.get (MYPROXY_USER_ACCOUNT,
			// MYPROXY_PASSPHRASE,
			// MYPROXY_PROXYLIFETIME);

			try 
			{
				if (credential != null) 
				{
					logUtils.printShortLogEntry("---------------------------------------------------------------------------------------");
					logUtils.printShortLogEntry(" Got proxy DN 	= "
							+ credential.getName());
					logUtils.printShortLogEntry(" Remaining lifetime = "
							+ credential.getRemainingLifetime() + " sec.");

					byte[] buf = ((ExtendedGSSCredential) credential).export(ExtendedGSSCredential.IMPEXP_OPAQUE);

					try 
					{
						FileOutputStream out = new FileOutputStream("/tmp/proxyfile");

						try 
						{
							out.write(buf);
							out.close();

							String X509_USER_PROXY = System.getProperty("X509_USER_PROXY", "/tmp/x509up_u" + "helio");
							logUtils.printShortLogEntry(" X509_USER_PROXY 	= "	+ X509_USER_PROXY);

							// Check if the proxy file has been successfully
							// created.
							File file_proxy = new File(X509_USER_PROXY);
							if (file_proxy.exists())
								logUtils.printShortLogEntry(" Proxy file has been successfully created!");
						} 
						catch (java.io.IOException io) 
						{
							System.out.println(io.toString());
							throw new MyProxyManagerException("IOException");
						}
					} 
					catch (java.io.FileNotFoundException fileexception) 
					{
						System.out.println(fileexception.toString());
						throw new MyProxyManagerException("File Not found exception");
					}
				}
			} 
			catch (org.ietf.jgss.GSSException e) 
			{
				System.out.println(e.toString());
				throw new MyProxyManagerException("GSSException");
			}
		} 
		catch (org.globus.myproxy.MyProxyException a) 
		{
			System.out.println(a.toString());
			throw new MyProxyManagerException("MyProxy Exception");
		}

		return credential;
	}

	/*
	 * @param MYPROXY_SERVER Hostname of the MyProxy Server to contact.
	 * 
	 * @param MYPROXY_PORT The MyProxy Server port on which the server is
	 * listening.
	 * 
	 * @param MYPROXY_USER_ACCOUNT The user account on the MyProxy Server.
	 * 
	 * @param MYPROXY_PROXYLIFETIME The life time for the user proxy.
	 */

	private void registerProxyToMyProxyServer(String id, 
			String MYPROXY_SERVER,
			int MYPROXY_PORT, 
			int MYPROXY_PROXYLIFETIME) 
	{
		// Initialize the MyProxy class object
		org.globus.myproxy.MyProxy myProxyServer = new MyProxy();
		// Initialize the credential class object
		org.ietf.jgss.GSSCredential credential = null;

		String MYPROXY_PASSPHRASE = "";
		// Set MyProxy Server hostname
		myProxyServer.setHost(MYPROXY_SERVER);
		// Set MyProxy Server port
		myProxyServer.setPort(MYPROXY_PORT);

		logUtils.printLongLogEntry(" USER ACCOUNT			: " + id + "\n" + 
									" MyProxy Server			: " + MYPROXY_SERVER + "\n" +
									" MyProxy Server Port		: " + MYPROXY_PORT + "\n" +
									" Proxy Lifetime			: " + MYPROXY_PROXYLIFETIME);

		// Get credential from local proxy.
		credential = getCredFromFile();

		// If we successfully got a credential, then put it to MyProxy Server.
		if (credential != null) {
			// MYPROXY_PASSPHRASE =
			// PasswordField.readPassword("Enter MyProxy pass phrase: ");
			MYPROXY_PASSPHRASE = pwdUtils.getPwdFor(id);

			// Check if the MyProxy pass phrase have been specified
			if (MYPROXY_PASSPHRASE.length() == 0)
				System.out
						.println(" No MyProxy pass phrase have been specified ");
			// log.info(" No MyProxy pass phrase have been specified ");
			else {
				// Initialize the MyProxy class object
				myProxyServer = new MyProxy();
				// Set MyProxy Server hostname
				myProxyServer.setHost(MYPROXY_SERVER);
				// Set MyProxy Server port
				myProxyServer.setPort(MYPROXY_PORT);
				try {
					// Register delegated credentails to the MyProxy Server
					// using local credentials
					myProxyServer.put(credential, id, MYPROXY_PASSPHRASE,
							MYPROXY_PROXYLIFETIME);
					try {
						// log.info(" Using credential    : " +
						// credential.getName());
						// log.info (" A proxy valid for " +
						// credential.getRemainingLifetime() +
						// " sec. now exists on " + MYPROXY_SERVER +
						// " for user " + MYPROXY_USER_ACCOUNT);

						logUtils.printShortLogEntry(" Using credential    : "
								+ credential.getName());
						logUtils.printShortLogEntry(" A proxy valid for "
								+ credential.getRemainingLifetime()
								+ " sec. now exists on " + MYPROXY_SERVER
								+ " for user " + id);

					} catch (org.ietf.jgss.GSSException cred) {
						System.out.println(cred.toString());
					}
				} catch (org.globus.myproxy.MyProxyException myproxy) {
					System.out.println(myproxy.toString());
				}
			}
		}

		// log.info("---------------------------------------------------------------------------------------");
//		System.out
//				.println("---------------------------------------------------------------------------------------");
	}

	public void registerProxy(GSSCredential credential, int duration) {
		// TODO Auto-generated method stub
		
	}

	
	
	
//	public static GSSCredential getCredFromFile(String filename) 
//	{
//		org.ietf.jgss.GSSCredential retcred = null;
//
//		if (filename.length() == 0)
//
//			System.out.println(" No proxy file specified. "
//					+ "Reading proxy from '" + filename + "'");
//		// log.info (" No proxy file specified. " + "Reading proxy from '" +
//		// filename + "'");
//
//		try {
//			File inFile = new File(filename);
//			byte[] data = new byte[(int) inFile.length()];
//			FileInputStream inStream = new FileInputStream(inFile);
//			inStream.read(data);
//			inStream.close();
//
//			ExtendedGSSManager manager = (ExtendedGSSManager) ExtendedGSSManager
//					.getInstance();
//
//			retcred = manager.createCredential(data,
//					ExtendedGSSCredential.IMPEXP_OPAQUE,
//					GSSCredential.DEFAULT_LIFETIME, null, // use default
//															// mechanism - GSI
//					GSSCredential.INITIATE_AND_ACCEPT);
//
//		} catch (Exception e) {
//			// log.info (" Could not read proxy from '" + filename +
//			// "' because " + e.getMessage());
//			System.out.println(" Could not read proxy from '" + filename
//					+ "' because " + e.getMessage());
//
//		}
//		return retcred;
//	}

	//		
	//	
	// /* M A I N */
	// public static void main (String[] args)
	// {
	// String MYPROXY_SERVER = "";
	// int MYPROXY_USERID = 0;
	// String MYPROXY_FILE = "";
	// int MYPROXY_PORT=0;
	// int MYPROXY_PROXYLIFETIME=0;
	// int read_credentials = 0;
	//
	// // Initialize the credential class object
	// org.ietf.jgss.GSSCredential credential = null;
	//		
	// log.setLevel(org.apache.log4j.Level.INFO);
	// log.info
	// ("-----------------------------------------------------------------------------");
	// log.info
	// (" +++ Configure System Setting(s) before to start user's proxy generation +++ ");
	// log.info ("");
	//		
	// String MYPROXY_USER_ACCOUNT = System.getProperty("user.name");
	// log.info
	// ("-----------------------------------------------------------------------------");
	//		
	// try {
	// // Check about the arguments provided in input.
	// if ((args == null) || (args.length < 6)) {
	// MyProxyManager.getUsage();
	// throw new Exception ("[ ARGUMENTS EXCEPTION ]");
	// }
	//		
	// //
	// =======================================================================
	// //
	// // C O N F I G U R E I N P U T S E T T I N G S
	// //
	// //
	// =======================================================================
	//			
	// //Hostname of the MyProxy Server to contact.
	// MYPROXY_SERVER = args[0];
	// //The MyProxy Server port on which the server is running.
	// MYPROXY_PORT = Integer.parseInt(args[1]);
	// //The proxy life time
	// if (args[2] != null) MYPROXY_PROXYLIFETIME = Integer.parseInt(args[2]);
	// else MYPROXY_PROXYLIFETIME = Integer.parseInt("604800"); //default value.
	// A proxy valid for 168 hours (7.0 days)
	//			
	// //The Unix ID of the user.
	// MYPROXY_USERID = Integer.parseInt(args[3]);
	//			
	// //The Unix ID of the user.
	// MYPROXY_FILE = args[4];
	//
	// // If read_credentials='1' user's credentials will be delegate from the
	// MyProxy Server
	// // If read_credentials='0' user's credentials will be register to the
	// MyProxy Server.
	// read_credentials = Integer.parseInt(args[5]);
	// 
	// if (read_credentials==1) {
	// // Delegate a certificate proxy from the MyProxy Server.
	// log.info(" Delegating in progress ... ");
	// credential=delegateProxyFromMyProxyServer (MYPROXY_SERVER, MYPROXY_PORT,
	// MYPROXY_USER_ACCOUNT,
	// MYPROXY_PROXYLIFETIME, MYPROXY_USERID, MYPROXY_FILE);
	// } else {
	// log.info(" Registering in progress ... ");
	// // Register a certificate proxy to the MyProxy Server.
	// registerProxyToMyProxyServer (MYPROXY_SERVER, MYPROXY_PORT,
	// MYPROXY_USER_ACCOUNT, MYPROXY_PROXYLIFETIME);
	// }
	// } catch (Exception exc){ System.out.println (exc.toString()); }
	// }
}
