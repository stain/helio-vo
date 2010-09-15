package  eu.heliovo.dpas.ie.controller;

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

import eu.heliovo.dpas.ie.common.DAOFactory;
import eu.heliovo.dpas.ie.services.vso.dao.interfaces.VsoQueryDao;
import eu.heliovo.dpas.ie.services.vso.transfer.VsoDataTO;
import eu.heliovo.dpas.ie.services.vso.utils.VsoUtils;


/**
 * Class: SoapDispatcher
 * Description: The dispatcher handles all soap requests and responses for a Query.  Called via the
 * SoapServlet. SoapRequests (Bodies) are placed into a DOM and by analyzing the uri 
 * determine the correct query service for the correct contract.  Responses are Stream based (NOT DOM) into an 
 * XMLStreamReader with the help of PipedStreams.
 *
 */
@WebServiceProvider(targetNamespace="http://controller.dpas.helio.i4ds.ie",
	      serviceName="QueryServiceService",
	      portName="QueryService")

// OLD:	      @WebServiceProvider(targetNamespace="http://controller.dpas.helio.i4ds.ie",
//	      serviceName="QueryServiceService",
//	      portName="QueryService")

	      
	      
@ServiceMode(value=javax.xml.ws.Service.Mode.PAYLOAD)

public class DPASSoapDispatcher implements Provider<Source> {
	@javax.annotation.Resource(type=Object.class)
	 protected WebServiceContext wsContext; 

  /**
   * Method: Constructor
   * Description: Setup any initiations, mainly hashtable of uri to query 
   * interface versions.
   */
  public DPASSoapDispatcher() {
	 
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
		MessageContext mc = wsContext.getMessageContext();
		HttpServletRequest req = (HttpServletRequest)mc.get(MessageContext.SERVLET_REQUEST);
		PipedReader pr=null;
		PipedWriter pw=null;
		try {
			 Element inputDoc=toDocument(request);
		     String interfaceName = inputDoc.getLocalName().intern();
		     QueryService queryService=new QueryService();
		     String[] startTime =null;
		     String[] stopTime =null;
		     String[] instruments =null;
		     boolean votable=false;
		     if(interfaceName == "VoTableQueryResponse".intern()) {
		    	 votable=true;
		     }else if(interfaceName == "StringQueryResponse".intern()) {
		    	 
		     }
		     
		     if(inputDoc.getElementsByTagNameNS("*","starttime").getLength()>0 &&  inputDoc.getElementsByTagNameNS("*","starttime").item(0).getFirstChild()!=null){
		    	 NodeList nodeList=inputDoc.getElementsByTagNameNS("*","starttime");
		    	 startTime=new String[nodeList.getLength()];
		    	 for(int i=0;i<nodeList.getLength();i++){
		    		 startTime[i]=nodeList.item(0).getFirstChild().getNodeValue();
		    	 }
			 }
    		 
		     
		     if(inputDoc.getElementsByTagNameNS("*","stoptime").getLength()>0 && inputDoc.getElementsByTagNameNS("*","stoptime").item(0).getFirstChild()!=null){
		    	 NodeList nodeList=inputDoc.getElementsByTagNameNS("*","stoptime");
		    	 stopTime=new String[nodeList.getLength()];
		    	 for(int i=0;i<nodeList.getLength();i++){
		    		 stopTime[i]=nodeList.item(0).getFirstChild().getNodeValue();
		    	 }
			 }
		    
		     
		     if(inputDoc.getElementsByTagNameNS("*","instruments").getLength()>0 && inputDoc.getElementsByTagNameNS("*","instruments").item(0).getFirstChild()!=null){
		    	 NodeList nodeList=inputDoc.getElementsByTagNameNS("*","instruments");
		    	 instruments=new String[nodeList.getLength()];
		    	 for(int i=0;i<nodeList.getLength();i++){
		    		 instruments[i]=nodeList.item(0).getFirstChild().getNodeValue();
		    	 }
			 }
		     
		     //
		     pr = new PipedReader();
			 pw = new PipedWriter(pr);
		     VsoDataTO vsoTO=new VsoDataTO();
		     vsoTO.setUrl(VsoUtils.getUrl(req));
		     vsoTO.setInstrument(instruments[0]);
		     vsoTO.setDateTo(startTime[0]);
		     vsoTO.setDateFrom(stopTime[0]);
		     vsoTO.setOutput(pw);
		     vsoTO.setWhichProvider("VSO");
		     vsoTO.setStatus("webservice");
		     //responseReader= queryService.sortedQuery(instruments, startTime, stopTime, false, null, null,votable);
		     //Calling DAO factory to connect VSO
		     VsoQueryDao vsoQueryDao= (VsoQueryDao) DAOFactory.getDAOFactory(vsoTO.getWhichProvider());
	         vsoQueryDao.query(vsoTO);
		     
		     responseReader= new StreamSource(pr); 
		     
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