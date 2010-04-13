/*
 * Created on Dec 2, 2004
 */
package org.egso.comms.eis.types;

/**
 * Class representing a <code>getTypes</code> request.
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 * @see org.egso.comms.eis.adapter.Session#getTypes()
 * @see org.egso.comms.eis.adapter.Session#getTypes(Class)
 */
public class GetTypesRequest implements RequestSubtype {

    private Class notifierType = null;

    public GetTypesRequest() {
    }
    
    public GetTypesRequest(Class notifierType) {
        this.notifierType = notifierType;
    }

    
    public Class getNotifierType() {
        return notifierType;
    }

    public void setNotifierType(Class notifierType) {
        this.notifierType = notifierType;
    }

}