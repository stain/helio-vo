package org.egso.common.context;

import java.net.InetAddress;
import java.net.UnknownHostException;



/**
 * This class is dedicated to the creation of EGSOContext objects.
 *
 * @author    Romain Linsolas (linsolas@gmail.com)
 * @version   1.0 - 20/09/2004 [20/09/2004]
 */
/*
1.0 - 20/09/2004:
	First implementation of the class.
*/
public class EGSOContextFactory {

	/**
	 * Type of the role that uses the EGSOContext.
	 */
	private int roleType = EGSOContext.ROLE_UNKNOWN;
	/**
	 * IP of the role that uses the EGSOContext.
	 */
	private String ipRole = null;
	/**
	 * Name of the role that uses the EGSOContext.
	 */
	private String roleName = null;
	/**
	 * Version of the role that uses the EGSOContext.
	 */
	private String roleVersion = null;


	/**
	 * Creates the EGSOContextFactory.
	 *
	 * @param roletype     Type of the role that uses the EGSOContext.
	 * @param rolename     Name of the role that uses the EGSOContext.
	 * @param roleversion  Version of the role that uses the EGSOContext.
	 * @param ip           IP of the role that uses the EGSOContext. If the value
	 *      is <code>null</code>, then the IP will be determined automatically
	 *      (better solution).
	 */
	private EGSOContextFactory(int roletype, String rolename, String roleversion, String ip) {
		roleType = roletype;
		roleName = rolename;
		roleVersion = roleversion;
		if (ip == null) {
			try {
				ipRole = InetAddress.getLocalHost().toString();
			} catch (UnknownHostException uhe) {
				System.out.println("EGSOContextFactory - Error can't found the IP");
				ipRole = "127.0.0.1";
			}
		} else {
			ipRole = ip;
		}
	}


	/**
	 * New instance of the EGSOContextFactory.
	 *
	 * @param roletype     Type of the role that uses the EGSOContext.
	 * @param rolename     Name of the role that uses the EGSOContext.
	 * @param roleversion  Version of the role that uses the EGSOContext.
	 * @return             A new instance of the EGSContextFactory.
	 */
	public static EGSOContextFactory newInstance(int roletype, String rolename, String roleversion) {
		return (new EGSOContextFactory(roletype, rolename, roleversion, null));
	}


	/**
	 * New instance of the EGSContextFactory, given a specified IP address.
	 *
	 * @param roletype     Type of the role that uses the EGSOContext.
	 * @param rolename     Name of the role that uses the EGSOContext.
	 * @param roleversion  Version of the role that uses the EGSOContext.
	 * @param ip           IP of the role that uses the EGSOContext.
	 * @return             New instance of the EGSOContextFactory.
	 */
	public static EGSOContextFactory newInstance(int roletype, String rolename, String roleversion, String ip) {
		return (new EGSOContextFactory(roletype, rolename, roleversion, ip));
	}


	/**
	 * Creates a new empty EGSOContext object.
	 *
	 * @param type  Type of EGSOContext.
	 * @return      New EGSOContext object.
	 */
	public EGSOContext createContext(int type) {
		return (new EGSOContext(type, roleType, roleName, roleVersion, ipRole));
	}


	/**
	 * Creates a new empty EGSOContext object.
	 *
	 * @param type  Type of the EGSOContext.
	 * @param id    ID of the Context.
	 * @return      New EGSOContext object.
	 */
	public EGSOContext createContext(int type, String id) {
		return (new EGSOContext(type, roleType, id, roleName, roleVersion, ipRole));
	}


	/**
	 * Creates a new EGSOContext object, given its content.
	 *
	 * @param xml  Content of the EGSOContext.
	 * @return     New EGSOContext object.
	 */
	public EGSOContext createContext(String xml) {
		return (new EGSOContext(xml, roleType, roleName, roleVersion, ipRole));
	}

}
