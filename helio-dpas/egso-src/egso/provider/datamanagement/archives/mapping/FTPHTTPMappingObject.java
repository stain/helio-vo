package org.egso.provider.datamanagement.archives.mapping;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;
import org.egso.provider.utils.Conversion;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class FTPHTTPMappingObject implements MappingObject {


	private String nameEGSO = null;
	private String nameArchive = null;
	private Hashtable<String,String> egso2archive = null;
	private Hashtable<String,String> archive2egso = null;
	private String none = null;
	private String[] nones = null;
	private String[] formats = null;
	private int index = -1;
	private int[] indexes = null;
	private String operation = null;
	private String[] operations = null;

	public FTPHTTPMappingObject(Node node, NodeList masks) {
		egso2archive = new Hashtable<String,String>();
		archive2egso = new Hashtable<String,String>();
		init(node, masks);
	}
	
	
	private void init(Node node, NodeList masks) {
		NamedNodeMap atts = node.getAttributes();
		nameEGSO = atts.getNamedItem("name").getNodeValue();
		if (nameEGSO.equals("TIME")) {
			none = atts.getNamedItem("none").getNodeValue();
			index = Integer.parseInt(atts.getNamedItem("index").getNodeValue());
			operation = getOperation(index, masks);
		} else {
			if (nameEGSO.equals("DATE")) {
				Node n = null;
				NodeList child = node.getChildNodes();
				Vector<String> v = new Vector<String>();
				for (int x = 0 ; x < child.getLength() ; x++) {
					n = child.item(x);
					if (n.getNodeType() == Node.ELEMENT_NODE) {
						atts = n.getAttributes();
						if (n.getNodeName().toLowerCase().equals("value")) {
							v.add(atts.getNamedItem("index").getNodeValue());
							v.add(atts.getNamedItem("format").getNodeValue());
							v.add(atts.getNamedItem("none").getNodeValue());
						}
					}
				}
				indexes = new int[v.size() / 3];
				formats = new String[v.size() / 3];
				nones = new String[v.size() / 3];
				operations = new String[v.size() / 3];
				Iterator<String> it = v.iterator();
        int i = 0;
				while (it.hasNext()) {
					indexes[i] = Integer.parseInt((String) it.next());
					formats[i] = (String) it.next();
					nones[i] = (String) it.next();
					operations[i] = getOperation(indexes[i], masks);
					i++;
				}
			} else {
				nameArchive = atts.getNamedItem("mappedName").getNodeValue();
				none = atts.getNamedItem("none").getNodeValue();
				if (atts.getNamedItem("multiple-index").getNodeValue().toLowerCase().equals("no")) {
					// One index.
					index = Integer.parseInt(atts.getNamedItem("index").getNodeValue());
					operation = getOperation(index, masks);
				} else {
					// Multiple indexes.
					
//					getOperation for all indexes.
				}
				Node n = null;
				NodeList child = node.getChildNodes();
				for (int x = 0 ; x < child.getLength() ; x++) {
					n = child.item(x);
					if (n.getNodeType() == Node.ELEMENT_NODE) {
						atts = n.getAttributes();
						if (n.getNodeName().toLowerCase().equals("value")) {
							egso2archive.put(atts.getNamedItem("name").getNodeValue(), atts.getNamedItem("value").getNodeValue());
							archive2egso.put(atts.getNamedItem("value").getNodeValue(), atts.getNamedItem("name").getNodeValue());
						} else {
							if (n.getNodeName().toLowerCase().equals("valueout")) {
								archive2egso.put(atts.getNamedItem("value").getNodeValue(), atts.getNamedItem("name").getNodeValue());
							} else {
								if (n.getNodeName().toLowerCase().equals("valuein")) {
									egso2archive.put(atts.getNamedItem("name").getNodeValue(), atts.getNamedItem("value").getNodeValue());
								}
							}
						}
					}
				}
			}
		}
	}
	
	private String getOperation(int index, NodeList masks) {
		Node n = null;
		StringBuffer op = new StringBuffer();
		boolean hasToken = false;
		for (int i = 0 ; i < masks.getLength() ; i++) {
			n = masks.item(i);
			NamedNodeMap atts = n.getAttributes();
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				if (n.getNodeName().equals("param") && (Integer.parseInt(n.getAttributes().getNamedItem("index").getNodeValue()) == index)) {
					if (atts.getNamedItem("token") != null) {
						op.append("token," + atts.getNamedItem("token").getNodeValue() + "," + atts.getNamedItem("nb").getNodeValue());
						hasToken = true;
					}
					if (atts.getNamedItem("start") != null) {
						if (hasToken) {
							op.append(",");
						}
						op.append("substring," + atts.getNamedItem("start").getNodeValue());
						if (atts.getNamedItem("end") != null) {
							op.append("," + atts.getNamedItem("end").getNodeValue());
						}
					}
					if (atts.getNamedItem("format") != null) {
						op.append(",format," + atts.getNamedItem("format").getNodeValue());
					}
				}
			}
		}
		return (op.toString());
	}

	public String getEGSOName() {
		return (nameEGSO);
	}

	public String getArchiveName() {
		return (nameArchive);
	}

	public String[] getArchiveNames() {
		return (new String[] {nameArchive});
	}

	public String egso2archive(String value) {
		Object obj = egso2archive.get(value);
		if (obj instanceof String) {
			return ((String) obj);
		}
		return (none);
	}

	public String archive2egso(String value) {
//		System.out.println("Mapping the value '" + value + "'");
		String oper = operation;
		if (oper == null) {
			int i = 0;
			boolean found = false;
			while (!found && (i < operations.length)) {
				found = (operations[i] != null) && (!operations[i].trim().equals(""));
				i++;
			}
			if (found) {
				oper = operations[--i];
			}
		}
//		System.out.println("Operation> " + oper);
		StringTokenizer tokenOperation = new StringTokenizer(oper, ",");
		String op = tokenOperation.nextToken();
		if (op.equals("token")) {
			String token = tokenOperation.nextToken();
			int x = Integer.parseInt(tokenOperation.nextToken());
			StringTokenizer tokenValue = new StringTokenizer(value, token);
			while (x > 1) {
				tokenValue.nextToken();
				x--;
			}
			value = tokenValue.nextToken();
			if (tokenOperation.hasMoreTokens()) {
				op = tokenOperation.nextToken();
			} else {
				op = null;
			}
		}
		if ((op != null) && op.equals("substring")) {
			int start = Integer.parseInt(tokenOperation.nextToken());
			String end = tokenOperation.nextToken();
			if (end == null) {
				value = value.substring(start);
			} else {
				value = value.substring(start, Integer.parseInt(end) + 1);
			}
		}
		if (tokenOperation.hasMoreTokens() && tokenOperation.nextToken().equals("format")) {
			String format = tokenOperation.nextToken();
			if (format.indexOf("YY") != -1) {
				// Convert Date...
				value = Conversion.convertDate(format, "YYYY-MM-DD", value);
			} else {
				// Convert Time...
				value = Conversion.convertAllTime(format, "HH:MM:SS", value);
			}
		}
//		System.out.println("-> '" + value + "'.");
		Object obj = archive2egso.get(value);
		if (obj instanceof String) {
//			System.out.println("--> '" + (String) obj + "'.");
			return ((String) obj);
		}
		return (value);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("FTP/HTTP Mapping Object for the EGSO parameter '" + nameEGSO + "'\n");
		sb.append("Archive value: " + nameArchive + " | In case of no values: " + none + "\n");
		sb.append("MAPPING TABLE :\nEGSO -> ARCHIVE:\n");
		for (String tmp:egso2archive.keySet())
		{
			sb.append("\t" + tmp + " -> " + egso2archive.get(tmp) + "\n");
		}
		sb.append("ARCHIVE -> EGSO:\n");
		for (String tmp:archive2egso.keySet())
		{
			sb.append("\t" + tmp + " -> " + (String) archive2egso.get(tmp) + "\n");
		}
		return (sb.toString());
	}

	public int getType() {
		return (MappingObject.FTP_HTTP);
	}

}

