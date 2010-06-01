package eu.heliovo.workflow.workflows.firstusecase;

import java.io.*;
import org.apache.xml.serialize.*;
import org.w3c.dom.Element;
import eu.heliovo.workflow.clients.hec.QueryResponse;
import eu.heliovo.workflow.clients.hec.HelioQueryServiceServiceTwo;
import eu.heliovo.workflow.clients.hec.Query;
import eu.heliovo.workflow.workflows.Workflow;

/**
 * This class represents Anaj's initial workflow. This workflow can be downloaded from
 * http://www.myexperiment.org/workflows/1285 to run it locally in Taverna. It's a
 * straight-forward 1:1 conversion.
 */
public class GetEvents extends Workflow
{
  public static void runWorkflow(Writer _w,String _date_start,String _date_end) throws Exception
  {
    writeHeader(_w);
    //_w.write("<VOTABLE version='1.1' xmlns=\"http://www.ivoa.net/xml/VOTable/v1.1\">\n");
    
    
    //inputs
    String FROM_value="goes_xray_flare";
    
    //query_query
    Query q=new Query();
    q.setFROM(FROM_value);
    q.setSTARTTIME(_date_start);
    q.setENDTIME(_date_end);
    
    //query
    QueryResponse res=new HelioQueryServiceServiceTwo().getHelioQueryServicePortTwo().query(q);
    
    //outputs
    XMLSerializer ser=new XMLSerializer();
    OutputFormat of=new OutputFormat();
    of.setOmitXMLDeclaration(true);
    ser.setOutputFormat(of);
    ser.setOutputCharStream(_w);
    ser.serialize((Element)res.getAny());
    
    //_w.write("</VOTABLE>");
    writeFooter(_w,true);
  }
}
