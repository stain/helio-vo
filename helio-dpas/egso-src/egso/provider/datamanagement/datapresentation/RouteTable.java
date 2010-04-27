package org.egso.provider.datamanagement.datapresentation;

import java.io.FileInputStream;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;
import org.apache.xerces.parsers.DOMParser;
import org.egso.provider.ProviderConfiguration;
import org.egso.provider.admin.ProviderMonitor;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


/**
 *  TODO: Description of the Class
 *
 *@author     Romain Linsolas (linsolas@gmail.com)
 *@version 0.1
 * 0.1: 18-11-2003.
 */
public class RouteTable {
/*
TODO: How to include an interval?
	-> Create Condition object (used as element in the hashtable), e.g. IF(value > x) AND (value < y).
*/
	
	
	/**
	 * The Hashtable routeTable contains all information needed to determine which
	 * archives must be queried, considering the list of available parameters in
	 * the query. This table is an hashtable, where the key is the name of the
	 * parameter and the corresponding object is a String[] or an hastable. The
	 * String[] is used to regroup all archives queried for this parameter (no
	 * matter what its value is). In the other case, the Hashtable has all
	 * possible values as keys, and all corresponding archives as object.
	 **/
	private Hashtable<String,Object> routeTable = null;
	private Hashtable<String,String[]> queryTable = null;
	private Hashtable<String,String> filesTable = null;
	private Vector<String> exclusiveParams = null;


	public RouteTable() {
		System.out.println("[Route Table] Initialization...");
		initRouteTable();
	}
	
	
	/**
	 * Populates the routeTable by parsing the XML File dedicated to the DPM.
	 **/
	@SuppressWarnings("unchecked")
  private void initRouteTable() {
		routeTable = new Hashtable<String,Object>();
		filesTable = new Hashtable<String,String>();
		queryTable = new Hashtable<String,String[]>();
		exclusiveParams = new Vector<String>();
		// TODO: Get XML file from Avalon?
		try {
			InputSource in = new InputSource(new FileInputStream((String) ProviderConfiguration.getInstance().getProperty("core.routetable")));
//			InputSource in = new InputSource(new BufferedReader(new InputStreamReader(cl.getResourceAsStream((String) ProviderConfiguration.getInstance().getProperty("core.routetable")))));
			DOMParser parser = new DOMParser();
			parser.parse(in);
			Node root = parser.getDocument().getDocumentElement();
			NodeList children = root.getChildNodes();
			Node n = null;
			NamedNodeMap atts = null;
			Node separator = null;
			Hashtable<String,Object> valuesTable = null;
			for (int i = 0 ; i < children.getLength() ; i++) {
				n = children.item(i);
				if ((n.getNodeType() == Node.ELEMENT_NODE) && (n.getNodeName().equals("param"))) {
					atts = n.getAttributes();
					if (atts != null) {
						String name = atts.getNamedItem("name").getNodeValue();
						// TODO: Test if the parameter already exists in the Hashtable.
						Object obj = routeTable.get(name);
						if (obj != null) {
							if (obj instanceof Hashtable<?,?>) {
								valuesTable = (Hashtable<String,Object>)obj;
							} else {
								// TODO: Log error.
								// The case where for a same parameter name we have both
								// value-specific and non-value-specific attribute is not
								// yet considered...
								System.out.println("ERROR 001 in XML File for the DPM (Case not considered yet).");
							}
						} else {
							valuesTable = null;
						}
						separator = atts.getNamedItem("separator");
						// Definition of the archives.
						String[] archives = null;
						if (separator != null) {
							// Many archives are listed.
							StringTokenizer st = new StringTokenizer(atts.getNamedItem("archives").getNodeValue(), separator.getNodeValue());
							archives = new String[st.countTokens()];
							for (int index = 0 ; st.hasMoreTokens() ; index++) {
								archives[index] = st.nextToken();
							}
						} else {
							// Only one archive.
							archives = new String[] {atts.getNamedItem("archives").getNodeValue()};
						}
						// Now, see if this is a value-specific parameter.
						if (atts.getNamedItem("value-specific").getNodeValue().toLowerCase().trim().equals("yes")) {
							if (valuesTable == null) {
								valuesTable = new Hashtable<String,Object>();
							}
							String[] values = null;
							// Value specific parameter.
							if (separator != null) {
								// Many values are indicated.
								StringTokenizer st = new StringTokenizer(atts.getNamedItem("values").getNodeValue(), separator.getNodeValue());
								values = new String[st.countTokens()];
								for (int index = 0 ; st.hasMoreTokens() ; index++) {
									values[index] = st.nextToken();
								}
							} else {
								// Only one value is indicated.
								values = new String[] {atts.getNamedItem("values").getNodeValue()};
							}
							// Populating the routeTable...
							for (int j = 0 ; j < values.length ; j++) {
								String val = values[j];
								if (valuesTable.containsKey(val)) {
									String[] tmp = (String[]) valuesTable.get(val.toUpperCase());
									String[] tmp2 = new String[tmp.length + archives.length];
									// TODO: VERIFY !!!!!!
									// Merge both old and new archives list.
									System.arraycopy(tmp, 0, tmp2, 0, tmp.length);
									System.arraycopy(archives, 0, tmp2, tmp.length + 1, archives.length);
									valuesTable.put(val.toUpperCase(), tmp2);
								} else {
									valuesTable.put(val.toUpperCase(), archives);
								}
								routeTable.put(name, valuesTable);
							}
						} else {
							// The value of the parameter is not important.
							if (valuesTable != null) {
								// TODO: Log.
								System.out.println("ERROR 002 DPM");
							}
							routeTable.put(name, archives);
							// Test if the parameter is exclusive.
							if ((atts.getNamedItem("exclusive") != null) && (atts.getNamedItem("exclusive").getNodeValue().toLowerCase().trim().equals("yes"))) {
								exclusiveParams.add(name);
							}
						}
					}
				} else {
					if ((n.getNodeType() == Node.ELEMENT_NODE) && (n.getNodeName().equals("query"))) {
						atts = n.getAttributes();
						String[] archives = null;
						separator = atts.getNamedItem("separator");
						if (separator != null) {
							// Many archives are listed.
							StringTokenizer st = new StringTokenizer(atts.getNamedItem("archives").getNodeValue(), separator.getNodeValue());
							archives = new String[st.countTokens()];
							for (int index = 0 ; st.hasMoreTokens() ; index++) {
								archives[index] = st.nextToken();
							}
						} else {
							// Only one archive.
							archives = new String[] {atts.getNamedItem("archives").getNodeValue()};
						}
						queryTable.put(atts.getNamedItem("name").getNodeValue(), archives);
					} else {
						if ((n.getNodeType() == Node.ELEMENT_NODE) && (n.getNodeName().equals("file"))) {
							filesTable.put(n.getAttributes().getNamedItem("start").getNodeValue(), n.getAttributes().getNamedItem("archive").getNodeValue());
						}
					}
				}
			}
		} catch (Exception e) {
			ProviderMonitor.getInstance().reportException(e);
			e.printStackTrace();
		}
	}


