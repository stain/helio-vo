package org.egso.provider.datamanagement.archives.mapping;



public interface MappingObject {


	public final static int FTP_HTTP = 0;
	public final static int SQL = 1;
	public final static int WEB_SERVICES = 2;


	public String getEGSOName();

	public String getArchiveName();

	public String[] getArchiveNames();

	public String egso2archive(String value);

	public String archive2egso(String value);

	public int getType();

}

