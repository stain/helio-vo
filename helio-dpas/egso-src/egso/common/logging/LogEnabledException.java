/*
 * LogEnabledException.java
 * Created on Oct 28, 2004
 * Author Philipp Kunz
 */
package org.egso.common.logging;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * extends {@link Exception} with a log method. 
 * <p>
 * Instead of writing
 * <pre>
 * Throwable ex = new WhateverException("blablabla");
 * if (logger.isInfoEnabled) logger.info(ex);
 * throw ex;
 * </pre>
 * it is now possible to use a slightly simpler form
 * <pre>
 * throw new WhateverException("blablabla").info(logger);
 * </pre>
 * So the exception uses the logger of the code where the
 * exception condition was detected and the exception was
 * raised. This is much more accurate, than as before logging
 * all exception into LoggedException's default category.
 * 
 * @since consumer-core-R2
 * @author Philipp Kunz
 * @version Oct 28, 2004-PK
 */
@SuppressWarnings("serial")
public class LogEnabledException extends Exception
{
    /**
     * The messages about missing logger go here.
     */
    private final static Level NOLOGGERLOGLEVEL = Level.WARN;
    
    
    /**
     * @see Exception#Exception()
     */
    public LogEnabledException() {
        super();
    }

    /**
     * @see Exception#Exception(java.lang.String)
     */
    public LogEnabledException(String message) {
        super(message);
    }

    /**
     * @see Exception#Exception(java.lang.Throwable)
     */
    public LogEnabledException(Throwable cause) {
        super(cause);
    }

    /**
     * @see Exception#Exception(java.lang.String,java.lang.Throwable)
     */
    public LogEnabledException(String message, Throwable cause) {
        super(message, cause);
    }
    
    
    /**
	 * the default logger.
	 * Used only if no other proper logger has been specified.
	 */
	/*
	 * its somewhat wierd because LoggedException cannot inherit both
	 * from Exception and AbstractLogEnabled at the same time
	 */
	private final static Logger defaultLogger = (new AbstractLogEnabled() {
		public Logger getLogger() {
			return super.getLogger();
		}
	}).getLogger();
	
	
    /**
     * logs the exception to DEBUG level into the given logger.
     * 
     * @param logger
     * the logger where to log the exception.
     * 
     * @return
     * The Exception instance itself
     * 
     * @throws IllegalArgumentException
     * if <code>logger</code> is <code>null</code>.
     */
    public Exception debug(Logger logger) 
    throws IllegalArgumentException {
        if (logger == null)
        {
            IllegalArgumentException iae = 
                new IllegalArgumentException("no logger specified!");
            defaultLogger.log(NOLOGGERLOGLEVEL, iae);
            if (defaultLogger.isDebugEnabled()) defaultLogger.debug(this);
            throw iae;
        }
        if (logger.isDebugEnabled()) log(Level.DEBUG, logger);
        return this;
    }
	
	
    /**
     * logs the exception to DEBUG level into the given logger.
     * 
     * @param logger
     * the logger where to log the exception.
     * 
     * @return
     * The Exception instance itself
     * 
     * @throws IllegalArgumentException
     * if <code>logger</code> is <code>null</code>.
     */
    public Exception info(Logger logger) 
    throws IllegalArgumentException {
        if (logger == null)
        {
            IllegalArgumentException iae = 
                new IllegalArgumentException("no logger specified!");
            defaultLogger.log(NOLOGGERLOGLEVEL, iae);
            if (defaultLogger.isInfoEnabled()) defaultLogger.info(this);
            throw iae;
        }
        if (logger.isInfoEnabled()) log(Level.INFO, logger);
        return this;
    }
	
	
    /**
     * logs the exception to WARN level into the given logger.
     * 
     * @param logger
     * the logger where to log the exception.
     * 
     * @return
     * The Exception instance itself
     * 
     * @throws IllegalArgumentException
     * if <code>logger</code> is <code>null</code>.
     */
    public Exception warn(Logger logger) 
    throws IllegalArgumentException {
        if (logger == null)
        {
            IllegalArgumentException iae = 
                new IllegalArgumentException("no logger specified!");
            defaultLogger.log(NOLOGGERLOGLEVEL, iae);
            defaultLogger.warn(this);
            throw iae;
        }
        log(Level.WARN, logger);
        return this;
    }
	
	
    /**
     * logs the exception to ERROR level into the given logger.
     * 
     * @param logger
     * the logger where to log the exception.
     * 
     * @return
     * The Exception instance itself
     * 
     * @throws IllegalArgumentException
     * if <code>logger</code> is <code>null</code>.
     */
    public Exception error(Logger logger) 
    throws IllegalArgumentException {
        if (logger == null)
        {
            IllegalArgumentException iae = 
                new IllegalArgumentException("no logger specified!");
            defaultLogger.log(NOLOGGERLOGLEVEL, iae);
            defaultLogger.error(this);
            throw iae;
        }
        log(Level.ERROR, logger);
        return this;
    }
	
	
    /**
     * logs the exception to the given log level into the given logger.
     * 
     * @param logger
     * the logger where to log the exception.
     * 
     * @param level
     * see {@link Level}
     * 
     * @return
     * The Exception instance itself
     * 
     * @throws IllegalArgumentException
     * if <code>logger</code> is <code>null</code>.
     */
    public Exception log(Level level, Logger logger) 
    throws IllegalArgumentException {
        if (logger == null)
        {
            IllegalArgumentException iae = 
                new IllegalArgumentException("no logger specified!");
            defaultLogger.log(NOLOGGERLOGLEVEL, iae);
            defaultLogger.log(level, this);
            throw iae;
        }
        logger.log(level, this);
        return this;
    }
}
