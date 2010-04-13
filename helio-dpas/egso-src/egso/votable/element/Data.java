package org.egso.votable.element;

import java.util.List;


/**
 *  JAVADOC: Description of the Class
 *
 * @author     Romain Linsolas (linsolas@gmail.com)
 * @version    0.9
 * @created    28 October 2003 0.9: 25/11/2003.
 */
public class Data extends VOTableElement {

	/**
	 *  JAVADOC: Description of the Field
	 */
	public final static int DATA_UNKNOWN = 0;
	/**
	 *  JAVADOC: Description of the Field
	 */
	public final static int DATA_TABLEDATA = 1;
	/**
	 *  JAVADOC: Description of the Field
	 */
	public final static int DATA_BINARY = 2;
	/**
	 *  JAVADOC: Description of the Field
	 */
	public final static int DATA_FITS = 3;
	/**
	 *  JAVADOC: Description of the Field
	 */
	private int type = DATA_UNKNOWN;
	/**
	 *  JAVADOC: Description of the Field
	 */
	private TableData tableData = null;
	/**
	 *  JAVADOC: Description of the Field
	 */
	private Binary binary = null;
	/**
	 *  JAVADOC: Description of the Field
	 */
	private Fits fits = null;


	/**
	 *  JAVADOC: Constructor for the Data object
	 */
	public Data() {
		super("DATA");
		type = DATA_UNKNOWN;
	}


	/**
	 *  JAVADOC: Constructor for the Data object
	 *
	 * @param  td  JAVADOC: Description of the Parameter
	 */
	public Data(TableData td) {
		super("DATA");
		tableData = td;
		type = DATA_TABLEDATA;
	}


	/**
	 *  JAVADOC: Constructor for the Data object
	 *
	 * @param  bin  JAVADOC: Description of the Parameter
	 */
	public Data(Binary bin) {
		super("DATA");
		binary = bin;
		type = DATA_BINARY;
	}


	/**
	 *  JAVADOC: Constructor for the Data object
	 *
	 * @param  f  JAVADOC: Description of the Parameter
	 */
	public Data(Fits f) {
		super("DATA");
		fits = f;
		type = DATA_FITS;
	}


	/**
	 *  JAVADOC: Sets the tableData attribute of the Data object
	 *
	 * @param  td  JAVADOC: The new tableData value
	 */
	public void setTableData(TableData td) {
		type = DATA_TABLEDATA;
		tableData = td;
		fits = null;
		binary = null;
	}


	/**
	 *  JAVADOC: Sets the binary attribute of the Data object
	 *
	 * @param  bin  JAVADOC: The new binary value
	 */
	public void setBinary(Binary bin) {
		type = DATA_BINARY;
		binary = bin;
		tableData = null;
		fits = null;
	}


	/**
	 *  JAVADOC: Sets the fits attribute of the Data object
	 *
	 * @param  f  JAVADOC: The new fits value
	 */
	public void setFits(Fits f) {
		type = DATA_FITS;
		fits = f;
		tableData = null;
		binary = null;
	}


	/**
	 *  JAVADOC: Gets the type attribute of the Data object
	 *
	 * @return    JAVADOC: The type value
	 */
	public int getType() {
		return (type);
	}


	/**
	 *  JAVADOC: Gets the tableData attribute of the Data object
	 *
	 * @return    JAVADOC: The tableData value
	 */
	public TableData getTableData() {
		return (tableData);
	}


	/**
	 *  JAVADOC: Gets the binary attribute of the Data object
	 *
	 * @return    JAVADOC: The binary value
	 */
	public Binary getBinary() {
		return (binary);
	}


	/**
	 *  JAVADOC: Gets the fits attribute of the Data object
	 *
	 * @return    JAVADOC: The fits value
	 */
	public Fits getFits() {
		return (fits);
	}


	/**
	 *  JAVADOC: Gets the data attribute of the Data object
	 *
	 * @return    JAVADOC: The data value
	 */
	public Object getData() {
		switch (type) {
						case DATA_TABLEDATA:
							return (tableData);
						case DATA_FITS:
							return (fits);
						case DATA_BINARY:
							return (binary);
		}
		return (null);
	}


	/**
	 *  Get all rows of the &lt;TABLEDATA&gt;, i.e. all data contained in the
	 *  VOTable.
	 *
	 * @return    A List of Lists of String. Each List is a row, each String in a
	 *      row is a value. <code>null</code> if no &lt;TABLEDATA&gt; node is
	 *      present in the VOTable.
	 */
	public List<List<String>> getAllRows() {
		if (tableData == null) {
			return (null);
		}
		return (tableData.getAllRows());
	}


	/**
	 *  Get all values for a given field index.
	 *
	 * @param  column  Index of the field.
	 * @return         A List of values (String), or <code>null</code> if the given
	 *      field name doesn't exist in the VOTable.
	 */
	public List getAllValues(int column) {
		if (tableData == null) {
			return (null);
		}
		return (tableData.getAllValues(column));
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
		if (tableData == null) {
			return (null);
		}
		List list = null;
		try {
			list = tableData.getRow(row);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw e;
		}
		return (list);
	}


	/**
	 *  Get all values where fieldName=fieldValue. The fieldValue value can be case
	 *  sensitive or not.
	 *
	 * @param  column           Index of the field.
	 * @param  fieldValue       Value of the field.
	 * @param  isCaseSensitive  Boolean that specifies if the fieldValue is case
	 *      sensitive or not.
	 * @return                  List of List of String. Each List is a row (a
	 *      result), each String is a value. <code>null</code> if there is no
	 *      result where fieldName=fieldValue.
	 */
	public List getRows(int column, String fieldValue, boolean isCaseSensitive) {
		if (tableData == null) {
			return (null);
		}
		return (tableData.getRows(column, fieldValue, isCaseSensitive));
	}


	/**
	 *  Get all values where fieldName=fieldValue (fieldValue is case insensitive).
	 *
	 * @param  column      Index of the field.
	 * @param  fieldValue  Value of the field.
	 * @return             List of List of String. Each List is a row (a result),
	 *      each String is a value. <code>null</code> if there is no result where
	 *      fieldName=fieldValue.
	 */
	public List getRows(int column, String fieldValue) {
		if (tableData == null) {
			return (null);
		}
		return (tableData.getRows(column, fieldValue, false));
	}


	/**
	 *  Returns the number of results contained in the whole VOTable (it is
	 *  possible that the VOTable contains more than one &lt;TABLEDATA&gt; node).
	 *
	 * @return    Number of rows in &lt;TABLEDATA&gt; (i.e. results).
	 */
	public int getNumberOfRows() {
		if (tableData == null) {
			return (0);
		}
		return (tableData.getNumberOfRows());
	}


	/**
	 *  A String representation of the element.
	 *
	 * @return    A String representation of the element.
	 */
	public String toString() {
		return ("<DATA>" + getData().toString() + "</DATA>");
	}

}

