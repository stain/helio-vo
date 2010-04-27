package org.egso.provider.query;

import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.util.Vector;
import javax.activation.DataHandler;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.log4j.Logger;
import org.egso.common.context.EGSOContext;
import org.egso.common.services.provider.ResponseQueryProvider;
import org.egso.provider.admin.ProviderMonitor;
import org.egso.provider.datamanagement.datapresentation.DataPresentationManager;
import org.egso.provider.utils.ProviderUtils;
import org.egso.provider.utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;

public class QueryExecutor extends Thread {

    private DocumentBuilder builder;

    private EGSOContext context = null;

    private String id = null;

    private ResponseQueryProvider notifier = null;

    private Document queryDocument = null;

    private QueryValidator queryValidator = null;

    private DataPresentationManager dataPresentationManager = null;

    private static final int MAXIMAL_RESULTS_DISPLAYED = 100;

    private Logger logger = null;

    public QueryExecutor(String queryID, EGSOContext cxt, String query,
            ResponseQueryProvider responseObject, QueryValidator qv,
            DataPresentationManager dpm) {
        logger = Logger.getLogger(this.getClass().getName());
        id = queryID;
        context = cxt;
        notifier = responseObject;
        queryValidator = qv;
        dataPresentationManager = dpm;
        // Removing the field unique-id if exists.
        int x = query.indexOf("<field name=\"unique-id\"/>");
        if (x != -1) {
            query = query.substring(0, x - 1) + query.substring(x + 25);
        }
        logger.info("Query [id=" + id + "] received:\n" + context.toXML()
                + "\n" + query);
        context.addRoute("Query Reception",
                "The query has been received by the provider");
        // Creates the query as a XML Document.
        try {
            DocumentBuilderFactory facto = DocumentBuilderFactory.newInstance();
            builder = facto.newDocumentBuilder();
            InputSource is = new InputSource(new ByteArrayInputStream(query
                    .getBytes()));
            queryDocument = builder.parse(is);
        } catch (Throwable t) {
            ProviderMonitor.getInstance().reportException(t);
            ProviderUtils.reportException("QueryExecutor", t);
        }
        ProviderMonitor.getInstance().setLastQuery(context + "\n" + query,
                false);
    }

