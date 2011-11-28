package ch.fhnw.i4ds.helio.browser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

/**
 * 
 * @author Lavanchy
 * @see http://openbook.galileodesign.de/javainsel7/javainsel_13_010.htm
 */
public class ZipHandler {
	private static final byte[] buffer = new byte[0xFFFF];

	/**
	 * Unzip the wished file to the required location.
	 * 
	 * @param zipLocation
	 *            zip File
	 * @param destination
	 * @return Return falls if operation failed
	 */
	public boolean unZip(File zipLocation, File destination) {
		try {
			ZipFile zipFile = new ZipFile(zipLocation);

			Enumeration<? extends ZipEntry> zipentEnum = zipFile.entries();

			while (zipentEnum.hasMoreElements()) {
				ZipEntry zipEntry = (ZipEntry) zipentEnum.nextElement();
				extractEntry(zipFile, zipEntry, destination);
			}

		} catch (ZipException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * Use only with unZip
	 * 
	 * @param zipFile
	 * @param zipEntry
	 * @param destination
	 * @throws IOException
	 */
	private void extractEntry(ZipFile zipFile, ZipEntry zipEntry,
			File destination) throws IOException {
		File file = new File(destination, zipEntry.getName());
		if (zipEntry.isDirectory()) {
			file.mkdirs();
		} else {
			new File(file.getParent()).mkdirs();
			InputStream is = null;
			FileOutputStream os = null;

			try {
				is = zipFile.getInputStream(zipEntry);
				os = new FileOutputStream(file);

				for (int len; (len = is.read(buffer)) != -1;) {
					os.write(buffer, 0, len);
				}
			} catch (Exception e) {
				System.out.println("extractEntry eror: ");
				e.printStackTrace();
			} finally {
				os.close();
				is.close();
			}

		}

	}
}
