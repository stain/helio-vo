/* #ident	"%W%" */
package eu.heliovo.dpas.ie.servlets;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import eu.heliovo.dpas.ie.services.common.utils.InstanceHolders;

public class StartupServlet extends HttpServlet {
	 
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {

		try{
			 ClassLoader loader = this.getClass().getClassLoader();
			 // check id test.txt available.
			 String sProfileFilePath=loader.getResource("struts.xml").getFile();
			 if(sProfileFilePath!=null && !sProfileFilePath.equals("")){
					InstanceHolders.getInstance().setProperty("hsqldb.database.path",sProfileFilePath.replaceAll("/struts.xml", ""));
					System.out.println(" : HSQLDB database location : "+sProfileFilePath.replaceAll("/struts.xml", ""));
			 }
			
		}
		catch(Exception ex)
		{
			System.out.println("Exception: "+ex);
			ex.printStackTrace();
		}
	}
	

}
