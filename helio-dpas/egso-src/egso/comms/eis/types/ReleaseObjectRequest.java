/*
 * Created on Jul 13, 2004
 */
package org.egso.comms.eis.types;

/**
 * Class representing <code>object.release</code> requests.
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 */
public class ReleaseObjectRequest implements RequestSubtype {

    private String objectId = null;

    public ReleaseObjectRequest(String objectId) {
        this.objectId = objectId;
    }

    public ReleaseObjectRequest() {
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

}