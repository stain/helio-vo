package org.egso.provider.query;

import java.io.ByteArrayInputStream;

import javax.activation.DataHandler;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.egso.common.context.EGSOContext;
import org.egso.common.services.provider.ResponseFileQueryProvider;
import org.egso.provider.admin.ProviderMonitor;
import org.egso.provider.datamanagement.datapresentation.DataPresentationManager;
import org.egso.provider.utils.ProviderUtils;
import org.egso.provider.utils.XMLUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;


public class FileExecutor extends Thread {

	private EGSOContext context = null;
	private String id = null;
	private ResponseFileQueryProvider notifier = null;
	private Document queryDocument = null;
	private DataPresentationManager dataPresentationManager = null;
	private Logger logger = null;


	public FileExecutor(String queryID, EGSOContext cxt, String query, ResponseFileQueryProvider responseObject, QueryValidator qv, DataPresentationManager dpm) {
		logger = Logger.getLogger(this.getClass().getName());
		id = queryID;
		context = cxt;
		notifier = responseObject;
		dataPresentationManager = dpm;
		logger.info("FileQuery [id=" + id + "] received:\n" + context.toXML() + "\n" + query);
		context.addRoute("File Query Reception", "The query has been received by the provider");
		// Creates the query as a XML Document.
		try {
			DocumentBuilderFactory facto = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = facto.newDocumentBuilder();
			InputSource is = new InputSource(new ByteArrayInputStream(query.getBytes()));
			queryDocument = builder.parse(is);
		} catch (Throwable t) {
			ProviderMonitor.getInstance().reportException(t);
			ProviderUtils.reportException("FileExecutor", t);
		}
		ProviderMonitor.getInstance().setLastQuery(context + "\n" + query, false);
	}


	public void run() {
		long start = System.currentTimeMillis();
		ProviderMonitor.getInstance().setLastQuery(XMLUtils.XMLToHTML(queryDocument), true);
		ProviderQuery pq = new ProviderQuery(ProviderQuery.FILES, context, queryDocument);
		System.out.println("[FileExecutor] (" + ProviderUtils.getDate() + ") Execution of a query for FILES:\n" + pq.toString());
		DataHandler dataHandler = dataPresentationManager.fetchFiles(pq);
		if (dataHandler == null) {
			dataHandler = new DataHandler("null", "text/xml");
		}
		// Returning response.
		System.out.println("\n\t[[ QUERY EXECUTED in " + (System.currentTimeMillis() - start) + "ms ]]\n");
		try {
			System.out.println("\t[RESPONSE READY TO BE SENT]...");
			notifier.sendFilesList(context.toXML(), dataHandler);
			System.out.println("\t[RESPONSE SENT SUCCESSFULLY]");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
