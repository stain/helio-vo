package org.egso.common.votable;

import cds.savot.pull.SavotPullParser;
import cds.savot.pull.SavotPullEngine;
import org.xml.sax.InputSource;
import java.io.File;
import java.io.InputStream;
import java.io.ByteArrayInputStream;



/**
 * The VOTableFactory provides methods to create VOTable elements using the
 * SAVOT parser and data model.<BR/>
 * See the http://vizier.u-strasbg.fr/devcorner.gml webpage for more details
 * about SAVOT.<BR/>
 * <B>Compatibilty with SAVOT 2.1.3.</B>
 *
 * @author    Romain Linsolas (linsolas@gmail.com)
 * @version   1.0 - 05/10/2004
 */
/*
1.0 - 05/10/2004:
	First release.
*/
public class VOTableFactory {

	/**
	 * Constructor for the VOTableFactory object.
	 */
	private VOTableFactory() {
	}


	/**
	 * Returns an instance of a VOTableFactory.
	 *
	 * @return   A new instance of a VOTableFactory.
	 */
	public static VOTableFactory newInstance() {
		return (new VOTableFactory());
	}


	/**
	 * Creates a VOTable element from a String.
	 *
	 * @param content  String that contains the VOTable.
	 * @return         The VOTable element.
	 */
	public EGSOVOTable createVOTable(String content) {
		SavotPullParser parser = new SavotPullParser(new ByteArrayInputStream(content.getBytes()), SavotPullEngine.FULL, "ISO-8859-1");
		return (new EGSOVOTable(parser.getVOTable()));
	}


	/**
	 * Creates a VOTable element from a file.
	 *
	 * @param file  File that contains the VOTable.
	 * @return      The VOTable element.
	 */
	public EGSOVOTable createVOTable(File file) {
		SavotPullParser parser = new SavotPullParser(file.getPath(), SavotPullEngine.FULL);
		return (new EGSOVOTable(parser.getVOTable()));
	}


	/**
	 * Creates a VOTable element from an InputStream.
	 *
	 * @param is  InputStream that contains the VOTable.
	 * @return    The VOTable element.
	 */
	public EGSOVOTable createVOTable(InputStream is) {
		SavotPullParser parser = new SavotPullParser(is, SavotPullEngine.FULL, "ISO-8859-1");
		return (new EGSOVOTable(parser.getVOTable()));
	}


	/**
	 * Creates a VOTable element from an InputSource.
	 *
	 * @param is  InputSource that contains the VOTable.
	 * @return    The VOTable element.
	 */
	public EGSOVOTable createVOTable(InputSource is) {
		SavotPullParser parser = new SavotPullParser(is.getByteStream(), SavotPullEngine.FULL, "ISO-8859-1");
		return (new EGSOVOTable(parser.getVOTable()));
	}

}

