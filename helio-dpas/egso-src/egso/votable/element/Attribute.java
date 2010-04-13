package org.egso.votable.element;


/**
 *  Definition of an Attribute for a VOTable Element.
 *
 * @author     Romain Linsolas (linsolas@gmail.com)
 * @version    1.0
 * @created    24 october 2003
 */
public class Attribute {
// TODO: Do we have to take care of the type of the attribute?
// TODO: Is the defaultValue useful?
/*
List of types:
	CDATA, ID, IDREF, IDREFS, NMTOKEN, NMTOKENS, ENTITY, ENTITIES, NOTATION.
*/

	/**
	 *  Name of the Attribute.
	 */
	private String name = null;
	/**
	 *  Value of the Attribute.
	 */
	private String value = null;
	/**
	 *  Default value for the attribute (used when no value is provided).
	 */
	private String defaultValue = null;
	/**
	 *  List of accepted values for the Attribute.
	 */
	private String[] acceptedValues = null;
	/**
	 *  Is the Attribute REQUIRED or IMPLIED?
	 */
	private int presence = IMPLIED;
	/**
	 *  Code for an IMPLIED Attribute.
	 */
	public final static int IMPLIED = 0;
	/**
	 *  Code for a REQUIRED Attribute.
	 */
	public final static int REQUIRED = 1;


	/**
	 *  Creation of an Attribute.
	 *
	 * @param  attributeName  Name of the attribute.
	 */
	public Attribute(String attributeName) {
		init(attributeName, null, null, null, IMPLIED);
	}


	/**
	 *  Creation of an Attribute.
	 *
	 * @param  attributeName  Name of the Attribute.
	 * @param  val            Value of the Attribute.
	 */
	public Attribute(String attributeName, String val) {
		init(attributeName, val, null, null, IMPLIED);
	}


	/**
	 *  Creation of an Attribute.
	 *
	 * @param  attributeName  Name of the attribute.
	 * @param  accepted       List of accepted values.
	 */
	public Attribute(String attributeName, String[] accepted) {
		init(attributeName, null, null, accepted, IMPLIED);
	}


	/**
	 *  Creation of an Attribute.
	 *
	 * @param  attributeName  Name of the Attribute.
	 * @param  val            Value of the Attribute.
	 * @param  accepted       List of accepted values.
	 */
	public Attribute(String attributeName, String val, String[] accepted) {
		init(attributeName, val, null, accepted, IMPLIED);
	}


	/**
	 *  Creation of an Attribute.
	 *
	 * @param  attributeName  Name of the Attribute.
	 * @param  val            Value of the Attribute.
	 * @param  defaultVal     Default value for the Attribute.
	 * @param  accepted       List of accepted values.
	 */
	public Attribute(String attributeName, String val, String defaultVal, String[] accepted) {
		init(attributeName, val, defaultVal, accepted, IMPLIED);
	}


	/**
	 *  Creation of an Attribute.
	 *
	 * @param  attributeName  Name of the Attribute.
	 * @param  val            Value of the Attribute.
	 * @param  defaultVal     Default value for the Attribute.
	 */
	public Attribute(String attributeName, String val, String defaultVal) {
		init(attributeName, val, defaultVal, null, IMPLIED);
	}


	/**
	 *  Creation of an Attribute.
	 *
	 * @param  attributeName  Name of the Attribute.
	 * @param  val            Value of the Attribute.
	 * @param  required       REQUIRED or IMPLIED Attribute.
	 */
	public Attribute(String attributeName, String val, int required) {
		init(attributeName, val, null, null, required);
	}


	/**
	 *  Creation of an Attribute.
	 *
	 * @param  attributeName  Name of the Attribute.
	 * @param  val            Value of the Attribute.
	 * @param  required       REQUIRED or IMPLIED Attribute.
	 * @param  accepted       List of accepted values.
	 */
	public Attribute(String attributeName, String val, String[] accepted, int required) {
		init(attributeName, val, null, accepted, required);
	}



