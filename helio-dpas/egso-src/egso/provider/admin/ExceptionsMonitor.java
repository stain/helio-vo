package org.egso.provider.admin;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.egso.provider.ProviderConfiguration;
import org.egso.provider.utils.ProviderUtils;


public class ExceptionsMonitor {//extends Statistics {

	private LinkedList<String> exceptions = null;
	private LinkedList<String> dates = null;
	private int maxExceptions = 25;
	private Logger logger = null;


	public ExceptionsMonitor() {
		init();
	}

	private void init() {
		logger = Logger.getLogger(this.getClass().getName());
		maxExceptions = ((Integer) ProviderConfiguration.getInstance().getProperty("monitor.maximum-exceptions")).intValue();
		exceptions = new LinkedList<String>();
		dates = new LinkedList<String>();
	}

	public void report(Throwable t) {
		StringWriter sw = new StringWriter();
		t.printStackTrace(new PrintWriter(sw));
		exceptions.addFirst(sw.getBuffer().toString());
		dates.addFirst(ProviderUtils.getDate());
		if (exceptions.size() > maxExceptions) {
			exceptions.removeLast();
			dates.removeLast();
		}
		logger.error(t);
	}

	public void clean() {
		init();
	}

	public int getMaximumExceptions() {
		return(maxExceptions);
	}

	public int getNumberOfExceptions() {
		return(exceptions.size());
	}

	public Iterator<String> getExceptions() {
		return(exceptions.iterator());
	}
	
	public Iterator<String> getDates() {
		return(dates.iterator());
	}

}
