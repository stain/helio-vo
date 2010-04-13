package org.egso.provider.datamanagement.connector;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Vector;

import javax.swing.text.html.parser.ParserDelegator;

public class HTTPFile {

    private String url = null;

    private String filename = null;

    private boolean directory = false;

    private int size = -1;

    private Vector<HTTPFile> files = null;

    public HTTPFile(String urlFile) throws FileNotFoundException {
        try {
            init(urlFile, null);
        } catch (FileNotFoundException fnfe) {
            throw (fnfe);
        }
    }

    public HTTPFile(String urlFile, String mask) throws FileNotFoundException {
        try {
            init(urlFile, mask);
        } catch (FileNotFoundException fnfe) {
            throw (fnfe);
        }
    }

    private void init(String urlFile, String mask) throws FileNotFoundException {
        if (mask != null) {
            mask = mask.replaceAll("\\?", "[\\\\w|_|.|-]?").replaceAll("\\*",
                    "[\\\\w|_|.|-]*?");
        }
        url = urlFile;
        try {
            URL u = new URL(urlFile);
            HttpURLConnection uc = (HttpURLConnection)u.openConnection();
            uc.connect();
            if(uc.getResponseCode()==404) {
                System.out.println("URL " + url + " is not reachable - 404.");
                throw (new FileNotFoundException("URL '" + url
                        + "' not reachable (404)."));
            }
            size = uc.getContentLength();

            // A directory has a length/size of -1.
            directory = (size == -1);
            if (directory) {
                // Parse the content of the directory to define the list of
                // files.
                BufferedReader inBuf = new BufferedReader(
                        new InputStreamReader(u.openStream()));
                HTMLParser parser = new HTMLParser(url);
                new ParserDelegator().parse(inBuf, parser, false);
                inBuf.close();
                files = new Vector<HTTPFile>();
                for (String tmp:parser.getFilesList()) {
                    if ((mask == null)
                            || ((mask != null) && tmp.substring(
                                    tmp.lastIndexOf('/') + 1).matches(mask))) {
                        files.add(new HTTPFile(tmp));
                    }
                }
            } else {
                filename = url.substring(url.lastIndexOf("/") + 1);
            }
        } catch (FileNotFoundException fnfe) {
            throw (fnfe);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isDirectory() {
        return (directory);
    }

    public boolean isFile() {
        return (!directory);
    }

    public Vector<HTTPFile> listFiles() {
        if (!directory) {
            return (null);
        }
        return (files);
    }

    public Vector<HTTPFile> listFiles(String mask) {
        if (!directory) {
            return (null);
        }
        // Replace * and ? with Java Patterns (\w = any word character).
        mask = mask.replaceAll("\\?", "[\\\\w|_|.|-]?").replaceAll("\\*",
                "[\\\\w|_|.|-]*?");
        Vector<HTTPFile> v = new Vector<HTTPFile>();
        for (HTTPFile http:files)
        {
            System.out.print("File " + http.getFilename() + " -> ");
            if (http.getFilename().matches(mask)) {
                System.out.println("OK");
                v.add(http);
            } else {
                System.out.println("NO");
            }
        }
        return (v);
    }

    public int getSize() {
        return (size);
    }

    public String getURL() {
        return (url);
    }

    public String getFilename() {
        return (filename);
    }

    public String toString() {
        if (directory) {
            StringBuffer sb = new StringBuffer("Directory '" + url + "' has "
                    + files.size() + " files:\n");
            for (HTTPFile h:files)
            {
                //sb.append("\t" + ((String) it.next()).toString() + "\n");
                sb.append("\t" + h.toString() + "\n");
            }
            return (sb.toString());
        }
        return ("File '" + url + "' (" + filename + "): Size=" + size);
    }
}