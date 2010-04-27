package org.egso.provider;

import java.io.*;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.Hashtable;

import org.apache.xerces.parsers.DOMParser;
import org.egso.provider.utils.XMLTools;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class ProviderConfiguration {

    private static ProviderConfiguration instance = null;
    
    private String configurationFile = null;

    private Hashtable<String,Object> options = null;

    private Hashtable<String,String> properties = null;

    private XMLTools xmlTools = null;

    private ProviderConfiguration() {
        configurationFile = ProviderConfiguration.class.getResource("conf/config.xml").getPath();
        try
        {
          configurationFile=URLDecoder.decode(configurationFile,"UTF-8");
        }
        catch(UnsupportedEncodingException _uee)
        {
          
        }
        init();
    }

    private ProviderConfiguration(String confPath) {
        configurationFile = confPath + "/config.xml";
        init();
    }

    public static ProviderConfiguration getInstance(String path) {
        if (instance == null) {
            instance = new ProviderConfiguration(path);
        }
        return (instance);
    }

    public static ProviderConfiguration getInstance() {
        if (instance == null) {
            instance = new ProviderConfiguration();
        }
        return (instance);
    }

    private void init() {
        System.out.println("Loading configuration file.");
        xmlTools = XMLTools.getInstance();
        options = new Hashtable<String,Object>();
        try {
            InputSource in = new InputSource(new FileInputStream(configurationFile));
            DOMParser parser = new DOMParser();
            parser.parse(in);
            Node root = parser.getDocument().getDocumentElement();
            // Defines properties.
            properties = new Hashtable<String,String>();
            NodeList nl = xmlTools.selectNodeList(root, "//provider/properties/property");
            Node n = null;
            for (int i = 0; i < nl.getLength(); i++) {
                n = nl.item(i);
                properties.put(n.getAttributes().getNamedItem("name").getNodeValue(), n.getAttributes().getNamedItem("value").getNodeValue());
            }
            String key = null;
            for (Enumeration<String> e = properties.keys(); e.hasMoreElements();) {
                key = (String) e.nextElement();
                properties.put(key, transform((String) properties.get(key)));
                /*
                 * int x = val.indexOf("${"); while (x != -1) { // Get
                 * the name of the properties between ${...} and
                 * replaces by its value. int y = val.indexOf("}", x);
                 * System.out.println("POUR '" + val + "'
                 * COLLONS:\n\t'" + val.substring(0, x) + "'");
                 * System.out.println("\t'" +
                 * properties.get(val.substring(x + 2, y)) + "'");
                 * System.out.println("\t'" + val.substring(y + 1) +
                 * "'\n---"); val = val.substring(0, x) + (String)
                 * properties.get(val.substring(x + 2, y)) +
                 * val.substring(y + 1); System.out.println("CE QUI
                 * DONNE '" + val + "'"); x = val.indexOf("${"); }
                 * System.out.println("ADD '" + key + "'='" + val +
                 * "'"); properties.put(key, val);
                 */
            }
            /*
             * System.out.println("- PROPERTIES -"); for (Enumeration
             * e = properties.keys() ; e.hasMoreElements() ; ) { key =
             * (String) e.nextElement(); System.out.println("\t" + key + " = " +
             * (String) properties.get(key)); }
             */
            // Core options.
            options.put("core.rolename", xmlTools.selectSingleNode(root, "//provider/core/rolename/text()").getNodeValue());
            options.put("core.roleversion", xmlTools.selectSingleNode(root, "//provider/core/roleversion/text()").getNodeValue());
            options.put("core.tomcat-install-dir", transform(xmlTools.selectSingleNode(root, "//provider/core/tomcat-install-dir/text()").getNodeValue()));
            options.put("core.validation", transform(xmlTools.selectSingleNode(root, "//provider/core/validation-file/text()").getNodeValue()));
            options.put("core.archives", transform(xmlTools.selectSingleNode(root, "//provider/core/archives-files/text()").getNodeValue()));
            options.put("core.archives-directory", transform(xmlTools.selectSingleNode(root, "//provider/core/archives-directory/text()").getNodeValue()));
            options.put("core.history", transform(xmlTools.selectSingleNode(root, "//provider/core/history-file/text()").getNodeValue()));
            options.put("core.routetable", transform(xmlTools.selectSingleNode(root, "//provider/core/routetable-file/text()").getNodeValue()));
            options.put("core.config", transform(xmlTools.selectSingleNode(root, "//provider/core/config-file/text()").getNodeValue()));

            // Archives options.
            nl = xmlTools.selectNodeList(root, "//provider/archives/archive");
            for (int i = 0; i < nl.getLength(); i++) {
                n = nl.item(i);
                options.put("archives." + n.getAttributes().getNamedItem("id").getNodeValue() + ".load-on-startup", n.getAttributes().getNamedItem("load-on-startup").getNodeValue());
            }
            options.put("archives.sql.maximum-results", new Integer(xmlTools.selectSingleNode(root, "//provider/archives/sql/maximum-results/text()").getNodeValue()));
            options.put("archives.ftp.gateway.server", xmlTools.selectSingleNode(root, "//provider/archives/ftp/gateway/server").getAttributes().getNamedItem("enable").getNodeValue());
            n = xmlTools.selectSingleNode(root, "//provider/archives/ftp/gateway/client");
            options.put("archives.ftp.gateway.client", n.getAttributes().getNamedItem("enable").getNodeValue());
            options.put("archives.ftp.gateway.url", n.getAttributes().getNamedItem("url").getNodeValue());

            // Services options.
            n = xmlTools.selectSingleNode(root, "//provider/services");
            options.put("service.load-on-startup", n.getAttributes().getNamedItem("load-on-startup").getNodeValue());
            nl = xmlTools.selectNodeList(root, "//provider/services/directories/dir/text()");
            String[] dirs = new String[nl.getLength()];
            for (int i = 0; i < nl.getLength(); i++) {
                dirs[i] = transform(nl.item(i).getNodeValue());
            }
            options.put("services.directories", dirs);
            options.put("service.cosec-na-image", xmlTools.selectSingleNode(root, "//provider/services/cosec/not-available-image/text()").getNodeValue());

            // Monitor options.
            options.put("monitor.maximum-exceptions", new Integer(xmlTools.selectSingleNode(root, "//provider/monitor/maximum-exceptions/text()").getNodeValue()));
            n = xmlTools.selectSingleNode(root, "//provider/monitor/report");
            String tmp = n.getAttributes().getNamedItem("unit").getNodeValue().toUpperCase();
            int facteur = 1000;
            if (tmp.equals("MINUTE")) {
                facteur *= 60;
            } else {
                if (tmp.equals("HOUR")) {
                    facteur *= 3600;
                } else {
                    if (tmp.equals("DAY")) {
                        facteur *= 86400;
                    } else {
                        // In other case, the default factor is 30
                        // minutes.
                        facteur *= 1800;
                    }
                }
            }
            int value = Integer.parseInt(n.getAttributes().getNamedItem("every").getNodeValue());
            options.put("monitor.report", new Integer(value * facteur));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        String key = null;
        Object obj = null;
        StringBuffer sb = new StringBuffer();
        sb.append("+-- PROVIDER CONFIGURATION ---\n");
        for (Enumeration<String> e = options.keys(); e.hasMoreElements();) {
            key = (String) e.nextElement();
            obj = options.get(key);
            if (obj instanceof String) {
                sb.append("| " + key + " = " + obj + "\n");
            } else {
                if (obj instanceof String[]) {
                    String[] s = (String[]) obj;
                    sb.append("| " + key + " = [" + s[0]);
                    for (int i = 1; i < s.length; i++) {
                        sb.append(", " + s[i]);
                    }
                    sb.append("]\n");
                } else {
                    if (obj instanceof Integer) {
                        sb.append("| " + key + " = " + obj.toString() + "\n");
                    } else {
                        sb.append("| " + key + " is a " + obj.getClass().getName() + " and value = " + obj.toString() + "\n");
                    }
                }
            }
        }
        sb.append("+-----------------------------\n");
        return (sb.toString());
    }

    public Enumeration<String> getProperties() {
        return (options.keys());
    }

    public Object getProperty(String param) {
        return (options.get(param));
    }

    public boolean hasProperty(String param) {
        return (options.containsKey(param));
    }

    private String transform(String val) {
        int x = val.indexOf("${");
        while (x != -1) {
            // Get the name of the properties between ${...} and
            // replaces by its value.
            int y = val.indexOf("}", x);
            val = val.substring(0, x) + (String) properties.get(val.substring(x + 2, y)) + val.substring(y + 1);
            x = val.indexOf("${");
        }
        return (val);
    }

}