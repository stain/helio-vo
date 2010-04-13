package org.egso.common.context;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Vector;
import java.util.Locale;
import java.util.Date;
import java.text.DateFormat;
import org.xml.sax.InputSource;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import java.io.ByteArrayInputStream;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;


/**
 * This class defines a Context for an Object in transit between EGSO roles
 * (broker, provider, consumer). Such an object can be a query, a result, a
 * service (query) or file(s).
 *
 * @author    Romain Linsolas (linsolas@gmail.com)
 * @version   2.0 - 20/09/2004 [14/06/2004]
 */
/*
2.0 - 20/09/2004:
	Refactoring of the class, now using EGSOContextFactory.
	Constructors are now only accessible from the package org.egso.common.context,
		so only EGSOContextFactory will be able to create EGSOContext instances.
1.1.2 - 16/09/2004:
	Modification of the addRoute methods to add the IP and the version of the role.
1.1.1 - 03/09/2004:
	Modification of the addRoute method, which now takes a Date/Calendar instead
		of a String for the date argument. This way, the format of this date
		will always be the same.
1.1 - 16/06/2004 - Romain Linsolas (linsolas@gmail.com):
	Adding the setType(int) method.
	Update Constructor signatures.
*/
public class EGSOContext {

	/**
	 * Code for the Context linked to an unknown object.
	 */
	public final static int CONTEXT_UNKNOWN = 0;
	/**
	 * Code for the Context linked to a query.
	 */
	public final static int CONTEXT_QUERY = 1;
	/**
	 * Code for the Context linked to a result.
	 */
	public final static int CONTEXT_RESULT = 2;
	/**
	 * Code for the Context linked to a service invocation.
	 */
	public final static int CONTEXT_SERVICE = 3;
	/**
	 * Code for the Context linked to a (set of) file(s).
	 */
	public final static int CONTEXT_FILES = 4;

	/**
	 * Code for the parameter's type WARNING.
	 */
	public final static int PARAMETER_WARNING = 0;
	/**
	 * Code for the parameter's type USERINFO.
	 */
	public final static int PARAMETER_USERINFO = 1;
	/**
	 * Code for the parameter's type SYSTEMINFO.
	 */
	public final static int PARAMETER_SYSTEMINFO = 2;
	/**
	 * Code for the parameter's type DEBUG.
	 */
	public final static int PARAMETER_DEBUG = 3;

	/**
	 * Code for the Provider EGSO role.
	 */
	public final static int ROLE_PROVIDER = 0;
	/**
	 * Code for the Consumer EGSO role.
	 */
	public final static int ROLE_CONSUMER = 1;
	/**
	 * Code for the Broker EGSO role.
	 */
	public final static int ROLE_BROKER = 2;
	/**
	 * Code for another kind of EGSO role.
	 */
	public final static int ROLE_UNKNOWN = 3;

	/**
	 * Code for the exception's type CRITICAL.
	 */
	public final static int EXCEPTION_CRITICAL = 0;
	/**
	 * Code for the exception's type ERROR.
	 */
	public final static int EXCEPTION_ERROR = 1;
	/**
	 * Code for the exception's type WARNING.
	 */
	public final static int EXCEPTION_WARNING = 2;

	/**
	 * Names of Context types.
	 */
	private final static String[] typeNames = {"unknown", "query", "result", "service", "files"};
	/**
	 * Names of parameter types.
	 */
	private final static String[] paramNames = {"warning", "userinfo", "systeminfo", "debug"};
	/**
	 * Names of exception types.
	 */
	private final static String[] exceptionNames = {"critical", "error", "warning"};
	/**
	 * Names of roles.
	 */
	private final static String[] roleNames = {"provider", "consumer", "broker", "unknown"};
	/**
	 * Type of Context.
	 */
	private int type = 0;
	/**
	 * ID of the context. This attribute is optional.
	 */
	private String id = null;
	/**
	 * Set of parameter descriptions.
	 */
	private Hashtable<String,String[]> parameters = null;
	/**
	 * Set of route descriptions.
	 */
	private Vector<String[]> routes = null;
	/**
	 * Set of exception descriptions.
	 */
	private Vector<String[]> exceptions = null;
	/**
	 * Name of the role that uses the EGSOContext.
	 */
	private String rolename = null;
	/**
	 * IP of the role that uses the EGSOContext.
	 */
	private String roleip = null;
	/**
	 * Version of the role that uses the EGSOContext.
	 */
	private String roleversion = null;
	/**
	 * Type of the role that uses the EGSOContext.
	 */
	private int roletype = ROLE_UNKNOWN;


