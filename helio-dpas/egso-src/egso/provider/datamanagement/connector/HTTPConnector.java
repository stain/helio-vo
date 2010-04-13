package org.egso.provider.datamanagement.connector;

import java.util.Vector;
import javax.activation.DataHandler;
import org.egso.common.context.EGSOContext;
import org.egso.provider.datamanagement.archives.HTTPArchive;
import org.egso.provider.query.HTTPQuery;
import org.egso.provider.query.ProviderTable;


/**
 * @author     Romain Linsolas (linsolas@gmail.com)
 * @version    0.9 - 11/01/2005 [11/01/2005]
 */
public class HTTPConnector implements Connector {

	private HTTPQuery query = null;
	private HTTPArchive archive = null;
	private ProviderTable providerTable = null;
	private ResultsFormatter formatter = null;


	public HTTPConnector(HTTPArchive arc, HTTPQuery hq, ProviderTable results, ResultsFormatter rf) {
		archive = arc;
		query = hq;
		providerTable = results;
		formatter = rf;
	}


	public void query() {
		int number_of_files = query.numberOfFiles();
		HTTPFile file = null;
		Vector<String> fileSet = new Vector<String>();
		long startTime = System.currentTimeMillis();
		for (int i = 0 ; i < number_of_files ; i++) {
			System.out.print("http(" + i + "/" + number_of_files + ")  ");
			file = query.listNextFile();
			if (file != null) {
				if (file.isDirectory()) {
					for (HTTPFile f:file.listFiles())
					{
						if (!fileSet.contains(f.getURL())) {
							fileSet.add(f.getURL());
							formatter.addResults(new String[] {f.getURL(), f.getFilename(), "" + f.getSize()});
						}
					}
				} else {
					if (!fileSet.contains(file.getURL())) {
						fileSet.add(file.getURL());
						formatter.addResults(new String[] {file.getURL(), file.getFilename(), "" + file.getSize()});
					}
				}
			}
		}
		float timeTaken = ((float) (System.currentTimeMillis() - startTime)) / 1000;
		providerTable.getContext().addParameter("Query time for '" + archive.getID() + "' HTTP archive", EGSOContext.PARAMETER_SYSTEMINFO, "" + timeTaken + " s");
		formatter.finish(providerTable);
	}


	public DataHandler getFiles() {
		return (null);
	}

	public boolean testConnection() {
		return(true);
	}

}

