/*
 * Created on Dec 2, 2004
 */
package org.egso.comms.eis.types;

import java.util.List;

/**
 * Serializer for <code>GetTypesRequest</code> objects.
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 * @see org.egso.comms.eis.adapter.Session#getTypes()
 * @see org.egso.comms.eis.adapter.Session#getTypes(Class)
 */
public class GetTypesResponse implements ResponseSubtype {

    private List types = null;

    public GetTypesResponse() {
    }
    
    public GetTypesResponse(List types) {
        this.types = types;
    }
    
    public List getTypes() {
        return types;
    }

    public void setTypes(List types) {
        this.types = types;
    }

}