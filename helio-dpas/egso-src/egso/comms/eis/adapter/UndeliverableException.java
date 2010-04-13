/*
 * Created on Jul 2, 2004
 */
package org.egso.comms.eis.adapter;

import org.egso.comms.pis.types.Header;
import org.egso.comms.pis.types.MessageStatus;

/**
 * Exception indicating a <code>Message</code> could not be
 * delivered.
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 * @see org.egso.comms.pis.types.MessageStatus
 */
@SuppressWarnings("serial")
public class UndeliverableException extends AdapterException {

    private Header header = null;

    public UndeliverableException(Header header) {
        super("Failed to complete delivery with id: " + header.getDeliveryId() + " to recipient with id: " + header.getRecipientId() + ", message status: " + MessageStatus.getString(header.getStatusCode()));
        this.header = header;
    }

    public Header getHeader() {
        return header;
    }

    public int getStatus() {
        return header.getStatusCode();
    }

}