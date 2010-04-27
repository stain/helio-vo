package org.egso.provider.datamanagement.connector;

import java.util.*;

import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;



public class VSOWebServiceParser extends DefaultHandler implements ProviderResultsParser {

	private String[] tmp;
	private Vector<String[]> results = null;
	private boolean inValue = false;
	private boolean handleResults = false;
	private int index = -1;
	private String[] pattern = null;
	private String[] fields = {"instrume", "id", "size", "obs_type", "date_obs", "date_end"};


	public VSOWebServiceParser() {
		System.out.println("New instance of 'VSOWebServiceParser' class.");
		results = new Vector<String[]>();
//		spaces = "";
	}

	
	public void setSelectedFields(Vector<String> selected) {
		pattern = new String[selected.size()];
		String str = null;
//		pattern[0] = "value,WS-VSO";
//		pattern[1] = "value,SOHO";
		for (int i = 0 ; i < selected.size() ; i++) {
			str = (String) selected.get(i);
			if (str.equals("instrument")) {
				pattern[i] = "index,0";
			} else {
				if (str.equals("filename")) {
					pattern[i] = "substring,1,lastIndexOf,/,END";
				} else {
					//if (str.equals("start-date")) {
				    if (str.equals("time_start")) {
						pattern[i] = "index,4";
					} else {
						//if (str.equals("end-date")) {
						    if (str.equals("time_end")) {
							pattern[i] = "index,5";
						} else {
							if (str.equals("link")) {
								pattern[i] = "index,1";
							} else {
								if (str.equals("IDArchive")) {
									pattern[i] = "value,WS-VSO";
								} else {
									if (str.equals("observatory")) {
										pattern[i] = "value,SOHO";
									} else {
										if (str.equals("observingdomain")) {
											pattern[i] = "observingdomain";
										} else {
											if (str.equals("filesize")) {
												pattern[i] = "index,2";
											} else {
												pattern[i] = "value,N/A";
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
/*
		System.out.println("SELECT FIELDS:");
		for (int i = 0 ; i < pattern.length ; i++) {
			System.out.println("\t" + pattern[i]);
		}
*/
	}

	public Vector<Vector<String>> getResults() {
		Vector<Vector<String>> finalResults = new Vector<Vector<String>>();
		int index = -1;
		int begin = -1;
		int end = -1;
		for (String[] tmp:results)
		{
		  Vector<String> tempo = new Vector<String>();
			String instr = null;
			for (int i = 0 ; i < pattern.length ; i++) {
				StringTokenizer st = new StringTokenizer(pattern[i], ",");
				String x = st.nextToken();
				if (x.equals("value")) {
					// Case of a static value.
					tempo.add(st.nextToken());
				} else {
					if (x.equals("index")) {
						// Case of a value that doesn't need to be modified.
						int y = Integer.parseInt(st.nextToken());
						if (y == 0) {
							instr = tmp[y].toUpperCase();
						}
						tempo.add(((y == 1) ? "http://sohodata.nascom.nasa.gov" : "") + tmp[y]);
					} else {
						if (x.equals("substring")) {
							// Case of a value that must be splitted.
							// substring,X,Y,Z: X=index, Y=begin, Z=end.
							x = st.nextToken();
							index = Integer.parseInt(x);
							x = st.nextToken();
							try {
								begin = Integer.parseInt(x);
							} catch (NumberFormatException nfe) {
								if (x.equals("lastIndexOf")) {
									begin = tmp[index].lastIndexOf(st.nextToken().charAt(0)) + 1;
								} else {
									if (x.equals("indexOf")) {
										begin = tmp[index].indexOf(st.nextToken().charAt(0)) + 1;
									}
								}
							}
							x = st.nextToken();
							if (x.equals("END")) {
								tempo.add(tmp[index].substring(begin));
							} else {
								try {
									end = Integer.parseInt(x);
								} catch (NumberFormatException nfe) {
									if (x.equals("lastIndexOf")) {
										end = tmp[index].lastIndexOf(st.nextToken().charAt(0)) + 1;
									} else {
										if (x.equals("indexOf")) {
											end = tmp[index].indexOf(st.nextToken().charAt(0)) + 1;
										}
									}
								}
								tempo.add(tmp[index].substring(begin, end));
							}
						} else {
							if (x.equals("replace")) {
								// Case of a value where some characters must be replaced.
								tempo.add(tmp[Integer.parseInt(st.nextToken())].replace(st.nextToken().charAt(0), st.nextToken().charAt(0)));
							} else {
								if (x.equals("observingdomain")) {
									String obs = "N/A";
									// CELIAS, COSTEP, ERNE, GOLF, MDI, VIRGO -> N/A
									// EIT, CDS, SWAN -> Extreme UV
									// SUMER, UVCS -> UV
									// LASCO -> Visible
									if (instr != null) {
										if (instr.equals("EIT") || instr.equals("CDS") || instr.equals("SWAN")) {
											obs = "Extreme UltraViolet";
										} else {
											if (instr.equals("LASCO")) {
												obs = "Visible";
											} else {
												if (instr.equals("SUMER") || instr.equals("UVCS")) {
													obs = "UltraViolet";
												}
											}
										}
									}
									tempo.add(obs);
								} else {
									System.out.println("[ERROR - VSO.WS.Parser]: Can't manage '" + x + "' pattern.");
								}
							}
						}
					}
				}
			}
			finalResults.add(tempo);
		}
		return (finalResults);
	}

	public void characters(char[] ch, int start, int length)
		throws SAXException {
		String str = new String(ch, start, length);
//		System.out.println(spaces + str);
		if (index != -1) {
			if (tmp[index] == null) {
				tmp[index] = str;
			} else {
				tmp[index] += str;
			}
//System.out.println("tmp[" + index + "] = " + str);
//		} else {
//			System.out.println("WARNING: Characters not accepted here: '" +  str + "'.");
		}
	}


	public void endDocument()
		throws SAXException {
//		System.out.println("...End of the document");
//		System.out.println("Reception of results finished. Formatting results...");
	}

	public void endElement(String namespaceURI, String localName, String qName)
		throws SAXException {
//			System.out.println("endElement: " + namespaceURI + " | " + localName + " | " + qName);
//		spaces = spaces.substring(2);
//		System.out.println(spaces + "</" + localName + ">");
		if (handleResults && qName.equals("value")) {
			results.add(tmp);
			inValue = false;
		}
		if (qName.equals("s-gensym5")) {
			handleResults = false;
		}
	}
	
	public void endPrefixMapping(String prefix)
		throws SAXException {
//			System.out.println("endPrefixMapping: " + prefix);
	}
	
	public void ignorableWhitespace(char[] ch, int start, int length)
		throws SAXException {
//		System.out.println("ignorableWhitespace: " + new String(ch, start, length));
	}
	
	public void processingInstruction(String target, String data)
		throws SAXException {
//		System.out.println("processingInstruction: " + target + " | " + data);
	}
	
	public void setDocumentLocator(Locator locator) {
//		System.out.println("setDocumentLocator");
	}
	
	public void skippedEntity(String name)
		throws SAXException {
//		System.out.println("skippedEntity: " + name);
	}
	
	public void startDocument()
		throws SAXException {
//		System.out.println("Start of the Document...");
		results = new Vector<String[]>();
	}
	
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts)
		throws SAXException {
//		System.out.println(spaces + "<" + localName + ">");
//		spaces += "  ";
		if (qName.equals("s-gensym5")) {
			handleResults = true;
		}
		if (handleResults && qName.equals("value")) {
			inValue = true;
		}
		if (inValue) {
			if (qName.equals("item")) {
				tmp = new String[6];
			} else {
				boolean found = false;
				int i = 0;
				while (!found && (i < fields.length)) {
					found = qName.equals(fields[i]);
					i++;
				}
				index = found ? (i - 1) : -1;
			}
		} else {
			index = -1;
		}
	}
	
	public void startPrefixMapping(String prefix, String uri)
		throws SAXException {
//			System.out.println("startPrefixMapping: " + prefix + " | " + uri);
	}
}

