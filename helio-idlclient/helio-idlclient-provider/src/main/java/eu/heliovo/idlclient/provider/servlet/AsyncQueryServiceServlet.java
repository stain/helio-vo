package eu.heliovo.idlclient.provider.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eu.heliovo.clientapi.query.HelioQueryResult;
import eu.heliovo.clientapi.query.longrunningquery.AsyncQueryService;
import eu.heliovo.clientapi.query.longrunningquery.impl.LongRunningQueryServiceFactory;
import eu.heliovo.clientapi.registry.impl.LongRunningServiceDescriptor;
import eu.heliovo.clientapi.workerservice.JobExecutionException;
import eu.heliovo.idlclient.provider.serialize.IdlConverter;

/**
 * AsyncQueryServiceServlet for IDL Clients.
 * Accept a query from IDL and pass it to the HELIO query.
 * Result is serialized for IDL and passed to IDL client.
 */
public class AsyncQueryServiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AsyncQueryServiceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
		PrintWriter writer = response.getWriter();

		String startTime = request.getParameter("starttime");
		String endTime = request.getParameter("endtime");
		String from = request.getParameter("from");
		String service = request.getParameter("service");
		String where = null;
		
		IdlHelioQueryResult idlresult = new IdlHelioQueryResult();
		
		//Check if all arguments exist
		if(startTime != null && endTime != null && from != null && service != null)
		{
			//Split arguments to arrays
			String[] startTimeArray = startTime.split(",");
			String[] endTimeArray = endTime.split(",");
			String[] fromArray = from.split(",");
			
			//Check date format
			SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss" );
			try {
				for(int i=0; i < startTimeArray.length; ++i) df.parse(startTimeArray[i]);
				for(int i=0; i < endTimeArray.length; ++i) df.parse(endTimeArray[i]);
			} catch (ParseException e) {
				RuntimeException e1 = new RuntimeException("Wrong date format. Date must be in ISO-8601 standard", e);
				String out = IdlConverter.idlserialize(e1);
				writer.append(out);
				return;
				//throw e1;
			}
			
			LongRunningServiceDescriptor lrsd;
			try{
			lrsd =
				LongRunningServiceDescriptor.valueOf("ASYNC_" + service.toUpperCase());
			}catch (IllegalArgumentException e) {
				RuntimeException e1 = new RuntimeException("Error, unknown service spezified");
				String out = IdlConverter.idlserialize(e1);
				writer.append(out);
				return;
				//throw e1;
			}catch (NullPointerException e){
				RuntimeException e1 = new RuntimeException("Error, no service spezified");
				String out = IdlConverter.idlserialize(e1);
				writer.append(out);
				return;
				//throw e1;
			}
			
			LongRunningQueryServiceFactory queryServiceFactory = LongRunningQueryServiceFactory.getInstance();
			AsyncQueryService queryService = queryServiceFactory.getLongRunningQueryService(lrsd);
			HelioQueryResult result;
			
			try{
				result = queryService.query(Arrays.asList(startTimeArray), Arrays.asList(endTimeArray), Arrays.asList(fromArray), where, 100, 0, null);
			}catch (JobExecutionException e){
				RuntimeException e1 = new RuntimeException("Error, request timeout");
				String out = IdlConverter.idlserialize(e1);
				writer.append(out);
				return;
				//throw e1;
			}catch (IllegalArgumentException e){
				RuntimeException e1 = new RuntimeException("Error, wrong argument");
				String out = IdlConverter.idlserialize(e1);
				writer.append(out);
				return;
				//throw e1;
			}
			
			if(result != null)
			{
				idlresult.setUrl(result.asURL().toString());
				idlresult.setLog(result.getUserLogs());
				
				String out = IdlConverter.idlserialize(idlresult);
				writer.append(out);
			}
		}
		else
		{
			RuntimeException e1 = new RuntimeException("Error, missing arguments");
			String out = IdlConverter.idlserialize(e1);
			writer.append(out);
			return;
			//throw e1;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
