/*
 * Created on Jul 6, 2004
 */
package org.egso.comms.eis.adapter;

import org.egso.comms.eis.types.Request;
import org.egso.comms.eis.types.Response;

/**
 * Base <code>RequestHandler</code> class.
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 */
public class RequestHandlerBase implements RequestHandler {

    /*
     * (non-Javadoc)
     * 
     * @see org.egso.comms.eis.adapter.RequestHandler#handleRequest(org.egso.comms.eis.types.Request,
     * org.egso.comms.eis.types.Response)
     */
    public void handleRequest(Request request, Response response) throws RequestHandlerException {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.egso.comms.eis.adapter.RequestHandler#handleOneWayRequest(org.egso.comms.eis.types.Request)
     */
    public void handleOneWayRequest(Request request) {
    }

}