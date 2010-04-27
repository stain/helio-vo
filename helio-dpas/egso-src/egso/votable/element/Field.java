package org.egso.votable.element;

import java.util.Arrays;

import org.egso.votable.VOTableConstants;


/**
 *  JAVADOC: Description of the Class
 *
 * @author     Romain Linsolas (linsolas@gmail.com)
 * @version    1.0
 * @created    28 October 2003
 */
public class Field extends VOTableElement {

	/**
	 *  JAVADOC: Description of the Field
	 */
	private Description description = null;
	/**
	 *  JAVADOC: Description of the Field
	 */
	private VOTableSet values = null;
	/**
	 *  JAVADOC: Description of the Field
	 */
	private VOTableSet link = null;


	/**
	 *  JAVADOC: Constructor for the Field object
	 */
	public Field() {
		super("FIELD");
		addAttribute(new Attribute("ID"));
		addAttribute(new Attribute("unit"));
		addAttribute(new Attribute("datatype", new String[]{"boolean", "bit", "unsignedByte", "short", "int", "long", "char", "unicodeChar", "float", "double", "floatComplex", "doubleComplex"}));
		addAttribute(new Attribute("precision"));
		addAttribute(new Attribute("width"));
		addAttribute(new Attribute("ref"));
		addAttribute(new Attribute("name"));
		addAttribute(new Attribute("ucd"));
		addAttribute(new Attribute("arraysize"));
		addAttribute(new Attribute("type", new String[]{"hidden", "no_query", "trigger"}));
		values = new VOTableSet(VOTableSet.VALUES, VOTableSet.ZERO_MANY_ELT);
		link = new VOTableSet(VOTableSet.LINK, VOTableSet.ZERO_MANY_ELT);
	}


	/**
	 *  JAVADOC: Sets the iD attribute of the Field object
	 *
	 * @param  id  JAVADOC: The new iD value
	 */
	public void setID(String id) {
		getAttribute("ID").setValue(id);
	}


	/**
	 *  JAVADOC: Sets the unit attribute of the Field object
	 *
	 * @param  unit  JAVADOC: The new unit value
	 */
	public void setUnit(String unit) {
		getAttribute("unit").setValue(unit);
	}


	/**
	 *  JAVADOC: Sets the dataType attribute of the Field object
	 *
	 * @param  dataType  JAVADOC: The new dataType value
	 */
	public void setDataType(String dataType) {
		getAttribute("datatype").setValue(dataType);
	}


	/**
	 *  JAVADOC: Sets the precision attribute of the Field object
	 *
	 * @param  pre  JAVADOC: The new precision value
	 */
	public void setPrecision(String pre) {
		getAttribute("precision").setValue(pre);
	}


	/**
	 *  JAVADOC: Sets the width attribute of the Field object
	 *
	 * @param  width  JAVADOC: The new width value
	 */
	public void setWidth(String width) {
		getAttribute("width").setValue(width);
	}


	/**
	 *  JAVADOC: Sets the ref attribute of the Field object
	 *
	 * @param  ref  JAVADOC: The new ref value
	 */
	public void setRef(String ref) {
		getAttribute("ref").setValue(ref);
	}


	/**
	 *  JAVADOC: Sets the name attribute of the Field object
	 *
	 * @param  name  JAVADOC: The new name value
	 */
	public void setName(String name) {
		getAttribute("name").setValue(name);
	}


	/**
	 *  JAVADOC: Sets the uCD attribute of the Field object
	 *
	 * @param  ucd  JAVADOC: The new uCD value
	 */
	public void setUCD(String ucd) {
		getAttribute("ucd").setValue(ucd);
	}


	/**
	 *  JAVADOC: Sets the arraySize attribute of the Field object
	 *
	 * @param  as  JAVADOC: The new arraySize value
	 */
	public void setArraySize(String as) {
		getAttribute("arraysize").setValue(as);
	}


	/**
	 *  JAVADOC: Sets the type attribute of the Field object
	 *
	 * @param  type  JAVADOC: The new type value
	 */
	public void setType(String type) {
		getAttribute("type").setValue(type);
	}


	/**
	 *  JAVADOC: Adds a feature to the Description attribute of the Field object
	 *
	 * @param  d  JAVADOC: The feature to be added to the Description attribute
	 */
	public void setDescription(Description d) {
		description = d;
	}


	/**
	 *  JAVADOC: Gets the iD attribute of the Field object
	 *
	 * @return    JAVADOC: The iD value
	 */
	public String getID() {
		return (getAttribute("ID").getValue());
	}


