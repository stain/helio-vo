package org.egso.provider.datamanagement.archives;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

/**
 * Object that maps a SQL Table.
 *
 * @author    Romain Linsolas (linsolas@gmail.com)
 * @version   1.0 - 12/10/2004
 */
public class Table extends SQLElement {

	/**
	 * Name of the table.
	 */
	private String name = null;
	/**
	 * Fields of the table.
	 */
	private Hashtable<String,Field> fields = null;
	/**
	 * Links with others tables.
	 */
	private Vector<Link> links = null;
	/**
	 * List of connected tables.
	 */
	private Hashtable<String,Table> connectedTables = null;
	/**
	 * Base that contains the table.
	 */
	private Base base = null;


	/**
	 * Creates a new Table.
	 *
	 * @param tableName  Name of the table.
	 * @param owner      Base that contains the table.
	 */
	Table(String tableName, Base owner) {
		super(SQLElement.TABLE);
		name = tableName;
		base = owner;
		fields = new Hashtable<String,Field>();
		links = new Vector<Link>();
		connectedTables = new Hashtable<String,Table>();
	}


	/**
	 * Returns the name of the table.
	 *
	 * @return   Name of the table.
	 */
	public String getName() {
		return (name);
	}


	/**
	 * Returns the Base that contains the table.
	 *
	 * @return   The Base that contains the table.
	 */
	public Base getBase() {
		return (base);
	}


	/**
	 * Get the Field given its name.
	 *
	 * @param fieldName  Name of the field.
	 * @return           The Field or <code>null</code> if it doesn't exist.
	 */
	public Field getField(String fieldName) {
		return fields.get(fieldName);
	}


	/**
	 * Returns the list of all table links.
	 *
	 * @return   The links of the table.
	 */
	public List<Link> getLinks() {
		return links;
	}


	/**
	 * Returns the list of tables that are linked to the current table.
	 *
	 * @return   List of connected tables.
	 */
	public List<Table> getConnectedTables() {
		Vector<Table> v = new Vector<Table>();
		v.addAll(connectedTables.values());
		return v;
	}


	/**
	 * Indicates if the table is linked to another table.
	 *
	 * @param table  Name of the other table.
	 * @return       <code>true</code> if both tables are connected, <code>false</code>
	 *      otherwise.
	 */
	public boolean isConnectedTo(String table)
	{
		for(Link l:links)
			if(l.getOtherField(name).getTable().getName().equals(table))
			  return true;

		return false;
	}


	/**
	 * Indicates if the table is linked to another table.
	 *
	 * @param t      JAVADOC: Description of the Parameter
	 * @return       <code>true</code> if both tables are connected, <code>false</code>
	 *      otherwise.
	 */
	public boolean isConnectedTo(Table t) {
		return (isConnectedTo(t.getName()));
	}


	/**
	 * Adds a new field.
	 *
	 * @param field  Field to be added.
	 * @return       The old field that has the same name, <code>null</code> if no
	 *      other field has the same name.
	 */
	public Field addField(Field field) {
		return ((Field) fields.put(field.getName(), field));
	}


	/**
	 * Adds a new link.
	 *
	 * @param l  The link to be added.
	 */
	public void addLink(Link l) {
		links.add(l);
		Table t = l.getOtherField(name).getTable();
		connectedTables.put(t.getName(), t);
	}


	/**
	 * Returns the link between the current table and another table.
	 *
	 * @param table  The other table.
	 * @return       The link that connects the two tables, <code>null</code> if
	 *      the tables are not linked.
	 */
	public Link linkWith(String table)
	{
		Field f = null;
		for(Link l:links)
		{
			f = l.getStart().getTable().getName().equals(name) ? l.getEnd() : l.getStart();
			if(f.getTable().getName().equals(table))
			  return l;
		}
		return null;
	}


	/**
	 * Returns a String representation of the Table.
	 *
	 * @return   String representation.
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer("TABLE " + name + "\n");
		for (Enumeration<Field> e = fields.elements(); e.hasMoreElements(); ) {
			sb.append("\t" + ((Field) e.nextElement()).toString() + "\n");
		}
		sb.append("LINKS:\n");
		for (Link l:links) {
			sb.append("\t" + l.toString() + "\n");
		}
		sb.append("DIRECT CONNECTION WITH:\n");
		for (Enumeration<String> e = connectedTables.keys(); e.hasMoreElements(); ) {
			sb.append("\t" + e.nextElement() + "\n");
		}
		return (sb.toString());
	}

}

