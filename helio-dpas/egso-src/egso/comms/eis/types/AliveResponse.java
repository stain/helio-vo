/*
 * Created on Sep 24, 2004
 */
package org.egso.comms.eis.types;

/**
 * Class representing an <code>isAlive</code> response.
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 * @see org.egso.comms.eis.adapter.InteractionMediator#isAlive(String)
 */
public class AliveResponse implements ResponseSubtype {

    private boolean alive = false;

    public AliveResponse() {
    }

    public AliveResponse(boolean alive) {
        this.alive = alive;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

}