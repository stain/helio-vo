package  eu.heliovo.dpas.ie.services.common.server;

import java.io.BufferedWriter;
import java.io.PipedReader;
import java.io.PipedWriter;
import javax.servlet.http.HttpServletRequest;
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
import eu.heliovo.dpas.ie.services.common.transfer.CommonTO;
import eu.heliovo.dpas.ie.services.common.utils.VOTableCreator;
import eu.heliovo.dpas.ie.services.common.utils.VotableThreadAnalizer;
import eu.heliovo.dpas.ie.services.vso.utils.VsoUtils;


/**
 * Class: SoapDispatcher
 * Description: The dispatcher handles all soap requests and responses for a Query.  Called via the
 * SoapServlet. SoapRequests (Bodies) are placed into a DOM and by analyzing the uri 
 * determine the correct query service for the correct contract.  Responses are Stream based (NOT DOM) into an 
 * XMLStreamReader with the help of PipedStreams.
 *
 */
@WebServiceProvider(targetNamespace="http://helio-vo.eu/xml/QueryService/v0.1",
	      serviceName="HelioQueryServiceService",
	      portName="HelioQueryServicePort")     
	      
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
		BufferedWriter bw =null;
		MessageContext mc = wsContext.getMessageContext();
		HttpServletRequest req = (HttpServletRequest)mc.get(MessageContext.SERVLET_REQUEST);
		PipedReader pr=null;
		PipedWriter pw=null;
		CommonTO commonTO=new CommonTO();
		
		try {
			 Element inputDoc=toDocument(request);
		     String[] startTime =null;
		     String[] stopTime =null;
		     String[] instruments =null;
		     String[] from =null;
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
		    
		     
		     if(inputDoc.getElementsByTagNameNS("*","FROM").getLength()>0 && inputDoc.getElementsByTagNameNS("*","FROM").item(0).getFirstChild()!=null){
				 NodeList nodeList=inputDoc.getElementsByTagNameNS("*","FROM");
				 from=new String[nodeList.getLength()];
    			 //List Name
		    	 for(int i=0;i<nodeList.getLength();i++){
		    		 from[i]=nodeList.item(i).getFirstChild().getNodeValue();
		    	 }
				 
				 votable=true;
				 
			 }
		     
		     if(inputDoc.getElementsByTagNameNS("*","INSTRUMENT").getLength()>0 && inputDoc.getElementsByTagNameNS("*","INSTRUMENT").item(0).getFirstChild()!=null){
		    	 NodeList nodeList=inputDoc.getElementsByTagNameNS("*","INSTRUMENT");
				 instruments=new String[nodeList.getLength()];
    			 //List Name
		    	 for(int i=0;i<nodeList.getLength();i++){
		    		 instruments[i]=nodeList.item(i).getFirstChild().getNodeValue();
		    	 }
				 votable=true;
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
		     commonTO.setStatus("webservice");
		     commonTO.setUrl(VsoUtils.getUrl(req));
		     commonTO.setInstruments(instruments);
		     commonTO.setStartTimes(startTime);
		     commonTO.setStopTimes(stopTime);
		     //Loop to check
		     if(startTime!=null && startTime.length>0 && stopTime!=null && stopTime.length>0 && instruments!=null && instruments.length>0 && instruments.length==startTime.length && instruments.length==stopTime.length){
		    	//
		    	 new VotableThreadAnalizer(commonTO).start();
		     }else{
		    	 commonTO.setExceptionStatus("exception");
		    	 commonTO.setBufferOutput(new BufferedWriter(pw));
		    	 commonTO.setVotableDescription("VSO query response");
		    	 commonTO.setQuerystatus("ERROR");
		    	 commonTO.setQuerydescription("Start Time,EndTime and Instruments cannot be null");
				 VOTableCreator.writeErrorTables(commonTO);
		     }
		     responseReader= new StreamSource(pr); 
		     
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println(" : Exception occured while creating the file :  "+e.getMessage());
			commonTO.setExceptionStatus("exception");
			commonTO.setBufferOutput(new BufferedWriter(pw));
			commonTO.setVotableDescription("Could not create VOTABLE, exception occured : "+e.getMessage());
			commonTO.setQuerystatus("ERROR");
			commonTO.setQuerydescription(e.getMessage());
			commonTO.setRequest(req);
			try {
				//Sending error messages
				VOTableCreator.writeErrorTables(commonTO);
			} catch (Exception e1) {
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