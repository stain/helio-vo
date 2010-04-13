package org.egso.votable.element;


/**
 *  JAVADOC: Description of the Class
 *
 * @author     Romain Linsolas (linsolas@gmail.com)
 * @version 1.0
 * @created    28 October 2003
 */
public class Definition extends VOTableElement {

	/**
	 *  JAVADOC: Description of the Field
	 */
	private Coosys coosys = null;
	/**
	 *  JAVADOC: Description of the Field
	 */
	private Param param = null;


	/**
	 *  JAVADOC: Constructor for the Definition object
	 */
	public Definition() {
		super("DEFINITION");
	}


	/**
	 *  JAVADOC: Constructor for the Definition object
	 *
	 * @param  co  JAVADOC: Description of the Parameter
	 */
	public Definition(Coosys co) {
		super("DEFINITION");
		coosys = co;
	}


	/**
	 *  JAVADOC: Constructor for the Definition object
	 *
	 * @param  par  JAVADOC: Description of the Parameter
	 */
	public Definition(Param par) {
		super("DEFINITION");
		param = par;
	}


	/**
	 *  JAVADOC: Constructor for the Definition object
	 *
	 * @param  co   JAVADOC: Description of the Parameter
	 * @param  par  JAVADOC: Description of the Parameter
	 */
	public Definition(Coosys co, Param par) {
		super("DEFINITION");
		coosys = co;
		param = par;
	}


	/**
	 *  JAVADOC: Sets the coosys attribute of the Definition object
	 *
	 * @param  c  JAVADOC: The new coosys value
	 */
	public void setCoosys(Coosys c) {
		coosys = c;
	}


	/**
	 *  JAVADOC: Sets the param attribute of the Definition object
	 *
	 * @param  p  JAVADOC: The new param value
	 */
	public void setParam(Param p) {
		param = p;
	}


	/**
	 *  JAVADOC: Gets the coosys attribute of the Definition object
	 *
	 * @return    JAVADOC: The coosys value
	 */
	public Coosys getCoosys() {
		return (coosys);
	}


	/**
	 *  JAVADOC: Gets the param attribute of the Definition object
	 *
	 * @return    JAVADOC: The param value
	 */
	public Param getParam() {
		return (param);
	}



	/**
	 *  String representation of the element.
	 *
	 * @return    Representation of the element.
	 */
	public String toString() {
		return (((coosys != null) ? coosys.toString() : "") + ((param != null) ? param.toString() : ""));
	}


}
