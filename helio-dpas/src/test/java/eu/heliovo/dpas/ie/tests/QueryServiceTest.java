package eu.heliovo.dpas.ie.tests;

import java.io.StringWriter;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import junit.framework.TestCase;

import org.junit.Ignore;
import org.junit.Test;
import org.w3c.dom.Document;

import eu.heliovo.dpas.ie.common.DebugUtilities;
import eu.heliovo.dpas.ie.controller.QueryService;


public class QueryServiceTest extends TestCase
{
	/*
	 * The dpas instance
	 */
	QueryService		dpas		=	new QueryService();
	/*
	 * Utilities
	 */
	DebugUtilities							debugUtils	=	new DebugUtilities();
	/*
	 * Input data
	 */
	String[] instruments 	= 	null;
	String[] startTimes 	= 	null;
	String[] stopTimes 		= 	null;
	String[] dataTypes 		= 	null;
	int[]  	 levels 		= 	null;
	boolean	 partialSorting	=	true;

	
	@Ignore @Test
	public void testSortedQuery()
	{
		initialize();
		
		debugUtils.printLog(this.getClass().getName(), "Starting testQueryService()...");
		/*
		 * Printing the input
		 */
		System.out.println("--------------------------------------------------------------------------------------------------------------------------");
		debugUtils.printLog(this.getClass().getName(), "Query Parameters : ");
		for(int index = 0; index<instruments.length; index++)
			debugUtils.printLog(this.getClass().getName(), instruments[index] + " from " + startTimes[index] + " to " + stopTimes[index]);
		System.out.println("--------------------------------------------------------------------------------------------------------------------------");
		/*
		 * Invoking the query...
		 */
		StreamSource result=null;
		try 
		{
			System.out.println("--------------------------------------------------------------------------------------------------------------------------");
			debugUtils.printLog(this.getClass().getName(), "Invoking Sorted Query....");
			result	=	dpas.sortedQuery(instruments, startTimes, stopTimes, !partialSorting, null, null,true);
			debugUtils.printLog(this.getClass().getName(), "....Sorted Query completed");
			System.out.println("--------------------------------------------------------------------------------------------------------------------------");

			Document wsDoc = null;
			wsDoc =  toDocument(result);	  	              
			System.out.println("--------------------------------------------------------------------------------------------------------------------------");
			debugUtils.printLog(this.getClass().getName(), "Resulting VO Table for " + getStringFromDoc(wsDoc) );
			debugUtils.printLog(this.getClass().getName(), "Query Parameters : ");
			for(int index = 0; index<instruments.length; index++)
				debugUtils.printLog(this.getClass().getName(), instruments[index] + " from " + startTimes[index] + " to " + stopTimes[index]);

			debugUtils.printLog(this.getClass().getName(), getStringFromDoc(wsDoc));
			System.out.println("--------------------------------------------------------------------------------------------------------------------------");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			assertTrue(false);
		}
		debugUtils.printLog(this.getClass().getName(), "....testQueryService() completed");
	}
/*
	@Ignore @Test
	public void testGetSupportedInstruments()
	{
		fail("Not yet implemented");
	}*/

