package eu.heliovo.dpas.ie.services.vso.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eu.heliovo.dpas.ie.common.DAOFactory;
import eu.heliovo.dpas.ie.services.vso.transfer.VsoDataTO;

/**
 * Servlet implementation class VsoFitsResponseServlet
 */
public class VsoFitsResponseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VsoFitsResponseServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/octet-stream");
		VsoDataTO vsoTO=new VsoDataTO();
		PrintWriter pw = response.getWriter();
		
		try{
			//Setting start time & end time parameter
		     String fileId=request.getParameter("ID");
		     String provider=request.getParameter("PROVIDER");			
		     //Setting for Instrument parameter.
		     vsoTO.setOutput(pw);
		     vsoTO.setWhichProvider("VSO");
		     vsoTO.setStatus("webservice");
		     vsoTO.setProvider(provider);
		     vsoTO.setFileId(fileId);
		     //Calling DAO factory to connect VSO
		     DAOFactory daoFactory= DAOFactory.getDAOFactory(vsoTO.getWhichProvider());
		     daoFactory.getVsoQueryDao().getFitsFile(vsoTO);
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(" : Exception occured while creating the file :  "+e.getMessage());
		}		
		finally
		{
			if(pw!=null){
				pw.close();
				pw=null;
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

}
