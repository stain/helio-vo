package eu.heliovo.workflow.workflows.firstusecase;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import org.w3c.dom.*;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import eu.heliovo.workflow.clients.dpas.HelioQueryServiceServiceTwo;
import eu.heliovo.workflow.clients.dpas.Query;
import eu.heliovo.workflow.clients.dpas.QueryResponse;
import eu.heliovo.workflow.workflows.Workflow;

/**
 * This class represents Anja's initial workflow. This workflow can be downloaded from
 * http://www.myexperiment.org/workflows/1289 to run it locally in Taverna. It's a
 * straight-forward 1:1 conversion.
 * 
 * @author simon felix at fhnw ch
 */
public class GetData extends Workflow
{
  public static void runWorkflow(Writer _w,List<String> _instruments,List<String> _date_start,List<String> _date_end) throws Exception
  {
    writeHeader(_w);
    
    
    //inputs
    
    //create_DPAS_input_lists
    
    //query
    ExecutorService tpe=Executors.newCachedThreadPool();
    List<Future<QueryResponse>> output_futures=new ArrayList<Future<QueryResponse>>();
    for(int i=0;i<_date_start.size();i++)
      for(String instrument:_instruments)
      {
        final Query q=new Query();
        q.setFROM(instrument);
        q.setSTARTTIME(_date_start.get(i));
        q.setENDTIME(_date_end.get(i));
        
        FutureTask<QueryResponse> ft=new FutureTask<QueryResponse>(new Callable<QueryResponse>()
            {
              @Override
              public QueryResponse call() throws Exception
              {
                return new HelioQueryServiceServiceTwo().getHelioQueryServicePortTwo().query(q);
              }
            });
        tpe.execute(ft);
        output_futures.add(ft);
      }
    
    //combineVOTables
    List<String> tables=new LinkedList<String>();
    for(Future<QueryResponse> qr:output_futures)
    {
      StringWriter sw=new StringWriter();
      XMLSerializer ser=new XMLSerializer();
      OutputFormat of=new OutputFormat();
      of.setOmitXMLDeclaration(true);
      ser.setOutputFormat(of);
      ser.setOutputCharStream(sw);
      ser.serialize((Element)qr.get().getAny());
      tables.add(sw.toString());
    }
    String combineVOTables_out=combineVOTables(tables);
    
    
    //outputs
    _w.write(combineVOTables_out);
    
    writeFooter(_w,true);
  }
  
  private static String combineVOTables(List<String> tables_in)
  {
    String table_out;
    if(tables_in.size()<1) {
      return new String();
    }
    table_out = new String();
    int pos= tables_in.get(0).lastIndexOf("</RESOURCE>")+11;
    table_out = table_out.concat(tables_in.get(0).substring(0,pos));

    for(int i=1; i<tables_in.size();i++) {
      String tab = tables_in.get(i);
      table_out = table_out.concat("\n"+tab.substring(tab.indexOf("<RESOURCE>"), tab.lastIndexOf("</RESOURCE>")+11));
    }
    table_out = table_out.concat(tables_in.get(0).substring(pos));
    return table_out;
  }
}
