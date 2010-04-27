package org.egso.votable.element;

import java.util.Vector;
import java.util.Iterator;
import java.util.Collection;


/**
 *  List of Attributes or VOTable Elements. One VOTableSet is dedicated to only
 *  one type of Element.
 *
 * @author     Romain Linsolas (linsolas@gmail.com).
 * @version    1.0
 * @created    24 October 2003
 */
@SuppressWarnings("serial")
public class VOTableSet<E> extends Vector<E> {
// TODO: Take into consideration the numberOfElement parameter (for add, and isValue methods).
	
	/**
	 *  Code for a Set of Attributes.
	 */
	public final static int ATTRIBUTE = 0;
	/**
	 *  Code for a Set of Elements 'INFO'.
	 */
	public final static int INFO = 1;
	/**
	 *  Code for a Set of Elements 'RESOURCE'.
	 */
	public final static int RESOURCE = 2;
	/**
	 *  Code for a Set of Elements 'COOSYS'.
	 */
	public final static int COOSYS = 3;
	/**
	 *  Code for a Set of Elements 'PARAM'.
	 */
	public final static int PARAM = 4;
	/**
	 *  Code for a Set of Elements 'LINK'.
	 */
	public final static int LINK = 5;
	/**
	 *  Code for a Set of Elements 'TABLE'.
	 */
	public final static int TABLE = 6;
	/**
	 *  Code for a Set of Elements 'FIELD'.
	 */
	public final static int FIELD = 7;
	/**
	 *  Code for a Set of Elements 'VALUES'.
	 */
	public final static int VALUES = 8;
	/**
	 *  Code for a Set of Elements 'OPTION'.
	 */
	public final static int OPTION = 9;
	/**
	 *  Code for a Set of Elements 'TR'.
	 */
	public final static int TR = 10;
	/**
	 *  Code for a Set of Elements 'TD'.
	 */
	public final static int TD = 11;
	/**
	 * Code for a Set of Elements 'DEFINITION'.
	 */
	public static final int DEFINITION = 12;
	/**
	 *  Code for "?" (0 or 1 element).
	 */
	public final static int ZERO_ONE_ELT = 1;
	/**
	 *  Code for "*" (0, 1+ elements).
	 */
	public final static int ZERO_MANY_ELT = 2;
	/**
	 *  Code for "+" (1+ elements).
	 */
	public final static int MANY_ELT = 3;

	/**
	 *  Type of Element stored in the Set.
	 */
	private int type = -1;
	/**
	 * Code to indicate if the set has (0 or 1), (0, 1+) or (1+) elements.
	 **/
	private int numberOfElement = 1;


	/**
	 *  Create a new Set.
	 *
	 * @param  setType  Type of Element accepted in the Set.
	 */
	public VOTableSet(int setType, int nbElt) {
		super();
		type = setType;
		numberOfElement = nbElt;
	}


	/**
	 *  Create a new Set.
	 *
	 * @param  setType  Type of Element accepted in the Set.
	 * @param  c        Collection of Elements that must be stored in the Set.
	 */
	public VOTableSet(int setType, int nbElt, Collection<? extends E> c) {
		super();
		type = setType;
		numberOfElement = nbElt;
		addAll(c);
	}


	/**
	 *  Create a new Set.
	 *
	 * @param  setType          Type of Element accepted in the Set.
	 * @param  initialCapacity  Initial capacity of the Set.
	 */
	public VOTableSet(int setType, int nbElt, int initialCapacity) {
		super(initialCapacity);
		type = setType;
		numberOfElement = nbElt;
	}


	/**
	 *  Create a new Set.
	 *
	 * @param  setType            Type of Element accepted in the Set.
	 * @param  initialCapacity    Initial capacity of the Set.
	 * @param  capacityIncrement  The amount by which the capacity is increased
	 *      when the Set overflows.
	 */
	public VOTableSet(int setType, int nbElt, int initialCapacity, int capacityIncrement) {
		super(initialCapacity, capacityIncrement);
		type = setType;
		numberOfElement = nbElt;
	}


	/**
	 *  JAVADOC: Gets the type attribute of the VOTableSet object
	 *
	 * @return    JAVADOC: The type value
	 */
	public int getType() {
		return (type);
	}

	public int getNumberOfElement() {
		return (numberOfElement);
	}
	
