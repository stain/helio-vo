package org.egso.provider.admin;

import org.egso.provider.service.EGSOService;
import org.egso.provider.service.ServiceDescriptor;


public class ServiceMonitor extends Statistics {

	private ServiceDescriptor descriptor = null;

	public ServiceMonitor(EGSOService service) {
		super(Statistics.SERVICE_MONITOR);
		descriptor = service.getDescriptor();
		setInfo();
	}


	private void setInfo() {
		addStatistic("id", descriptor.getID());
		addStatistic("name", descriptor.getName());
		addStatistic("description", descriptor.getDescription());
		addStatistic("author", descriptor.getAuthor());
		addStatistic("version", descriptor.getVersion());
		addStatistic("date", descriptor.getDate());
		addStatistic("main-class", descriptor.getMainClass());
		addStatistic("jar-name", descriptor.getJarName());
		addStatistic("Last access", descriptor.getLastAccess());
		addStatistic("Number of accesses", "" + descriptor.getNumberOfAccesses());
	}

	public void refreshInfo() {
//		addStatistic("State", descriptor.getStateAsString());
		addStatistic("Last access", descriptor.getLastAccess());
		addStatistic("Number of accesses", "" + descriptor.getNumberOfAccesses());
	}



}
