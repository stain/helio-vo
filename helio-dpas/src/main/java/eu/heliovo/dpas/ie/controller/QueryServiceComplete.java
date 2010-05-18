package eu.heliovo.dpas.ie.controller;

import java.io.Writer;

import javax.jws.WebMethod;
import javax.jws.WebService;

import eu.heliovo.dpas.ie.classad.ClassAdMapperException;
import eu.heliovo.dpas.ie.classad.ClassAdUtilitiesException;

/**
 * The Class QueryService.
 * 
 * @author Gabriele Pierantoni (pierang@cs.tcd.ie)
 * 
 */
@WebService
public class QueryServiceComplete
{
	/** The query engine. */
	ServiceEngine serviceEngine = null;
	
	
	public QueryServiceComplete() 
	{
		try {
			serviceEngine = new ServiceEngine();
		} catch (ArithmeticException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassAdMapperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassAdUtilitiesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


//	/**
//	 * Query.
//	 * 
//	 * @param instruments the instruments
//	 * @param startTimes the start times
//	 * @param stopTimes the stop times
//	 * @return the string
//	 */
//	@WebMethod
//	public String query(String[] instruments, 
//			String[] startTimes,
//			String[] stopTimes, 
//			String[] dataTypes, 
//			int[] dataLevels)
//	{
//		System.out.println("QueryEngine.executeQuery(...)");
//
//		return serviceEngine.executeQuery(instruments, startTimes, stopTimes, 
//				false,
//				dataTypes, dataLevels);
//		
////		try
////		{
////			return queryEngine.executeQuery(true, instruments, startTimes,
////					stopTimes, true);
////		}
////		catch (ServiceEngineException e)
////		{
////			e.printStackTrace();
////			return "Exception";
////		}
//	}


	/**
	 * Sorted query.
	 * 
	 * @param instruments the instruments
	 * @param startTimes the start times
	 * @param stopTimes the stop times
	 * @param partialSorting the partial sorting
	 * @return the string
	 * @throws Exception 
	 */
	@WebMethod
	public String sortedQuery(Writer printWriter,String[] instruments, 
			String[] startTimes,
			String[] stopTimes, 
			boolean partialSorting, 
			String[] dataTypes, 
			int[] dataLevels) throws Exception
	{
		return serviceEngine.executeQuery(printWriter,instruments, 
				startTimes, 
				stopTimes, 
				partialSorting,
				dataTypes, 
				dataLevels,false);		
	}

//	/**
//		 * Simple query.
//		 * 
//		 * @param instrument the instrument of the query
//		 * @param startTime the start time of the query
//		 * @param stopTime the stop time of the query
//		 * @return the result of the query formatted as a string.
//		 */
//		@WebMethod
//		public String simpleQuery(String instrument, String startTime,
//				String stopTime, String dataType, String dataLevel)
//		{
//			return "Work in Progress";
//			
//	//		return serviceEngine.executeQuery(instrument, startTime, stopTime, 
//	//				false,
//	//				dataType, dataLevel);
//	
//	
//	//		try
//	//		{
//	//			return queryEngine.executeQuery(true, instrument, startTime,
//	//					stopTime);
//	//		}
//	//		catch (ServiceEngineException e)
//	//		{
//	//			e.printStackTrace();
//	//			return "Exception";
//	//		}
//		}


//	/**
//	 * Gets the instruments.
//	 * 
//	 * @return the instruments
//	 */
//	@WebMethod
//	public String getInstruments()
//	{
//		return "Work in progress....";
//
////		return queryEngine.getInstruments();		
//	}	
}
