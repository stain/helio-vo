package org.egso.votable;

import java.util.Hashtable;
import java.util.Stack;
import org.egso.votable.element.Binary;
import org.egso.votable.element.Coosys;
import org.egso.votable.element.Data;
import org.egso.votable.element.Definitions;
import org.egso.votable.element.Description;
import org.egso.votable.element.Field;
import org.egso.votable.element.Fits;
import org.egso.votable.element.Info;
import org.egso.votable.element.Link;
import org.egso.votable.element.Max;
import org.egso.votable.element.Min;
import org.egso.votable.element.Option;
import org.egso.votable.element.Param;
import org.egso.votable.element.Resource;
import org.egso.votable.element.Stream;
import org.egso.votable.element.Table;
import org.egso.votable.element.TableData;
import org.egso.votable.element.Td;
import org.egso.votable.element.Tr;
import org.egso.votable.element.VOTableElement;
import org.egso.votable.element.VOTableRoot;
import org.egso.votable.element.Values;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


/**
 *  JAVADOC: Description of the Class
 *
 * @author     Romain Linsolas (linsolas@gmail.com)
 * @version    1.0
 * @created    29 October 2003
 */
public class VOTableParser<E> extends DefaultHandler {

	/**
	 *  JAVADOC: Description of the Field
	 */
	private VOTableRoot votable = null;
	/**
	 *  JAVADOC: Description of the Field
	 */
	private final static int VOTABLE = 0;
	/**
	 *  JAVADOC: Description of the Field
	 */
	private final static int DESCRIPTION = 1;
	/**
	 *  JAVADOC: Description of the Field
	 */
	private final static int DEFINITIONS = 2;
	/**
	 *  JAVADOC: Description of the Field
	 */
	private final static int INFO = 3;
	/**
	 *  JAVADOC: Description of the Field
	 */
	private final static int RESOURCE = 4;
	/**
	 *  JAVADOC: Description of the Field
	 */
	private final static int COOSYS = 5;
	/**
	 *  JAVADOC: Description of the Field
	 */
	private final static int PARAM = 6;
	/**
	 *  JAVADOC: Description of the Field
	 */
	private final static int LINK = 7;
	/**
	 *  JAVADOC: Description of the Field
	 */
	private final static int TABLE = 8;
	/**
	 *  JAVADOC: Description of the Field
	 */
	private final static int VALUES = 9;
	/**
	 *  JAVADOC: Description of the Field
	 */
	private final static int FIELD = 10;
	/**
	 *  JAVADOC: Description of the Field
	 */
	private final static int DATA = 11;
	/**
	 *  JAVADOC: Description of the Field
	 */
	private final static int MIN = 12;
	/**
	 *  JAVADOC: Description of the Field
	 */
	private final static int MAX = 13;
	/**
	 *  JAVADOC: Description of the Field
	 */
	private final static int OPTION = 14;
	/**
	 *  JAVADOC: Description of the Field
	 */
	private final static int TABLEDATA = 15;
	/**
	 *  JAVADOC: Description of the Field
	 */
	private final static int BINARY = 16;
	/**
	 *  JAVADOC: Description of the Field
	 */
	private final static int FITS = 17;
	/**
	 *  JAVADOC: Description of the Field
	 */
	private final static int TR = 18;
	/**
	 *  JAVADOC: Description of the Field
	 */
	private final static int TD = 19;
	/**
	 *  JAVADOC: Description of the Field
	 */
	private final static int STREAM = 20;
	/**
	 *  JAVADOC: Description of the Field
	 */
	private final static String[] tagNames = {"VOTABLE", "DESCRIPTION", "DEFINITIONS", "INFO", "RESOURCE", "COOSYS", "PARAM",
			"LINK", "TABLE", "VALUES", "FIELD", "DATA", "MIN", "MAX", "OPTION", "TABLEDATA", "BINARY", "FITS", "TR", "TD", "STREAM"};
	/**
	 *  JAVADOC: Description of the Field
	 */
	private final static String[][] acceptedList = {
			{"DESCRIPTION", "DEFINITIONS", "INFO", "RESOURCE"}, {}, {"COOSYS", "PARAM"}, {},
			{"DESCRIPTION", "INFO", "COOSYS", "PARAM", "LINK", "TABLE", "RESOURCE"}, {},
			{"DESCRIPTION", "VALUE", "LINK"}, {}, {"DESCRIPTION", "FIELD", "LINK", "DATA"},
			{"MIN", "MAX", "OPTION"}, {"DESCRIPTION", "VALUES", "LINK"}, {"TABLEDATA", "BINARY", "FITS"},
			{}, {}, {"OPTION"}, {"TR"}, {"STREAM"}, {"STREAM"}, {"TD"}, {}, {}};