	/**
	 *  JAVADOC: Gets the unit attribute of the Field object
	 *
	 * @return    JAVADOC: The unit value
	 */
	public String getUnit() {
		return (getAttribute("unit").getValue());
	}


	/**
	 *  JAVADOC: Gets the dataType attribute of the Field object
	 *
	 * @return    JAVADOC: The dataType value
	 */
	public String getDataType() {
		return (getAttribute("datatype").getValue());
	}


	/**
	 *  JAVADOC: Gets the dataTypeAsInt attribute of the Field object
	 *
	 * @return    JAVADOC: The dataTypeAsInt value
	 */
	public int getDataTypeAsInt() {
		String tmp = getAttribute("datatype").getValue();
		int i = 0;
		while (i < VOTableConstants.DATA_TYPES.length) {
			if (VOTableConstants.DATA_TYPES[i].equals(tmp)) {
				return (i);
			}
			i++;
		}
		return (-1);
	}


	/**
	 *  JAVADOC: Gets the precision attribute of the Field object
	 *
	 * @return    JAVADOC: The precision value
	 */
	public String getPrecision() {
		return (getAttribute("precision").getValue());
	}


	/**
	 *  JAVADOC: Gets the width attribute of the Field object
	 *
	 * @return    JAVADOC: The width value
	 */
	public String getWidth() {
		return (getAttribute("width").getValue());
	}


	/**
	 *  JAVADOC: Gets the ref attribute of the Field object
	 *
	 * @return    JAVADOC: The ref value
	 */
	public String getRef() {
		return (getAttribute("ref").getValue());
	}


	/**
	 *  JAVADOC: Gets the name attribute of the Field object
	 *
	 * @return    JAVADOC: The name value
	 */
	public String getName() {
		return (getAttribute("name").getValue());
	}


	/**
	 *  JAVADOC: Gets the uCD attribute of the Field object
	 *
	 * @return    JAVADOC: The uCD value
	 */
	public String getUCD() {
		return (getAttribute("ucd").getValue());
	}


	/**
	 *  JAVADOC: Gets the arraySize attribute of the Field object
	 *
	 * @return    JAVADOC: The arraySize value
	 */
	public String getArraySize() {
		return (getAttribute("arraysize").getValue());
	}


	/**
	 *  JAVADOC: Gets the type attribute of the Field object
	 *
	 * @return    JAVADOC: The type value
	 */
	public String getType() {
		return (getAttribute("type").getValue());
	}


	/**
	 *  JAVADOC: Gets the description attribute of the Field object
	 *
	 * @return    JAVADOC: The description value
	 */
	public Description getDescription() {
		return (description);
	}


	/**
	 *  JAVADOC: Gets the values attribute of the Field object
	 *
	 * @return    JAVADOC: The values value
	 */
	public VOTableSet getValues() {
		return (values);
	}


	/**
	 *  JAVADOC: Gets the link attribute of the Field object
	 *
	 * @return    JAVADOC: The link value
	 */
	public VOTableSet getLink() {
		return (link);
	}


	/**
	 *  JAVADOC: Adds a feature to the Values attribute of the Field object
	 *
	 * @param  v  JAVADOC: The feature to be added to the Values attribute
	 */
	public void addValues(Values v) {
		values.add(v);
	}


	/**
	 *  JAVADOC: Adds a feature to the Values attribute of the Field object
	 *
	 * @param  v  JAVADOC: The feature to be added to the Values attribute
	 */
	public void addValues(Values[] v) {
	  values.addAll(Arrays.asList(v));
	}


	/**
	 *  JAVADOC: Adds a feature to the Link attribute of the Field object
	 *
	 * @param  l  JAVADOC: The feature to be added to the Link attribute
	 */
	public void addLink(Link l) {
		link.add(l);
	}


	/**
	 *  JAVADOC: Adds a feature to the Links attribute of the Field object
	 *
	 * @param  l  JAVADOC: The feature to be added to the Links attribute
	 */
	public void addLinks(Link[] l) {
	  link.addAll(Arrays.asList(l));
	}



	/**
	 *  A String representation of the element.
	 *
	 * @return    A String representation of the element.
	 */
	public String toString() {
		String tmp = ((description != null) ? description.toString() : "") + values.toString() + link.toString();
		return ("<FIELD " + attributes.toString() + (tmp.equals("") ? "/>" : (">" + tmp + "</FIELD>")));
	}

}

