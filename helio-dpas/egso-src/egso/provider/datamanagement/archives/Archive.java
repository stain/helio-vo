package org.egso.provider.datamanagement.archives;

import java.io.FileInputStream;

import org.apache.xerces.parsers.DOMParser;
import org.egso.provider.ProviderConfiguration;
import org.egso.provider.admin.ProviderMonitor;
import org.egso.provider.datamanagement.archives.mapping.MappingTable;
import org.egso.provider.utils.ProviderUtils;
import org.egso.provider.utils.XMLTools;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class Archive {

    protected String id = null;

    protected String name = null;

    protected String url = null;

    protected int port = -1;

    protected Node confNode = null;

    protected String confFile = null;

    protected String[] observatories = null;

    protected int type = 0;

    protected int mapperType = 0;

    protected int connectorType = 0;

    protected int state = STATE_STOPPED;

    protected String[] typeNames = { "UNDEFINED", "FTP", "SQL", "WEBSERVICE",
            "MIXED", "HTTP" };

    protected String[] stateNames = { "Stopped", "Running", "Paused", "Error" };

    protected String lastAccess = null;

    protected int nbAccesses = 0;

    protected MappingTable mappingTable = null;

    public final static int FTP_ARCHIVE = 1;

    public final static int SQL_ARCHIVE = 2;

    public final static int WEB_SERVICES_ARCHIVE = 3;

    public final static int MIXED_ARCHIVE = 4;

    public final static int HTTP_ARCHIVE = 5;

    public final static int STATE_STOPPED = 0;

    public final static int STATE_RUNNING = 1;

    public final static int STATE_PAUSED = 2;

    public final static int STATE_ERROR = 3;

    public final static int FTP_MAPPER = FTP_ARCHIVE;

    public final static int SQL_MAPPER = SQL_ARCHIVE;

    public final static int WEB_SERVICES_MAPPER = WEB_SERVICES_ARCHIVE;

    public final static int HTTP_MAPPER = HTTP_ARCHIVE;

    public final static int FTP_CONNECTOR = FTP_ARCHIVE;

    public final static int SQL_CONNECTOR = SQL_ARCHIVE;

    public final static int WEB_SERVICES_CONNECTOR = WEB_SERVICES_ARCHIVE;

    public final static int HTTP_CONNECTOR = HTTP_ARCHIVE;

    public Archive(int archiveType, Node n) {
        type = archiveType;
        init(n);
        //		System.out.println("Creation of a " + typeNames[type] + " archive ["
        // + id + "].");
        state = STATE_RUNNING;
    }

    // Parse the conf file in order to retrieve all parameter of the archive.
    private void init(Node n) {
        XMLTools xmlTools = XMLTools.getInstance();
        NodeList nl = n.getChildNodes();
        Node tmp = null;
        NamedNodeMap atts = null;
        for (int i = 0; i < nl.getLength(); i++) {
            tmp = nl.item(i);
            if (tmp.getNodeType() == Node.ELEMENT_NODE) {
                atts = tmp.getAttributes();
                if (tmp.getNodeName().equals("description")) {
                    try {
                        id = atts.getNamedItem("ID").getNodeValue();
                        name = atts.getNamedItem("name").getNodeValue();
                        String typeInXML = atts.getNamedItem("type")
                                .getNodeValue();
                        if (!typeInXML.toUpperCase().equals(typeNames[type])) {
                            //							System.out.println("Archive " + id + " is defined
                            // as a " + typeInXML + " archive in the conf file,
                            // but has been initialized as a " + typeNames[type]
                            // + ".");
                        }
                        // Handling MIXED archive.
                        if (type == MIXED_ARCHIVE) {
                            Node tempo = atts.getNamedItem("connector");
                            if (tempo != null) {
                                typeInXML = tempo.getNodeValue().toUpperCase();
                                if (typeInXML.equals("SQL")) {
                                    connectorType = SQL_CONNECTOR;
                                } else {
                                    if (typeInXML.equals("FTP")) {
                                        connectorType = FTP_CONNECTOR;
                                    } else {
                                        connectorType = WEB_SERVICES_CONNECTOR;
                                    }
                                }
                            } else {
                                System.out
                                        .println("A MIXED Archive must specify the type of connector.");
                            }
                            tempo = atts.getNamedItem("mapper");
                            if (tempo != null) {
                                typeInXML = tempo.getNodeValue().toUpperCase();
                                if (typeInXML.equals("SQL")) {
                                    mapperType = SQL_MAPPER;
                                } else {
                                    if (typeInXML.equals("FTP")) {
                                        mapperType = FTP_MAPPER;
                                    } else {
                                        mapperType = WEB_SERVICES_MAPPER;
                                    }
                                }
                            } else {
                                System.out
                                        .println("A MIXED Archive must specify the type of connector.");
                            }
                        }
                    } catch (Exception e) {
                        ProviderMonitor.getInstance().reportException(e);
                        System.out
                                .println("Error in the creation of the Archive Object:");
                        e.printStackTrace();
                    }
                } else {
                    if (tmp.getNodeName().equals("files")) {
                        try {
                            confFile = ((String) ProviderConfiguration
                                    .getInstance().getProperty(
                                            "core.archives-directory"))
                                    + atts.getNamedItem("conf").getNodeValue();
                            InputSource in = new InputSource(
                                    new FileInputStream(confFile));
                            DOMParser parser = new DOMParser();
                            parser.parse(in);
                            confNode = parser.getDocument()
                                    .getDocumentElement();
                        } catch (Exception e) {
                            ProviderMonitor.getInstance().reportException(e);
                            System.out
                                    .println("Error in the creation of the Archive Object:");
                            e.printStackTrace();
                        }
                    } else {
                        if (!tmp.getNodeName().equals("connexion")) {
                            System.out
                                    .println("Node '"
                                            + tmp.getNodeName()
                                            + "' not accepted as a <archive> node children.");
                        }
                    }
                }
            }
        }
        try {
            tmp = xmlTools.selectSingleNode(confNode, "//map/info/observatory");
            if (tmp != null) {
                if (tmp.getAttributes().getNamedItem("name") != null) {
                    observatories = new String[1];
                    observatories[0] = tmp.getAttributes().getNamedItem("name")
                            .getNodeValue();
                } else {
                    nl = xmlTools.selectNodeList(confNode,
                            "//map/info/observatory/instrument");
                    observatories = new String[2 * nl.getLength()];
                    for (int i = 0; i < nl.getLength(); i++) {
                        atts = nl.item(i).getAttributes();
                        observatories[2 * i] = atts.getNamedItem("name")
                                .getNodeValue();
                        observatories[(2 * i) + 1] = atts.getNamedItem(
                                "observatory").getNodeValue();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (type != WEB_SERVICES_ARCHIVE) {
            mappingTable = new MappingTable(this);
        }

        nbAccesses = 0;
        lastAccess = "none";
    }

    public MappingTable getMappingTable() {
        return (mappingTable);
    }

    public String getID() {
        return (id);
    }

    public String getObservatory() {
        if (observatories.length == 1) {
            return (observatories[0]);
        }
        return (null);
    }

    public String getObservatory(String instrument) {
        if (observatories.length == 1) {
            return (observatories[0]);
        }
        for (int i = 0; i < (observatories.length / 2); i++) {
            if (observatories[2 * i].equalsIgnoreCase(instrument)) {
                return (observatories[(2 * i) + 1]);
            }
        }
        // TEMP FIX FOR FTP-BBSO WHICH ISNT RETURNING OBSERVATORY INFO
        if (instrument.equals("BBSO__HALPH")) {
            return ("BBSO");
        } else if (instrument.equals("BBSO__GONG_SYN")) {
            return ("BBSO");
        } else if (instrument.equals("KANZ__HALPH")) {
            return ("KANZ");
        } else if (instrument.equals("HSOS__HALPH")) {
            return ("HSOS");
        } else if (instrument.equals("OACT__HALPH")) {
            return ("OACT");
        } else if (instrument.equals("YNAO__HALPH")) {
            return ("YNAO");
        } else {
            return ("???");
        }
    }

    public String getName() {
        return (name);
    }

    public String getURL() {
        return (url);
    }

    public int getPort() {
        return (port);
    }

    public int getType() {
        return (type);
    }

    public String getTypeAsString() {
        return (typeNames[type]);
    }

    public int getState() {
        return (state);
    }

    public String getStateAsString() {
        return (stateNames[state]);
    }

    public String getConfFile() {
        return (confFile);
    }

    public Node getConfNode() {
        return (confNode);
    }

    public String getLastAccess() {
        return (lastAccess);
    }

    public int getNumberOfAccesses() {
        return (nbAccesses);
    }

    public void access() {
        lastAccess = ProviderUtils.getDate();
        nbAccesses++;
    }

    public Node getStructureNode() {
        NodeList nl = confNode.getChildNodes();
        int i = 0;
        Node n = null;
        while (i < nl.getLength()) {
            n = nl.item(i);
            if ((n.getNodeType() == Node.ELEMENT_NODE)
                    && (n.getNodeName().equals("structure"))) {
                return (n);
            }
            i++;
        }
        return (null);
    }

    public Node getMappingNode() {
        NodeList nl = confNode.getChildNodes();
        int i = 0;
        Node n = null;
        while (i < nl.getLength()) {
            n = nl.item(i);
            if ((n.getNodeType() == Node.ELEMENT_NODE)
                    && (n.getNodeName().equals("mapping"))) {
                return (n);
            }
            i++;
        }
        return (null);
    }

    public boolean isFTP() {
        return (type == FTP_ARCHIVE);
    }

    public boolean isSQL() {
        return (type == SQL_ARCHIVE);
    }

    public boolean isWebServices() {
        return (type == WEB_SERVICES_ARCHIVE);
    }

    public boolean isHTTP() {
        return (type == HTTP_ARCHIVE);
    }

    public boolean isMixed() {
        return (type == MIXED_ARCHIVE);
    }

    public int getConnectorType() {
        return (connectorType);
    }

    public int getMapperType() {
        return (mapperType);
    }

    public String toString() {
        return ("ARCHIVE '"
                + name
                + "':\n\tid="
                + id
                + "\ntype="
                + typeNames[type]
                + ((type == MIXED_ARCHIVE) ? ("\nmapper="
                        + typeNames[mapperType] + " connector=" + typeNames[connectorType])
                        : "") + "\n\turl=" + url + ":" + port);
    }

    public boolean pause() {
        if (state == STATE_RUNNING) {
            state = STATE_PAUSED;
            return (true);
        }
        return (false);
    }

    public boolean restart() {
        if (state == STATE_PAUSED) {
            state = STATE_RUNNING;
            return (true);
        }
        return (false);
    }

    public boolean reload() {
        return (true);
    }

    public boolean stop() {
        if ((state == STATE_RUNNING) || (state == STATE_PAUSED)) {
            state = STATE_STOPPED;
            return (true);
        }
        return (false);
    }

    public boolean start() {
        if (state == STATE_STOPPED) {
            state = STATE_RUNNING;
            return (true);
        }
        return (false);
    }
}