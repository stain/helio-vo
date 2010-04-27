/*
 * AbstractLogEnabled.java
 * Created on Jul 14, 2004
 */
package org.egso.common.logging;

import org.apache.log4j.Logger;

/**
 * abstract class providing a Logger.
 * <p>
 * replaces org.apache.avalon.framework.logger.AbstractLogenabled.
 * <br/>
 * This class is a wrapper to the logging system.
 * 
 * 
 * <h3>Changelog:</h3>
 * <ul><li>Aug 25, 2004-PK:<br/>
 * the log4j.xml was specified explicitly here in AbstractLogEnabled.
 * But log4j finds the log4j.xml automatically in the classpath.
 * Well, I just deleted 90% of this class' source code ;-)</li>
 * <li>Jul 14, 2004-PK: creation</li>
 * </ul>
 * 
 * @author Philipp Kunz
 * @version Aug 25, 2004-PK
 */
public class AbstractLogEnabled 
{
    protected AbstractLogEnabled() {
        
    }
    
    
    /**
     * each object's logger
     */
    private Logger theLogger = null;        
    
    
    /**
     * returns a Logger that logs to to the category
     * equal to the class name
     * 
     * @return a log4j Logger
     */
    protected Logger getLogger()
    {
        if (theLogger == null)
        {
            theLogger = Logger.getLogger(getClass().getName());
        }
        
        return theLogger;
    }
}
