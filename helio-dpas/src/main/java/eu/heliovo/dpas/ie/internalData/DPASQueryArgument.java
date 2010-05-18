package eu.heliovo.dpas.ie.internalData;

import java.util.Vector;

/**
 * The Class DPASQueryArgument.
 */
public class DPASQueryArgument extends DPASRequestArgument
{
	Vector<String>	instruments		=	new Vector<String>();
	Vector<String>	startTimes		=	new Vector<String>();
	Vector<String>	stopTimes		=	new Vector<String>();
	boolean			partialSorting	=	false;
	Vector<String>	dataTypes		=	new Vector<String>();
	Vector<Integer>	dataLevels		=	new Vector<Integer>();
	
	/**
	 * Instantiates a new dPAS query argument.
	 * 
	 * @param instruments the instruments
	 * @param startTimes the start times
	 * @param stopTimes the stop times
	 * @throws DPASQueryArgumentException 
	 */
	public DPASQueryArgument(String[] instruments, String[] startTimes,
			String[] stopTimes) throws DPASQueryArgumentException
	{
		if(!testInputConsistency(instruments, startTimes, stopTimes))
			throw new DPASQueryArgumentException();
		fillVectors(instruments, startTimes, stopTimes);
	}

	/**
	 * Instantiates a new dPAS query argument.
	 * 
	 * @param instruments the instruments
	 * @param startTimes the start times
	 * @param stopTimes the stop times
	 * @param partialSorting the partial sorting
	 * @throws DPASQueryArgumentException the dPAS query argument exception
	 */
	public DPASQueryArgument(String[] instruments, String[] startTimes,
			String[] stopTimes, boolean partialSorting) throws DPASQueryArgumentException
	{
		if(!testInputConsistency(instruments, startTimes, stopTimes))
			throw new DPASQueryArgumentException();

		fillVectors(instruments, startTimes, stopTimes);
		this.partialSorting	=	partialSorting;
	}

	/**
	 * Instantiates a new dPAS query argument.
	 * 
	 * @param instruments the instruments
	 * @param startTimes the start times
	 * @param stopTimes the stop times
	 * @param partialSorting the partial sorting
	 * @throws DPASQueryArgumentException the dPAS query argument exception
	 */
	public DPASQueryArgument(String[] instruments, String[] startTimes,
			String[] stopTimes, boolean partialSorting, String[] dataTypes, int[]dataLevels) throws DPASQueryArgumentException
	{
		if(!testInputConsistency(instruments, startTimes, stopTimes, dataTypes, dataLevels))
			throw new DPASQueryArgumentException();

		fillVectors(instruments, startTimes, stopTimes);
		this.partialSorting	=	partialSorting;
	}
	
	
	public boolean isPartialSorting() 
	{
		return partialSorting;
	}

	public void setPartialSorting(boolean partialSorting) 
	{
		this.partialSorting = partialSorting;
	}

	/**
	 * Gets the number of instruments queries.
	 *
	 * @return the number of instruments queries
	 */
	public	int	getNumberOfInstrumentsQueries()
	{
		return	instruments.size();
	}

	public	String	getCurrentInstrument(int n)
	{
		return	instruments.get(n);
	}

	public	String	getCurrentStartTime(int n)
	{
		return	startTimes.get(n);
	}

	public	String	getCurrentStopTime(int n)
	{
		return	stopTimes.get(n);
	}

	public	String	getCurrentDataType(int n)
	{
		if(dataTypes != null)
			return	dataTypes.get(n);
		else
			return null;
	}

	public	Integer	getCurrentDataLevel(int n)
	{
		if(dataLevels != null)
			return	dataLevels.get(n);
		else
			return null;
	}

	/*
	 * Utilities
	 */	
	public	void	fillVectors(String[] instruments, String[] startTimes,
			String[] stopTimes)
	{
		this.instruments	=	createVectorFromArray(instruments);
		this.startTimes		=	createVectorFromArray(startTimes);
		this.stopTimes		=	createVectorFromArray(stopTimes);
	}
	public	void	fillVectors(String[] instruments, String[] startTimes,
			String[] stopTimes, String[] dataTypes, int[] dataLevels)
	{
		this.instruments	=	createVectorFromArray(instruments);
		this.startTimes		=	createVectorFromArray(startTimes);
		this.stopTimes		=	createVectorFromArray(stopTimes);
		this.dataTypes		=	createVectorFromArray(dataTypes);
		this.dataLevels		=	createVectorFromArray(dataLevels);
	}
	/**
	 * Creates the vector from array.
	 * 
	 * @param a the a
	 * @return the vector
	 */
	public	Vector<String>	createVectorFromArray(String[] a)
	{
		Vector<String>	v	=	new Vector<String>();
		for(int index = 0; index < a.length; index++)
			v.add(a[index]);
		return	v;
	}
	public	Vector<Integer>	createVectorFromArray(int[] a)
	{
		Vector<Integer>	v	=	new Vector<Integer>();
		for(int index = 0; index < a.length; index++)
			v.add(new Integer(a[index]));
		return	v;
	}
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
	/**
	 * Test input consistency.
	 * 
	 * @param instruments the instruments
	 * @param startTimes the start times
	 * @param stopTimes the stop times
	 * @param dataTypes the data types
	 * @param dataLevels the data levels
	 * @return true, if successful
	 */	
	/*
	 * TODO : Not all checks are performed
	 */
	private boolean testInputConsistency(String[] instruments,
			String[] startTimes, String[] stopTimes, String[] dataTypes,
			int[] dataLevels) 
	{
		if (instruments.length != startTimes.length)
			return false;
		if (instruments.length != stopTimes.length)
			return false;
		if (stopTimes.length != startTimes.length)
			return false;
		if(dataTypes != null)
			if (instruments.length != dataTypes.length)
				return false;
		if(dataLevels != null)
			if (instruments.length != dataLevels.length)
				return false;
		
		return true;
	}

	@Override
	public String toString() 
	{
		String res	=	new String();
		
		for(int n = 0; n < this.getNumberOfInstrumentsQueries(); n++)
		{
			res	+=	"["+n+"] --> ";
			res	+= this.getCurrentInstrument(n);
			res += " from ";
			res += this.getCurrentStartTime(n);
			res += " to ";
			res += this.getCurrentStopTime(n);
			res	+= "\n";
		}
		
		return res;
	}
	
	
}
