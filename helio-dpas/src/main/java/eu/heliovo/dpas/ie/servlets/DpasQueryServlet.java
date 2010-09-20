package eu.heliovo.dpas.ie.servlets;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import eu.heliovo.dpas.ie.common.CommonTO;
import eu.heliovo.dpas.ie.common.DAOFactory;
import eu.heliovo.dpas.ie.common.VOTableCreator;
import eu.heliovo.dpas.ie.dataProviders.DPASDataProvider;
import eu.heliovo.dpas.ie.services.cdaweb.dao.interfaces.CdaWebQueryDao;
import eu.heliovo.dpas.ie.services.uoc.dao.interfaces.UocQueryDao;
import eu.heliovo.dpas.ie.services.vso.dao.interfaces.VsoQueryDao;
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
		CommonTO commonTO=new CommonTO();
		PrintWriter pw =  response.getWriter(); 
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
		     commonTO.setVotableDescription("DPAS query response");
		     
		     if(startTime!=null && startTime.length>0 && stopTime!=null && stopTime.length>0 && instruments!=null && instruments.length>0 && instruments.length==startTime.length && instruments.length==stopTime.length){
		    	 //VOTable header
				 VOTableCreator.writeHeaderOfTables(commonTO);
				 //For loop
		    	 for(int count=0;count<instruments.length;count++){
			    	 commonTO.setInstrument(instruments[count].split("__")[1]);
			    	 commonTO.setDateFrom(startTime[count]);
			    	 commonTO.setDateTo(stopTime[count]);
			    	 commonTO.setWhichProvider(instruments[count].split("__")[0]);
				     //Calling DAO factory to connect PROVIDERS
				     if(DAOFactory.getDAOFactory(commonTO.getWhichProvider()) instanceof VsoQueryDao ){
				    	 commonTO.setVotableDescription("VSO query response");
				    	 commonTO.setUrl(VsoUtils.getUrl(request));
				    	 VsoQueryDao vsoQueryDao= (VsoQueryDao) DAOFactory.getDAOFactory(commonTO.getWhichProvider());
			         	 vsoQueryDao.query(commonTO);
				     }else if(DAOFactory.getDAOFactory(commonTO.getWhichProvider()) instanceof UocQueryDao ){
				    	 UocQueryDao uocQueryDao=(UocQueryDao)DAOFactory.getDAOFactory(commonTO.getWhichProvider());
				    	 uocQueryDao.query(commonTO);
				     }else if(DAOFactory.getDAOFactory(commonTO.getWhichProvider()) instanceof CdaWebQueryDao ){
				    	 commonTO.setVotableDescription("CDAWEB query response");
				    	 CdaWebQueryDao cdaWebQueryDao=(CdaWebQueryDao)DAOFactory.getDAOFactory(commonTO.getWhichProvider());
				    	 cdaWebQueryDao.query(commonTO);
				     }else{
				    	 
				     }
		    	 }
		    	//VOTable footer.
				VOTableCreator.writeFooterOfTables(commonTO);
		     }else{
		    	 commonTO.setBufferOutput(new BufferedWriter(pw));
		    	 commonTO.setVotableDescription("VSO query response");
		    	 commonTO.setQuerystatus("ERROR");
		    	 commonTO.setQuerydescription("Start Time,EndTime and Instruments cannot be null");
				 VOTableCreator.writeErrorTables(commonTO);
		     }
		    
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(" : Exception occured while creating the file :  "+e.getMessage());
			commonTO.setBufferOutput(new BufferedWriter(pw));
			commonTO.setVotableDescription("VSO query response");
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
