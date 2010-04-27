package org.egso.provider.datamanagement.archives;


import org.egso.provider.admin.ProviderMonitor;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class HTTPArchive extends Archive {

	/**
	 * Boolean that indicates if the paths where data are stored are date
	 * dependent, i.e. if directories are created depending on the date of
	 * observations (one directory per day of observation, for example).
	 */
	private boolean dateDependentPath = false;
	private int datePathType = UNKNOWN;
	public final static int UNKNOWN = -1;
	public final static int YYYY_MM_DD = 0;
	public final static int YYYY_DoY = 1;


	/**
	 * Constructor of the class.
	 *
	 * @param n  Description of the archive.
	 */
	public HTTPArchive(Node n) {
		super(Archive.HTTP_ARCHIVE, n);
		init(n);
	}


	private void init(Node n) {
		NodeList nl = n.getChildNodes();
		Node tmp = null;
		Node tmp2 = null;
		NamedNodeMap atts = null;
		for (int i = 0; i < nl.getLength(); i++) {
			tmp = nl.item(i);
			if (tmp.getNodeType() == Node.ELEMENT_NODE) {
				if (tmp.getNodeName().equals("connexion")) {
					NodeList children = tmp.getChildNodes();
					for (int j = 0; j < children.getLength(); j++) {
						tmp2 = children.item(j);
						if (tmp2.getNodeType() == Node.ELEMENT_NODE) {
							atts = tmp2.getAttributes();
							if (tmp2.getNodeName().equals("url")) {
								try {
									url = atts.getNamedItem("url").getNodeValue();
								} catch (Exception e) {
									ProviderMonitor.getInstance().reportException(e);
									System.out.println("Error in HTTP Archive Object creation:");
									e.printStackTrace();
								}
							} else {
								if (tmp2.getNodeName().equals("path")) {
									try {
										dateDependentPath = atts.getNamedItem("dateDependentPath").getNodeValue().toLowerCase().equals("yes");
										if (dateDependentPath) {
											String tempo = atts.getNamedItem("type").getNodeValue();
											if (tempo.equals("YYYY/MM/DD")) {
												datePathType = YYYY_MM_DD;
											} else {
												if (tempo.equals("YYYY/DoY")) {
													datePathType = YYYY_DoY;
												} else {
													datePathType = UNKNOWN;
												}
											}
										}
									} catch (Exception e) {
										ProviderMonitor.getInstance().reportException(e);
										System.out.println("Error in FTP Archive Object creation:");
										e.printStackTrace();
									}
								} else {
									System.out.println("Node '" + tmp2.getNodeName() + "' not considered as a <connexion> children for HTTP archives.");
								}
							}
						}
					}
				}
			}
		}
	}


	/**
	 * Indicates if the name of directories is dependent on the dates.
	 *
	 * @return   Are the directories names date dependent?
	 */
	public boolean hasDateDependentPath() {
		return (dateDependentPath);
	}

	public int getDatePathType() {
		return (datePathType);
	}


}

