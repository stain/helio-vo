package org.egso.provider.datamanagement.archives;

import org.w3c.dom.Node;


public class MixedArchive extends Archive {


	private Archive connector = null;
	private Base sqlBase = null;

	public MixedArchive(Node n) {
		super(Archive.MIXED_ARCHIVE, n);
		switch (connectorType) {
			case Archive.FTP_CONNECTOR:
					connector = new FTPArchive(n);
				break;
			case Archive.SQL_CONNECTOR:
					connector = new SQLArchive(n);
				break;
			case Archive.WEB_SERVICES_CONNECTOR:
					connector = new WebServiceArchive(n);
				break;
			default:
				System.out.println("Invalid Connector type for the MIXED archive " + id + ".");
		}
		if (mapperType == SQL_MAPPER) {
			SQLBaseFactory factory = SQLBaseFactory.newInstance();
			sqlBase = factory.createBase(confNode);
		}
	}

	public Archive getConnectorArchive() {
		return (connector);
	}

	public Base getBase() {
		return (sqlBase);
	}

}
