package eu.heliovo.workflow.workflows;

import java.io.IOException;
import java.io.Writer;

/**
 * This class provides helper methods that can be used by all workflow implementations.
 */
public class Workflow
{
  public static void writeHeader(Writer _w) throws IOException
  {
    _w.write("<helio:queryResponse xmlns:helio=\"http://helio-vo.eu/xml/QueryService/v0.1\">");
  }
  
  public static void writeFooter(Writer _w,boolean _closeStream) throws IOException
  {
    _w.write("</helio:queryResponse>");
    
    if(_closeStream)
    {
      _w.flush();
      _w.close();
    }
  }
}
