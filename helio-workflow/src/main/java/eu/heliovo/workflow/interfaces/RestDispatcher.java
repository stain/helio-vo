package eu.heliovo.workflow.interfaces;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eu.heliovo.workflow.workflows.InitialWorkflow;

/**
 * Servlet implementation class HelioQueryService
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
      String goes_min=request.getParameter("goes_min");
      String goes_max=request.getParameter("goes_max");
      String date_start=request.getParameter("date_start");
      String date_end=request.getParameter("date_end");
      String[] instruments=request.getParameter("instruments").split(",");
      
      InitialWorkflow.runWorkflow(pw,instruments,date_start,date_end,goes_min,goes_max);
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
