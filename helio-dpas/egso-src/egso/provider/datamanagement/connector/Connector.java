package org.egso.provider.datamanagement.connector;

import javax.activation.DataHandler;


/**
 *  TODO: Description of the Interface
 *
 * @author     Romain Linsolas (linsolas@gmail.com)
 * @version    1.0 - 26/11/2003 [14/10/2003].
 */
public interface Connector {


	/**
	 *  TODO: Description of the Method
	 *
	 * @return          TODO: Description of the Return Value
	 */
	public void query();


	/**
	 *  TODO: Gets the files attribute of the Connector object
	 *
	 * @return          TODO: The files value
	 */
	public DataHandler getFiles();


	/**
	 *  Test the connection to the archive.
	 *
	 * @return          JAVADOC: Description of the Return Value
	 */
	public boolean testConnection();


}

