package eu.heliovo.dpas.ie.internalData;

import java.util.Vector;

// TODO: Auto-generated Javadoc
/**
 * The Class DPASQuery.
 */
public class DPASQueryData
{
	
	/** The instruments. */
	private	Vector<String>	instruments;
	
	/** The start times. */
	private	Vector<String>	startTimes;
	
	/** The stop times. */
	private	Vector<String>	stopTimes;
	
	
	/**
	 * Instantiates a new dPAS query data.
	 * 
	 * @param instruments the instruments
	 * @param startTimes the start times
	 * @param stopTimes the stop times
	 */
	public DPASQueryData(Vector<String> instruments, Vector<String> startTimes,
			Vector<String> stopTimes)
	{
		super();
		this.instruments = instruments;
		this.startTimes = startTimes;
		this.stopTimes = stopTimes;
	}

	/**
	 * Instantiates a new dPAS query data.
	 * 
	 * @param instruments the instruments
	 * @param startTimes the start times
	 * @param stopTimes the stop times
	 */
	public DPASQueryData(String[] instruments, String[] startTimes,
			String[] stopTimes)
	{
		super();
//		/*
//		 * Check for consistency, all arrays must have the same lenght
//		 */
//		if(!testInputConsistency)
//		this.instruments = instruments;
//		this.startTimes = startTimes;
//		this.stopTimes = stopTimes;
	}
	/*
	 * Utilities
	 */	
	/**
	 * Test input consistency.
	 * 
	 * @param instruments the instruments
	 * @param startTimes the start times
	 * @param stopTimes the stop times
	 * @return true, if successful
	 */
	private boolean testInputConsistency(String[] instruments,
			String[] startTimes, String[] stopTimes)
	{
		if (instruments.length != startTimes.length)
			return false;
		if (instruments.length != stopTimes.length)
			return false;
		if (stopTimes.length != startTimes.length)
			return false;
	
		return true;
	}
	
}
