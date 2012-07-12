package eu.heliovo.idlclient.provider.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.logging.LogRecord;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.log4j.Logger;
import org.springframework.context.support.GenericXmlApplicationContext;

import eu.heliovo.clientapi.HelioClient;
import eu.heliovo.clientapi.query.HelioQueryResult;
import eu.heliovo.clientapi.query.syncquery.SyncQueryService;
import eu.heliovo.idlclient.model.IdlHelioQueryResult;
import eu.heliovo.idlclient.model.IdlLogRecord;
import eu.heliovo.idlclient.provider.serialize.IdlObjConverter;
import eu.heliovo.registryclient.HelioServiceName;
import eu.heliovo.registryclient.ServiceCapability;
import eu.heliovo.shared.util.AssertUtil;

/**
 * AsyncQueryServiceServlet for IDL Clients.
 * Accept a query from IDL and pass it to the HELIO query.
 * Result is serialized for IDL and passed to IDL client.
 */
public class AsyncQueryServiceServlet extends HttpServlet {
    /**
     * Happy little logger
     */
    private static final Logger _LOGGER = Logger.getLogger(AsyncQueryServiceServlet.class);
    
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AsyncQueryServiceServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//IdlConverter idl = IdlConverter.getInstance();
		IdlObjConverter idl = IdlObjConverter.getInstance();
		idl.registerSerialisationHandler(HelioQueryResult.class, IdlHelioQueryResult.class);
		idl.registerSerialisationHandler(LogRecord.class, IdlLogRecord.class);
		
		try {
    		String service = request.getParameter("service");
    		String from = request.getParameter("from");
    		String startTime = request.getParameter("starttime");
    		String endTime = request.getParameter("endtime");
    		String where = request.getParameter("where"); // the where clause, may be null
    		
    		AssertUtil.assertArgumentHasText(service, "service");
    		AssertUtil.assertArgumentHasText(from, "from");
    		AssertUtil.assertArgumentHasText(startTime, "startTime");
    		AssertUtil.assertArgumentHasText(endTime, "endTime");
    		    		
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
			    throw new RuntimeException("Wrong date format. Date must be in ISO-8601 standard", e);
			}

	        GenericXmlApplicationContext context = new GenericXmlApplicationContext("classpath:spring/clientapi-main.xml");
	        HelioClient helioClient = (HelioClient) context.getBean("helioClient");
	        SyncQueryService queryService = (SyncQueryService)helioClient.getServiceInstance(HelioServiceName.valueOf(service.toUpperCase()), null, ServiceCapability.SYNC_QUERY_SERVICE);
			
			if (queryService == null) {
			    throw new RuntimeException("Unable to find service with name " + service.toUpperCase());
			}
			HelioQueryResult result;
			
			result = queryService.query(Arrays.asList(startTimeArray), Arrays.asList(endTimeArray), Arrays.asList(fromArray), where, 100, 0, null);
			if(result != null)
			{
				String out = idl.idlSerialize(result);

				PrintWriter writer = response.getWriter();
		        response.setContentType("text/plain");
		        response.setContentLength(out.length());
				writer.append(out);
			}
		} catch (Exception e) {
		    _LOGGER.warn("Exception while processing request: " + e.getMessage(), e);
		    String out = idl.idlSerialize(e);
            //response.sendError(200, out);
		    PrintWriter writer = response.getWriter();
	        response.setContentType("text/plain");
	        response.setContentLength(out.length());
			writer.append(out);
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
