package org.egso.provider.datamanagement.archives;

import org.egso.provider.admin.ProviderMonitor;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * This class contains the description of an FTP archive.
 *
 * @author    Romain Linsolas (linsolas@gmail.com)
 * @version   1.0 - 29/11/2003 [29/11/2003].
 */
public class FTPArchive extends Archive {

	/**
	 * User name for the FTP server connection.
	 */
	private String user = null;
	/**
	 * Password value for the FTP server connection.
	 */
	private String password = null;
	/**
	 * Boolean that indicates if the connection is anonymous or not.
	 */
	private boolean anonymous = true;
	/**
	 * Root directory where data are stored.
	 */
	private String rootPath = null;
	/**
	 * Boolean that indicates if the paths where data are stored are date
	 * dependent, i.e. if directories are created depending on the date of
	 * observations (one directory per day of observation, for example).
	 */
	private boolean dateDependentPath = false;
	private int datePathType = UNKNOWN;
	public final static int UNKNOWN = -1;
	public final static int YYYY_MM_DD = 0;
	public final static int YYYY_WW = 1;


	/**
	 * Constructor of the class.
	 *
	 * @param n  Description of the archive.
	 */
	public FTPArchive(Node n) {
		super(Archive.FTP_ARCHIVE, n);
		init(n);
	}


	/**
	 * Returns the name of the user for the connection to the FTP server.
	 *
	 * @return   Name of the user.
	 */
	public String getUser() {
		return (user);
	}


	/**
	 * Returns the password of the user for the connection to the FTP server
	 *
	 * @return   The password value.
	 */
	public String getPassword() {
		return (password);
	}


	/**
	 * Indicates if the connection is anonymous or not.
	 *
	 * @return   Is the connection anonymous?
	 */
	public boolean isAnonymous() {
		return (anonymous);
	}


	/**
	 * Gets the root directory on the FTP archive.
	 *
	 * @return   Root direcotry.
	 */
	public String getRootPath() {
		return (rootPath);
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


	/**
	 * Initializes the construction of the class.
	 *
	 * @param n  Archive description.
	 */
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
									port = Integer.parseInt(atts.getNamedItem("port").getNodeValue());
								} catch (Exception e) {
									ProviderMonitor.getInstance().reportException(e);
									System.out.println("Error in FTP Archive Object creation:");
									e.printStackTrace();
								}
							} else {
								if (tmp2.getNodeName().equals("login")) {
									try {
										user = atts.getNamedItem("user").getNodeValue();
										password = atts.getNamedItem("password").getNodeValue();
										anonymous = atts.getNamedItem("anonymous").getNodeValue().toLowerCase().equals("yes");
									} catch (Exception e) {
										ProviderMonitor.getInstance().reportException(e);
										System.out.println("Error in FTP Archive Object creation:");
										e.printStackTrace();
									}
								} else {
									if (tmp2.getNodeName().equals("path")) {
										try {
											rootPath = atts.getNamedItem("root").getNodeValue();
											dateDependentPath = atts.getNamedItem("dateDependentPath").getNodeValue().toLowerCase().equals("yes");
											if (dateDependentPath) {
												String tempo = atts.getNamedItem("type").getNodeValue();
												if (tempo.equals("YYYY/MM/DD")) {
													datePathType = YYYY_MM_DD;
												} else {
													if (tempo.equals("YYYY_WW")) {
														datePathType = YYYY_WW;
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
										System.out.println("Node '" + tmp2.getNodeName() + "' not considered as a <connexion> children for FTP archives.");
									}
								}
							}
						}
					}
				}
			}
		}
	}


	/**
	 * String representation of the FTP archive.
	 *
	 * @return   String representation of the archive.
	 */
	public String toString() {
		return (super.toString() + "\n\tAccess: anonymous=" + anonymous + " | login=" + user + " | password=" + password);
	}

}

