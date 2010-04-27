package org.egso.common.externalinteraction;

/**
 * Factory to create the InteractionManager. Currently
 * it supports only the ECIInteractionManagerImpl.
 * @author Marco Soldati
 * @created 21.12.2004
 */
public class InteractionManagerFactory
{
    /**
     * 
     */
    private InteractionManagerFactory()
    {
        super();
    }
    
    /**
     * return the ECIInteractionManager singelton
     * @return
     */
    public static InteractionManager getInteractionManager()
    {
        try
        {
            return ECIInteractionManagerImpl.getInstance();
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
