package  eu.heliovo.dpas.ie.services.common.server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Provider;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.WebServiceProvider;
import javax.xml.ws.handler.MessageContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import eu.heliovo.dpas.ie.services.CommonDaoFactory;
import eu.heliovo.dpas.ie.services.common.dao.interfaces.LongRunningQueryDao;
import eu.heliovo.dpas.ie.services.common.transfer.CommonTO;
import eu.heliovo.dpas.ie.services.common.transfer.FileResultTO;
import eu.heliovo.dpas.ie.services.common.utils.CommonUtils;
import eu.heliovo.dpas.ie.services.common.utils.FileUtils;
import eu.heliovo.dpas.ie.services.common.utils.HsqlDbUtils;
import eu.heliovo.dpas.ie.services.common.utils.InstanceHolders;
import eu.heliovo.dpas.ie.services.common.utils.LongRunningQueryIdHolders;
import eu.heliovo.dpas.ie.services.common.utils.RunService;
import eu.heliovo.dpas.ie.services.common.utils.VOTableCreator;
import eu.heliovo.dpas.ie.services.common.utils.VotableThreadAnalizer;

/**
 * Class: SoapDispatcher
 * Description: The dispatcher handles all soap requests and responses for a Query.  Called via the
 * SoapServlet. SoapRequests (Bodies) are placed into a DOM and by analyzing the uri 
 * determine the correct query service for the correct contract.  Responses are Stream based (NOT DOM) into an 
 * XMLStreamReader with the help of PipedStreams.
 *
 */
@WebServiceProvider(targetNamespace="",
	      serviceName="",
	      portName="")     
	      
@ServiceMode(value=javax.xml.ws.Service.Mode.PAYLOAD)

public class SoapDispatcher implements Provider<Source> {
	@javax.annotation.Resource(type=Object.class)
	 protected WebServiceContext wsContext; 
	

  /**
   * Method: Constructor
   * Description: Setup any initiations, mainly hashtable of uri to query 
   * interface versions.
   */
  public SoapDispatcher() {
	 
  }

  /**
   * Method: invoke
   * Description:For all soap requests and 
   * responses.Using Metro.
   * @param request - Source that is used to extract the soap request.
   * @return Source - response StreamSource that contains the 
   * soap response populated by InputStream (PipedInputStream) 
   */
  
