/*
 * Created on Jul 4, 2004
 */
package org.egso.comms.eis.types;

import javax.xml.soap.SOAPElement;

/**
 * Class representing requests.
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 */
public class Request implements FormalMessage {

    private String id = null;

    private long timeout = 0l;

    private String clientId = null;

    private String serverId = null;

    private String handlerId = null;

    private boolean oneWay = false;

    private SOAPElement subtype = null;

    public Request(String id, long timeout, String clientId, String serverId, String handlerId, boolean oneWay, SOAPElement subtype) {
        this.id = id;
        this.timeout = timeout;
        this.clientId = clientId;
        this.serverId = serverId;
        this.handlerId = handlerId;
        this.oneWay = oneWay;
        this.subtype = subtype;
    }

    public Request() {
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public SOAPElement getSubtype() {
        return subtype;
    }

    public void setSubtype(SOAPElement subtype) {
        this.subtype = subtype;
    }

    public String getHandlerId() {
        return handlerId;
    }

    public void setHandlerId(String handlerId) {
        this.handlerId = handlerId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isOneWay() {
        return oneWay;
    }

    public void setOneWay(boolean oneWay) {
        this.oneWay = oneWay;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

}