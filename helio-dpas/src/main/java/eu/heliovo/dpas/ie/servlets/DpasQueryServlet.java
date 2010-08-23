package eu.heliovo.dpas.ie.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eu.heliovo.dpas.ie.common.DAOFactory;
import eu.heliovo.dpas.ie.services.vso.transfer.VsoDataTO;
import eu.heliovo.dpas.ie.services.vso.utils.VsoUtils;

/**
 * Servlet implementation class DpasQueryServlet
 */
public class DpasQueryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DpasQueryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/xml;charset=UTF-8");
		VsoDataTO vsoTO=new VsoDataTO();
		PrintWriter pw = response.getWriter(); 
		String instruments[]=null;
		String startTime[]=null;
		String stopTime[]=null;
		try{
		     //Setting start time & end time parameter
		     String sStartTime=request.getParameter("STARTTIME");
		     String sEndTime=request.getParameter("ENDTIME");			
		     //Setting for Instrument parameter.
		     String sInstrument=request.getParameter("INSTRUMENT");
		     //Start time
		     if(sStartTime!=null)
		    	 startTime=sStartTime.split(",");
		     //End Time
		     if(sStartTime!=null)
		    	 stopTime=sStartTime.split(",");
		     //Instrument
		     if(sInstrument!=null)
		    	 instruments=sInstrument.split(",");
		     vsoTO.setUrl(VsoUtils.getUrl(request));
		     vsoTO.setInstruments(instruments);
		     vsoTO.setStartTimes(startTime);
		     vsoTO.setStopTimes(stopTime);
		     vsoTO.setOutput(pw);
		     vsoTO.setWhichProvider("VSO");
		     vsoTO.setStatus("webservice");
		     vsoTO.setVotableDescription("VSO query response");
		     //responseReader= queryService.sortedQuery(instruments, startTime, stopTime, false, null, null,votable);
		     //Calling DAO factory to connect VSO
		     DAOFactory daoFactory= DAOFactory.getDAOFactory(vsoTO.getWhichProvider());
		     daoFactory.getVsoQueryDao().query(vsoTO);
		    
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
