package org.egso.provider.utils;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This class regroups some static methods to work on XML nodes.
 * 
 * @author Romain Linsolas (linsolas@gmail.com)
 * @version 0.2.2-jd - 27/05/2004 [28/11/2003]
 */
/*
 * 0.2.2 - 27/05/2004: Adding XMLToHTML to display an XML node in a HTML page.
 * 0.2.1 - 31/03/2004: Little correction about the XML formatting mechanism. "
 * <a>x </a>" replaces " <a> x </a>" which seems to cause problem with some Perl
 * XML Parsers (VSO Web Service). 0.2 - 11/12/2003: Move some operations not
 * directly refering to XML to the ProviderUtils class.
 */
public class XMLUtils {

    /**
     * Code for the font used to display a start node in HTML.
     */
    private final static String HTML_FONT_START_NODE = "<font color=\"#0000FF\" style=\"font-weight: bold;\">";

    /**
     * Code for the font used to display a end node in HTML.
     */
    private final static String HTML_FONT_END_NODE = "<font color=\"#0000FF\">";

    /**
     * Code for the font used to display an attribute name in HTML.
     */
    private final static String HTML_FONT_ATTRIBUTE_NAME = "<font color=\"#228B22\" style=\"font-weight: bold;\">";

    /**
     * Code for the font used to display an attribute value in HTML.
     */
    private final static String HTML_FONT_ATTRIBUTE_VALUE = "<font>";

    /**
     * Code for the font used to display a comment node in HTML.
     */
    private final static String HTML_FONT_COMMENT = "<font color=\"#993399\" style=\"font-style: italic;\">";

    private final static char ESC = 0x1B;

    @SuppressWarnings("unused")
    private final static String black = ESC + "[0;30m";

    @SuppressWarnings("unused")
    private final static String blackBold = ESC + "[1;30m";

    private final static String red = ESC + "[0;31m";

    private final static String redBold = ESC + "[1;31m";

    private final static String green = ESC + "[1;32m";

    @SuppressWarnings("unused")
    private final static String greenBold = ESC + "[1;32m";

    @SuppressWarnings("unused")
    private final static String yellow = ESC + "[0;33m";

    @SuppressWarnings("unused")
    private final static String yellowBold = ESC + "[1;33m";

    private final static String blue = ESC + "[0;34m";

    @SuppressWarnings("unused")
    private final static String blueBold = ESC + "[1;34m";

    /**
     * Returns the String representation of an XML node.
     * 
     * @param n
     *            Node to print.
     * @param indent
     *            The current indentation (a set of spaces/tabulation).
     * @return The String representation of the node.
     */
    public static String nodeToString(Node n, String indent) {
        StringBuffer sb = new StringBuffer(indent);
        boolean inText = false;
        sb.append("<" + n.getNodeName());
        if (n.hasAttributes()) {
            NamedNodeMap nnm = n.getAttributes();
            Node tmp = null;
            for (int i = 0; i < nnm.getLength(); i++) {
                tmp = nnm.item(i);
                sb.append(" " + tmp.getNodeName() + "=\"" + tmp.getNodeValue()
                        + "\"");
            }
        }
        if (n.hasChildNodes()) {
            sb.append(">\n");
            NodeList nl = n.getChildNodes();
            Node tmp = null;
            for (int i = 0; i < nl.getLength(); i++) {
                tmp = nl.item(i);
                if (tmp.getNodeType() == Node.TEXT_NODE) {
                    String x = tmp.getNodeValue().trim();
                    if ((x != null) && (!x.equals(""))) {
                        // Delete the previous '\n' character.
                        sb = sb.delete(sb.length() - 1, sb.length());
                        sb.append(x);
                        inText = true;
                    }
                } else {
                    if (tmp.getNodeType() != Node.COMMENT_NODE) {
                        sb.append(nodeToString(tmp, indent + "  "));
                    }
                }
            }
            sb.append((inText ? "" : indent) + "</" + n.getNodeName() + ">\n");
            inText = false;
        } else {
            sb.append("/>\n");
        }
        return (sb.toString());
    }

    /**
     * Returns the String representation of an XML node.
     * 
     * @param n
     *            Node to print.
     * @return The String representation of an XML node.
     */
    public static String nodeToString(Node n) {
        return (nodeToString(n, ""));
    }

    /**
     * Prints a node in System.out.
     * 
     * @param n
     *            Node to print.
     */
    public static void printNode(Node n) {
        System.out.print(nodeToString(n));
    }

    /**
     * Returns the String representation of an XML document.
     * 
     * @param doc
     *            Document to print.
     * @return The String representation of an XML document.
     */
    public static String documentToString(Document doc) {
        return (nodeToString(doc.getDocumentElement(), ""));
    }

