package org.egso.votable;

import org.egso.votable.element.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.ByteArrayInputStream;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


/**
 *  The VOTableFactory creates VOTable structures from different kind of input
 *  sources (InputSource, file, String).
 *
 * @author     Romain Linsolas (linsolas@gmail.com)
 * @version    0.9.1 - 03/12/2003 [25/11/2003]
 */
/*
0.9.1 - 03/12/2003: Adding more explicit exception messages.
*/
public class VOTableFactory {

	/**
	 *  Constructor for the VOTableFactory object.
	 */
	private VOTableFactory() {
	}


	/**
	 *  Returns an instance of a VOTableFactory.
	 *
	 * @return                              A new instance of a VOTableFactory.
	 * @exception  VOTableFactoryException  If a problem occurred in the creation
	 *      of the factory.
	 */
	public static VOTableFactory newInstance()
			 throws VOTableFactoryException {
		return (new VOTableFactory());
	}


	/**
	 *  Creates a VOTable object from a String.
	 *
	 * @param  content                      String that contains a VOTable.
	 * @return                              VOTable corresponding to the content of
	 *      the String.
	 * @exception  VOTableFactoryException  If a problem appears during the parsing
	 *      of the String.
	 */
	public VOTableRoot createVOTable(String content)
			 throws VOTableFactoryException {
		VOTableRoot root = null;
		try {
			root = parse(new InputSource(new ByteArrayInputStream(content.getBytes())));
		} catch (SAXException e) {
			throw (new VOTableFactoryException("VOTableFactory: SAXException during the parsing of the String:\n" + e.getMessage()));
		} catch (IOException e) {
			throw (new VOTableFactoryException("VOTableFactory: IOException during the parsing of the String:\n" + e.getMessage()));
		}
		return (root);
	}


	/**
	 *  Creates a VOTable from a file.
	 *
	 * @param  file                         File containing the VOTable.
	 * @return                              VOTable corresponding to the content of
	 *      the file.
	 * @exception  VOTableFactoryException  If a problem appears during the parsing
	 *      of the file.
	 * @exception  FileNotFoundException    If the file doesn't exist.
	 * @exception  SecurityException        If some security problems happens.
	 */
	public VOTableRoot createVOTable(File file)
			 throws VOTableFactoryException, FileNotFoundException, SecurityException {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException fnfe) {
			throw fnfe;
		} catch (SecurityException se) {
			throw se;
		}
		VOTableRoot root = null;
		try {
			root = parse(new InputSource(fis));
		} catch (SAXException e) {
			throw (new VOTableFactoryException("VOTableFactory: SAXException during the parsing of the File " + file.getName() + ":\n" + e.getMessage()));
		} catch (IOException e) {
			throw (new VOTableFactoryException("VOTableFactory: IOException during the parsing of the " + file.getName() + ":\n" + e.getMessage()));
		}
		return (root);
	}


	/**
	 *  Creates a VOTable from an InputSource.
	 *
	 * @param  is                           InputSource containing the VOTable.
	 * @return                              VOTable corresponding to the content of
	 *      the InputSource.
	 * @exception  VOTableFactoryException  If a problem appears during the
	 *      parsing.
	 */
	public VOTableRoot createVOTable(InputSource is) throws VOTableFactoryException {
		VOTableRoot root = null;
		try {
			root = parse(is);
		} catch (SAXException e) {
			throw (new VOTableFactoryException("VOTableFactory: SAXException during the parsing of the InputSource:\n" + e.getMessage()));
		} catch (IOException e) {
			throw (new VOTableFactoryException("VOTableFactory: IOException during the parsing of the InputSource:\n" + e.getMessage()));
		}
		return (root);
	}


	/**
	 *  Parse an InputSource and returns the VOTable contained in this InputSource.
	 *
	 * @param  is                The InputSource that contains the VOTable.
	 * @return                   VOTable.
	 * @exception  SAXException  Exception during the SAX parsing.
	 * @exception  IOException   IO Exception.
	 */
	private VOTableRoot parse(InputSource is) throws SAXException, IOException {
		is.setEncoding("ISO-8859-1");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);
        
        SAXParser saxParser = null;
        try
        {
            saxParser = factory.newSAXParser();
        } catch (ParserConfigurationException e)
        {
            throw new SAXException(e);
        }
        
        VOTableParser parser = new VOTableParser();
        
        try {
			saxParser.parse(is, parser);
		} catch (SAXException se) {
			throw se;
		} catch (IOException ioe) {
			throw ioe;
		}
		return (parser.getVOTable());
	}

}

