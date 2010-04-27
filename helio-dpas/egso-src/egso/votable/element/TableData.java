package org.egso.votable.element;

import java.util.*;

/**
 *  JAVADOC: Description of the Class
 *
 * @author     Romain Linsolas (linsolas@gmail.com)
 * @version    0.9
 * @created    28 October 2003
 * 0.9: 24/11/2003.
 */
public class TableData<E> extends VOTableElement<E> {

	/**
	 *  JAVADOC: Description of the Field
	 */
	private VOTableSet<Tr<E>> trs = null;


	/**
	 *  JAVADOC: Constructor for the Tr object
	 */
	public TableData() {
		super("TABLEDATA");
		trs = new VOTableSet<Tr<E>>(VOTableSet.TR, VOTableSet.ZERO_MANY_ELT);
	}


	/**
	 *  JAVADOC: Constructor for the Tr object
	 *
	 * @param  tr  JAVADOC: Description of the Parameter
	 */
	public TableData(Tr<E>[] tr) {
		super("TABLEDATA");
		trs = new VOTableSet<Tr<E>>(VOTableSet.TR, VOTableSet.ZERO_MANY_ELT);
		trs.addAll(Arrays.asList(tr));
	}



	/**
	 *  JAVADOC: Gets the tDSet attribute of the Tr object
	 *
	 * @return    JAVADOC: The tDSet value
	 */
	public VOTableSet<Tr<E>> getTr() {
		return (trs);
	}


	/**
	 *  JAVADOC: Description of the Method
	 *
	 * @param  td  JAVADOC: Description of the Parameter
	 */
	public void addTr(Tr<E> tr) {
		trs.add(tr);
	}


	/**
	 * Get all rows of the &lt;TABLEDATA&gt;, i.e. all data contained in the VOTable.
	 * @return A List of Lists of String. Each List is a row, each String in a
	 * row is a value. <code>null</code> if no &lt;TABLEDATA&gt; node is present
	 * in the VOTable.
	 **/
	public List<List<String>> getAllRows() {
		Vector<List<String>> rows = new Vector<List<String>>();
		for (Tr<E> t:trs) {
			rows.add(t.getAllValues());
		}
		return(rows.subList(0, rows.size()));
	}
	
	/**
	 * Get all values for a given field index.
	 * @param column Index of the field.
	 * @return A List of values (String), or <code>null</code> if the given
	 * field name doesn't exist in the VOTable.
	 **/
	public List<String> getAllValues(int column) {
		Vector<String> rows = new Vector<String>();
		for (Tr<E> r:trs) {
			rows.add((r.getTd().get(column)).getContent());
		}
		return(rows.subList(0, rows.size()));
	}
	
	/**
	 * Get the 'row'-th row (or results) on the VOTable.
	 * @param row Row number.
	 * @return List of values (String) for the 'row'-th row.
	 * @throws ArrayIndexOutOfBoundsException index is < 0 or greater than the number of rows.
	 **/
	public List<String> getRow(int row) throws ArrayIndexOutOfBoundsException {
		List<String> list = null;
		try {
			list = trs.get(row).getAllValues();
		} catch (ArrayIndexOutOfBoundsException e) {
			throw e;
		}
		return(list);
	}
	
	/**
	 * Get all values where fieldName=fieldValue. The fieldValue value can be
	 * case sensitive or not.
	 * @param column Index of the field.
	 * @param fieldValue Value of the field.
	 * @param isCaseSensitive Boolean that specifies if the fieldValue is case
	 * sensitive or not.
	 * @return List of List of String. Each List is a row (a result), each String
	 * is a value. <code>null</code> if there is no result where fieldName=fieldValue.
	 **/
	public List<List<String>> getRows(int column, String fieldValue, boolean isCaseSensitive) {
		Vector<List<String>> v = new Vector<List<String>>();
		String tmp = null;
		for (Tr<E> tr:trs) {
			tmp = ((Td<E>) tr.getTd().get(column)).getContent();
			if (isCaseSensitive) {
				if (tmp.equals(fieldValue)) {
					v.add(tr.getAllValues());
				}
			} else {
				if (tmp.toLowerCase().equals(fieldValue.toLowerCase())) {
					v.add(tr.getAllValues());
				}
			}
		}
		return(v.subList(0, v.size()));
	}
	
	/**
	 * Get all values where fieldName=fieldValue (fieldValue is case insensitive).
	 * @param column Index of the field.
	 * @param fieldValue Value of the field.
	 * @return List of List of String. Each List is a row (a result), each String
	 * is a value. <code>null</code> if there is no result where fieldName=fieldValue.
	 **/
	public List<List<String>> getRows(int column, String fieldValue) {
		return(getRows(column, fieldValue, false));
	}
	
	/**
	 * Returns the number of results contained in the whole VOTable (it is possible
	 * that the VOTable contains more than one &lt;TABLEDATA&gt; node).
	 * @return Number of rows in &lt;TABLEDATA&gt; (i.e. results).
	 **/
	public int getNumberOfRows() {
		return(trs.size());
	}
	
	/**
	 *  String representation of the element.
	 *
	 * @return    Representation of the element.
	 */
	public String toString() {
		return ("<TABLEDATA" + ((trs.size() != 0) ? (">" + trs.toString() + "</TABLEDATA>") : "/>"));
	}


}

