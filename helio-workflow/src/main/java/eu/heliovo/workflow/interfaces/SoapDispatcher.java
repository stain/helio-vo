package eu.heliovo.workflow.interfaces;

import java.io.PipedReader;
import java.io.PipedWriter;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Provider;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.WebServiceProvider;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;

import eu.heliovo.workflow.workflows.InitialWorkflow;

/**
 * Class: SoapDispatcher Description: The dispatcher handles all soap requests
 * and responses for a Query. Called via the SoapServlet. SoapRequests (Bodies)
 * are placed into a DOM and by analyzing the uri determine the correct query
 * service for the correct contract. Responses are Stream based (NOT DOM) into
 * an XMLStreamReader with the help of PipedStreams.
 */
@WebServiceProvider(targetNamespace="http://helio-vo.eu/xml/QueryService/v0.1",serviceName="HelioQueryServiceService",portName="HelioQueryServicePort")
@ServiceMode(value=javax.xml.ws.Service.Mode.PAYLOAD)
public class SoapDispatcher implements Provider<Source>
{
  protected final Logger logger=Logger.getLogger(this.getClass());

  public SoapDispatcher()
  {
  }

  /**
   * Method: invoke Description:For all soap requests and responses. Using Metro.
   * 
   * @param request
   *          - Source that is used to extract the soap request.
   * @return Source - response StreamSource that contains the soap response
   *         populated by InputStream (PipedInputStream)
   */
  @Override
  public Source invoke(Source request)
  {
    PipedReader pr=new PipedReader();
    final PipedWriter pw;
    try
    {
      pw=new PipedWriter(pr);
      
      Element inputDoc=toDocument(request);
      
      NodeList nl;
      
      nl=inputDoc.getElementsByTagName("goes_min");
      final String goes_min;
      if(nl.getLength()>0)
        goes_min=nl.item(0).getFirstChild().getNodeValue();
      else
        goes_min="";
      
      nl=inputDoc.getElementsByTagName("goes_max");
      final String goes_max;
      if(nl.getLength()>0)
        goes_max=nl.item(0).getFirstChild().getNodeValue();
      else
        goes_max="";
      
      nl=inputDoc.getElementsByTagName("date_start");
      final String date_start;
      if(nl.getLength()>0)
        date_start=nl.item(0).getFirstChild().getNodeValue();
      else
        date_start="";
      
      nl=inputDoc.getElementsByTagName("date_end");
      final String date_end;
      if(nl.getLength()>0)
        date_end=nl.item(0).getFirstChild().getNodeValue();
      else
        date_end="";
      
      nl=inputDoc.getElementsByTagName("instruments");
      final String[] instruments;
      if(nl.getLength()>0)
        instruments=nl.item(0).getFirstChild().getNodeValue().split(",");
      else
        instruments=new String[0];
      
      new Thread(new Runnable()
      {
        public void run()
        {
          InitialWorkflow.runWorkflow(pw,instruments,date_start,date_end,goes_min,goes_max);
        }
      }).start();
      return new StreamSource(pr);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }

  /*
   * Method used to convert Source to dom object.
   */
  private synchronized Element toDocument(Source src) throws TransformerException
  {
    DOMResult result=new DOMResult();
    try
    {
      TransformerFactory.newInstance().newTransformer().transform(src,result);
    }
    catch(TransformerException te)
    {
      throw new TransformerException("Error while applying template",te);
    }
    return ((Document)result.getNode()).getDocumentElement();
  }
}