	/**
	 *  JAVADOC: Gets the type attribute of the VOTableSet object
	 *
	 * @param  setType  JAVADOC: Description of the Parameter
	 * @return          JAVADOC: The type value
	 */
	public boolean isType(int setType) {
		return (setType == type);
	}


	/**
	 *  JAVADOC: Description of the Method
	 *
	 * @param  index  JAVADOC: Description of the Parameter
	 * @param  obj    JAVADOC: Description of the Parameter
	 */
	public void add(int index, E obj) {
		if (accept(obj)) {
			super.add(index, obj);
		}
	}


	/**
	 *  JAVADOC: Description of the Method
	 *
	 * @param  obj  JAVADOC: Description of the Parameter
	 */
	public boolean add(E obj) {
		if (accept(obj)) {
			return (super.add(obj));
		}
		return (false);
	}


	/**
	 *  JAVADOC: Adds a feature to the All attribute of the VOTableSet object
	 *
	 * @param  c  JAVADOC: The feature to be added to the All attribute
	 * @return    JAVADOC: Description of the Return Value
	 */
	public boolean addAll(Collection<? extends E> c) {
		if (accept(c)) {
			return (super.addAll(c));
		}
		return (false);
	}


	/**
	 *  JAVADOC: Adds a feature to the All attribute of the VOTableSet object
	 *
	 * @param  index  JAVADOC: The feature to be added to the All attribute
	 * @param  c      JAVADOC: The feature to be added to the All attribute
	 * @return        JAVADOC: Description of the Return Value
	 */
	public boolean addAll(int index, Collection<? extends E> c) {
		if (accept(c)) {
			return (super.addAll(index, c));
		}
		return (false);
	}


	/**
	 *  JAVADOC: Adds a feature to the Element attribute of the VOTableSet object
	 *
	 * @param  obj  JAVADOC: The feature to be added to the Element attribute
	 */
	public void addElement(E obj) {
		if (accept(obj)) {
			super.addElement(obj);
		}
	}


	/**
	 *  JAVADOC: Description of the Method
	 *
	 * @param  obj  JAVADOC: Description of the Parameter
	 * @return      JAVADOC: Description of the Return Value
	 */
	private boolean accept(Object obj) {
		switch (type) {
						case ATTRIBUTE:
							return (obj instanceof org.egso.votable.element.Attribute);
						case INFO:
							return (obj instanceof org.egso.votable.element.Info);
						case RESOURCE:
							return (obj instanceof org.egso.votable.element.Resource);
						case COOSYS:
							return (obj instanceof org.egso.votable.element.Coosys);
						case PARAM:
							return (obj instanceof org.egso.votable.element.Param);
						case LINK:
							return (obj instanceof org.egso.votable.element.Link);
						case TABLE:
							return (obj instanceof org.egso.votable.element.Table);
						case FIELD:
							return (obj instanceof org.egso.votable.element.Field);
						case VALUES:
							return (obj instanceof org.egso.votable.element.Values);
						case OPTION:
							return (obj instanceof org.egso.votable.element.Option);
						case TR:
							return (obj instanceof org.egso.votable.element.Tr);
						case TD:
							return (obj instanceof org.egso.votable.element.Td);
						case DEFINITION:
							return (obj instanceof org.egso.votable.element.Definition);
		}
		return (false);
	}


	/**
	 *  JAVADOC: Description of the Method
	 *
	 * @param  c  JAVADOC: Description of the Parameter
	 * @return    JAVADOC: Description of the Return Value
	 */
	private boolean accept(Collection<? extends E> c) {
		Iterator<? extends E> it = c.iterator();
		boolean ok = true;
		while (ok && it.hasNext()) {
			ok = accept(it.next());
		}
		return (ok);
	}


	/**
	 *  JAVADOC: Description of the Method
	 *
	 * @return    JAVADOC: Description of the Return Value
	 */
	public String toString() {
		if (size() == 0) {
			return ("");
		}
		StringBuffer sb = new StringBuffer();
		String tmp = (type == ATTRIBUTE) ? " " : "";
		String tempo = null;
		for (Iterator<E> it = iterator(); it.hasNext(); ) {
			tempo = it.next().toString();
			if ((tempo != null) && (!tempo.equals(""))) {
				sb.append(tempo + tmp);
			}
		}
		return (sb.toString().trim());
	}
}

