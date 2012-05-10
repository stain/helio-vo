package eu.heliovo.shared.props;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;

import eu.heliovo.shared.util.IOUtil;

/**
 * Utility method to retrieve the HELIO configuration directory.
 * @author MarcoSoldati
 *
 */
public class HelioFileUtil {
    /**
     * A logger instance
     */
	private final static Logger _LOGGER = Logger.getLogger(HelioFileUtil.class);
	
	/**
	 * Return the home directory of HELIO.
	 * The directory will be created if not existing.
	 * Points to ${user.home}/.helio/area
	 * @param area the area directory.
	 * @return the home dir.
	 */
	public static File getHelioHomeDir(String area) {
		File homeDir = new File(System.getProperty("user.home"), ".helio" + File.separator + area);
		if (!homeDir.exists() && !homeDir.mkdirs()) {
			throw new RuntimeException("Unable to create home dir: " + homeDir);
		}
		return homeDir;
	}
	
	/**
	 * Get the helio temp dir. 
	 * The directory will be created if not existing
	 * Points to ${java.io.tmpdir}/.helio/area
	 * @param area the area directory
	 * @return the temp dir
	 */
	public static File getHelioTempDir(String area) {
		File tempDir = new File(System.getProperty("java.io.tmpdir"), ".helio" + File.separator + area);
		if (!tempDir.exists() && !tempDir.mkdirs()) {
			throw new RuntimeException("Unable to create temp dir: " + tempDir);
		}
		return tempDir;
	}
	
	/**
	 * Get a file from a remote location of from the cache if remote fails. 
	 * The cache will be updated automatically
	 * @param cacheFileDir the directory in which to store the cached file
	 * @param cacheFileName the name of the cached file
	 * @param remoteURL the url of the file.
	 * @return reference to the file in the cache.
	 */
	public static File getFileFromRemoteOrCache(String cacheFileDir, String cacheFileName, URL remoteURL) {
		// create the cache dir
		File cacheDir = getHelioTempDir(cacheFileDir);
		File cacheFile = cacheDir == null ? null : new File(cacheDir, cacheFileName);
		//System.out.println(cacheFile);
		// try to get the File from remote
		InputStream remoteInputStream;
		try {
			URLConnection conn = remoteURL.openConnection();
			conn.setUseCaches(false);
			remoteInputStream = conn.getInputStream();
			if (remoteInputStream.available() > 0) { 
				// and update the cache.
				try {
					IOUtil.ioCopy(remoteInputStream, new FileOutputStream(cacheFile));
				} catch (FileNotFoundException e) {
					_LOGGER.info("Unable to find cache file: " + cacheFile + ": " + e.getMessage(), e);				
				} catch (IOException e) {
					_LOGGER.info("Unable to write cache file: " + cacheFile + ": " + e.getMessage(), e);				
				}
			} else {
				_LOGGER.info("Unable to retrieve cached file from remote URL: " + remoteURL + ". The host seems to be disconnected.");								
			}
		} catch (IOException e) {
			_LOGGER.info("Unable to load cached file from remote URL: " + remoteURL + ": " + e.getMessage() + ". Trying to get local copy");
		}

		if (!cacheFile.exists()) {
			return null;
		}
		return cacheFile;
	}	
}
