package org.egso.provider.tools;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Hashtable;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.egso.provider.datamanagement.connector.FTPConnection;



@SuppressWarnings("serial")
public class FTPGatewayServlet extends HttpServlet {


	public Hashtable<String,FTPConnection> connections = null;
	private static final int LOGIN = 0;
	private static final int LOGOUT = 1;
	private static final int LIST = 2;
	private static final int DETAILED_LIST = 3;
	private static final int CHDIR = 4;
	private static final int GET = 5;
	private static final String[] commandNames = {"login", "logout", "list", "detailed_list", "chdir", "get"};
	private Random random = null;



	public FTPGatewayServlet() {
		connections = new Hashtable<String,FTPConnection>();
		random = new Random();
	}


	public void service(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
		System.out.println("[FTP Gateway] GETTING A FTP COMMAND:");
		System.out.println("[" + request.getQueryString() + "]");
		if (request.getParameter("command") == null) {
			System.out.println("FTP Servlet ERROR: No command found");
			return;
		}
		String cmd = request.getParameter("command").toLowerCase();
		boolean found = false;
		int i = 0;
		while (!found && (i < commandNames.length)) {
			found = cmd.equals(commandNames[i]);
			i++;
		}
		i--;
		if (!found) {
			System.out.println("ERROR: FTP Command " + cmd + " not recognized.");
			return;
		}
		FTPConnection ftp = null;
		String id = request.getParameter("id");
		if ((id == null) && (i != LOGIN)) {
			System.out.println("ERROR: ID not found (and command is not LOGIN");
			return;
		} else {
			if (i != LOGIN) {
				ftp = (FTPConnection) connections.get(id);
				if (ftp == null) {
					System.out.println("ERROR: For ID " + id + " no previous FTP Connection have been found");
					return;
				}
			}
		}
		String result = null;
		try {
			switch (i) {
				case LOGIN:
						String server = request.getParameter("server");
						int port = Integer.parseInt(request.getParameter("port"));
						String user = URLDecoder.decode(request.getParameter("user"), "UTF-8");
						String password = URLDecoder.decode(request.getParameter("password"), "UTF-8");
//						System.out.println("Connecting to server " + server + ":" + port + " " + user + "/" + password);
						id = "" + Math.abs(random.nextInt());
						System.out.println("ID Created :" + id);
						ftp = new FTPConnection(server, port);
						ftp.login(user, password);
						connections.put(id, ftp);
						result = id;
					break;
				case LOGOUT:
//						System.out.println("LOGING");
						connections.remove(id);
					break;
				case LIST:
						String mask = URLDecoder.decode(request.getParameter("mask"), "UTF-8");
//						System.out.println("Listing with mask " + mask);
						result = ftp.list(mask);
					break;
				case DETAILED_LIST:
						mask = URLDecoder.decode(request.getParameter("mask"), "UTF-8");
//						System.out.println("Listing with mask " + mask);
						result = ftp.list(mask, true);
					break;
				case CHDIR:
						String dir = URLDecoder.decode(request.getParameter("dir"), "UTF-8");
//						System.out.println("Changing directory to " + dir);
						ftp.chdir(dir);
					break;
				case GET:
/*						String local = request.getParameter("local");
						String remote = request.getParameter("remote");
						System.out.println("GETTING FILE: remote=" + remote + " localname=" + local);*/
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println("RESULT:\n" + result);
		if (result != null) {
			PrintWriter out = response.getWriter();
			out.print(result);
			out.flush();
			out.close();
		}
	}

}

