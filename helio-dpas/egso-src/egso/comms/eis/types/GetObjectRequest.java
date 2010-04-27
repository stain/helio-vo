/*
 * Created on Jul 13, 2004
 */
package org.egso.comms.eis.types;

/**
 * Class representing a <code>session.getObject</code> request.
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 * @see org.egso.comms.eis.adapter.Session#getObject(Class)
 * @see org.egso.comms.eis.adapter.Session#getObject(Class, Object, Class) 
 */
public class GetObjectRequest implements RequestSubtype {

    private Class type = null;

    private String notifierId = null;

    private Class notifierType = null;

    public GetObjectRequest() {
    }

    public GetObjectRequest(Class type, String notifierId, Class notifierType) {
        this.type = type;
        this.notifierId = notifierId;
        this.notifierType = notifierType;
    }

    public String getNotifierId() {
        return notifierId;
    }

    public void setNotifierId(String notifierId) {
        this.notifierId = notifierId;
    }

    public Class getNotifierType() {
        return notifierType;
    }

    public void setNotifierType(Class notifierType) {
        this.notifierType = notifierType;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

}