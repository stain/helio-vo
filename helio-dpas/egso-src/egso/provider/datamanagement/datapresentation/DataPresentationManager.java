package org.egso.provider.datamanagement.datapresentation;

import java.io.File;
import java.util.*;
import java.util.Map.Entry;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import org.apache.log4j.Logger;
import org.egso.provider.admin.ProviderMonitor;
import org.egso.provider.datamanagement.archives.Archive;
import org.egso.provider.datamanagement.archives.ArchiveManager;
import org.egso.provider.datamanagement.archives.FTPArchive;
import org.egso.provider.datamanagement.archives.HTTPArchive;
import org.egso.provider.datamanagement.archives.MixedArchive;
import org.egso.provider.datamanagement.archives.SQLArchive;
import org.egso.provider.datamanagement.archives.WebServiceArchive;
import org.egso.provider.datamanagement.connector.FTPConnector;
import org.egso.provider.datamanagement.mapper.FTPMapper;
import org.egso.provider.datamanagement.mapper.HTTPMapper;
import org.egso.provider.datamanagement.mapper.SQLMapper;
import org.egso.provider.datamanagement.mapper.WebServiceMapper;
import org.egso.provider.query.FTPQuery;
import org.egso.provider.query.ProviderQuery;
import org.egso.provider.query.ProviderTable;
import org.egso.provider.utils.Compression;
import org.egso.provider.utils.ProviderUtils;
import org.egso.provider.utils.XMLTools;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 *  TODO: Description of the Class
 *
 *@author     Romain Linsolas (linsolas@gmail.com)
 *@created    1.0 - [14/10/2003]
 */
public class DataPresentationManager {

	private RouteTable routeTable = null;
	private ArchiveManager archiveManager = null;
	private Logger logger = null;


	public DataPresentationManager() {
		System.out.println("[Data Presentation Manager] Initialization.");
		routeTable = new RouteTable();
		archiveManager = new ArchiveManager();
		logger = Logger.getLogger(this.getClass().getName());
		logger.info("DataPresentationManagerImpl Initialization");
	}


