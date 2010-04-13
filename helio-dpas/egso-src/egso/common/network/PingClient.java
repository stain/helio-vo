package org.egso.common.network;

import org.egso.common.externalinteraction.InteractionManager;
import org.egso.common.externalinteraction.InteractionManagerFactory;
import org.egso.common.services.network.Ping;
import org.egso.comms.eis.adapter.Session;

/**
 * ECI Application level Ping Client.
 * @author Marco Soldati
 */
public class PingClient
{
    private InteractionManager interactionManager = null;
    
    private PingStatus pingStatus = null;
        
    /**
     *  Setup the PingClientImpl
     */
    public PingClient()
    {
        initialize();
    }
        
    /**
     * init the component
     * @see org.apache.avalon.framework.activity.Initializable#initialize()
     */
    private void initialize()
    {
        pingStatus = new PingStatus();
        pingStatus.setStart(System.currentTimeMillis());    
        interactionManager = InteractionManagerFactory.getInteractionManager();       
    }
    
    /**
     * Try to ping a recipient and return the result.
     * Open a session, call the ping command and close the session again.
     * @param sender ECI name of the sender of the ping request.
     * @param recipient ECI name of the server to ping.
     * @return the result string of the pinged server.
     */
    public String ping(String sender, String recipient) throws Exception
    {
        // get a Session
        pingStatus.setStartSession(System.currentTimeMillis());
        Session session = interactionManager.getSession("anonymous", null, recipient);
        
        try
        {
            pingStatus.setStartPingObject(System.currentTimeMillis());
            Ping ping = (Ping)session.getObject(Ping.class);
            
            
            pingStatus.setStartPing(System.currentTimeMillis());            
            String response = ping.ping(sender);
            
            return response;
        }
        finally
        {
            pingStatus.setStartCleanup(System.currentTimeMillis());
            if (session != null)
                session.close();
                interactionManager.releaseSession(session);
            pingStatus.setEnd(System.currentTimeMillis());
        }
    }

    /**
     * return a status object about the ping command.
     * @see org.egso.common.network.PingClient#getPingTiming()
     */
    public String getPingTiming()
    {
        return pingStatus.toString();
    }
}

/**
 * Simple status object that holds additional status information for the ping command.
 * @author Marco Soldati
 * @created 23.09.2004
 */
class PingStatus
{
    private long start = 0;
    
    private long startSession = 0;
        
    private long startPingObject = 0;
    
    private long startPing = 0;
    
    private long startCleanup = 0;
    
    private long end = 0;
    
    /**
     * default constructor. 
     */
    public PingStatus()
    {
        super();
    }
    
    /**
     * @return Returns the end.
     */
    public long getEnd()
    {
        return end;
    }
    /**
     * @param end The end to set.
     */
    public void setEnd(long end)
    {
        this.end = end;
    }
    /**
     * @return Returns the startCleanup.
     */
    public long getStartCleanup()
    {
        return startCleanup;
    }
    /**
     * @param startCleanup The startCleanup to set.
     */
    public void setStartCleanup(long endPing)
    {
        this.startCleanup = endPing;
    }
    /**
     * @return Returns the start.
     */
    public long getStart()
    {
        return start;
    }
    /**
     * @param start The start to set.
     */
    public void setStart(long start)
    {
        this.start = start;
    }
    /**
     * @return Returns the startPing.
     */
    public long getStartPing()
    {
        return startPing;
    }
    /**
     * @param startPing The startPing to set.
     */
    public void setStartPing(long startPing)
    {
        this.startPing = startPing;
    }
    /**
     * @return Returns the startSession.
     */
    public long getStartSession()
    {
        return startSession;
    }
    /**
     * @param startSession The startSession to set.
     */
    public void setStartSession(long startSession)
    {
        this.startSession = startSession;
    }
    /**
     * @return Returns the startPingObject.
     */
    public long getStartPingObject()
    {
        return startPingObject;
    }
    /**
     * @param startPingObject The startPingObject to set.
     */
    public void setStartPingObject(long startPingObject)
    {
        this.startPingObject = startPingObject;
    }
    
    public String toString()
    {
        if (end > 0)
        {
            StringBuffer sb = new StringBuffer();
            sb.append("initialize: ").append(startSession - start).append("ms, ");
            sb.append("get session: ").append(startPingObject - startSession).append("ms, ");
            sb.append("get ping obj: ").append(startPing - startPingObject).append("ms, ");
            sb.append("do ping: ").append(startCleanup - startPing).append("ms, ");            
            sb.append("cleanup: ").append(end - startCleanup).append("ms, ");
            sb.append("total: ").append(end - start).append("ms");
            return sb.toString();
        }
        else
            return "pinging";
    }
}
