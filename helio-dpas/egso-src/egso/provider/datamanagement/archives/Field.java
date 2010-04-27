package org.egso.provider.datamanagement.archives;

import java.text.MessageFormat;

/**
 * Object that maps a SQL field.
 *
 * @author    Romain Linsolas (linsolas@gmail.com)
 * @version   1.0 - 20/10/2004
 */
public class Field extends SQLElement {

	/**
	 * Field name.
	 */
	private String name = null;
	/**
	 * Type of the field.
	 */
	private String type = null;
	/**
	 * Format of the field. This is used to convert the value to a specific format.
	 */
	private String format = null;
	/**
	 * Table that contains the Field.
	 */
	private Table table = null;


	/**
	 * Creates a new Field.
	 *
	 * @param fieldName    Name of the Field.
	 * @param owner        Table that contains the Field.
	 * @param fieldType    Type of the Field (can be <code>null</code>).
	 * @param fieldFormat  Format of the Field (can be <code>null</code>).
	 */
	Field(String fieldName, Table owner, String fieldType, String fieldFormat) {
		super(SQLElement.FIELD);
		name = fieldName;
		type = fieldType;
		format = fieldFormat;
		table = owner;
	}


	/**
	 * Returns the name of the Field.
	 *
	 * @return   Name of the Field.
	 */
	public String getName() {
		return (name);
	}


	/**
	 * Returns the type of the Field.
	 *
	 * @return   The type of the Field.
	 */
	public String getType() {
		return (type);
	}


	/**
	 * Returns the Table that contains the Field.
	 *
	 * @return   The Table that contains the Field.
	 */
	public Table getTable() {
		return (table);
	}


	/**
	 * Returns the complete name (i.e. "table.field") of the Field.
	 *
	 * @return   The complete name of the Field.
	 */
	public String getCompleteName() {
		return (table.getName() + "." + name);
	}


	/**
	 * Returns the format of the Field.
	 *
	 * @return   The format of the Field.
	 */
	public String getFormat() {
		return (format);
	}


	/**
	 * String representation of the Field.
	 *
	 * @return   String representation of the Field.
	 */
	public String toString() {
		return (table.getName() + "." + name + " [type=" + type + "] [format=" + format + "]");
	}


	/**
	 * Maps a value affected to the Field (using the '=' operator). The value will
	 * be formatted considering the format attribute of the Field.
	 *
	 * @param value  Value to be affected to the Field.
	 * @return       Equation as a String (i.e. "field = value").
	 */
	public String map(String value) {
		return (map(value, "="));
	}


	/**
	 * Maps a value affected to the Field, using the given operator. The value will
	 * be formatted considering the format attribute of the Field.
	 *
	 * @param value     Value to be affected to the Field.
	 * @param operator  Operator for the equation.
	 * @return          Equation as a String (i.e. "field <i>operator</i> value").
	 */
	public String map(String value, String operator) {
		// If a format have been specified.
		if (format != null) {
			if (format.indexOf("{1}") != -1) {
				return (MessageFormat.format(format, new Object[] {value, operator}));
			}
			return (table.getName() + "." + name + operator + MessageFormat.format(format, new Object[]{value}));
		}
		return (table.getName() + "." + name + operator + value);
	}

}

