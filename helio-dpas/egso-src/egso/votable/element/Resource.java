package org.egso.votable.element;

import java.util.*;


/**
 *  JAVADOC: Description of the Class
 *
 * @author     Romain Linsolas (linsolas@gmail.com)
 * @version    0.9
 * @created    28 October 2003 0.9: 25/11/2003.
 */
public class Resource extends VOTableElement {

	/**
	 *  JAVADOC: Description of the Field
	 */
	private Description description = null;
	/**
	 *  JAVADOC: Description of the Field
	 */
	private VOTableSet info = null;
	/**
	 *  JAVADOC: Description of the Field
	 */
	private VOTableSet coosys = null;
	/**
	 *  JAVADOC: Description of the Field
	 */
	private VOTableSet param = null;
	/**
	 *  JAVADOC: Description of the Field
	 */
	private VOTableSet link = null;
	/**
	 *  JAVADOC: Description of the Field
	 */
	private VOTableSet table = null;
	/**
	 *  JAVADOC: Description of the Field
	 */
	private VOTableSet resource = null;


	/**
	 *  JAVADOC: Constructor for the Resource object
	 */
	public Resource() {
		super("RESOURCE");
		addAttribute(new Attribute("name"));
		addAttribute(new Attribute("ID"));
		addAttribute(new Attribute("type", null, "results", new String[]{"results", "meta"}));
		info = new VOTableSet(VOTableSet.INFO, VOTableSet.ZERO_MANY_ELT);
		coosys = new VOTableSet(VOTableSet.COOSYS, VOTableSet.ZERO_MANY_ELT);
		param = new VOTableSet(VOTableSet.PARAM, VOTableSet.ZERO_MANY_ELT);
		link = new VOTableSet(VOTableSet.LINK, VOTableSet.ZERO_MANY_ELT);
		table = new VOTableSet(VOTableSet.TABLE, VOTableSet.ZERO_MANY_ELT);
		resource = new VOTableSet(VOTableSet.RESOURCE, VOTableSet.ZERO_MANY_ELT);
	}


	/**
	 *  JAVADOC: Sets the name attribute of the Resource object
	 *
	 * @param  name  JAVADOC: The new name value
	 */
	public void setName(String name) {
		getAttribute("name").setValue(name);
	}


	/**
	 *  JAVADOC: Sets the iD attribute of the Resource object
	 *
	 * @param  id  JAVADOC: The new iD value
	 */
	public void setID(String id) {
		getAttribute("ID").setValue(id);
	}


	/**
	 *  JAVADOC: Sets the type attribute of the Resource object
	 *
	 * @param  type  JAVADOC: The new type value
	 */
	public void setType(String type) {
		getAttribute("type").setValue(type);
	}


	/**
	 *  JAVADOC: Sets the description attribute of the Resource object
	 *
	 * @param  d  JAVADOC: The new description value
	 */
	public void setDescription(Description d) {
		description = d;
	}


	/**
	 *  JAVADOC: Gets the name attribute of the Resource object
	 *
	 * @return    JAVADOC: The name value
	 */
	public String getName() {
		return (getAttribute("name").getValue());
	}


	/**
	 *  JAVADOC: Gets the iD attribute of the Resource object
	 *
	 * @return    JAVADOC: The iD value
	 */
	public String getID() {
		return (getAttribute("ID").getValue());
	}


	/**
	 *  JAVADOC: Gets the type attribute of the Resource object
	 *
	 * @return    JAVADOC: The type value
	 */
	public String getType() {
		return (getAttribute("type").getValue());
	}


	/**
	 *  JAVADOC: Gets the description attribute of the Resource object
	 *
	 * @return    JAVADOC: The description value
	 */
	public Description getDescription() {
		return (description);
	}


	/**
	 *  JAVADOC: Gets the info attribute of the Resource object
	 *
	 * @return    JAVADOC: The info value
	 */
	public VOTableSet getInfo() {
		return (info);
	}


	/**
	 *  JAVADOC: Gets the coosys attribute of the Resource object
	 *
	 * @return    JAVADOC: The coosys value
	 */
	public VOTableSet getCoosys() {
		return (coosys);
	}


	/**
	 *  JAVADOC: Gets the param attribute of the Resource object
	 *
	 * @return    JAVADOC: The param value
	 */
	public VOTableSet getParam() {
		return (param);
	}


