package ch.fhnw.i4ds.helio.browser;

import java.io.File;
import java.net.URL;

/**
 * 
 * @author Lavanchy
 *
 */
public class BrowserConfig {
	URL url ;
	String os;
	int bit;
	File filename;
	File targetName;
	String browserTyp;
	

	
	/**
	 * 
	 * @param url Download location on the web. The downloaded file must be a ZIP file
	 * @param os
	 * @param bit 32 or 64 bit
	 * @param filename To run the portable browser. *.exe 
	 * @param targetName Place on the local system 
	 * @param browserTyp Firefox, Internet Explorer, Chrome, Opera
	 */
	public BrowserConfig(URL url, String os, int bit, File filename,
			File targetName, String browserTyp) {
		super();
		this.url = url;
		this.os = os;
		this.bit = bit;
		this.filename = filename;
		this.targetName = targetName;
		this.browserTyp = browserTyp;
	}



	public URL getUrl() {
		return url;
	}



	public String getOs() {
		return os;
	}



	public int getBit() {
		return bit;
	}



	public File getFilename() {
		return filename;
	}



	public File getTargetName() {
		return targetName;
	}



	public String getBrowserTyp() {
		return browserTyp;
	}
	
	

	
	
}
