package org.egso.votable.element;


/**
 *  JAVADOC: Description of the Class
 *
 * @author     Romain Linsolas (linsolas@gmail.com)
 * @version 1.0
 * @created    28 October 2003
 */
public class Definitions extends VOTableElement {

	/**
	 *  JAVADOC: Description of the Field
	 */
	private VOTableSet definitions = null;


	/**
	 *  JAVADOC: Constructor for the Definitions object
	 */
	public Definitions() {
		super("DEFINITIONS");
		definitions = new VOTableSet(VOTableSet.DEFINITION, VOTableSet.ZERO_MANY_ELT);
	}


	/**
	 *  JAVADOC: Gets the definitions attribute of the Definitions object
	 *
	 * @return    JAVADOC: The definitions value
	 */
	public VOTableSet getDefinitions() {
		return (definitions);
	}


	/**
	 *  JAVADOC: Adds a feature to the Definition attribute of the Definitions
	 *  object
	 *
	 * @param  d  JAVADOC: The feature to be added to the Definition attribute
	 */
	public void addDefinition(Definition d) {
		definitions.add(d);
	}


	/**
	 *  JAVADOC: Adds a feature to the Definition attribute of the Definitions
	 *  object
	 *
	 * @param  c  JAVADOC: The feature to be added to the Definition attribute
	 */
	public void addDefinition(Coosys c) {
		definitions.add(new Definition(c));
	}


	/**
	 *  JAVADOC: Adds a feature to the Definition attribute of the Definitions
	 *  object
	 *
	 * @param  p  JAVADOC: The feature to be added to the Definition attribute
	 */
	public void addDefinition(Param p) {
		definitions.add(new Definition(p));
	}


	/**
	 *  JAVADOC: Adds a feature to the Definition attribute of the Definitions
	 *  object
	 *
	 * @param  c  JAVADOC: The feature to be added to the Definition attribute
	 * @param  p  JAVADOC: The feature to be added to the Definition attribute
	 */
	public void addDefinition(Coosys c, Param p) {
		definitions.add(new Definition(c, p));
	}


	/**
	 *  String representation of the element.
	 *
	 * @return    Representation of the element.
	 */
	public String toString() {
		return ("<DEFINITIONS>" + definitions.toString() + "</DEFINITIONS>");
	}


}

