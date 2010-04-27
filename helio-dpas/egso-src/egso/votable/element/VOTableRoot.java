package org.egso.votable.element;

import java.util.*;


/**
 *  The VOTableRoot is the root element of a VOTable object.
 *
 * @author     Romain Linsolas (linsolas@gmail.com)
 * @version    0.9
 * @created    28 October 2003
 */
/*
0.9 - 25/11/2003: Adding data access methods.
*/
public class VOTableRoot extends VOTableElement implements DataAccess {

	/**
	 *  &lt;DESCRIPTION&gt; part of the &lt;VOTABLE&gt; node. Can be <code>null</code>
	 */
	private Description description = null;
	/**
	 *  &lt;DEFINITIONS&gt; part of the &lt;VOTABLE&gt; node. Can be <code>null</code>
	 */
	private Definitions definitions = null;
	/**
	 *  &lt;INFO&gt; part of the &lt;VOTABLE&gt; node. Can be empty.
	 */
	private VOTableSet info = null;
	/**
	 *  &lt;RESOURCE&gt; part of the &lt;VOTABLE&gt; node. Can be empty.
	 */
	private VOTableSet resource = null;


	/**
	 *  Builds a VOTableRoot object.
	 */
	public VOTableRoot() {
		super("VOTABLE");
		addAttribute(new Attribute("ID"));
		addAttribute(new Attribute("version"));
		info = new VOTableSet(VOTableSet.INFO, VOTableSet.ZERO_MANY_ELT);
		resource = new VOTableSet(VOTableSet.RESOURCE, VOTableSet.ZERO_MANY_ELT);
	}


	/**
	 *  Sets the ID attribute.
	 *
	 * @param  id  The new ID value.
	 */
	public void setID(String id) {
		getAttribute("ID").setValue(id);
	}


	/**
	 *  Sets the version attribute of the VOTable object.
	 *
	 * @param  v  The new version value.
	 */
	public void setVersion(String v) {
		getAttribute("version").setValue(v);
	}


	/**
	 *  Sets the &lt;DESCRIPTION&gt; part of the VOTable object.
	 *
	 * @param  desc  The new &lt;DESCRIPTION&gt; node.
	 */
	public void setDescription(Description desc) {
		description = desc;
	}


	/**
	 *  Sets the &lt;DEFINITIONS&gt; node of the VOTable object.
	 *
	 * @param  def  The new &lt;DEFINITIONS&gt; node.
	 */
	public void setDefinitions(Definitions def) {
		definitions = def;
	}


	/**
	 *  Gets the ID attribute of the VOTable object.
	 *
	 * @return    The ID value.
	 */
	public String getID() {
		return (getAttribute("ID").getValue());
	}


	/**
	 *  Gets the version attribute of the VOTable object.
	 *
	 * @return    The version value.
	 */
	public String getVersion() {
		return (getAttribute("version").getValue());
	}


	/**
	 *  Gets the &lt;DESCRIPTION&gt; node of the VOTable object.
	 *
	 * @return    The node &lt;DESCRIPTION&gt;.
	 */
	public Description getDescription() {
		return (description);
	}


	/**
	 *  Gets the &lt;DEFINITIONS&gt; node of the VOTable object
	 *
	 * @return    The node &ltDEFINITIONS;&gt;.
	 */
	public Definitions getDefinitions() {
		return (definitions);
	}


	/**
	 *  Gets the VOTableSet &lt;INFO&gt; of the VOTable object.
	 *
	 * @return    The &lt;INFO&gt; VOTableSet.
	 */
	public VOTableSet getInfo() {
		return (info);
	}


	/**
	 *  Gets the VOTableSet &lt;RESOURCE&gt; of the VOTable object.
	 *
	 * @return    The &lt;RESOURCE&gt; VOTableSet.
	 */
	public VOTableSet getResource() {
		return (resource);
	}


