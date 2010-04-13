/*
 * Created on Jul 4, 2004
 */
package org.egso.comms.eis.types;

import javax.xml.soap.SOAPElement;

import org.egso.comms.eis.adapter.AdapterException;

/**
 * Class representing responses.
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 */
public class Response implements FormalMessage {

    private String requestId = null;

    private String clientId = null;

    private String serverId = null;

    private AdapterException exception = null;

    private SOAPElement subtype = null;

    public Response(String requestId, String clientId, String serverId, AdapterException exception, SOAPElement subtype) {
        this.requestId = requestId;
        this.clientId = clientId;
        this.serverId = serverId;
        this.exception = exception;
        this.subtype = subtype;
    }

    public Response() {
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

    public AdapterException getException() {
        return exception;
    }

    public void setException(AdapterException exception) {
        this.exception = exception;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

}