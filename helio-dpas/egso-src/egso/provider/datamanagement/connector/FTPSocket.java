package org.egso.provider.datamanagement.connector;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.Socket;

import org.egso.provider.admin.ProviderMonitor;


/**
 * FTPSocket creates a Socket specific to FTP Servers connections.
 * 
 * @author Romain Linsolas (linsolas@gmail.com)
 * @version 1.1 - 29/11/2003 [09/2001].
 **/
public class FTPSocket {


	public static final String EOL = "\r\n" ;
	public static final int DEFAULT_PORT = 21 ;
	public static final String ASCII = "A" ;
	public static final String BINARY = "I" ;
	private boolean verbose = false ;
	private Socket ftpSocket = null ;
	private Writer writer = null ;
	private BufferedReader reader = null ;


	public FTPSocket(String server)
		throws IOException, FTPException {
		this(server, DEFAULT_PORT) ;
	}


	public FTPSocket(String server, int port)
		throws IOException, FTPException {
		ftpSocket = new Socket(server, port);
		initStreams();
		validateConnection();
	}


	public FTPSocket(InetAddress address)
		throws IOException, FTPException {
		this (address, DEFAULT_PORT);
	}


	public FTPSocket(InetAddress address, int port)
		throws IOException, FTPException {
		ftpSocket = new Socket(address, port);
		initStreams();
		validateConnection();
	}


	public void setVerbose(boolean verb) {
		verbose = verb;
	}


	private void validateConnection()
		throws IOException, FTPException {
		String response = readResponse();
		validateResponse(response, "220");
	}


	private void initStreams()
		throws IOException {
		InputStream is = ftpSocket.getInputStream();
		reader = new BufferedReader(new InputStreamReader(is));
		OutputStream os = ftpSocket.getOutputStream();
		writer = new OutputStreamWriter(os);
	}


	public String getRemoteHostName() {
		return(ftpSocket.getInetAddress().getHostName());
	}
		

	public void logout()
		throws IOException {
		writer.close();
		reader.close();
		ftpSocket.close();
	}


	public Socket createDataSocket()
		throws IOException, FTPException {
		String response = sendCommand("PASV");
		validateResponse(response, "227");
		int a = response.indexOf('(');
		int b = response.indexOf(')');
		String ipData = response.substring(a + 1, b);
		int[] parts = new int[6];
		int taille = ipData.length();
		int pos = 0;
		StringBuffer sb = new StringBuffer();
		for (int i = 0 ; (i < taille) && (pos <= 6) ; i++) {
			char c = ipData.charAt(i);
			if (Character.isDigit(c)) {
				sb.append(c);
			} else {
				if (c != ',') {
					throw new FTPException("Malformed PASV reply: " + response) ;
				}
			}
			if ((c == ',') || (i + 1 == taille)) {
				try {
					parts[pos] = Integer.parseInt(sb.toString());
					pos++;
					sb.setLength(0);
				} catch (NumberFormatException e) {
					ProviderMonitor.getInstance().reportException(e);
					throw new FTPException("Malformed PASV reply: " + response) ;
				}
			}
		}
		String ipAddress = parts[0] + "." + parts[1] + "." + parts[2] + "." + parts[3];
		int port = (parts[4] << 8) + parts[5];
		return(new Socket(ipAddress, port));
	}


	public String sendCommand(String cmd)
		throws IOException {
		writer.write(cmd + EOL);
		writer.flush();
		return (readResponse());
	}


	public String readResponse()
		throws IOException {
		StringBuffer response = new StringBuffer(reader.readLine());
		if (verbose) {
			System.out.println(response.toString());
		}
		String codeResponse = response.toString().substring(0, 3);
		if (response.charAt(3) == '-') {
			boolean complete = false ;
			String line = null ;
			while (!complete) {
				line = reader.readLine();
				if (line.length() == 0) {
					continue;
				}
				if (verbose) {
					System.out.println(line);
				}
				if ((line.substring(0, 3).equals(codeResponse)) && (line.charAt(3) == ' ')) {
					response.append(line.substring(3));
					complete = true;
				} else {
					response.append(" ");
					response.append(line);
				}
			}
		}
		return(response.toString());
	}


	public void validateResponse(String response, String correctCodeResponse)
		throws IOException, FTPException {
		String codeResponse = response.substring(0, 3);
		if (!codeResponse.equals(correctCodeResponse)) {
			throw(new FTPException(response.substring(4)));
		}
	}


	public void validateResponse(String response, String[] correctCodesResponse)
		throws IOException, FTPException {
		String codeResponse = response.substring(0, 3);
		for (int i = 0 ; i < correctCodesResponse.length ; i++) {
			if (codeResponse.equals(correctCodesResponse[i])) {
				return;
			}
		}
		throw (new FTPException(response.substring(4)));
	}


}

