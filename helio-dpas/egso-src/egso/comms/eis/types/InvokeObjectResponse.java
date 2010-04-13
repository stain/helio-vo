/*
 * Created on Jul 13, 2004
 */
package org.egso.comms.eis.types;

/**
 * Class representing a <code>object.invoke</code> response.
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 * @see java.lang.Class#getDeclaredMethod(java.lang.String, java.lang.Class[])
 * @see java.lang.reflect.Method#invoke(java.lang.Object, java.lang.Object[])
 */
public class InvokeObjectResponse implements ResponseSubtype {

    private Parameter outputParam = null;

    public InvokeObjectResponse() {
    }

    public InvokeObjectResponse(Parameter outputParam) {
        this.outputParam = outputParam;
    }

    public Parameter getOutputParam() {
        return outputParam;
    }

    public void setOutputParam(Parameter outputParam) {
        this.outputParam = outputParam;
    }

}