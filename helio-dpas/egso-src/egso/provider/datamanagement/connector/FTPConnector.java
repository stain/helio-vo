package org.egso.provider.datamanagement.connector;

import java.util.StringTokenizer;
import java.util.Vector;

import javax.activation.DataHandler;

import org.egso.common.context.EGSOContext;
import org.egso.provider.ProviderConfiguration;
import org.egso.provider.admin.ProviderMonitor;
import org.egso.provider.datamanagement.archives.FTPArchive;
import org.egso.provider.query.FTPQuery;
import org.egso.provider.query.ProviderTable;


/**
 *  TODO: Description of the Class
 *
 * @author     Romain Linsolas (linsolas@gmail.com)
 * @version    0.9.1 - 25/03/2004 [14/10/2003]
 */
/*
0.9.1 - 25/03/2004:
	Bug correction, when an FTP instrument appears more than once in a query.
TODO:
	Un-hardcode results formatting.
*/
public class FTPConnector implements Connector {

	private FTPQuery query = null;
	private FTPArchive archive = null;
	private ProviderTable providerTable = null;
	private ResultsFormatter formatter = null;


	/**
	 *  TODO: Constructor for the DataPresentationManagerImpl object
	 */
	public FTPConnector(FTPArchive arc, FTPQuery fq, ProviderTable results, ResultsFormatter rf) {
		archive = arc;
		query = fq;
		if (!fq.isValid()) {
			System.out.println("ERROR: The FTPConnector can't be created because the sequence of the FTP Command is invalid:\n" + fq.whyNotValid());
		}
		providerTable = results;
		formatter = rf;
	}

	public void query() {
		ProviderConfiguration pc = ProviderConfiguration.getInstance();
		if (((String) pc.getProperty("archives.ftp.gateway.client")).equals("true")) {
			query.useGateway((String) pc.getProperty("archives.ftp.gateway.url"), archive.getURL(), "" + archive.getPort());
		} else {
			// Creation of the FTPConnection.
			FTPConnection ftp = null;
			try {
				ftp = new FTPConnection(archive.getURL(), archive.getPort());
				query.setFTPConnection(ftp);
			} catch (Throwable t) {
				ProviderMonitor.getInstance().reportException(t);
			}
		}
		String results = null;
		// Execution of all commands.
		System.out.println("# FTP COMMANDS EXECUTION #");
		int nbLists = query.howManyCommands(FTPQuery.LIST) + query.howManyCommands(FTPQuery.DETAILED_LIST);
		int i = 1;
		try {
			String file = null;
			String link = null;
			StringTokenizer st = null;
			String[] fileInfo = null;
			String tmp = null;
			Vector<String> fileSet = new Vector<String>();
			long startTime = System.currentTimeMillis();
			while (query.hasLeftCommands()) {
				if ((query.getNextCommandType() == FTPQuery.LIST) || (query.getNextCommandType() == FTPQuery.DETAILED_LIST)) {
					System.out.print("[ftp:" + i++ + "/" + nbLists + "] ");
				}
				results = query.executeNextCommand();
				if (results != null) {
					st = new StringTokenizer(results, "\n");
					while (st.hasMoreTokens()) {
						link = st.nextToken().trim();
						// Gets all information in a String[], where all information is separated with one or multiple whitespaces.
						fileInfo = link.split("( )+");
						file = fileInfo[8].substring(fileInfo[8].lastIndexOf('/') + 1);
						tmp = query.getLastExecutedCommandParameters()[0];
						link = tmp.substring(0, tmp.lastIndexOf('/') + 1) + file;
						if (!fileSet.contains(file)) {
							// Test if the file has been already processed.
							fileSet.add(file);
							link = "ftp://" + archive.getURL() + "/" + archive.getRootPath() + link;
							formatter.addResults(new String[] {link, file, fileInfo[4]});
						}
					}
				}
			}
			float timeTaken = ((float) (System.currentTimeMillis() - startTime)) / 1000;
			providerTable.getContext().addParameter("Query time for '" + archive.getID() + "' FTP archive", EGSOContext.PARAMETER_SYSTEMINFO, "" + timeTaken + " s");
			System.out.println();
			formatter.finish(providerTable);
		} catch (Throwable t) {
			ProviderMonitor.getInstance().reportException(t);
			System.out.println("ERROR while executing FTP Commands:");
			return;
		}
	}

	
	/**
	 *  TODO: Gets the files attribute of the Connector object
	 *
	 *@param  query  TODO: Description of the Parameter
	 *@return        TODO: The files value
	 */
	public DataHandler getFiles() {
		return (null);
	}

	public boolean testConnection() {
		return(true);
	}
	
}

