package org.egso.provider.query;

import java.io.FileNotFoundException;
import java.util.Vector;

import org.egso.provider.datamanagement.connector.HTTPFile;


public class HTTPQuery extends ArchiveQuery {


	private Vector<String> urls = null;
	private Vector<String> masks = null;
	private Vector<String> fields = null;
	private int index = -1;


	public HTTPQuery() {
		super(ArchiveQuery.HTTP_ARCHIVE);
		urls = new Vector<String>();
		masks = new Vector<String>();
		fields = new Vector<String>();
		index = 0;
	}

	public void addHTPPFile(String url) {
		addHTTPFile(url, null);
	}

	public void addHTTPFile(String url, String mask) {
		urls.add(url);
		masks.add(mask);
	}

	public int numberOfFiles() {
		return (masks.size());
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("HTTP URLS:\n");
		String m = null;
		for (int i = 0 ; i < urls.size() ; i++) {
			m = (String) masks.get(i);
			sb.append("\turl= " + ((String) urls.get(i)));
			if (m != null) {
				sb.append(" | mask = " + m);
			}
			sb.append("\n");
		}
		return (sb.toString());
	}

	public boolean hasLeftFiles() {
		return (index != urls.size());
	}
	
	public HTTPFile listNextFile() {
		if (index == urls.size()) {
			return (null);
		}
		HTTPFile f = null;
		try {
			f = new HTTPFile((String) urls.get(index), (String) masks.get(index));
		} catch (FileNotFoundException e) {
			return (null);
		}
		index++;
		return (f);
	}
	
	public boolean ignoreNextCommand() {
		if (index >= (urls.size() - 1)) {
			return (false);
		}
		index++;
		return(true);
	}
	
	public void restart() {
		index = 0;
	}
	
	public HTTPFile[] listAllLeftFiles() {
		HTTPFile[] results = new HTTPFile[urls.size() - index];
		int i = 0;
		while (index < urls.size()) {
			try {
				results[i] = new HTTPFile((String) urls.get(index), (String) masks.get(index));
			} catch (FileNotFoundException e) {
				results[i] = null;
			}
			i++;
			index++;
		}
		return (results);
	}



	public HTTPFile[] listAllFiles() {
		index = 0;
		return (listAllLeftFiles());
	}
	

	public void addField(String field) {
		fields.add(field);
	}
	
	public void setFields(Vector<String> v) {
		fields = v;
	}
	
	public Vector<String> getSelectedFields() {
		return (fields);
	}


}