	/**
	 *  JAVADOC: Description of the Field
	 */
	private String[] accepted = null;
	/**
	 *  JAVADOC: Description of the Field
	 */
	private Stack<VOTableElement<E>> stack = null;
	/**
	 *  JAVADOC: Description of the Field
	 */
	private VOTableElement<E> current = null;
	/**
	 *  JAVADOC: Description of the Field
	 */
	private boolean text = false;

	
	/**
	 *  JAVADOC: Constructor for the VOTableParser object
	 */
	public VOTableParser() {
		stack = new Stack<VOTableElement<E>>();
	}

	public VOTableRoot getVOTable() {
		return(votable);
	}

	/**
	 *  JAVADOC: Sets the documentLocator attribute of the VOTableParser object
	 *
	 * @param  locator  JAVADOC: The new documentLocator value
	 */
	public void setDocumentLocator(Locator locator) {
//		System.out.println("setDocumentLocator");
	}


	/**
	 *  JAVADOC: Gets the attributes attribute of the VOTableParser object
	 *
	 * @param  atts  JAVADOC: Description of the Parameter
	 * @return       JAVADOC: The attributes value
	 */
	private Hashtable<String,String> getAttributes(Attributes atts) {
		int size = atts.getLength();
		Hashtable<String,String> hash = new Hashtable<String,String>(size);
		String tmp = null;
		for (int i = 0; i < size; i++) {
			tmp = atts.getLocalName(i).toLowerCase();
			if (tmp.equals("id")) {
				tmp = "ID";
			}
			hash.put(tmp, atts.getValue(i));
		}
		return (hash);
	}


	/**
	 *  JAVADOC: Gets the tag attribute of the VOTableParser object
	 *
	 * @param  localName  JAVADOC: Description of the Parameter
	 * @return            JAVADOC: The tag value
	 */
	private int getTag(String localName) {
		int i = 0;
		boolean found = false;
		while (!found & (i < tagNames.length)) {
			found = localName.equals(tagNames[i]);
			i++;
		}
		if (!found) {
			return(-1);
		}
		return (i - 1);
	}


	/**
	 *  JAVADOC: Description of the Method
	 *
	 * @param  ch                JAVADOC: Description of the Parameter
	 * @param  start             JAVADOC: Description of the Parameter
	 * @param  length            JAVADOC: Description of the Parameter
	 * @exception  SAXException  JAVADOC: Description of the Exception
	 */
	public void characters(char[] ch, int start, int length)
			 throws SAXException {
		String tmp = (new String(ch, start, length)).trim();
		if (text) {
			switch (getTag(current.getTagName())) {
							case DESCRIPTION:
								((Description) current).setContent(tmp);
								break;
							case INFO:
								((Info) current).setContent(tmp);
								break;
							case MIN:
								((Min) current).setContent(tmp);
								break;
							case MAX:
								((Max) current).setContent(tmp);
								break;
							case LINK:
								((Link) current).setContent(tmp);
								break;
							case TD:
								((Td<E>) current).setContent(tmp);
								break;
							case STREAM:
								((Stream) current).setContent(tmp);
								break;
							case COOSYS:
								((Coosys) current).setContent(tmp);
								break;
							default:
			}
			text = false;
		} else {
			if (!tmp.equals("")) {
				if (getTag(current.getTagName()) == TD) {
					((Td<E>) current).setContent(((Td<E>) current).getContent() + tmp);
				} else {
					System.out.println("Warning: The following characters are not allowed here: '" + tmp + "'");
				}
			}
		}
//			System.out.println("characters: " + new String(ch, start, length));
	}


