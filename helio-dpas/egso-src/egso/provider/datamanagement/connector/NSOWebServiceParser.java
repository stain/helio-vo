package org.egso.provider.datamanagement.connector;

import java.util.StringTokenizer;
import java.util.Vector;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class NSOWebServiceParser extends DefaultHandler implements ProviderResultsParser {

	private String[] tmp;
//	private String spaces = "";
	private Vector<String[]> results = null;
	private boolean inValue = false;
	private boolean inSrc = false;
	private boolean inFile = false;
	private boolean inObs = false;
	private boolean inTimeSpec = false;
	private int index = -1;
	private String[] pattern = null;


	public NSOWebServiceParser() {
		System.out.println("New instance of 'NSOWebServiceParser' class.");
		results = new Vector<String[]>();
//		spaces = "";
	}

	
	public void setSelectedFields(Vector<String> selected) {
		pattern = new String[selected.size()];
		String str = null;
		
		//TODO: fix
		if(1==1)
		  throw new RuntimeException("FIX ME FIX ME FIX ME");
		
		for (int i = 0 ; i < selected.size() ; i++) {
			str = (String) selected.get(i - 2);
			if (str.equals("instrument")) {
				pattern[i] = "index,0";
			} else {
				if (str.equals("filename")) {
					pattern[i] = "substring,2,lastIndexOf,/,END";
				} else {
					//if (str.equals("start-date")) {
				    if (str.equals("time_start")) {
						pattern[i] = "replace,5,.,-";
					} else {
						//if (str.equals("end-date")) {
					    if (str.equals("time_end")) {
							pattern[i] = "replace,6,.,-";
						} else {
							if (str.equals("link")) {
								pattern[i] = "index,2";
							} else {
								if (str.equals("IDArchive")) {
									pattern[i] = "value,WS-NSO";
								} else {
									if (str.equals("observatory")) {
										pattern[i] = "value,NSO";
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
/*
		System.out.println("SELECT FIELDS:");
		for (int i = 0 ; i < pattern.length ; i++) {
			System.out.println("\t" + pattern[i]);
		}
*/
	}

	public Vector<Vector<String>> getResults() {
		Vector<Vector<String>> finalResults = new Vector<Vector<String>>();
		StringTokenizer st = null;
		String x = null;
		int index = -1;
		int begin = -1;
		int end = -1;
		
		for (String[] tmp:results)
		{
			Vector<String> tempo = new Vector<String>();
			for (int i = 0 ; i < pattern.length ; i++) {
				st = new StringTokenizer(pattern[i], ",");
				x = st.nextToken();
				if (x.equals("value")) {
					// Case of a static value.
					tempo.add(st.nextToken());
				} else {
					if (x.equals("index")) {
						// Case of a value that doesn't need to be modified.
						tempo.add(tmp[Integer.parseInt(st.nextToken())]);
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
								System.out.println("[ERROR - NSO.WS.Parser]: Can't manage '" + x + "' pattern.");
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
			tmp[index] = str;
//		} else {
//			System.out.println("WARNING: Characters not accepted here: '" +  str + "'.");
		}
	}


	public void endDocument()
		throws SAXException {
//		System.out.println("...End of the document");
	}

	public void endElement(String namespaceURI, String localName, String qName)
		throws SAXException {
//		System.out.println("endElement: " + namespaceURI + " | " + localName + " | " + qName);
//		spaces = spaces.substring(2);
//		System.out.println(spaces + "</" + localName + ">");
		if (qName.equals("value")) {
			inValue = false;
		}
		if (inValue) {
			if (qName.equals("item")) {
				results.add(tmp);
			} else {
				if (qName.equals("src")) {
					inSrc = false;
				} else {
					if (qName.equals("file")) {
						inFile = false;
					} else {
						if (qName.equals("obs")) {
							inObs = false;
						} else {
							if (qName.equals("timespec")) {
								inTimeSpec = false;
							}
						}
					}
				}
			}
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
		results = new Vector<String[]>();
//		System.out.println("Start of the Document...");
	}
	
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts)
		throws SAXException {
//		System.out.println(spaces + "<" + localName + ">");
//		spaces += "  ";
		if (qName.equals("value")) {
			inValue = true;
		}
		if (inValue) {
			if (qName.equals("item")) {
				tmp = new String[7];
			} else {
				if (qName.equals("src")) {
					inSrc = true;
				} else {
					if (qName.equals("file")) {
						inFile = true;
					} else {
						if (qName.equals("obs")) {
							inObs = true;
						} else {
							if (qName.equals("timespec")) {
								inTimeSpec = true;
							}
						}
					}
				}
				if (inSrc && qName.equals("name")) {
					index = 0;
				} else {
					if (inSrc && qName.equals("instrument")) {
						index = 1;
					} else {
						if (inFile && qName.equals("id")) {
							index = 2;
						} else {
							if (inFile && qName.equals("size")) {
								index = 3;
							} else {
								if (inObs && qName.equals("name")) {
									index = 4;
								} else {
									if (inTimeSpec && qName.equals("start")) {
										index = 5;
									} else {
										if (inTimeSpec && qName.equals("end")) {
											index = 6;
										} else {
											index = -1;
										}
									}
								}
							}
						}
					}
				}
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

