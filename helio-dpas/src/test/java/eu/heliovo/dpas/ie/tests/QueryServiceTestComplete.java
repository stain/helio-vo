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

import eu.heliovo.dpas.ie.controller.QueryService;


public class QueryServiceTestComplete
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
	
	@Test
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
			result	=	dpasService.sortedQuery(instruments, startTimes, stopTimes, !partialSorting, null, null,true);
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

	/*
	 * Utilities
	 */
	private void createInput() throws ParseException
	{
		instruments = new String[1];
		startTimes = new String[1];
		stopTimes = new String[1];

		instruments[0] 	= "RHESSI__HESSI_GMR";
		startTimes[0] 	= "2003-03-28 00:00:00";
		stopTimes[0] 	= "2003-04-11 00:00:00";

//		instruments[1] 	= "RHESSI__HESSI_HXR";
//		startTimes[1] 	= "2007-01-01 00:00:00";
//		stopTimes[1] 	= "2009-01-01 00:00:00";
//
//		instruments[0] = "PHOENIX__2";
//		startTimes[0] 	= "1978-01-01 00:00:00";
//		stopTimes[0] 	= "2010-06-01 00:00:00";
//
//		instruments[3] = "HINODE__EIS";
//		startTimes[3] 	= "2007-01-01 00:00:00";
//		stopTimes[3] 	= "2009-01-01 00:00:00";
	}
}
