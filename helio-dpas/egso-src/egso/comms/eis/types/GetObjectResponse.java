/*
 * Created on Jul 13, 2004
 */
package org.egso.comms.eis.types;

/**
 * Class representing a <code>session.getObject</code> response.
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 * @see org.egso.comms.eis.adapter.Session#getObject(Class)
 * @see org.egso.comms.eis.adapter.Session#getObject(Class, Object, Class)
 */
public class GetObjectResponse implements ResponseSubtype {

    private String objectId = null;

    public GetObjectResponse() {
    }

    public GetObjectResponse(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

}