package eu.heliovo.dpas.ie.controller;

import eu.heliovo.dpas.ie.components.DummyIdentityEngine;
import eu.heliovo.dpas.ie.internalData.DPASQueryArgument;
import eu.heliovo.dpas.ie.internalData.DPASQueryArgumentException;
import eu.heliovo.dpas.ie.internalData.DPASRequest;
import eu.heliovo.dpas.ie.internalData.DPASRequestTypes;

/**
 * The Class ServiceUtilities.
 * 
 * @author Gabriele Pierantoni (pierang@cs.tcd.ie)
 * 
 */
public class ServiceUtilities
{
	private 	DummyIdentityEngine		idEngine		=	new DummyIdentityEngine();

	public DPASRequest	createDPASQueryRequest()
	{
		DPASRequest request	=	new DPASRequest(
				idEngine.createDummyIdentity(),
				DPASRequestTypes.queryRequest,
				null);
			
		return request;
	}

	public DPASQueryArgument	createDPASQueryArgument(String[] instruments, String[] startTimes,
			String[] stopTimes, boolean partialSorting, String[] dataTypes, int[] dataLevels) throws DPASQueryArgumentException
	{
		DPASQueryArgument	queryArguments	= new DPASQueryArgument(
					instruments, startTimes, stopTimes,
					partialSorting, dataTypes, dataLevels);
			
		return queryArguments;
	}

}
