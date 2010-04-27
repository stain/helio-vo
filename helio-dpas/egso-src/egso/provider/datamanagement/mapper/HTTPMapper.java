package org.egso.provider.datamanagement.mapper;

import java.io.ByteArrayInputStream;
import javax.xml.parsers.SAXParser;
import org.egso.provider.admin.ProviderMonitor;
import org.egso.provider.datamanagement.archives.HTTPArchive;
import org.egso.provider.datamanagement.archives.MixedArchive;
import org.egso.provider.datamanagement.connector.HTTPConnector;
import org.egso.provider.datamanagement.connector.ResultsFormatter;
import org.egso.provider.query.HTTPQuery;
import org.egso.provider.query.ProviderQuery;
import org.egso.provider.query.ProviderTable;
import org.egso.provider.utils.XMLTools;
import org.egso.provider.utils.XMLUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


/**
 * Mapper for HTTP Archives.
 *
 * @author     Romain Linsolas (linsolas@gmail.com)
 * @version    0.1 - 18/01/2005 [18/01/2005]
 */
public class HTTPMapper extends Thread implements Mapper {

	private ProviderQuery providerQuery = null;
	private ProviderTable providerTable = null;
	private HTTPArchive archive = null;
	private MixedArchive mixedArchive = null;


	/**
	 *  TODO: Constructor for the DataPresentationManagerImpl object
	 */
	public HTTPMapper(ThreadGroup tg, ProviderQuery pq, HTTPArchive arch, ProviderTable results) {
		super(tg, "http-mapper@" + arch.getID());
		providerQuery = pq;
		providerTable = results;
		archive = arch;
		mixedArchive = null;
	}

	public HTTPMapper(ThreadGroup tg, ProviderQuery pq, MixedArchive arch, ProviderTable results) {
		super(tg, "http-mapper@" + arch.getID());
		providerQuery = pq;
		providerTable = results;
		archive = null;
		mixedArchive = arch;
	}


	public void run() {
		HTTPQuery http = new HTTPQuery();
		NodeList nl = providerQuery.getSelect().getChildNodes();
		Node n = null;
		http.setFields(providerTable.getFields());
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
		HTTPParser parser = new HTTPParser(mask, nb, archive.getMappingNode());
		InputSource is = new InputSource(new ByteArrayInputStream(XMLUtils.nodeToString(providerQuery.getData()).getBytes()));
		is.setEncoding("ISO-8859-1");
		try {
			SAXParser saxParser = XMLTools.getSAXParser();
			saxParser.parse(is, parser);
		} catch (Throwable t) {
			t.printStackTrace();
			ProviderMonitor.getInstance().reportException(t);
		}
		for (String x:parser.getAllMasks())
		{
			String url=archive.getURL() + "/" + x.substring(0, x.lastIndexOf('/'));
			String url_mask=x.substring(x.lastIndexOf('/') + 1);
			if("".equals(url_mask))
			  url+='/';
			http.addHTTPFile(url, mask);
		}
		System.out.println("URL / MASK VERIFICATION FOR HTTP ARCHIVE:\n" + http.toString());
		// Running the FTPQuery object.
		if (mixedArchive == null) {
			ResultsFormatter rf = new ResultsFormatter(archive, providerTable.getFields());
			HTTPConnector connector = new HTTPConnector(archive, http, providerTable, rf);
			connector.query();
		} else {
			System.out.println("!!! MIXED ARCHIVE NOT AVAILABLE YET FOR HTTP ARCHIVES");
//			MixedMapper map = new MixedMapper(mixedArchive, http, providerTable);
//			map.query();
		}
	}



}

