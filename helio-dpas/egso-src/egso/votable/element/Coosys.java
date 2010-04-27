package org.egso.votable.element;


/**
 *  JAVADOC: Description of the Class
 *
 * @author     Romain Linsolas (linsolas@gmail.com)
 * @version 1.0
 * @created    28 October 2003
 */
public class Coosys extends VOTableElement {

	/**
	 *  JAVADOC: Description of the Field
	 */
	private String content = null;


	/**
	 *  JAVADOC: Constructor for the Coosys object
	 */
	public Coosys() {
		super("COOSYS");
		init();
	}


	/**
	 *  JAVADOC: Constructor for the Coosys object
	 *
	 * @param  coosysContent  JAVADOC: Description of the Parameter
	 */
	public Coosys(String coosysContent) {
		super("COOSYS");
		content = coosysContent;
		init();
	}


	/**
	 *  JAVADOC: Sets the content attribute of the Coosys object
	 *
	 * @param  cnt  JAVADOC: The new content value
	 */
	public void setContent(String cnt) {
		content = cnt;
	}


	/**
	 *  JAVADOC: Sets the iD attribute of the Coosys object
	 *
	 * @param  id  JAVADOC: The new iD value
	 */
	public void setID(String id) {
		getAttribute("ID").setValue(id);
	}


	/**
	 *  JAVADOC: Sets the equinox attribute of the Coosys object
	 *
	 * @param  eq  JAVADOC: The new equinox value
	 */
	public void setEquinox(String eq) {
		getAttribute("equinox").setValue(eq);
	}


	/**
	 *  JAVADOC: Sets the epoch attribute of the Coosys object
	 *
	 * @param  ep  JAVADOC: The new epoch value
	 */
	public void setEpoch(String ep) {
		getAttribute("epoch").setValue(ep);
	}


	/**
	 *  JAVADOC: Sets the system attribute of the Coosys object
	 *
	 * @param  sys  JAVADOC: The new system value
	 */
	public void setSystem(String sys) {
		getAttribute("system").setValue(sys);
	}



	/**
	 *  JAVADOC: Gets the content attribute of the Coosys object
	 *
	 * @return    JAVADOC: The content value
	 */
	public String getContent() {
		return (content);
	}


	/**
	 *  JAVADOC: Gets the iD attribute of the Coosys object
	 *
	 * @return    JAVADOC: The iD value
	 */
	public String getID() {
		return (getAttribute("ID").getValue());
	}


	/**
	 *  JAVADOC: Gets the equinox attribute of the Coosys object
	 *
	 * @return    JAVADOC: The equinox value
	 */
	public String getEquinox() {
		return (getAttribute("equinox").getValue());
	}


	/**
	 *  JAVADOC: Gets the epoch attribute of the Coosys object
	 *
	 * @return    JAVADOC: The epoch value
	 */
	public String getEpoch() {
		return (getAttribute("epoch").getValue());
	}


	/**
	 *  JAVADOC: Gets the system attribute of the Coosys object
	 *
	 * @return    JAVADOC: The system value
	 */
	public String getSystem() {
		return (getAttribute("system").getValue());
	}


	/**
	 *  JAVADOC: Description of the Method
	 */
	private void init() {
		addAttribute(new Attribute("ID"));
		addAttribute(new Attribute("equinox"));
		addAttribute(new Attribute("epoch"));
		addAttribute(new Attribute("system", null, "eq_FK5", new String[]{"eq_FK4", "eq_FK5", "ICRS", "ecl_FK4", "ecl_FK5", "galactic", "supergalactic", "xy", "barycentric", "geo_app"}));
	}


	/**
	 * A String representation of the element.
	 *
	 * @return A String representation of the element.
	 **/
	public String toString() {
		return ("<COOSYS " + attributes.toString() + ">" + content + "</COOSYS>");
	}
}

