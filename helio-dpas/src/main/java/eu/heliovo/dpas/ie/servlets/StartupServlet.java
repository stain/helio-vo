/* #ident	"%W%" */
package eu.heliovo.dpas.ie.servlets;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import eu.heliovo.dpas.ie.services.common.utils.InstanceHolders;

public class StartupServlet extends HttpServlet {
	 
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try{
			 // getting the path.
			 String sProfileFilePath=getServletContext().getRealPath("/");
			 if(sProfileFilePath!=null && !sProfileFilePath.equals("")){
				 	sProfileFilePath=sProfileFilePath+ "WEB-INF";
					InstanceHolders.getInstance().setProperty("hsqldb.database.path",sProfileFilePath);
					System.out.println(" : HSQLDB database location : "+sProfileFilePath);
			 }
		}
		catch(Exception ex)
		{
			System.out.println("Exception: "+ex);
			ex.printStackTrace();
		}
	}
	

}
