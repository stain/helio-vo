/* #ident	"%W%" */
package eu.heliovo.dpas.ie.servlets;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import eu.heliovo.dpas.ie.services.common.dao.exception.DetailsNotFoundException;
import eu.heliovo.dpas.ie.services.common.utils.CommonUtils;
import eu.heliovo.dpas.ie.services.common.utils.HsqlDbUtils;
import eu.heliovo.dpas.ie.services.common.utils.InstanceHolders;
import eu.heliovo.dpas.ie.services.common.utils.DeleteStatusTimer;

public class StartupServlet extends HttpServlet {
	 
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try{
			//Getting the path.
			System.out.println("---> getting HSQL database path -------->");
			String sProfileFilePath=getServletContext().getRealPath("/");
			if(sProfileFilePath!=null && !sProfileFilePath.equals("")){
				 	sProfileFilePath=sProfileFilePath+ "WEB-INF";
					InstanceHolders.getInstance().setProperty("hsqldb.database.path",sProfileFilePath);
					System.out.println(" : HSQLDB database location : "+sProfileFilePath);
			}
			//
			InstanceHolders.getInstance().setProperty("hsql.status.delete.date",CommonUtils.date2String(new Date()));
			System.out.println("---> done database path configuration-------->");
			System.out.println("---> Setting PAT table -------->");
			//Setting .txt for 'pat' table.
			//HsqlDbUtils.getInstance().loadProviderAccessTable("pat.txt","pat");	
			// Pat File Name
			InstanceHolders.getInstance().setProperty("patFileName","pat.txt");
			//
			//Setting .txt for 'pat' table.
			//HsqlDbUtils.getInstance().loadProviderAccessTable("ftppat.txt","ftppat");
			//Ftp Pat File Name
			InstanceHolders.getInstance().setProperty("patFtpFileName","ftppat.txt");
			System.out.println("---> Done configuration of PAT table -------->");
			//
			TimerTask task = new DeleteStatusTimer() {
				@Override
				 protected void execute(){
			      	try{			      		
			      		int noOfMonth=CommonUtils.getNoOfMonths(InstanceHolders.getInstance().getProperty("hsql.status.delete.date"));
			      		System.out.println(" : Differnce in month : "+noOfMonth );
			      		if(noOfMonth>=6){
			      			deleteLongRunningQueryStatus();
			      			InstanceHolders.getInstance().setProperty("hsql.status.delete.date",CommonUtils.date2String(new Date()));
			      		}
				    }catch (Exception e){
				    	System.out.println(" :  Exception occured in while deleting Long running query saved status and url : "+ e);
			      	}
			    }
			};

			Timer timer = new Timer();
			//Check every 2 day 
			timer.schedule(task , new Date(), 86400000*2); // refresh after 86400000 milliseconds  = 1 day
		}
		catch(Exception ex)
		{
			System.out.println(": Exception : "+ex);
			ex.printStackTrace();
		}
	}
	

	private void deleteLongRunningQueryStatus() throws DetailsNotFoundException{
		System.out.println(" : Deleting Long Running Query : ");
		//Deleting Saved File
		HsqlDbUtils.getInstance().deleteSavedVoTable();
		//Deleting Status from JOB_STATUS
		HsqlDbUtils.getInstance().deleteStatusFromHsqlDB();
		//Deleting Status from JOB_URL
		HsqlDbUtils.getInstance().deleteUrlFromHsqlDB();
	}
	
}
