/*
 * Created on Jul 13, 2004
 */
package org.egso.comms.eis.types;

/**
 * Class representing <code>session.isValid</code> responses.
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 * @see org.egso.comms.eis.adapter.Session#isValid()
 */
public class ValidateSessionResponse implements ResponseSubtype {

    private boolean valid = false;

    public ValidateSessionResponse() {
    }

    public ValidateSessionResponse(boolean valid) {
        this.valid = valid;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

}