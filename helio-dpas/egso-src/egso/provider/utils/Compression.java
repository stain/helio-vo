package org.egso.provider.utils;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.egso.provider.admin.ProviderMonitor;


/**
 * The class Compression allows the compression of multiple files into a ZIP
 * file, as well as the decompression of a ZIP file.
 *
 * @author    Romain Linsolas (linsolas@gmail.com)
 * @version   1.0-jd - 30/01/2004 [29/01/2004]
 */
public class Compression {

	/**
	 * Constructor for the Compression class.
	 */
	public Compression() {
	}


	/**
	 * Decompression of a ZIP file.
	 *
	 * @param file  ZIP file to be decompressed.
	 */
	public static void unzipFile(String file) {
		try {
			ZipFile zipFile = new ZipFile(file);
			ZipEntry entry = null;
			for (Enumeration<? extends ZipEntry> entries = zipFile.entries(); entries.hasMoreElements(); ) {
				entry = entries.nextElement();
				/*
				if (entry.isDirectory()) {
					// Assume directories are stored parents first then children.
					System.out.println("Extracting directory: " + entry.getName());
					// This is not robust, just for demonstration purposes.
					(new File(entry.getName())).mkdir();
					continue;
				}
*/
				System.out.println("Extracting file: " + entry.getName());
				// Extraction of the file.
				InputStream in = zipFile.getInputStream(entry);
				OutputStream out = new BufferedOutputStream(new FileOutputStream(entry.getName()));
				byte[] buffer = new byte[1024];
				int len;
				while ((len = in.read(buffer)) >= 0) {
					out.write(buffer, 0, len);
				}
				in.close();
				out.close();

			}
			zipFile.close();
		} catch (IOException ioe) {
			ProviderMonitor.getInstance().reportException(ioe);
			ioe.printStackTrace();
		}
	}


	/**
	 * Compression of a set of files into a ZIP file. The compression level used
	 * here is the DEFALUT_COMPRESSION level.
	 *
	 * @param files    Names of files that must be compressed.
	 * @param zipFile  Name of the ZIP file.
	 */
	public static void zipFiles(String[] files, String zipFile) {
		zipFiles(files, zipFile, Deflater.DEFAULT_COMPRESSION);
	}


	/**
	 * Compression of a set of files into a ZIP file with a given compression
	 * level.
	 *
	 * @param files                         Names of files that must be compressed.
	 * @param zipFile                       Name of the ZIP file.
	 * @param levelOfCompression            Level of compression used (0 -> 9).
	 * @exception IllegalArgumentException  If the compression level is incorrect.
	 */
	public static void zipFiles(String[] files, String zipFile, int levelOfCompression)
			 throws IllegalArgumentException {
		// Create a buffer for reading the files
		byte[] buf = new byte[1024];
		try {
			// Create the ZIP file
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));
			try {
				out.setLevel(levelOfCompression);
			} catch (IllegalArgumentException iae) {
				ProviderMonitor.getInstance().reportException(iae);
				throw iae;
			}
			for (int i = 0; i < files.length; i++) {
				// Compress the files
				FileInputStream in = new FileInputStream(files[i]);
				// Add ZIP entry to output stream.
				out.putNextEntry(new ZipEntry(files[i]));
				// Transfer bytes from the file to the ZIP file
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				// Complete the entry
				out.closeEntry();
				in.close();
			}
			// Complete the ZIP file
			out.close();
		} catch (IOException e) {
			ProviderMonitor.getInstance().reportException(e);
			e.printStackTrace();
		}
	}

}

