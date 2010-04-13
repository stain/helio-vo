package org.egso.provider.datamanagement.archives.mapping;


import java.util.Enumeration;
import java.util.Hashtable;

import org.w3c.dom.Node;


public class WebServicesMappingObject implements MappingObject {


	private String nameEGSO = null;
	private String valueArchive = null;
	private Hashtable<String,String> egso2archive = null;
	private Hashtable<String,String> archive2egso = null;


	public WebServicesMappingObject(Node node) {
		egso2archive = new Hashtable<String,String>();
		archive2egso = new Hashtable<String,String>();
		init(node);
	}
	
	private void init(Node node) {
		// TODO
		
	}
	

	public String getEGSOName() {
		return (nameEGSO);
	}

	public String getArchiveName() {
		return (valueArchive);
	}

	public String[] getArchiveNames() {
		return (new String[] {valueArchive});
	}

	public String egso2archive(String value) {
		return egso2archive.get(value);
	}

	public String archive2egso(String value) {
		return archive2egso.get(value);
	}

	public int getType() {
		return (MappingObject.WEB_SERVICES);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("WEB SERVICES Mapping Object for the EGSO parameter '" + nameEGSO + "'\n");
		sb.append("Archive Value: " + valueArchive);
		sb.append("MAPPING TABLE :\nEGSO -> ARCHIVE:\n");

		for (Enumeration<String> e = egso2archive.keys() ; e.hasMoreElements() ; ) {
			String tmp = e.nextElement();
			sb.append("\t" + tmp + " -> " + egso2archive.get(tmp) + "\n");
		}
		sb.append("ARCHIVE -> EGSO:\n");
		for (Enumeration<String> e = archive2egso.keys() ; e.hasMoreElements() ; ) {
			String tmp = (String) e.nextElement();
			sb.append("\t" + tmp + " -> " + archive2egso.get(tmp) + "\n");
		}
		return (sb.toString());
	}


}

