/*
 * Created on Jul 13, 2004
 */
package org.egso.comms.eis.types;

/**
 * Class representing a <code>createSession</code> response.
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 * @see org.egso.comms.eis.adapter.InteractionMediator#createSession(String)
 */
public class CreateSessionResponse implements ResponseSubtype {

    private String sessionId = null;

    public CreateSessionResponse() {
    }

    public CreateSessionResponse(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

}