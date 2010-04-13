package org.egso.common.externalinteraction;

/**
 * Marks an egso service as asynchronous.
 * @author Marco Soldati
 * @created 05.09.2004
 */
public interface AsyncService
{
    /**
     * pass the notifier to the service object
     * @param notifier
     */
    public void setNotifier(Object notifier);
}
