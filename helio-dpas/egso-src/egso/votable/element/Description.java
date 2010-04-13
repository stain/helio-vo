package org.egso.votable.element;


/**
 *  JAVADOC: Description of the Class
 *
 * @author     Romain Linsolas (linsolas@gmail.com)
 * @version    1.0
 * @created    28 October 2003
 */
public class Description extends VOTableElement {

	/**
	 *  JAVADOC: Description of the Field
	 */
	private String content = null;


	/**
	 *  JAVADOC: Constructor for the Description object
	 */
	public Description() {
		super("DESCRIPTION");
	}


	/**
	 *  JAVADOC: Sets the content attribute of the Description object
	 *
	 * @param  cnt  JAVADOC: The new content value
	 */
	public void setContent(String cnt) {
		content = cnt;
	}


	/**
	 *  JAVADOC: Gets the content attribute of the Description object
	 *
	 * @return    JAVADOC: The content value
	 */
	public String getContent() {
		return (content);
	}


	/**
	 * A String representation of the element.
	 *
	 * @return A String representation of the element.
	 **/
	public String toString() {
		return ("<DESCRIPTION>" + content + "</DESCRIPTION>");
	}

}

