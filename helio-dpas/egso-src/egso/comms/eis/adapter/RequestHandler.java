/*
 * Created on Jan 27, 2004
 */
package org.egso.comms.eis.adapter;

import org.egso.comms.eis.types.Request;
import org.egso.comms.eis.types.Response;

/**
 * Interface for objects that provide request handling.
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 * @see org.egso.comms.eis.adapter.RequestManager
 */
public interface RequestHandler {

    /**
     * Handles a synchronous request.
     * 
     * @param request the <code>Request</code>
     * @param response the <code>Response</code>
     * @throws RequestHandlerException
     */
    public void handleRequest(Request request, Response response) throws RequestHandlerException;

    /**
     * Handles an asynchronous request.
     * 
     * @param request the <code>Request</code>
     */
    public void handleOneWayRequest(Request request);

}