	public String getArchiveForFile(String file) {
		for(String key:filesTable.keySet())
			if (file.startsWith(key))
				return ((String) filesTable.get(key));

		return (null);
	}


	public String[] getParamNames() {
		String[] names = new String[routeTable.size()];
		int i = 0;
		for(String rtEntry:routeTable.keySet())
			names[i++] = rtEntry;

		return(names);
	}
	
	
	public boolean isValueSpecific(String name) {
		Object obj = routeTable.get(name);
		return((obj != null) && (obj instanceof Hashtable<?,?>));
	}
	
	public boolean isValueIndependent(String name) {
		Object obj = routeTable.get(name);
		return((obj != null) && (!(obj instanceof Hashtable<?,?>)));
	}

	public boolean isGeneric(String name) {
		return(routeTable.get(name) == null);
	}
	

	public boolean isExclusive(String name) {
		return (exclusiveParams.contains(name));
	}
	
	public String[] getArchives(String name) {
		Object obj = routeTable.get(name);
		if ((obj == null) || (obj instanceof Hashtable<?,?>)) {
			return(null);
		}
		return((String[]) obj);
	}
	
	@SuppressWarnings("unchecked")
  public String[] getArchives(String name, String value) {
		Object obj = routeTable.get(name);
		if ((obj == null) || (!(obj instanceof Hashtable<?,?>))) {
			return(null);
		}
		return ((Hashtable<String,String[]>)obj).get(value.toUpperCase());
	}
	
	@SuppressWarnings("unchecked")
  public String[] getArchives(String name, String[] values) {
		Object obj = routeTable.get(name);
		if ((obj == null) || (!(obj instanceof Hashtable<?,?>))) {
			return(null);
		}
		Vector<String> v = new Vector<String>();
		for (String value:values)
		{
			String[] archs = ((Hashtable<String,String[]>) obj).get(value.toUpperCase());
			if (archs != null)
			  for(String arch:archs)
					if (!v.contains(arch))
						v.add(arch);
		}
		
		return v.toArray(new String[0]);
	}
	
	public String[] getArchivesFromQuery(String name) {
		return ((String[]) queryTable.get(name));
	}
	
	public String[] getArchives(String name, Vector<String> values) {
		// TODO: To be implemented...
		return(null);
	}
	
	public boolean containsParam(String name) {
		return(routeTable.get(name) != null);
	}
	
	
	public String[] getArchives(Node param) {
		String name = param.getAttributes().getNamedItem("name").getNodeValue();
		Object obj = routeTable.get(name);
		if (obj == null)
			// Generic parameter.
			return null;

		// Archive-specific parameter.
		if (!(obj instanceof Hashtable<?,?>))
			// Value independent parameter.
			return (String[])obj;

		// Value specific parameter.
		return new String[0];
	}
	
	
	@SuppressWarnings("unchecked")
  public String toString() {
		StringBuffer sb = new StringBuffer() ;
		for (String name:routeTable.keySet())
		{
			Object obj = routeTable.get(name);
			sb.append("Parameter '" + name + "':\n");
			if (obj instanceof Hashtable<?,?>) {
				Hashtable<String,String[]> values = (Hashtable<String,String[]>) obj;
				for (String val:values.keySet())
				{
					sb.append("\tValue '" + val + "': ");
					for(String s:values.get(val))
						sb.append(s + " ");

					sb.append("\n");
				}
			} else {
				sb.append("\tArchives: ");
				for (String s:(String[])obj) {
					sb.append(s + " ");
				}
				sb.append("\n");
			}
		}
		return(sb.toString());
	}
	
}
