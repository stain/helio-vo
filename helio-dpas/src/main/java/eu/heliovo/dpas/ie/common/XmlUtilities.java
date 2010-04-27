package eu.heliovo.dpas.ie.common;

import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

public class XmlUtilities 
{
	TransformerFactory 	transfac 	= null;
	Transformer 		trans 		= null;
		
	public XmlUtilities()
	{
		transfac 	= TransformerFactory.newInstance();
		try 
		{
			trans 		= transfac.newTransformer();
		} 
		catch (TransformerConfigurationException e) 
		{
			e.printStackTrace();
		}
		trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		trans.setOutputProperty(OutputKeys.INDENT, "yes");		
	}

	
	public String	documentToString(Document doc) throws TransformerException
	{
		StringWriter sw = new StringWriter();
		StreamResult result = new StreamResult(sw);
		DOMSource source = new DOMSource(doc);
		trans.transform(source, result);
		return sw.toString();
	}
}