	/**
	 *  Get all field names.
	 *
	 * @return    List of field names (as String), <code>null</code> if no fields
	 *      are specified in the VOTable.
	 */
	public List getFieldNames() {
		if (resource.size() == 0) {
			return (null);
		}
		List list = ((Resource) resource.get(0)).getFieldNames();
		for (int i = 1; i < resource.size(); i++) {
			list.addAll(((Resource) resource.get(i)).getFieldNames());
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
		if (resource.size() == 0) {
			return (null);
		}
		List list = ((Resource) resource.get(0)).getAllValues(fieldName);
		for (int i = 1; i < resource.size(); i++) {
			list.addAll(((Resource) resource.get(i)).getAllValues(fieldName));
		}
		return (list);
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
		if (resource.size() == 0) {
			return (null);
		}
		List list = ((Resource) resource.get(0)).getAllRows();
		for (int i = 1; i < resource.size(); i++) {
			list.addAll(((Resource) resource.get(i)).getAllRows());
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
		if (resource.size() == 0) {
			return (null);
		}
		List list = null;
		try {
			list = ((Resource) resource.get(0)).getRow(row);
			for (int i = 1; i < resource.size(); i++) {
				list.addAll(((Resource) resource.get(i)).getRow(row));
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			throw e;
		}
		return (list);
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
		if (resource.size() == 0) {
			return (null);
		}
		List list = ((Resource) resource.get(0)).getRows(fieldName, fieldValue, isCaseSensitive);
		for (int i = 1; i < resource.size(); i++) {
			list.addAll(((Resource) resource.get(i)).getRows(fieldName, fieldValue, isCaseSensitive));
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
		if (resource.size() == 0) {
			return (0);
		}
		int nb = 0;
		for (int i = 0; i < resource.size(); i++) {
			nb += ((Resource) resource.get(i)).getNumberOfRows();
		}
		return (nb);
	}

	/**
	 * Gets the type of the <b>FIRST</b> field.
	 * @ param fieldName Name of the field.
	 * @return Type of the field (@see VOTableConstants), or -1 if the field does
	 * not exist.
	 **/
	public int getFieldType(String fieldName) {
		if (resource.size() == 0) {
			return (-1);
		}
		int type = -1;
		int i = 0;
		while ((type == -1) && (i < resource.size())) {
			type = ((Resource) resource.get(i)).getFieldType(fieldName);
			i++;
		}
		return (type);
	}

	/**
	 *  Adds an &lt;INFO&gt; node.
	 *
	 * @param  inf  &lt;INFO&gt; node to be added.
	 */
	public void addInfo(Info inf) {
		info.add(inf);
	}


	/**
	 *  Adds an array of &lt;INFO&gt; nodes.
	 *
	 * @param  inf  Array of &lt;INFO&gt; nodes that must be added.
	 */
	public void addInfos(Info[] inf) {
		info.addAll(Arrays.asList(inf));
	}


	/**
	 *  Adds a &lt;RESOURCE&gt; node.
	 *
	 * @param  res  &lt;RESOURCE&gt; node to be added.
	 */
	public void addResource(Resource res) {
		resource.add(res);
	}


	/**
	 *  Adds an array of &lt;RESOURCE&gt; nodes.
	 *
	 * @param  res  The array of &lt;RESOURCE&gt; nodes that must be added.
	 */
	public void addResources(Resource[] res) {
		resource.addAll(Arrays.asList(res));
	}


	/**
	 *  String representation of the element.
	 *
	 * @return    Representation of the element.
	 */
	public String toString() {
//		System.out.println("VOTABLEROOT:\n\tDescription: " + description + "\n\tDEFINITIONS: " + definitions + "\n\tINFO: " + info.size() + "\n\tRESOURCE:" + resource.size());
		return ("<VOTABLE " + attributes.toString() + ">" + ((description != null) ? description.toString() : "") +
				((definitions != null) ? definitions.toString() : "") + info.toString() + resource.toString() + "</VOTABLE>");
	}

}