    public void run() {
        long start = System.currentTimeMillis();
        Vector<Node> providerQueries = new Vector<Node>();
        Vector<ProviderTable> results = new Vector<ProviderTable>();
        ProviderTable providerTable = null;
        ProviderTable finalResults = null;
        String resultsAsString = null;
        // Split query according to <group> elements
        providerQueries = splitQuery(queryDocument);

        for (Node tempQuery:providerQueries) {
            // Validate the query.
            //      System.out.println(XMLUtils.nodeToString(n, ""));
            //  Document qdoc = n.getOwnerDocument();
            //  NodeList tempnl = qdoc.getChildNodes();
            // System.out.println(tempnl.getLength());
            //  Object obj = queryValidator.validate((Document) it.next());

            //   System.out.println(XMLUtils.nodeToString(simon, ""));
            Document resultDocument = null;
            try {
                resultDocument = builder
                        .parse(new InputSource(new StringReader(XMLUtils
                                .nodeToString(tempQuery, ""))));
            } catch (Exception e) {
                System.out.println("ERROR PARSING NODE");
            }
            Object obj = queryValidator.validate(resultDocument);
            if (obj instanceof String) {

                // If the validation was refused...
                context.setType(EGSOContext.CONTEXT_RESULT);
                providerTable = new ProviderTable(context, new Vector<String>());
                System.out.println("ERROR DURING VALIDATION: " + obj);
                System.out.println(providerTable.createMessage(true));
                logger.error("Error during the Validation of the query:"
                        + (String) obj);
                ProviderMonitor.getInstance().setLastQueryResult(
                        "Exception during the Validation:<br/>" + (String) obj);
            } else {
                // The validation was successfull.
                logger.info("Query Validated.");
                System.out.println("NEW QUERY:\n"
                        + XMLUtils.documentToString((Document) obj));
                ProviderMonitor.getInstance().setLastQuery(
                        XMLUtils.XMLToHTML((Document) obj), true);
                ProviderQuery pq = new ProviderQuery(ProviderQuery.QUERY,
                        context, (Document) obj);
                //ProviderQuery pq = new ProviderQuery(ProviderQuery.QUERY,
                //        context, n);
                // System.out.println(pq);
                providerTable = dataPresentationManager.query(pq);
                // If no results was found...
                if (providerTable == null) {
                    providerTable = new ProviderTable(context, new Vector<String>());
                } else {
                    results.add(providerTable);
                }
                //			context = providerTable.getContex();
                context.setType(EGSOContext.CONTEXT_RESULT);
                context.addRoute("Query Processed",
                        "The query has been processed by the provider");
                //			result.setContext(cxt);

                int x = providerTable.getNumberOfResults();
                System.out
                        .println("\t\t## QUERY RESULTS HAVE BEEN CALCULATED ##");
                System.out.println(context.toXML());
                resultsAsString = providerTable.createMessage(false);
                if (x > MAXIMAL_RESULTS_DISPLAYED) {
                    logger.info("Number of results found: " + x
                            + ". Results not displayed if more than "
                            + MAXIMAL_RESULTS_DISPLAYED + ".");
                } else {
                    logger.info("Number of results found: " + x + "\n"
                            + resultsAsString);
                }
            }
        }//End for loop
        // Returning response.
        // Merge results

        for (ProviderTable pt:results)
        {
            if (finalResults == null) {
                finalResults = pt;
            } else {
                finalResults.merge(pt);
            }
        }
        if (finalResults == null) {
            resultsAsString = null;
        } else {
            resultsAsString = finalResults.createMessage(false);
        }
        System.out.println("\n\t[[ QUERY EXECUTED in "
                + (System.currentTimeMillis() - start) + "ms ]]\n");
        try {
            if (resultsAsString == null) {
                resultsAsString = providerTable.createMessage(false);
            }
            DataHandler dh = new DataHandler(resultsAsString, "text/xml");
            System.out.println("\t[RESPONSE READY TO BE SENT]...");
            notifier.sendResponse(providerTable.getContext().toXML(), dh);
            System.out.println("\t[RESPONSE SENT SUCCESSFULLY]");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Splits queries containing <group>element into multiple queries not using
     * <group>element so existing provider code can be used to perform queries.
     * 
     * @param queryDocument2
     *            Query from broker
     * @return Multiple queries not containing <group>elements, or input query
     *         if it does not contain <group>element
     */
    private Vector<Node> splitQuery(Document queryDocument2) {
        Node rootElement = queryDocument2.getDocumentElement();
        NodeList nodeList = rootElement.getChildNodes();
        Node dataNode = null; // Represents <data> element
        Node skeleton = null;
        // Get <data> element
        dataNode = getDataNode(nodeList);
        // Get children of <data> and identify <group> nodes/<param nodes>
        NodeList dataNodeList = dataNode.getChildNodes();
        if (getGroupInstances(dataNodeList) == 0) { // No <group> elements
            //queries.add(queryDocument2);
            rootElement = splitNobeyamaNode(rootElement);
            Vector<Node> queries = new Vector<Node>();
            queries.add(rootElement);
            return queries;
        } else { // Contains <group> elements, query needs splitting
            skeleton = getSkeleton(rootElement);
            return split(dataNodeList, skeleton, dataNode);
        }
    }

    /**
     * Splits queries containing <group>elements
     * 
     * @param dataChildren
     *            children of <data>
     * @param skeleton
     *            Outline query without children of <data>
     * @return Multiple queries
     */
    private Vector<Node> split(NodeList dataChildren, Node skeleton, Node dataNode) {
        Vector<Node> queries = new Vector<Node>(); // Contains multiple queries generated
        // from the input
        Node commonParams = null; // common <data><param> elements
        //  Node data = skeleton.getLastChild(); // <data> element with no
        // children
        Node data = dataNode.cloneNode(false);
        Node tmpNode = null;
        // Isolate <param> elements common to all queries i.e. direct children
        // of <data>
        commonParams = data;
        for (int i = 0; i < dataChildren.getLength(); ++i) {
            tmpNode = dataChildren.item(i);
            if ((tmpNode.getNodeType() == Node.ELEMENT_NODE)
                    && (tmpNode.getNodeName().equals("param"))) {
                commonParams.appendChild(tmpNode);
            }
        }

        NodeList groupChildren = null; // Children of <data><group>
        NodeList groupGroupChildren = null; // Children of <data><group><group>
        // ie (<params>)
        Node dataGroupChildNode = null; // <data><group><?> node
        Node cloneCommonParams = null;
        Node groupCloneCommonParams = null;
        Node nestedNode = null;
        Node dataChildNode = null; // Node representing children of <data>
        Node temporary = null;
        // Document tempDoc = null;
        // Loop over all <group> children of <data>
        for (int j = 0; j < dataChildren.getLength(); ++j) {
            dataChildNode = dataChildren.item(j);
            if ((dataChildNode.getNodeType() == Node.ELEMENT_NODE)
                    && (dataChildNode.getNodeName().equals("group"))) {
                // Get children of <group>
                groupChildren = dataChildNode.getChildNodes();
                cloneCommonParams = commonParams.cloneNode(true);
                // Loop over children of <group>
                for (int k = 0; k < groupChildren.getLength(); ++k) {
                    dataGroupChildNode = groupChildren.item(k);
                    if ((dataGroupChildNode.getNodeType() == Node.ELEMENT_NODE)
                            && (dataGroupChildNode.getNodeName()
                                    .equals("param"))) {
                        cloneCommonParams.appendChild(dataGroupChildNode);
                    } else if ((dataGroupChildNode.getNodeType() == Node.ELEMENT_NODE)
                            && (dataGroupChildNode.getNodeName()
                                    .equals("group"))) {
                        groupGroupChildren = dataGroupChildNode.getChildNodes();
                        groupCloneCommonParams = commonParams.cloneNode(true);
                        for (int l = 0; l < groupGroupChildren.getLength(); ++l) {
                            nestedNode = groupGroupChildren.item(l);
                            if ((nestedNode.getNodeType() == Node.ELEMENT_NODE)
                                    && (nestedNode.getNodeName()
                                            .equals("param"))) {
                                // Should be <group><group><param> as no further
                                // nesting currently envisaged
                                groupCloneCommonParams.appendChild(nestedNode);
                            } else {
                                // further nesting not yet supported
                            }
                        }
                        temporary = skeleton.cloneNode(true);
                        temporary.appendChild(groupCloneCommonParams);
                        //  tempDoc = temporary.getOwnerDocument();
                        queries.add(temporary);
                    }
                }
                temporary = skeleton.cloneNode(true);
                temporary.appendChild(cloneCommonParams);
                //  tempDoc = temporary.getOwnerDocument();
                temporary = splitNobeyamaNode(temporary);
                queries.add(temporary);
            }
        }
        return queries;
    }

    /**
     * Get skeleton query with <query>
     * element and its children <select> and <data>. 
     * The <select> element is complete, but the the <data> element has no children. 
     * 
     * @return Skeleton query (<query>)
     */
    private Node getSkeleton(Node query) {
        Node queryClone = null;
        Node selectClone = null;
        queryClone = query.cloneNode(false);
        NodeList queryChildren = query.getChildNodes();
        for (int i = 0; i < queryChildren.getLength(); ++i) {
            if ((queryChildren.item(i).getNodeType() == Node.ELEMENT_NODE)
                    && (queryChildren.item(i).getNodeName().equals("select"))) {
                selectClone = queryChildren.item(i).cloneNode(true);
            } else if ((queryChildren.item(i).getNodeType() == Node.ELEMENT_NODE)
                    && (queryChildren.item(i).getNodeName().equals("data"))) {
                //dataClone = queryChildren.item(i).cloneNode(false);
            }
        }
        queryClone.appendChild(selectClone);
        //  queryClone.appendChild(dataClone);
        return queryClone;
    }

    /**
     * Get <data>element from node list
     * 
     * @param nl
     *            Node list - children of <query>
     * @return <data> node
     */
    private Node getDataNode(NodeList nl) {
        Node tmpNode = null;
        for (int i = 0; i < nl.getLength(); ++i) {
            tmpNode = nl.item(i);
            if ((tmpNode.getNodeType() == Node.ELEMENT_NODE)
                    && (tmpNode.getNodeName().equals("data"))) {
                break;
            }
        }
        return tmpNode;
    }

    /**
     * Find number of <param>children of <data>
     * 
     * @param dataChildren
     *            children of <data>
     * @return Number of <param>children
     */
    @SuppressWarnings("unused")
    private int getParamInstances(NodeList dataChildren) {
        int tmpCount = 0;
        Node tmpNode = null;
        for (int i = 0; i < dataChildren.getLength(); ++i) {
            tmpNode = dataChildren.item(i);
            if ((tmpNode.getNodeType() == Node.ELEMENT_NODE)
                    && (tmpNode.getNodeName().equals("param"))) {
                ++tmpCount;
            }
        }
        return tmpCount;
    }

    /**
     * Find number of <group>children of <data>
     * 
     * @param dataChildren
     *            Children of <data>
     * @return Number of <group>children
     */
    private int getGroupInstances(NodeList dataChildren) {
        int tmpCount = 0;
        Node tmpNode = null;
        for (int i = 0; i < dataChildren.getLength(); ++i) {
            tmpNode = dataChildren.item(i);
            if ((tmpNode.getNodeType() == Node.ELEMENT_NODE)
                    && (tmpNode.getNodeName().equals("group"))) {
                ++tmpCount;
            }
        }
        return tmpCount;
    }

    /**
     * Splits a <param name="instrument"> <value="NOBE__NORH"/></param> node
     * into three for values NOBE__NORH_IFA, NOBE__NORH_IFS, NOBE__NORH_IFZ
     * 
     * @param rootNode
     *            Complete query
     * @return Complete query but with extra <param>elements if NOBE__NORH
     *         instrument is being queried
     */
    private Node splitNobeyamaNode(Node rootNode) {
        Document nobeyamaDocument = rootNode.getOwnerDocument();
        boolean cloneNodes = false; // Indicates whether to split query
        NamedNodeMap nodeMap = null;
        Node paramName = null;
        Node tmpNode = null;
        NodeList rootChildren = rootNode.getChildNodes();
        // Get <data> element
        Node dataNode = getDataNode(rootChildren);
        NodeList dataChildren = dataNode.getChildNodes();
        // Search through children of <data> i.e. <param> elements

        // CHANGE TO <ELEMENT> for attribute methods
        // Get TEXT subclass of NODE to access value
        for (int i = 0; i < dataChildren.getLength(); ++i) {
            tmpNode = dataChildren.item(i);
            if ((tmpNode.getNodeType() == Node.ELEMENT_NODE)
                    && (tmpNode.getNodeName().equals("param"))) {
                nodeMap = tmpNode.getAttributes();
                paramName = nodeMap.getNamedItem("name");
                if (paramName.getNodeValue().equals("instrument")) {
                    NodeList nl1 = tmpNode.getChildNodes();
                    for (int k = 0; k < nl1.getLength(); ++k) {
                        if ((nl1.item(k).getNodeType() == Node.ELEMENT_NODE)
                                && (nl1.item(k).getNodeName().equals("value"))) {

                            Node verytempnode = nl1.item(k).getFirstChild();
                            if (verytempnode.getNodeValue()
                                    .equals("NOBE__NORH")) {
                                cloneNodes = true;
                            }
                        }
                    }
                }
            }
        }
        if (cloneNodes) {
            Element ifa = nobeyamaDocument.createElement("param");
            ifa.setAttribute("name", "instrument");
            Element ifs = nobeyamaDocument.createElement("param");
            ifs.setAttribute("name", "instrument");
            Element ifz = nobeyamaDocument.createElement("param");
            ifz.setAttribute("name", "instrument");
            Element ifaValue = nobeyamaDocument.createElement("value");
            Element ifsValue = nobeyamaDocument.createElement("value");
            Element ifzValue = nobeyamaDocument.createElement("value");
            Text ifatext = nobeyamaDocument.createTextNode("NOBE__NORH_IFA");
            ifaValue.appendChild(ifatext);
            ifa.appendChild(ifaValue);
            Text ifstext = nobeyamaDocument.createTextNode("NOBE__NORH_IFS");
            ifsValue.appendChild(ifstext);
            ifs.appendChild(ifsValue);
            Text ifztext = nobeyamaDocument.createTextNode("NOBE__NORH_IFZ");
            ifzValue.appendChild(ifztext);
            ifz.appendChild(ifzValue);
            dataNode.appendChild(ifa);
            dataNode.appendChild(ifs);
            dataNode.appendChild(ifz);
        }
        return rootNode;
    }
}