package eu.heliovo.dpas.ie.controller;

import java.io.PipedReader;
import java.io.PipedWriter;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.transform.stream.StreamSource;
import eu.heliovo.dpas.ie.common.CommonTO;
import eu.heliovo.dpas.ie.common.QueryThreadAnalizer;


/**
 * The Class QueryService.
 * 
 * @author Gabriele Pierantoni (pierang@cs.tcd.ie)
 * 
 */
@WebService
public class QueryService
{
	
	
	public QueryService() 
	{
		
	}



	/**
	 * Sorted query.
	 *
	 * All arrays must have the same length, otherwise an exception is raised.
	 * 
	 * @param instruments the instruments of the query
	 * @param startTimes the start times of the query
	 * @param stopTimes The stop times of the query
	 * @param partialSorting Whether the sorting should be partial or global
	 * @param dataTypes The type of the data 
	 * @param dataLevels The type of level of the data
	 * @return the result of the query as a String
	 */
	@WebMethod
	public StreamSource sortedQuery(String[] instruments, 
			String[] startTimes,
			String[] stopTimes, 
			boolean partialSorting, 
			String[] dataTypes, 
			int[] dataLevels,boolean votable) throws Exception
	{
	
		
		PipedReader pr = new PipedReader();
		PipedWriter pw = new PipedWriter(pr);
		
		CommonTO commonTO=new CommonTO();
		commonTO.setInstruments(instruments);
		commonTO.setPartialSorting(partialSorting);
		commonTO.setDataTypes(dataTypes);
		commonTO.setStopTimes(stopTimes);
		commonTO.setDataLevels(dataLevels);
		commonTO.setStartTimes(startTimes);
		commonTO.setPrintWriter(pw);
		commonTO.setVotable(votable);
		new QueryThreadAnalizer(commonTO).start();
		System.out.println("Done Votable");
		return 	new StreamSource(pr);
	}
	
	
	/*
	@WebMethod
	public String sortedQueryString(String[] instruments, 
			String[] startTimes,
			String[] stopTimes, 
			boolean partialSorting, 
			String[] dataTypes, 
			int[] dataLevels,boolean votable) throws Exception
	{
		//PipedReader pr = new PipedReader();
		//PipedWriter pw = new PipedWriter(pr);
		ServiceEngine serviceEngine = new ServiceEngine();
		return serviceEngine.executeQuery(null,instruments, 
				startTimes, 
				stopTimes, 
				partialSorting,
				dataTypes, 
				dataLevels,votable);	
	}
	*/
}
