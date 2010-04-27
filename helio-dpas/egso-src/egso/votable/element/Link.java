package org.egso.votable.element;


/**
 *  JAVADOC: Description of the Class
 *
 * @author     Romain Linsolas (linsolas@gmail.com)
 * @version    1.0
 * @created    28 October 2003
 */
public class Link extends VOTableElement {

	/**
	 *  JAVADOC: Description of the Field
	 */
	private String content = null;


	/**
	 *  JAVADOC: Constructor for the Link object
	 */
	public Link() {
		super("LINK");
		init();
	}


	/**
	 *  JAVADOC: Constructor for the Link object
	 *
	 * @param  linkContent  JAVADOC: Description of the Parameter
	 */
	public Link(String linkContent) {
		super("LINK");
		content = linkContent;
		init();
	}


	/**
	 *  JAVADOC: Sets the content attribute of the Link object
	 *
	 * @param  cnt  JAVADOC: The new content value
	 */
	public void setContent(String cnt) {
		content = cnt;
	}


	/**
	 *  JAVADOC: Sets the iD attribute of the Link object
	 *
	 * @param  id  JAVADOC: The new iD value
	 */
	public void setID(String id) {
		getAttribute("ID").setValue(id);
	}


	/**
	 *  JAVADOC: Sets the contentRole attribute of the Link object
	 *
	 * @param  cr  JAVADOC: The new contentRole value
	 */
	public void setContentRole(String cr) {
		getAttribute("content-role").setValue(cr);
	}


	/**
	 *  JAVADOC: Sets the contentType attribute of the Link object
	 *
	 * @param  ct  JAVADOC: The new contentType value
	 */
	public void setContentType(String ct) {
		getAttribute("content-type").setValue(ct);
	}


	/**
	 *  JAVADOC: Sets the title attribute of the Link object
	 *
	 * @param  t  JAVADOC: The new title value
	 */
	public void setTitle(String t) {
		getAttribute("title").setValue(t);
	}


	/**
	 *  JAVADOC: Sets the value attribute of the Link object
	 *
	 * @param  val  JAVADOC: The new value value
	 */
	public void setValue(String val) {
		getAttribute("value").setValue(val);
	}


	/**
	 *  JAVADOC: Sets the href attribute of the Link object
	 *
	 * @param  href  JAVADOC: The new href value
	 */
	public void setHref(String href) {
		getAttribute("href").setValue(href);
	}


	/**
	 *  JAVADOC: Sets the gref attribute of the Link object
	 *
	 * @param  gref  JAVADOC: The new gref value
	 */
	public void setGref(String gref) {
		getAttribute("gref").setValue(gref);
	}


	/**
	 *  JAVADOC: Description of the Method
	 *
	 * @param  a  JAVADOC: Description of the Parameter
	 */
	public void setAction(String a) {
		getAttribute("action").setValue(a);
	}


	/**
	 *  JAVADOC: Gets the content attribute of the Link object
	 *
	 * @return    JAVADOC: The content value
	 */
	public String getContent() {
		return (content);
	}


	/**
	 *  JAVADOC: Gets the iD attribute of the Link object
	 *
	 * @return    JAVADOC: The iD value
	 */
	public String getID() {
		return (getAttribute("ID").getValue());
	}


	/**
	 *  JAVADOC: Gets the contentRole attribute of the Link object
	 *
	 * @return    JAVADOC: The contentRole value
	 */
	public String getContentRole() {
		return (getAttribute("content-role").getValue());
	}


	/**
	 *  JAVADOC: Gets the contentType attribute of the Link object
	 *
	 * @return    JAVADOC: The contentType value
	 */
	public String getContentType() {
		return (getAttribute("content-type").getValue());
	}


	/**
	 *  JAVADOC: Gets the title attribute of the Link object
	 *
	 * @return    JAVADOC: The title value
	 */
	public String getTitle() {
		return (getAttribute("title").getValue());
	}


	/**
	 *  JAVADOC: Gets the value attribute of the Link object
	 *
	 * @return    JAVADOC: The value value
	 */
	public String getValue() {
		return (getAttribute("value").getValue());
	}


	/**
	 *  JAVADOC: Gets the href attribute of the Link object
	 *
	 * @return    JAVADOC: The href value
	 */
	public String getHref() {
		return (getAttribute("href").getValue());
	}


	/**
	 *  JAVADOC: Gets the gref attribute of the Link object
	 *
	 * @return    JAVADOC: The gref value
	 */
	public String getGref() {
		return (getAttribute("gref").getValue());
	}


	/**
	 *  JAVADOC: Gets the action attribute of the Link object
	 *
	 * @return    JAVADOC: The action value
	 */
	public String getAction() {
		return (getAttribute("action").getValue());
	}


	/**
	 *  JAVADOC: Description of the Method
	 */
	private void init() {
		addAttribute(new Attribute("ID"));
		addAttribute(new Attribute("content-role", new String[]{"query", "hints", "doc"}));
		addAttribute(new Attribute("content-type"));
		addAttribute(new Attribute("title"));
		addAttribute(new Attribute("value"));
		addAttribute(new Attribute("href"));
		addAttribute(new Attribute("gref"));
		addAttribute(new Attribute("action"));
	}

	/**
	 * A String representation of the element.
	 *
	 * @return A String representation of the element.
	 **/
	public String toString() {
		return ("<LINK " + attributes.toString() + ">" + content + "</LINK>");
	}


}

