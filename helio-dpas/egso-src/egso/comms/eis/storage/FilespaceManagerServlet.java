/*
 * Created on Sep 24, 2004
 */
package org.egso.comms.eis.storage;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.egso.comms.eis.conf.Configuration;
import org.egso.comms.eis.conf.ConfigurationException;
import org.egso.comms.eis.conf.ConfigurationFactory;

/**
 * Servlet class for managing an HTTP accessible filespace.
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 */
@SuppressWarnings("serial")
public class FilespaceManagerServlet extends HttpServlet {

    // Logging

    private static Logger logger = LogManager.getLogger(FilespaceManagerServlet.class);

    // Instance variables

    private File filespaceBaseDirectory = null;

    private boolean configured = false;

    private boolean init = false;

    // Configurable implemenation

    public synchronized void configure(Configuration configuration) {
        logger.info("Configuring, filespace base directory: " + configuration.getFilespaceBaseDirectory() + ", filespace base URI: " + configuration.getFilespaceBaseURI());
        filespaceBaseDirectory = configuration.getFilespaceBaseDirectory();
        configured = true;
    }

    // Lifecycle implementation
    public void init(ServletConfig config) throws ServletException {
        if (!init) {
            try {
                logger.info("Initializing");
                if (!configured) {
                    configure(ConfigurationFactory.createConfiguration());
                }
                filespaceBaseDirectory.mkdirs();
                logger.info("Ready");
            } catch (ConfigurationException e) {
                throw new ServletException("Failed to initialize", e);
            }
        }
    }

    public void destroy() {
        init = false;
        logger.info("Destroyed");
    }

    // Public interface

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getPathInfo() == null || request.getPathInfo().equals("/")) {
            writeIndex(request, response);
        } else {
            retrieveData(request, response);
        }
    }

    protected void retrieveData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        File file = new File(filespaceBaseDirectory, request.getPathInfo());
        if (!file.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, request.getRequestURI());
        } else {
            DataHandler dataHandler = new DataHandler(new FileDataSource(file));
            response.setContentType(dataHandler.getContentType());
            dataHandler.writeTo(response.getOutputStream());
            if (file.getName().startsWith(FilespaceManager.PIPE_PREFIX)) {
                file.delete();
            }
        }
    }

    private static String getHumanReadable(long bytes) {
        String[] magnitude = new String[] { "bytes", "Kb", "Mb", "Gb" };
        int i = 0;
        while (bytes >= 1024) {
            bytes /= 1024;
            i++;
        }
        return (int) bytes + " " + magnitude[i];
    }

    public long getTotalBytes(File[] files) {
        long bytes = 0;
        for (int i = 0; i < files.length; i++) {
            bytes += files[i].length();
        }
        return bytes;
    }

    protected void writeIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        writer.println("<html>");
        writer.println();
        writer.println("  <head>");
        writer.println("    <title>Filespace</title>");
        writer.println("  </head>");
        writer.println();
        writer.println("  <body>");
        writer.println();
        writer.println("  <h1>Filespace</h1>");
        writer.println();
        writer.println("  <h2>File Summary</h2>");
        writer.println();
        File[] files = filespaceBaseDirectory.listFiles(new PrefixFilter(FilespaceManager.FILE_PREFIX));
        String s = files.length + " file";
        if (files.length != 1) {
            s += "s";
        }
        s += ", " + getHumanReadable(getTotalBytes(files));
        writer.println(s);
        writer.println();
        writer.println("  <h2>Pipe Summary</h2>");
        writer.println();
        files = filespaceBaseDirectory.listFiles(new PrefixFilter(FilespaceManager.PIPE_PREFIX));
        s = files.length + " pipe";
        if (files.length != 1) {
            s += "s";
        }
        s += ", " + getHumanReadable(getTotalBytes(files));
        writer.println(s);
        writer.println();
        writer.println("  </body>");
        writer.println();
        writer.println("</html>");
    }

    protected static class PrefixFilter implements FilenameFilter {

        private String prefix = null;

        public PrefixFilter(String prefix) {
            this.prefix = prefix;
        }

        public boolean accept(File directory, String filename) {
            return filename.startsWith(prefix);
        }

    }

}