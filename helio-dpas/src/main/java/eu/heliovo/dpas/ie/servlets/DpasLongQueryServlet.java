package eu.heliovo.dpas.ie.servlets;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import eu.heliovo.dpas.ie.services.common.transfer.CommonTO;
import eu.heliovo.dpas.ie.services.common.transfer.FileResultTO;
import eu.heliovo.dpas.ie.services.common.utils.CommonUtils;
import eu.heliovo.dpas.ie.services.common.utils.FileUtils;
import eu.heliovo.dpas.ie.services.common.utils.InstanceHolders;
import eu.heliovo.dpas.ie.services.common.utils.RunService;
import eu.heliovo.dpas.ie.services.common.utils.VOTableCreator;

/**
 * Servlet implementation class DpasQueryServlet
 */
public class DpasLongQueryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DpasLongQueryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /** 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/xml;charset=UTF-8");
		CommonTO commonTO=new CommonTO();
		PrintWriter pw =  response.getWriter(); 
		String instruments[]=null;
		String startTime[]=null;
		String stopTime[]=null;
		commonTO.setRequest(request);
		FileResultTO fileTO=new FileResultTO();
		try{
		     //Setting start time & end time parameter
		     String sStartTime=request.getParameter("STARTTIME");
		     String sEndTime=request.getParameter("ENDTIME");			
		     //Setting for Instrument parameter.
		     String sInstrument=request.getParameter("INSTRUMENT");
		     commonTO.setTableName(sInstrument);
		    //FROM Clause
		     String sFrom=request.getParameter("FROM");
		     //setting where clause
		     String whereClause=request.getParameter("WHERE");
		     //Start time
		     if(sStartTime!=null && !sStartTime.trim().equals(""))
		    	 startTime=sStartTime.split(",");
		     //End Time
		     if(sEndTime!=null && !sEndTime.trim().equals(""))
		    	 stopTime=sEndTime.split(",");
		     //Instrument
		     if(sInstrument!=null && !sInstrument.trim().equals(""))
		    	 instruments=sInstrument.split(",");
		     String saveTo=request.getParameter("SAVETO");
		     //Setting SELECT parameter
		     String sSelect=request.getParameter("SELECT");
		     // Setting Print Writer.
		     commonTO.setContextUrl(CommonUtils.getUrl(request));
		     commonTO.setAllDateFrom(sStartTime);
		     commonTO.setAllDateTo(sEndTime);
		     commonTO.setAllInstrument(sInstrument);
		     commonTO.setInstruments(instruments);
		     commonTO.setStartTimes(startTime);
		     commonTO.setStopTimes(stopTime);
		     if(sFrom!=null && !sFrom.trim().equals("")){
		    	 commonTO.setInstruments(sFrom.split(","));
		    	 commonTO.setTableName(sFrom);
		     }
		     commonTO.setWhereClause(whereClause);
		     if(sSelect!=null && !sSelect.trim().equals(""))
		    	 commonTO.setSelect(sSelect.toUpperCase());
		     if(sFrom!=null && !sFrom.trim().equals(""))
		    	 commonTO.setInstruments(sFrom.split(","));
		     // Save To file.
			if(saveTo==null || saveTo==""){
				    saveTo= InstanceHolders.getInstance().getProperty("hsqldb.database.path")+"/files";
				    File f = new File(saveTo);
				    //Checking if directry present; if not create one.
				    if(!f.exists())
				    	f.mkdir();
				    //passing save to value to common TO.	
				   System.out.println(" : save to file location :  "+saveTo);
			}
			//Save To
			commonTO.setSaveto(saveTo);
			 //Creating UUID and generating unique ID.
		    UUID uuid = UUID.randomUUID();
		    String randomUUIDString = uuid.toString();
		    //Checking for file or ftp.
		    File file=null;
		    BufferedWriter bw =null;
		    String saveFilePath=null;
		    //
		    if(saveTo!=null && saveTo.trim().contains("ftp")){
		    	FileUtils.saveFileToFtp(saveTo,"votable_"+randomUUIDString+".xml");	    			    	
		    }else{
		    	 saveFilePath=saveTo+"/votable_"+randomUUIDString+".xml";
		    	 file = new File(saveFilePath);
			     bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
		    }
		    
		   //Setting print writer.
		    commonTO.setPrintWriter(bw);
		    //Buffered output
		    commonTO.setBufferOutput(bw);
		    //Status set to inform long running query
		    commonTO.setLongRunningQueryStatus("LongRunning");
		    //Running the service in back round
		    RunService oRunReport= new RunService(commonTO,randomUUIDString);
			Thread th = new Thread(oRunReport);
			th.start();
			
			//Creating a xml file 
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder docBuilder = builderFactory.newDocumentBuilder();
	        //creating a new instance of a DOM to build a DOM tree.
	        Document doc = docBuilder.newDocument();
	        //setting file TO.
	        fileTO.setRandomUUIDString(randomUUIDString);
	        String xmlString=CommonUtils.createXmlForWebService(fileTO);
			System.out.println(" : XML String : "+xmlString);
			pw.write(xmlString);
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

}
