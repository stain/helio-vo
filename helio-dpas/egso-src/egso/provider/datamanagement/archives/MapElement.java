package org.egso.provider.datamanagement.archives;

import java.util.Vector;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * Object that stores information for mapping.
 *
 * @author    Romain Linsolas (linsolas@gmail.com)
 * @version   1.0 - 20/10/2004
 */
public class MapElement {

	/**
	 * JAVADOC: Description of the Field
	 */
	private String egsoName = null;
	/**
	 * JAVADOC: Description of the Field
	 */
	private Field startField = null;
	/**
	 * JAVADOC: Description of the Field
	 */
	private Field endField = null;
	/**
	 * JAVADOC: Description of the Field
	 */
	private String[][] values = null;
	/**
	 * JAVADOC: Description of the Field
	 */
	private Object[] concatenations = null;
	private String concat = null;
	private int type = VALUE;
	private final static int VALUE = 0;
	private final static int INTERVAL = 1;
	private final static int CONCATENATION = 2;


	/**
	 * JAVADOC: Constructor for the MapElement object
	 *
	 * @param base  JAVADOC: Description of the Parameter
	 * @param xml   JAVADOC: Description of the Parameter
	 */
	MapElement(Base base, Node xml) {
		NamedNodeMap atts = xml.getAttributes();
		egsoName = atts.getNamedItem("name").getNodeValue();
		type = atts.getNamedItem("value").getNodeValue().toLowerCase().equals("yes") ? VALUE : ((atts.getNamedItem("concat") != null) ? CONCATENATION : INTERVAL);
		String sqlStartName = null;
		String sqlEndName = null;
		if (type == VALUE) {
			// VALUE
			sqlStartName = atts.getNamedItem("map").getNodeValue();
			if (sqlStartName.equals("IGNORE")) {
				egsoName = "IGNORE";
				return;
			}
		} else {
			if (type == CONCATENATION) {
				// CONCATENATION
				int x = Integer.parseInt(atts.getNamedItem("concat").getNodeValue());
				concatenations = new Object[x];
				String tmp = null;
				StringBuffer sb = new StringBuffer();
				StringBuffer sb2 = new StringBuffer();
				for (int y = 1 ; y <= x ; y++) {
					tmp = atts.getNamedItem("map" + y).getNodeValue();
					if (tmp.startsWith("#")) {
						concatenations[y - 1] = tmp.substring(1);
					} else {
						concatenations[y - 1] = base.getField(tmp);
					}
					if (y != x) {
						if (tmp.startsWith("#")) {
							sb.append("CONCAT('" + tmp.substring(1) + "', ");
						} else {
							sb.append("CONCAT(" + base.getField(tmp).getCompleteName() + ", ");
						}
						sb2.append(")");
					}
				}
				// Handle the last object.
				x--;
				sb.append((concatenations[x] instanceof String) ? (String) concatenations[x] : ((Field) concatenations[x]).getCompleteName());
				concat = sb.toString() + sb2.toString();
			} else {
				// INTERVAL
				sqlStartName = atts.getNamedItem("begin").getNodeValue();
				sqlEndName = atts.getNamedItem("end").getNodeValue();
				endField = base.getField(sqlEndName);
			}
		}
		if (type != CONCATENATION) {
			startField = base.getField(sqlStartName);
		}
		NodeList val = xml.getChildNodes();
		int nb = 0;
		// The xml.getChildNodes().getLength() is different from the number of
		// <param> child-nodes because of eventual empty text nodes. So, we must
		// start by counting the real number of <param> child-nodes.
		for (int i = 0; i < val.getLength(); i++) {
			if ((val.item(i).getNodeType() == Node.ELEMENT_NODE) && val.item(i).getNodeName().equals("value")) {
				nb++;
			}
		}
		if (nb != 0) {
			values = new String[2][nb];
			nb = 0;
			Node n = null;
			for (int i = 0; i < val.getLength(); i++) {
				n = val.item(i);
				if ((n.getNodeType() == Node.ELEMENT_NODE) && n.getNodeName().equals("value")) {
					atts = n.getAttributes();
					values[0][nb] = atts.getNamedItem("value").getNodeValue();
					values[1][nb] = atts.getNamedItem("map").getNodeValue();
					nb++;
				}
			}
		}
	}


