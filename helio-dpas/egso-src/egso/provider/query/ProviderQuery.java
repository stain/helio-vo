package org.egso.provider.query;

import org.egso.common.context.EGSOContext;
import org.egso.provider.admin.ProviderMonitor;
import org.egso.provider.utils.XMLTools;
import org.egso.provider.utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * The class ProviderQuery contains the EGSO Provider Query created by the Query
 * Engine, and also contains all sub-Provider Queries. These sub-queries are
 * created by the Data Presentation Manager and each of them is specific to an
 * archive.
 *
 * @author Romain Linsolas (linsolas@gmail.com)
 * @version 0.3 - 30/01/2004 [17/11/2003]
 **/
/*
0.3 - 17/06/2004:
	Incorporation of the EGSOContext.
0.2 - 30/01/2004:
	Handles files transfer queries.
	Comment getValues(), getAllValues() methods [not used].
0.1 - 17/11/2003:
	First version of the ProviderQuery. Handles only query.
*/
public class ProviderQuery {

	public final static int QUERY = 0;
	public final static int FILES = 1;
	private int nature = QUERY;
	private Node query = null;
	private boolean generic = true;
	private String idArchive = null;
	private EGSOContext context = null;


	public ProviderQuery(int natureOfQuery, EGSOContext cxt, Node providerQuery) {
		init(natureOfQuery, cxt, providerQuery, null);
	}
	
	public ProviderQuery(int natureOfQuery, EGSOContext cxt, Document providerQuery) {
		init(natureOfQuery, cxt, providerQuery.getDocumentElement(), null);
	}

	public ProviderQuery(int natureOfQuery, EGSOContext cxt, Node providerQuery, String archive) {
		init(natureOfQuery, cxt, providerQuery, archive);
	}

	public ProviderQuery(int natureOfQuery, EGSOContext cxt, Document providerQuery, String archive) {
		init(natureOfQuery, cxt, providerQuery.getDocumentElement(), archive);
	}

	private void init(int natureOfQuery, EGSOContext cxt, Node providerQuery, String archive) {
		nature = natureOfQuery;
		context = cxt;
		generic = (archive != null);
		idArchive = archive;
		query = providerQuery;
	}
	
	public int getNatureOfQuery() {
		return (nature);
	}
	
	public Node getQuery() {
		return (query);
	}
	
	public EGSOContext getContext() {
		return (context);
	}
	
	public void setContext(EGSOContext cxt) {
		context = cxt;
	}
	
	public boolean isGeneric() {
		return(generic);
	}
	
	public String getArchive() {
		return(idArchive);
	}
	
	public Node getSelect() {
		if (nature == FILES) {
			return (null);
		}
		NodeList nl = query.getChildNodes();
		int i = 0;
		Node select = null;
		while (i < nl.getLength()) {
			select = nl.item(i);
			if ((select.getNodeType() == Node.ELEMENT_NODE) && (select.getNodeName().equals("select"))) {
				return (select);
			}
			i++;
		}
		return (null);
	}
	
	public Node getData() {
		if (nature == FILES) {
			return (null);
		}
		NodeList nl = query.getChildNodes();
		int i = 0;
		Node data = null;
		while (i < nl.getLength()) {
			data = nl.item(i);
			if ((data.getNodeType() == Node.ELEMENT_NODE) && (data.getNodeName().equals("data"))) {
				return (data);
			}
			i++;
		}
		// TODO: Log error.
		return (null);
	}

	public Node getInformation() {
		if (nature == QUERY) {
			return (null);
		}
		NodeList nl = query.getChildNodes();
		int i = 0;
		Node info = null;
		while (i < nl.getLength()) {
			info = nl.item(i);
			if ((info.getNodeType() == Node.ELEMENT_NODE) && (info.getNodeName().equals("information"))) {
				return (info);
			}
			i++;
		}
		// TODO: Log error.
		return (null);
	}
	
	public Node getFiles() {
		if (nature == QUERY) {
			return (null);
		}
		NodeList nl = query.getChildNodes();
		int i = 0;
		Node files = null;
		while (i < nl.getLength()) {
			files = nl.item(i);
			if ((files.getNodeType() == Node.ELEMENT_NODE) && (files.getNodeName().equals("files"))) {
				return (files);
			}
			i++;
		}
		// TODO: Log error.
		return (null);
	}

	private String[] getParamNames(Node data) {
		// TODO: Create a class for XPath processing...
		if (data == null) {
			// TODO: Log error.
			return(null);
		}
		NodeList nl = null;
		try {
			nl = XMLTools.getInstance().selectNodeList(data, "/query/data//param[@name]");
		} catch (Exception e) {
			ProviderMonitor.getInstance().reportException(e);
			e.printStackTrace();
		}
		String[] names = new String[nl.getLength()];
		for (int i = 0 ; i < nl.getLength() ; i++) {
			names[i] = nl.item(i).getAttributes().getNamedItem("name").getNodeValue();
		}
		return(names);
	}

	public String[] getParamNames() {
		return(getParamNames(getData()));
	}
	

	public boolean isQueryForFiles() {
		return (nature == FILES);
	}
	
	public String toString() {
		return ("PROVIDER QUERY [" + ((nature == FILES) ? "FILES" : "QUERY") + "](" + (generic ? "Generic Query" : ("Specific Query for archive " + idArchive)) + ")\nCONTEXT:\n" + context.toXML() + "\nQUERY:\n" + XMLUtils.nodeToString(query));
	}

}
