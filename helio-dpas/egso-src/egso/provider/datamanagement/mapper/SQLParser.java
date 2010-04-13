package org.egso.provider.datamanagement.mapper;

import java.util.Stack;

import org.egso.provider.datamanagement.archives.Base;
import org.egso.provider.datamanagement.archives.MapElement;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * SQL Parser that creates the part WHERE of the SQL Query from the EGSO Query.
 * 
 * @author Romain Linsolas (linsolas@gmail.com)
 * @version 2.1 - 26/10/2004
 */
/*
 * 2.1 - 26/10/2004: Adaptation to the SAX Parser instead of the Xerces SAX
 * Parser. 2.0 - 20/10/2004: Recreation of the SQL Parser using the new SQL Base
 * mapping.
 */
public class SQLParser extends DefaultHandler {

    /**
     * All Param objects processed.
     */
    private Stack<Param> paramStack = null;

    /**
     * The Param object that is currently processed.
     */
    private Param currentParam = null;

    /**
     * All nodes processed.
     */
    private Stack<String> nodeStack = null;

    /**
     * Default relation between parameters values (AND/OR).
     */
    private String defaultRelation = null;

    /**
     * If the default relation value is not defined in the query itself, this
     * constant is used.
     */
    private final String DEFAULT_RELATION = "AND";

    /**
     * Code for a DATA node.
     */
    private final int DATA = 0;

    /**
     * Code for a PARAM node.
     */
    private final int PARAM = 1;

    /**
     * Code for a VALUE node.
     */
    private final int VALUE = 2;

    /**
     * Code for an INTERVAL node.
     */
    private final int INTERVAL = 3;

    /**
     * Code for a START node.
     */
    private final int START = 4;

    /**
     * Code for an END node.
     */
    private final int END = 5;

    /**
     * List of tag names for XML nodes.
     */
    private final String[] tagNames = { "data", "param", "value", "interval",
            "start", "end" };

    /**
     * List of accepted tag names, depending on the name of the root node (e.g.
     * "start" and "end" nodes are accepted only if the root node is the
     * "interval" node).
     */
    private final String[][] acceptedList = { { "param" },
            { "param", "value", "interval" }, {}, { "start", "end" }, {}, {} };

    /**
     * List of accepted nodes, depending on the current processed node.
     */
    private String[] acceptedNodes = null;

    /**
     * Boolean that indicated if an XML text is expected.
     */
    private boolean textExpected = false;

    /**
     * Boolean that indicates if a value has been found (useful for the
     * processing of interval nodes).
     */
    private boolean gotTheValue = false;

    /**
     * Integer used to check the integrity of intervals (i.e. if there is as
     * many of BEGIN that END).
     */
    private int intervalStartEnd = 0;

    /**
     * Name of the current node.
     */
    private String currentNode = null;

    /**
     * JAVADOC: Description of the Field
     */
    private boolean ignoreParam = false;

    /**
     * JAVADOC: Description of the Field
     */
    private Base sqlBase = null;

    //	private StringBuffer temporateValue = null;

    // *********** TO DO ************
    // Modify the code to use the temporateValue that avoid the split of values
    // in XML text parts.

    /**
     * Constructor of the SQLParser.
     * 
     * @param b
     *            JAVADOC: Description of the Parameter
     */
    public SQLParser(Base b) {
        sqlBase = b;
        init();
    }

    /**
     * Receives an object for locating the origin of SAX document events. <BR>
     * <i>Not used here. </i>
     * 
     * @param locator
     *            An object that can return the location of any SAX document
     *            event.
     */
    public void setDocumentLocator(Locator locator) {
        //		System.out.println("setDocumentLocator");
    }

    /**
     * JAVADOC: Gets the query attribute of the SQLParser object
     * 
     * @return JAVADOC: The query value
     */
    public String getQuery() {
        return (currentParam.finish());
    }

    /**
     * Gets the code that corresponds to the tag name (name of the XML node).
     * 
     * @param localName
     *            Name of the node.
     * @return The code of the tag.
     */
    private int getTag(String qName) {
        int i = 0;
        boolean found = false;
        while (!found & (i < tagNames.length)) {
            found = qName.equals(tagNames[i]);
            i++;
        }
        if (!found) {
            return (-1);
        }
        return (i - 1);
    }

    /**
     * JAVADOC: Description of the Method
     */
    private void init() {
        acceptedNodes = new String[] { "data" };
        paramStack = new Stack<Param>();
        nodeStack = new Stack<String>();
    }

