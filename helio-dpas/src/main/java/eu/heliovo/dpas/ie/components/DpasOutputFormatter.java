package eu.heliovo.dpas.ie.components;

import java.io.BufferedWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import uk.ac.starlink.table.ColumnInfo;
import eu.heliovo.dpas.ie.common.DpasUtilities;
import eu.heliovo.dpas.ie.common.VOTableMaker;
import eu.heliovo.dpas.ie.common.XmlUtilities;
import eu.heliovo.dpas.ie.internalData.DPASResultItem;

public class DpasOutputFormatter 
{
	XmlUtilities			xmlUtils		=	null;
	DocumentBuilderFactory 	dbfac 			=	null;
	DocumentBuilder 		docBuilder 		= 	null;
	DpasUtilities			dpasUtils		=	null;

	public DpasOutputFormatter() 
	{
		try 
		{
			xmlUtils	=	new XmlUtilities();
			dbfac 		=	DocumentBuilderFactory.newInstance();
			docBuilder 	= 	dbfac.newDocumentBuilder();
			dpasUtils	=	new DpasUtilities();
		} 
		catch (ParserConfigurationException e) 
		{
			e.printStackTrace();
		}
	}

	/*
	 * Creates and empty document with root named "root"
	 */
	public Document createNewDocument()
	{
		return createNewDocument("root");
	}
	/*
	 * Creates and empty document with root named rootName
	 */
	public Document createNewDocument(String rootName)
	{
		Document doc = docBuilder.newDocument();
		Element root = doc.createElement(rootName);
		doc.appendChild(root);
		return doc;
	}

	/*
	 * Creates and empty document with root named "root"
	 */
	public Document addFlatChildToDoc(String childName, 
			Vector<String> attrNames,
			Vector<String> attrValues,
			Document doc) throws DpasOutputFormatterException
	{
		/*
		 * Check consistency for the lenght of the vectors
		 */
		if(attrNames.size() != attrValues.size())
			throw new DpasOutputFormatterException();
		
		// create child element, add an attribute, and add to root
		Element child = doc.createElement("childName");
		child.setAttribute("instrument","instrument1");
		child.setAttribute("measurementStart","2008-01-01T00:23:00.245+01:00");
		child.setAttribute("measurementStop","2008-01-01T00:23:05.245+01:00");
		child.setAttribute("url1","fake_url1");
		child.setAttribute("url2","fake_url2");
		
		Element root = doc.getDocumentElement(); 
		root.appendChild(child);
		return doc;
	}


	private Document addFlatChildToDoc(String childName, DPASResultItem r,	Document d) 
	{		
		// Get the root of the document
		Element root = d.getDocumentElement(); 
		// Create child element, add an attribute, and add to root
		Element child = d.createElement(childName);
		String		formattedDate		=	null;
		Calendar	formattedCalendar	=	null;	
		/*
		 * Adding the instrument name
		 */
		child.setAttribute("Instrument", r.instrument);
		/*
		 * Adding now all the fields
		 */
		for(int i = 0; i < DPASResultItem.FIELD_NAMES.length; i++)
		{
			child.setAttribute(DPASResultItem.FIELD_NAMES[i], r.toString(i));
			/*
			 * If it is a date, transform the result.
			 */
			if((i == 0) || (i == 1) || (i == 9))
			{
				/*
				 * Check if the date is valid
				 */
				formattedCalendar	=	(Calendar) r.getColumn(i);
				if(formattedCalendar != null)
				{
					formattedDate	=	dpasUtils.calendarToHELIOTime(formattedCalendar);
					if(formattedDate	!= null)
						child.setAttribute(DPASResultItem.FIELD_NAMES[i], formattedDate);
						
				}				
			}
		}

		root.appendChild(child);						
		return d;
	}
	


	public Document addSortedRealToDocument(Document doc,
			Map<String, DPASResultItem> results)
	{
		/*
		 * First we sort the results...
		 * 
		 */
		Object[] tmpArray	=	null;
		tmpArray			=	results.keySet().toArray();		
		String[] keys		=	new String[tmpArray.length];
		/*
		 * Create the string array of all the keys...
		 */
		for(int i = 0; i < tmpArray.length; i++)
		{
			keys[i] = (String)tmpArray[i];
		}
		/*
		 * Now sorting the keys...
		 */
		Arrays.sort(keys); 
		/*
		 * Now adding the sorted results into the xml
		 */
		DPASResultItem	sortedResult	=	null;
		for(int i = 0; i < keys.length; i++)
		{
			sortedResult	=	results.get(keys[i]);
			doc 	=	addFlatChildToDoc("result", sortedResult, doc);
		}		
		return doc;
	}
	
	
	public void createVOTable(Writer printWriter,Map<String, DPASResultItem> results) throws Exception{
		/*
		 * First we sort the results... 
		 * 
		 */
		BufferedWriter output = new BufferedWriter( printWriter);
		Object[] tmpArray	=	null;
		tmpArray			=	results.keySet().toArray();		
		String[] keys		=	new String[tmpArray.length];
		Calendar	formattedCalendar	=	null;	
		/*
		 * Create the string array of all the keys...
		 */
		for(int i = 0; i < tmpArray.length; i++)
		{
			keys[i] = (String)tmpArray[i];
		}
		/*
		 * Now sorting the keys...
		 */
		Arrays.sort(keys); 
		
		//Creating VOTable
		VOTableMaker voTableMarker=createVOTableMaker();
		voTableMarker.writeBeginVOTable(output,"Dpas VOTable");
		/*
		 * Now adding the sorted results into the xml
		 */	
		DPASResultItem	sortedResult	=	null;
		for(int i = 0; i < keys.length; i++)
		{
			sortedResult	=	results.get(keys[i]);
			for(int j = 0; j < 4; j++)
			{
				System.out.println(sortedResult.toString(j));
				if(j<2 || j==2){
					if(j==0 || j==1){
						//System.out.println(sortedResult.getColumn(j));
						formattedCalendar	=	(Calendar) sortedResult.getColumn(j);
						if(formattedCalendar != null){
							voTableMarker.getValues()[j] =dpasUtils.calendarToHELIOTime(formattedCalendar);
						}
					}else{
						voTableMarker.getValues()[j] = sortedResult.toString(j);
					}
				}else{
					voTableMarker.getValues()[j] =sortedResult.instrument;
				}
				
			}
			voTableMarker.addRow();
			
		}	
		if(voTableMarker.getRowCount() > 0) {
			 voTableMarker.writeTable(output);
    	} 
		
		//Writing end of VOTable.
		voTableMarker.writeEndVOTable(output);

	}
	
	/*
	 * It creates the VOTable.
	 */
	@SuppressWarnings("unused")
	private  VOTableMaker createVOTableMaker() {
								 
		ColumnInfo [] defValues = new ColumnInfo[4];					
		defValues[1] = new ColumnInfo("MeasurementStart",String.class,"Measurement Start");
		defValues[2] = new ColumnInfo("MeasurementEnd",String.class,"Measurement End");
		defValues[0] = new ColumnInfo("FitsURL",String.class,"Fits URL");
		defValues[3] = new ColumnInfo("Instrument",String.class,"Instrument Name");
		return new VOTableMaker(defValues);
	}
	
	
}
