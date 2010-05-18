package  eu.heliovo.dpas.ie.controller;

import java.io.BufferedWriter;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Provider;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.WebServiceProvider;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


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
  
	@Override
	public Source invoke(Source request) {
		StreamSource responseReader = null;
		BufferedWriter bw =null;
		
		try {
			 Element inputDoc=toDocument(request);
		     String interfaceName = inputDoc.getLocalName().intern();
		     QueryService queryService=new QueryService();
		     String[] startTime =null;
		     String[] stopTime =null;
		     String[] instruments =null;
		     boolean votable=true;
		     
		      if(inputDoc.getElementsByTagNameNS("*","STARTTIME").getLength()>0){
				 startTime=new String[1];
				 startTime[0] = inputDoc.getElementsByTagNameNS("*","STARTTIME").item(0).getFirstChild().getNodeValue();
			 }
    		 
		     
		     
	    		 if(inputDoc.getElementsByTagNameNS("*","ENDTIME").getLength()>0){
	    			 stopTime=new String[1];
	    			 stopTime[0] = inputDoc.getElementsByTagNameNS("*","ENDTIME").item(0).getFirstChild().getNodeValue();
	    	 }
		    
		     
		     if(inputDoc.getElementsByTagNameNS("*","FROM").getLength()>0){
				 instruments=new String[1];
				 instruments[0] = inputDoc.getElementsByTagNameNS("*","FROM").item(0).getFirstChild().getNodeValue();
				 votable=true;
				 
			 }
		     
		     
		   //Setting for Start Row parameter.
			 if(inputDoc.getElementsByTagNameNS("*","STARTINDEX").getLength()>0){
				 String startRow = inputDoc.getElementsByTagNameNS("*","STARTINDEX").item(0).getFirstChild().getNodeValue();
				 
			 }
			 
			//Setting for No Of Rows parameter.
			 if(inputDoc.getElementsByTagNameNS("*","MAXRECORDS").getLength()>0){
				 String noOfRows = inputDoc.getElementsByTagNameNS("*","MAXRECORDS").item(0).getFirstChild().getNodeValue();
				
			 }
		     
			 if(inputDoc.getElementsByTagNameNS("*","INSTRUMENT").getLength()>0){
				 String inst = inputDoc.getElementsByTagNameNS("*","INSTRUMENT").item(0).getFirstChild().getNodeValue();
				
			 }
			//Setting for WHERE parameter.
			 if(inputDoc.getElementsByTagNameNS("*","WHERE").getLength()>0){
				 String whereClause = inputDoc.getElementsByTagNameNS("*","WHERE").item(0).getFirstChild().getNodeValue();
				
			 }
			 
		     responseReader= queryService.sortedQuery(instruments, startTime, stopTime, false, null, null,votable);
		     
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
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