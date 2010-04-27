package org.egso.provider.datamanagement.archives;


/**
 * Object that represents a Link between two Fields (and by extension between
 * two tables).
 *
 * @author    Romain Linsolas (linsolas@gmail.com)
 * @version   1.0 - 13/10/2004
 */
public class Link extends SQLElement {

	/**
	 * The first Field of the Link.
	 */
	private Field start = null;
	/**
	 * The second Field of the Link.
	 */
	private Field end = null;
	/**
	 * Type of the Link (<i>Not implemented yet</i> ).
	 */
	private String type = null;


	/**
	 * Creates a new Link.
	 *
	 * @param f1      First Field of the Link.
	 * @param f2      Second Field of the Link.
	 * @param nature  Nature of the Link.
	 */
	Link(Field f1, Field f2, String nature) {
		super(SQLElement.LINK);
		start = f1;
		end = f2;
		type = nature;
	}


	/**
	 * Returns the type of the Link.
	 *
	 * @return   The type of the Link.
	 */
	public String getType() {
		return (type);
	}


	/**
	 * Returns the first Field of the Link.
	 *
	 * @return   The first Field of the Link.
	 */
	public Field getStart() {
		return (start);
	}


	/**
	 * Returns the second Field of the Link.
	 *
	 * @return   The second Field of the Link.
	 */
	public Field getEnd() {
		return (end);
	}


	/**
	 * Returns the Field of the given table.
	 *
	 * @param table  Name of the table.
	 * @return       The Field from this table.
	 */
	public Field getField(String table) {
		return ((start.getTable().getName().equals(table)) ? start : end);
	}


	/**
	 * Returns the Field of the other table.
	 *
	 * @param table  Name of the table.
	 * @return       The Field from the other table.
	 */
	public Field getOtherField(String table) {
		return ((start.getTable().getName().equals(table)) ? end : start);
	}


	/**
	 * Indicates if the current Link and the given Link are connected, i.e. if they
	 * have one table in common.
	 *
	 * @param l  Another Link.
	 * @return   A boolean that is set to <code>true</code> only if the two Links
	 *      have a table in common.
	 */
	public boolean isConnected(Link l) {
		String s1 = start.getTable().getName();
		String e1 = end.getTable().getName();
		String s2 = l.getStart().getTable().getName();
		String e2 = l.getEnd().getTable().getName();
		return (s1.equals(s2) || s1.equals(e2) || e1.equals(s2) || e1.equals(e2));
	}


	/**
	 * Returns the common Table of the two Links, if exists.
	 *
	 * @param l  Other Link.
	 * @return   The common Table of the two Links, or <code>null</code> if the two
	 *      Links have no Table in common.
	 */
	public Table getCommonTable(Link l) {
		String s1 = start.getTable().getName();
		String e1 = end.getTable().getName();
		String s2 = l.getStart().getTable().getName();
		String e2 = l.getEnd().getTable().getName();
		if (s1.equals(s2) || s1.equals(e2)) {
			return (start.getTable());
		}
		if (e1.equals(s2) || e1.equals(e2)) {
			return (end.getTable());
		}
		return (null);
	}


	/**
	 * Returns the end Table of a route that starts from the given Table and uses
	 * the two Links (the current Link and the one given as parameter). If the two
	 * Links are not connected or if the Table given as parameter is not related to
	 * one of the two Links, the responses will be <code>null</code>.
	 *
	 * @param l      The other Link.
	 * @param table  The starting Table.
	 * @return       The Table that finishes the route, or <code>null</code>.
	 */
	public Table getEndTable(Link l, String table) {
		String s1 = start.getTable().getName();
		String e1 = end.getTable().getName();
		String s2 = l.getStart().getTable().getName();
		String e2 = l.getEnd().getTable().getName();
		if (s1.equals(s2) || s1.equals(e2)) {
			return (e1.equals(table) ? l.getEnd().getTable() : end.getTable());
		}
		if (e1.equals(s2) || e1.equals(e2)) {
			return (s1.equals(table) ? l.getStart().getTable() : start.getTable());
		}
		return (null);
	}


	/**
	 * Returns the start and end Tables for the route created by the two Links.
	 *
	 * @param l  Other Link that creates the route.
	 * @return   An array of two Tables that are the starting and ending Table of
	 *      the route created by the two Links, or <code>null</code> if the route
	 *      doesn't exist (i.e. because the two Links are not connected).
	 */
	public Table[] getRoute(Link l) {
		String s1 = start.getTable().getName();
		String e1 = end.getTable().getName();
		String s2 = l.getStart().getTable().getName();
		String e2 = l.getEnd().getTable().getName();
		if (s1.equals(s2)) {
			return (new Table[]{end.getTable(), l.getEnd().getTable()});
		}
		if (s1.equals(e2)) {
			return (new Table[]{end.getTable(), l.getStart().getTable()});
		}
		if (e1.equals(s2)) {
			return (new Table[]{start.getTable(), l.getEnd().getTable()});
		}
		if (e1.equals(e2)) {
			return (new Table[]{start.getTable(), l.getStart().getTable()});
		}
		return (null);
	}


	/**
	 * Prints the Link like "table1.field1 = table2.field2".
	 *
	 * @return   The Link as a String.
	 */
	public String toStringWithoutType() {
		return (start.getCompleteName() + "=" + end.getCompleteName());
	}


	/**
	 * String representation of the Link.
	 *
	 * @return   String representation of the Link.
	 */
	public String toString() {
		return (start.getCompleteName() + "=" + end.getCompleteName() + " [type=" + type + "]");
	}

}

