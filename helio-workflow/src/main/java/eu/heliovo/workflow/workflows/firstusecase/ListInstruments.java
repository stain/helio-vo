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
import org.apache.xml.serialize.*;
import eu.heliovo.workflow.clients.ics.HelioQueryServiceServiceTwo;
import eu.heliovo.workflow.clients.ics.Query;
import eu.heliovo.workflow.clients.ics.QueryResponse;
import eu.heliovo.workflow.workflows.Workflow;

/**
 * This class represents Anaj's initial workflow. This workflow can be downloaded from
 * http://www.myexperiment.org/workflows/1286 to run it locally in Taverna. It's a
 * straight-forward 1:1 conversion.
 */
public class ListInstruments extends Workflow
{
  public static void runWorkflow(Writer _w,List<String> _date_start,List<String> _date_end) throws Exception
  {
    writeHeader(_w);
    _w.write("<VOTABLE version='1.1' xmlns=\"http://www.ivoa.net/xml/VOTable/v1.1\">\n");
    
    
    //inputs
    String INSTRUMENT_value="instruments";
    
    //query_query
    
    //query
    ExecutorService tpe=Executors.newCachedThreadPool();
    List<Future<QueryResponse>> output_futures=new ArrayList<Future<QueryResponse>>();
    for(int i=0;i<_date_start.size();i++)
    {
      final Query q=new Query();
      q.setFROM(INSTRUMENT_value);
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
    
    _w.write("</VOTABLE>");
    writeFooter(_w,true);
  }
  
  private static String combineVOTables(List<String> tables_in)
  {
    String table_out;
    
    if(tables_in.size()<1) {
      return new String();
    }
    table_out = new String();
    int pos= tables_in.get(0).lastIndexOf("</DATA>")+7;
    table_out = table_out.concat(tables_in.get(0).substring(0,pos));

    for(int i=1; i<tables_in.size();i++) {
      String tab = tables_in.get(i);
      table_out = table_out.concat("\n"+tab.substring(tab.indexOf("<DATA>"), tab.lastIndexOf("</DATA>")+7));
    }
    table_out = table_out.concat(tables_in.get(0).substring(pos));
    
    return table_out;
  }
}
