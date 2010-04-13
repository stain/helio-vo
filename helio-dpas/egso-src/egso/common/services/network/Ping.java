package org.egso.common.services.network;


/**
 * Tests the accessibility of a role.
 *
 * @author    Romain Linsolas (linsolas@gmail.com).
 * @version   1.0 - 15/09/2004 [15/09/2004]
 */
/*
1.0 - 15/09/2004:
	First implementation of the interface.
*/
public interface Ping {

	/**
	 * Name of the avalon Role.
	 */
	public final static String ROLE = Ping.class.getName();


	/**
	 * Tests the accessibility of the role.
	 *
	 * @param sender  Name (ie NDS ID) of the role who is sending the PING.
	 * @return        Name (ie NDS ID) of the role who is receiving the PING.
	 */
	public String ping(String sender);

}
