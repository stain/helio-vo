package org.egso.provider.datamanagement.connector;

import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;
import javax.activation.DataHandler;
import javax.xml.parsers.SAXParser;
import org.egso.common.context.EGSOContext;
import org.egso.provider.admin.ProviderMonitor;
import org.egso.provider.datamanagement.archives.WebServiceArchive;
import org.egso.provider.query.ProviderTable;
import org.egso.provider.query.WebServiceQuery;
import org.egso.provider.utils.XMLTools;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;

/**
 * TODO: Description of the Class
 *
 * @author    Romain Linsolas (linsolas@gmail.com)
 * @version   0.9 27/11/2003 [14/10/2003].
 */
public class WebServiceConnector implements Connector {

	/**
	 * JAVADOC: Description of the Field
	 */
	private WebServiceQuery query = null;
	/**
	 * JAVADOC: Description of the Field
	 */
	private WebServiceArchive archive = null;
	/**
	 * JAVADOC: Description of the Field
	 */
	private ProviderTable results = null;
	/**
	 * JAVADOC: Description of the Field
	 */
	private ProviderResultsParser parser = null;
	private SAXParser saxParser = null;


	/**
	 * TODO: Constructor for the DataPresentationManagerImpl object
	 *
	 * @param arc            JAVADOC: Description of the Parameter
	 * @param wsq            JAVADOC: Description of the Parameter
	 * @param providerTable  JAVADOC: Description of the Parameter
	 */
	public WebServiceConnector(WebServiceArchive arc, WebServiceQuery wsq, ProviderTable providerTable) {
		archive = arc;
		query = wsq;
		results = providerTable;
		try {
			saxParser = XMLTools.getSAXParser();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (arc.getResultParser().equals("org.egso.provider.datamanagement.connector.VSOWebServiceParser")) {
			parser = new VSOWebServiceParser();
		} else {
			if (arc.getResultParser().equals("org.egso.provider.datamanagement.connector.NSOWebServiceParser")) {
				parser = new NSOWebServiceParser();
			} else {
				if (arc.getResultParser().equals("org.egso.provider.datamanagement.connector.SECWebServiceParser")) {
					parser = new SECWebServiceParser();
				} else {
					if (arc.getResultParser().equals("org.egso.provider.datamanagement.connector.UOCWebServiceParser")) {
						parser = new UOCWebServiceParser();
					} else {
						System.out.println("[WebService Connector] Don't know which parser to use for WebService connection!");
					}
				}
			}
		}
		parser.setSelectedFields(wsq.getSelectedFields());
/*
		try {
			ClassLoader cl = ClassLoader.getSystemClassLoader();
			Class parseurClass = cl.loadClass(arc.getResultParser());
			parser = (ProviderResultsParser) parseurClass.newInstance();
			parser.setSelectedFields(wsq.getSelectedFields());
		} catch (Throwable t) {
			ProviderMonitor.getInstance().reportException(t);
		}
*/
	}


	/**
	 * TODO: Gets the files attribute of the Connector object
	 *
	 * @return       TODO: The files value
	 */
	public DataHandler getFiles() {
		return (null);
	}


	/**
	 * JAVADOC: Description of the Method
	 *
	 * @param query  JAVADOC: Description of the Parameter
	 */
	private void sendMessage(String query) {
		try {
//			System.out.println("Querying Web-Service at " + archive.getURL() + "...");
//			System.out.println("Web Service Query ->\n" + query);
			URL url = new URL(archive.getURL() + "\n");
			URLConnection connection = url.openConnection();
			connection.setUseCaches(true);
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestProperty("Content-type", "text/xml");
			connection.setRequestProperty("Content-length", "" + query.length());
			// get the outputStream to write to
			OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
			// and write the data
			osw.write(query, 0, query.length());
			osw.flush();
			osw.close();
			connection.connect();
			saxParser.parse(new InputSource(connection.getInputStream()), (DefaultHandler) parser);
		} catch (Throwable t) {
			ProviderMonitor.getInstance().reportException(t);
			t.printStackTrace();
		}
	}


	/**
	 * JAVADOC: Description of the Method
	 */
	public void query() {
//		System.out.println("_________________________________________________");
//		System.out.println(query);
//		System.out.println("_________________________________________________");
		int nbQ = query.getAllCalls().size();
		int index = 1;
		System.out.println(nbQ + " Web-Service call(s) for the archive " + archive.getID() + ".");
		long startTime = System.currentTimeMillis();
		
		//TODO: fix buggy code
		if(1==1)
		  throw new RuntimeException("FIXME FIXME FIXME");
		
		for (String[] s:query.getAllCalls())
		{
			System.out.print("[ws:" + index++ + "/" + nbQ + "] ");
			//sendMessage(s); //<----- THIS DOES NOT COMPILE
			for (Vector<String> result:parser.getResults())
				results.addResult(result);
		}
		float timeTaken = ((float) (System.currentTimeMillis() - startTime)) / 1000;
		results.getContext().addParameter("Query time for '" + archive.getID() + "' WebService archive", EGSOContext.PARAMETER_SYSTEMINFO, "" + timeTaken + " s");
	}


	/**
	 * A unit test for JUnit
	 *
	 * @return   JAVADOC: Description of the Return Value
	 */
	public boolean testConnection() {
		return (true);
	}

}
