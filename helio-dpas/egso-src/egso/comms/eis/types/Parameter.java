/*
 * Created on Sep 21, 2004
 */
package org.egso.comms.eis.types;

/**
 * Class representing <code>InvokeObjectRequest</code> and
 * <code>InvokeObjectResponse</code> parameters.
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 * @see org.egso.comms.eis.types.InvokeObjectRequest
 * @see org.egso.comms.eis.types.InvokeObjectResponse
 */
public class Parameter {

    private Class type = null;

    private Object value = null;

    public Parameter() {
    }

    public Parameter(Class type, Object value) {
        this.type = type;
        this.value = value;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

}