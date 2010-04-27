package org.egso.votable.element;


/**
 *  JAVADOC: Description of the Class
 *
 * @author     Romain Linsolas (linsolas@gmail.com)
 * @version    1.0
 * @created    28 October 2003
 */
public class Stream extends VOTableElement {

	/**
	 *  JAVADOC: Description of the Field
	 */
	public String content = null;


	/**
	 *  JAVADOC: Constructor for the Stream object
	 */
	public Stream() {
		super("STREAM");
		init();
	}


	/**
	 *  JAVADOC: Constructor for the Stream object
	 *
	 * @param  cont  JAVADOC: Description of the Parameter
	 */
	public Stream(String cont) {
		super("STREAM");
		content = cont;
		init();
	}


	/**
	 *  JAVADOC: Sets the content attribute of the Stream object
	 *
	 * @param  cont  JAVADOC: The new content value
	 */
	public void setContent(String cont) {
		content = cont;
	}


	/**
	 *  JAVADOC: Sets the type attribute of the Stream object
	 *
	 * @param  type  JAVADOC: The new type value
	 */
	public void setType(String type) {
		getAttribute("type").setValue(type);
	}


	/**
	 *  JAVADOC: Sets the href attribute of the Stream object
	 *
	 * @param  href  JAVADOC: The new href value
	 */
	public void setHref(String href) {
		getAttribute("href").setValue(href);
	}


	/**
	 *  JAVADOC: Sets the actuate attribute of the Stream object
	 *
	 * @param  actuate  JAVADOC: The new actuate value
	 */
	public void setActuate(String actuate) {
		getAttribute("actuate").setValue(actuate);
	}


	/**
	 *  JAVADOC: Sets the encoding attribute of the Stream object
	 *
	 * @param  encoding  JAVADOC: The new encoding value
	 */
	public void setEncoding(String encoding) {
		getAttribute("encoding").setValue(encoding);
	}


	/**
	 *  JAVADOC: Sets the expires attribute of the Stream object
	 *
	 * @param  expires  JAVADOC: The new expires value
	 */
	public void setExpires(String expires) {
		getAttribute("expires").setValue(expires);
	}


	/**
	 *  JAVADOC: Sets the rights attribute of the Stream object
	 *
	 * @param  rights  JAVADOC: The new rights value
	 */
	public void setRights(String rights) {
		getAttribute("rights").setValue(rights);
	}


	/**
	 *  JAVADOC: Gets the content attribute of the Stream object
	 *
	 * @return    JAVADOC: The content value
	 */
	public String getContent() {
		return (content);
	}


	/**
	 *  JAVADOC: Gets the type attribute of the Stream object
	 *
	 * @return    JAVADOC: The type value
	 */
	public String getType() {
		return (getAttribute("type").getValue());
	}


	/**
	 *  JAVADOC: Gets the href attribute of the Stream object
	 *
	 * @return    JAVADOC: The href value
	 */
	public String getHref() {
		return (getAttribute("href").getValue());
	}


	/**
	 *  JAVADOC: Gets the actuate attribute of the Stream object
	 *
	 * @return    JAVADOC: The actuate value
	 */
	public String getActuate() {
		return (getAttribute("actuate").getValue());
	}


	/**
	 *  JAVADOC: Gets the encoding attribute of the Stream object
	 *
	 * @return    JAVADOC: The encoding value
	 */
	public String getEncoding() {
		return (getAttribute("encoding").getValue());
	}


	/**
	 *  JAVADOC: Gets the expires attribute of the Stream object
	 *
	 * @return    JAVADOC: The expires value
	 */
	public String getExpires() {
		return (getAttribute("expires").getValue());
	}


	/**
	 *  JAVADOC: Gets the rights attribute of the Stream object
	 *
	 * @return    JAVADOC: The rights value
	 */
	public String getRights() {
		return (getAttribute("rights").getValue());
	}


	/**
	 *  JAVADOC: Description of the Method
	 */
	private void init() {
		addAttribute(new Attribute("type", null, "locator", new String[]{"other", "locator"}));
		addAttribute(new Attribute("href"));
		addAttribute(new Attribute("actuate", null, "onRequest", new String[]{"onLoad", "onRequest", "other", "none"}));
		addAttribute(new Attribute("encoding", null, "none", new String[]{"gzip", "base64", "dynamic", "none"}));
		addAttribute(new Attribute("expires"));
		addAttribute(new Attribute("rights"));
	}


	/**
	 *  String representation of the element.
	 *
	 * @return    Representation of the element.
	 */
	public String toString() {
		return ("<STREAM" + attributes.toString() + ">" + content.toString() + "</STREAM>");
	}


}

