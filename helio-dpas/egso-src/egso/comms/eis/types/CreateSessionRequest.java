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
public class CreateSessionRequest implements RequestSubtype {

    private String clientId = null;

    public CreateSessionRequest() {
    }

    public CreateSessionRequest(String clientId) {
        this.clientId = clientId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

}