package org.egso.votable.element;

import java.util.List;



/**
 *  The DataAccess interface lists the main methods available to offer access to
 *  data stored in the &lt;TABLEDATA&gt; node of the VOTable.
 *
 * @author     Romain Linsolas (linsolas@gmail.com).
 * @version    0.9
 * @created    24 novembre 2003
 */
/*
0.9: 25/11/2003: Adding getNumberOfRows().
*/
public interface DataAccess {
	/**
	 *  Get all field names.
	 *
	 * @return    List of field names (as String), <code>null</code> if no fields
	 *      are specified in the VOTable.
	 */
	public List getFieldNames();


	/**
	 *  Get all values for a given field name.
	 *
	 * @param  fieldName  Name of the field.
	 * @return            A List of values (String), or <code>null</code> if the
	 *      given field name doesn't exist in the VOTable.
	 */
	public List getAllValues(String fieldName);


	/**
	 *  Get all rows of the &lt;TABLEDATA&gt;, i.e. all data contained in the
	 *  VOTable.
	 *
	 * @return    A List of Lists of String. Each List is a row, each String in a
	 *      row is a value. <code>null</code> if no &lt;TABLEDATA&gt; node is
	 *      present in the VOTable.
	 */
	public List getAllRows();


	/**
	 *  Get the 'row'-th row (or results) on the VOTable.
	 *
	 * @param  row                              Row number.
	 * @return                                  List of values (String) for the
	 *      'row'-th row.
	 * @throws  ArrayIndexOutOfBoundsException  index is < 0 or greater than the
	 *      number of rows.
	 */
	public List getRow(int row) throws ArrayIndexOutOfBoundsException;


	/**
	 *  Get all values where fieldName=fieldValue (fieldValue is case insensitive).
	 *
	 * @param  fieldName   Name of the field.
	 * @param  fieldValue  Value of the field.
	 * @return             List of List of String. Each List is a row (a result),
	 *      each String is a value. <code>null</code> if there is no result where
	 *      fieldName=fieldValue.
	 */
	public List getRows(String fieldName, String fieldValue);


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
	public List getRows(String fieldName, String fieldValue, boolean isCaseSensitive);


	/**
	 *  Returns the number of results contained in the whole VOTable (it is
	 *  possible that the VOTable contains more than one &lt;TABLEDATA&gt; node).
	 *
	 * @return    Number of rows in &lt;TABLEDATA&gt; (i.e. results).
	 */
	public int getNumberOfRows();

	/**
	 * Gets the type of the <b>FIRST</b> field.
	 * @ param fieldName Name of the field.
	 * @return Type of the field (@see VOTableConstants), or -1 if the field does
	 * not exist.
	 **/
	public int getFieldType(String fieldName);
	
	
}