	/*
	 * Utilities
	 */	
	private void initialize()
	{
		instruments = new String[1];
		startTimes = new String[1];
		stopTimes = new String[1];

		
//		instruments[0] = "YNAO__HALPH";
//		startTimes[0] 	= "2007-07-01 00:00:00";
//		stopTimes[0] 	= "2007-07-02 00:00:00";

//		instruments[0] = "YOHKOH__WBS_GRS";
//		startTimes[0] 	= "2000-07-01 00:00:00";
//		stopTimes[0] 	= "2000-07-02 00:00:00";

//		instruments[0] = "KPNO__VSM";
//		startTimes[0] 	= "2007-07-01 00:00:00";
//		stopTimes[0] 	= "2007-07-02 00:00:00";

//		instruments[0] = "SOHO__VIRGO";
//		startTimes[0] 	= "2007-07-01 00:00:00";
//		stopTimes[0] 	= "2007-07-02 00:00:00";

//		instruments[0] = "SOHO__EIT";
//		startTimes[0] 	= "2007-07-01 00:00:00";
//		stopTimes[0] 	= "2007-07-02 00:00:00";

//		instruments[0] = "SOHO__CDS";
//		startTimes[0] 	= "2007-07-01 00:00:00";
//		stopTimes[0] 	= "2007-07-02 00:00:00";

//		instruments[0] = "SOHO__SUMER";
//		startTimes[0] 	= "2007-07-01 00:00:00";
//		stopTimes[0] 	= "2007-07-02 00:00:00";

//		instruments[0] = "SOHO__UVCS";
//		startTimes[0] 	= "2007-07-01 00:00:00";
//		stopTimes[0] 	= "2007-07-02 00:00:00";

//		instruments[0] = "SOHO__LASCO";
//		startTimes[0] 	= "2007-07-01 00:00:00";
//		stopTimes[0] 	= "2007-07-02 00:00:00";

//		instruments[0] = "SOHO__SWAN";
//		startTimes[0] 	= "2007-07-01 00:00:00";
//		stopTimes[0] 	= "2007-07-02 00:00:00";

//		instruments[0] = "SOHO__MDI";
//		startTimes[0] 	= "2007-07-01 00:00:00";
//		stopTimes[0] 	= "2007-07-07 00:00:00";

//		instruments[0] = "SOHO__GOLF";
//		startTimes[0] 	= "2007-07-01 00:00:00";
//		stopTimes[0] 	= "2007-07-07 00:00:00";

//		instruments[0] = "SOHO__CELIAS";
//		startTimes[0] 	= "2007-07-01 00:00:00";
//		stopTimes[0] 	= "2007-07-02 00:00:00";

//		instruments[0] = "SOHO__COSTEP";
//		startTimes[0] 	= "2007-07-01 00:00:00";
//		stopTimes[0] 	= "2007-07-02 00:00:00";

		instruments[0] = "SOHO__ERNE";
		startTimes[0] 	= "2007-07-01 00:00:00";
		stopTimes[0] 	= "2007-07-02 00:00:00";

//		instruments[0] 	= "RHESSI__HESSI_GMR";
//		startTimes[0] 	= "2005-01-01 00:00:00";
//		stopTimes[0] 	= "2005-01-02 12:55:00";
//
//		instruments[1] 	= "RHESSI__HESSI_HXR";
//		startTimes[1] 	= "2003-03-27 00:00:00";
//		stopTimes[1] 	= "2005-03-02 00:00:00";
//
//		instruments[1] = "PHOENIX__2";
//		startTimes[1] 	= "2005-01-01 00:00:00";
//		stopTimes[1] 	= "2005-02-01 00:00:00";

//		instruments[1] = "HINODE__EIS";
//		startTimes[1] 	= "2007-07-01 00:00:00";
//		stopTimes[1] 	= "2007-07-02 00:00:00";
//
//		instruments[1] = "HINODE__XRT";
//		startTimes[1] 	= "2007-07-01 00:00:00";
//		stopTimes[1] 	= "2007-07-02 00:00:00";
//
//		instruments[4] = "HINODE__SOT_SP";
//		startTimes[4] 	= "2006-10-20 00:00:00";
//		stopTimes[4] 	= "2006-10-23 00:00:00";
//
//		instruments[5] 	= "HINODE__SOT_FG";
//		startTimes[5] 	= "2006-10-20 00:00:00";
//		stopTimes[5] 	= "2006-10-25 00:00:00";

//		instruments[0] 	= "NOBE__NORH";
//		startTimes[0] 	= "1996-05-01 00:00:00";
//		stopTimes[0] 	= "1999-05-30 00:00:00";

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
}
