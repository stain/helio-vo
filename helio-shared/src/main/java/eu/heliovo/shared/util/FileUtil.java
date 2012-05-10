package eu.heliovo.shared.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.log4j.Logger;

/**
 * Collection of static utilities for File handling.
 * @author marco.soldati (at fhnw.ch)
 *
 */
public class FileUtil {
    
    /**
     * The logger
     */
    private static final Logger _LOGGER = Logger.getLogger(FileUtil.class);
    
    
    /**
     * Overwrite options for the #copyFile method.
     * @author marco.soldati (at fhnw.ch)
     *
     */
    public enum OverwriteOption {
        /**
         * Always overwrite existing files while copying.
         */
        ALWAYS,

        /**
         * Only overwrite a file if the file to copy is newer.
         */
        IF_NEWER,
        
        /**
         * If there is any time difference, no matter which file is newer.
         */
        IF_TIME_DIFFERS,

        /**
         * Never overwrite a file if it already exists. 
         */
        NEVER; 

    }
    

    /**
     * Options for coping files.
     * @author marco.soldati (at fhnw.ch)
     *
     */
    public enum CopyOption {
        /**
         * copy a directory and all its children to the target
         */
        COPY_DIR,
        
        /**
         * Only copy the children of a directory (and their children) to the target, but not the directory itself
         */
        COPY_CHILDREN;
    }
    
    
    /**
     * no args value for getter methods.
     */
    public static final Object[] NO_ARGS = null;


    /**
     * Load the file and make sure that it exists.
     * @param filePath path of the file to be loaded.
     * @param fileName name of the file to be loaded.
     * @param validate should this method validate if the file exists?
     * @return the pointer to the file.
     * @throws IllegalArgumentException if the file path or the file does not exist.
     */
    public static File loadFile(String filePath, String fileName, boolean validate) throws IllegalArgumentException {
        return loadFile(filePath, fileName, validate, null);
    }
    
    /**
     * Load the file and make sure that it exists.
     * @param filePath path of the file to be loaded.
     * @param fileName name of the file to be loaded.
     * @param validate should this method validate if the file exists?
     * @param propertyName the name of the property that describes the dir. For user feedback only. Will be ignored if null.
     * @return the pointer to the file.
     * @throws IllegalArgumentException if the file path or the file does not exist.
     */
    public static File loadFile(String filePath, String fileName, boolean validate, String propertyName) throws IllegalArgumentException {
        File path = new File(filePath);
        if (validate && (path == null || !path.exists())) {
            throw new IllegalArgumentException((propertyName != null ? "Property '" + propertyName + "': d" : "D") + "irectory '" + filePath + "' does not exist.");
        }
        if (validate && !path.isDirectory())
            throw new IllegalArgumentException((propertyName != null ? "Property '" + propertyName + "': f" : "F") + "ile: '" + filePath + "' is not a directory.");
        
        File file = new File(path, fileName);
        if (validate && (file == null || !file.exists())) {
            throw new IllegalArgumentException((propertyName != null ? "Property '" + propertyName + "': f" : "F") + "ile: '" + filePath + "/" + fileName + "' does not exist.");
        }
        
        return file;
    }

    /**
     * load the file and make sure that it exists.
     * @param filePath the full path to the file.
     * @param validate should this method validate if the file exists?
     * @return pointer to the file.
     * @throws IllegalArgumentException if validation fails.
     */
    public static File loadFile(String filePath, boolean validate) throws IllegalArgumentException {        
        return loadFile(filePath, validate, null);
    }

    /**
     * load the file and make sure that it exists.
     * @param filePath the full path to the file.
     * @param validate should this method validate if the file exists?
     * @param propertyName the name of the property that describes the dir. For user feedback only. Will be ignored if null.
     * @return pointer to the file.
     * @throws IllegalArgumentException if validation fails.
     */    
    public static File loadFile(String filePath, boolean validate, String propertyName) throws IllegalArgumentException {
        File file = new File(filePath);
        if (validate && (file == null || !file.exists() || !file.isFile())) {
            throw new IllegalArgumentException((propertyName != null ? "Property '" + propertyName + "': f" : "F") + "ile: '" + filePath + "' does not exist.");
        }
        
        return file;
    }
    
    /**
     * Load a directory and make sure that it exists.
     * @param dir the directory to load
     * @param validate should this method validate if the file exists?
     * @throws IllegalArgumentException if validation fails.
     * @return pointer to the directory.
     */
    public static File loadDir(String dir, boolean validate) throws IllegalArgumentException {
        return loadDir(dir, validate, null);
    }
    
