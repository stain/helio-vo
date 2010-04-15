package eu.heliovo.workflow.workflows;

import java.io.IOException;
import java.io.Writer;

/**
 * This class provides helper methods that can be used by all workflow implementations.
 */
public class Workflow
{
  static void writeHeader(Writer _w,String _description) throws IOException
  {
    _w.write("<helio:queryResponse xmlns:helio=\"http://helio-vo.eu/xml/QueryService/v0.1\">");
    _w.write("<VOTABLE version='1.1' xmlns=\"http://www.ivoa.net/xml/VOTable/v1.1\">\n");
    _w.write("<RESOURCE>\n");
    _w.write("<DESCRIPTION>"+_description+"</DESCRIPTION>\n");
  }
  
  static void writeFooter(Writer _w,boolean _closeStream) throws IOException
  {
    _w.write("</RESOURCE>\n");
    _w.write("</VOTABLE>\n");
    _w.write("</helio:queryResponse>");
    
    if(_closeStream)
    {
      _w.flush();
      _w.close();
    }
  }
}
