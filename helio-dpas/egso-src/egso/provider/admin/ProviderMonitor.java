package org.egso.provider.admin;


import java.util.Enumeration;
import java.util.Hashtable;


public class ProviderMonitor {

	private Hashtable<String,ServiceMonitor> services = null;
	private Hashtable<String,ArchiveMonitor> archives = null;
	private SystemInformation provider = null;
	private static ProviderMonitor providerMonitor = null;
	private ExceptionsMonitor exceptionsMonitor = null;
	private String lastQuery = null;
	private String lastQueryValidated = null;
	private String lastQueryResult = null;


	private ProviderMonitor() {
		System.out.println("[Provider Monitor] Initialization.");
		services = new Hashtable<String,ServiceMonitor>();
		archives = new Hashtable<String,ArchiveMonitor>();
		exceptionsMonitor = new ExceptionsMonitor();
		lastQuery = "No query processed yet.";
		lastQueryValidated = "Query not validated yet.";
	}

	public static ProviderMonitor getInstance() {
		if (providerMonitor == null) {
			providerMonitor = new ProviderMonitor();
		}
		return (providerMonitor);
	}


	public SystemInformation getSystemInformation() {
		return (provider);
	}


	public ServiceMonitor getService(String id) {
		return ((ServiceMonitor) services.get(id));
	}

	public Enumeration<ServiceMonitor> getAllServices() {
		return (services.elements());
	}

	public ArchiveMonitor getArchive(String id) {
		return ((ArchiveMonitor) archives.get(id));
	}

	public Enumeration<ArchiveMonitor> getAllArchives() {
		return (archives.elements());
	}

	public ServiceMonitor addService(String id, ServiceMonitor sm) {
		return ((ServiceMonitor) services.put(id, sm));
	}

	public ArchiveMonitor addArchive(String id, ArchiveMonitor am) {
		return ((ArchiveMonitor) archives.put(id, am));
	}

	public void reportException(Throwable t) {
		exceptionsMonitor.report(t);
	}

	public ExceptionsMonitor getExceptionsMonitor() {
		return(exceptionsMonitor);
	}

	public void setSystemInformation(SystemInformation si) {
		provider = si;
	}

	public String getLastQuery(boolean validated) {
		return(validated ? lastQueryValidated : lastQuery);
	}

	public String getLastQueryResult() {
		return(lastQueryResult);
	}

	public void setLastQuery(String query, boolean validated) {
		if (validated) {
			lastQueryValidated = query;
		} else {
			lastQuery = query.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
			lastQueryValidated = "Query not validated yet.";
			lastQueryResult = null;
		}
	}

	public void setLastQueryResult(String result) {
		lastQueryResult = result;
	}
}

