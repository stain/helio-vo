package org.egso.comms.log.log4j;

import java.rmi.RemoteException;

import javax.xml.rpc.Stub;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.AsyncAppender;
import org.apache.log4j.spi.ErrorCode;
import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;
import org.egso.comms.log.types.Event;
import org.egso.comms.log.types.EventList;
import org.egso.comms.log.types.Location;
import org.egso.comms.log.types.Log;
import org.egso.comms.log.types.Throwable;
import org.egso.comms.log.wsdl.Logging_PortType;
import org.egso.comms.log.wsdl.Logging_Service_Impl;

/**
 * Client class for use with the Log4j framework. For performance
 * reason events are buffered by this class and then sent
 * asynchronously. This means that applications must explicitly manage
 * the lifecycle of associated <code>Loggers</code> in order to
 * ensure that remaining events are flushed on shutdown. The
 * recommended way of doing this is to use a <code>LogManager</code>:
 * 
 * <pre> Logger logger = LogManager.getLogger(com.example.MyClass.class);
 * ...
 * LogManager.shutdown();</pre>
 * 
 * For reference your <code>log4j.xml</code> configuration file
 * should probably contain something similar to the following:
 * 
 * <pre>
 * &lt;appender name=&quot;service&quot; class=&quot;org.egso.comms.log.log4j.Appender&quot;&gt;
 *   &lt;param name=&quot;LogEndpoint&quot; value=&quot;http://www.example.com:8080/egso-comms-log/Logging&quot;/&gt;
 *   &lt;param name=&quot;LogOwnerId&quot; value=&quot;myComponent&quot;/&gt;
 *   &lt;param name=&quot;LogName&quot; value=&quot;myLog&quot;/&gt;
 *   &lt;param name=&quot;AppendLocation&quot; value=&quot;false&quot;/&gt;
 * &lt;/appender&gt;
 *   
 * &lt;category name=&quot;com.example.myClass&quot;&gt;
 *   &lt;priority value=&quot;info&quot;/&gt;
 *   &lt;appender-ref ref=&quot;service&quot;/&gt;
 * &lt;/category&gt;
 * </pre>
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 */
public class Appender extends AsyncAppender {

    // Instance variables

    private DelegateAppender delegateAppender = null;

    // Constructors

    public Appender() {
        this.delegateAppender = new DelegateAppender();
        addAppender(delegateAppender);
    }

    public Appender(String logEndpoint, String logOwnerId, String logName, String logId, boolean appendLocation) {
        delegateAppender = new DelegateAppender(logEndpoint, logOwnerId, logName, logId, appendLocation);
        addAppender(delegateAppender);
    }

    // Public interface

    public void setBufferSize(int size) {
        super.setBufferSize(size);
        delegateAppender.setBufferSize(size);
    }

    public String getLogEndpoint() {
        return delegateAppender.getLogEndpoint();
    }

    public void setLogEndpoint(String logEndpoint) {
        delegateAppender.setLogEndpoint(logEndpoint);
    }

    public String getLogId() {
        return delegateAppender.getLogId();
    }

    public void setLogId(String logId) {
        delegateAppender.setLogId(logId);
    }

    public String getLogName() {
        return delegateAppender.getLogName();
    }

    public void setLogName(String logName) {
        delegateAppender.setLogName(logName);
    }

    public String getLogOwnerId() {
        return delegateAppender.getLogOwnerId();
    }

    public void setLogOwnerId(String logOwnerId) {
        delegateAppender.setLogOwnerId(logOwnerId);
    }

    public boolean isAppendLocation() {
        return delegateAppender.isAppendLocation();
    }

    public void setAppendLocation(boolean appendLocation) {
        delegateAppender.setAppendLocation(appendLocation);
    }

    // Member classes

    protected class DelegateAppender extends AppenderSkeleton {

        // Instance Variables

        private Logging_PortType log = new Logging_Service_Impl().getLoggingPort();

        protected String logOwnerId = null;

        private String logId = null;

