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
		
		IdlHelioQueryResult idlresult = new IdlHelioQueryResult();
		
		if(startTime != null && endTime != null && from != null && service != null)
		{
			SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss" );
			try {
				df.parse(startTime);
				df.parse(endTime);
			} catch (ParseException e) {
				throw new RuntimeException("Wrong date format. Date must be in ISO-8601 standard", e);
			}

			String[] startTimeArray = startTime.split(",");
			String[] endTimeArray = endTime.split(",");
			String[] fromArray = from.split(",");
			
			LongRunningServiceDescriptor lrsd;
			
			if(service.toUpperCase().compareTo("HEC") == 0)
				lrsd = LongRunningServiceDescriptor.ASYNC_HEC;
			else if(service.toUpperCase().compareTo("UOC") == 0)
				lrsd = LongRunningServiceDescriptor.ASYNC_UOC;
			else if(service.toUpperCase().compareTo("DPAS") == 0)
				lrsd = LongRunningServiceDescriptor.ASYNC_DPAS;
			else if(service.toUpperCase().compareTo("ICS") == 0)
				lrsd = LongRunningServiceDescriptor.ASYNC_ICS;
			else if(service.toUpperCase().compareTo("ILS") == 0)
				lrsd = LongRunningServiceDescriptor.ASYNC_ILS;
			else if(service.toUpperCase().compareTo("MDES") == 0)
				lrsd = LongRunningServiceDescriptor.ASYNC_MDES;
			else
				throw new RuntimeException("Error, unknown Service spezified");
			
			LongRunningQueryServiceFactory queryServiceFactory = LongRunningQueryServiceFactory.getInstance();
			AsyncQueryService queryService = queryServiceFactory.getLongRunningQueryService(lrsd);
			HelioQueryResult result;

			result = queryService.timeQuery(Arrays.asList(startTimeArray), Arrays.asList(endTimeArray), Arrays.asList(fromArray), 100, 0, null);
			
			if(result != null)
			{
				idlresult.setUrl(result.asURL().toString());
				idlresult.setLog(result.getUserLogs());
				
				String out = IdlConverter.idlserialize(idlresult);
				writer.append(out);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
