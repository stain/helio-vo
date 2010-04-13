package org.egso.votable.element;

import java.util.Arrays;


/**
 *  JAVADOC: Description of the Class
 *
 * @author     Romain Linsolas (linsolas@gmail.com)
 * @version    1.0
 * @created    28 October 2003
 */
public class Option extends VOTableElement {

	/**
	 *  JAVADOC: Description of the Field
	 */
	private VOTableSet option = null;
	
	
	/**
	 *  JAVADOC: Constructor for the Option object
	 */
	public Option(String value) {
		super("OPTION");
		addAttribute(new Attribute("name"));
		addAttribute(new Attribute("value", value, Attribute.REQUIRED));
		option = new VOTableSet(VOTableSet.OPTION, VOTableSet.ZERO_MANY_ELT);
	}


	/**
	 *  JAVADOC: Sets the name attribute of the Option object
	 *
	 * @param  name  JAVADOC: The new name value
	 */
	public void setName(String name) {
		getAttribute("name").setValue(name);
	}


	/**
	 *  JAVADOC: Sets the value attribute of the Option object
	 *
	 * @param  val  JAVADOC: The new value value
	 */
	public void setValue(String val) {
		getAttribute("value").setValue(val);
	}


	/**
	 *  JAVADOC: Gets the name attribute of the Option object
	 *
	 * @return    JAVADOC: The name value
	 */
	public String getName() {
		return (getAttribute("name").getValue());
	}


	/**
	 *  JAVADOC: Gets the value attribute of the Option object
	 *
	 * @return    JAVADOC: The value value
	 */
	public String getValue() {
		return (getAttribute("value").getValue());
	}


	/**
	 *  JAVADOC: Gets the option attribute of the Option object
	 *
	 * @return    JAVADOC: The option value
	 */
	public VOTableSet getOption() {
		return (option);
	}


	/**
	 *  JAVADOC: Adds a feature to the Option attribute of the Option object
	 *
	 * @param  o  JAVADOC: The feature to be added to the Option attribute
	 */
	public void addOption(Option o) {
		option.add(o);
	}


	/**
	 *  JAVADOC: Adds a feature to the Options attribute of the Option object
	 *
	 * @param  o  JAVADOC: The feature to be added to the Options attribute
	 */
	public void addOptions(Option[] o) {
	  option.addAll(Arrays.asList(o));
	}

	/**
	 *  String representation of the element.
	 *
	 * @return    Representation of the element.
	 */
	public String toString() {
		return ("<OPTION " + attributes.toString() + ((option.size() != 0) ? (">" + option.toString() + "</OPTION>") : "/>"));
	}

	
}

