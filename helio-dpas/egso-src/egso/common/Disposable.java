package org.egso.common;

/**
 * Mark a class as destroyable.
 * @author Marco Soldati
 * @created 21.12.2004
 */
public interface Disposable
{
    /**
     * release any resources.
     * @throws Exception
     */
    public void dispose();
}