    /**
     * Load a directory and make sure that it exists.
     * @param dir the directory to load
     * @param validate should this method validate if the file exists?
     * @param propertyName the name of the property that describes the dir. For user feedback only. Will be ignored if null.
     * @throws IllegalArgumentException if validation fails.
     * @return pointer to the directory.
     */
    public static File loadDir(String dir, boolean validate, String propertyName) throws IllegalArgumentException {
        File path = new File(dir);
        if (validate && (path == null || !path.exists())) {
            throw new IllegalArgumentException((propertyName != null ? "Property '" + propertyName + "': d" : "D") +  "irectory '" + dir + "' does not exist.");
        }
        if (validate && !path.isDirectory())
            throw new IllegalArgumentException((propertyName != null ? "Property '" + propertyName + "': f" : "F") +  "ile '" + dir + "' is not a directory.");
        return path;
    }
    

    /**
     * Recursively remove a directory and all its subdirs.
     * @param dir the directory to remove
     * @return true if successful.
     */
    public static boolean removeDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = removeDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // The directory is now empty so delete it
        return dir.delete();

    }
    
    /**
     * Delete a file and make sure it is really gone.
     * @param fileToDelete the file to be deleted
     */
    public static boolean removeFile(File fileToDelete) {
        int i = 3;
        // retry sometimes as Windows sometimes takes its time to remove files.
        while( i > 0 && !fileToDelete.delete()) {
            System.gc();
            i--;
        }
        
        if (i == 0) {
            return false;
        }
        return true;
    }
    
    /**
     * Copy a File or recursively copy a directory and all its sub directories.
     * @param sourceFile the File or Directory to copy.
     * @param targetFile the location where to copy the file to. Could be either a File or a directory.
     * @param overwriteOption How to deal with existing target files. 
     * @param copyOption How to copy directories.
     * @return Number of files that have been copied.
     * @throws IOException if any IOError occurs.
     * 
     */
    public static int copyFile(File sourceFile, File targetFile, OverwriteOption overwriteOption, CopyOption copyOption) throws IOException {
        int numOfCopiedFiles = 0;

        //System.out.println(sourceFile + "->" + targetFile);
        if (!sourceFile.exists())
            throw new IllegalArgumentException("Unable to find file " + sourceFile);
        
        if (sourceFile.isDirectory() && targetFile.isFile()) {
            throw new IllegalArgumentException("Cannot copy a directory [" + sourceFile + "] to a file [" + targetFile + "].");
        }
        
        if (sourceFile.isFile()) {
            
            // make a file if targetFile is a directory.
            File target = targetFile.isDirectory() ? new File(targetFile, sourceFile.getName()) : targetFile;
            
            // create dir if parent does not exist.
            if (!target.getParentFile().exists()) {
                target.getParentFile().mkdirs();
            }
            
            long sourceTime = sourceFile.lastModified();
            if (sourceTime <= 0) {
                sourceTime = System.currentTimeMillis();
            }
            long targetTime = target.exists() ? target.lastModified() : -2;

            
            if (!target.exists() || 
                overwriteOption == OverwriteOption.ALWAYS ||
                (overwriteOption == OverwriteOption.IF_NEWER && targetTime < sourceTime) ||
                (overwriteOption == OverwriteOption.IF_TIME_DIFFERS && targetTime != sourceTime)
            ) {                
                FileInputStream input = new FileInputStream(sourceFile);
                FileOutputStream output = new FileOutputStream(target);
                
                if (_LOGGER.isDebugEnabled())
                    _LOGGER.debug("Copy " + sourceFile.getAbsolutePath() + " to " + target.getAbsolutePath());
                IOUtil.ioCopy(input, output, false, false);
                target.setLastModified(sourceTime);
                numOfCopiedFiles++;
            }
            
        } else { // we've got a directory.
            File[] children = sourceFile.listFiles();
            
            File newTarget = null;
            if (copyOption == CopyOption.COPY_DIR) {
                newTarget = new File (targetFile, sourceFile.getName());
                newTarget.mkdir();
            } else if (copyOption == CopyOption.COPY_CHILDREN) {
                newTarget = targetFile;                
            } else {
                throw new IllegalArgumentException("CopyOption '" + copyOption + "' is not supported by this method.");
            }
            for (File child : children) {
                numOfCopiedFiles += copyFile(child, newTarget, overwriteOption, CopyOption.COPY_DIR);
            }
        }
        return numOfCopiedFiles;
    }
    
    /**
     * Extract a resource from the classpath resource to a targetDir.
     * @param javaResourcePath the resource to copy. A file will be just copied over, a directory will be copied as defined by parameter copyOption.
     * @param targetDir where to write the files to.
     * @param overwriteOption how to deal with existing target files.
     * @param copyOption how to deal with directories.
     * @return number of copied files.
     */
    public static int copyClasspathResource(String javaResourcePath, File targetDir, OverwriteOption overwriteOption, CopyOption copyOption) {
        if (!targetDir.isDirectory()) {
            throw new IllegalArgumentException("Argument 'targetDir' must be a valid directory.");
        }
        
        if (!targetDir.exists())
            targetDir.mkdirs();

        URL resource = FileUtil.class.getResource(javaResourcePath);
        if (resource == null) {
            throw new IllegalArgumentException("Unable to find classpath resource " + javaResourcePath);
        }
        
        AssertUtil.assertArgumentNotNull(overwriteOption, "overwriteOption");
        AssertUtil.assertArgumentNotNull(copyOption, "copyOption");
        int numOfFiles = 0;
        
        URLConnection conn;
        try {
            conn = resource.openConnection();
        } catch (IOException e) {
            throw new RuntimeException("Unable to open connection to " + resource + ". Cause: " + e.getMessage());
        }
        if (conn instanceof JarURLConnection) {
            JarURLConnection jarConn = (JarURLConnection)conn;
            JarFile jar;
            JarEntry root;
            try {
                jar = jarConn.getJarFile();
                root = jarConn.getJarEntry();
                
            } catch (IOException e) {
                throw new RuntimeException("Unable to access jar file " + resource + ". Cause: " + e.getMessage());                
            }
            
            int rootLen = 0;
            
            if (root.isDirectory()) {
                rootLen = root.getName().length();
            } else {
                rootLen = root.getName().lastIndexOf("/") + 1;
            }

            // loop over all entries
            long start = System.currentTimeMillis();
            for (Enumeration<JarEntry> en = jar.entries(); en.hasMoreElements();) {
                JarEntry entry = en.nextElement();
                if (entry.getName().startsWith(root.getName())) {                    
                    String relPath = entry.getName().substring(rootLen);
                    if ( (!entry.getName().equals(root.getName())) || 
                         (copyOption == CopyOption.COPY_DIR) || 
                         (copyOption == CopyOption.COPY_CHILDREN && !relPath.contains("/") && !entry.isDirectory())) {
                        if (entry.isDirectory()) {
                            File dir = new File(targetDir, relPath);
                            if (!dir.exists())
                                dir.mkdirs();
                        } else {
                            File targetFile = new File(targetDir, relPath);

                            if (!targetFile.getParentFile().exists()) {
                                targetFile.getParentFile().mkdirs();
                            }

                            long sourceTime = entry.getTime();
                            if (sourceTime <= 0) {
                                sourceTime = System.currentTimeMillis();
                            }
                            long targetTime = targetFile.exists() ? targetFile.lastModified() : -2;

                            // (over)write target file
//                          StringBuilder sb = new StringBuilder();
//                          if (targetFile.exists()) {
//                          sb.append("Overwriting ");
//                          } else {
//                          sb.append("Copying ");
//                          }
//                          sb.append("from ")
//                          .append(jar.getName())
//                          .append("!")
//                          .append(entry.getName())
//                          .append(" to ")
//                          .append(targetFile);
//                          System.out.println(sb.toString());

                            if (!targetFile.exists() || 
                                    overwriteOption == OverwriteOption.ALWAYS ||
                                    (overwriteOption == OverwriteOption.IF_NEWER && targetTime < sourceTime) ||
                                    (overwriteOption == OverwriteOption.IF_TIME_DIFFERS && targetTime != sourceTime)
                                ) {
                                try {
                                    IOUtil.ioCopy(jar.getInputStream(entry), new FileOutputStream(targetFile), false, false);
                                    targetFile.setLastModified(sourceTime);
                                    numOfFiles++;
                                } catch (FileNotFoundException e) {
                                    throw new RuntimeException("FileNotFoundException while copying '" + jar.getName() + "!" + entry.getName() + "' to '" + targetFile + "'. Cause: " + e.getMessage());                
                                } catch (IOException e) {
                                    throw new RuntimeException("Unable to copy file '" + jar.getName() + "!" + entry + "' to '" + targetFile + "'. Cause: " + e.getMessage());
                                }
                            }
                        }
                    } else {
                        // ignore entry
                        //System.out.println(entry);
                    }
                }
            }
            long end = System.currentTimeMillis();
            if (_LOGGER.isInfoEnabled()) {
                _LOGGER.info(numOfFiles + " files copied in " + (end-start) + "ms from " + jar.getName() + "!" + root.getName() + " to " + targetDir + ".");
            }
            
        } else {
            // we assume that we have a fileURLConnection
            File root = null;
            try {
                root = new File(resource.toURI());
            } catch (Exception e) {
                throw new IllegalArgumentException(
                    "This class only supports resources in a jar file or in a local directory structure. The url " 
                    + resource + " could not be loaded as java.io.File: " + e.getMessage());
            }

            long start = System.currentTimeMillis();            
            try {
                if (_LOGGER.isDebugEnabled()) {
                    _LOGGER.debug("Processing file system resource: " + resource);
                }
                //System.out.println(resource);
                numOfFiles = FileUtil.copyFile(root, targetDir, overwriteOption, copyOption);                
            } catch (FileNotFoundException e) {
                throw new RuntimeException("FileNotFoundException while copying from '" + root + "' to '" + targetDir + "'. Cause: " + e.getMessage());                
            } catch (IOException e) {
                throw new RuntimeException("Unable to copy from '" + root + "' to '" + targetDir + "'. Cause: " + e.getMessage());
            }
            long end = System.currentTimeMillis();
            if (_LOGGER.isInfoEnabled()) {
                _LOGGER.info(numOfFiles + " files copied in " + (end-start) + "ms from " + root + " to " + targetDir + ".");
            }
        }
        return numOfFiles;
    }
    
    
    /**
     * Get relative path of File 'target' with respect to 'home' directory
     * Example:
     * <pre>
     * home = /a/b/c
     * tar  = /a/d/e/x.txt
     * s = getRelativePath(home,tar) = ../../d/e/x.txt
     * </pre>
     * <p>Copied and adapted from http://www.devx.com/tips/Tip/13737.</p>
     * @param home base path. If it is a file the parent directory will be chosen.
     * @param target file to generate path for
     * @return relative path from home to f as a string using the {@link File#separator} as separator.
     * @throws IOException if the canonical path of any of the files cannot be determinded.
     */
    public static String getRelativePath(File home, File target) throws IOException {
        File homeFile = home.getCanonicalFile();
        if (!homeFile.isDirectory()) {
            homeFile = homeFile.getParentFile();
        }
        
        File targetFile = target.getCanonicalFile();
        
        List<String> homelist = getPathList(homeFile);
        List<String> filelist = getPathList(targetFile);
        String s = matchPathLists(homelist, filelist);
        return s;
    }
       
    /**
     * Figure out a string representing the relative path of
     * 'homeList' with respect to 'fileList'
     * @param homeList home path
     * @param fileList path of file
     * @return the relative path between the home and the file.
     */
    private static String matchPathLists(List<String> homeList, List<String> fileList) {
        int i;
        int j;
        StringBuilder sb = new StringBuilder();
        // start at the beginning of the lists
        // iterate while both lists are equal        
        i = homeList.size() - 1;
        j = fileList.size() - 1;

        // first eliminate common root
        while ((i >= 0) && (j >= 0) && (homeList.get(i).equals(fileList.get(j)))) {
            i--;
            j--;
        }

        // for each remaining level in the home path, add a ..
        for (; i >= 0; i--) {
            sb.append("..").append(File.separator);
        }

        // for each level in the file path, add the path
        for (; j >= 1; j--) {
            sb.append(fileList.get(j)).append(File.separator);
        }

        // file name
        if (j >= 0) {
            sb.append(fileList.get(j));
        } else if (sb.length() > 0) {
            sb.deleteCharAt(sb.length()-1);
        }
        return sb.toString();
    }

    /**
     * Break a path down into individual elements and add to a list.
     * Example : if a path is /a/b/c/d.txt, the breakdown will be [d.txt,c,b,a]
     * @param f input file
     * @return a List collection with the individual elements of the path in reverse order
     */
    private static List<String> getPathList(File f) {
        List<String> l = new ArrayList<String>();
        File r = f;
        while (r != null) {
            l.add(r.getName());
            r = r.getParentFile();
        }
        return l;
    }

    /**
     * Convert String to URL. 
     * @param url the url to convert
     * @return the url as URL object
     * @throws RuntimeException if the URL is not valid
     */
    public static URL asURL(String url) throws RuntimeException {
    	try {
    		return new URL(url);
    	} catch (MalformedURLException e) {
    		throw new RuntimeException("Unable to parse URL '" + url + "'. Cause: " + e.getMessage(), e);
    	}
    }

}