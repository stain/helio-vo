package org.egso.votable.element;

import java.util.Iterator;
import java.util.Vector;


/**
 *  JAVADOC: Description of the Class
 *
 * @author     Romain Linsolas (linsolas@gmail.com)
 * @version    1.0
 * @created    28 October 2003
 */
public class VOTableElement<E> {

	/**
	 *  JAVADOC: Description of the Field
	 */
	protected String tagName = null;
	/**
	 *  JAVADOC: Description of the Field
	 */
	protected VOTableSet<Attribute> attributes = null;


	/**
	 *  JAVADOC: Constructor for the VOTableElement object
	 *
	 * @param  name  JAVADOC: Description of the Parameter
	 */
	public VOTableElement(String name) {
		tagName = name;
		attributes = new VOTableSet<Attribute>(VOTableSet.ATTRIBUTE, VOTableSet.ZERO_MANY_ELT);
	}


	/**
	 *  JAVADOC: Gets the name attribute of the VOTableElement object
	 *
	 * @return    JAVADOC: The name value
	 */
	public String getTagName() {
		return (tagName);
	}


	/**
	 *  JAVADOC: Gets the attributes attribute of the VOTableElement object
	 *
	 * @return    JAVADOC: The attributes value
	 */
	public Attribute[] getAttributes() {
		return ((Attribute[]) attributes.toArray());
	}


	/**
	 *  JAVADOC: Gets the attribute attribute of the VOTableElement object
	 *
	 * @param  attributeName  JAVADOC: Description of the Parameter
	 * @return                JAVADOC: The attribute value
	 */
	public Attribute getAttribute(String attributeName) {
		Iterator<Attribute> it = attributes.iterator();
		boolean found = false;
		Attribute att = null;
		while (!found && it.hasNext()) {
			att = (Attribute) it.next();
			found = att.getName().equals(attributeName);
		}
		if (found) {
			return (att);
		}
		return (null);
	}


	/**
	 *  JAVADOC: Gets the impliedAttributes attribute of the VOTableElement object
	 *
	 * @return    JAVADOC: The impliedAttributes value
	 */
	public Attribute[] getImpliedAttributes() {
		Vector<Attribute> v = new Vector<Attribute>();
		Attribute att = null;
		for (Iterator<Attribute> it = attributes.iterator(); it.hasNext(); ) {
			att = (Attribute) it.next();
			if (!att.isRequired()) {
				v.add(att);
			}
		}
		return ((Attribute[]) v.toArray());
	}


	/**
	 *  JAVADOC: Gets the requiredAttributes attribute of the VOTableElement object
	 *
	 * @return    JAVADOC: The requiredAttributes value
	 */
	public Attribute[] getRequiredAttributes() {
		Vector<Attribute> v = new Vector<Attribute>();
		Attribute att = null;
		for (Iterator<Attribute> it = attributes.iterator(); it.hasNext(); ) {
			att = (Attribute) it.next();
			if (att.isRequired()) {
				v.add(att);
			}
		}
		return ((Attribute[]) v.toArray());
	}


	/**
	 *  JAVADOC: Adds a feature to the Attribute attribute of the VOTableElement
	 *  object
	 *
	 * @param  att  JAVADOC: The feature to be added to the Attribute attribute
	 */
	public void addAttribute(Attribute att) {
		if (!attributes.contains(att)) {
			attributes.add(att);
		}
	}

	
	/**
	 *  String representation of the element.
	 *
	 * @return    Representation of the element.
	 */
	public String toString() {
		return ("<" + tagName + " " + attributes.toString() + "/>");
	}

	
}