	/**
	 * Builds an empty Context, only specifying its type.
	 *
	 * @param cxt_type     Type of Context.
	 * @param roleName     Name of the role that uses the EGSOContext.
	 * @param roleIP       IP of the role that uses the EGSOContext.
	 * @param roleType     Type of the role that uses the EGSOContext.
	 * @param roleVersion  Version of the role that uses the EGSOContext.
	 */
	EGSOContext(int cxt_type, int roleType, String roleName, String roleVersion, String roleIP) {
		type = cxt_type;
		roletype = roleType;
		rolename = roleName;
		roleversion = roleVersion;
		roleip = roleIP;
		init(null);
	}


	/**
	 * Builds an empty Context, only specifying its type and ID.
	 *
	 * @param cxt_type     Type of Context.
	 * @param cxtID        ID of the Context.
	 * @param roleName     Name of the role that uses the EGSOContext.
	 * @param roleIP       IP of the role that uses the EGSOContext.
	 * @param roleType     Type of the role that uses the EGSOContext.
	 * @param roleVersion  Version of the role that uses the EGSOContext.
	 */
	EGSOContext(int cxt_type, int roleType, String cxtID, String roleName, String roleVersion, String roleIP) {
		type = cxt_type;
		id = cxtID;
		roletype = roleType;
		rolename = roleName;
		roleversion = roleVersion;
		roleip = roleIP;
		init(null);
	}


	/**
	 * Builds a Context, given its content.
	 *
	 * @param xml          String (XML) representation of the Context.
	 * @param roleName     Name of the role that uses the EGSOContext.
	 * @param roleIP       IP of the role that uses the EGSOContext.
	 * @param roleType     Type of the role that uses the EGSOContext.
	 * @param roleVersion  Version of the role that uses the EGSOContext.
	 */
	EGSOContext(String xml, int roleType, String roleName, String roleVersion, String roleIP) {
		roletype = roleType;
		rolename = roleName;
		roleversion = roleVersion;
		roleip = roleIP;
		init(xml);
	}


	/**
	 * Sets the ID of the Context.
	 *
	 * @param newID  New ID.
	 */
	public void setID(String newID) {
		id = newID;
	}


	/**
	 * Sets the type of the Context.
	 *
	 * @param cxtType  New type of the Context.
	 */
	public void setType(int cxtType) {
		type = cxtType;
	}


	/**
	 * Get the Context ID.
	 *
	 * @return   ID of the Context - it may be <code>null</code>.
	 */
	public String getID() {
		return (id);
	}


	/**
	 * Gets the Context type.
	 *
	 * @return   Type of the Context.
	 */
	public int getType() {
		return (type);
	}


	/**
	 * Gets the Context type as a String.
	 *
	 * @return   Type of the Context, in a String representation.
	 */
	public String getTypeAsString() {
		return (typeNames[type]);
	}


	/**
	 * Returns all Exceptions defined in the Context. The Collection returned
	 * contains a set of String arrays (i.e. String[]). Each array contains:<br/>
	 * index 0: role.<br/>
	 * index 1: origin.<br/>
	 * index 2: type.<br/>
	 * index 3: message.<br/>
	 * index 4: class.<br/>
	 * index 5: stacktrace.<br/>
	 *
	 *
	 * @return   All exceptions in a Collection object.
	 */
	public Collection<String[]> getExceptions() {
		return (exceptions);
	}


	/**
	 * Returns all Exceptions of the Context, represented as XML nodes. Such node
	 * is written like that:<br>
	 * &lt;exception role="{provider | consumer | broker}" origin="..."
	 * type="{critical | error | warning}"&gt;<br/>
	 * &nbsp;&nbsp;&lt;message&gt;exception message&lt;/message&gt;<br/>
	 * &nbsp;&nbsp;&lt;class&gt;class where the exception occured.&gt;/class&gt;
	 * <br/>
	 * &nbsp;&nbsp;&lt;stacktrace&gt; &lt;![CDATA[ stacktrace...
	 * ]]&gt;&lt;/stacktrace&gt;<br/>
	 * &lt;/exception&gt;
	 *
	 * @return   All exceptions in a Collection object.
	 */
	public Collection<String> getExceptionsAsXML() {
		Vector<String> results = new Vector<String>();
		StringBuffer sb = new StringBuffer();
		for (String[] tmp:exceptions)
		{
			sb = new StringBuffer();
			sb.append("<exception role=\"" + tmp[0] + "\" origin=\"" + tmp[1] + "\" type=\"" + tmp[2] + "\">");
			sb.append("<message>" + tmp[3] + "</message><class>" + tmp[4] + "</class>");
			sb.append("<stacktrace>" + tmp[5] + "</stacktrace></exception>");
			results.add(sb.toString());
		}
		return (results);
	}


