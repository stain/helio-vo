package org.egso.provider.query;

import java.io.FileInputStream;
import java.util.Iterator;
import java.util.Vector;

import org.apache.xerces.parsers.DOMParser;
import org.egso.provider.ProviderConfiguration;
import org.egso.provider.admin.ProviderMonitor;
import org.egso.provider.utils.XMLTools;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class QueryValidator {

    private final static int IF = 0;

    private final static int IFNOT = 1;

    private final static int THEN = 2;

    private final static int ELSE = 3;

    private final static int EXIST_NODE = 4;

    private final static int ADD_NODE = 5;

    private final static int DELETE_NODE = 6;

    private final static int RETURN_ERROR = 7;

    private final static int ADD_ATTRIBUTE = 8;

    private final static int DELETE_ATTRIBUTE = 9;

    private Vector<Node> rules = null;

    private String errorMessage = null;

    private XMLTools xmlTools = null;

    public QueryValidator() {
        System.out.println("[Query Validator] Initialization.");
        init();
    }

    private void init() {
        xmlTools = XMLTools.getInstance();
        rules = new Vector<Node>();
        try {
            InputSource in = new InputSource(new FileInputStream(
                    (String) ProviderConfiguration.getInstance().getProperty(
                            "core.validation")));
            DOMParser parser = new DOMParser();
            parser.parse(in);
            Node root = parser.getDocument().getDocumentElement();
            NodeList children = root.getChildNodes();
            Node n = null;
            for (int i = 0; i < children.getLength(); i++) {
                n = children.item(i);
                if ((n.getNodeType() == Node.ELEMENT_NODE)
                        && (n.getNodeName().equals("rule"))) {
                    rules.add(n);
                }
            }
        } catch (Exception e) {
            ProviderMonitor.getInstance().reportException(e);
            e.printStackTrace();
        }
    }

    public Object validate(Document document) {
        document = deleteParamRedundancies(document);
        //  System.out.println(XMLUtils.nodeToString(root, ""));
        for (Node rule:rules)
        {
            if (!executeRule(rule, document)) {
                return (errorMessage);
            }
        }
        //		System.out.println("\n\nEND OF THE QUERY VALIDATION\n\n");
        return (document);
    }

    private boolean executeRule(Node rule, Document document) {
        //		System.out.println("=== EXECUTING RULE ===");
        //		System.out.print(XMLUtils.nodeToString(rule));
        //		System.out.println("======================");
        //		System.out.println("Executing rule '" +
        // rule.getAttributes().getNamedItem("id").getNodeValue() + "'");
        Node n = null;
        NodeList children = rule.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            n = children.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE) {
                switch (operation(n.getNodeName())) {
                case IF:
                    if (!executeIf(n, document, true)) {
                        return (false);
                    }
                    break;
                case IFNOT:
                    if (!executeIf(n, document, false)) {
                        return (false);
                    }
                    break;
                }
            }
        }
        return (true);
    }

    private boolean executeIf(Node condition, Document document, boolean ifOrNot) {
        //		System.out.println("Execution of if" + (ifOrNot ? "." : "-not."));
        NamedNodeMap atts = condition.getAttributes();
        Node query = document.getDocumentElement();
        switch (operation(atts.getNamedItem("expression").getNodeValue())) {
        case EXIST_NODE:
            try {
                //						System.out.println("Exist-node found.");
                // If ifOrNot (false for <if-not>, true for <if>) is equals to
                // the evaluation of the condition,
                // then we look for the node <then>, else, for the node <else>.
                String go = (ifOrNot == (xmlTools.selectSingleNode(query, atts
                        .getNamedItem("xpath").getNodeValue()) != null)) ? "then"
                        : "else";
                /*
                 * System.out.println("ifOrNot: " + ifOrNot);
                 * System.out.println("XPath (" +
                 * atts.getNamedItem("xpath").getNodeName() + "): ");
                 * System.out.println(xmlTools.selectSingleNode(query,
                 * atts.getNamedItem("xpath").getNodeValue()) != null);
                 * System.out.println("Looking for the node " + go);
                 */
                Node n = null;
                NodeList nl = condition.getChildNodes();
                for (int i = 0; i < nl.getLength(); i++) {
                    n = nl.item(i);
                    if ((n.getNodeType() == Node.ELEMENT_NODE)
                            && (n.getNodeName().equals(go))) {
                        //								System.out.println("-> FOUND !");
                        NodeList nl2 = n.getChildNodes();
                        Node n2 = null;
                        for (int j = 0; j < nl2.getLength(); j++) {
                            n2 = nl2.item(j);
                            if ((n2.getNodeType() == Node.ELEMENT_NODE)
                                    && (n2.getNodeName().equals("operation"))) {
                                if (!executeOperation(n2, document)) {
                                    return (false);
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                ProviderMonitor.getInstance().reportException(e);
                e.printStackTrace();
            }
            break;
        default:
            System.out
                    .println("[QUERYVALIDATOR] - Can't manage the expression '"
                            + atts.getNamedItem("expression").getNodeValue()
                            + "' for IF.");
            break;
        }
        return (true);
    }

    private boolean executeOperation(Node op, Document document) {
        //		System.out.println("Execution of the operation '" +
        // op.getAttributes().getNamedItem("name").getNodeValue() + "'.");
        Node query = document.getDocumentElement();
        switch (operation(op.getAttributes().getNamedItem("name")
                .getNodeValue())) {
        case ADD_NODE:
            NodeList nl = op.getChildNodes();
            Node n = null;
            String param = op.getAttributes().getNamedItem("param")
                    .getNodeValue();
            for (int i = 0; i < nl.getLength(); i++) {
                n = nl.item(i);
                if (n.getNodeType() == Node.ELEMENT_NODE) {
                    try {
                        Node parent = xmlTools.selectSingleNode(query, param);
                        parent.appendChild(document.importNode(n, true));
                    } catch (Exception e) {
                        ProviderMonitor.getInstance().reportException(e);
                        e.printStackTrace();
                    }
                }
            }
            break;
        case DELETE_NODE:
            try {
                n = xmlTools.selectSingleNode(query, op.getAttributes()
                        .getNamedItem("node").getNodeValue());
                if (n != null) {
                    //							System.out.println("Removing node :\n" +
                    // XMLUtils.nodeToString(n));
                    n = document.importNode(n, true);
                    query.removeChild(n);
                } else {
                    System.out
                            .println("[Query Validator] ERROR: The node to be removed was not found (is null).");
                }
            } catch (Exception e) {
                ProviderMonitor.getInstance().reportException(e);
                //						e.printStackTrace();
            }
            break;
        case ADD_ATTRIBUTE:
            //					System.out.println("ADDING AN ATTRIBUTE");
            try {
                NamedNodeMap atts = op.getAttributes();
                Attr attr = document.createAttribute(atts.getNamedItem(
                        "attribute").getNodeValue());
                attr.setValue(atts.getNamedItem("value").getNodeValue());
                NodeList parents = xmlTools.selectNodeList(query, atts
                        .getNamedItem("node").getNodeValue());
                for (int i = 0; i < parents.getLength(); i++) {
                    parents.item(i).getAttributes().setNamedItem(attr);
                }
            } catch (Exception e) {
                ProviderMonitor.getInstance().reportException(e);
                e.printStackTrace();
            }
            break;
        case RETURN_ERROR:
            nl = op.getChildNodes();
            n = null;
            errorMessage = "";
            for (int i = 0; i < nl.getLength(); i++) {
                n = nl.item(i);
                if (n.getNodeType() == Node.TEXT_NODE) {
                    errorMessage += n.getNodeValue().trim();
                }
            }
            // Returns a direct response.
            return (false);
        default:
            System.out
                    .println("[QUERYVALIDATOR] - Can't manage the operation '"
                            + op.getAttributes().getNamedItem("name")
                                    .getNodeValue() + "'.");
            break;
        }
        return (true);
    }

    private int operation(String op) {
        //		System.out.println("OPERATION for " + op);
        if (op.equals("if")) {
            return (IF);
        }
        if (op.equals("if-not")) {
            return (IFNOT);
        }
        if (op.equals("then")) {
            return (THEN);
        }
        if (op.equals("else")) {
            return (ELSE);
        }
        if (op.equals("exist-node")) {
            return (EXIST_NODE);
        }
        if (op.equals("add-node")) {
            return (ADD_NODE);
        }
        if (op.equals("add-attribute")) {
            return (ADD_ATTRIBUTE);
        }
        if (op.equals("delete-node")) {
            return (DELETE_NODE);
        }
        if (op.equals("delete-attribute")) {
            return (DELETE_ATTRIBUTE);
        }
        if (op.equals("return-error")) {
            return (RETURN_ERROR);
        }
        /*
         * if (op.equals("")) { return (); }
         */
        return (-1);
    }

    private Document deleteParamRedundancies(Document doc) {
        try {
            
            //TODO: fix this code
            if(1==1)
              throw new RuntimeException("FIXME FIXME FIXME");
          
            //			System.out.println("=#= Deleting redundancies =#=");
            NodeList params = xmlTools.selectNodeList(doc.getDocumentElement(),
                    "//param");
            Node n = null;
            Node n2 = null;
            Node n3 = null;
            Node n4 = null;
            NodeList nl = null;
            NodeList nl2 = null;
            NodeList nl3 = null;
            String val = null;
            // Test all <param> nodes.
            for (int i = 0; i < params.getLength(); i++) {
                n = params.item(i);
                Vector values = new Vector();
                //				System.out.println("Processing the PARAM " +
                // n.getAttributes().getNamedItem("name").getNodeValue());
                nl = n.getChildNodes();
                Vector<Node> nodesToDelete = new Vector<Node>();
                for (int j = 0; j < nl.getLength(); j++) {
                    n2 = nl.item(j);
                    boolean delete = false;
                    if (n2.getNodeType() == Node.ELEMENT_NODE) {
                        if (n2.getNodeName().equals("value")) {
                            val = "";
                            nl2 = n2.getChildNodes();
                            for (int k = 0; k < nl2.getLength(); k++) {
                                n3 = nl2.item(k);
                                if (n3.getNodeType() == Node.TEXT_NODE) {
                                    val += n3.getNodeValue().trim();
                                }
                                if (!values.contains(val)) {
                                    values.add(val);
                                } else {
                                    //									System.out.println("The value '" + val +
                                    // "' will be deleted.");
                                    nodesToDelete.add(n2);
                                }
                            }
                        } else {
                            if (n2.getNodeName().equals("interval")) {
                                nl2 = n2.getChildNodes();
                                String[] tmp = new String[2];
                                for (int k = 0; k < nl2.getLength(); k++) {
                                    n3 = nl2.item(k);
                                    if (n3.getNodeType() == Node.ELEMENT_NODE) {
                                        nl3 = n3.getChildNodes();
                                        val = "";
                                        for (int l = 0; l < nl3.getLength(); l++) {
                                            n4 = nl3.item(l);
                                            if (n4.getNodeType() == Node.TEXT_NODE) {
                                                val += n4.getNodeValue().trim();
                                            }
                                        }
                                        tmp[n3.getNodeName().equals("start") ? 0
                                                : 1] = val;
                                    }
                                }
                                Iterator<String[]> it = values.iterator();
                                String[] tmp2 = null;
                                while (it.hasNext() && (tmp != null)) {
                                    tmp2 = (String[]) it.next();
                                    if (tmp[0].equals(tmp2[0])
                                            && tmp[1].equals(tmp2[1])) {
                                        //										System.out.println("The value [" +
                                        // tmp[0] + " - " + tmp[1] + "] will be
                                        // deleted!");
                                        nodesToDelete.add(n2);
                                        tmp = null;
                                    }
                                }
                                if (tmp != null) {
                                    values.add(tmp);
                                }
                            } else {
                                System.out.println("[QUERYVALIDATOR] ERROR-> "
                                        + n2.getLocalName());
                            }
                        }
                    }
                }
                
                for (Node d:nodesToDelete)
                    n.removeChild(d);
            }
            //			System.out.println("=#= Redundancies deleted =#=");
        } catch (Exception e) {
            ProviderMonitor.getInstance().reportException(e);
            e.printStackTrace();
        }
        return (doc);
    }

}

