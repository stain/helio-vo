/*
 * Created on Sep 24, 2004
 */
package org.egso.comms.eis.types;

/**
 * Class representing an <code>isAlive</code> request.
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 * @see org.egso.comms.eis.adapter.InteractionMediator#isAlive(String)
 */
public class AliveRequest implements RequestSubtype {

    private String clientId = null;

    public AliveRequest() {
    }

    public AliveRequest(String clientId) {
        this.clientId = clientId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

}