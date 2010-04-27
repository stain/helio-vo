package org.egso.provider.datamanagement.archives;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import org.w3c.dom.NodeList;

/**
 * Object that maps a SQL Base.
 *
 * @author    Romain Linsolas (linsolas@gmail.com)
 * @version   1.0 - 20/10/2004
 */
public class Base {

	/**
	 * Name of the SQL Base.
	 */
	private String name = null;
	/**
	 * Set of tables that compose the base.
	 */
	private Hashtable<String,Table> tables = null;
	/**
	 * Object that stores information about all links in the table.
	 */
	private LinkMatrix matrix = null;
	/**
	 * A table that contains all MapElements.
	 */
	private Hashtable<String,MapElement> mappingTable = null;
	private Vector<Field> newSelect = null;
	private Vector<Table> newFrom = null;
	private Vector<String> newWhere = null;
	


	/**
	 * Constructor.
	 *
	 * @param baseName  Name of the base.
	 */
	Base(String baseName) {
		name = baseName;
		tables = new Hashtable<String,Table>();
		newSelect = new Vector<Field>();
		newFrom = new Vector<Table>();
		newWhere = new Vector<String>();
	}


	/**
	 * Sets the LinkMatrix object for this base.
	 *
	 * @param linkMatrix  The LinkMatrix object.
	 */
	void setLinkMatrix(LinkMatrix linkMatrix) {
		matrix = linkMatrix;
	}


	/**
	 * Returns the mapped fields (MapElements) for an EGSO field.
	 *
	 * @param field  (EGSO) name of the field.
	 * @return       An array that contains one (in case of value) or two (in case
	 *      of interval) fields, or <code>null</code> if no mapped field exist for
	 *      this EGSO field name.
	 */
	public Field[] getMappedFields(String field) {
		MapElement elt = (MapElement) mappingTable.get(field);
		if (elt == null) {
			return (null);
		}
		return (elt.getFields());
	}

	public boolean isConcatField(String field) {
		MapElement elt = (MapElement) mappingTable.get(field);
		return ((elt != null) && (elt.isConcat()));
	}

	/**
	 * Returns the MapElement of the EGSO field.
	 *
	 * @param field  (EGSO) name of the field.
	 * @return       MapElement related to the EGSO field name, or <code>null</code>
	 *      if not found.
	 */
	public MapElement getMapElement(String field) {
		return ((MapElement) mappingTable.get(field));
	}


	/**
	 * Gets the LinkMatrix of the Base.
	 *
	 * @return   The linkMatrix object.
	 */
	public LinkMatrix getLinkMatrix() {
		return (matrix);
	}


	/**
	 * Returns the field, given its name (the name of this field must be
	 * table.field).
	 *
	 * @param fieldName  Field name ("table.field").
	 * @return           Field or <code>null</code>.
	 */
	public Field getField(String fieldName) {
		int x = fieldName.lastIndexOf('.');
		if (x != -1) {
			Table table = (Table) tables.get(fieldName.substring(0, x));
			if (table != null) {
				return (table.getField(fieldName.substring(x + 1)));
			}
		}
		return (null);
	}


	/**
	 * Returns all Field with a particular name. If the fieldname given as
	 * parameter is like table.field, the results will be only a List that contains
	 * only one Field (or <code>null</code>). If this name is only the fieldname
	 * (without the table name), the List may have more than one element.
	 *
	 * @param fieldName  The field name ("table.field" or "field").
	 * @return           A List that contains all Field with the given name.
	 */
	public List<Field> getFields(String fieldName) {
		Vector<Field> v = new Vector<Field>();
		if (fieldName.indexOf('.') != -1) {
			v.add(getField(fieldName));
			return v;
		}
		for (Table t:tables.values()) {
			Field obj = t.getField(fieldName);
			if (obj != null) {
				v.add(obj);
			}
		}
		return v;
	}


	/**
	 * Returns a table given its name.
	 *
	 * @param table  Name of the table.
	 * @return       The Table, or <code>null</code> if no table has the given
	 *      name.
	 */
	public Table getTable(String table) {
		return ((Table) tables.get(table));
	}


	/**
	 * Returns all tables.
	 *
	 * @return   A List that contains all tables of the Base.
	 */
	public List<Table> getTables() {
		Vector<Table> v = new Vector<Table>();
		v.addAll(tables.values());
		return v;
	}


	/**
	 * Creates the mapping table.
	 *
	 * @param mapping  NodeList, where each Node is a &lt;param&gt; node of the
	 *      &lt;structure&gt;&lt;mapping&gt; node of the XML description file.
	 */
	void createMappingTable(NodeList mapping) {
		mappingTable = new Hashtable<String,MapElement>();
		MapElement elt = null;
		for (int i = 0; i < mapping.getLength(); i++) {
			elt = new MapElement(this, mapping.item(i));
			if (!elt.getName().equals("IGNORE")) {
				mappingTable.put(elt.getName(), elt);
			}
		}
	}


	/**
	 * Adds a new table in the Base.
	 *
	 * @param table  The table to be added.
	 * @return       If a table with the same name was already present, it is
	 *      returned (<code>null</code> is returned otherwise).
	 */
	public Table addTable(Table table) {
		return ((Table) tables.put(table.getName(), table));
	}


	public Vector<Field> createNewSelect() {
		Vector<Field> v = new Vector<Field>();
		v.addAll(newSelect);
		return (v);
	}

	public Vector<Table> createNewFrom() {
		Vector<Table> v = new Vector<Table>();
		v.addAll(newFrom);
		return v;
	}

	public Vector<String> createNewWhere() {
		Vector<String> v = new Vector<String>();
		v.addAll(newWhere);
		return (v);
	}

	public void addToNewSelect(Field s) {
		newSelect.add(s);
	}

	public void addToNewFrom(Table f) {
		newFrom.add(f);
	}

	public void addToNewWhere(String w) {
		newWhere.add(w);
	}


	/**
	 * String representation of the Base.
	 *
	 * @return   String representation of the Base.
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer("BASE " + name + "\n");
		for (Enumeration<Table> e = tables.elements(); e.hasMoreElements(); ) {
			sb.append(((Table) e.nextElement()).toString() + "\n");
		}
		sb.append("LINK MATRIX:\n");
		sb.append((matrix == null) ? "No Link Matrix defined yet" : matrix.toString());
/*
		sb.append("\nMAPPING TABLE:\n");
		for (Enumeration e = mappingTable.keys(); e.hasMoreElements(); ) {
			String key = (String) e.nextElement();
			sb.append(key + ": " + ((MapElement) mappingTable.get(key)).toString());
		}
*/
		return (sb.toString());
	}

}