    /**
     * Processes character data.
     * 
     * @param ch
     *            The characters from the XML document.
     * @param start
     *            The start position in the array.
     * @param length
     *            The number of characters to read from the array.
     * @exception SAXException
     *                Any SAX exception, possibly wrapping another exception.
     */
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        //		System.out.println("characters: " + new String(ch, start, length));
        if (ignoreParam) {
            return;
        }
        String tmp = (new String(ch, start, length)).trim();
        if (!tmp.equals("")) {
            if (textExpected) {
                // Text expected. The parent node is <value>, <start> or
                // <end>...
                gotTheValue = true;
                if (currentNode.equals("value")) {
                    currentParam.addValue(tmp);
                } else {
                    if (currentNode.equals("start")) {
                        currentParam.addStartIntervalValue(tmp);
                    } else {
                        if (currentNode.equals("end")) {
                            currentParam.addEndIntervalValue(tmp);
                        } else {
                            System.out
                                    .println("ERROR: Don't know what to do with these characters: '"
                                            + tmp + "'.");
                        }
                    }
                }
                textExpected = false;
            } else {
                System.out.println("WARNING: Text (" + tmp
                        + ") not expected here [parent node = " + currentNode
                        + "]");
            }
        }
    }

    /**
     * Receives notification of the end of a document.
     * 
     * @exception SAXException
     *                Any SAX exception, possibly wrapping another exception.
     */
    public void endDocument() throws SAXException {
        System.out.println("...End of the document");
        if (currentParam == null) {
            System.out.println("ERROR, currentParam == null");
        } else {
            if (!currentParam.isDataNode()) {
                System.out.println("ERROR, currentParam != dataNode: "
                        + currentParam.getName());
            }
        }
    }

    /**
     * Receives notification of the end of an element.
     * 
     * @param namespaceURI
     *            The namespace URI.
     * @param localName
     *            The local name (without prefix), or the empty String if
     *            Namespace processing is not being performed.
     * @param qName
     *            The qualified XML 1.0 name (with prefix), or the empty String
     *            if qualified names are not available.
     * @exception SAXException
     *                Any SAX exception, possibly wrapping another exception.
     */
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        //		System.out.println("endElement: </" + localName + ">");
        if (ignoreParam && !qName.equals("param")) {
            return;
        }
        switch (getTag(qName)) {
        case PARAM:
            if (ignoreParam) {
                ignoreParam = false;
            } else {
                ((Param) paramStack.peek()).addChildParam(currentParam);
                currentParam = (Param) paramStack.pop();
            }
            break;
        // Same processing for VALUE, START and END.
        case VALUE:
        case START:
        case END:
            if (!gotTheValue) {
                System.out.println("ERROR: No value (text) for a " + qName
                        + " node!");
            }
            gotTheValue = false;
            break;
        case INTERVAL:
            if (intervalStartEnd != 0) {
                System.out
                        .println("ERROR: INTERVAL node has the good number of children: Node "
                                + ((intervalStartEnd < 0) ? "<END>" : "<START>")
                                + " is not present.");
            }
            intervalStartEnd = 0;
            break;
        }
        currentNode = (String) nodeStack.pop();
        if (currentNode != null) {
            acceptedNodes = acceptedList[getTag(currentNode)];
        }
    }

    /**
     * Receives notification of the beginning of the document.
     * 
     * @exception SAXException
     *                Any SAX exception, possibly wrapping another exception.
     */
    public void startDocument() throws SAXException {
        //		System.out.println("Start of the Document...");
        init();
    }

    /**
     * Receives notification of the beginning of an element.
     * 
     * @param namespaceURI
     *            The namespace URI.
     * @param localName
     *            The local name (without prefix), or the empty String if
     *            Namespace processing is not being performed.
     * @param qName
     *            The qualified name (with prefix), or the empty String if
     *            qualified names are not available.
     * @param atts
     *            The attributes attached to the element. If there are no
     *            attributes, it shall be an empty Attributes object.
     * @exception SAXException
     *                Any SAX exception, possibly wrapping another exception.
     */
    public void startElement(String namespaceURI, String localName,
            String qName, Attributes atts) throws SAXException {
        if (ignoreParam) {
            return;
        }
        //		System.out.println("startElement: <" + localName + ">");
        boolean found = false;
        int i = 0;
        // Test if the start element is correct.
        while (!found && (i < acceptedNodes.length)) {
            found = qName.equals(acceptedNodes[i]);
            i++;
        }
        if (!found) {
            System.out.println("ERROR: The " + qName
                    + " element is not expected here. The parent element is "
                    + currentNode + ".");
            return;
        }
        if (textExpected) {
            System.out.println("ERROR: A text node was expected here!");
            return;
        }
        // Replace the currentNode value by the current node, and push the old
        // one in the Stack.
        nodeStack.push(currentNode);
        currentNode = qName;
        int tag = getTag(qName);
        acceptedNodes = acceptedList[tag];
        String relation = null;
        MapElement elt = null;
        switch (tag) {
        case PARAM:
            String name = atts.getValue("name");
            elt = sqlBase.getMapElement(name);
            if (elt == null) {
                ignoreParam = true;
                System.out.println("IGNORING " + name);
            } else {
                paramStack.push(currentParam);
                relation = atts.getValue("relation");
                if (relation == null) {
                    relation = defaultRelation;
                }
                currentParam = new Param(sqlBase.getMapElement(name), relation);
            }
            break;
        case VALUE:
            textExpected = true;
            break;
        case INTERVAL:
            textExpected = false;
            gotTheValue = false;
            // When we will reach the </INTERVAL>, we must have
            // intervalStartEnd==0,
            // which means that we have one node <START> and one node <END>.
            intervalStartEnd = 0;
            break;
        case START:
            textExpected = true;
            gotTheValue = false;
            intervalStartEnd--;
            break;
        case END:
            textExpected = true;
            gotTheValue = false;
            intervalStartEnd++;
            break;
        case DATA:
            defaultRelation = (String) atts.getValue("default-relation");
            if (defaultRelation == null) {
                defaultRelation = DEFAULT_RELATION;
            }
            //relation = atts.getValue("relation");
            relation = null;
            if (relation == null) {
                relation = defaultRelation;
            }
            currentParam = new Param(relation);
            break;
        default:
            System.out.println("ERROR: Node " + qName + " not supported.");
            break;
        }
    }

    /**
     * Begins the scope of a prefix-URI Namespace mapping. <BR>
     * <I>Not used here. </I>
     * 
     * @param prefix
     *            The Namespace prefix being declared.
     * @param uri
     *            The Namespace URI the prefix is mapped to.
     * @exception SAXException
     *                Any SAX exception, possibly wrapping another exception.
     */
    public void startPrefixMapping(String prefix, String uri)
            throws SAXException {
        //			System.out.println("startPrefixMapping: " + prefix + " | " + uri);
    }

    /**
     * Receives notification of ignorable whitespace in element content. <BR>
     * <I>Not used here. </I>
     * 
     * @param ch
     *            The characters from the XML document.
     * @param start
     *            The start position in the array.
     * @param length
     *            The number of characters to read from the array.
     * @exception SAXException
     *                Any SAX exception, possibly wrapping another exception.
     */
    public void ignorableWhitespace(char[] ch, int start, int length)
            throws SAXException {
        //			System.out.println("ignorableWhitespace: " + new String(ch, start,
        // length));
    }

    /**
     * Receives notification of a processing instruction. <BR>
     * <I>Not used here. </I>
     * 
     * @param target
     *            The processing instruction target.
     * @param data
     *            The processing instruction data, or null if none was supplied.
     *            The data does not include any whitespace separating it from
     *            the target.
     * @exception SAXException
     *                Any SAX exception, possibly wrapping another exception.
     */
    public void processingInstruction(String target, String data)
            throws SAXException {
        //			System.out.println("processingInstruction: " + target + " | " +
        // data);
    }

    /**
     * Receives notification of a skipped entity. <BR>
     * <I>Not used here. </I>
     * 
     * @param name
     *            The name of the skipped entity. If it is a parameter entity,
     *            the name will begin with '%', and if it is the external DTD
     *            subset, it will be the string "[dtd]".
     * @exception SAXException
     *                Any SAX exception, possibly wrapping another exception.
     */
    public void skippedEntity(String name) throws SAXException {
        //			System.out.println("skippedEntity: " + name);
    }

    /**
     * Ends the scope of a prefix-URI mapping. <BR>
     * <I>Not used here. </I>
     * 
     * @param prefix
     *            The prefix that was being mapping.
     * @exception SAXException
     *                Any SAX exception, possibly wrapping another exception.
     */
    public void endPrefixMapping(String prefix) throws SAXException {
        //			System.out.println("endPrefixMapping: " + prefix);
    }

}