	/**
	 * JAVADOC: Gets the fields attribute of the MapElement object
	 *
	 * @return   JAVADOC: The fields value
	 */
	public Field[] getFields() {
		if (type == CONCATENATION) {
			Vector<Field> v = new Vector<Field>();
			for(Object o:concatenations)
				if (o instanceof Field)
					v.add((Field)o);

			int i = 0;
			Field[] x = new Field[v.size()];
			for (Field f:v)
				x[i++]=f;

			return x;
		}
		if (type == INTERVAL) {
			return (new Field[]{startField, endField});
		}
		return (new Field[]{startField});
	}

	public String getConcatenationString() {
		return (concat);
	}

	/**
	 * JAVADOC: Gets the name attribute of the MapElement object
	 *
	 * @return   JAVADOC: The name value
	 */
	public String getName() {
		return (egsoName);
	}


	/**
	 * JAVADOC: Gets the value attribute of the MapElement object
	 *
	 * @return   JAVADOC: The value value
	 */
	public boolean isValue() {
		return (type == VALUE);
	}


	/**
	 * JAVADOC: Gets the interval attribute of the MapElement object
	 *
	 * @return   JAVADOC: The interval value
	 */
	public boolean isInterval() {
		return (type == INTERVAL);
	}

	public boolean isConcat() {
		return (type == CONCATENATION);
	}

	/**
	 * JAVADOC: Gets the mappedValue attribute of the MapElement object
	 *
	 * @param value  JAVADOC: Description of the Parameter
	 * @return       JAVADOC: The mappedValue value
	 */
	private String getMappedValue(String value) {
		if (values != null) {
			boolean found = false;
			int i = 0;
			while (!found && (i < values[0].length)) {
				found = values[0][i].equals(value);
				i++;
			}
			if (found) {
				i--;
				return (values[1][i]);
			}
		}
		return (value);
	}


	/**
	 * JAVADOC: Description of the Method
	 *
	 * @param value  JAVADOC: Description of the Parameter
	 * @return       JAVADOC: Description of the Return Value
	 */
	public String map(String value) {
		return (map(value, "="));
	}


	/**
	 * JAVADOC: Description of the Method
	 *
	 * @param value  JAVADOC: Description of the Parameter
	 * @param op     JAVADOC: Description of the Parameter
	 * @return       JAVADOC: Description of the Return Value
	 */
	public String map(String value, String op) {
		if (type == INTERVAL) {
			return (null);
		}
		String mappedValue = getMappedValue(value);
		if (mappedValue == null) {
			return (null);
		}
		return (startField.map(mappedValue, op));
	}


	/**
	 * Map an interval with given beginning and ending values. The comparison
	 * may be strict (i.e. &lt; and &gt;) or not (&lt;= and &gt;=).<BR/>
	 *
	 * @param begin   Begin value of the interval.
	 * @param end     End value of the interval.
	 * @param strict  If <code>true</code>, then the comparison will be strict
	 * (i.e. uses &lt; and &gt;).
	 * @return        The String representation of the equation of the interval.
	 */
	public String map(String begin, String end, boolean strict) {
		if (type != INTERVAL) {
			return (null);
		}
		String mappedBegin = getMappedValue(begin);
		String mappedEnd = getMappedValue(end);
		if ((mappedBegin == null) || (mappedEnd == null)) {
			return (null);
		}
		String op1 = ">" + (strict ? "" : "=");
		String op2 = "<" + (strict ? "" : "=");
		if (egsoName.equals("date")) {
			// start_field <= end AND end_field >= begin.
			StringBuffer sb = new StringBuffer();
			sb.append("((" + startField.map(mappedEnd, op2) + " AND " + endField.map(mappedBegin, op1) + ") OR");
			sb.append("(" + startField.map(mappedBegin, op1) + " AND " + startField.map(mappedEnd, op2) + "))");
			return (sb.toString());
		}
		return ("(" + startField.map(mappedBegin, op1) + " AND " + endField.map(mappedEnd, op2) + ")");
	}

}

