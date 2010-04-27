package org.egso.provider.query;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.InvalidParameterException;
import java.util.Vector;

import org.egso.provider.admin.ProviderMonitor;
import org.egso.provider.datamanagement.connector.FTPConnection;
import org.egso.provider.datamanagement.connector.FTPException;


public class FTPQuery extends ArchiveQuery {
	
	
	public static final int LOGIN = 0;
	public static final int LOGOUT = 1;
	public static final int LIST = 2;
	public static final int DETAILED_LIST = 3;
	public static final int CHDIR = 4;
	public static final int GET = 5;
	private final String[] commandNames = {"login", "logout", "list", "detailed_list", "chdir", "get"};
	private final int[] numberOfParameters = {2, 0, 1, 1, 1, 2};
	private Vector<Integer> commands = null;
	private Vector<String[]> parameters = null;
	private int indexCmd = 0;
	private Vector<String> fields = null;
	private FTPConnection connection = null;
	private String gatewayURL = null;
	private boolean usesGateway = false;
	private String server = null;
	private String port = null;
	private String idGateway = null;


	public FTPQuery() {
		super(ArchiveQuery.FTP_ARCHIVE);
		commands = new Vector<Integer>();
		parameters = new Vector<String[]>();
		indexCmd = 0;
		fields = new Vector<String>();
		usesGateway = false;
	}

	public void useGateway(String url, String ftpServer, String ftpPort) {
		usesGateway = true;
		gatewayURL = url;
		server = ftpServer;
		port = ftpPort;
	}
	
	public void setFTPConnection(FTPConnection ftp) {
		connection = ftp;
		usesGateway = false;
	}
	
	public boolean usesGateway() {
		return (usesGateway);
	}
	
	public void addCommand(int cmd, String[] params)
			throws InvalidParameterException {
		// Test if the command exists.
		if ((cmd < 0) || (cmd >= commandNames.length)) {
			throw (new InvalidParameterException("Can't add an unknown FTP Command."));
		}
		// Test if we have parameters not null (only logout command allows params==null).
		if ((params == null) && (cmd != LOGOUT)) {
			throw (new InvalidParameterException("No parameters specified. Only the FTP command 'logout' does need no parameter."));
		}
		// Test if we have the correct number of parameters.
		if ((params != null) && (params.length != numberOfParameters[cmd])) {
			throw (new InvalidParameterException("Can't add the FTP command " + commandNames[cmd] +
				" because the number of parameters for this command is not correct (must have " + numberOfParameters[cmd] +
				" parameter(s) and not " + params.length + ")."));
		}
		commands.add(new Integer(cmd));
		parameters.add(params);
	}


	public String toString() {
		StringBuffer sb = new StringBuffer();
		String[] tmp = null;
		for (int i = 0 ; i < commands.size() ; i++) {
			sb.append("FTP COMMAND: " + commandNames[((Integer) commands.get(i)).intValue()] + " (");
			tmp = (String[]) parameters.get(i);
			if (tmp != null) {
				sb.append(tmp[0]);
				for (int j = 1 ; j < tmp.length ; j++) {
					sb.append(", " + tmp[j]);
				}
			}
			sb.append(")\n");
		}
		return (sb.toString());
	}

	public boolean hasLeftCommands() {
		return (indexCmd != commands.size());
	}
	
	public String executeNextCommand()
			throws FTPCommandExecutionException {
		if (indexCmd == commands.size()) {
			throw (new FTPCommandExecutionException("No more FTP command."));
		}
		int cmd = ((Integer) commands.get(indexCmd)).intValue();
		String[] param = (String[]) parameters.get(indexCmd);
		indexCmd++;
		if (usesGateway) {
			if (cmd == LOGIN) {
				return (sendCommandToGateway(cmd, new String[] {server, port, param[0], param[1]}));
			}
			return (sendCommandToGateway(cmd, param));
		}
		try {
			switch (cmd) {
				case LOGIN:
						connection.login(param[0], param[1]);
					break;
				case LOGOUT:
						connection.quit();
					break;
				case LIST:
						try {
							return (connection.list(param[0]).trim());
						} catch (FTPException e) {
							return (null);
						}
				case DETAILED_LIST:
						try {
							return (connection.list(param[0], true).trim());
						} catch (FTPException e) {
							return (null);
						}
				case CHDIR:
						connection.chdir(param[0]);
					break;
				case GET:
						connection.get(param[0], param[1]);
					break;
			}
		} catch (IOException e) {
			ProviderMonitor.getInstance().reportException(e);
			throw (new FTPCommandExecutionException("FTP Error: " + e.getMessage()));
		}
		return (null);
	}
	