	/**
	 * Execution of a query. The Data Presentation Manager gets a ProviderQuery,
	 * which is generic, and creates one ProviderQuery per archive that must be
	 * queried.
	 * @param query The generic ProviderQuery (all sub-queries will be inserted
	 * in this object).
	 * @return A ProviderTable that contains all results.
	 */
	 public ProviderTable query(ProviderQuery query) {
		// Get all <param> which are direct children of <data>.
		logger.info("Executing the query by the DPM.");
		NodeList paramList = null;
		Node queryNode = query.getQuery();
		Node queryType = queryNode.getAttributes().getNamedItem("type");
		Hashtable<String,Node> distribution = new Hashtable<String,Node>();
		Node queryData = query.getData().cloneNode(false);
		String exception = null;
		try {
			paramList = XMLTools.getInstance().selectNodeList(queryNode, "/query/data/param");
		} catch (Throwable t) {
			ProviderMonitor.getInstance().reportException(t);
			exception = ProviderUtils.reportException("DataPresentationManagerImpl", t);
		}
		// See for specific queries (<query type="XXX">...)
		String[] archives = null;
		if (queryType != null) {
			archives = routeTable.getArchivesFromQuery(queryType.getNodeValue());
			if (archives != null) {
				for (int i = 0 ; i < archives.length ; i++) {
					distribution.put(archives[i], queryData);
				}
			}
		}
		// Node skeleton contains all information of the query, expect all <data> children.
		Node skeleton = queryNode.cloneNode(false);
		skeleton.appendChild(query.getSelect().cloneNode(true));
		NodeList fieldNodes = query.getSelect().getChildNodes();
		Vector<String> fields = new Vector<String>();
		Node tmp = null;
		for (int i = 0 ; i < fieldNodes.getLength() ; i++) {
			tmp = fieldNodes.item(i);
			if ((tmp.getNodeType() == Node.ELEMENT_NODE) && (tmp.getNodeName().equals("field"))) {
				fields.add(tmp.getAttributes().getNamedItem("name").getNodeValue());
			}
		}
/*
		System.out.println("#############################################");
		for (Iterator it = fields.iterator() ; it.hasNext() ; ) {
			System.out.println("\t" + (String) it.next());
		}
		System.out.println("---------------------------------------------");
*/
		fields.remove("IDArchive");
		fields.remove("observatory");
		fields.remove("instrument");
		fields.remove("filename");
		fields.remove("observingdomain");
		fields.remove("link");
		fields.remove("filesize");
		fields.insertElementAt("IDArchive", 0);
		if ((queryData.getAttributes().getNamedItem("type") == null) || (!queryData.getAttributes().getNamedItem("type").getNodeValue().equals("event"))) {
			fields.insertElementAt("observatory", 1);
			fields.insertElementAt("instrument", 2);
			fields.add("observingdomain");
			fields.add("filename");
			fields.add("link");
			fields.add("filesize");
		}
/*
		for (Iterator it = fields.iterator() ; it.hasNext() ; ) {
			System.out.println("\t" + (String) it.next());
		}
		System.out.println("#############################################");
*/
		Node param = null;
		String paramName = null;
		String valtmp = null;
		Vector<String> exclusiveDB = null;
//		System.out.println("SKELETON NODE:\n" + XMLUtils.nodeToString(skeleton));
		for (int i = 0 ; i < paramList.getLength() ; i++) {
			param = paramList.item(i);
			// Determine if this is a generic parameter or an archive-specific parameter.
			paramName = param.getAttributes().getNamedItem("name").getNodeValue().toLowerCase();
			param.getAttributes().getNamedItem("name").setNodeValue(paramName);
//			System.out.print("    [DPM] Handling " + paramName + " parameter: ");
			if (routeTable.isGeneric(paramName)) {
//				System.out.println("GENERIC.");
				tmp = (Node) distribution.get("GENERIC");
				if (tmp == null) {
					tmp = queryData.cloneNode(false);
				}
				tmp.appendChild(param.cloneNode(true));
				distribution.put("GENERIC", tmp);
			} else {
				if (routeTable.isValueIndependent(paramName)) {
//					System.out.println("VALUE INDEPENDENT.");
					boolean exclusive = routeTable.isExclusive(paramName);
					archives = routeTable.getArchives(paramName);
					if (archives == null) {
						System.out.println("[Data Presentation Manager] ERROR: Parameter " + paramName + " is defined as a value-independent parameter, but no archives are related to it!");
					} else {
						for (int j = 0 ; j < archives.length ; j++) {
							tmp = (Node) distribution.get(archives[j]);
							if (tmp == null) {
								tmp = queryData.cloneNode(false);
							}
							tmp.appendChild(param.cloneNode(true));
							distribution.put(archives[j], tmp);
							if (exclusive) {
								if (exclusiveDB == null) {
									exclusiveDB = new Vector<String>();
								}
								exclusiveDB.add(archives[j]);
//								System.out.println("Adding " + archives[j] + " concerning the param " + paramName);
							}
						}
					}
				} else {
					if (routeTable.isValueSpecific(paramName)) {
//						System.out.println("VALUE SPECIFIC.");
						// Get all values for this parameter.
						// TODO: Consider the case with <interval>.
						NodeList nl = param.getChildNodes();
						Hashtable<String,Vector<Node>> values = new Hashtable<String,Vector<Node>>();
						values.put("GENERIC", new Vector<Node>());
						for (int j = 0 ; j < nl.getLength() ; j++) {
							Node val = nl.item(j);
							if (val.getNodeType() == Node.ELEMENT_NODE) {
								if (val.getNodeName().equals("value")) {
									valtmp = val.getFirstChild().getNodeValue().trim().toUpperCase();
									archives = routeTable.getArchives(paramName, valtmp);
									if (archives == null) {
										System.out.println("*WARNING*: No archive of the provider maps the field '" + paramName + "'='" + valtmp + "'.");
									} else {
										for (int k = 0 ; k < archives.length ; k++) {
											Vector<Node> vect = values.get(archives[k]);
											if (vect == null) {
												vect = new Vector<Node>();
											}
											vect.add(val.cloneNode(true));
											values.put(archives[k], vect);
										}
									}
								} else {
									// Add this node in the generic nodes list.
									Vector<Node> vect = values.get("GENERIC");
									vect.add(val.cloneNode(true));
									values.put("GENERIC", vect);
								}
							}
						}
						Node generic = param.cloneNode(false);
						for(Node n:values.remove("GENERIC"))
							generic.appendChild(n);

						Node node = null;
						for (String key:values.keySet())
						{
							node = generic.cloneNode(true);
							Vector<Node> vect = (Vector<Node>) values.get(key);
							for (Node n:vect)
								node.appendChild(n);

							Node x = (Node) distribution.get(key);
							if (x == null) {
								x = queryData.cloneNode(true);
							}
							x.appendChild(node);
							distribution.put(key, x);
						}
					} else {
						// Unreachable...
						System.out.println("ERROR, param not generic or archive-specific!");
					}
				}
			}
		}
/*
		System.out.println("DEBUG: CHECKING THE CONTENT OF THE HASHTABLE DISTRIBUTION:");
		for (Enumeration e = distribution.keys() ; e.hasMoreElements() ; ) {
			String key = (String) e.nextElement();
			System.out.println("ARCHIVE '" + key + "':\n" + XMLUtils.nodeToString((Node) distribution.get(key)) + "\n");
		}
*/
		// Finish the distribution of the query...
		Node generic = (Node) distribution.remove("GENERIC");
		String idArchive = null;
		Node specificQuery = null;
		Node skel = null;
		NodeList genericChildren = null;
		
		Hashtable<String,ProviderQuery> queries=new Hashtable<String,ProviderQuery>();
		for (Entry<String,Node> e:distribution.entrySet())
		{
			idArchive = e.getKey();
			specificQuery = e.getValue();
			if (generic != null) {
				genericChildren = generic.getChildNodes();
				for (int i = 0 ; i < genericChildren.getLength() ; i++) {
					specificQuery.appendChild(genericChildren.item(i).cloneNode(true));
				}
			}
			skel = skeleton.cloneNode(true);
			skel.appendChild(specificQuery);
			queries.put(idArchive, new ProviderQuery(ProviderQuery.QUERY, query.getContext(), skel, idArchive));
		}
/*
		// Just some debug...
		System.out.println("DEBUG: CHECKING THE CONTENT OF THE HASHTABLE DISTRIBUTION:");
		for (Enumeration e = distribution.keys() ; e.hasMoreElements() ; ) {
			String key = (String) e.nextElement();
			System.out.println("Query for the archive '" + key + "':\n" + ((ProviderQuery) distribution.get(key)).toString() + "\n");
		}
*/
		// STEP 3: Select all Mappers (and Connectors) and start their execution.
		Archive arc = null;
		ThreadGroup group = new ThreadGroup("mappers");
		ProviderTable table = null;
		Vector<ProviderTable> results = new Vector<ProviderTable>();
		for (String id:queries.keySet())
		{
			if ((exclusiveDB != null) && (!exclusiveDB.contains(id))) {
				System.out.println("The archive " + id + " is not queried because of the lack of an exclusive parameter.");
				continue;
			}
			arc = archiveManager.getArchive(id);
			arc.access();
			table = new ProviderTable(query.getContext(), fields);
			results.add(table);
			if (arc.isFTP()) {
				FTPMapper map = new FTPMapper(group, queries.get(id), (FTPArchive) arc, table);
				map.start();
			} else {
				if (arc.isSQL()) {
					SQLMapper map = new SQLMapper(group, queries.get(id), (SQLArchive) arc, table);
					map.start();
				} else {
					if (arc.isWebServices()) {
						WebServiceMapper map = new WebServiceMapper(group, queries.get(id), (WebServiceArchive) arc, table);
						map.start();
					} else {
						if (arc.isHTTP()) {
							HTTPMapper map = new HTTPMapper(group, queries.get(id), (HTTPArchive) arc, table);
							map.start();
						} else {
							if (arc.isMixed()) {
								switch (arc.getMapperType()) {
									case Archive.FTP_MAPPER:
											FTPMapper ftpMap = new FTPMapper(group, queries.get(id), (MixedArchive) arc, table);
											ftpMap.start();
										break;
									case Archive.SQL_MAPPER:
											SQLMapper sqlMap = new SQLMapper(group, queries.get(id), (MixedArchive) arc, table);
											sqlMap.start();
										break;
									case Archive.WEB_SERVICES_MAPPER:
											WebServiceMapper wsMap = new WebServiceMapper(group, queries.get(id), (MixedArchive) arc, table);
											wsMap.start();
										break;
									case Archive.HTTP_MAPPER:
											HTTPMapper httpMap = new HTTPMapper(group, queries.get(id), (MixedArchive) arc, table);
											httpMap.start();
										break;
									default:
										System.out.println("[Data Presentation Manager] Wrong Mapper type for a MIXED Archive.");
								}
							} else {
								System.out.println("[Data Presentation Manager] Can't find a mapper for a " + arc.getTypeAsString() + " archive.");
							}
						}
					}
				}
			}
		}
		
		
		//FIXME: activeCount() is only an estimate and not guaranteed to be correct. use .join() instead.
		
		// Wait until all threads (Mappers + Connectors) have finished their task.
		int WAITING_TIME = 100 ;
		long x = 0 ;
		while (group.activeCount() != 0) {
			try {
				Thread.sleep(WAITING_TIME) ;
				x += WAITING_TIME ;
			} catch (Exception e) {
			}
		}
		logger.info("Total execution time for ALL queries: " + (x / 60000) + "m" + ((x % 60000) / 1000) + "s.") ;
		// Merge all results.
		if (results.size() == 0) {
			return (null);
		}
		ProviderTable finalResult = (ProviderTable) results.firstElement();
//		System.out.println("MERGING ALL RESULTS:");
//		System.out.println(finalResult.toString());
		for (int i = 1 ; i < results.size() ; i++) {
//			System.out.println("MERGING WITH:");
//			System.out.println(((ProviderTable) results.get(i)).toString());
			finalResult.merge((ProviderTable) results.get(i));
//			System.out.println("AFTER MERGING OPERATION:");
//			System.out.println(finalResult.toString());
		}
		if (exception != null) {
			System.out.println("EXCEPTION:\n" + exception);
		}
		logger.info("Total number of results: " + finalResult.getNumberOfResults());
		return(finalResult);
	}


