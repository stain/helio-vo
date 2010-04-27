package org.egso.provider.datamanagement.archives;


/**
 * Superclass for all SQL Elements (Table, Field and Link).
 *
 * @author    Romain Linsolas (linsolas@gmail.com)
 * @version   1.0 - 11/10/2004
 */
public class SQLElement {

	/**
	 * JAVADOC: Description of the Field
	 */
	public final static int TABLE = 0;
	/**
	 * JAVADOC: Description of the Field
	 */
	public final static int FIELD = 0;
	/**
	 * JAVADOC: Description of the Field
	 */
	public final static int LINK = 0;
	/**
	 * JAVADOC: Description of the Field
	 */
	private int elementType = -1;


	/**
	 * JAVADOC: Constructor for the SQLElement object
	 *
	 * @param type  JAVADOC: Description of the Parameter
	 */
	public SQLElement(int type) {
		elementType = type;
	}


	/**
	 * JAVADOC: Gets the elementType attribute of the SQLElement object
	 *
	 * @return   JAVADOC: The elementType value
	 */
	public int getElementType() {
		return (elementType);
	}

}