        protected String logName = null;

        private boolean appendLocation = false;

        private LoggingEvent[] buffer = new LoggingEvent[50];

        private int nextEvent = 0;

        // Constructors

        public DelegateAppender() {
        }

        public DelegateAppender(String logEndpoint, String logOwnerId, String logName, String logId, boolean appendLocation) {
            ((Stub) log)._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, logEndpoint);
            this.logOwnerId = logOwnerId;
            this.logName = logName;
            this.logId = logId;
            this.appendLocation = appendLocation;
        }

        // Public interface

        public void setBufferSize(int size) {
            appendEvents();
            buffer = new LoggingEvent[size];
        }

        public String getLogOwnerId() {
            return logOwnerId;
        }

        public void setLogOwnerId(String logOwnerId) {
            appendEvents();
            this.logOwnerId = logOwnerId;
        }

        public String getLogName() {
            return logName;
        }

        public void setLogName(String logName) {
            appendEvents();
            this.logName = logName;
        }

        public String getLogId() {
            return logId;
        }

        public void setLogId(String logId) {
            this.logId = logId;
        }

        public String getLogEndpoint() {
            return (String) ((Stub) log)._getProperty(Stub.ENDPOINT_ADDRESS_PROPERTY);
        }

        public void setLogEndpoint(String logEndpoint) {
            appendEvents();
            ((Stub) log)._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, logEndpoint);
        }

        public boolean isAppendLocation() {
            return appendLocation;
        }

        public void setAppendLocation(boolean appendLocation) {
            appendEvents();
            this.appendLocation = appendLocation;
        }

        public boolean requiresLayout() {
            return false;
        }

        public void close() {
            appendEvents();
        }

        protected void append(LoggingEvent loggingEvent) {
            if (nextEvent == buffer.length) {
                appendEvents();
                nextEvent = 0;
            }
            buffer[nextEvent++] = loggingEvent;
        }

        // Private interface

        private void appendEvents() {
            try {
                if (logId == null) {
                    logId = log.createLog(new Log(null, logOwnerId, logName, System.currentTimeMillis())).getId();
                }
                Event[] events = new Event[nextEvent];
                for (int i = 0; i < events.length; i++) {
                    events[i] = parseEvent(buffer[i]);
                }
                log.appendEvents(new EventList(events));
            } catch (RemoteException e) {
                errorHandler.error("Failed to append events", e, ErrorCode.GENERIC_FAILURE);
            }

        }

        private Event parseEvent(LoggingEvent loggingEvent) {
            Throwable throwable = null;
            ThrowableInformation throwableInfo = loggingEvent.getThrowableInformation();
            if (throwableInfo != null) {
                throwable = parseThrowable(throwableInfo);
            }
            Location location = null;
            if (appendLocation) {
                location = parseLocation(loggingEvent.getLocationInformation());
            }
            return new Event(null, logId, loggingEvent.getLoggerName(), loggingEvent.getLevel().toString(), loggingEvent.getThreadName(), loggingEvent.timeStamp, loggingEvent.getRenderedMessage(), loggingEvent.getNDC(), throwable, location);
        }

        private Throwable parseThrowable(ThrowableInformation throwableInfo) {
            java.lang.Throwable throwable = throwableInfo.getThrowable();
            StringBuffer buffer = new StringBuffer();
            String[] detail = throwableInfo.getThrowableStrRep();
            for (int i = 0; i < detail.length; i++) {
                buffer.append(detail[i]);
            }
            return new Throwable(throwable.getClass().getName(), throwable.getMessage(), buffer.toString());
        }

        private Location parseLocation(LocationInfo locationInfo) {
            Integer lineNumber;
            try {
                lineNumber = new Integer(locationInfo.getLineNumber());
            } catch (NumberFormatException e) {
                lineNumber = new Integer(0);
            }
            return new Location(locationInfo.getClassName(), locationInfo.getMethodName(), locationInfo.getFileName(), lineNumber);
        }

    }

}