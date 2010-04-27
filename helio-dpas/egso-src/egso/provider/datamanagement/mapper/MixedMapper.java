package org.egso.provider.datamanagement.mapper;

import java.util.Vector;

import org.egso.provider.datamanagement.archives.Archive;
import org.egso.provider.datamanagement.archives.FTPArchive;
import org.egso.provider.datamanagement.archives.MixedArchive;
import org.egso.provider.datamanagement.archives.SQLArchive;
import org.egso.provider.datamanagement.archives.WebServiceArchive;
import org.egso.provider.datamanagement.connector.FTPConnector;
import org.egso.provider.datamanagement.connector.ResultsFormatter;
import org.egso.provider.datamanagement.connector.SQLConnector;
import org.egso.provider.datamanagement.connector.WebServiceConnector;
import org.egso.provider.query.FTPQuery;
import org.egso.provider.query.ProviderTable;
import org.egso.provider.query.SQLQuery;
import org.egso.provider.query.WebServiceQuery;


public class MixedMapper implements Mapper {

	private MixedArchive archive = null;
	private SQLQuery sql = null;
	private FTPQuery ftp = null;
	private WebServiceQuery webservice = null;
	private ProviderTable results = null;


	public MixedMapper(MixedArchive arch, SQLQuery query, ProviderTable pt) {
		archive = arch;
		sql = query;
		results = pt;
	}
	
	public MixedMapper(MixedArchive arch, FTPQuery query, ProviderTable pt) {
		archive = arch;
		ftp = query;
		results = pt;
	}
	
	public MixedMapper(MixedArchive arch, WebServiceQuery query, ProviderTable pt) {
		archive = arch;
		webservice = query;
		results = pt;
	}
	
	public void query() {
		if (archive.getType() == archive.getConnectorType()) {
			System.out.println("[Mixed Mapper] Can't transform a " + archive.getTypeAsString() + " into the same kind of archive.");
			return;
		}
		ResultsFormatter rf = new ResultsFormatter(archive, results.getFields());
		switch (archive.getConnectorType()) {
			case Archive.FTP_CONNECTOR:
					if (sql != null) {
						SQL2FTP();
					} else {
						if (webservice != null) {
							WS2FTP();
						} else {
							System.out.println("[Mixed Mapper] Can't transform FTP to FTP.");
						}
					}
					FTPConnector ftpConnector = new FTPConnector((FTPArchive) archive.getConnectorArchive(), ftp, results, rf);
					ftpConnector.query();
				break;
			case Archive.SQL_CONNECTOR:
					if (ftp != null) {
						FTP2SQL();
					} else {
						if (webservice != null) {
							WS2SQL();
						} else {
							System.out.println("[Mixed Mapper] Can't transform SQL to SQL.");
						}
					}
					SQLConnector sqlConnector = new SQLConnector((SQLArchive) archive.getConnectorArchive(), sql, results, rf);
					sqlConnector.query();
				break;
			case Archive.WEB_SERVICES_CONNECTOR:
					if (sql != null) {
						SQL2WS();
					} else {
						if (ftp != null) {
							FTP2WS();
						} else {
							System.out.println("[Mixed Mapper] Can't transform WEBSERVICE to WEBSERVICE.");
						}
					}
					WebServiceConnector wsConnector = new WebServiceConnector((WebServiceArchive) archive.getConnectorArchive(), webservice, results);
					wsConnector.query();
				break;
			default:
				System.out.println("[Mixed Mapper] Don't know what to do here...");
		}
	}



	private void SQL2FTP() {
		System.out.println("[Mix Mapper] ERROR, the method 'SLQ2FTP' is not implemented yet.");
	}

	private void SQL2WS() {
		System.out.println("_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_");
		System.out.println("[Mix Mapper] Transforming the SQL Query into a Web Service call...");
		System.out.println("SQL Query:\n" + sql.toString());
		webservice = new WebServiceQuery();
		
		
		Vector<String> fieldsAsString=new Vector<String>();
		for(Object o:sql.getSelect())
		  if(o instanceof String)
		    fieldsAsString.add((String)o);
		  else
		    throw new RuntimeException("SQL query contained non-strings as fields");
		
		webservice.setSelectedFields(fieldsAsString);
		String sqlQuery = sql.toString();
		sqlQuery = sqlQuery.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		sb.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/ XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n");
		sb.append("  <soapenv:Body>\n");
		sb.append("    <sql xmlns=\"\">\n");
		sb.append("      <arg0 xsi:type=\"xsd:string\">\n");
		sb.append(sqlQuery);
		sb.append("\n       </arg0>\n");
		sb.append("    </sql>\n");
		sb.append("  </soapenv:Body>\n");
		sb.append("</soapenv:Envelope>");
		
		if(1==1)
		  throw new RuntimeException("FIXME FIXME FIXME");
		
		//webservice.addCall(sb.toString()); //<--- THIS CODE DOES NOT COMPILE
		System.out.println("WebService Query:\n" + webservice.toString());
		System.out.println("_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_");
	}

	private void FTP2WS() {
		System.out.println("[Mix Mapper] ERROR, the method 'FTP2WS' is not implemented yet.");
	}

	private void FTP2SQL() {
		System.out.println("[Mix Mapper] ERROR, the method 'FTP2SQL' is not implemented yet.");
	}

	private void WS2FTP() {
		System.out.println("[Mix Mapper] ERROR, the method 'WS2FTP' is not implemented yet.");
	}
	
	private void WS2SQL() {
		System.out.println("[Mix Mapper] ERROR, the method 'WS2SQL' is not implemented yet.");
	}

}
