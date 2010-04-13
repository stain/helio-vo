package org.egso.provider.datamanagement.archives;

import org.egso.provider.admin.ProviderMonitor;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class SQLArchive extends Archive {

	private String user = null;
	private String password = null;
	private String sid = null;
	private String driverName = null;
	private String driverType = null;
	private String driverDB = null;
	private int timeout = 0;
	public static final int ORACLE = 1;
	public static final int MYSQL = 2;
	public static final int POSTGRESQL = 3;
	public static final int SQLDATABASE = 0;
	private static final String[] db_types = {"SQL", "Oracle", "MySQL", "PostgreSQL"};
	private int db_type = SQLDATABASE;
	private int DEFAULT_MAXIMUM_RESULTS = 200;
	private int maximum_results = DEFAULT_MAXIMUM_RESULTS;
	private Base base = null;


	public SQLArchive(Node n) {
		super(Archive.SQL_ARCHIVE, n);
		init(n);
	}
	
	public String getCompleteURL() {
		switch (db_type) {
			case ORACLE:
				return("jdbc:" + driverDB + ":" + driverType + ":@" + url + ":" + port + ":" + sid);
			case POSTGRESQL:
				return("jdbc:" + driverType + "://" + url + ":" + port + "/" + sid);
		}
		return("jdbc:" + driverDB + ":" + driverType + ":@" + url + ":" + port + ":" + sid);
	}
	
	public String getUser() {
		return(user);
	}
	
	public String getPassword() {
		return(password);
	}
	
	public String getSID() {
		return(sid);
	}
	
	public String getDriverName() {
		return(driverName);
	}
	
	public String getDriverType() {
		return(driverType);
	}
	
	public String getDriverDB() {
		return(driverDB);
	}
	
	public int getTimeout() {
		return(timeout);
	}
	
	public int getDatabase() {
		return (db_type);
	}
	
	public String getDatabaseAsString() {
		return (db_types[db_type]);
	}
	
	public boolean isOracle() {
		return (db_type == ORACLE);
	}
	
	public boolean isMySQL() {
		return (db_type == MYSQL);
	}
	
	public boolean isPostgreSQL() {
		return (db_type == POSTGRESQL);
	}
	
	public int getMaximumResults() {
		return (maximum_results);
	}
	
	public void setMaximumResults(int max) {
		if (max > 0) {
			maximum_results = max;
		}
	}
	
	private void init(Node n) {
		NodeList nl = n.getChildNodes();
		Node tmp = null;
		Node tmp2 = null;
		NamedNodeMap atts = null;
		for (int i = 0 ; i < nl.getLength() ; i++) {
			tmp = nl.item(i);
			if (tmp.getNodeType() == Node.ELEMENT_NODE) {
				if (tmp.getNodeName().equals("connexion")) {
					String db = tmp.getAttributes().getNamedItem("database").getNodeValue();
					if (db.equals("oracle")) {
						db_type = ORACLE;
					} else {
						if (db.equals("postgresql")) {
							db_type = POSTGRESQL;
						} else {
							if (db.equals("mysql")) {
								db_type = MYSQL;
							} else {
								db_type = SQLDATABASE;
							}
						}
					}
					NodeList children = tmp.getChildNodes();
					for (int j = 0 ; j < children.getLength() ; j++) {
						tmp2 = children.item(j);
						if (tmp2.getNodeType() == Node.ELEMENT_NODE) {
							atts = tmp2.getAttributes();
							if (tmp2.getNodeName().equals("url")) {
								try {
									 url = atts.getNamedItem("url").getNodeValue();
									 port = Integer.parseInt(atts.getNamedItem("port").getNodeValue());
								} catch (Exception e) {
									ProviderMonitor.getInstance().reportException(e);
									System.out.println("Error in SQL Archive Object creation:");
									e.printStackTrace();
								}
							} else {
								if (tmp2.getNodeName().equals("login")) {
									try {
										user = atts.getNamedItem("user").getNodeValue();
										password = atts.getNamedItem("password").getNodeValue();
										sid = atts.getNamedItem("sid").getNodeValue();
									} catch (Exception e) {
										ProviderMonitor.getInstance().reportException(e);
										System.out.println("Error in SQL Archive Object creation:");
										e.printStackTrace();
									}
								} else {
									if (tmp2.getNodeName().equals("driver")) {
										try {
											 driverName = atts.getNamedItem("name").getNodeValue();
											 driverType = atts.getNamedItem("type").getNodeValue();
											 driverDB = atts.getNamedItem("db").getNodeValue();
											 timeout = Integer.parseInt(atts.getNamedItem("timeout").getNodeValue());
										} catch (Exception e) {
											ProviderMonitor.getInstance().reportException(e);
											System.out.println("Error in SQL Archive Object creation:");
											e.printStackTrace();
										}
									} else {
										System.out.println("Node '" + tmp2.getNodeName() + "' not considered as a <connexion> children for SQL archives.");
									}
								}
							}
						}
					}
				}
			}
		}
		SQLBaseFactory factory = SQLBaseFactory.newInstance();
		base = factory.createBase(confNode);
	}

	public Base getBase() {
		return (base);
	}

	public String toString() {
		return(super.toString() + "\n\tDatabase nature: " + db_types[db_type] + "\n\tLogin: user=" + user + " | password=" + password + " | sid=" + sid + "\n\tDriver: name=" + driverName + " | type=" + driverType + " | db=" + driverDB + " | timeout=" + timeout);
	}

}
