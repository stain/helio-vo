package org.egso.provider.datamanagement.mapper;

import java.io.ByteArrayInputStream;
import javax.xml.parsers.SAXParser;
import org.egso.provider.admin.ProviderMonitor;
import org.egso.provider.datamanagement.archives.MixedArchive;
import org.egso.provider.datamanagement.archives.WebServiceArchive;
import org.egso.provider.datamanagement.connector.WebServiceConnector;
import org.egso.provider.query.ProviderQuery;
import org.egso.provider.query.ProviderTable;
import org.egso.provider.query.WebServiceQuery;
import org.egso.provider.utils.XMLTools;
import org.egso.provider.utils.XMLUtils;
import org.xml.sax.InputSource;


/**
 * TODO: Description of the Class
 *
 * @author    Romain Linsolas (linsolas@gmail.com)
 * @version   1.1 - 26/10/2004 [15/10/2003]
 */
/*
1.1 - 26/10/2004:
	Adaptation to the SAXParser (instead of Xerces SAX Parser).
*/
public class WebServiceMapper extends Thread implements Mapper {

	/**
	 * JAVADOC: Description of the Field
	 */
	private ProviderQuery providerQuery = null;
	/**
	 * JAVADOC: Description of the Field
	 */
	private ProviderTable providerTable = null;
	/**
	 * JAVADOC: Description of the Field
	 */
	private WebServiceArchive archive = null;
	private MixedArchive mixedArchive = null;


	/**
	 * TODO: Constructor for the DataPresentationManagerImpl object
	 *
	 * @param tg       JAVADOC: Description of the Parameter
	 * @param pq       JAVADOC: Description of the Parameter
	 * @param arch     JAVADOC: Description of the Parameter
	 * @param results  JAVADOC: Description of the Parameter
	 */
	public WebServiceMapper(ThreadGroup tg, ProviderQuery pq, WebServiceArchive arch, ProviderTable results) {
		super(tg, "ws-mapper@" + arch.getID());
		providerQuery = pq;
		providerTable = results;
		archive = arch;
		mixedArchive = null;
	}

	public WebServiceMapper(ThreadGroup tg, ProviderQuery pq, MixedArchive arch, ProviderTable results) {
		super(tg, "ws-mapper@" + arch.getID());
		providerQuery = pq;
		providerTable = results;
		archive = null;
		mixedArchive = arch;
	}


	/**
	 * TODO: Description of the Method
	 *
	 */
	public void run() {
		WebServiceQuery wsq = new WebServiceQuery();
		// Adding fields selected in the FTPQuery object.
		wsq.setFields(providerTable.getFields());
		InputSource is = new InputSource(new ByteArrayInputStream(XMLUtils.nodeToString(providerQuery.getData()).getBytes()));
		is.setEncoding("ISO-8859-1");
		WebServiceParser parser = new WebServiceParser(archive.getMappingNode(), archive.getStructureNode());
		try {
			SAXParser saxParser = XMLTools.getSAXParser();
			saxParser.parse(is, parser);
		} catch (Throwable t) {
			ProviderMonitor.getInstance().reportException(t);
			t.printStackTrace();
		}
		wsq.setAllCalls(parser.getAllCalls());
		//System.out.println("WebService Query created:\n" + wsq.toString());
		if (mixedArchive == null) {
			WebServiceConnector connector = new WebServiceConnector(archive, wsq, providerTable);
			connector.query();
		} else {
			MixedMapper map = new MixedMapper(mixedArchive, wsq, providerTable);
			map.query();
		}
	}
}