	/**
	 *  JAVADOC: Gets the link attribute of the Resource object
	 *
	 * @return    JAVADOC: The link value
	 */
	public VOTableSet getLink() {
		return (link);
	}


	/**
	 *  JAVADOC: Gets the table attribute of the Resource object
	 *
	 * @return    JAVADOC: The table value
	 */
	public VOTableSet getTable() {
		return (table);
	}


	/**
	 *  JAVADOC: Gets the resource attribute of the Resource object
	 *
	 * @return    JAVADOC: The resource value
	 */
	public VOTableSet getResource() {
		return (resource);
	}


	/**
	 *  Get all rows of the &lt;TABLEDATA&gt;, i.e. all data contained in the
	 *  VOTable.
	 *
	 * @return    A List of Lists of String. Each List is a row, each String in a
	 *      row is a value. <code>null</code> if no &lt;TABLEDATA&gt; node is
	 *      present in the VOTable.
	 */
	public List getAllRows() {
		List list = null;
		if (table.size() == 0) {
			// If no <TABLE> is present, just initiate the list variable.
			if (resource.size() == 0) {
				return (null);
			}
			Vector v = new Vector();
			list = v.subList(0, 0);
		} else {
			// Add all information from <TABLE>
			list = ((Table) table.firstElement()).getAllRows();
			for (int i = 1; i < table.size(); i++) {
				list.addAll(((Table) table.get(i)).getAllRows());
			}
		}
		// See if we also have some <RESOURCE> that may contains <TABLE>...
		List tmp = null;
		for (Iterator it = resource.iterator(); it.hasNext(); ) {
			tmp = ((Resource) it.next()).getAllRows();
			if (tmp != null) {
				list.addAll(tmp);
			}
		}
		if ((list == null) || (list.size() == 0)) {
			return (null);
		}
		return (list);
	}


	/**
	 *  Get all values for a given field name.
	 *
	 * @param  fieldName  Name of the field.
	 * @return            A List of values (String), or <code>null</code> if the
	 *      given field name doesn't exist in the VOTable.
	 */
	public List getAllValues(String fieldName) {
		List list = null;
		if (table.size() == 0) {
			// If no <TABLE> is present, just initiate the list variable.
			if (resource.size() == 0) {
				return (null);
			}
			Vector v = new Vector();
			list = v.subList(0, 0);
		} else {
			// Add all information from <TABLE>
			list = ((Table) table.firstElement()).getAllValues(fieldName);
			for (int i = 1; i < table.size(); i++) {
				list.addAll(((Table) table.get(i)).getAllValues(fieldName));
			}
		}
		// See if we also have some <RESOURCE> that may contains <TABLE>...
		List tmp = null;
		for (Iterator it = resource.iterator(); it.hasNext(); ) {
			tmp = ((Resource) it.next()).getAllValues(fieldName);
			if (tmp != null) {
				list.addAll(tmp);
			}
		}
		if ((list == null) || (list.size() == 0)) {
			return (null);
		}
		return (list);
	}


