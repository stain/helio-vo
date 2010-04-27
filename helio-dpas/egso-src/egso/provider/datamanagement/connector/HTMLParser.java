package org.egso.provider.datamanagement.connector;

import java.util.Enumeration;
import java.util.Vector;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;



public class HTMLParser extends HTMLEditorKit.ParserCallback {

	private Vector<String> files = null;
	private String url = null;
	private String mask = null;

	public HTMLParser(String directoryURL) {
		url = directoryURL;
		files = new Vector<String>();
	}


	public HTMLParser(String directoryURL, String fileMask) {
		url = directoryURL;
		if (fileMask != null) {
			// Replace * and ? with \\w* and \\w? (\\w = any word character).
			mask = fileMask.replaceAll("\\*", "\\\\w*").replaceAll("\\?", "\\\\w?");
		}
		files = new Vector<String>();
	}


	public void handleStartTag(HTML.Tag t, MutableAttributeSet atts, int pos) {
		if (t == HTML.Tag.A) {
			String f = null;
			for (Enumeration<?> e = atts.getAttributeNames() ; e.hasMoreElements() ; ) {
				f = (String) atts.getAttribute(e.nextElement());
//				System.out.println("Link found = '" + f + "'");
				if (f.indexOf('.') != -1) {
					// Add the file if no mask or the file matches the mask.
					if ((mask == null) || ((mask != null) && (f.matches(mask)))) {
						files.add(url + "/" + f);
					}
				}
			}
		}
	}

	public Vector<String> getFilesList() {
		return (files);
	}


}
