package org.egso.votable.element;


/**
 *  Definition of the MAX VOTable element.
 *
 * @author     Romain Linsolas (linsolas@gmail.com).
 * @version    1.0
 * @created    28 October 2003
 */
public class Max extends VOTableElement {

	/**
	 *  Content of the MAX element.
	 */
	private String content = null;


	/**
	 *  Creation of a MAX object.
	 *
	 * @param  value  Value of MAX.
	 */
	public Max(String value) {
		super("MAX");
		addAttribute(new Attribute("value", value, Attribute.REQUIRED));
		addAttribute(new Attribute("inclusive", null, "yes", new String[]{"yes", "no"}));
	}


	/**
	 *  Creation of a MAX object with a given content.
	 *
	 * @param  value       Value of MAX.
	 * @param  minContent  Content of MAX.
	 */
	public Max(String value, String minContent) {
		super("MAX");
		addAttribute(new Attribute("value", value, Attribute.REQUIRED));
		addAttribute(new Attribute("inclusive", null, "yes", new String[]{"yes", "no"}));
		content = minContent;
	}


	/**
	 *  Set the content.
	 *
	 * @param  minContent  New content.
	 */
	public void setContent(String maxContent) {
		content = maxContent;
	}


	/**
	 *  Set the value of the MAX element.
	 *
	 * @param  value  New value.
	 */
	public void setValue(String value) {
		getAttribute("value").setValue(value);
	}


	/**
	 *  Set the inclusive attribute of the MAX element.
	 *
	 * @param  value                      New value.
	 * @exception  InvalidValueException  If the value is neither "yes" nor "no".
	 */
	public void setInclusive(String value)
			 throws InvalidValueException {
		getAttribute("inclusive").setValue(value);
	}


	/**
	 *  Get the content.
	 *
	 * @return    Content of the MIN element.
	 */
	public String getContent() {
		return (content);
	}


	/**
	 *  Get the value of MAX.
	 *
	 * @return    The value of MAX.
	 */
	public String getValue() {
		return (getAttribute("value").getValue());
	}


	/**
	 *  Get the inclusive attribute of the MAX element.
	 *
	 * @return    The inclusive value.
	 */
	public String getInclusive() {
		return (getAttribute("inclusive").getValue());
	}


	/**
	 *  Representation of the MAX element.
	 *
	 * @return    Representation of the MAX element.
	 */
	public String toString() {
		return ("<MAX " + attributes.toString() + ">" + content + "</MAX>");
	}

}

