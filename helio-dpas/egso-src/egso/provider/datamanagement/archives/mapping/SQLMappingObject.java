package org.egso.provider.datamanagement.archives.mapping;


import java.util.Enumeration;
import java.util.Hashtable;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class SQLMappingObject implements MappingObject {


	private String nameEGSO = null;
	private String valueArchive = null;
	private String[] intervalArchive = null;
	private String[] concatArchive = null;
	private Hashtable<String,String> egso2archive = null;
	private Hashtable<String,String> archive2egso = null;
	private int type = UNKNOWN;
	private final static int UNKNOWN = -1;
	private final static int VALUE = 0;
	private final static int INTERVAL = 1;
	private final static int CONCATENATION = 2;


	public SQLMappingObject(Node node) {
		egso2archive = new Hashtable<String,String>();
		archive2egso = new Hashtable<String,String>();
		init(node);
	}
	
	private void init(Node node) {
		NamedNodeMap atts = node.getAttributes();
		nameEGSO = atts.getNamedItem("name").getNodeValue();
		boolean value = atts.getNamedItem("value").getNodeValue().toLowerCase().equals("yes");
		if (value) {
			// Case of a value.
			type = VALUE;
			valueArchive = atts.getNamedItem("map").getNodeValue();
		} else {
			if (atts.getNamedItem("concat") == null) {
				// INTERVAL...
				type = INTERVAL;
				intervalArchive = new String[2];
				intervalArchive[0] = atts.getNamedItem("begin").getNodeValue();
				intervalArchive[1] = atts.getNamedItem("end").getNodeValue();
			} else {
				// CONCAT...
				type = CONCATENATION;
				int x = Integer.parseInt(atts.getNamedItem("concat").getNodeValue());
				concatArchive = new String[x];
				for (int i = 0 ; i < x ; i++) {
					concatArchive[i] = atts.getNamedItem("map" + (i + 1)).getNodeValue();
				}
			}
		}
		Node n = null;
		NodeList child = node.getChildNodes();
		for (int x = 0 ; x < child.getLength() ; x++) {
			n = child.item(x);
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				atts = n.getAttributes();
				if (n.getNodeName().toLowerCase().equals("value")) {
					egso2archive.put(atts.getNamedItem("value").getNodeValue(), atts.getNamedItem("map").getNodeValue());
					archive2egso.put(atts.getNamedItem("map").getNodeValue(), atts.getNamedItem("value").getNodeValue());
				} else {
					if (n.getNodeName().toLowerCase().equals("valueout")) {
						archive2egso.put(atts.getNamedItem("value").getNodeValue(), atts.getNamedItem("map").getNodeValue());
					} else {
						if (n.getNodeName().toLowerCase().equals("valuein")) {
							egso2archive.put(atts.getNamedItem("value").getNodeValue(), atts.getNamedItem("map").getNodeValue());
						}
					}
				}
			}
		}
	}

	public String getEGSOName() {
		return (nameEGSO);
	}

	public String getArchiveName() {
		if (type != VALUE) {
			return (null);
		}
		return (valueArchive);
	}

	public String[] getArchiveNames() {
		if (type == VALUE) {
			return (new String[] {valueArchive});
		}
		if (type == INTERVAL) {
			return (intervalArchive);
		}
		return (concatArchive);
	}

	public String egso2archive(String value) {
		Object obj = egso2archive.get(value);
		if (obj instanceof String) {
			return ((String) obj);
		}
		return (null);
	}

	public String archive2egso(String value) {
		Object obj = archive2egso.get(value);
		if (obj instanceof String) {
			return ((String) obj);
		}
		return (null);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("SQL Mapping Object for the EGSO parameter '" + nameEGSO + "'\n");
		if (type == VALUE) {
			sb.append("Archive value: " + valueArchive + "\n");
		} else {
			if (type == INTERVAL) {
				sb.append("Interval value: (" + intervalArchive[0] + ", " + intervalArchive[1] + ")\n");
			} else {
				sb.append("Concat value: [");
				for (int i = 0 ; i < (concatArchive.length - 1) ; i++) {
					sb.append(concatArchive[i] + ", ");
				}
				sb.append(concatArchive[concatArchive.length - 1] + "]\n");
			}
		}
		sb.append("MAPPING TABLE :\nEGSO -> ARCHIVE:\n");
		for (Enumeration<String> e = egso2archive.keys() ; e.hasMoreElements() ; ) {
			String tmp = e.nextElement();
			sb.append("\t" + tmp + " -> " + egso2archive.get(tmp) + "\n");
		}
		sb.append("ARCHIVE -> EGSO:\n");
		for (Enumeration<String> e = archive2egso.keys() ; e.hasMoreElements() ; ) {
			String tmp = e.nextElement();
			sb.append("\t" + tmp + " -> " + archive2egso.get(tmp) + "\n");
		}
		return (sb.toString());
	}

	public int getType() {
		return (MappingObject.SQL);
	}

}

