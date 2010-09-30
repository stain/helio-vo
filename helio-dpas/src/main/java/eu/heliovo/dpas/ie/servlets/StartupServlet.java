/* #ident	"%W%" */
package eu.heliovo.dpas.ie.servlets;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import eu.heliovo.dpas.ie.services.common.utils.HsqlDbUtils;
import eu.heliovo.dpas.ie.services.common.utils.InstanceHolders;

public class StartupServlet extends HttpServlet {
	 
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try{
			 // getting the path.
			System.out.println("---> getting HSQL database path -------->");
			 String sProfileFilePath=getServletContext().getRealPath("/");
			 if(sProfileFilePath!=null && !sProfileFilePath.equals("")){
				 	sProfileFilePath=sProfileFilePath+ "WEB-INF";
					InstanceHolders.getInstance().setProperty("hsqldb.database.path",sProfileFilePath);
					System.out.println(" : HSQLDB database location : "+sProfileFilePath);
			 }
			System.out.println("---> done database path configuration-------->");
			System.out.println("---> Setting PAT table -------->");
			//Setting .txt for 'pat' table.
			HsqlDbUtils.getInstance().loadProviderAccessTable("pat.txt");	
			InstanceHolders.getInstance().setProperty("patFileName","pat.txt");
			System.out.println("---> Done configuration of PAT table -------->");
		}
		catch(Exception ex)
		{
			System.out.println("Exception: "+ex);
			ex.printStackTrace();
		}
	}
	

}