	/**
	 * Gets all exceptions that has a certain value for the criteria "role" or
	 * "type".
	 *
	 * @param criteria  Criteria, must <b>only</b> be "role" or "type" (no case
	 *      sensitive).
	 * @param value     Value of the criteria.
	 * @return          All exceptions where "criteria = value".
	 */
	public Collection<Object> getExceptions(String criteria, int value) {
		criteria = criteria.toLowerCase();
		if (criteria.equals("role")) {
			return (getExceptions(criteria, roleNames[value], true));
		}
		if (criteria.equals("type")) {
			return (getExceptions(criteria, exceptionNames[value], true));
		}
		return (new Vector<Object>());
	}


	/**
	 * Gets all exceptions that has a certain value for the criteria "role",
	 * "type", "origin", "class", "message" or "stacktrace".
	 *
	 * @param criteria  Criteria, must <b>only</b> be "role", "type", "origin",
	 *      "class", "message" or "stacktrace" (no case sensitive).
	 * @param value     Value of the criteria.
	 * @return          All exceptions where "criteria = value".
	 */
	public Collection<Object> getExceptions(String criteria, String value) {
		return (getExceptions(criteria, value, false));
	}


	/**
	 * Gets all exceptions that has a certain value for the criteria "role",
	 * "type", "origin", "class", "message" or "stacktrace".
	 *
	 * @param criteria  Criteria, must <b>only</b> be "role", "type", "origin",
	 *      "class", "message" or "stacktrace" (no case sensitive).
	 * @param value     Value of the criteria.
	 * @return          All exceptions in an XML representation, where "criteria =
	 *      value".
	 */
	public Collection<Object> getExceptionsAsXML(String criteria, String value) {
		return (getExceptions(criteria, value, true));
	}


	/**
	 * Gets all exceptions that has a certain value for the criteria "role",
	 * "type", "origin", "class", "message" or "stacktrace".
	 *
	 * @param criteria  Criteria, must <b>only</b> be "role", "type", "origin",
	 *      "class", "message" or "stacktrace" (no case sensitive).
	 * @param value     Value of the criteria.
	 * @return          All exceptions where "criteria = value".
	 */
	public Collection<Object> getExceptionsAsXML(String criteria, int value) {
		criteria = criteria.toLowerCase();
		if (criteria.equals("role")) {
			return (getExceptions(criteria, roleNames[value], true));
		}
		if (criteria.equals("type")) {
			return (getExceptions(criteria, exceptionNames[value], true));
		}
		return (new Vector<Object>());
	}


	/**
	 * Gets all exceptions that has a certain value for the criteria "role",
	 * "type", "origin", "class", "message" or "stacktrace".
	 *
	 * @param criteria  Criteria, must <b>only</b> be "role", "type", "origin",
	 *      "class", "message" or "stacktrace" (no case sensitive).
	 * @param value     Value of the criteria.
	 * @param xml       Boolean at <code>true</code> to have a Collection that
	 *      contains XML results, at <code>false</code> to have results as
	 *      String[].
	 * @return          All exceptions in an XML or String[] representation, where
	 *      "criteria = value".
	 */
	private Collection<Object> getExceptions(String criteria, String value, boolean xml) {
		Vector<Object> results = new Vector<Object>();
		criteria = criteria.toLowerCase();
		value = value.toLowerCase();
		int index = 0;
		if (criteria.equals("role")) {
			index = 0;
		} else {
			if (criteria.equals("origin")) {
				index = 1;
			} else {
				if (criteria.equals("type")) {
					index = 2;
				} else {
					if (criteria.equals("message")) {
						index = 3;
					} else {
						if (criteria.equals("classname")) {
							index = 4;
						} else {
							if (criteria.equals("stacktrace")) {
								index = 5;
							} else {
								return (results);
							}
						}
					}
				}
			}
		}
		for (String[] tmp:exceptions) {
			if (tmp[index].toLowerCase().equals(value)) {
				if (xml) {
					StringBuffer sb = new StringBuffer();
					sb.append("<exception role=\"" + tmp[0] + "\" origin=\"" + tmp[1] + "\" type=\"" + tmp[2] + "\">");
					sb.append("<message>" + tmp[3] + "</message><class>" + tmp[4] + "</class>");
					sb.append("<stacktrace>" + tmp[5] + "</stacktrace></exception>");
					results.add(sb.toString());
				} else {
					results.add(tmp);
				}
			}
		}
		return (results);
	}


