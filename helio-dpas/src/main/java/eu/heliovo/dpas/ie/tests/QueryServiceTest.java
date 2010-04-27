/*
 * 
 */
package eu.heliovo.dpas.ie.tests;

import java.text.ParseException;

import javax.xml.transform.stream.StreamSource;

import junit.framework.TestCase;
import eu.heliovo.dpas.ie.controller.QueryService;

public class QueryServiceTest extends TestCase
{
	QueryService		dpasService		=	new eu.heliovo.dpas.ie.controller.QueryService();
	/*
	 * Input data
	 */
	String[] 	instruments 	= 	null;
	String[] 	startTimes 		= 	null;
	String[] 	stopTimes 		= 	null;
	String[] 	dataTypes 		= 	null;
	int[]  	 	levels 			= 	null;
	boolean	 	partialSorting	=	true;
	/*
	 * Output data
	 */
	StreamSource		result			=	null;

	public void testSortedQuery()
	{
		System.out.println();
		System.out.println();
		System.out.println("**** Test SortedQuery - starting....****");
		/*
		 * Creating inputs
		 */
		try 
		{
			createInput();
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
		} 
//		/*
//		 * Printing the input
//		 */
//		for(int index = 0; index<instruments.length; index++)
//			System.out.println(instruments[index] + " from " + startTimes[index] + " to " + stopTimes[index]);
		/*
		 * Invoking the query...
		 */
		try {
			result	=	dpasService.sortedQuery(instruments, startTimes, stopTimes, partialSorting, null, null,false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Partially Sorted Result : " + result);

		try {
			result	=	dpasService.sortedQuery(instruments, startTimes, stopTimes, !partialSorting, null, null,false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Globally Sorted Result : " + result);

		System.out.println("**** Test SortedQuery - done' ****");
	}


	/*
	 * Utilities
	 */
	private void createInput() throws ParseException
	{
		instruments = new String[4];
		startTimes = new String[4];
		stopTimes = new String[4];

		instruments[0] 	= "RHESSI__HESSI_GMR";
		startTimes[0] 	= "2002-01-01 00:00:00";
		stopTimes[0] 	= "2008-01-01 00:00:00";

		instruments[1] 	= "RHESSI__HESSI_HXR";
		startTimes[1] 	= "2002-01-01 00:00:00";
		stopTimes[1] 	= "2008-01-01 00:00:00";

		instruments[2] = "PHOENIX__2";
		startTimes[2] 	= "2002-01-01 00:00:00";
		stopTimes[2] 	= "2008-01-01 00:00:00";

		instruments[3] = "HINODE__EIS";
		startTimes[3] 	= "2002-01-01 00:00:00";
		stopTimes[3] 	= "2008-01-01 00:00:00";
	}
}