	/**
	 *  Get the 'row'-th row (or results) on the VOTable.
	 *
	 * @param  row                              Row number.
	 * @return                                  List of values (String) for the
	 *      'row'-th row.
	 * @throws  ArrayIndexOutOfBoundsException  index is < 0 or greater than the
	 *      number of rows.
	 */
	public List getRow(int row) throws ArrayIndexOutOfBoundsException {
		List list = null;
		if (table.size() == 0) {
			// If no <TABLE> is present, just initiate the list variable.
			if (resource.size() == 0) {
				return (null);
			}
			Vector v = new Vector();
			list = v.subList(0, 0);
		} else {
			// Add all information from <TABLE>
			try {
				list = ((Table) table.firstElement()).getRow(row);
				for (int i = 1; i < table.size(); i++) {
					list.addAll(((Table) table.get(i)).getRow(row));
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				throw e;
			}
		}
		// See if we also have some <RESOURCE> that may contains <TABLE>...
		List tmp = null;
		for (Iterator it = resource.iterator(); it.hasNext(); ) {
			try {
				tmp = ((Resource) it.next()).getRow(row);
				if (tmp != null) {
					list.addAll(tmp);
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				throw e;
			}
		}
		if ((list == null) || (list.size() == 0)) {
			return (null);
		}
		return (list);
	}


	/**
	 *  Get all values where fieldName=fieldValue. The fieldValue value can be case
	 *  sensitive or not.
	 *
	 * @param  fieldName        Name of the field.
	 * @param  fieldValue       Value of the field.
	 * @param  isCaseSensitive  Boolean that specifies if the fieldValue is case
	 *      sensitive or not.
	 * @return                  List of List of String. Each List is a row (a
	 *      result), each String is a value. <code>null</code> if there is no
	 *      result where fieldName=fieldValue.
	 */
	public List getRows(String fieldName, String fieldValue, boolean isCaseSensitive) {
		List list = null;
		if (table.size() == 0) {
			// If no <TABLE> is present, just initiate the list variable.
			if (resource.size() == 0) {
				return (null);
			}
			Vector v = new Vector();
			list = v.subList(0, 0);
		} else {
			// Add all information from <TABLE>
			list = ((Table) table.firstElement()).getRows(fieldName, fieldValue, isCaseSensitive);
			for (int i = 1; i < table.size(); i++) {
				list.addAll(((Table) table.get(i)).getRows(fieldName, fieldValue, isCaseSensitive));
			}
		}
		// See if we also have some <RESOURCE> that may contains <TABLE>...
		List tmp = null;
		for (Iterator it = resource.iterator(); it.hasNext(); ) {
			tmp = ((Resource) it.next()).getRows(fieldName, fieldValue, isCaseSensitive);
			if (tmp != null) {
				list.addAll(tmp);
			}
		}
		if ((list == null) || (list.size() == 0)) {
			return (null);
		}
		return (list);
	}


	/**
	 *  Gets the type of the <b>FIRST</b> field. @ param fieldName Name of the
	 *  field.
	 *
	 * @param  fieldName  JAVADOC: Description of the Parameter
	 * @return            Type of the field (@see VOTableConstants), or -1 if the
	 *      field does not exist.
	 */
	public int getFieldType(String fieldName) {
		int type = -1;
		if (table.size() == 0) {
			if (resource.size() == 0) {
				return (-1);
			}
		} else {
			int i = 0;
			while ((type == -1) && (i < table.size())) {
				type = ((Table) table.get(i)).getFieldType(fieldName);
				i++;
			}
			if (type != -1) {
				return (type);
			}
		}
		// See if we also have some <RESOURCE> that may contains <TABLE>...
		int i = 0;
		while ((type == -1) && (i < resource.size())) {
			type = ((Resource) resource.get(i)).getFieldType(fieldName);
			i++;
		}
		return (type);
	}


	/**
	 *  Get all values where fieldName=fieldValue (fieldValue is case insensitive).
	 *
	 * @param  fieldName   Name of the field.
	 * @param  fieldValue  Value of the field.
	 * @return             List of List of String. Each List is a row (a result),
	 *      each String is a value. <code>null</code> if there is no result where
	 *      fieldName=fieldValue.
	 */
	public List getRows(String fieldName, String fieldValue) {
		return (getRows(fieldName, fieldValue, false));
	}


	/**
	 *  Get all field names.
	 *
	 * @return    List of field names (as String), <code>null</code> if no fields
	 *      are specified in the VOTable.
	 */
	public List getFieldNames() {
		List list = null;
		if (table.size() == 0) {
			// If no <TABLE> is present, just initiate the list variable.
			if (resource.size() == 0) {
				return (null);
			}
			Vector v = new Vector();
			list = v.subList(0, 0);
		} else {
			// Add all information from <TABLE>
			list = ((Table) table.firstElement()).getFieldNames();
			for (int i = 1; i < table.size(); i++) {
				list.addAll(((Table) table.get(i)).getFieldNames());
			}
		}
		// See if we also have some <RESOURCE> that may contains <TABLE>...
		List tmp = null;
		for (Iterator it = resource.iterator(); it.hasNext(); ) {
			tmp = ((Resource) it.next()).getFieldNames();
			if (tmp != null) {
				list.addAll(tmp);
			}
		}
		if ((list == null) || (list.size() == 0)) {
			return (null);
		}
		return (list);
	}


	/**
	 *  Returns the number of results contained in the whole VOTable (it is
	 *  possible that the VOTable contains more than one &lt;TABLEDATA&gt; node).
	 *
	 * @return    Number of rows in &lt;TABLEDATA&gt; (i.e. results).
	 */
	public int getNumberOfRows() {
		int nb = 0;
		if (table.size() == 0) {
			if (resource.size() == 0) {
				return (0);
			}
		} else {
			for (int i = 0; i < table.size(); i++) {
				nb += ((Table) table.get(i)).getNumberOfRows();
			}
		}
		// See if we also have some <RESOURCE> that may contains <TABLE>...
		for (Iterator it = resource.iterator(); it.hasNext(); ) {
			nb += ((Resource) it.next()).getNumberOfRows();
		}
		return (nb);
	}


	/**
	 *  JAVADOC: Adds a feature to the Info attribute of the Resource object
	 *
	 * @param  inf  JAVADOC: The feature to be added to the Info attribute
	 */
	public void addInfo(Info inf) {
		info.add(inf);
	}


	/**
	 *  JAVADOC: Adds a feature to the Infos attribute of the Resource object
	 *
	 * @param  inf  JAVADOC: The feature to be added to the Infos attribute
	 */
	public void addInfos(Info[] inf) {
	  info.addAll(Arrays.asList(inf));
	}


	/**
	 *  JAVADOC: Adds a feature to the Coosys attribute of the Resource object
	 *
	 * @param  cs  JAVADOC: The feature to be added to the Coosys attribute
	 */
	public void addCoosys(Coosys cs) {
		coosys.add(cs);
	}


	/**
	 *  JAVADOC: Adds a feature to the Coosys attribute of the Resource object
	 *
	 * @param  cs  JAVADOC: The feature to be added to the Coosys attribute
	 */
	public void addCoosys(Coosys[] cs) {
		coosys.addAll(Arrays.asList(cs));
	}


	/**
	 *  JAVADOC: Adds a feature to the Param attribute of the Resource object
	 *
	 * @param  p  JAVADOC: The feature to be added to the Param attribute
	 */
	public void addParam(Param p) {
		param.add(p);
	}


	/**
	 *  JAVADOC: Adds a feature to the Params attribute of the Resource object
	 *
	 * @param  p  JAVADOC: The feature to be added to the Params attribute
	 */
	public void addParams(Param[] p) {
		param.addAll(Arrays.asList(p));
	}


	/**
	 *  JAVADOC: Adds a feature to the Link attribute of the Resource object
	 *
	 * @param  l  JAVADOC: The feature to be added to the Link attribute
	 */
	public void addLink(Link l) {
		link.add(l);
	}


	/**
	 *  JAVADOC: Adds a feature to the Links attribute of the Resource object
	 *
	 * @param  l  JAVADOC: The feature to be added to the Links attribute
	 */
	public void addLinks(Link[] l) {
		link.addAll(Arrays.asList(l));
	}


	/**
	 *  JAVADOC: Adds a feature to the Table attribute of the Resource object
	 *
	 * @param  t  JAVADOC: The feature to be added to the Table attribute
	 */
	public void addTable(Table t) {
		table.add(t);
	}


	/**
	 *  JAVADOC: Adds a feature to the Tables attribute of the Resource object
	 *
	 * @param  t  JAVADOC: The feature to be added to the Tables attribute
	 */
	public void addTables(Table[] t) {
		table.addAll(Arrays.asList(t));
	}


	/**
	 *  JAVADOC: Adds a feature to the Resource attribute of the Resource object
	 *
	 * @param  r  JAVADOC: The feature to be added to the Resource attribute
	 */
	public void addResource(Resource r) {
		resource.add(r);
	}


	/**
	 *  JAVADOC: Adds a feature to the Resources attribute of the Resource object
	 *
	 * @param  r  JAVADOC: The feature to be added to the Resources attribute
	 */
	public void addResources(Resource[] r) {
		resource.addAll(Arrays.asList(r));
	}


	/**
	 *  String representation of the element.
	 *
	 * @return    Representation of the element.
	 */
	public String toString() {
		String tmp = ((description != null) ? description.toString() : "") + info.toString() + coosys.toString() + param.toString() +
				link.toString() + table.toString() + resource.toString();
		return ("<RESOURCE " + attributes.toString() + ">" + tmp + "</RESOURCE>");
	}

}

