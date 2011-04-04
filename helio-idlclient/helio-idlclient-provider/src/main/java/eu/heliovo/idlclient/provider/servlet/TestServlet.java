package eu.heliovo.idlclient.provider.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eu.heliovo.idlclient.provider.serialize.IdlConverter;

/**
 * Servlet implementation class TestServlet
 */
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public TestServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
		PrintWriter writer = response.getWriter();

		String starttime = request.getParameter("starttime");
		String endtime = request.getParameter("endtime");
		
		//Generate dummy testdata & fill in parameters
		CatalogService cs = new CatalogService();
		dummydata.fill(cs);
		if(starttime != null && endtime != null)
		{
			cs.getCatalogs().get(0).getFields().get(0).setMyFloat(Float.valueOf(starttime));
			cs.getCatalogs().get(0).getFields().get(1).setMyFloat(Float.valueOf(endtime));
		}
		
		//Serialize data to idl structure
		String out = IdlConverter.idlserialize(cs);
		writer.append(out);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
		PrintWriter writer = response.getWriter();
		
		System.out.println("Post");
		
		writer.append("test");
	}

}
