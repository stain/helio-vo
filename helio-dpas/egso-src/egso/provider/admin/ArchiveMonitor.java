package org.egso.provider.admin;

import org.egso.provider.datamanagement.archives.Archive;
import org.egso.provider.datamanagement.archives.SQLArchive;



/*
List of information for an archive:
+ State.
+ Last access.
+ Number of access.
+ Version of the description.
*/
public class ArchiveMonitor extends Statistics {

	private Archive archive = null;

	public ArchiveMonitor(Archive arc) {
		super(Statistics.ARCHIVE_MONITOR);
		archive = arc;
		setInfo();
	}

	private void setInfo() {
		addStatistic("ID", archive.getID());
		addStatistic("Name", archive.getName());
		addStatistic("Type", archive.getTypeAsString());
		addStatistic("URL", archive.getURL() + ":" + archive.getPort());
		addStatistic("Configuration file", archive.getConfFile());
//		addStatistic("Log file", archive.getLogsFile());
		addStatistic("State", archive.getStateAsString());
		addStatistic("Last access", archive.getLastAccess());
		addStatistic("Number of accesses", "" + archive.getNumberOfAccesses());
		if (archive.isSQL()) {
			addStatistic("Type of database", ((SQLArchive) archive).getDatabaseAsString());
		}
	}

	public void refreshInfo() {
		addStatistic("State", archive.getStateAsString());
		addStatistic("Last access", archive.getLastAccess());
		addStatistic("Number of accesses", "" + archive.getNumberOfAccesses());
	}

}
