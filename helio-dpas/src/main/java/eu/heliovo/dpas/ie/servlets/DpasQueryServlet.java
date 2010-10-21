package eu.heliovo.dpas.ie.servlets;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import eu.heliovo.dpas.ie.controller.ServiceEngine;
import eu.heliovo.dpas.ie.services.cdaweb.dao.interfaces.CdaWebQueryDao;
import eu.heliovo.dpas.ie.services.common.transfer.CommonTO;
import eu.heliovo.dpas.ie.services.common.transfer.ResultTO;
import eu.heliovo.dpas.ie.services.common.utils.DAOFactory;
import eu.heliovo.dpas.ie.services.common.utils.HsqlDbUtils;
import eu.heliovo.dpas.ie.services.common.utils.VOTableCreator;
import eu.heliovo.dpas.ie.services.directory.dao.interfaces.DirQueryDao;
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
		ServiceEngine serviceEngine=null;
		ResultTO[] resultTo=null;
		try{
			 serviceEngine=new ServiceEngine();
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
		  		     
		     if(startTime!=null && startTime.length>0 && stopTime!=null && stopTime.length>0 && instruments!=null && instruments.length>0 && instruments.length==startTime.length && instruments.length==stopTime.length){
		    	 //VOTable header
				 VOTableCreator.writeHeaderOfTables(commonTO);
				 //For loop
		    	 for(int count=0;count<instruments.length;count++){
		    		 try{
		    		 //getting details from Provider access table
		    		 resultTo=HsqlDbUtils.getInstance().getAccessTableBasedOnInst(instruments[count]);
		    		 if(resultTo!=null && resultTo.length>0 && resultTo[0]!=null){
				    	 commonTO.setHelioInstrument(resultTo[0].getHelioInst());
				    	 System.out.println(" : Helio Instrument : "+resultTo[0].getHelioInst());
				    	 commonTO.setDateFrom(startTime[count]);
				    	 System.out.println(" : Start Date Contraint : "+stopTime[count]);
				    	 commonTO.setDateTo(stopTime[count]);
				    	 System.out.println(" : Stop Date Contraint : "+stopTime[count]);
				    	 commonTO.setWhichProvider(resultTo[0].getProviderName());
				    	 System.out.println(" : Provider Type : "+resultTo[0].getProviderName());
				    	 commonTO.setInstrument(resultTo[0].getInst());
				    	 System.out.println(" : Instrument : "+resultTo[0].getInst());
					     //Calling DAO factory to connect PROVIDERS
					     if(DAOFactory.getDAOFactory(commonTO.getWhichProvider()) instanceof VsoQueryDao ){
					    	 System.out.println("--->  VSO Provider intiated--->");
					    	 System.out.println(" : VSO Provider Name : "+resultTo[0].getProviderSource());
					    	 commonTO.setVotableDescription("VSO query response"+ resultTo[0].getProviderSource());
					    	 commonTO.setUrl(VsoUtils.getUrl(request));
					    	 commonTO.setProviderSource(resultTo[0].getProviderSource());
					    	 VsoQueryDao vsoQueryDao= (VsoQueryDao) DAOFactory.getDAOFactory(commonTO.getWhichProvider());
				         	 vsoQueryDao.query(commonTO);
					     }else if(DAOFactory.getDAOFactory(commonTO.getWhichProvider()) instanceof UocQueryDao ){
					    	 commonTO.setVotableDescription("UOC query response");
					    	 System.out.println("--->  UOC Provider intiated--->");
					    	 System.out.println(" : Table name for UOC  : "+resultTo[0].getObsId());
					    	 commonTO.setInstrument(resultTo[0].getObsId());
					    	 UocQueryDao uocQueryDao=(UocQueryDao)DAOFactory.getDAOFactory(commonTO.getWhichProvider());
					    	 uocQueryDao.query(commonTO);
					     }else if(DAOFactory.getDAOFactory(commonTO.getWhichProvider()) instanceof CdaWebQueryDao ){
					    	 System.out.println("--->  CDAWEB Provider intiated--->");
					    	 System.out.println(" : Mission name for CDAWEB  : "+resultTo[0].getObsId());
					    	 commonTO.setMissionName(resultTo[0].getObsId());
					    	 commonTO.setVotableDescription("CDAWEB query response");
					    	 CdaWebQueryDao cdaWebQueryDao=(CdaWebQueryDao)DAOFactory.getDAOFactory(commonTO.getWhichProvider());
					    	 cdaWebQueryDao.query(commonTO);
					     }else if(DAOFactory.getDAOFactory(commonTO.getWhichProvider()) instanceof DirQueryDao ){
					    	 commonTO.setVotableDescription("Archive query response");
					    	 System.out.println("--->  Directory Provider intiated--->");
					    	 DirQueryDao dirQueryDao=(DirQueryDao)DAOFactory.getDAOFactory(commonTO.getWhichProvider());
					    	 dirQueryDao.query(commonTO);
					     }
		    		 }else{
		    			 //commonTO.setBufferOutput(new BufferedWriter(pw));
				    	 commonTO.setVotableDescription("Error votable response, no data");
				    	 commonTO.setQuerystatus("ERROR");
				    	 commonTO.setQuerydescription("No data avialable for Instrument: "+instruments[count]);
						 VOTableCreator.writeErrorTables(commonTO);
		    		 }
		    		 //catch exception if there is error.
		    		 }catch (Exception e) {
						// TODO: handle exception
		    			System.out.println(" : Exception occured while creating the file :  "+e.getMessage());
	    				//commonTO.setBufferOutput(new BufferedWriter(pw));
	    				commonTO.setVotableDescription("Could not create VOTABLE, exception occured : "+e.getMessage()+" : "+instruments[count]);
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
		    	 }
		    	//VOTable footer.
				VOTableCreator.writeFooterOfTables(commonTO);
		     }else{
		    	 commonTO.setExceptionStatus("exception");
		    	 commonTO.setVotableDescription("VSO query response");
		    	 commonTO.setQuerystatus("ERROR");
		    	 commonTO.setQuerydescription("Start Time,EndTime and Instruments cannot be null");
				 VOTableCreator.writeErrorTables(commonTO);
		     }
		    
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(" : Exception occured while creating the file :  "+e.getMessage());
			if(instruments!=null && instruments.length==1)
				commonTO.setExceptionStatus("exception");
			commonTO.setBufferOutput(new BufferedWriter(pw));
			commonTO.setVotableDescription("Could not create VOTABLE, exception occured : "+e.getMessage());
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
