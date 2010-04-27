package eu.heliovo.dpas.ie.controller;

import eu.heliovo.dpas.ie.classad.ClassAdMapperException;
import eu.heliovo.dpas.ie.classad.ClassAdUtilitiesException;
import eu.heliovo.dpas.ie.components.DPASLogger;
import eu.heliovo.dpas.ie.components.DummyIdentityEngine;
import eu.heliovo.dpas.ie.components.OutputFormatter;
import eu.heliovo.dpas.ie.components.QueryRefinmentEngine;
import eu.heliovo.dpas.ie.internalData.DPASQueryArgument;
import eu.heliovo.dpas.ie.internalData.DPASQueryArgumentException;
import eu.heliovo.dpas.ie.internalData.DPASRequest;
import eu.heliovo.dpas.ie.internalData.DPASRequestFailureReasons;
import eu.heliovo.dpas.ie.providers.DataProviderManager;


/**
 * The Class QueryEngine.
 * 
 * @author Gabriele Pierantoni - pierang@cs.tcd.ie
 * 
 * This is the main class that executes all requests from the Service class.
 * It handles queries requests that deal with data and management requests
 * that manage the data provider's table.
 */
public class OldServiceEngine
{
	/** 
	 * The data provider manager. 
	 */
	private 	DataProviderManager		dpManager		=	null;
	private 	QueryRefinmentEngine	qRefinement		=	null;
	private 	DPASLogger				logger			=	null;
	private 	DummyIdentityEngine		idEngine		=	null;
	private		QueryEngine				qEngine			=	null;
	private		OutputFormatter			outputFormatter =	null;
	/**
	 * Instantiates a new query engine.
	 * 
	 * Default Constructor
	 */
	public OldServiceEngine()
	{
		try {
			dpManager	=	new DataProviderManager();
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
		qRefinement	=	new QueryRefinmentEngine();
		logger		=	new DPASLogger();
		idEngine	=	new DummyIdentityEngine();
		qEngine		=	new QueryEngine(dpManager);
	}

	/*
	 * Methods that implement the Service methods
	 */
	/**
	 * Execute query.
	 * 
	 * @param notDummy the not dummy
	 * @param instruments the instruments
	 * @param startTimes the start times
	 * @param stopTimes the stop times
	 * @param partialSorting the partial sorting
	 * @return the string
	 * @throws ServiceEngineException the query engine exception
	 */
	public String executeQuery(boolean notDummy, String[] instruments,
			String[] startTimes, String[] stopTimes, boolean partialSorting)
			throws ServiceEngineException
	{
		System.out.println("QueryEngine.executeQuery(...)");
		DPASRequest		request		=	null;
		/*
		 * Create the DPASRequest 
		 */
		/*
		 * TODO : Add real identity
		 */
//		request	=	new DPASRequest(
//				idEngine.createDummyIdentity(),
//				DPASRequestTypes.queryRequest);
		/*
		 * Create the arguments and add the parameters to the query
		 */
		try
		{
			request.setRequestArgument(new DPASQueryArgument(instruments, startTimes, stopTimes, partialSorting));
		}
		catch (DPASQueryArgumentException e)
		{
			/*
			 * The arguments were not formatted properly.
			 */
			request.failRequest(DPASRequestFailureReasons.BadQueryArguments);	
			e.printStackTrace();
			return	logAndReturn(request);
		}
		/*
		 * Check that the request is authorised, the request holds all the data needed
		 */
		if(!idEngine.checkAuthorization(request))
		{
			/*
			 * The request was not authorised.
			 */
			request.failRequest(DPASRequestFailureReasons.AuthorizationFailed);	
			return	logAndReturn(request);
		}
		/*
		 * Refine the query
		 */
		request.setRequestArgument(qRefinement.refine((DPASQueryArgument) request.getRequestArgument()));
		/*
		 * Execute the query
		 */
		request.setRequestResult(qEngine.execute((DPASQueryArgument) request.getRequestArgument()));
		/*
		 * Return the result
		 */
		return	logAndReturn(request);
	}

	/**
	 * Execute query.
	 * 
	 * @param notDummy the not dummy
	 * @param instrument the instrument
	 * @param startTime the start time
	 * @param stopTime the stop time
	 * @return the string
	 * @throws ServiceEngineException the query engine exception
	 */
	public String executeQuery(boolean notDummy, String instrument,
			String startTime, String stopTime) throws ServiceEngineException
	{
		System.out.println("QueryEngine.executeQuery(...)");

		return "Work in Progress...";
	}
	/**
	 * Gets the instruments.
	 * 
	 * @return the instruments
	 */
	public String getInstruments()
	{
		System.out.println("QueryEngine.getInstruments(...)");

		return "Work in Progress...";
	}


	/**
	 * Sets the instruments.
	 * 
	 * @param instrument the instrument
	 * @param dataSourceUrl the data source url
	 * @param adapter the adapter
	 * @return the string
	 */
	public String setInstruments(String instrument, String dataSourceUrl,
			Object adapter)
	{
		System.out.println("QueryEngine.setInstruments(...)");

		return "Work in Progress...";
	}
	/*
	 * Creates the output for the request
	 */
	private String logAndReturn(DPASRequest request)
	{
		logger.addRequest(request);
		return outputFormatter.format(request.getRequestResult());
	}
}
