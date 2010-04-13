package org.egso.provider.utils;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.TransformerException;

import org.apache.xpath.XPathAPI;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



/**
 * XMLTools provides SAX Parsers instances as well as a basic interface for simple
 * XPath queries.
 *
 * @author    Romain Linsolas (linsolas@gmail.com)
 * @version   1.0 - 26/10/2004
 */
/*
1.0 - 26/10/2004:
	First version of the XMLTools.
*/
public class XMLTools {

	/**
	 * SAX Parser Factory that is used to create new SAX Parser instances.
	 */
	private static SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
	/**
	 * Instance of the XMLTools (used for XPath queries).
	 */
	private static XMLTools xmlTools = null;


	/**
	 * Initialization of the XMLTools.
	 */
	private XMLTools() {
		System.out.println("[XMLTools] Initialization");
	}


	/**
	 * Gets the instance of the XMLTools. The instanciation of the XMLTools may
	 * be done if no previous instance exists.
	 *
	 * @return   The instance of the XMLTools.
	 */
	public static XMLTools getInstance() {
		if (xmlTools == null) {
			xmlTools = new XMLTools();
		}
		return (xmlTools);
	}


	/**
	 * Creates a new instance of a SAX Parser.
	 *
	 * @return                                  A SAX Parser.
	 * @exception ParserConfigurationException  If a parser cannot be created which satisfies the requested configuration.
	 * @exception SAXException                  If a SAX Exception occurs during the creation of the Parser.
	 */
	public static SAXParser getSAXParser()
			 throws ParserConfigurationException, SAXException {
		return (saxParserFactory.newSAXParser());
	}


	/**
	 * Executes a given XPath query to find a single node.
	 *
	 * @param node   The node to start searching from.
	 * @param query  The XPath query.
	 * @return       The first node found that matches the XPath query, or null.
	 */
	public Node selectSingleNode(Node node, String query)
		throws TransformerException {
		return (XPathAPI.selectSingleNode(node, query));
/*
		PrefixResolver prefixResolver = new PrefixResolverDefault((node.getNodeType() == Node.DOCUMENT_NODE) ? ((Document) node).getDocumentElement() : node);
		XObject result = null;
		try {
			XPath xpath = new XPath(query, null, prefixResolver, XPath.SELECT, null);
			int ctxtNode = xpathSupport.getDTMHandleFromNode(node);
			result = xpath.execute(xpathSupport, ctxtNode, prefixResolver);
			return (result.nodeset().nextNode());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (null);
*/
	}


	/**
	 * Executes a given XPath query to find a list of nodes.
	 *
	 * @param node   The node to start searching from.
	 * @param query  The XPath query.
	 * @return       The list of nodes found that matches the XPath query, or null.
	 */
	public NodeList selectNodeList(Node node, String query)
		throws TransformerException {
		return (XPathAPI.selectNodeList(node, query));
/*
		PrefixResolver prefixResolver = new PrefixResolverDefault((node.getNodeType() == Node.DOCUMENT_NODE) ? ((Document) node).getDocumentElement() : node);
		XObject result = null;
		try {
			XPath xpath = new XPath(query, null, prefixResolver, XPath.SELECT, null);
			int ctxtNode = xpathSupport.getDTMHandleFromNode(node);
			result = xpath.execute(xpathSupport, ctxtNode, prefixResolver);
			return (result.nodelist());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (null);
*/
	}


}

