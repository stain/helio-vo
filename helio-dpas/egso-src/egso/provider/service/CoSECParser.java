package org.egso.provider.service;


import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * This class help an XML parser to create an SQL query (in fact, it just
 * creates the WHERE part of this query, where all parameters are stored) for a
 * given EGSO query (XML query).
 *
 * @author    Romain Linsolas (linsolas@gmail.com)
 * @version   0.9.1-jd - 30/04/2004 [26/11/2003]
 */
//public class CoSECParser implements ContentHandler {
public class CoSECParser extends DefaultHandler {

	private StringBuffer result = null;
	private boolean considerResult = false;


	public CoSECParser() {
		result = new StringBuffer();
	}

	public String getResult() {
		return (result.toString());
	}

	/**
	 * Receives an object for locating the origin of SAX document events.<BR>
	 * <I>Not used here.</I>
	 *
	 * @param locator  An object that can return the location of any SAX document
	 *      event.
	 */
	public void setDocumentLocator(Locator locator) {
//		System.out.println("setDocumentLocator");
	}


	/**
	 * Processes character data.
	 *
	 * @param ch                The characters from the XML document.
	 * @param start             The start position in the array.
	 * @param length            The number of characters to read from the array.
	 * @exception SAXException  Any SAX exception, possibly wrapping another
	 *      exception.
	 */
	public void characters(char[] ch, int start, int length)
			 throws SAXException {
//		System.out.println("characters: " + new String(ch, start, length));
		if (considerResult) {
			result.append((new String(ch, start, length)).trim());
		}
	}


	/**
	 * Receives notification of the end of a document.
	 *
	 * @exception SAXException  Any SAX exception, possibly wrapping another
	 *      exception.
	 */
	public void endDocument()
			 throws SAXException {
//		System.out.println("...End of the document");
	}


	/**
	 * Receives notification of the end of an element.
	 *
	 * @param namespaceURI      The namespace URI.
	 * @param localName         The local name (without prefix), or the empty
	 *      String if Namespace processing is not being performed.
	 * @param qName             The qualified XML 1.0 name (with prefix), or the
	 *      empty String if qualified names are not available.
	 * @exception SAXException  Any SAX exception, possibly wrapping another
	 *      exception.
	 */
	public void endElement(String namespaceURI, String localName, String qName)
			 throws SAXException {
//		System.out.println("endElement: </" + localName + ">");
		if (qName.equals("param")) {
			considerResult = false;
		}
	}


	/**
	 * Receives notification of the beginning of the document.
	 *
	 * @exception SAXException  Any SAX exception, possibly wrapping another
	 *      exception.
	 */
	public void startDocument()
			 throws SAXException {
//		System.out.println("Start of the Document...");
	}


	/**
	 * Receives notification of the beginning of an element.
	 *
	 * @param namespaceURI      The namespace URI.
	 * @param localName         The local name (without prefix), or the empty
	 *      String if Namespace processing is not being performed.
	 * @param qName             The qualified name (with prefix), or the empty
	 *      String if qualified names are not available.
	 * @param atts              The attributes attached to the element. If there
	 *      are no attributes, it shall be an empty Attributes object.
	 * @exception SAXException  Any SAX exception, possibly wrapping another
	 *      exception.
	 */
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts)
			 throws SAXException {
//		System.out.println("startElement: <" + namespaceURI + "|" + localName + "|" + qName + ">");
		considerResult = qName.equals("param");
	}



	/**
	 * Begins the scope of a prefix-URI Namespace mapping. <BR>
	 * <I>Not used here.</I>
	 *
	 * @param prefix            The Namespace prefix being declared.
	 * @param uri               The Namespace URI the prefix is mapped to.
	 * @exception SAXException  Any SAX exception, possibly wrapping another
	 *      exception.
	 */
	public void startPrefixMapping(String prefix, String uri)
			 throws SAXException {
//			System.out.println("startPrefixMapping: " + prefix + " | " + uri);
	}


	/**
	 * Receives notification of ignorable whitespace in element content. <BR>
	 * <I>Not used here.</I>
	 *
	 * @param ch                The characters from the XML document.
	 * @param start             The start position in the array.
	 * @param length            The number of characters to read from the array.
	 * @exception SAXException  Any SAX exception, possibly wrapping another
	 *      exception.
	 */
	public void ignorableWhitespace(char[] ch, int start, int length)
			 throws SAXException {
//			System.out.println("ignorableWhitespace: " + new String(ch, start, length));
	}


	/**
	 * Receives notification of a processing instruction. <BR>
	 * <I>Not used here.</I>
	 *
	 * @param target            The processing instruction target.
	 * @param data              The processing instruction data, or null if none
	 *      was supplied. The data does not include any whitespace separating it
	 *      from the target.
	 * @exception SAXException  Any SAX exception, possibly wrapping another
	 *      exception.
	 */
	public void processingInstruction(String target, String data)
			 throws SAXException {
//			System.out.println("processingInstruction: " + target + " | " + data);
	}


	/**
	 * Receives notification of a skipped entity. <BR>
	 * <I>Not used here.</I>
	 *
	 * @param name              The name of the skipped entity. If it is a
	 *      parameter entity, the name will begin with '%', and if it is the
	 *      external DTD subset, it will be the string "[dtd]".
	 * @exception SAXException  Any SAX exception, possibly wrapping another
	 *      exception.
	 */
	public void skippedEntity(String name)
			 throws SAXException {
//			System.out.println("skippedEntity: " + name);
	}


	/**
	 * Ends the scope of a prefix-URI mapping. <BR>
	 * <I>Not used here.</I>
	 *
	 * @param prefix            The prefix that was being mapping.
	 * @exception SAXException  Any SAX exception, possibly wrapping another
	 *      exception.
	 */
	public void endPrefixMapping(String prefix)
			 throws SAXException {
//			System.out.println("endPrefixMapping: " + prefix);
	}

}
