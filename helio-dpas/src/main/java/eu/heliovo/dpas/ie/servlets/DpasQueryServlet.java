package eu.heliovo.dpas.ie.servlets;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import eu.heliovo.dpas.ie.services.CommonDaoFactory;
import eu.heliovo.dpas.ie.services.common.dao.interfaces.ShortNameQueryDao;
import eu.heliovo.dpas.ie.services.common.transfer.CommonTO;
import eu.heliovo.dpas.ie.services.common.utils.CommonUtils;
import eu.heliovo.dpas.ie.services.common.utils.VOTableCreator;

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
		CommonTO commonTO=new CommonTO();
		PrintWriter pw =  response.getWriter(); 
		String instruments[]=null;
		String startTime[]=null;
		String stopTime[]=null;
		commonTO.setRequest(request);
		try{
		     //Setting start time & end time parameter
		     String sStartTime=request.getParameter("STARTTIME");
		     String sEndTime=request.getParameter("ENDTIME");			
		     //Setting for Instrument parameter.
		     String sInstrument=request.getParameter("INSTRUMENT");
		     //Start time
		     if(sStartTime!=null && !sStartTime.trim().equals(""))
		    	 startTime=sStartTime.split(",");
		     //End Time
		     if(sEndTime!=null && !sEndTime.trim().equals(""))
		    	 stopTime=sEndTime.split(",");
		     //Instrument
		     if(sInstrument!=null && !sInstrument.trim().equals(""))
		    	 instruments=sInstrument.split(",");
		     // Setting Print Writer.
		     commonTO.setPrintWriter(pw);
		     commonTO.setBufferOutput(new BufferedWriter(pw) );
		     commonTO.setContextUrl(CommonUtils.getUrl(request));
		     commonTO.setAllDateFrom(sStartTime);
		     commonTO.setAllDateTo(sEndTime);
		     commonTO.setAllInstrument(sInstrument);
		     commonTO.setInstruments(instruments);
		     commonTO.setStartTimes(startTime);
		     commonTO.setStopTimes(stopTime);
		    
		     ShortNameQueryDao shortNameDao= CommonDaoFactory.getInstance().getShortNameQueryDao();
		     shortNameDao.generateVOTable(commonTO);
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(" : Exception occurred while creating the file :  "+e.getMessage());
			if(instruments!=null && instruments.length==1)
				commonTO.setExceptionStatus("exception");
			commonTO.setBufferOutput(new BufferedWriter(pw));
			commonTO.setVotableDescription("Could not create VOTABLE, exception occurred : "+e.getMessage());
			commonTO.setQuerystatus("ERROR");
			commonTO.setQuerydescription(e.getMessage());
			try {
				//Sending error messages
				VOTableCreator.writeErrorTables(commonTO);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
