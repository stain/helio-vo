package org.egso.votable.element;


/**
 *  JAVADOC: Description of the Class
 *
 * @author     Romain Linsolas (linsolas@gmail.com)
 * @version    1.0
 * @created    28 October 2003
 */
public class Fits extends VOTableElement {

	/**
	 *  JAVADOC: Description of the Field
	 */
	private Stream stream = null;


	/**
	 *  JAVADOC: Constructor for the Fits object
	 *
	 * @param  str  JAVADOC: Description of the Parameter
	 */
	public Fits(Stream str) {
		super("FITS");
		stream = str;
		addAttribute(new Attribute("extnum"));
	}


	/**
	 *  JAVADOC: Constructor for the Fits object
	 */
	public Fits() {
		super("FITS");
		addAttribute(new Attribute("extnum"));
	}


	/**
	 *  JAVADOC: Sets the extNum attribute of the Fits object
	 *
	 * @param  num  JAVADOC: The new extNum value
	 */
	public void setExtNum(String num) {
		getAttribute("extnum").setValue(num);
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
	 *  JAVADOC: Gets the extNum attribute of the Fits object
	 *
	 * @return    JAVADOC: The extNum value
	 */
	public String getExtNum() {
		return (getAttribute("extnum").getValue());
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
		return ("<FITS " + attributes.toString() + ">" + stream.toString() + "</FITS>");
	}

}

