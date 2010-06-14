/*
 * 
 */
package eu.heliovo.dpas.ie.tests;

import java.io.StringWriter;
import java.text.ParseException;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;


import org.junit.Ignore;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

import junit.framework.TestCase;
import eu.heliovo.dpas.ie.controller.QueryService;


public class QueryServiceTestCompleteOld
{
	QueryService		dpasService		=	new eu.heliovo.dpas.ie.controller.QueryService();
	/*
	 * Input data
	 */
	String[] instruments 	= 	null;
	String[] startTimes 	= 	null;
	String[] stopTimes 		= 	null;
	String[] dataTypes 		= 	null;
	int[]  	 levels 		= 	null;
	boolean	 partialSorting	=	true;
	
//	/**
//	 * Test simple query.
//	 */
//	public void testSimpleQuery()
//	{
//		System.out.println();
//		System.out.println();
//		System.out.println("**** Test SimpleQuery - starting....****");
//
//		/*
//		 * Creating inputs
//		 */
//		try 
//		{
//			createInput();
//		} 
//		catch (ParseException e) 
//		{
//			e.printStackTrace();
//		} 
//		System.out.println(instruments[0] + " from " + startTimes[0] + " to " + stopTimes[0]);
//		/*
//		 * Invoking the query...
//		 */
//		String result = dpasService.simpleQuery(instruments[0], startTimes[0], stopTimes[0], null, null);
//		System.out.println("Result : " + result);
//
//		System.out.println("**** Test SimpleDummyQuery - done' ****");
//	}

//	/**
//	 * Test query.
//	 */
//	public void testQuery()
//	{
//		System.out.println();
//		System.out.println();
//		System.out.println("**** Test Query - starting....****");
//
//		/*
//		 * Creating inputs
//		 */
//		try 
//		{
//			createInput();
//		} 
//		catch (ParseException e) 
//		{
//			e.printStackTrace();
//		} 
//		/*
//		 * Printing the input
//		 */
//		for(int index = 0; index<instruments.length; index++)
//			System.out.println(instruments[index] + " from " + startTimes[index] + " to " + stopTimes[index]);
//		/*
//		 * Invoking the query...
//		 */
//		String result = dpasService.query(instruments, startTimes, stopTimes, null, null);
//		System.out.println("Result : " + result);
//		System.out.println("**** Test Query - done' ****");
//	}

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
		/*
		 * Printing the input
		 */
		for(int index = 0; index<instruments.length; index++)
			System.out.println(instruments[index] + " from " + startTimes[index] + " to " + stopTimes[index]);
		/*
		 * Invoking the query...
		 */
		StreamSource result=null;
		try {
			//result = dpasService.sortedQuery(instruments, startTimes, stopTimes, partialSorting, null, null);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
		try {
			result	=	dpasService.sortedQuery(instruments, startTimes, stopTimes, !partialSorting, null, null,false);
			Document wsDoc = null;
			wsDoc =  toDocument(result);	  	              
	        System.out.println("THE RESULTDOC FROM SERVICE = " +getStringFromDoc(wsDoc) );
			System.out.println("Globally Sorted Result : " + result);
			System.out.println("**** Test SortedQuery - done' ****");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * Method used to convert Source to dom object.
	 */
	private  Document toDocument(Source src) throws TransformerException {
        DOMResult result = new DOMResult();
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        try {
            transformer.transform(src, result);
        } catch (TransformerException te) {
            throw new TransformerException("Error while applying template", te);
        }
        
        Document root = (Document) (result.getNode());//.getOwnerDocument();
       return root;
	}
	
	
	public String getStringFromDoc(org.w3c.dom.Document doc)    {
        try
        {
        	 Transformer transformer = TransformerFactory.newInstance().newTransformer();
        	  StreamResult result = new StreamResult(new StringWriter());
        	  DOMSource source = new DOMSource(doc);
        	  transformer.transform(source, result);
        	  return result.getWriter().toString();

        }
        catch(TransformerException ex)
        {
           ex.printStackTrace();
           return null;
        }
    }

//	public void testGetInstruments()
//	{
//		System.out.println();
//		System.out.println();
//		System.out.println("**** Test GetInstruments - starting....****");
//		/*
//		 * Invoking the query...
//		 */
//		String result = dpasService.getInstruments();
//		System.out.println("Instruments : " + result);
//
//		System.out.println("**** Test GetInstruments - done' ****");
//	}

	@Ignore @Test
	public void testSetSimpleInstrument()
	{
		
	}

	/*
	 * Utilities
	 */
	private void createInput() throws ParseException
	{
		instruments = new String[3];
		startTimes = new String[3];
		stopTimes = new String[3];

		instruments[0] 	= "RHESSI__HESSI_GMR";
		startTimes[0] 	= "2002-01-01 00:00:00";
		stopTimes[0] 	= "2004-01-01 00:00:00";

		instruments[1] 	= "RHESSI__HESSI_HXR";
		startTimes[1] 	= "2002-01-01 00:00:00";
		stopTimes[1] 	= "2004-01-01 00:00:00";

		instruments[2] = "PHOENIX__2";
		startTimes[2] 	= "2002-01-01 00:00:00";
		stopTimes[2] 	= "2004-01-01 00:00:00";
	}
}
