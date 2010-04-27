package org.egso.provider.datamanagement.mapper;

import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Stack;
import java.util.Vector;

import org.egso.provider.admin.ProviderMonitor;
import org.egso.provider.utils.Conversion;
import org.egso.provider.utils.ProviderUtils;
import org.egso.provider.utils.XMLTools;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author     Romain Linsolas (linsolas@gmail.com)
 * @version    0.9.1 - 23/01/2004 [29/11/2003]
 */
public class HTTPParser extends DefaultHandler {

	private Stack<String> stack = null;
	private Stack<String> paramName = null;
	private final int DATA = 0;
	private final int PARAM = 1;
	private final int VALUE = 2;
	private final int INTERVAL = 3;
	private final int START = 4;
	private final int END = 5;
	/*private final String AND = "AND";
	private final String OR = "OR";*/
	private final String[] tagNames = {"data", "param", "value", "interval", "start", "end"};
	private final String[][] acceptedList = {{"param"}, {"param", "value", "interval"}, {}, {"start", "end"}, {}, {}};
	private String[] acceptedNodes = null;
	private boolean textExpected = false;
	private boolean gotTheValue = false;
	private int intervalStartEnd = 0;
	private String currentNode = null;
	private Vector<StringBuffer[]> dates = null;
	private Vector<String> finalMasks = null;
	private String mask = null;
	private StringBuffer[] dateInterval = null;
//	private int numberOfFields = 0;
	private Vector<String>[] valuesForFields = null;
	private Node mappingNode = null;
	private Vector<String> exceptions = null;
	private XMLTools xmlTools = null;
	private StringBuffer temporateValue = null;


	@SuppressWarnings("unchecked")
  public HTTPParser(String genericMask, int number, Node mapping) {
		paramName = new Stack<String>();
		stack = new Stack<String>();
		dates = new Vector<StringBuffer[]>();
		finalMasks = new Vector<String>();
		mask = genericMask;
		valuesForFields = new Vector[number];
		mappingNode = mapping;
		exceptions = new Vector<String>();
		xmlTools = XMLTools.getInstance();
	}

	/**
	 * Gets all the masks created by the parsing of the XML query.
	 *
	 * @return All FTP masks created during the parsing.
	 **/
	public Vector<String> getAllMasks() {
		return (finalMasks);
	}
	
	public Vector<String> getExceptions() {
		return (exceptions);
	}

	/**
	 * Gets the code that corresponds to the tag name (name of the XML node).
	 *
	 * @param localName  Name of the node.
	 * @return           The code of the tag.
	 */
	private int getTag(String qName) {
		int i = 0;
		boolean found = false;
		while (!found & (i < tagNames.length)) {
			found = qName.equals(tagNames[i]);
			i++;
		}
		if (!found) {
			return(-1);
		}
		return (i - 1);
	}


	private void addValue(String val) {
		String tmp = paramName.peek();
		try {
			// Get the node that contains mapping information for the current parameter.
			Node n = xmlTools.selectSingleNode(mappingNode, "//mapping/param[@name='" + tmp + "']");
			if (n != null) {
				// If n == null, then there is no mapping for this parameter...
				if (n.getAttributes().getNamedItem("multiple-index").getNodeValue().equals("no")) {
					// Case where the param is used only once in the mask, ie there is only one index.
					Node n2 = xmlTools.selectSingleNode(n, "//param/value[@name='" + val.toUpperCase() + "']");
					if (n2 != null) {
						// n2 contains the information to map the value into an archive-specific value...
						// Get the mapped value, and add it into the corresponding Vector.
						int index = Integer.parseInt(n.getAttributes().getNamedItem("index").getNodeValue());
						Vector<String> v = valuesForFields[index];
						if (v == null) {
							v = new Vector<String>();
						}
						v.add(n2.getAttributes().getNamedItem("value").getNodeValue());
						valuesForFields[index] = v;
					}
//				} else {
					// Case where the param is used many times in the mask, ie there is more than one index.
//					System.out.println("[DEBUG - FTPParser]: addValue(String), n==null");
				}
			} else {
				System.out.println("[DEBUG - FTPParser]: NO MAPPING FOUND !");
			}
		} catch (Throwable t) {
			t.printStackTrace();
			ProviderMonitor.getInstance().reportException(t);
			exceptions.add(ProviderUtils.reportException("FTPParser", t));
		}
	}