	/**
	 * Gets all routes in such XML representation:<br/>
	 * &lt;route role="{provider | consumer | broker}" rolename="..."
	 * role-ip="xxx.xxx.xxx.xxx" role-version="..." time="..." action="..."&gt;
	 * Description of the route...&lt;/route&gt;
	 *
	 * @return   A Collection containing, if any, all routes in a XML
	 *      representation.
	 */
	public Collection<String> getRoutesAsXML() {
		Vector<String> results = new Vector<String>();
		for (String[] tmp:routes) {
			results.add("<route role=\"" + tmp[0] + "\" rolename=\"" + tmp[1] + "\" role-ip=\"" + tmp[2] + "\" role-version=\"" + tmp[3] + "\" time=\"" + tmp[4] + "\" action=\"" + tmp[5] + "\">" + tmp[6] + "</route>");
		}
		return (results);
	}


	/**
	 * Gets All Routes as a String[] representation, where:<br/>
	 * index 0: role.<br/>
	 * index 1: rolename.<br/>
	 * index 2: role-ip<br/>
	 * index 3: role-version<br/>
	 * index 4: time.<br/>
	 * index 5: action.<br/>
	 * index 6: description.<br/>
	 *
	 *
	 * @return   All routes in a Collection object.
	 */
	public Collection<String[]> getRoutes() {
		return (routes);
	}


	/**
	 * Gets all routes where given the value of a criteria.
	 *
	 * @param criteria  Criteria, <b>must</b> be "role" (case insensitive).
	 * @param value     Value of the criteria.
	 * @return          A Collection of routes where "criteria = value".
	 */
	public Collection<String[]> getRoutes(String criteria, int value) {
		criteria = criteria.toLowerCase();
		if (criteria.equals("role")) {
			return (getRoutes(criteria, roleNames[value], false));
		}
		return (new Vector<String[]>());
	}


	/**
	 * Gets all routes where given the value of a criteria.
	 *
	 * @param criteria  Criteria, <b>must</b> be "role", "rolename", "role-ip",
	 *      "role-versione", "time", "description" or "action" (case insensitive).
	 * @param value     Value of the criteria.
	 * @return          A Collection of routes where "criteria = value".
	 */
	public Collection<String[]> getRoutes(String criteria, String value) {
		return (getRoutes(criteria, value, false));
	}


	/**
	 * Gets all routes in a XML representation where given the value of a criteria.
	 *
	 * @param criteria  Criteria, <b>must</b> be "role", "rolename", "role-ip",
	 *      "role-version", "time", "description" or "action" (case insensitive).
	 * @param value     Value of the criteria.
	 * @return          A Collection of routes where "criteria = value".
	 */
	public Collection<String[]> getRoutesAsXML(String criteria, String value) {
		return (getRoutes(criteria, value, true));
	}


	/**
	 * Gets all routes in a XML representation where given the value of a criteria.
	 *
	 * @param criteria  Criteria, <b>must</b> be "role" (case insensitive).
	 * @param value     Value of the criteria.
	 * @return          A Collection of routes where "criteria = value".
	 */
	public Collection<String[]> getRoutesAsXML(String criteria, int value) {
		criteria = criteria.toLowerCase();
		if (criteria.equals("role")) {
			return (getRoutes(criteria, roleNames[value], true));
		}
		return (new Vector<String[]>());
	}


	/**
	 * Gets all routes in a XML or String[] representation where given the value of
	 * a criteria.
	 *
	 * @param criteria  Criteria, <b>must</b> be "role", "rolename", "role-ip",
	 *      "role-version", "time", "description" or "action" (case insensitive).
	 * @param value     Value of the criteria.
	 * @param xml       Boolean at <code>true</code> to have a Collection that
	 *      contains XML results, at <code>false</code> to have results as
	 *      String[].
	 * @return          A Collection of routes where "criteria = value".
	 */
	private Collection<String[]> getRoutes(String criteria, String value, boolean xml) {
		Vector<String[]> results = new Vector<String[]>();
		criteria = criteria.toLowerCase();
		value = value.toLowerCase();
		int index = 0;
		String[] crits = {"role", "rolename", "role-ip", "role-version", "time", "action", "description"};
		boolean found = false;
		while (!found && (index < crits.length)) {
			found = criteria.equals(crits[index]);
			index++;
		}
		index--;
		if (!found) {
			return (results);
		}
		for (String[] tmp:routes) {
			if (tmp[index].toLowerCase().equals(value)) {
				if (xml) {
					results.add(new String[]{"<route role=\"" + tmp[0] + "\" rolename=\"" + tmp[1] + "\" role-ip=\"" + tmp[2] + "\" role-version=\"" + tmp[3] + "\" time=\"" + tmp[4] + "\" action=\"" + tmp[5] + "\">" + tmp[6] + "</route>"});
				} else {
					results.add(tmp);
				}
			}
		}
		return (results);
	}


