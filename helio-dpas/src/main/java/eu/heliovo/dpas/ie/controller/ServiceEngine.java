package eu.heliovo.dpas.ie.controller;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

import javax.xml.transform.TransformerException;

import eu.heliovo.dpas.ie.classad.ClassAdMapperException;
import eu.heliovo.dpas.ie.classad.ClassAdUtilitiesException;
import eu.heliovo.dpas.ie.common.DebugUtilities;
import eu.heliovo.dpas.ie.common.XmlUtilities;
import eu.heliovo.dpas.ie.components.DPASLogger;
import eu.heliovo.dpas.ie.components.DummyIdentityEngine;
import eu.heliovo.dpas.ie.components.QueryRefinmentEngine;
import eu.heliovo.dpas.ie.controller.ServiceUtilities;
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
 * 
 */
public class ServiceEngine
{
	/** 
	 * The data provider manager. 
	 */
	private		ServiceUtilities		sUtilities		=	null;
	private 	DataProviderManager		dpManager		=	null;
	private 	QueryRefinmentEngine	qRefinement		=	null;
	private 	DPASLogger				logger			=	null;
	private 	DummyIdentityEngine		idEngine		=	null;
	private		QueryEngine				qEngine			=	null;
	private		XmlUtilities			xmlUtilities 	=	null;
	private		DebugUtilities			debugUtils		=	null;
	/**
	 * Instantiates a new query engine.
	 * 
	 * Default Constructor
	 * @throws ClassAdUtilitiesException 
	 * @throws ClassAdMapperException 
	 * @throws ArithmeticException 
	 */
	public ServiceEngine() throws ArithmeticException, ClassAdMapperException, ClassAdUtilitiesException
	{
		sUtilities			=	new ServiceUtilities();
		dpManager			=	new DataProviderManager();
		qRefinement			=	new QueryRefinmentEngine();
		logger				=	new DPASLogger();
		idEngine			=	new DummyIdentityEngine();
		qEngine				=	new QueryEngine(dpManager);
		xmlUtilities 		=	new XmlUtilities();
		debugUtils		=	new DebugUtilities();
	}

	/*
	 * Methods that implement the Service methods
	 */
	/**
	 * Execute query.
	 * 
	 * @param instruments the instruments
	 * @param startTimes the start times
	 * @param stopTimes the stop times
	 * @param partialSorting the partial sorting
	 * @param dataTypes the dataTypes
	 * @param dataLevels the dataLevels
	 * @return the XML results formatted as a string.
	 * @throws Exception 
	 * 
	 * @throws ServiceEngineException the query engine exception
	 */
	public String executeQuery(Writer printWriter,String[] instruments,
			String[] startTimes, 
			String[] stopTimes, 
			boolean partialSorting,
			String[] dataTypes, 
			int[] dataLevels,boolean votable) throws Exception
	{
		debugUtils.printLog(this.getClass().getName(), "Executing Query...");

		DPASRequest			request			=	null;
		DPASQueryArgument	queryArgument	=	null;
		/*
		 * Create the request 
		 */
		request	=	sUtilities.createDPASQueryRequest();
		/*
		 * Create the Object that represents the query arguments 
		 */
		try 
		{
			queryArgument	=	sUtilities.createDPASQueryArgument(instruments, 
					startTimes, 
					stopTimes, 
					partialSorting, 
					dataTypes, 
					dataLevels);
		} 
		/*
		 * The arguments were not properly formatted
		 */
		catch (DPASQueryArgumentException e) 
		{
			e.printStackTrace();
			request.failRequest(DPASRequestFailureReasons.BadQueryArguments);	
			return	logAndReturn(request);
		}
		/*
		 * If the query arguments were properly formatted, add them to 
		 * the request object
		 */
		request.setRequestArgument(queryArgument);
		
		/*
		 * Check that the request is authorised, the request holds all the data needed
		 */
		if(!idEngine.checkAuthorization(request))
		{
			/*
			 * The request was not authorised.
			 */
			debugUtils.printLog(this.getClass().getName(), "Authorization Failure, exiting...");
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
//		request.setRequestResult(qEngine.execute((DPASQueryArgument) request.getRequestArgument()));
		System.out.println("--------------------------------------------------------------------------------------------------------------------------");
		debugUtils.printLog(this.getClass().getName(), "Executing Query...");
		request = qEngine.execute(printWriter,request,votable);
		debugUtils.printLog(this.getClass().getName(), "... Executing Query - done");
		System.out.println("--------------------------------------------------------------------------------------------------------------------------");
		/*
		 * Return the result
		 */
		return	printOutputToStream(printWriter,logAndReturn(request),votable);
	}

	/*
	 * Creates the output for the request
	 */
	private String logAndReturn(DPASRequest request)
	{
		logger.addRequest(request);
		
		try
		{
			return xmlUtilities.documentToString(request.getXmlOutput());
		}
		catch (TransformerException e)
		{
			e.printStackTrace();
			return "DPAS - Exception";
		}
	}
	
	private String printOutputToStream(Writer printWriter,String queryRes,boolean votable) throws IOException
	{
		if(!votable && printWriter!=null)
		{
			BufferedWriter output = new BufferedWriter( printWriter);
			//System.out.println("  Query Response   "+queryRes);
			output.write(queryRes);
			output.flush();
			output.close();
		}
		return queryRes;
	}	
}
