package eu.heliovo.workflow.interfaces;

import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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
 * The dispatcher handles all soap requests and responses for a Query. Called
 * via the SoapServlet. SoapRequests (Bodies) are placed into a DOM and by analyzing
 * the uri determine the correct query service for the correct contract. Responses
 * are Stream based (NOT DOM) into an XMLStreamReader with the help of PipedStreams.
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
      
      //parse request
      Element inputDoc=toDocument(request);
      
      NodeList nl;
      
      //parse parameters
      nl=inputDoc.getElementsByTagName("GOES_MIN");
      final String goes_min;
      if(nl.getLength()>0)
        goes_min=nl.item(0).getFirstChild().getNodeValue();
      else
        goes_min="";
      
      nl=inputDoc.getElementsByTagName("GOES_MAX");
      final String goes_max;
      if(nl.getLength()>0)
        goes_max=nl.item(0).getFirstChild().getNodeValue();
      else
        goes_max="";
      
      nl=inputDoc.getElementsByTagName("STARTTIME");
      final String date_start;
      if(nl.getLength()>0)
        date_start=nl.item(0).getFirstChild().getNodeValue();
      else
        date_start="";
      
      nl=inputDoc.getElementsByTagName("ENDTIME");
      final String date_end;
      if(nl.getLength()>0)
        date_end=nl.item(0).getFirstChild().getNodeValue();
      else
        date_end="";
      
      nl=inputDoc.getElementsByTagName("INSTRUMENT");
      final List<String> instruments;
      if(nl.getLength()>0)
        instruments=Arrays.asList(nl.item(0).getFirstChild().getNodeValue().split(","));
      else
        instruments=new LinkedList<String>();
      
      //execute workflow
      new Thread(new Runnable()
      {
        public void run()
        {
          try
          {
            InitialWorkflow.runInitialWorkflow(pw,instruments,date_start,date_end,goes_min,goes_max);
          }
          catch(Exception e)
          {
            e.printStackTrace();
          }
        }
      }).start();
      
      //return results async
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