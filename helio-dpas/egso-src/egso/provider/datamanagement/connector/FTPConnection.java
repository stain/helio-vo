package org.egso.provider.datamanagement.connector;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;


/**
 * The class FTPConnection provide all FTP command to manipulate data on FTP
 * servers. Considering the need we have with FTP server on the EGSO commands,
 * all methods to write, delete or create files and directories on the server
 * are disabled.
 *
 * @author    Romain Linsolas (linsolas@gmail.com)
 * @version   1.2 - 29/11/2003 [09/2001]
 */
/*
1.2 - 25/10/2004:
	Modification of the get() method (by P. Kunz). Old method is getAsByteArray().
*/
public class FTPConnection {

	/**
	 * JAVADOC: Description of the Field
	 */
	private FTPSocket ftpSocket = null;
	/**
	 * JAVADOC: Description of the Field
	 */
	private Socket data = null;
	/**
	 * JAVADOC: Description of the Field
	 */
	private String transfertType = null;


	/**
	 * JAVADOC: Constructor for the FTPConnection object
	 *
	 * @param remoteHost        JAVADOC: Description of the Parameter
	 * @exception IOException   JAVADOC: Description of the Exception
	 * @exception FTPException  JAVADOC: Description of the Exception
	 */
	public FTPConnection(String remoteHost)
			 throws IOException, FTPException {
		ftpSocket = new FTPSocket(remoteHost);
	}


	/**
	 * JAVADOC: Constructor for the FTPConnection object
	 *
	 * @param remoteHost        JAVADOC: Description of the Parameter
	 * @param port              JAVADOC: Description of the Parameter
	 * @exception IOException   JAVADOC: Description of the Exception
	 * @exception FTPException  JAVADOC: Description of the Exception
	 */
	public FTPConnection(String remoteHost, int port)
			 throws IOException, FTPException {
		ftpSocket = new FTPSocket(remoteHost, port);
	}


	/**
	 * JAVADOC: Constructor for the FTPConnection object
	 *
	 * @param remoteAddr        JAVADOC: Description of the Parameter
	 * @exception IOException   JAVADOC: Description of the Exception
	 * @exception FTPException  JAVADOC: Description of the Exception
	 */
	public FTPConnection(InetAddress remoteAddr)
			 throws IOException, FTPException {
		ftpSocket = new FTPSocket(remoteAddr);
	}


	/**
	 * JAVADOC: Constructor for the FTPConnection object
	 *
	 * @param remoteAddr        JAVADOC: Description of the Parameter
	 * @param port              JAVADOC: Description of the Parameter
	 * @exception IOException   JAVADOC: Description of the Exception
	 * @exception FTPException  JAVADOC: Description of the Exception
	 */
	public FTPConnection(InetAddress remoteAddr, int port)
			 throws IOException, FTPException {
		ftpSocket = new FTPSocket(remoteAddr, port);
	}


	/**
	 * JAVADOC: Sets the type attribute of the FTPConnection object
	 *
	 * @param type              JAVADOC: The new type value
	 * @exception IOException   JAVADOC: Description of the Exception
	 * @exception FTPException  JAVADOC: Description of the Exception
	 */
	public void setType(String type)
			 throws IOException, FTPException {
		String typeStr = FTPSocket.ASCII;
		if (type.equals(FTPSocket.BINARY)) {
			typeStr = FTPSocket.BINARY;
		}
		ftpSocket.validateResponse(ftpSocket.sendCommand("TYPE " + typeStr), "200");
		transfertType = type;
	}


	/**
	 * JAVADOC: Sets the verbose attribute of the FTPConnection object
	 *
	 * @param verb  JAVADOC: The new verbose value
	 */
	public void setVerbose(boolean verb) {
		ftpSocket.setVerbose(verb);
	}


	/**
	 * JAVADOC: Gets the type attribute of the FTPConnection object
	 *
	 * @return   JAVADOC: The type value
	 */
	public String getType() {
		return (transfertType);
	}


	/**
	 * JAVADOC: Gets the remoteHostName attribute of the FTPConnection object
	 *
	 * @return   JAVADOC: The remoteHostName value
	 */
	public String getRemoteHostName() {
		return (ftpSocket.getRemoteHostName());
	}