	public DataHandler fetchFiles(ProviderQuery query) {
		NodeList files = query.getFiles().getChildNodes();
		Vector<String> explicitLinks = new Vector<String>();
		Vector<String> abstractLinks = new Vector<String>();
		for (int i = 0 ; i < files.getLength() ; i++) {
			Node tmp = files.item(i);
			if ((tmp.getNodeType() == Node.ELEMENT_NODE) && (tmp.getNodeName().equals("file"))) {
				NodeList nl = tmp.getChildNodes();
				for (int j = 0 ; j < nl.getLength() ; j++) {
					Node file = nl.item(j);
					if (file.getNodeType() == Node.TEXT_NODE) {
						String filename = file.getNodeValue().trim();
						if (filename.startsWith("egso:")) {
							abstractLinks.add(filename);
						} else {
							explicitLinks.add(filename);
						}
					}
				}
			}
		}
		System.out.println(explicitLinks.size() + " explicit file(s), and " + abstractLinks.size() + " abstract file(s) to retrieve.");
		// Manage explicit links.
		Hashtable<String,Vector<String>> queries = new Hashtable<String,Vector<String>>();
		for (String filename:explicitLinks)
		{
			String archive = routeTable.getArchiveForFile(filename);
			Vector<String> v = queries.get(archive);
			if (v == null) {
				v = new Vector<String>();
			}
			v.add(filename);
			queries.put(archive, v);
		}
		
		// Manage abstract links.
		// NOT IMPLEMENTED YET.
		for (String link:abstractLinks)
		{
			System.out.println("Abstract link '" + link + "' not accessible yet [FEATURE NOT IMPLEMENTED].");
		}
		
		// Create queries to retrieve files.
		Archive arc = null;
		ThreadGroup group = new ThreadGroup("files");
		Vector<String> receivedFiles = new Vector<String>();
		// TODO: THREAD THAT !!!!!
		for (String archive:queries.keySet())
		{
			Vector<String> v = queries.get(archive);
			arc = archiveManager.getArchive(archive);
			if (arc.isFTP()) {
				FTPQuery ftp = new FTPQuery();
				FTPArchive ftpArc = (FTPArchive) arc;
				ftp.addCommand(FTPQuery.LOGIN, new String[] {ftpArc.getUser(), ftpArc.getPassword()});
				ftp.addCommand(FTPQuery.CHDIR, new String[] {ftpArc.getRootPath()});
				for (String filename:v)
				{
					ftp.addCommand(FTPQuery.GET, new String[] {filename.substring(47), filename.substring(23)});
					receivedFiles.add(filename.substring(47));
				}
				ftp.addCommand(FTPQuery.LOGOUT, null);
				System.out.println("TEST-FTP Commands:\n" + ftp.toString());
				FTPConnector connector = new FTPConnector(ftpArc, ftp, null, null);
				connector.query();
			} else {
				if (arc.isSQL()) {
					System.out.println("Retrieving files from an SQL archive is not implemented yet...");
				} else {
					if (arc.isWebServices()) {
						System.out.println("Retrieving files from a Web-Service archive is not implemented yet...");
					}
				}	
			}
		}
		// Wait until all threads (Mappers + Connectors) have finished their task.
		int WAITING_TIME = 100 ;
		long x = 0 ;
		while (group.activeCount() != 0) {
			try {
				Thread.sleep(WAITING_TIME) ;
				x += WAITING_TIME ;
			} catch (Exception e) {
			}
		}
		logger.info("Total execution time for ALL queries: " + (x / 60000) + "m" + ((x % 60000) / 1000) + "s.") ;
		// Compression of files.
		System.out.println("Creation of ZIP file");
		String[] toZip = receivedFiles.toArray(new String[0]);		
		String nameOfZippedFile = "egso-result-" + Math.round(Integer.MAX_VALUE * Math.random()) + ".zip";
		Compression.zipFiles(toZip, nameOfZippedFile);
		// Deletion of files.
		File f = null;
		for (String fn:toZip)
		{
			f = new File(fn);
			f.delete();
			System.out.println(fn + " deleted.");
		}
		return (new DataHandler(new FileDataSource(nameOfZippedFile)));
	}


}
