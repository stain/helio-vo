package org.egso.votable.element;


/**
 *  JAVADOC: Description of the Class
 *
 * @author     Romain Linsolas (linsolas@gmail.com)
 * @version    1.0
 * @created    28 October 2003
 */
public class Binary extends VOTableElement {

	/**
	 *  JAVADOC: Description of the Field
	 */
	private Stream stream = null;


	/**
	 *  JAVADOC: Constructor for the Binary object
	 */
	public Binary() {
		super("BINARY");
	}


	/**
	 *  JAVADOC: Constructor for the Binary object
	 *
	 * @param  str  JAVADOC: Description of the Parameter
	 */
	public Binary(Stream str) {
		super("BINARY");
		stream = str;
	}


	/**
	 *  JAVADOC: Sets the stream attribute of the Binary object
	 *
	 * @param  str  JAVADOC: The new stream value
	 */
	public void setStream(Stream str) {
		stream = str;
	}


	/**
	 *  JAVADOC: Gets the stream attribute of the Binary object
	 *
	 * @return    JAVADOC: The stream value
	 */
	public Stream getStream() {
		return (stream);
	}

	
	/**
	 * A String representation of the element.
	 *
	 * @return A String representation of the element.
	 **/
	public String toString() {
		return ("<BINARY>" + stream.toString() + "</BINARY>");
	}
}

