/*
 * Created on Feb 23, 2004
 */
package org.egso.comms.pis.types;

/**
 * Enumeration class for message status codes.
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 */
public class MessageStatus {

    public static final int UNDEFINED = 0;
    
    public static final int NDS_UNAVAILABLE = 1;

    public static final int RECIPIENT_PIS_NOT_FOUND = 2;

    public static final int RECIPIENT_PIS_UNAVAILABLE = 3;

    public static final int SENDER_PIS_NOT_FOUND = 4;

    public static final int SENDER_PIS_UNAVAILABLE = 5;

    public static final int RECIPIENT_NOT_FOUND = 6;

    public static final int RECIPIENT_UNAVAILABLE = 7;

    public static final int SENDER_NOT_FOUND = 8;

    public static final int SENDER_UNAVAILABLE = 9;

    public static final int UNKNOWN_PROTOCOL = 10;
    
    public static String getString(int code) {
        switch (code) {
        case UNDEFINED:
            return "Undefined";
        case NDS_UNAVAILABLE:
            return "NDS unavailable";
        case RECIPIENT_PIS_NOT_FOUND:
            return "NDS failed to resolve parent PIS for recipient";
        case RECIPIENT_PIS_UNAVAILABLE:
            return "Parent PIS for recipient unavailable";
        case SENDER_PIS_NOT_FOUND:
            return "NDS failed to resolve parent PIS for sender";
        case SENDER_PIS_UNAVAILABLE:
            return "Parent PIS unavailable for sender";
        case RECIPIENT_NOT_FOUND:
            return "NDS failed to resolve recipient";
        case RECIPIENT_UNAVAILABLE:
            return "Recipient unavailable";
        case SENDER_NOT_FOUND:
            return "NDS failed to resolve sender";
        case SENDER_UNAVAILABLE:
            return "Sender unavailable";
        case UNKNOWN_PROTOCOL:
            return "Protocol unknown";
        default:
            return null;
        }
    }

}