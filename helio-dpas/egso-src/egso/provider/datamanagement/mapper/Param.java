package org.egso.provider.datamanagement.mapper;


import java.util.Vector;

import org.egso.provider.datamanagement.archives.MapElement;


/**
 * The class Param regroups all information of a node PARAM (or eventually a
 * node DATA) in an EGSO-XML query.
 *
 * @author    Romain Linsolas (linsolas@gmail.com)
 * @version   1.0 - 20/10/2004
 */
public class Param {

	/**
	 * JAVADOC: Description of the Field
	 */
	private String relation = null;
	/**
	 * JAVADOC: Description of the Field
	 */
	private MapElement mapElement = null;
	/**
	 * JAVADOC: Description of the Field
	 */
	private String temporaryIntervalValue = null;
	/**
	 * JAVADOC: Description of the Field
	 */
	private Vector<String> values = null;
	/**
	 * JAVADOC: Description of the Field
	 */
	private boolean dataNode = false;


	/**
	 * JAVADOC: Constructor for the Param object
	 *
	 * @param map  JAVADOC: Description of the Parameter
	 * @param rel  JAVADOC: Description of the Parameter
	 */
	Param(MapElement map, String rel) {
		dataNode = false;
		mapElement = map;
		relation = rel;
		values = new Vector<String>();
	}


	/**
	 * JAVADOC: Constructor for the Param object
	 *
	 * @param rel  JAVADOC: Description of the Parameter
	 */
	Param(String rel) {
		dataNode = true;
		relation = rel;
		values = new Vector<String>();
	}


	/**
	 * JAVADOC: Gets the name attribute of the Param object
	 *
	 * @return   JAVADOC: The name value
	 */
	public String getName() {
		return (mapElement.getName());
	}


	/**
	 * JAVADOC: Gets the dataNode attribute of the Param object
	 *
	 * @return   JAVADOC: The dataNode value
	 */
	public boolean isDataNode() {
		return (dataNode);
	}


	/**
	 * JAVADOC: Gets the mapElement attribute of the Param object
	 *
	 * @return   JAVADOC: The mapElement value
	 */
	public MapElement getMapElement() {
		return (mapElement);
	}


	/**
	 * JAVADOC: Gets the relation attribute of the Param object
	 *
	 * @return   JAVADOC: The relation value
	 */
	public String getRelation() {
		return (relation);
	}


	/**
	 * JAVADOC: Adds a feature to the Value attribute of the Param object
	 *
	 * @param value  JAVADOC: The feature to be added to the Value attribute
	 */
	public void addValue(String value) {
		values.add(mapElement.map(value, "="));
	}


	/**
	 * JAVADOC: Adds a feature to the StartIntervalValue attribute of the Param
	 * object
	 *
	 * @param value  JAVADOC: The feature to be added to the StartIntervalValue
	 *      attribute
	 */
	public void addStartIntervalValue(String value) {
		if (temporaryIntervalValue == null) {
			temporaryIntervalValue = value;
		} else {
			values.add("(" + mapElement.map(value, temporaryIntervalValue, false) + ")");
			temporaryIntervalValue = null;
		}
	}


	/**
	 * JAVADOC: Adds a feature to the EndIntervalValue attribute of the Param
	 * object
	 *
	 * @param value  JAVADOC: The feature to be added to the EndIntervalValue
	 *      attribute
	 */
	public void addEndIntervalValue(String value) {
		if (temporaryIntervalValue == null) {
			temporaryIntervalValue = value;
		} else {
			values.add("(" + mapElement.map(temporaryIntervalValue, value, false) + ")");
			temporaryIntervalValue = null;
		}
	}


	/**
	 * JAVADOC: Adds a feature to the ChildParam attribute of the Param object
	 *
	 * @param p  JAVADOC: The feature to be added to the ChildParam attribute
	 */
	public void addChildParam(Param p) {
		values.add(p.finish());
	}


	/**
	 * JAVADOC: Description of the Method
	 *
	 * @return   JAVADOC: Description of the Return Value
	 */
	public String finish() {
		if (values.size() == 0) {
			return ("");
		}
		StringBuffer sb = new StringBuffer("(");
		for (int i = 0; i < (values.size() - 1); i++) {
			sb.append((String) values.get(i) + " " + relation + " ");
		}
		sb.append((String) values.lastElement() + ")");
		return (sb.toString());
	}
}

