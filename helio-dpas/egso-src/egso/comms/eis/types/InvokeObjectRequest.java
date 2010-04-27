/*
 * Created on Jul 13, 2004
 */
package org.egso.comms.eis.types;

import java.util.List;

/**
 * Class representing a <code>object.invoke</code> request.
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 * @see java.lang.Class#getDeclaredMethod(java.lang.String, java.lang.Class[])
 * @see java.lang.reflect.Method#invoke(java.lang.Object, java.lang.Object[])
 */
public class InvokeObjectRequest implements RequestSubtype {

    private String objectId = null;

    private String methodName = null;

    private List<Parameter> inputParams = null;

    public InvokeObjectRequest() {
    }

    public InvokeObjectRequest(String objectId, String methodName, List<Parameter> inputParams) {
        this.objectId = objectId;
        this.methodName = methodName;
        this.inputParams = inputParams;
    }

    public List<Parameter> getInputParams() {
        return inputParams;
    }

    public void setInputParams(List<Parameter> inputParams) {
        this.inputParams = inputParams;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

}