	/**
	 * JAVADOC: Description of the Method
	 *
	 * @param localDirectory    JAVADOC: Description of the Parameter
	 * @param remoteFile        JAVADOC: Description of the Parameter
	 * @exception IOException   JAVADOC: Description of the Exception
	 * @exception FTPException  JAVADOC: Description of the Exception
	 */
	public void get(String localDirectory, String remoteFile)
			 throws IOException, FTPException {
		System.out.print("Getting " + remoteFile + " file...");
		data = ftpSocket.createDataSocket();
		DataInputStream in = new DataInputStream(data.getInputStream());
		BufferedInputStream bIn = new BufferedInputStream(in);
		String response = ftpSocket.sendCommand("RETR " + remoteFile);
		String[] validCodes = {"125", "150"};
		ftpSocket.validateResponse(response, validCodes);
		int taille = 4096;
		byte[] chunk = new byte[taille];
		int count;
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(localDirectory, false));
		while ((count = bIn.read(chunk, 0, taille)) >= 0) {
			out.write(chunk, 0, count);
		}
		out.close();
		try {
			bIn.close();
			data.close();
		} catch (IOException ignore) {
		}
		validCodes = new String[]{"226", "250"};
		response = ftpSocket.readResponse();
		ftpSocket.validateResponse(response, validCodes);
		System.out.println(" done");
	}


	/**
	 * JAVADOC: Gets the asByteArray attribute of the FTPConnection object
	 *
	 * @param remoteFile        JAVADOC: Description of the Parameter
	 * @return                  JAVADOC: The asByteArray value
	 * @exception IOException   JAVADOC: Description of the Exception
	 * @exception FTPException  JAVADOC: Description of the Exception
	 */
	public byte[] getAsByteArray(String remoteFile)
			 throws IOException, FTPException {
		data = ftpSocket.createDataSocket();
		DataInputStream in = new DataInputStream(data.getInputStream());
		BufferedInputStream bIn = new BufferedInputStream(in);
		String response = ftpSocket.sendCommand("RETR " + remoteFile);
		String[] validCodes = {"125", "150"};
		ftpSocket.validateResponse(response, validCodes);
		int taille = 4096;
		byte[] chunk = new byte[taille];
		byte[] resultBuf = new byte[taille];
		byte[] temp = null;
		int count;
		int bufsize = 0;
		while ((count = bIn.read(chunk, 0, taille)) >= 0) {
			temp = new byte[bufsize + count];
			System.arraycopy(resultBuf, 0, temp, 0, bufsize);
			System.arraycopy(chunk, 0, temp, bufsize, count);
			resultBuf = temp;
			bufsize += count;
		}
		try {
			bIn.close();
			data.close();
		} catch (IOException ignore) {
		}
		validCodes = new String[]{"226", "250"};
		response = ftpSocket.readResponse();
		ftpSocket.validateResponse(response, validCodes);
		return (resultBuf);
	}


	/**
	 * JAVADOC: Description of the Method
	 *
	 * @param remoteFile        JAVADOC: Description of the Parameter
	 * @return                  JAVADOC: Description of the Return Value
	 * @exception IOException   JAVADOC: Description of the Exception
	 * @exception FTPException  JAVADOC: Description of the Exception
	 */
	public InputStream get(String remoteFile)
		throws IOException, FTPException {
		// check if there is a path in 'remoteFile'. if so we need to
		// compose this call with a chdir(path) + a get(naked filename only)
		int slashindex = remoteFile.lastIndexOf('/');
		if (slashindex >= 0) {
			String pathPortion = remoteFile.substring(0, slashindex);
			String fileNamePortion = remoteFile.substring(slashindex + 1);
			chdir(pathPortion);
			return get(fileNamePortion);
		}

		// here comes the file contents delivered
		data = ftpSocket.createDataSocket();
		DataInputStream in = new DataInputStream(data.getInputStream());
		BufferedInputStream bIn =
			new BufferedInputStream(in) {
				// the processing of the ftp server response to RETR is deferred to
				// after the stream has been closed.
				public void close() throws IOException {
					super.close();
					String response = ftpSocket.readResponse();
					ftpSocket.validateResponse(response, new String[]{"226"});
				}
			};
		String response = ftpSocket.sendCommand("RETR " + remoteFile);
		ftpSocket.validateResponse(response, new String[]{"125", "150"});
		return (bIn);
	}


	/**
	 * JAVADOC: Description of the Method
	 *
	 * @param user              JAVADOC: Description of the Parameter
	 * @param password          JAVADOC: Description of the Parameter
	 * @exception IOException   JAVADOC: Description of the Exception
	 * @exception FTPException  JAVADOC: Description of the Exception
	 */
	public void login(String user, String password)
			 throws IOException, FTPException {
		String response = ftpSocket.sendCommand("USER " + user);
		ftpSocket.validateResponse(response, "331");
		response = ftpSocket.sendCommand("PASS " + password);
		ftpSocket.validateResponse(response, "230");
		setType(FTPSocket.BINARY);
	}


	/**
	 * JAVADOC: Description of the Method
	 *
	 * @param user              JAVADOC: Description of the Parameter
	 * @exception IOException   JAVADOC: Description of the Exception
	 * @exception FTPException  JAVADOC: Description of the Exception
	 */
	public void user(String user)
			 throws IOException, FTPException {
		String response = ftpSocket.sendCommand("USER " + user);
		String[] validCodes = {"230", "331"};
		ftpSocket.validateResponse(response, validCodes);
	}


	/**
	 * JAVADOC: Description of the Method
	 *
	 * @param password          JAVADOC: Description of the Parameter
	 * @exception IOException   JAVADOC: Description of the Exception
	 * @exception FTPException  JAVADOC: Description of the Exception
	 */
	public void password(String password)
			 throws IOException, FTPException {
		String response = ftpSocket.sendCommand("PASS " + password);
		String[] validCodes = {"230", "202"};
		ftpSocket.validateResponse(response, validCodes);
		setType(FTPSocket.BINARY);
	}


	/**
	 * JAVADOC: Description of the Method
	 *
	 * @param command           JAVADOC: Description of the Parameter
	 * @param validCodes        JAVADOC: Description of the Parameter
	 * @exception IOException   JAVADOC: Description of the Exception
	 * @exception FTPException  JAVADOC: Description of the Exception
	 */
	public void quote(String command, String[] validCodes)
			 throws IOException, FTPException {
		String response = ftpSocket.sendCommand(command);
		if ((validCodes != null) && (validCodes.length > 0)) {
			ftpSocket.validateResponse(response, validCodes);
		}
	}


	/**
	 * JAVADOC: Description of the Method
	 *
	 * @param mask              JAVADOC: Description of the Parameter
	 * @return                  JAVADOC: Description of the Return Value
	 * @exception IOException   JAVADOC: Description of the Exception
	 * @exception FTPException  JAVADOC: Description of the Exception
	 */
	public String list(String mask)
			 throws IOException, FTPException {
		return (list(mask, false));
	}


	/**
	 * JAVADOC: Description of the Method
	 *
	 * @param mask              JAVADOC: Description of the Parameter
	 * @param details           JAVADOC: Description of the Parameter
	 * @return                  JAVADOC: Description of the Return Value
	 * @exception IOException   JAVADOC: Description of the Exception
	 * @exception FTPException  JAVADOC: Description of the Exception
	 */
	public String list(String mask, boolean details)
			 throws IOException, FTPException {
		boolean isBinary = false;
		if (transfertType.equals(FTPSocket.BINARY)) {
			isBinary = true;
			setType(FTPSocket.ASCII);
		}
		data = ftpSocket.createDataSocket();
		InputStreamReader in = new InputStreamReader(data.getInputStream());
		BufferedReader bIn = new BufferedReader(in);
		String command = details ? "LIST " : "NLST ";
		String response = ftpSocket.sendCommand(command + mask);
		String[] validCodes = {"125", "150"};
		ftpSocket.validateResponse(response, validCodes);
		int taille = 4096;
		char[] chunk = new char[taille];
		char[] resultBuf = new char[taille];
		char[] temp = null;
		int count;
		int bufsize = 0;
		while ((count = bIn.read(chunk, 0, taille)) >= 0) {
			temp = new char[bufsize + count];
			System.arraycopy(resultBuf, 0, temp, 0, bufsize);
			System.arraycopy(chunk, 0, temp, bufsize, count);
			resultBuf = temp;
			bufsize += count;
		}
		try {
			bIn.close();
			data.close();
		} catch (IOException ignore) {
		}
		validCodes = new String[]{"226", "250"};
		response = ftpSocket.readResponse();
		ftpSocket.validateResponse(response, validCodes);
		if (isBinary) {
			setType(FTPSocket.BINARY);
		}
		String result = null;
		if (resultBuf.length > 0) {
			result = new String(resultBuf);
		}
		return (result);
	}


	/**
	 * JAVADOC: Description of the Method
	 *
	 * @param dir               JAVADOC: Description of the Parameter
	 * @exception IOException   JAVADOC: Description of the Exception
	 * @exception FTPException  JAVADOC: Description of the Exception
	 */
	public void chdir(String dir)
			 throws IOException, FTPException {
		String response = ftpSocket.sendCommand("CWD " + dir);
		ftpSocket.validateResponse(response, "250");
	}


	/**
	 * JAVADOC: Description of the Method
	 *
	 * @return                  JAVADOC: Description of the Return Value
	 * @exception IOException   JAVADOC: Description of the Exception
	 * @exception FTPException  JAVADOC: Description of the Exception
	 */
	public String pwd()
			 throws IOException, FTPException {
		String response = ftpSocket.sendCommand("PWD");
		ftpSocket.validateResponse(response, "257");
		return (response);
	}


	/**
	 * JAVADOC: Description of the Method
	 *
	 * @exception IOException   JAVADOC: Description of the Exception
	 * @exception FTPException  JAVADOC: Description of the Exception
	 */
	public void quit()
			 throws IOException, FTPException {
		String response = ftpSocket.sendCommand("QUIT" + FTPSocket.EOL);
		ftpSocket.validateResponse(response, "221");
		ftpSocket.logout();
		ftpSocket = null;
	}

	/*
// The methods below are nos used by the EGSO system.
	public String system ()
		throws IOException, FTPException {
		String response = ftpSocket.sendCommand ("SYST") ;
		ftpSocket.validateResponse (response, "215") ;
		return (response) ;
	}
	public boolean site (String command)
		throws IOException, FTPException {
		String response = ftpSocket.sendCommand ("SITE " + command) ;
		String[] codesValides = {"200", "202", "502"} ;
		ftpSocket.validateResponse (response, codesValides) ;
		return (response.substring(0, 3).equals ("200")) ;
	}
	public void put (String repertoireLocal, String fichierDistant)
		throws IOException, FTPException {
		put (repertoireLocal, fichierDistant, false) ;
	}
	public void put (String repertoireLocal, String fichierDistant, boolean append)
		throws IOException, FTPException
{
		data = ftpSocket.createDataSocket () ;
		DataOutputStream out = new DataOutputStream (data.getOutputStream ()) ;
		String cmd = append ? "APPE " : "STOR " ;
		String response = ftpSocket.sendCommand (cmd + fichierDistant) ;
		String[] codesValides = {"125", "150"} ;
		ftpSocket.validateResponse (response, codesValides) ;
		FileInputStream input = new FileInputStream (repertoireLocal) ;
		byte[] buf = new byte[512] ;
		int count = 0 ;
		while ((count = input.read (buf)) > 0) {
			out.write (buf, 0, count) ;
		}
		input.close () ;
		out.flush () ;
		out.close () ;
		try {
			data.close () ;
		} catch (IOException ignore) {
		}
		String[] codesValides2 = {"226", "250"} ;
		response = ftpSocket.lectureReponse () ;
		ftpSocket.validateResponse (response, codesValides2) ;
	}
	public void put (byte[] bytes, String fichierDistant)
		throws IOException, FTPException {
		put (bytes, fichierDistant, false) ;
	}
	public void put (byte[] bytes, String fichierDistant, boolean append)
		throws IOException, FTPException
{
		data = ftpSocket.createDataSocket () ;
		DataOutputStream out = new DataOutputStream (data.getOutputStream ()) ;
		String cmd = append ? "APPE " : "STOR " ;
		String response = ftpSocket.sendCommand (cmd + fichierDistant) ;
		String[] codesValides = {"125", "150"} ;
		ftpSocket.validateResponse (response, codesValides) ;
		out.write (bytes, 0, bytes.length) ;
		out.flush () ;
		out.close () ;
		try {
			data.close () ;
		} catch (IOException ignore) {
		}
		String[] codesValides2 = {"226", "250"} ;
		response = ftpSocket.lectureReponse () ;
		ftpSocket.validateResponse (response, codesValides2) ;
	}
	public void delete (String fichierDistant)
		throws IOException, FTPException {
		String response = ftpSocket.sendCommand ("DELE " + fichierDistant) ;
		ftpSocket.validateResponse (response, "250") ;
	}
	public void rename (String from, String to)
		throws IOException, FTPException {
		String response = ftpSocket.sendCommand ("RNFR " + from) ;
		ftpSocket.validateResponse (response, "350") ;
		response = ftpSocket.sendCommand ("RNTO " + to) ;
		ftpSocket.validateResponse (response, "250") ;
	}
	public void rmdir (String dir)
		throws IOException, FTPException {
		String response = ftpSocket.sendCommand ("RMD " + dir) ;
		ftpSocket.validateResponse (response, "250") ;
	}
	public void mkdir (String dir)
		throws IOException, FTPException {
		String response = ftpSocket.sendCommand ("MKD " + dir) ;
		ftpSocket.validateResponse (response, "257") ;
	}
*/
}

