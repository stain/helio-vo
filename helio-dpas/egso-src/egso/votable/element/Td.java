package org.egso.votable.element;


/**
 *  JAVADOC: Description of the Class
 *
 * @author     Romain Linsolas (linsolas@gmail.com)
 * @version 1.0
 * @created    28 October 2003
 */
public class Td<E> extends VOTableElement<E> {

	/**
	 *  JAVADOC: Description of the Field
	 */
	private String content = null;


	/**
	 *  JAVADOC: Constructor for the Td object
	 */
	public Td() {
		super("TD");
		addAttribute(new Attribute("ref"));
	}


	/**
	 *  JAVADOC: Constructor for the Td object
	 *
	 * @param  tdContent  JAVADOC: Description of the Parameter
	 */
	public Td(String tdContent) {
		super("TD");
		content = tdContent;
		addAttribute(new Attribute("ref"));
	}


	/**
	 *  JAVADOC: Sets the content attribute of the Td object
	 *
	 * @param  tdContent  JAVADOC: The new content value
	 */
	public void setContent(String tdContent) {
		content = tdContent;
	}


	/**
	 *  JAVADOC: Gets the content attribute of the Td object
	 *
	 * @return    JAVADOC: The content value
	 */
	public String getContent() {
		return (content);
	}

	public String getRef() {
		return (getAttribute("ref").getValue());
	}
	
	public void setRef(String ref) {
		getAttribute("ref").setValue(ref);
	}

	/**
	 *  JAVADOC: Description of the Method
	 *
	 * @return    JAVADOC: Description of the Return Value
	 */
	public String toString() {
		return ("<TD" + attributes.toString() + ">" + content + "</TD>");
	}

	
	public boolean isValid() {
		return (true);
	}
}

