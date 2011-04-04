package eu.heliovo.idlclient.provider.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.LogRecord;

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
 * Servlet implementation class AsyncQueryServiceServlet
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
		
		IdlHelioResult idlresult = new IdlHelioResult();
		
		if(startTime != null && endTime != null && from != null)
		{
			SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss" );
			try {
				df.parse(startTime);
				df.parse(endTime);
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
			
			//writer.append(startTime + " - " + endTime + " : " + from);
			LongRunningQueryServiceFactory queryServiceFactory = LongRunningQueryServiceFactory.getInstance();
			AsyncQueryService queryService = queryServiceFactory.getLongRunningQueryService(LongRunningServiceDescriptor.ASYNC_ILS);
			HelioQueryResult result = queryService.timeQuery(startTime, endTime, from, 100, 0, null);
			
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