	/**
	 * Processes character data.
	 *
	 * @param ch                The characters from the XML document.
	 * @param start             The start position in the array.
	 * @param length            The number of characters to read from the array.
	 * @exception SAXException  Any SAX exception, possibly wrapping another
	 *      exception.
	 */
	public void characters(char[] ch, int start, int length)
			 throws SAXException {
//		System.out.println("characters: " + new String(ch, start, length));
		String tmp = new String(ch, start, length);
		if (!tmp.trim().equals("")) {
			if (textExpected) {
				// Text expected. The parent node is <value>, <start> or <end>...
				gotTheValue = true;
				if (currentNode.equals("value")) {
					// Value. Must map the param.
					temporateValue.append(tmp);
					//addValue(tmp);
				} else {
					if (currentNode.equals("start")) {
						if (paramName.peek().equals("date")) {
							if (dateInterval[0] == null) {
								dateInterval[0] = new StringBuffer(tmp);
							} else {
								dateInterval[0].append(tmp);
							}
//						} else {
//							System.out.println("[DEBUG - FTPParser]: Interval/start for " + paramName.peek());
						}
					} else {
						if (currentNode.equals("end")) {
							if (paramName.peek().equals("date")) {
								if (dateInterval[1] == null) {
									dateInterval[1] = new StringBuffer(tmp);
								} else {
									dateInterval[1].append(tmp);
								}
//							} else {
//								System.out.println("[DEBUG - FTPParser]: Interval/end for " + paramName.peek());
							}
						} else {
							System.out.println("ERROR: Don't know what to do with these characters: '" + tmp + "'.");
						}
					}
				}
				//textExpected = false;
			} else {
				System.out.println("WARNING: Text (" + tmp + ") not expected here [parent node = " + currentNode + "]");
			}
		}
	}



	/**
	 * Receives notification of the end of an element.
	 *
	 * @param namespaceURI      The namespace URI.
	 * @param localName         The local name (without prefix), or the empty
	 *      String if Namespace processing is not being performed.
	 * @param qName             The qualified XML 1.0 name (with prefix), or the
	 *      empty String if qualified names are not available.
	 * @exception SAXException  Any SAX exception, possibly wrapping another
	 *      exception.
	 */
	public void endElement(String namespaceURI, String localName, String qName)
			 throws SAXException {
//		System.out.println("endElement: </" + namespaceURI + "|" + localName + "|" + qName + ">");
		switch(getTag(qName)) {
			case PARAM:
					break;
			case VALUE:
			case START:
			case END:
					if (!gotTheValue) {
						System.out.println("ERROR: No value (text) for a " + qName + " node!");
					}
					gotTheValue = false;
					if (temporateValue != null) {
						addValue(temporateValue.toString());
					}
					temporateValue = null;
					textExpected = false;
				break;
			case INTERVAL:
					if (intervalStartEnd != 0) {
						System.out.println("ERROR: INTERVAL node has the good number of children: Node " + ((intervalStartEnd < 0) ? "<END>" : "<START>") + " is not present.");
					}
					intervalStartEnd = 0;
					if (paramName.peek().equals("date")) {
//						System.out.println("ADDING DATE: [" + dateInterval[0] + " | " + dateInterval[1] + "].");
						dates.add(dateInterval);
					}
					textExpected = false;
				break;
			case DATA:
//					System.out.println("END OF <DATA>");
				break;
		}
		currentNode = stack.pop();
		if (currentNode != null) {
			acceptedNodes = acceptedList[getTag(currentNode)];
		}
	}