	/**
	 *  Creation of an Attribute.
	 *
	 * @param  attributeName  Name of the Attribute.
	 * @param  val            Value of the Attribute.
	 * @param  defaultVal     Default value for the Attribute.
	 * @param  required       REQUIRED or IMPLIED Attribute.
	 */
	public Attribute(String attributeName, String val, String defaultVal, int required) {
		init(attributeName, val, defaultVal, null, required);
	}


	/**
	 *  Creation of an Attribute.
	 *
	 * @param  attributeName  Name of the Attribute.
	 * @param  val            Value of the Attribute.
	 * @param  defaultVal     Default value for the Attribute.
	 * @param  accepted       List of accepted values.
	 * @param  required       REQUIRED or IMPLIED Attribute.
	 */
	public Attribute(String attributeName, String val, String defaultVal, String[] accepted, int required) {
		init(attributeName, val, defaultVal, accepted, required);
	}


	/**
	 *  Set the default value for the Attribute.
	 *
	 * @param  defaultVal  Default value for the Attribute.
	 */
	public void setDefaultValue(String defaultVal) {
		defaultValue = defaultVal;
	}


	/**
	 *  Set the value of the Attribute.
	 *
	 * @param  val                        Value of the Attribute.
	 * @exception  InvalidValueException  If a list of accepted values has been
	 *      defined, the value must be one of them.
	 */
	public void setValue(String val)
			 throws InvalidValueException {
		if ((acceptedValues != null) && (val != null)) {
			int i = 0;
			boolean found = false;
			while (!found && (i < acceptedValues.length)) {
				found = acceptedValues[i].toLowerCase().equals(val.toLowerCase());
				i++;
			}
			if (found) {
				value = val;
			} else {
				String tmp = "[";
				for (int x = 0; x < (acceptedValues.length - 1); x++) {
					tmp += "'" + acceptedValues[x] + "' ; ";
				}
				tmp += "'" + acceptedValues + "'].";
				throw new InvalidValueException(val + " is not a valid value for the attribute " + name + ". The list of accepted value is: " + tmp);
			}
		} else {
			value = val;
		}
	}


	/**
	 *  JAVADOC: Gets the value attribute of the Attribute object
	 *
	 * @return    JAVADOC: The value value
	 */
	public String getValue() {
		return ((value != null) ? value : defaultValue);
	}


	/**
	 *  Get the name of the Attribute.
	 *
	 * @return    The name of the Attribute.
	 */
	public String getName() {
		return (name);
	}


	/**
	 *  Get the IMPLIED or REQUIRED quality of the Attribute.
	 *
	 * @return    IMPLIED or REQUIRED.
	 */
	public int getPresence() {
		return (presence);
	}


	/**
	 *  JAVADOC: Gets the required attribute of the Attribute object
	 *
	 * @return    JAVADOC: The required value
	 */
	public boolean isRequired() {
		return (presence == REQUIRED);
	}


	/**
	 *  Get the default value of the Attribute.
	 *
	 * @return    The default value.
	 */
	public String getDefaultValue() {
		return (defaultValue);
	}


	/**
	 *  Get the list of accepted values.
	 *
	 * @return    The list of accepted values.
	 */
	public String[] getAcceptedValues() {
		return (acceptedValues);
	}


	/**
	 *  Initialize all parameters of the Attribute.
	 *
	 * @param  attributeName              Name of the Attribute (can't be null).
	 * @param  val                        Value of the Attribute.
	 * @param  defaultVal                 Default value of the Attribute.
	 * @param  accepted                   List of accepted values.
	 * @param  required                   IMPLIED or REQUESTED.
	 * @exception  InvalidValueException  JAVADOC: Description of the Exception
	 */
	private void init(String attributeName, String val, String defaultVal, String[] accepted, int required) {
		name = attributeName;
		defaultValue = defaultVal;
		accepted = acceptedValues;
		presence = required;
		setValue(val);
	}


	/**
	 *  JAVADOC: Description of the Method
	 *
	 * @return    JAVADOC: Description of the Return Value
	 */
	public String toString() {
		if ((value == null) && (defaultValue == null)) {
			return ("");
		}
		return (" " + name + "=\"" + ((value != null) ? value : defaultValue) + "\"");
	}

}

