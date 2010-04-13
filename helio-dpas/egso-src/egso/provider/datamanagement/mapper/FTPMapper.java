package org.egso.provider.datamanagement.mapper;

import java.io.ByteArrayInputStream;
import javax.xml.parsers.SAXParser;
import org.egso.provider.admin.ProviderMonitor;
import org.egso.provider.datamanagement.archives.FTPArchive;
import org.egso.provider.datamanagement.archives.MixedArchive;
import org.egso.provider.datamanagement.connector.FTPConnector;
import org.egso.provider.datamanagement.connector.ResultsFormatter;
import org.egso.provider.query.FTPQuery;
import org.egso.provider.query.ProviderQuery;
import org.egso.provider.query.ProviderTable;
import org.egso.provider.utils.XMLTools;
import org.egso.provider.utils.XMLUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


/**
 *  TODO: Description of the Class
 *
 * @author     Romain Linsolas (linsolas@gmail.com)
 * @version    0.9.1 - 23/01/2004 [14/10/2003]
 */
/*
0.9.1 - 23/01/2004:
	Using the new FTPParser.
*/
public class FTPMapper extends Thread implements Mapper {

	private ProviderQuery providerQuery = null;
	private ProviderTable providerTable = null;
	private FTPArchive archive = null;
	private MixedArchive mixedArchive = null;


	/**
	 *  TODO: Constructor for the DataPresentationManagerImpl object
	 */
	public FTPMapper(ThreadGroup tg, ProviderQuery pq, FTPArchive arch, ProviderTable results) {
		super(tg, "ftp-mapper@" + arch.getID());
		providerQuery = pq;
		providerTable = results;
		archive = arch;
		mixedArchive = null;
	}

	public FTPMapper(ThreadGroup tg, ProviderQuery pq, MixedArchive arch, ProviderTable results) {
		super(tg, "ftp-mapper@" + arch.getID());
		providerQuery = pq;
		providerTable = results;
		archive = null;
		mixedArchive = arch;
	}


	public void run() {
//		System.out.println("[FTP-M] Running query.");
		FTPQuery ftp = new FTPQuery();
		NodeList nl = providerQuery.getSelect().getChildNodes();
		Node n = null;
		ftp.setFields(providerTable.getFields());
		// Login and chdir to the root path.
		ftp.addCommand(FTPQuery.LOGIN, new String[] {archive.getUser(), archive.getPassword()});
		ftp.addCommand(FTPQuery.CHDIR, new String[] {archive.getRootPath()});
		// Add all masks for files (using the FTP Parser).
		nl = archive.getStructureNode().getChildNodes();
		String mask = null;
		int nb = 0;
		for (int i = 0 ; i < nl.getLength() ; i++) {
			n = nl.item(i);
			if ((n.getNodeType() == Node.ELEMENT_NODE) && (n.getNodeName().equals("mask"))) {
				mask = n.getAttributes().getNamedItem("mask").getNodeValue();
				nb = Integer.parseInt(n.getAttributes().getNamedItem("number").getNodeValue());
			}
		}
		FTPParser parser = new FTPParser(mask, nb, archive.getMappingNode());
		InputSource is = new InputSource(new ByteArrayInputStream(XMLUtils.nodeToString(providerQuery.getData()).getBytes()));
		is.setEncoding("ISO-8859-1");
		try {
			SAXParser saxParser = XMLTools.getSAXParser();
			saxParser.parse(is, parser);
		} catch (Throwable t) {
			ProviderMonitor.getInstance().reportException(t);
		}
		// Adding all created masks in the FTPQuery object.
		for (String s:parser.getAllMasks()) {
			ftp.addCommand(FTPQuery.DETAILED_LIST, new String[] {s});
		}
		// Adding the Log out command.
		ftp.addCommand(FTPQuery.LOGOUT, null);
		System.out.println("FTP Query Commands verification:\n" + ftp.toString());
		// Running the FTPQuery object.
		if (mixedArchive == null) {
			FTPConnector connector = new FTPConnector(archive, ftp, providerTable, new ResultsFormatter(archive, providerTable.getFields()));
			connector.query();
		} else {
			MixedMapper map = new MixedMapper(mixedArchive, ftp, providerTable);
			map.query();
		}
	}



}