	@SuppressWarnings("null")
	@Override
	public Source invoke(Source request) {
		StreamSource responseReader = null;
		MessageContext mc = wsContext.getMessageContext();
		HttpServletRequest req = (HttpServletRequest)mc.get(MessageContext.SERVLET_REQUEST);
		PipedReader pr=null;
		PipedWriter pw=null;
		CommonTO commonTO=new CommonTO();
	    String[] startTime =null;
	    String[] stopTime =null;
	    String[] instruments =null;
	    String[] from =null;
	    String saveTo =null;
	    FileResultTO fileTO=new FileResultTO();
	    //Creating UUID and generating unique ID.
		UUID uuid = UUID.randomUUID();
		String randomUUIDString = uuid.toString();
		File file=null;
		String saveFilePath=null;
		
		try {
			 Element inputDoc=toDocument(request);
			 String interfaceName = inputDoc.getLocalName().intern();
		     boolean votable=true;
		     
		    if(inputDoc.getElementsByTagNameNS("*","STARTTIME").getLength()>0 && inputDoc.getElementsByTagNameNS("*","STARTTIME").item(0).getFirstChild()!=null){
				 NodeList nodeList=inputDoc.getElementsByTagNameNS("*","STARTTIME");
    			 startTime=new String[nodeList.getLength()];
    			 //List Name
		    	 for(int i=0;i<nodeList.getLength();i++){
		    		 startTime[i]=nodeList.item(i).getFirstChild().getNodeValue();
		    	 }
			 }
    		 
	    	if(inputDoc.getElementsByTagNameNS("*","ENDTIME").getLength()>0 && inputDoc.getElementsByTagNameNS("*","ENDTIME").item(0).getFirstChild()!=null){
	    			 NodeList nodeList=inputDoc.getElementsByTagNameNS("*","ENDTIME");
	    			 stopTime=new String[nodeList.getLength()];
	    			 //List Name
			    	 for(int i=0;i<nodeList.getLength();i++){
			    		 stopTime[i]=nodeList.item(i).getFirstChild().getNodeValue();
			    	 }
	    	 }	    
		     //Counter for instrument.	  
		     int instCount=0;
		     if(inputDoc.getElementsByTagNameNS("*","INSTRUMENT").getLength()>0 && inputDoc.getElementsByTagNameNS("*","INSTRUMENT").item(0).getFirstChild()!=null){
		    	 NodeList nodeList=inputDoc.getElementsByTagNameNS("*","INSTRUMENT");
				 instruments=new String[nodeList.getLength()];
    			 //List Name
		    	 for(int i=0;i<nodeList.getLength();i++){
		    		 instruments[i]=nodeList.item(i).getFirstChild().getNodeValue();
		    		 instCount++;
		    	 }
			 }
		     //Setting 'INSTRUMENT' value to FROM.
		     if(inputDoc.getElementsByTagNameNS("*","FROM").getLength()>0 && inputDoc.getElementsByTagNameNS("*","FROM").item(0).getFirstChild()!=null && instCount==0){
				 NodeList nodeList=inputDoc.getElementsByTagNameNS("*","FROM");
				 instruments=new String[nodeList.getLength()];
    			 //List Name
		    	 for(int i=0;i<nodeList.getLength();i++){
		    		 instruments[i]=nodeList.item(i).getFirstChild().getNodeValue();
		    	 }			 
			 }
		     
		     //Setting for Start Row parameter.
			 if(inputDoc.getElementsByTagNameNS("*","STARTINDEX").getLength()>0 && inputDoc.getElementsByTagNameNS("*","STARTINDEX").item(0).getFirstChild()!=null){
				 String startRow = inputDoc.getElementsByTagNameNS("*","STARTINDEX").item(0).getFirstChild().getNodeValue();
				 
			 }
			//Setting for No Of Rows parameter.
			 if(inputDoc.getElementsByTagNameNS("*","MAXRECORDS").getLength()>0 && inputDoc.getElementsByTagNameNS("*","MAXRECORDS").item(0).getFirstChild()!=null){
				 String noOfRows = inputDoc.getElementsByTagNameNS("*","MAXRECORDS").item(0).getFirstChild().getNodeValue();
				
			 }
		     
			 if(inputDoc.getElementsByTagNameNS("*","INSTRUMENT").getLength()>0 && inputDoc.getElementsByTagNameNS("*","INSTRUMENT").item(0).getFirstChild()!=null){
				 String inst = inputDoc.getElementsByTagNameNS("*","INSTRUMENT").item(0).getFirstChild().getNodeValue();
				
			 }
			//Setting for WHERE parameter.
			 if(inputDoc.getElementsByTagNameNS("*","WHERE").getLength()>0 && inputDoc.getElementsByTagNameNS("*","WHERE").item(0).getFirstChild()!=null){
				 String whereClause = inputDoc.getElementsByTagNameNS("*","WHERE").item(0).getFirstChild().getNodeValue();
				
			 }
			 	     
		     //
		     pr = new PipedReader();
			 pw = new PipedWriter(pr);
			 commonTO.setPrintWriter(pw);
		     commonTO.setBufferOutput(new BufferedWriter(pw) );
		     commonTO.setInstruments(instruments);
		     commonTO.setStartTimes(startTime);
		     commonTO.setStopTimes(stopTime);
		     commonTO.setRequest(req);
		     commonTO.setContextUrl(CommonUtils.getUrl(req));
		     //Start time
		     if(startTime!=null && !startTime.toString().trim().equals(""))
		    	 commonTO.setAllDateFrom(CommonUtils.arrayToString(startTime,","));
		     //Stop time
		     if(stopTime!=null && !stopTime.toString().trim().equals(""))
		    	 commonTO.setAllDateTo(CommonUtils.arrayToString(stopTime,","));
		     //Instruments
		     if(instruments!=null && !instruments.toString().trim().equals(""))
		    	 commonTO.setAllInstrument(CommonUtils.arrayToString(instruments,","));
		     
		     if(interfaceName == "LongTimeQuery".intern() || interfaceName == "LongQuery".intern()){
		     
		     if(startTime!=null && startTime.length>0 && stopTime!=null && stopTime.length>0 && instruments!=null && instruments.length>0 && instruments.length==startTime.length && instruments.length==stopTime.length){
		    	 
		     		 //Setting for No Of Rows parameter.
					 if(inputDoc.getElementsByTagNameNS("*","SAVETO").getLength()>0 && inputDoc.getElementsByTagNameNS("*","SAVETO").item(0).getFirstChild()!=null){
						 saveTo = inputDoc.getElementsByTagNameNS("*","SAVETO").item(0).getFirstChild().getNodeValue();
					 } 
					 // Save To file.
					 if(saveTo==null || saveTo==""){
					    saveTo= InstanceHolders.getInstance().getProperty("hsqldb.database.path")+"/files";
					    File f = new File(saveTo);
					    //Checking if directry present; if not create one.
					    if(!f.exists())
					    	f.mkdir();		    
					 }
					 System.out.println(" : save to file location :  "+saveTo);
					 //passing save to value to common TO.	
					 commonTO.setSaveto(saveTo);
					 commonTO.setLongRunningQueryStatus("id");
					 //
					 fileTO.setRandomUUIDString(randomUUIDString);
					 //
					 String xmlString=CommonUtils.createXmlForWebService(fileTO);
					 System.out.println(" : XML String : "+xmlString);
					 BufferedWriter bw =null;
					 //Set data to print writer.
					 commonTO.setDataXml(xmlString);
					 //Setting piped reader 
					 commonTO.setLongRunningPrintWriter(pw);
					 //Thread created to load data into response.
					 LongRunningQueryDao longRunningQueryDao= CommonDaoFactory.getInstance().getLongRunningQueryDao();
					 longRunningQueryDao.generatelongRunningQueryXML(commonTO);	
					 System.out.println(" : Done VOTABLE : ");	
					 //Save To 
					 if(saveTo!=null && saveTo.contains("ftp")){
					    FileUtils.saveFileToFtp(saveTo,"votable_"+randomUUIDString+".xml");	    			    	
					 }else{
						//Save the file to local system.
					    saveFilePath=saveTo+"/votable_"+randomUUIDString+".xml";
					    file = new File(saveFilePath);
						bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
						//Setting print writer.
						commonTO.setPrintWriter(bw);
						//Buffered output
						commonTO.setBufferOutput(bw);
						//Running the service in back round
					 }
					 RunService oRunReport= new RunService(commonTO,randomUUIDString);
					 Thread th = new Thread(oRunReport);
					 th.start();
					 //Long running query status of completion.
		     }else{
		    	 commonTO.setExceptionStatus("exception");
		    	 commonTO.setBufferOutput(new BufferedWriter(pw));
		    	 commonTO.setVotableDescription("DPAS query response");
		    	 commonTO.setQuerystatus("ERROR");
		    	 commonTO.setQuerydescription("Start Time,EndTime and Instruments cannot be null");
				 VOTableCreator.writeErrorTables(commonTO);
		     }
			 }else if(interfaceName == "GetStatus".intern()){
				 String sID =null;
				 if(inputDoc.getElementsByTagNameNS("*","ID").getLength()>0 && inputDoc.getElementsByTagNameNS("*","ID").item(0).getFirstChild()!=null){
		    		 sID = inputDoc.getElementsByTagNameNS("*","ID").item(0).getFirstChild().getNodeValue();
				 }
				 commonTO.setLongRunningQueryStatus("status");
				 String sStatus=LongRunningQueryIdHolders.getInstance().getProperty(sID);
					if(sStatus==null || sStatus.trim().equals(""))
					  sStatus=HsqlDbUtils.getInstance().getStatusFromHsqlDB(sID);
					//Setting file TO
					fileTO.setRandomUUIDString(sID);
					fileTO.setStatus(sStatus);
					
					String xmlString=CommonUtils.createXmlForWebService(fileTO);
					System.out.println(" : XML String : "+xmlString);	
					//Setting piped reader 
					 commonTO.setLongRunningPrintWriter(pw);
					//Set data to print writer.
					commonTO.setDataXml(xmlString);
					//Thread created to load data into response.
					LongRunningQueryDao longRunningQueryDao= CommonDaoFactory.getInstance().getLongRunningQueryDao();
					longRunningQueryDao.generatelongRunningQueryXML(commonTO);	
					//Long running query file location path result.
			 }else if(interfaceName == "GetResults".intern()){
				 String sID =null;
				 if(inputDoc.getElementsByTagNameNS("*","ID").getLength()>0 && inputDoc.getElementsByTagNameNS("*","ID").item(0).getFirstChild()!=null){
		    		 sID = inputDoc.getElementsByTagNameNS("*","ID").item(0).getFirstChild().getNodeValue();
				 }
				 commonTO.setLongRunningQueryStatus("result");
				 String sStatus=LongRunningQueryIdHolders.getInstance().getProperty(sID);
					if(sStatus==null || sStatus.trim().equals(""))
					  sStatus=HsqlDbUtils.getInstance().getStatusFromHsqlDB(sID);
					String contextPath=CommonUtils.getUrl(req,sID);
					//Setting file TO
					fileTO.setRandomUUIDString(sID);
					fileTO.setStatus(sStatus);
					fileTO.setsUrl(contextPath);
					String xmlString=CommonUtils.createXmlForWebService(fileTO);
					System.out.println(" : XML String : "+xmlString);	
					//Setting piped reader 
					commonTO.setLongRunningPrintWriter(pw);
					//Set data to print writer.
					commonTO.setDataXml(xmlString);
					//Thread created to load data into response.
					LongRunningQueryDao longRunningQueryDao= CommonDaoFactory.getInstance().getLongRunningQueryDao();
					longRunningQueryDao.generatelongRunningQueryXML(commonTO);		
					//Long running query response result.
			 	}else if(interfaceName == "GetResponseFile".intern()){
					 //Presently not in use.
					 StringBuilder fileData=null;
					 String sID =null;
					 if(inputDoc.getElementsByTagNameNS("*","ID").getLength()>0 && inputDoc.getElementsByTagNameNS("*","ID").item(0).getFirstChild()!=null){
			    		 sID = inputDoc.getElementsByTagNameNS("*","ID").item(0).getFirstChild().getNodeValue();
					 }
					 String sUrl=HsqlDbUtils.getInstance().getUrlFromHsqlDB(sID);
						if(sUrl.contains("ftp")){
							String ftpUrl=HsqlDbUtils.getInstance().getUrlFromHsqlDB(sID);
							fileData=FileUtils.getFileDataFromFtp(ftpUrl);
						}else if(sUrl.startsWith("http")) {
						
						}
						else{
							File xmlfile = new File(sUrl);
					        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					        DocumentBuilder builder = factory.newDocumentBuilder();
					        Document document = builder.parse(xmlfile);
					        fileData=FileUtils.readDataFromFile(document);
						}
						pw.write(fileData.toString());
			    }
		  //Non long running query
		  if(interfaceName == "Query".intern() || interfaceName == "TimeQuery".intern()){ 
				 //Web service 
				 commonTO.setStatus("webservice");
				 //Setting buffered printer
				 commonTO.setBufferOutput(new BufferedWriter(pw) );
				 //Setting piped reader 
				 commonTO.setPrintWriter(pw);
		    	 //Thread created to load data into PipeReader.
				 new VotableThreadAnalizer(commonTO).start();				
				 System.out.println(" : Done VOTABLE : ");				
		  }
	     //else
		  // response reader.  
	   responseReader= new StreamSource(pr);
	   
		}catch (Exception e) {
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
			}catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		return responseReader;
	
	}
	
	/*
	 * Method used to convert Source to dom object.
	 */
	private synchronized Element toDocument(Source src) throws TransformerException {
        DOMResult result = new DOMResult();
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        try {
            transformer.transform(src, result);
        } catch (TransformerException te) {
            throw new TransformerException("Error while applying template", te);
        }
        Element root = ((Document)result.getNode()).getDocumentElement();
       return root;
    }
}