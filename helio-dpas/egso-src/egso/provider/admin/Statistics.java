package org.egso.provider.admin;


import java.util.Enumeration;
import java.util.Hashtable;


public class Statistics {


	public static final int SYSTEM_INFORMATION = 0;
	public static final int ARCHIVE_MONITOR = 1;
	public static final int SERVICE_MONITOR = 2;
	public static final int OTHER = 3;
	private int nature = OTHER;
	private Hashtable<String,Object> stats = null;


	public Statistics(int statNature) {
		nature = statNature;
		stats = new Hashtable<String,Object>();
	}


	public int getNature() {
		return (nature);
	}
	
	public boolean isSystemInformation() {
		return (nature == SYSTEM_INFORMATION);
	}

	public boolean isArchive() {
		return (nature == ARCHIVE_MONITOR);
	}

	public boolean isService() {
		return (nature == SERVICE_MONITOR);
	}


	public Object addStatistic(String key, Object stat) {
		return (stats.put(key, stat));
	}


	public Object getStatistic(String key) {
		return (stats.get(key));
	}


	public boolean deleteStatistic(String key) {
		return (stats.remove(key) != null);
	}


	public boolean hasStatistic(String key) {
		return (stats.containsKey(key));
	}


	public Enumeration<String> getKeys() {
		return (stats.keys());
	}


	public Enumeration<Object> getStatistics() {
		return (stats.elements());
	}

	public int numberOfStatistics() {
		return(stats.size());
	}
}
