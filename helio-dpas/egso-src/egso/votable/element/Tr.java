package org.egso.votable.element;

import java.util.*;


/**
 *  JAVADOC: Description of the Class
 *
 * @author     Romain Linsolas (linsolas@gmail.com)
 * @version 0.9
 * @created    28 October 2003
 * 0.9 25/11/2003.
 */
public class Tr<E> extends VOTableElement<Td<E>> {

	/**
	 *  JAVADOC: Description of the Field
	 */
	private VOTableSet<Td<E>> tds = null;


	/**
	 *  JAVADOC: Constructor for the Tr object
	 */
	public Tr() {
		super("TR");
		tds = new VOTableSet<Td<E>>(VOTableSet.TD, VOTableSet.MANY_ELT);
	}


	/**
	 *  JAVADOC: Constructor for the Tr object
	 *
	 * @param  c  JAVADOC: Description of the Parameter
	 */
	public Tr(Collection<Td<E>> c) {
		super("TR");
		tds = new VOTableSet<Td<E>>(VOTableSet.TD, VOTableSet.MANY_ELT, c);
	}


	/**
	 *  JAVADOC: Constructor for the Tr object
	 *
	 * @param  td  JAVADOC: Description of the Parameter
	 */
	public Tr(Td<E>[] td) {
		super("TR");
		tds = new VOTableSet<Td<E>>(VOTableSet.TD, VOTableSet.MANY_ELT);
		tds.addAll(Arrays.asList(td));
	}


	/**
	 *  JAVADOC: Gets the tDSet attribute of the Tr object
	 *
	 * @return    JAVADOC: The tDSet value
	 */
	public VOTableSet<Td<E>> getTd() {
		return (tds);
	}


	/**
	 *  JAVADOC: Description of the Method
	 *
	 * @return    JAVADOC: Description of the Return Value
	 */
	public String toString() {
		return ("<TR>" + tds.toString() + "</TR>");
	}


	/**
	 *  JAVADOC: Description of the Method
	 *
	 * @param  td  JAVADOC: Description of the Parameter
	 */
	public void addTd(Td<E> td) {
		tds.add(td);
	}
	
	/**
	 * Get all values for a given field name.
	 * @param fieldName Name of the field.
	 * @return A List of values (String), or <code>null</code> if the given
	 * field name doesn't exist in the VOTable.
	 **/
	public List<String> getAllValues() {
		Vector<String> v = new Vector<String>();
		for ( Iterator<Td<E>> it = tds.iterator() ; it.hasNext() ; ) {
			v.add(it.next().getContent());
		}
		return(v.subList(0, v.size()));
	}


}

