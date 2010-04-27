package org.egso.votable.element;

import java.util.Arrays;


/**
 *  JAVADOC: Description of the Class
 *
 * @author     Romain Linsolas (linsolas@gmail.com)
 * @version    1.0
 * @created    28 October 2003
 */
public class Values extends VOTableElement {

	/**
	 *  JAVADOC: Description of the Field
	 */
	private Min min = null;
	/**
	 *  JAVADOC: Description of the Field
	 */
	private Max max = null;
	/**
	 *  JAVADOC: Description of the Field
	 */
	private VOTableSet option = null;


	/**
	 *  JAVADOC: Constructor for the Values object
	 */
	public Values() {
		super("VALUES");
		init();
	}


	/**
	 *  JAVADOC: Description of the Method
	 *
	 * @param  id  JAVADOC: Description of the Parameter
	 */
	public void setID(String id) {
		getAttribute("ID").setValue(id);
	}


	/**
	 *  JAVADOC: Sets the type attribute of the Values object
	 *
	 * @param  type  JAVADOC: The new type value
	 */
	public void setType(String type) {
		getAttribute("type").setValue(type);
	}


	/**
	 *  JAVADOC: Sets the null attribute of the Values object
	 *
	 * @param  nul  JAVADOC: The new null value
	 */
	public void setNull(String nul) {
		getAttribute("null").setValue(nul);
	}


	/**
	 *  JAVADOC: Description of the Method
	 *
	 * @param  invalid  JAVADOC: Description of the Parameter
	 */
	public void setInvalid(String invalid) {
		getAttribute("invalid").setValue(invalid);
	}


	/**
	 *  JAVADOC: Gets the iD attribute of the Values object
	 *
	 * @return    JAVADOC: The iD value
	 */
	public String getID() {
		return (getAttribute("ID").getValue());
	}


	/**
	 *  JAVADOC: Gets the type attribute of the Values object
	 *
	 * @return    JAVADOC: The type value
	 */
	public String getType() {
		return (getAttribute("type").getValue());
	}


	/**
	 *  JAVADOC: Gets the null attribute of the Values object
	 *
	 * @return    JAVADOC: The null value
	 */
	public String getNull() {
		return (getAttribute("null").getValue());
	}


	/**
	 *  JAVADOC: Gets the invalid attribute of the Values object
	 *
	 * @return    JAVADOC: The invalid value
	 */
	public String getInvalid() {
		return (getAttribute("invalid").getValue());
	}


	/**
	 *  JAVADOC: Gets the min attribute of the Values object
	 *
	 * @return    JAVADOC: The min value
	 */
	public Min getMin() {
		return (min);
	}


	/**
	 *  JAVADOC: Gets the max attribute of the Values object
	 *
	 * @return    JAVADOC: The max value
	 */
	public Max getMax() {
		return (max);
	}


	/**
	 *  JAVADOC: Gets the option attribute of the Values object
	 *
	 * @return    JAVADOC: The option value
	 */
	public VOTableSet getOption() {
		return (option);
	}


	/**
	 *  JAVADOC: Description of the Method
	 */
	private void init() {
		addAttribute(new Attribute("ID"));
		addAttribute(new Attribute("type", null, "legal", new String[]{"legal", "actual"}));
		addAttribute(new Attribute("null"));
		addAttribute(new Attribute("invalid", null, "no", new String[]{"yes", "no"}));
		option = new VOTableSet(VOTableSet.OPTION, VOTableSet.ZERO_MANY_ELT);
	}


	/**
	 *  JAVADOC: Adds a feature to the Min attribute of the Values object
	 *
	 * @param  m  JAVADOC: The feature to be added to the Min attribute
	 */
	public void setMin(Min m) {
		min = m;
	}


	/**
	 *  JAVADOC: Adds a feature to the Max attribute of the Values object
	 *
	 * @param  m  JAVADOC: The feature to be added to the Max attribute
	 */
	public void setMax(Max m) {
		max = m;
	}


	/**
	 *  JAVADOC: Adds a feature to the Option attribute of the Values object
	 *
	 * @param  o  JAVADOC: The feature to be added to the Option attribute
	 */
	public void addOption(Option o) {
		option.add(o);
	}


	/**
	 *  JAVADOC: Adds a feature to the Options attribute of the Values object
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
		return ("<VALUES " + attributes.toString() + ">" + ((min != null) ? min.toString() : "") + ((max != null) ? max.toString() : "") +
			option.toString() + "</VALUES>");
	}


}

