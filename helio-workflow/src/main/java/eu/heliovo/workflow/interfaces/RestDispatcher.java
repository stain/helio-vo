package eu.heliovo.workflow.interfaces;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet gets the REST calls and calls the appropriate workflow
 *
 * @author simon felix at fhnw ch
 */
public class RestDispatcher extends HttpServlet
{
  private static final long serialVersionUID=1L;

  protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
  {
    response.setContentType("text/xml;charset=UTF-8");
    PrintWriter pw=response.getWriter();
    try
    {
      //convert parameters to parameter map
      Map<String,String> params=new LinkedHashMap<String,String>();
      for(Object s:request.getParameterMap().keySet())
        params.put((String)s,request.getParameter((String)s));
      
      //call the workflow
      WorkflowDispatcher.runWorkflow(pw,params);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      if(pw!=null)
        pw.close();
    }
  }
}
