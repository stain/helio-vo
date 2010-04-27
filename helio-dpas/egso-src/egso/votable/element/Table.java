package org.egso.votable.element;

import java.util.*;


/**
 *  JAVADOC: Description of the Class
 *
 * @author     Romain Linsolas (linsolas@gmail.com)
 * @version    0.9
 * @created    28 October 2003.
 */
public class Table extends VOTableElement {

	/**
	 *  JAVADOC: Description of the Field
	 */
	private Description description = null;
	/**
	 *  JAVADOC: Description of the Field
	 */
	private VOTableSet field = null;
	/**
	 *  JAVADOC: Description of the Field
	 */
	private VOTableSet link = null;
	/**
	 *  JAVADOC: Description of the Field
	 */
	private Data data = null;


	/**
	 *  JAVADOC: Constructor for the Table object
	 */
	public Table() {
		super("TABLE");
		addAttribute(new Attribute("ID"));
		addAttribute(new Attribute("name"));
		addAttribute(new Attribute("ref"));
		field = new VOTableSet(VOTableSet.FIELD, VOTableSet.ZERO_MANY_ELT);
		link = new VOTableSet(VOTableSet.LINK, VOTableSet.ZERO_MANY_ELT);
	}


	/**
	 *  JAVADOC: Sets the iD attribute of the Table object
	 *
	 * @param  id  JAVADOC: The new iD value
	 */
	public void setID(String id) {
		getAttribute("ID").setValue(id);
	}


	/**
	 *  JAVADOC: Sets the name attribute of the Table object
	 *
	 * @param  name  JAVADOC: The new name value
	 */
	public void setName(String name) {
		getAttribute("name").setValue(name);
	}


	/**
	 *  JAVADOC: Sets the ref attribute of the Table object
	 *
	 * @param  ref  JAVADOC: The new ref value
	 */
	public void setRef(String ref) {
		getAttribute("ref").setValue(ref);
	}


	/**
	 *  JAVADOC: Sets the description attribute of the Table object
	 *
	 * @param  desc  JAVADOC: The new description value
	 */
	public void setDescription(Description desc) {
		description = desc;
	}


	/**
	 *  JAVADOC: Sets the data attribute of the Table object
	 *
	 * @param  d  JAVADOC: The new data value
	 */
	public void setData(Data d) {
		data = d;
	}


	/**
	 *  JAVADOC: Gets the iD attribute of the Table object
	 *
	 * @return    JAVADOC: The iD value
	 */
	public String getID() {
		return (getAttribute("ID").getValue());
	}


	/**
	 *  JAVADOC: Gets the name attribute of the Table object
	 *
	 * @return    JAVADOC: The name value
	 */
	public String getName() {
		return (getAttribute("name").getValue());
	}


	/**
	 *  JAVADOC: Gets the ref attribute of the Table object
	 *
	 * @return    JAVADOC: The ref value
	 */
	public String getRef() {
		return (getAttribute("ref").getValue());
	}


	/**
	 *  JAVADOC: Gets the description attribute of the Table object
	 *
	 * @return    JAVADOC: The description value
	 */
	public Description getDescription() {
		return (description);
	}


	/**
	 *  JAVADOC: Gets the data attribute of the Table object
	 *
	 * @return    JAVADOC: The data value
	 */
	public Data getData() {
		return (data);
	}


	/**
	 *  JAVADOC: Gets the link attribute of the Table object
	 *
	 * @return    JAVADOC: The link value
	 */
	public VOTableSet getLink() {
		return (link);
	}


	/**
	 *  JAVADOC: Gets the field attribute of the Table object
	 *
	 * @return    JAVADOC: The field value
	 */
	public VOTableSet getField() {
		return (field);
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
		if (data == null) {
			return (null);
		}
		return (data.getAllRows());
	}


	/**
	 *  Get all values for a given field name.
	 *
	 * @param  fieldName  Name of the field.
	 * @return            A List of values (String), or <code>null</code> if the
	 *      given field name doesn't exist in the VOTable.
	 */
	public List getAllValues(String fieldName) {
		if (data == null) {
			return (null);
		}
		int column = getIndexOfField(fieldName);
		if (column == -1) {
			return (null);
		}
		return (data.getAllValues(column));
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
		if (data == null) {
			return (null);
		}
		List list = null;
		try {
			list = data.getRow(row);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw e;
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
		if (data == null) {
			return (null);
		}
		int column = getIndexOfField(fieldName);
		if (column == -1) {
			return (null);
		}
		return (data.getRows(column, fieldValue, isCaseSensitive));
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
		if (data == null) {
			return (null);
		}
		int column = getIndexOfField(fieldName);
		if (column == -1) {
			return (null);
		}
		return (data.getRows(column, fieldValue, false));
	}


	/**
	 *  Returns the index of the field given its name.
	 *
	 * @param  fieldName  Name of the field.
	 * @return            Index of the field, -1 if this field doesn't exist.
	 */
	public int getIndexOfField(String fieldName) {
		int i = 0;
		boolean found = false;
		String tmp = fieldName.toLowerCase();
		while ((!found) && (i < field.size())) {
			found = ((Field) field.get(i)).getName().toLowerCase().equals(tmp);
			i++;
		}
		return (found ? (i - 1) : -1);
	}


	/**
	 *  Get all field names.
	 *
	 * @return    List of field names (as String), <code>null</code> if no fields
	 *      are specified in the VOTable.
	 */
	public List getFieldNames() {
		Vector v = new Vector();
		for (Iterator it = field.iterator(); it.hasNext(); ) {
			v.add(((Field) it.next()).getName());
		}
		return (v.subList(0, v.size()));
	}


	/**
	 *  Returns the number of results contained in the whole VOTable (it is
	 *  possible that the VOTable contains more than one &lt;TABLEDATA&gt; node).
	 *
	 * @return    Number of rows in &lt;TABLEDATA&gt; (i.e. results).
	 */
	public int getNumberOfRows() {
		if (data == null) {
			return (0);
		}
		return (data.getNumberOfRows());
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
		if (field.size() == 0) {
			return (-1);
		}
		int i = 0;
		Field f = null;
		while (i < field.size()) {
			f = (Field) field.get(i);
			if (f.getName().equals(fieldName)) {
				return(f.getDataTypeAsInt());
			}
			i++;
		}
		return (-1);
	}


	/**
	 *  JAVADOC: Adds a feature to the Link attribute of the Table object
	 *
	 * @param  l  JAVADOC: The feature to be added to the Link attribute
	 */
	public void addLink(Link l) {
		link.add(l);
	}


	/**
	 *  JAVADOC: Adds a feature to the Link attribute of the Table object
	 *
	 * @param  l  JAVADOC: The feature to be added to the Link attribute
	 */
	public void addLink(Link[] l) {
		link.addAll(Arrays.asList(l));
	}


	/**
	 *  JAVADOC: Adds a feature to the Field attribute of the Table object
	 *
	 * @param  f  JAVADOC: The feature to be added to the Field attribute
	 */
	public void addField(Field f) {
		field.add(f);
	}


	/**
	 *  JAVADOC: Adds a feature to the Fields attribute of the Table object
	 *
	 * @param  f  JAVADOC: The feature to be added to the Fields attribute
	 */
	public void addFields(Field[] f) {
		field.addAll(Arrays.asList(f));
	}


	/**
	 *  String representation of the element.
	 *
	 * @return    Representation of the element.
	 */
	public String toString() {
		return ("<TABLE " + attributes.toString() + ">" + ((description != null) ? description.toString() : "") + field.toString() +
				link.toString() + ((data != null) ? data.toString() : "") + "</TABLE>");
	}

}

