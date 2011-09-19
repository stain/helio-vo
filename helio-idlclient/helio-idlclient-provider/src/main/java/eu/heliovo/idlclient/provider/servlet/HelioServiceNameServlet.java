package eu.heliovo.idlclient.provider.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eu.heliovo.clientapi.HelioClient;
import eu.heliovo.idlclient.provider.serialize.*;
import eu.heliovo.registryclient.HelioServiceName;
import eu.heliovo.registryclient.ServiceCapability;

/**
 * Servlet implementation class HelioServiceNameServlet
 */
public class HelioServiceNameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HelioServiceNameServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IdlConverter idl = IdlConverter.getInstance();
		
		response.setContentType("text/plain");
		PrintWriter writer = response.getWriter();
		
		HelioClient helioClient = new HelioClient();
		
		ArrayList<String> serviceNameList = new ArrayList<String>();
		for (HelioServiceName c : helioClient.getServiceNamesByCapability(ServiceCapability.ASYNC_QUERY_SERVICE)) {
			serviceNameList.add(c.toString());
        }
		writer.println(idl.idlserialize(serviceNameList));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