    /**
     * Returns the String representation of an XML document.
     * 
     * @param doc
     *            The String representation of an XML document.
     */
    public static void printDocument(Document doc) {
        System.out.print(documentToString(doc));
    }

    /**
     * Returns the HTML representation of an XML document.
     * 
     * @param doc
     *            Document to print in HTML.
     * @return The HTML representation of the XML document.
     */
    public static String XMLToHTML(Document doc) {
        return (XMLToHTML(doc.getDocumentElement(), ""));
    }

    /**
     * Returns the HTML representation of an XML node.
     * 
     * @param n
     *            Node to print in HTML.
     * @param indent
     *            Current indentation.
     * @return The HTML representation of the XML node.
     */
    private static String XMLToHTML(Node n, String indent) {
        StringBuffer sb = new StringBuffer(indent);
        boolean inText = false;
        sb.append("&lt;" + HTML_FONT_START_NODE + n.getNodeName() + "</font>");
        if (n.hasAttributes()) {
            NamedNodeMap nnm = n.getAttributes();
            Node tmp = null;
            for (int i = 0; i < nnm.getLength(); i++) {
                tmp = nnm.item(i);
                sb.append(" " + HTML_FONT_ATTRIBUTE_NAME + tmp.getNodeName()
                        + "</font>=\"" + HTML_FONT_ATTRIBUTE_VALUE
                        + tmp.getNodeValue() + "</font>\"");
            }
        }
        if (n.hasChildNodes()) {
            sb.append("&gt;<br/>");
            NodeList nl = n.getChildNodes();
            Node tmp = null;
            for (int i = 0; i < nl.getLength(); i++) {
                tmp = nl.item(i);
                if (tmp.getNodeType() == Node.TEXT_NODE) {
                    String x = tmp.getNodeValue().trim();
                    if ((x != null) && (!x.equals(""))) {
                        // Delete the previous '<br/>' character.
                        sb = sb.delete(sb.length() - 5, sb.length());
                        sb.append(x);
                        inText = true;
                    }
                } else {
                    if (tmp.getNodeType() == Node.COMMENT_NODE) {
                        sb.append(indent + HTML_FONT_COMMENT + "&lt;!-- "
                                + tmp.getNodeValue() + "--&gt;</font><br/>");
                    } else {
                        sb
                                .append(XMLToHTML(tmp, indent
                                        + "&nbsp;&nbsp;&nbsp;"));
                    }
                }
            }
            sb.append((inText ? "" : indent) + "&lt;/" + HTML_FONT_END_NODE
                    + n.getNodeName() + "</font>&gt;<br/>");
            inText = false;
        } else {
            sb.append("/&gt;<br/>");
        }
        return (sb.toString());
    }

    /**
     * Returns the HTML representation of an XML node.
     * 
     * @param n
     *            Node to print.
     * @return The HTML representation of an XML node.
     */
    public static String XMLToHTML(Node n) {
        return (XMLToHTML(n, ""));
    }

    /**
     * Returns the String representation of an XML node.
     * 
     * @param n
     *            Node to print.
     * @param indent
     *            The current indentation (a set of spaces/tabulation).
     * @return The String representation of the node.
     */
    @SuppressWarnings("unused")
    private static String coloredNodeToString(Node n, String indent) {
        StringBuffer sb = new StringBuffer(indent);
        boolean inText = false;
        sb.append(green);
        sb.append("<" + redBold + n.getNodeName() + green);
        if (n.hasAttributes()) {
            NamedNodeMap nnm = n.getAttributes();
            Node tmp = null;
            for (int i = 0; i < nnm.getLength(); i++) {
                tmp = nnm.item(i);
                sb.append(" " + blue + tmp.getNodeName() + green + "=\""
                        + tmp.getNodeValue() + "\"");
            }
        }
        if (n.hasChildNodes()) {
            sb.append(">\n");
            NodeList nl = n.getChildNodes();
            Node tmp = null;
            for (int i = 0; i < nl.getLength(); i++) {
                tmp = nl.item(i);
                if (tmp.getNodeType() == Node.TEXT_NODE) {
                    String x = tmp.getNodeValue().trim();
                    if ((x != null) && (!x.equals(""))) {
                        // Delete the previous '\n' character.
                        sb = sb.delete(sb.length() - 1, sb.length());
                        sb.append(x);
                        inText = true;
                    }
                } else {
                    if (tmp.getNodeType() != Node.COMMENT_NODE) {
                        sb.append(nodeToString(tmp, indent + "  "));
                    }
                }
            }
            sb.append((inText ? "" : indent) + "</" + red + n.getNodeName()
                    + green + ">\n");
            inText = false;
        } else {
            sb.append("/>\n");
        }
        return (sb.toString());
    }

}

