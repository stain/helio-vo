package org.egso.common.externalinteraction;

import java.net.URI;
import org.egso.comms.eis.adapter.*;
import org.egso.comms.nds.types.ApplicationList;

/**
 *  Adapter for the InteractionMediator.
 *
 * @author     Marco Soldati
 * @created    9. Februar 2004
 * @version    $id$
 */
public interface InteractionMediatorAdapter
{
  // ************************* client side methods *****************************
  /**
   *  Returns a Session with the specified partner.
   *
   * @param  partner        the partner to set up a session with
   * @return                the Session
   * @exception  Exception  If anything gies wrong
   */
  public Session createSession(String partner)
    throws Exception;
  
  /**
   * Dynamically lookup Applications of a certain type
   * @param type urn of the application
   * @return List of Applications
   * @throws InteractionManagerException
   */
  public ApplicationList selectApplicationByType(URI type) 
      throws InteractionManagerException;
    
}