	/**
	 * Receives notification of the beginning of an element.
	 *
	 * @param namespaceURI      The namespace URI.
	 * @param localName         The local name (without prefix), or the empty
	 *      String if Namespace processing is not being performed.
	 * @param qName             The qualified name (with prefix), or the empty
	 *      String if qualified names are not available.
	 * @param atts              The attributes attached to the element. If there
	 *      are no attributes, it shall be an empty Attributes object.
	 * @exception SAXException  Any SAX exception, possibly wrapping another
	 *      exception.
	 */
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts)
			 throws SAXException {
//		System.out.println("startElement: <" + namespaceURI + "|" + localName + "|" + qName + ">");
		boolean found = false;
		int i = 0;
		// Test if the current node is accepted here...
		while (!found && (i < acceptedNodes.length)) {
			found = qName.equals(acceptedNodes[i]);
			i++;
		}
		if (!found) {
			System.out.println("ERROR: The " + qName + " element is not expected here. The parent element is " + currentNode + ".");
			return;
		}
		if (textExpected) {
			System.out.println("ERROR: A text node was expected here!");
			return;
		}
		// Replace the currentNode value by the current node, and push the old one in the Stack.
		stack.push(currentNode);
		currentNode = qName;
		int tag = getTag(qName);
		acceptedNodes = acceptedList[tag];
		switch(tag) {
			case PARAM:
					paramName.push(atts.getValue("name"));
				break;
			case VALUE:
					textExpected = true;
					temporateValue = new StringBuffer();
				break;
			case INTERVAL:
					textExpected = false;
					gotTheValue = false;
					// When we will reach the </INTERVAL>, we must have intervalStartEnd==0,
					// which means that we have one node <START> and one node <END>.
					intervalStartEnd = 0;
					dateInterval = new StringBuffer[2];
				break;
			case START:
					textExpected = true;
					gotTheValue = false;
					intervalStartEnd--;
				break;
			case END:
					textExpected = true;
					gotTheValue = false;
					intervalStartEnd++;
				break;
			case DATA:
					break;
			default:
					System.out.println("ERROR: Node " + qName + " not supported.");
				break;
		}
	}


	private Vector<Object[]> addObjects(Vector<Object[]> objects, Vector<String> values, int index) {
		Vector<Object[]> v = new Vector<Object[]>();

		// For all values...
		for (String val:values) {
			// And for all existing objects list for masks...
			for (Object[] tmp_original:objects) {
				Object[] tmp = (Object[]) tmp_original.clone();
				tmp[index] = val;
				// Add the list of objects with the new value.
				v.add(tmp);
			}
		}
		return (v);
	}

	private Vector<String> addDates(Vector<Object[]> objects) {
		// Create all dates masks.
		Vector<String> datesMasks = null;
		for (StringBuffer[] tempo:dates) {
			datesMasks = getDateIntervals(tempo[0].toString().substring(0, 10), "YYYY-MM-DD", tempo[1].toString().substring(0, 10), "YYYY-MM-DD", "YYYYMMDD");
		}
		// Get mapping information.
		Node dateNode = null;
		try {
			dateNode = xmlTools.selectSingleNode(mappingNode, "//mapping/param[@name='DATE']");
		} catch (Exception e) {
			ProviderMonitor.getInstance().reportException(e);
			e.printStackTrace();
		}
		if (dateNode == null) {
			System.out.println("[DEBUG - HTTPParser]: No DATE node found on XML description");
		}
		NodeList nl = dateNode.getChildNodes() ;

		// Get all format information for dates.
		String[] formats = new String[valuesForFields.length];
		for (int i = 0 ; i < nl.getLength() ; i++) {
			Node n = nl.item(i);
			if ((n.getNodeType() == Node.ELEMENT_NODE) && (n.getNodeName().equals("value"))) {
				int index = Integer.parseInt(n.getAttributes().getNamedItem("index").getNodeValue());
				formats[index] = n.getAttributes().getNamedItem("format").getNodeValue();
			}
		}
		// Create masks.
		Vector<Object[]> temporaryMasks = new Vector<Object[]>();
		for (String msk:datesMasks)
		{
			System.out.println(">> " + msk);
			for (int i = 0 ; i < objects.size() ; i++) {
				Object[] tmp = objects.get(i);
				for (int j = 0 ; j < formats.length ; j++) {
					if (formats[j] != null) {
						System.out.print("\t" + msk + " (" + formats[j] + ") -> ");
						tmp[j] = Conversion.convertDate("YYYYMMDD", formats[j], msk);
						System.out.println(tmp[j]);
					}
				}
				temporaryMasks.add((Object[]) tmp.clone());
			}
		}
		// Create masks with MessageFormat.
		for (Object[] o:temporaryMasks) {
			finalMasks.add(MessageFormat.format(mask,o));
		}
		return(finalMasks);
	}

	private void createFinalMasks() {
		Vector<String> v = null;
		Vector<Object[]> objects = new Vector<Object[]>();
		objects.add(new String[valuesForFields.length]);
		finalMasks = new Vector<String>();
		Node n = null;
		for (int i = 0 ; i < valuesForFields.length ; i++) {
			try {
				n = xmlTools.selectSingleNode(mappingNode, "//mapping/param[@index='" + i + "']");
			} catch (Exception e) {
				ProviderMonitor.getInstance().reportException(e);
				e.printStackTrace();
			}
			v = valuesForFields[i];
			if (v == null) {
				if (n == null) {
					// Case of DATE -> Managed by addDates (after the loop FOR).
				} else {
					// Case of parameter without values. Adding the value with "?".
					Vector<String> tmpVect = new Vector<String>();
					tmpVect.add(n.getAttributes().getNamedItem("none").getNodeValue());
					objects = addObjects(objects, tmpVect, i);
				}
			} else {
				// Case where we have value(s) for this parameter.
				objects = addObjects(objects, v, i);
			}
		}
		finalMasks = addDates(objects);

		System.out.println("-- DEBUG FINAL --");
		for (String s:finalMasks) {
			System.out.println("> " + s);
		}
		System.out.println("-----------------");

	}


	/**
	 * Creates all masks needed for the date for a given date interval.
	 *
	 * @param begin Date when the interval starts.
	 * @param beginFormat Format of the <I>begin</I> date.
	 * @param end Date when the interval end.
	 * @param endFormat Format of the <I>end</I> date.
	 * @param format Format for output dates.
	 * @return Vector that contains all created masks.
	 */
	private Vector<String> getDateIntervals(String begin, String beginFormat, String end, String endFormat, String format) {
		// Determination des jours, mois et annees de l'intervalle considere.
		int annee1 = Integer.parseInt(Conversion.convertDate(beginFormat, "YYYY", begin));
		int mois1 = Integer.parseInt(Conversion.convertDate(beginFormat, "MM", begin));
		int jour1 = Integer.parseInt(Conversion.convertDate(beginFormat, "DD", begin));
		int annee2 = Integer.parseInt(Conversion.convertDate(endFormat, "YYYY", end));
		int mois2 = Integer.parseInt(Conversion.convertDate(endFormat, "MM", end));
		int jour2 = Integer.parseInt(Conversion.convertDate(endFormat, "DD", end));
		// Premiere etape : creation d'un noeud - dates - contenant l'arbre des
		// dates. Cet arbre sera ensuite parcouru pour creer les chaines de dates.
		Hashtable<String,Hashtable<String,Vector<String>>> datesTable = new Hashtable<String,Hashtable<String,Vector<String>>>();
		Hashtable<String,Vector<String>> year = new Hashtable<String,Vector<String>>();
		Vector<String> month = new Vector<String>();
		int dM;
		int fM;
		int dJ;
		int fJ = 0;
		// Traitement de l'annee.
		for (int a = annee1; a <= annee2; a++) {
			year = new Hashtable<String,Vector<String>>();
			dM = 1;
			fM = 12;
			if (annee1 == annee2) {
				dM = mois1;
				fM = mois2;
			} else {
				if (a == annee1) {
					dM = mois1;
				} else {
					if (a == annee2) {
						fM = mois2;
					}
				}
			}
			// Traitement du mois.
			for (int m = dM; m <= fM; m++) {
				month = new Vector<String>();
				dJ = 1;
				fJ = nbDays(m, a);
				if (annee1 == annee2) {
					if (mois1 == mois2) {
						dJ = jour1;
						fJ = jour2;
					} else {
						if (m == mois1) {
							dJ = jour1;
						} else {
							if (m == mois2) {
								fJ = jour2;
							}
						}
					}
				} else {
					if (a == annee1) {
						if (m == mois1) {
							dJ = jour1;
						}
					} else {
						if (a == annee2) {
							if (m == mois2) {
								fJ = jour2;
							}
						}
					}
				}
				// Traitement du jour.
				for (int j = dJ; j <= fJ; j++) {
					month.add(((j < 10) ? "0" : "") + j);
				}
				year.put(((m < 10) ? "0" : "") + m, month);
			}
			datesTable.put("" + a, year);
		}
		Vector<String> allMasks = new Vector<String>();
		for (Enumeration<String> el = datesTable.keys() ; el.hasMoreElements() ; ) {
			String key = el.nextElement();
			Hashtable<String,Vector<String>> tableTmp = datesTable.get(key);
			
			for (Enumeration<String> el2 = tableTmp.keys() ; el2.hasMoreElements() ; ) {
				String key2 = el2.nextElement();
				Vector<String> jours= tableTmp.get(key2);
				
				for (String jour:jours) {
					allMasks.add(key + key2 + jour);
				}
			}
		}
		datesTable = null;
		return(allMasks);
	}


	/**
	 * Returns the number of days for a given month in a given year.
	 *
	 * @param m Month.
	 * @param y Year.
	 * @return Number of days in the month <I>m</I> in the year <I>y</I>.
	 */
	private int nbDays (int m, int y) {
		// 30 days months: April, June, September and November.
		if ((m == 4) || (m == 6) || (m == 9) || (m == 11)) {
			return (30) ;
		}
		// Managing February (including month with 29 days).
		if (m == 2) {
			float f = (new Integer (y)).floatValue () ;
			String annee = "" + y ;
			boolean bissextile =  ((f / 4) == (new Float (f / 4)).intValue ()) ;
			if ((bissextile) && (annee.endsWith ("00"))) {
				bissextile = annee.endsWith ("000") ;
			}
			return (bissextile ? 29 : 28) ;
		}
		// All other months, with 31 days...
		return (31) ;
	}


	/**
	 * Receives notification of the beginning of the document.
	 *
	 * @exception SAXException  Any SAX exception, possibly wrapping another
	 *      exception.
	 */
	public void startDocument()
			 throws SAXException {
//		System.out.println("Start of the Document...");
		acceptedNodes = new String[] {"data"};
	}


	/**
	 * Receives notification of the end of a document.
	 *
	 * @exception SAXException  Any SAX exception, possibly wrapping another
	 *      exception.
	 */
	public void endDocument()
			 throws SAXException {
		createFinalMasks();
//		System.out.println("...End of the document");
	}


	/**
	 * Receives an object for locating the origin of SAX document events.<BR>
	 * <I>Not used here.</I>
	 *
	 * @param locator  An object that can return the location of any SAX document
	 *      event.
	 */
	public void setDocumentLocator(Locator locator) {
//		System.out.println("setDocumentLocator");
	}


	/**
	 * Begins the scope of a prefix-URI Namespace mapping. <BR>
	 * <I>Not used here.</I>
	 *
	 * @param prefix            The Namespace prefix being declared.
	 * @param uri               The Namespace URI the prefix is mapped to.
	 * @exception SAXException  Any SAX exception, possibly wrapping another
	 *      exception.
	 */
	public void startPrefixMapping(String prefix, String uri)
			 throws SAXException {
//			System.out.println("startPrefixMapping: " + prefix + " | " + uri);
	}


	/**
	 * Receives notification of ignorable whitespace in element content. <BR>
	 * <I>Not used here.</I>
	 *
	 * @param ch                The characters from the XML document.
	 * @param start             The start position in the array.
	 * @param length            The number of characters to read from the array.
	 * @exception SAXException  Any SAX exception, possibly wrapping another
	 *      exception.
	 */
	public void ignorableWhitespace(char[] ch, int start, int length)
			 throws SAXException {
//			System.out.println("ignorableWhitespace: " + new String(ch, start, length));
	}


	/**
	 * Receives notification of a processing instruction. <BR>
	 * <I>Not used here.</I>
	 *
	 * @param target            The processing instruction target.
	 * @param data              The processing instruction data, or null if none
	 *      was supplied. The data does not include any whitespace separating it
	 *      from the target.
	 * @exception SAXException  Any SAX exception, possibly wrapping another
	 *      exception.
	 */
	public void processingInstruction(String target, String data)
			 throws SAXException {
//			System.out.println("processingInstruction: " + target + " | " + data);
	}


	/**
	 * Receives notification of a skipped entity. <BR>
	 * <I>Not used here.</I>
	 *
	 * @param name              The name of the skipped entity. If it is a
	 *      parameter entity, the name will begin with '%', and if it is the
	 *      external DTD subset, it will be the string "[dtd]".
	 * @exception SAXException  Any SAX exception, possibly wrapping another
	 *      exception.
	 */
	public void skippedEntity(String name)
			 throws SAXException {
//			System.out.println("skippedEntity: " + name);
	}


	/**
	 * Ends the scope of a prefix-URI mapping. <BR>
	 * <I>Not used here.</I>
	 *
	 * @param prefix            The prefix that was being mapping.
	 * @exception SAXException  Any SAX exception, possibly wrapping another
	 *      exception.
	 */
	public void endPrefixMapping(String prefix)
			 throws SAXException {
//			System.out.println("endPrefixMapping: " + prefix);
	}



}

