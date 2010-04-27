package org.egso.votable.element;


/**
 *  Definition of the MIN VOTable element.
 *
 * @author     Romain Linsolas (linsolas@gmail.com).
 * @version    1.0
 * @created    28 October 2003
 */
public class Min extends VOTableElement {

	/**
	 *  Content of the MIN element.
	 */
	private String content = null;


	/**
	 *  Creation of a MIN object.
	 *
	 * @param  value  Value of MIN.
	 */
	public Min(String value) {
		super("MIN");
		addAttribute(new Attribute("value", value, Attribute.REQUIRED));
		addAttribute(new Attribute("inclusive", null, "yes", new String[]{"yes", "no"}));
	}


	/**
	 *  Creation of a MIN object with a given content.
	 *
	 * @param  value       Value of MIN.
	 * @param  minContent  Content of MIN.
	 */
	public Min(String value, String minContent) {
		super("MIN");
		addAttribute(new Attribute("value", value, Attribute.REQUIRED));
		addAttribute(new Attribute("inclusive", null, "yes", new String[]{"yes", "no"}));
		content = minContent;
	}


	/**
	 *  Set the content.
	 *
	 * @param  minContent  New content.
	 */
	public void setContent(String minContent) {
		content = minContent;
	}


	/**
	 *  Set the value of the MIN element.
	 *
	 * @param  value  New value.
	 */
	public void setValue(String value) {
		getAttribute("value").setValue(value);
	}


	/**
	 *  Set the inclusive attribute of the MIN element.
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
	 *  Get the value of MIN.
	 *
	 * @return    The value of MIN.
	 */
	public String getValue() {
		return (getAttribute("value").getValue());
	}


	/**
	 *  Get the inclusive attribute of the MIN element.
	 *
	 * @return    The inclusive value.
	 */
	public String getInclusive() {
		return (getAttribute("inclusive").getValue());
	}


	/**
	 *  Representation of the MIN element.
	 *
	 * @return    Representation of the MIN element.
	 */
	public String toString() {
		return ("<MIN " + attributes.toString() + ">" + content + "</MIN>");
	}

}