	/**
	 *  JAVADOC: Description of the Method
	 *
	 * @exception  SAXException  JAVADOC: Description of the Exception
	 */
	public void endDocument()
			 throws SAXException {
//		System.out.println("...End of the document");
		if (!stack.empty()) {
			System.out.println("ERROR: Stack not empty...");
		}
		votable = (VOTableRoot) current;
//		System.out.println("PRINTING VOTABLE CONTENT:\n============================");
//		System.out.println(votable.toString());
	}


	/**
	 *  JAVADOC: Description of the Method
	 *
	 * @param  namespaceURI      JAVADOC: Description of the Parameter
	 * @param  localName         JAVADOC: Description of the Parameter
	 * @param  qName             JAVADOC: Description of the Parameter
	 * @exception  SAXException  JAVADOC: Description of the Exception
	 */
	public void endElement(String namespaceURI, String localName, String qName)
			 throws SAXException {
//		System.out.println("endElement: </" + localName + ">");
		VOTableElement<E> tmp = current;
		int typeChild = getTag(current.getTagName());
		current = (VOTableElement<E>) stack.pop();
		switch (getTag(current.getTagName())) {
						case VOTABLE:
							switch (typeChild) {
											case DESCRIPTION:
												((VOTableRoot) current).setDescription((Description) tmp);
												break;
											case DEFINITIONS:
												((VOTableRoot) current).setDefinitions((Definitions) tmp);
												break;
											case INFO:
												((VOTableRoot) current).addInfo((Info) tmp);
												break;
											case RESOURCE:
												((VOTableRoot) current).addResource((Resource) tmp);
												break;
											case VOTABLE:
												// TODO: Find why this case is reachable...
												current = tmp;
												break;
											default:
												System.out.println("ERROR: A '" + tmp.getTagName() + "' element can't be added to VOTABLE element.");
												break;
							}
							break;
						case DEFINITIONS:
							switch (typeChild) {
											// TODO: Handle DEFINITIONS correctly (pb with DEFINITION element).
											case COOSYS:
												((Definitions) current).addDefinition((Coosys) tmp);
												break;
											case PARAM:
												((Definitions) current).addDefinition((Param) tmp);
												break;
											default:
												System.out.println("ERROR: A '" + tmp.getTagName() + "' element can't be added to DEFINITIONS element.");
							}
							break;
						case RESOURCE:
							switch (typeChild) {
											case DESCRIPTION:
												((Resource) current).setDescription((Description) tmp);
												break;
											case INFO:
												((Resource) current).addInfo((Info) tmp);
												break;
											case COOSYS:
												((Resource) current).addCoosys((Coosys) tmp);
												break;
											case PARAM:
												((Resource) current).addParam((Param) tmp);
												break;
											case LINK:
												((Resource) current).addLink((Link) tmp);
												break;
											case TABLE:
												((Resource) current).addTable((Table) tmp);
												break;
											case RESOURCE:
												((Resource) current).addResource((Resource) tmp);
												break;
											default:
												System.out.println("ERROR: A '" + tmp.getTagName() + "' element can't be added to RESOURCE element.");
							}
							break;
						case PARAM:
							switch (typeChild) {
											case DESCRIPTION:
												((Param) current).setDescription((Description) tmp);
												break;
											case VALUES:
												((Param) current).setValues((Values) tmp);
												break;
											case LINK:
												((Param) current).addLink((Link) tmp);
												break;
											default:
												System.out.println("ERROR: A '" + tmp.getTagName() + "' element can't be added to PARAM element.");
							}
							break;
						case VALUES:
							switch (typeChild) {
											case MIN:
												((Values) current).setMin((Min) tmp);
												break;
											case MAX:
												((Values) current).setMax((Max) tmp);
												break;
											case OPTION:
												((Values) current).addOption((Option) tmp);
												break;
											default:
												System.out.println("ERROR: A '" + tmp.getTagName() + "' element can't be added to VALUES element.");
							}
							break;
						case OPTION:
							switch (typeChild) {
											case OPTION:
												((Option) current).addOption((Option) tmp);
												break;
											default:
												System.out.println("ERROR: A '" + tmp.getTagName() + "' element can't be added to OPTION element.");
							}
							break;
						case TABLE:
							switch (typeChild) {
											case DESCRIPTION:
												((Table) current).setDescription((Description) tmp);
												break;
											case FIELD:
												((Table) current).addField((Field) tmp);
												break;
											case LINK:
												((Table) current).addLink((Link) tmp);
												break;
											case DATA:
												((Table) current).setData((Data) tmp);
												break;
											default:
												System.out.println("ERROR: A '" + tmp.getTagName() + "' element can't be added to TABLE element.");
							}
							break;
						case FIELD:
							switch (typeChild) {
											case DESCRIPTION:
												((Field) current).setDescription((Description) tmp);
												break;
											case VALUES:
												((Field) current).addValues((Values) tmp);
												break;
											case LINK:
												((Field) current).addLink((Link) tmp);
												break;
											default:
												System.out.println("ERROR: A '" + tmp.getTagName() + "' element can't be added to FIELD element.");
							}
							break;
						case DATA:
							switch (typeChild) {
											case TABLEDATA:
												((Data) current).setTableData((TableData<E>) tmp);
												break;
											case BINARY:
												((Data) current).setBinary((Binary) tmp);
												break;
											case FITS:
												((Data) current).setFits((Fits) tmp);
												break;
											default:
												System.out.println("ERROR: A '" + tmp.getTagName() + "' element can't be added to DATA element.");
							}
							break;
						case TABLEDATA:
							if (typeChild == TR) {
								((TableData<E>) current).addTr((Tr<E>) tmp);
							} else {
								System.out.println("ERROR: A '" + tmp.getTagName() + "' element can't be added to TABLEDATA element.");
							}
							break;
						case TR:
							if (typeChild == TD) {
								((Tr<E>) current).addTd((Td<E>) tmp);
							} else {
								System.out.println("ERROR: A '" + tmp.getTagName() + "' element can't be added to TR element.");
							}
							break;
						case BINARY:
							if (typeChild == STREAM) {
								((Binary) current).setStream((Stream) tmp);
							} else {
								System.out.println("ERROR: A '" + tmp.getTagName() + "' element can't be added to BINARY element.");
							}
							break;
						case FITS:
							if (typeChild == STREAM) {
								((Fits) current).setStream((Stream) tmp);
							} else {
								System.out.println("ERROR: A '" + tmp.getTagName() + "' element can't be added to FITS element.");
							}
							break;
		}
		accepted = acceptedList[getTag(current.getTagName())];
	}


