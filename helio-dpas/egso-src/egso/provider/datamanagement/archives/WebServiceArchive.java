package org.egso.provider.datamanagement.archives;

import org.egso.provider.admin.ProviderMonitor;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class WebServiceArchive extends Archive {

	private String uri = null;
	private String parserClass = null;


	public WebServiceArchive(Node n) {
		super(Archive.WEB_SERVICES_ARCHIVE, n);
		init(n);
	}

	public String getResultParser() {
		return (parserClass);
	}
	
	public String getURI() {
		return (uri);
	}

	private void init(Node n) {
		NodeList nl = n.getChildNodes();
		Node tmp = null;
		Node tmp2 = null;
		NamedNodeMap atts = null;
		for (int i = 0 ; i < nl.getLength() ; i++) {
			tmp = nl.item(i);
			if (tmp.getNodeType() == Node.ELEMENT_NODE) {
				if (tmp.getLocalName().equals("connexion")) {
					NodeList children = tmp.getChildNodes();
					for (int j = 0 ; j < children.getLength() ; j++) {
						tmp2 = children.item(j);
						if (tmp2.getNodeType() == Node.ELEMENT_NODE) {
							atts = tmp2.getAttributes();
							if (tmp2.getLocalName().equals("url")) {
								try {
									 url= atts.getNamedItem("url").getNodeValue();
									 uri= atts.getNamedItem("uri").getNodeValue();
									 port= Integer.parseInt(atts.getNamedItem("port").getNodeValue());
								} catch (Exception e) {
									ProviderMonitor.getInstance().reportException(e);
									System.out.println("Error in Web Service Archive Object creation:");
									e.printStackTrace();
								}
							} else {
								if (tmp2.getLocalName().equals("result-parser")) {
									try {
										parserClass = atts.getNamedItem("class").getNodeValue();
									} catch (Exception e) {
										ProviderMonitor.getInstance().reportException(e);
										System.out.println("Error in Web Service Archive Object creation:");
										e.printStackTrace();
									}
								} else {
									System.out.println("Node '" + tmp2.getNodeName() + "' not considered as a <connexion> children for WS archives.");
								}
							}
						}
					}
				}
			}
		}
	}


}
