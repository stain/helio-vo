package org.egso.provider.datamanagement.archives;


import org.egso.provider.utils.XMLTools;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



/**
 * Creates a SQL Base object that maps a SQL archive description.
 *
 * @author    Romain Linsolas (linsolas@gmail.com)
 * @version   1.0 - 20/10/2004
 */
public class SQLBaseFactory {

	private XMLTools xmlTools = null;


	/**
	 * Creator. Does nothing.
	 */
	private SQLBaseFactory() {
		xmlTools = XMLTools.getInstance();
	}


	/**
	 * Creates a new instance of a SQLBaseFactory object.
	 *
	 * @return   New instance of a SQLBaseFactory object.
	 */
	public static SQLBaseFactory newInstance() {
		return (new SQLBaseFactory());
	}


	/**
	 * Creates a new Base object from a description (in XML).
	 *
	 * @param xml  The XML description of the archive.
	 * @return     The Base object that maps the description.
	 */
	public Base createBase(Node xml) {
		Base base = new Base(xml.getAttributes().getNamedItem("idArchive").getNodeValue());
		NodeList tables = null;
		NodeList links = null;
		NodeList mapping = null;
		try {
			tables = xmlTools.selectNodeList(xml, "//structure/table");
			links = xmlTools.selectNodeList(xml, "//structure/links/link");
			mapping = xmlTools.selectNodeList(xml, "//map/mapping/param");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Node node = null;
		Node n = null;
		NodeList children = null;
		NamedNodeMap atts = null;
		Table table = null;
		String type = null;
		String format = null;
		// Creating tables...
		for (int i = 0; i < tables.getLength(); i++) {
			node = tables.item(i);
			table = new Table(node.getAttributes().getNamedItem("name").getNodeValue(), base);
			children = node.getChildNodes();
			for (int j = 0; j < children.getLength(); j++) {
				n = children.item(j);
				if ((n.getNodeType() == Node.ELEMENT_NODE) && (n.getNodeName().equals("field"))) {
					atts = n.getAttributes();
					type = (atts.getNamedItem("type") != null) ? atts.getNamedItem("type").getNodeValue() : null;
					format = (atts.getNamedItem("format") != null) ? atts.getNamedItem("format").getNodeValue() : null;
					table.addField(new Field(atts.getNamedItem("name").getNodeValue(), table, type, format));
				}
			}
			base.addTable(table);
		}
		// Creating links...
		Field f1 = null;
		Field f2 = null;
		Link link = null;
		for (int i = 0; i < links.getLength() ; i++) {
			atts = links.item(i).getAttributes();
			f1 = base.getField(atts.getNamedItem("start").getNodeValue());
			f2 = base.getField(atts.getNamedItem("end").getNodeValue());
			type = (atts.getNamedItem("type") != null) ? atts.getNamedItem("type").getNodeValue() : null;
			link = new Link(f1, f2, type);
			f1.getTable().addLink(link);
			f2.getTable().addLink(link);
		}
		base.setLinkMatrix(new LinkMatrix(base));
		// Creating mapping information...
		base.createMappingTable(mapping);
		
//		NodeList addS = null;
//		NodeList addF = null;
		NodeList addW = null;
		try {
//			addS = xmlTools.selectNodeList(xml, "//map/mapping/addition/select/add");
//			addF = xmlTools.selectNodeList(xml, "//map/mapping/addition/from/add");
			addW = xmlTools.selectNodeList(xml, "//map/mapping/addition/where/add");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO : Do it for SELECT and FROM...
		if (addW != null) {
			for (int i = 0 ; i < addW.getLength() ; i++) {
				base.addToNewWhere(addW.item(i).getAttributes().getNamedItem("value").getNodeValue());
			}
		}
		
		return (base);
	}

}