	private String sendCommandToGateway(int cmd, String[] args) {
		// Encode the parameters.
		try {
			for (int i = 0 ; i < args.length ; i++) {
				args[i] = URLEncoder.encode(args[i], "UTF-8");
			}
		} catch (UnsupportedEncodingException uee) {
			uee.printStackTrace();
		}
		StringBuffer query = new StringBuffer(gatewayURL + "?command=" + commandNames[cmd] + "&");
		switch (cmd) {
			case LOGIN:
					query.append("server=" + args[0] + "&port=" + args[1]);
					query.append("&user=" + args[2] + "&password=" + args[3]);
				break;
			case CHDIR:
					query.append("id=" + idGateway + "&dir=" + args[0]);
				break;
			case LIST:
					query.append("id=" + idGateway + "&mask=" + args[0]);
				break;
			case DETAILED_LIST:
					query.append("id=" + idGateway + "&mask=" + args[0]);
				break;
			case LOGOUT:
					query.append("id=" + idGateway);
				break;
		}
		System.out.println("SENDING FTP COMMAND VIA GATEWAY [" + query.toString() + "]");
		StringBuffer sb = new StringBuffer();
		try {
			URL url = new URL(query.toString());
			URLConnection con = url.openConnection();
			con.connect();
			InputStream in = con.getInputStream();
			int x;
			while ((x = in.read()) != -1) {
				sb.append((char) x);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("RESULT:\n" + sb.toString());
		if ((cmd == LIST) || (cmd == DETAILED_LIST)) {
			return (sb.toString());
		}
		if (cmd == LOGIN) {
			idGateway = sb.toString();
		}
		return (null);
	}
	
	public int getLastExecutedCommand() {
		if (indexCmd == 0) {
			return (-1);
		}
		return (((Integer) commands.get(indexCmd - 1)).intValue());
	}

	public String[] getLastExecutedCommandParameters() {
		if (indexCmd == 0) {
			return (null);
		}
		return ((String[]) parameters.get(indexCmd - 1));
	}
	
	public boolean ignoreNextCommand() {
		if (indexCmd >= (commands.size() - 1)) {
			return (false);
		}
		indexCmd++;
		return(true);
	}
	
	public void restart() {
		indexCmd = 0;
	}
	
	/**
	 * Execute only the FTP commands that has not been executed yet.
	 * @param ftp FTP Connection object.
	 * @return An array of String where each String contains the result of each
	 * FTP command executed (some of them may be <code>null</code>).
	 **/
	public String[] executeAllLeftCommands(FTPConnection ftp)
			throws FTPCommandExecutionException {
		String[] results = new String[commands.size() - indexCmd];
		int cmd = 0;
		String[] param = null;
		while (indexCmd < commands.size()) {
			cmd = ((Integer) commands.get(indexCmd)).intValue();
			param = (String[]) parameters.get(indexCmd);
			results[indexCmd] = null;
			try {
				switch (cmd) {
					case LOGIN:
							ftp.login(param[0], param[1]);
						break;
					case LOGOUT:
							ftp.quit();
						break;
					case LIST:
							results[indexCmd] = ftp.list(param[0]);
						break;
					case DETAILED_LIST:
							results[indexCmd] = ftp.list(param[0], true);
						break;
					case CHDIR:
							ftp.chdir(param[0]);
						break;
					case GET:
							ftp.get(param[0]);
						break;
				}
			} catch (IOException e) {
				ProviderMonitor.getInstance().reportException(e);
				throw (new FTPCommandExecutionException("FTP Error: " + e.getMessage()));
			}
			indexCmd++;
		}
		return (results);
	}


	/**
	 * Execute ALL FTP commands.
	 * @param ftp FTP Connection object.
	 * @return An array of String where each String contains the result of each
	 * FTP command executed (some of them may be <code>null</code>).
	 **/
	public String[] executeAllCommands(FTPConnection ftp)
			throws FTPCommandExecutionException {
		indexCmd = 0;
		return (executeAllLeftCommands(ftp));
	}
	
	
	/**
	 * Test the validity of the sequence of FTP Commands.
	 * @return Boolean that indicates if the sequence seems ok or not.
	 **/
	public boolean isValid() {
		return (validity() == -1);
	}
	
	/**
	 * Indicates where is the first incoherence in the FTP commands sequence. If this
	 * sequence of commands is valid, then the method returns -1.
	 * @return Index of the first incoherent command. -1 if the sequence is correct.
	 **/
	private int validity() {
		if (commands.size() == 0) {
			return (-1);
		}
		int index = 0;
		// We must start with a login command.
		boolean mustHaveALogin = true;
		for (Integer cmd_obj:commands) {
			int cmd = cmd_obj.intValue();
			// If we await a login...
			if (mustHaveALogin && (cmd != LOGIN)) {
				return (index);
			}
			// If we have a Login, we must see if we are awating one...
			if ((cmd == LOGIN) && (!mustHaveALogin)) {
				return (index);
			}
			// If we have a logout, then the next command (if exists) must be a login.
			mustHaveALogin = (cmd == LOGOUT);
			index++;
		}
		// We must finish with a logout, so mustHaveALogin==true.
		if (!mustHaveALogin) {
			return (--index);
		}
		return (-1);
	}

	public String whyNotValid() {
		int x = validity();
		if (x == -1) {
			return ("The sequence is valid.");
		}
		if (x == 0) {
			return ("A sequence of FTP commands must start with a 'LOGIN'.");
		}
		if (x == commands.size()) {
			return ("A sequence of FTP commands must end with a 'LOGOUT'.");
		}
		int cmd = ((Integer) commands.get(x)).intValue();
		// If we encountered a problem with a Login, that means that we were already
		// connected to a server. A Login can only follow a logout or start a sequence.
		if (cmd == LOGIN) {
			return ("A 'LOGIN' command can only be used as the first command or just after a 'LOGOUT'.");
		}
		// If we reach this part of the code, this means that we have a command
		// that is not a login after a logout.
		return ("If a 'LOGOUT' is not the last command, then the next command must be a 'LOGIN'.");
	}


	public void addField(String field) {
		fields.add(field);
	}
	
	public void setFields(Vector<String> v) {
		fields = v;
	}
	
	public Vector<String> getSelectedFields() {
		return (fields);
	}

	public int getNextCommandType() {
		if (indexCmd == commands.size()) {
			return(-1);
		}
		return (((Integer) commands.get(indexCmd)).intValue());
	}
	
	public int howManyCommands(int cmd) {
		int nb = 0;
		for (int i:commands) {
			if (i == cmd) {
				nb++;
			}
		}
		return (nb);
	}

}

