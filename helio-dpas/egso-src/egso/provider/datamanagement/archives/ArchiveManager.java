package org.egso.provider.datamanagement.archives;


import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.Hashtable;

import org.apache.xerces.parsers.DOMParser;
import org.egso.provider.ProviderConfiguration;
import org.egso.provider.admin.ArchiveMonitor;
import org.egso.provider.admin.ProviderMonitor;
import org.egso.provider.utils.XMLUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


public class ArchiveManager {
	
	private Hashtable<String,Archive> archives = null;
	
	
	public ArchiveManager() {
		System.out.println("[Archive Manager] Initialization.");
		archives = new Hashtable<String,Archive>();
		// TODO: Get the conf. file from Avalon.
		try {
//			ClassLoader cl = getClass().getClassLoader();
//			InputSource in = new InputSource(new BufferedReader(new InputStreamReader(cl.getResourceAsStream((String) ProviderConfiguration.getInstance().getProperty("core.archives")))));
			InputSource in = new InputSource(new FileInputStream((String) ProviderConfiguration.getInstance().getProperty("core.archives")));
			DOMParser parser = new DOMParser();
			parser.parse(in);
			NodeList archs = parser.getDocument().getDocumentElement().getChildNodes();
			Node arc = null;
			for (int i = 0 ; i < archs.getLength() ; i++) {
				arc = archs.item(i);
				if (arc.getNodeType() == Node.ELEMENT_NODE) {
					if (arc.getNodeName().equals("archive")) {
						addArchive(arc);
					}
				}
			}
		} catch (Exception e) {
			ProviderMonitor.getInstance().reportException(e);
			e.printStackTrace();
		}
//		System.out.println("     [AM] Checking the content of the ArchiveManager:\n" + contentToString());
	}


	private Archive addArchive(Node n) {
		NodeList nl = n.getChildNodes();
		int i = 0;
		boolean found = false;
		Archive arc = null;
		Node tmp = null;
		while ((!found) && (i < nl.getLength())) {
			tmp = nl.item(i);
			if ((tmp.getNodeType() == Node.ELEMENT_NODE) && (tmp.getNodeName().equals("description"))) {
				found = true;
				String id = tmp.getAttributes().getNamedItem("ID").getNodeValue();
				String type = tmp.getAttributes().getNamedItem("type").getNodeValue().toUpperCase();
				if (type.equals("FTP")) {
					arc = new FTPArchive(n);
				} else {
					if (type.equals("SQL")) {
						arc = new SQLArchive(n);
						((SQLArchive) arc).setMaximumResults(((Integer) ProviderConfiguration.getInstance().getProperty("archives.sql.maximum-results")).intValue());
					} else {
						if (type.equals("WEBSERVICE")) {
							arc = new WebServiceArchive(n);
						} else {
							if (type.equals("HTTP")) {
								arc = new HTTPArchive(n);
							} else {
								if (type.equals("MIXED")) {
									arc = new MixedArchive(n);
								} else {
									System.out.println("[Archive Manager] ERROR: Unknown type of archive: " + type);
								}
							}
						}
					}
				}
				if (((String) ProviderConfiguration.getInstance().getProperty("archives." + id + ".load-on-startup")).equals("true")) {
					arc.start();
				} else {
					arc.stop();
				}
			}
			i++;
		}
		if (arc != null) {
			return(addArchive(arc));
		}
		System.out.println("Error in adding the archive in the database. Configuration of the archive:\n" + XMLUtils.nodeToString(n));
		return(null);
	}
	
	public Archive addArchive(Archive arc) {
//		System.out.println("Add " + arc.getTypeAsString() + " archive '" + arc.getName() + "' to the Archive Manager DB.");
		ProviderMonitor monitor = ProviderMonitor.getInstance();
		monitor.addArchive(arc.getID(), new ArchiveMonitor(arc));
		return((Archive) archives.put(arc.getID(), arc));
	}
	
	public Enumeration<String> getArchivesNames() {
		return(archives.keys());
	}
	
	public Enumeration<Archive> getArchives() {
		return(archives.elements());
	}

	public Archive getArchive(String idArchive) {
		return((Archive) archives.get(idArchive));
	}

	public int getArchiveType(String idArchive) {
		Archive arc = (Archive) archives.get(idArchive);
		if (arc == null) {
			return(-1);
		}
		return(arc.getType());
	}
	
	public String contentToString() {
		StringBuffer sb = new StringBuffer("Content of the Archive Manager:\n");
		for (Enumeration<Archive> e = archives.elements() ; e.hasMoreElements() ; ) {
			Archive arc = e.nextElement();
			switch (arc.getType()) {
				case Archive.FTP_ARCHIVE:
						sb.append(((FTPArchive) arc).toString() + "\n");
					break;
				case Archive.SQL_ARCHIVE:
						sb.append(((SQLArchive) arc).toString() + "\n");
					break;
				case Archive.WEB_SERVICES_ARCHIVE:
						sb.append(((WebServiceArchive) arc).toString() + "\n");
					break;
				case Archive.HTTP_ARCHIVE:
						sb.append(((HTTPArchive) arc).toString() + "\n");
					break;
				case Archive.MIXED_ARCHIVE:
						sb.append(((MixedArchive) arc).toString() + "\n");
					break;
				default:
					System.out.println("Archive of unknown type:\n" + arc.toString());
			}
		}
		return(sb.toString());
	}

}