	/**
	 *  JAVADOC: Description of the Method
	 *
	 * @exception  SAXException  JAVADOC: Description of the Exception
	 */
	public void startDocument()
			 throws SAXException {
//		System.out.println("Start of the Document...");
		current = new VOTableRoot();
		accepted = new String[]{"VOTABLE"};
	}


	/**
	 *  JAVADOC: Description of the Method
	 *
	 * @param  namespaceURI      JAVADOC: Description of the Parameter
	 * @param  localName         JAVADOC: Description of the Parameter
	 * @param  qName             JAVADOC: Description of the Parameter
	 * @param  atts              JAVADOC: Description of the Parameter
	 * @exception  SAXException  JAVADOC: Description of the Exception
	 */
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts)
			 throws SAXException {
//		System.out.println("startElement: <" + localName + ">");
		localName = localName.toUpperCase();
		int type = getTag(localName);
		boolean found = false;
		int i = 0;
		while (!found && (i < accepted.length)) {
			found = localName.equals(accepted[i]);
			i++;
		}
		if (!found) {
			String message = "ERROR: The " + localName + " element is not expected here. The root element is " + current.getTagName();
/*
			System.out.print("List of Accepted tags: ");
			for (int h = 0; h < accepted.length; h++) {
				System.out.print(accepted[h] + " ");
			}
			System.out.println();
*/
			throw new SAXException(message);
		}
		if (text) {
			System.out.println("ERROR: A text was expect, no?");
		}
		if ((type != -1) && (current != null)) {
			stack.push(current);
			current = null;
		}
		Hashtable<String,String> att = getAttributes(atts);
		switch (type) {
						case VOTABLE:
							current = new VOTableRoot();
							((VOTableRoot) current).setID((String) att.get("ID"));
							((VOTableRoot) current).setVersion((String) att.get("version"));
							break;
						case DESCRIPTION:
							current = new Description();
							text = true;
							break;
						case DEFINITIONS:
							current = new Definitions();
							break;
						case INFO:
							current = new Info();
							text = true;
							((Info) current).setID((String) att.get("ID"));
							((Info) current).setValue((String) att.get("value"));
							((Info) current).setName((String) att.get("name"));
							break;
						case RESOURCE:
							current = new Resource();
							((Resource) current).setName((String) att.get("name"));
							((Resource) current).setID((String) att.get("ID"));
							((Resource) current).setType((String) att.get("type"));
							break;
						case COOSYS:
							current = new Coosys();
							text = true;
							((Coosys) current).setID((String) att.get("ID"));
							((Coosys) current).setEquinox((String) att.get("equinox"));
							((Coosys) current).setEpoch((String) att.get("epoch"));
							((Coosys) current).setSystem((String) att.get("system"));
							break;
						case PARAM:
							current = new Param();
							((Param) current).setID((String) att.get("ID"));
							((Param) current).setUnit((String) att.get("unit"));
							((Param) current).setDataType((String) att.get("datatype"));
							((Param) current).setPrecision((String) att.get("precision"));
							((Param) current).setWidth((String) att.get("width"));
							((Param) current).setRef((String) att.get("ref"));
							((Param) current).setName((String) att.get("name"));
							((Param) current).setUCD((String) att.get("ucd"));
							((Param) current).setValue((String) att.get("value"));
							((Param) current).setArraySize((String) att.get("arraysize"));
							break;
						case LINK:
							current = new Link();
							text = true;
							((Link) current).setID((String) att.get("ID"));
							((Link) current).setContentRole((String) att.get("content-role"));
							((Link) current).setContentType((String) att.get("content-type"));
							((Link) current).setTitle((String) att.get("title"));
							((Link) current).setValue((String) att.get("value"));
							((Link) current).setHref((String) att.get("href"));
							((Link) current).setGref((String) att.get("gref"));
							((Link) current).setAction((String) att.get("action"));
							break;
						case TABLE:
							current = new Table();
							((Table) current).setID((String) att.get("ID"));
							((Table) current).setName((String) att.get("name"));
							((Table) current).setRef((String) att.get("ref"));
							break;
						case VALUES:
							current = new Values();
							((Values) current).setID((String) att.get("ID"));
							((Values) current).setType((String) att.get("type"));
							((Values) current).setNull((String) att.get("null"));
							((Values) current).setInvalid((String) att.get("invalid"));
							break;
						case FIELD:
							current = new Field();
							((Field) current).setID((String) att.get("ID"));
							((Field) current).setUnit((String) att.get("unit"));
							((Field) current).setDataType((String) att.get("datatype"));
							((Field) current).setPrecision((String) att.get("precision"));
							((Field) current).setWidth((String) att.get("width"));
							((Field) current).setRef((String) att.get("ref"));
							((Field) current).setName((String) att.get("name"));
							((Field) current).setUCD((String) att.get("ucd"));
							((Field) current).setArraySize((String) att.get("arraysize"));
							((Field) current).setType((String) att.get("type"));
							break;
						case DATA:
							current = new Data();
							break;
						case MIN:
							current = new Min((String) att.get("value"));
							text = true;
							((Min) current).setInclusive((String) att.get("inclusive"));
							break;
						case MAX:
							current = new Max((String) att.get("value"));
							text = true;
							((Max) current).setInclusive((String) att.get("inclusive"));
							break;
						case OPTION:
							current = new Option((String) att.get("value"));
							((Option) current).setName((String) att.get("name"));
							break;
						case TABLEDATA:
							current = new TableData();
							break;
						case BINARY:
							current = new Binary();
							break;
						case FITS:
							current = new Fits();
							((Fits) current).setExtNum((String) att.get("extnum"));
							break;
						case TR:
							current = new Tr();
							break;
						case TD:
							current = new Td();
							text = true;
							((Td) current).setRef((String) att.get("ref"));
							break;
						case STREAM:
							current = new Stream();
							text = true;
							((Stream) current).setType((String) att.get("type"));
							((Stream) current).setHref((String) att.get("href"));
							((Stream) current).setActuate((String) att.get("actuate"));
							((Stream) current).setEncoding((String) att.get("encoding"));
							((Stream) current).setExpires((String) att.get("expires"));
							((Stream) current).setRights((String) att.get("rights"));
							break;
						default:
							System.out.println("ERROR: Type unknown.");
							break;
		}
		accepted = acceptedList[getTag(current.getTagName())];
	}



	/**
	 *  JAVADOC: Description of the Method
	 *
	 * @param  prefix            JAVADOC: Description of the Parameter
	 * @param  uri               JAVADOC: Description of the Parameter
	 * @exception  SAXException  JAVADOC: Description of the Exception
	 */
	public void startPrefixMapping(String prefix, String uri)
			 throws SAXException {
//			System.out.println("startPrefixMapping: " + prefix + " | " + uri);
	}


	/**
	 *  JAVADOC: Description of the Method
	 *
	 * @param  ch                JAVADOC: Description of the Parameter
	 * @param  start             JAVADOC: Description of the Parameter
	 * @param  length            JAVADOC: Description of the Parameter
	 * @exception  SAXException  JAVADOC: Description of the Exception
	 */
	public void ignorableWhitespace(char[] ch, int start, int length)
			 throws SAXException {
//			System.out.println("ignorableWhitespace: " + new String(ch, start, length));
	}


	/**
	 *  JAVADOC: Description of the Method
	 *
	 * @param  target            JAVADOC: Description of the Parameter
	 * @param  data              JAVADOC: Description of the Parameter
	 * @exception  SAXException  JAVADOC: Description of the Exception
	 */
	public void processingInstruction(String target, String data)
			 throws SAXException {
//			System.out.println("processingInstruction: " + target + " | " + data);
	}


	/**
	 *  JAVADOC: Description of the Method
	 *
	 * @param  name              JAVADOC: Description of the Parameter
	 * @exception  SAXException  JAVADOC: Description of the Exception
	 */
	public void skippedEntity(String name)
			 throws SAXException {
//			System.out.println("skippedEntity: " + name);
	}


	/**
	 *  JAVADOC: Description of the Method
	 *
	 * @param  prefix            JAVADOC: Description of the Parameter
	 * @exception  SAXException  JAVADOC: Description of the Exception
	 */
	public void endPrefixMapping(String prefix)
			 throws SAXException {
//			System.out.println("endPrefixMapping: " + prefix);
	}

}

