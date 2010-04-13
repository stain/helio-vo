package org.egso.votable.element;


/**
 *  JAVADOC: Description of the Class
 *
 * @author     Romain Linsolas (linsolas@gmail.com)
 * @version    1.0
 * @created    28 October 2003
 */
public class Info extends VOTableElement {

	/**
	 *  JAVADOC: Description of the Field
	 */
	private String content = null;


	/**
	 *  JAVADOC: Constructor for the Info object
	 */
	public Info() {
		super("INFO");
		init();
	}


	/**
	 *  JAVADOC: Constructor for the Info object
	 *
	 * @param  infoContent  JAVADOC: Description of the Parameter
	 */
	public Info(String infoContent) {
		super("info");
		content = infoContent;
		init();
	}


	/**
	 *  JAVADOC: Sets the iD attribute of the Info object
	 *
	 * @param  id  JAVADOC: The new iD value
	 */
	public void setID(String id) {
		getAttribute("ID").setValue(id);
	}


	/**
	 *  JAVADOC: Sets the name attribute of the Info object
	 *
	 * @param  name  JAVADOC: The new name value
	 */
	public void setName(String name) {
		getAttribute("name").setValue(name);
	}


	/**
	 *  JAVADOC: Description of the Method
	 *
	 * @param  val  JAVADOC: Description of the Parameter
	 */
	public void setValue(String val) {
		getAttribute("value").setValue(val);
	}


	/**
	 *  JAVADOC: Sets the content attribute of the Info object
	 *
	 * @param  cnt  JAVADOC: The new content value
	 */
	public void setContent(String cnt) {
		content = cnt;
	}


	/**
	 *  JAVADOC: Gets the iD attribute of the Info object
	 *
	 * @return    JAVADOC: The iD value
	 */
	public String getID() {
		return (getAttribute("ID").getValue());
	}


	/**
	 *  JAVADOC: Gets the name attribute of the Info object
	 *
	 * @return    JAVADOC: The name value
	 */
	public String getName() {
		return (getAttribute("name").getValue());
	}


	/**
	 *  JAVADOC: Gets the value attribute of the Info object
	 *
	 * @return    JAVADOC: The value value
	 */
	public String getValue() {
		return (getAttribute("value").getValue());
	}


	/**
	 *  JAVADOC: Gets the content attribute of the Info object
	 *
	 * @return    JAVADOC: The content value
	 */
	public String getContent() {
		return (content);
	}


	/**
	 *  JAVADOC: Description of the Method
	 */
	private void init() {
		addAttribute(new Attribute("ID"));
		addAttribute(new Attribute("name"));
		addAttribute(new Attribute("value"));
	}


	/**
	 *  JAVADOC: Description of the Method
	 *
	 * @return    JAVADOC: Description of the Return Value
	 */
	public String toString() {
		return ("<INFO " + attributes.toString() + ">" + content + "</INFO>");
	}
}

