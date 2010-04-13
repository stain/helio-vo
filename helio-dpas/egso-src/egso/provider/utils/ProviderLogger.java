package org.egso.provider.utils;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.HTMLLayout;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.Priority;
import org.apache.log4j.xml.XMLLayout;
import org.egso.provider.ProviderConfiguration;

public class ProviderLogger {

    private static ProviderLogger providerLogger = null;

    private Logger logger = null;

    public static final int DEBUG = 0;

    public static final int INFO = 1;

    public static final int WARN = 2;

    public static final int ERROR = 3;

    public static final int FATAL = 4;

    private ProviderLogger() {
        System.out.println("[Provider Logger] Initialization.");
        System.out.println("[][][][][][][][][][][][][][][][][][][]");
        System.out.println("[][][][][][][][][][][][][][][][][][][]");
        System.out.println("[ ERROR => MUST NOT BE USED ANYMORE. ]");
        System.out.println("[][][][][][][][][][][][][][][][][][][]");
        System.out.println("[][][][][][][][][][][][][][][][][][][]");
        // Configure the logs format.
        ProviderConfiguration conf = ProviderConfiguration.getInstance();
        PatternLayout layout = new PatternLayout((String) conf.getProperty("logs.pattern"));
        // Create the logger and set its level.
        logger = LogManager.getLogger("provider");
        logger.setLevel(Level.toLevel((String) conf.getProperty("logs.level")));
        // Console logger configuration.
        if (((String) conf.getProperty("logs.console")).toLowerCase().equals("true")) {
            System.out.println("Enabling console logging.");
            logger.addAppender(new ConsoleAppender(layout));
        }
        // Files loggers configuration.
        FileAppender file = null;
        String filename = null;
        if (((String) conf.getProperty("logs.file.txt")).toLowerCase().equals("true")) {
            filename = (String) conf.getProperty("logs.file.txt.name");
            System.out.println("Enabling textual logging (" + filename + ").");
            try {
                file = new FileAppender(layout, filename);
                file.setAppend(((String) conf.getProperty("logs.file.txt.append")).toLowerCase().equals("true"));
                file.activateOptions();
                logger.addAppender(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (((String) conf.getProperty("logs.file.xml")).toLowerCase().equals("true")) {
            filename = (String) conf.getProperty("logs.file.xml.name");
            System.out.println("Enabling XML logging (" + filename + ").");
            try {
                file = new FileAppender(new XMLLayout(), filename);
                file.setAppend(((String) conf.getProperty("logs.file.xml.append")).toLowerCase().equals("true"));
                file.activateOptions();
                logger.addAppender(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (((String) conf.getProperty("logs.file.html")).toLowerCase().equals("true")) {
            filename = (String) conf.getProperty("logs.file.html.name");
            System.out.println("Enabling HTML logging (" + filename + ").");
            try {
                HTMLLayout htmlLayout = new HTMLLayout();
                htmlLayout.setTitle("EGSO Provider logs");
                file = new FileAppender(htmlLayout, filename);
                file.setAppend(((String) conf.getProperty("logs.file.txt.append")).toLowerCase().equals("true"));
                file.activateOptions();
                logger.addAppender(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // Manage remote log servers...
        // TODO !
    }

    public static ProviderLogger getInstance() {
        if (providerLogger == null) {
            providerLogger = new ProviderLogger();
        }
        return (providerLogger);
    }

    public void debug(Object obj) {
        log(DEBUG, obj);
    }

    public void info(Object obj) {
        log(INFO, obj);
    }

    public void warn(Object obj) {
        log(WARN, obj);
    }

    public void error(Object obj) {
        log(ERROR, obj);
    }

    public void fatal(Object obj) {
        log(FATAL, obj);
    }

    public void log(int type, Object obj) {
        Priority priority = null;
        switch (type) {
        case DEBUG:
            priority = Level.DEBUG;
            break;
        case INFO:
            priority = Level.INFO;
            break;
        case WARN:
            priority = Level.WARN;
            break;
        case ERROR:
            priority = Level.ERROR;
            break;
        case FATAL:
            priority = Level.FATAL;
            break;
        default:
            System.out.println("The priority " + type + " is not valid for Logging.");
            break;
        }
        logger.log(priority, obj);
    }

}