	/**
	 * Gets the number of routes defined in the Context.
	 *
	 * @return   Number of routes.
	 */
	public int getNumberOfRoutes() {
		return (routes.size());
	}


	/**
	 * Gets the number of exceptions defined in the Context.
	 *
	 * @return   Number of exceptions.
	 */
	public int getNumberOfExceptions() {
		return (exceptions.size());
	}


	/**
	 * Gets the list of names for parameters.
	 *
	 * @return   A Collection containing all exception names.
	 */
	public Collection<String> getParameterNames() {
		Vector<String> names = new Vector<String>();
		for (Enumeration<String> e = parameters.keys(); e.hasMoreElements(); ) {
			names.add(e.nextElement());
		}
		return (names);
	}


	/**
	 * Gets the value of the parameter.
	 *
	 * @param name  Name of the parameter (case sensitive).
	 * @return      Value of the parameter, if exists, <code>null</code> otherwise.
	 */
	public String getParameter(String name) {
		String[] obj = parameters.get(name);
		if (obj != null) {
			return (obj[1]);
		}
		return (null);
	}


	/**
	 * Gets the XML representation of the parameter. The XML representation is the
	 * following:<br/>
	 * &lt;parameter type="{warning | userinfo | systeminfo | debug}" name="..."
	 * value="..."/&gt;
	 *
	 * @param name  Name of the parameter (case sensitive).
	 * @return      XML representation of the parameter, <code>null</code> if the
	 *      parameter doesn't exist.
	 */
	public String getParameterAsXML(String name) {
		String[] obj = parameters.get(name);
		if (obj != null) {
			return ("<parameter type=\"" + obj[0] + "\" name=\"" + name + "\" value=\"" + obj[1] + "\"/>");
		}
		return (null);
	}


	/**
	 * Gets all parameters (in XML representation) of a certain type.
	 *
	 * @param paramType  Type of parameters.
	 * @return           A Collection containing all parameters of the specified
	 *      type.
	 */
	public Collection<String> getParameters(int paramType) {
		Vector<String> results = new Vector<String>();
		String val = paramNames[paramType];
		String key = null;
		String[] tmp = null;
		for (Enumeration<String> e = parameters.keys(); e.hasMoreElements(); ) {
			key = e.nextElement();
			tmp = parameters.get(key);
			if (tmp[0].equals(val)) {
				results.add("<parameter type=\"" + tmp[0] + "\" name=\"" + key + "\" value=\"" + tmp[1] + "\"/>");
			}
		}
		return (results);
	}


	/**
	 * Initialisation of the Context. The content of the Context can be specified
	 * in a XML String.
	 *
	 * @param xml  Optional XML content of the Context.
	 */
	private void init(String xml) {
		parameters = new Hashtable<String,String[]>();
		exceptions = new Vector<String[]>();
		routes = new Vector<String[]>();
		if (xml == null) {
			return;
		}
		Node node = null;
		try {
			DocumentBuilderFactory facto = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = facto.newDocumentBuilder();
			InputSource is = new InputSource(new ByteArrayInputStream(xml.getBytes()));
			node = builder.parse(is).getDocumentElement();
		} catch (Throwable t) {
			t.printStackTrace();
			throw (new RuntimeException(t));
		}
		NodeList nl = node.getChildNodes();
		NodeList nl2 = null;
		NodeList nl3 = null;
		NodeList nl4 = null;
		Node n = null;
		Node n2 = null;
		Node n3 = null;
		Node n4 = null;
		String tmp = null;
		String text = null;
		String[] tempo = null;
		NamedNodeMap nnm = null;
		for (int i = 0; i < nl.getLength(); i++) {
			n = nl.item(i);
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				tmp = n.getNodeName().toLowerCase();
				if (tmp.equals("id")) {
					// Get the ID.
					id = n.getAttributes().getNamedItem("value").getNodeValue();
				} else {
					if (tmp.equals("type")) {
						// Get the type.
						tmp = n.getAttributes().getNamedItem("value").getNodeValue().toLowerCase();
						boolean found = false;
						int x = 0;
						while (!found && (x < typeNames.length)) {
							found = typeNames[x].equals(tmp);
							x++;
						}
						if (found) {
							type = x - 1;
						} else {
							type = CONTEXT_UNKNOWN;
							throw (new RuntimeException("Type '" + tmp + "' unknown for a Context."));
						}
					} else {
						if (tmp.equals("parameters")) {
							// Get the parameters.
							nl2 = n.getChildNodes();
							for (int j = 0; j < nl2.getLength(); j++) {
								n2 = nl2.item(j);
								if (n2.getNodeType() == Node.ELEMENT_NODE) {
									nnm = n2.getAttributes();
									parameters.put(nnm.getNamedItem("name").getNodeValue(), new String[]{nnm.getNamedItem("type").getNodeValue(), nnm.getNamedItem("value").getNodeValue()});
								}
							}
						} else {
							if (tmp.equals("exceptions")) {
								// Get the exceptions.
								nl2 = n.getChildNodes();
								for (int j = 0; j < nl2.getLength(); j++) {
									n2 = nl2.item(j);
									if (n2.getNodeType() == Node.ELEMENT_NODE) {
										nnm = n2.getAttributes();
										tempo = new String[6];
										tempo[0] = nnm.getNamedItem("role").getNodeValue();
										tempo[1] = nnm.getNamedItem("origin").getNodeValue();
										tempo[2] = nnm.getNamedItem("type").getNodeValue().toLowerCase();
										nl3 = n2.getChildNodes();
										for (int k = 0; k < nl3.getLength(); k++) {
											n3 = nl3.item(k);
											if (n3.getNodeType() == Node.ELEMENT_NODE) {
												nl4 = n3.getChildNodes();
												text = n3.getNodeName().toLowerCase();
												if ((text.equals("message")) || (text.equals("class"))) {
													int index = text.equals("message") ? 3 : 4;
													text = "";
													for (int l = 0; l < nl4.getLength(); l++) {
														n4 = nl4.item(l);
														if (n4.getNodeType() == Node.TEXT_NODE) {
															text += n4.getNodeValue().trim();
														}
														tempo[index] = text;
													}
												} else {
													// Get the stacktrace.
													for (int l = 0; l < nl4.getLength(); l++) {
														n4 = nl4.item(l);
														if (n4.getNodeType() == Node.CDATA_SECTION_NODE) {
															tempo[5] = "<![CDATA[ " + n4.getNodeValue().trim() + " ]]>";
														}
													}
												}
											}
										}
										exceptions.add(tempo);
									}
								}
							} else {
								if (tmp.equals("traceroute")) {
									// Get the traceroute.
									nl2 = n.getChildNodes();
									for (int j = 0; j < nl2.getLength(); j++) {
										n2 = nl2.item(j);
										if (n2.getNodeType() == Node.ELEMENT_NODE) {
											tempo = new String[7];
											nnm = n2.getAttributes();
											tempo[0] = nnm.getNamedItem("role").getNodeValue();
											tempo[1] = nnm.getNamedItem("rolename").getNodeValue();
											tempo[2] = nnm.getNamedItem("role-ip").getNodeValue();
											tempo[3] = nnm.getNamedItem("role-version").getNodeValue();
											tempo[4] = nnm.getNamedItem("time").getNodeValue();
											tempo[5] = nnm.getNamedItem("action").getNodeValue();
											nl3 = n2.getChildNodes();
											text = "";
											for (int k = 0; k < nl3.getLength(); k++) {
												n4 = nl3.item(k);
												if (n4.getNodeType() == Node.TEXT_NODE) {
													text += n4.getNodeValue().trim();
												}
											}
											tempo[6] = text;
											routes.add(tempo);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		if (type == -1) {
			throw (new RuntimeException("The type of the query must be defined in the XML description."));
		}
	}


	/**
	 * Adds a new Exception, given its XML representation.
	 *
	 * @param exception  XML representation of the new exception.
	 */
	public void addException(String exception) {
		String[] tmp = new String[6];
		int start = exception.indexOf("role=\"") + 6;
		if (start == 5) {
			throw (new RuntimeException("Misformed exception: Attribute 'role' is not defined in the node exception."));
		}
		int end = exception.indexOf("\"", start);
		tmp[0] = exception.substring(start, end);
		start = exception.indexOf("origin=\"") + 8;
		if (start == 7) {
			throw (new RuntimeException("Misformed exception: Attribute 'origin' is not defined in the node exception."));
		}
		end = exception.indexOf("\"", start);
		tmp[1] = exception.substring(start, end);
		start = exception.indexOf("type=\"") + 6;
		if (start == 5) {
			throw (new RuntimeException("Misformed exception: Attribute 'type' is not defined in the node exception."));
		}
		end = exception.indexOf("\"", start);
		tmp[2] = exception.substring(start, end);
		tmp[3] = exception.substring(exception.indexOf("<message>") + 9, exception.indexOf("</message>"));
		tmp[4] = exception.substring(exception.indexOf("<class>") + 7, exception.indexOf("</class>"));
		tmp[5] = exception.substring(exception.indexOf("<stacktrace>") + 12, exception.indexOf("</stacktrace>")).trim();
		if (!tmp[5].startsWith("<![CDATA[")) {
			tmp[5] = "<![CDATA[" + tmp[5] + " ]]>";
		}
		exceptions.add(tmp);
	}


	/**
	 * Adds new exception.
	 *
	 * @param origin         Origin of the exception.
	 * @param exceptionType  Type of the exception.
	 * @param message        Message of the exception.
	 * @param classname      Name of the class where the exception occurred.
	 * @param trace          Stacktrace of the exception.
	 */
	public void addException(String origin, int exceptionType, String message, String classname, String trace) {
		exceptions.add(new String[]{roleNames[roletype], origin, exceptionNames[exceptionType], message, classname, "<![CDATA[ " + trace + " ]]>"});
	}


	/**
	 * Deletes all the exceptions within the Context.
	 */
	public void removeAllExceptions() {
		exceptions = new Vector<String[]>();
	}


	/**
	 * Adds a new route with the current time.
	 *
	 * @param action       Action of the route.
	 * @param description  Description of the route.
	 * @since              2.0
	 */
	public void addRoute(String action, String description) {
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.UK);
		routes.add(new String[]{roleNames[roletype], rolename, roleip, roleversion, df.format(new Date()), action, description});
	}


	/**
	 * Adds a new route with a specified date.
	 *
	 * @param action       Action of the route.
	 * @param description  Description of the route.
	 * @param time         Time when the route occured.
	 * @since              2.0
	 */
	public void addRoute(String action, String description, Date time) {
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.UK);
		routes.add(new String[]{roleNames[roletype], rolename, roleip, roleversion, df.format(time), action, description});
	}


	/**
	 * Deletes all the routes within the Context.
	 */
	public void removeAllRoutes() {
		routes = new Vector<String[]>();
	}


	/**
	 * Adds a new parameter. Using this method sets the type of parameter to
	 * SYSTEMINFO.
	 *
	 * @param name   Name of the parameter.
	 * @param value  Value of the parameter.
	 * @return       The previous value (if exists, <code>null</code> otherwise) of
	 *      this parameter.
	 */
	public String addParameter(String name, String value) {
		String[] tmp = parameters.put(name, new String[]{"SYSTEMINFO", value});
		if (tmp != null) {
			return (tmp[1]);
		}
		return (null);
	}


	/**
	 * Adds a parameter.
	 *
	 * @param name       Name of the parameter.
	 * @param paramType  Type of parameter.
	 * @param value      Value of the parameter.
	 * @return           The previous value (if exists, <code>null</code>
	 *      otherwise) of this parameter.
	 */
	public String addParameter(String name, int paramType, String value) {
		String[] tmp = parameters.put(name, new String[]{paramNames[paramType], value});
		if (tmp != null) {
			return (tmp[1]);
		}
		return (null);
	}


	/**
	 * Removes a parameter.
	 *
	 * @param param  Name of the parameter to be removed.
	 * @return       The value (if exists, <code>null</code> otherwise) of this
	 *      parameter.
	 */
	public String removeParameter(String param) {
		String[] tmp = parameters.remove(param);
		if (tmp != null) {
			return (tmp[1]);
		}
		return (null);
	}


	/**
	 * Removes all parameters within the Context.
	 */
	public void removeAllParameters() {
		parameters = new Hashtable<String,String[]>();
	}


	/**
	 * Get the number of parameters.
	 *
	 * @return   Number of parameters.
	 */
	public int numberOfParameters() {
		return (parameters.size());
	}


	/**
	 * Returns a String (XML in fact) representation of this Context.
	 *
	 * @return   String (XML) representation of the Context.
	 */
	public String toString() {
		return (toXML());
	}


	/**
	 * Returns a XML representation of this Context.
	 *
	 * @return   XML representation of the Context.
	 */
	public String toXML() {
		StringBuffer sb = new StringBuffer();
		sb.append("<context>");
		if (id != null) {
			sb.append("<id value=\"" + id + "\"/>");
		}
		sb.append("<type value=\"" + typeNames[type] + "\"/><parameters>");
		String key = null;
		for (Enumeration<String> e = parameters.keys(); e.hasMoreElements(); ) {
			key = e.nextElement();
			String[] tmp = parameters.get(key);
			sb.append("<parameter type=\"" + tmp[0] + "\" name=\"" + key + "\" value=\"" + tmp[1] + "\"/>");
		}
		sb.append("</parameters><traceroute>");
		for (String[] tmp:routes) {
			sb.append("<route role=\"" + tmp[0] + "\" rolename=\"" + tmp[1] + "\" role-ip=\"" + tmp[2] + "\" role-version=\"" + tmp[3] + "\" time=\"" + tmp[4] + "\" action=\"" + tmp[5] + "\">" + tmp[6] + "</route>");
		}
		sb.append("</traceroute><exceptions>");
		for (String[] tmp:exceptions) {
			sb.append("<exception role=\"" + tmp[0] + "\" origin=\"" + tmp[1] + "\" type=\"" + tmp[2] + "\">");
			sb.append("<message>" + tmp[3] + "</message><class>" + tmp[4] + "</class>");
			sb.append("<stacktrace>" + tmp[5] + "</stacktrace></exception>");
		}
		sb.append("</exceptions></context>");
		return (sb.toString());
	}


	/**
	 * Returns a XML representation of this Context.
	 *
	 * @param indentation  Boolean at <code>true</code> if the representation is
	 *      indented, at <code>false</code> otherwise.
	 * @return             XML representation of the Context.
	 */
	public String toXML(boolean indentation) {
		if (!indentation) {
			return (toXML());
		}
		String indent = "  ";
		StringBuffer sb = new StringBuffer();
		sb.append("<context>\n");
		if (id != null) {
			sb.append(indent + "<id value=\"" + id + "\"/>\n");
		}
		sb.append(indent + "<type value=\"" + typeNames[type] + "\"/>\n");
		if (parameters.size() == 0) {
			sb.append(indent + "<parameters/>\n");
		} else {
			sb.append(indent + "<parameters>\n");
			for (Enumeration<String> e = parameters.keys(); e.hasMoreElements(); ) {
				String key = e.nextElement();
				String[] tmp = parameters.get(key);
				sb.append(indent + indent + "<parameter type=\"" + tmp[0] + "\" name=\"" + key + "\" value=\"" + tmp[1] + "\"/>\n");
			}
			sb.append(indent + "</parameters>\n");
		}
		if (routes.size() == 0) {
			sb.append(indent + "<traceroute/>\n");
		} else {
			sb.append(indent + "<traceroute>\n");
			for (String[] tmp:routes) {
				sb.append(indent + indent + "<route role=\"" + tmp[0] + "\" rolename=\"" + tmp[1] + "\" role-ip=\"" + tmp[2] + "\" role-version=\"" + tmp[3] + "\" time=\"" + tmp[4] + "\" action=\"" + tmp[5] + "\">" + tmp[6] + "</route>\n");
			}
			sb.append(indent + "</traceroute>\n");
		}
		if (exceptions.size() == 0) {
			sb.append(indent + "<exceptions/>\n");
		} else {
			sb.append(indent + "<exceptions>\n");
			for (String[] tmp:exceptions) {
				sb.append(indent + indent + "<exception role=\"" + tmp[0] + "\" origin=\"" + tmp[1] + "\" type=\"" + tmp[2] + "\">\n");
				sb.append(indent + indent + indent + "<message>" + tmp[3] + "</message>\n");
				sb.append(indent + indent + indent + "<class>" + tmp[4] + "</class>\n");
				sb.append(indent + indent + indent + "<stacktrace>\n" + tmp[5] + "\n" + indent + indent + indent + "</stacktrace>\n");
				sb.append(indent + indent + "</exception>\n");
			}
			sb.append(indent + "</exceptions>\n");
		}
		sb.append("</context>");
		return (sb.toString());
	}

}

