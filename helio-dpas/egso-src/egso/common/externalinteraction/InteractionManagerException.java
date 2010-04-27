package org.egso.common.externalinteraction;

/**
 *  Interaction Manager Exception
 *
 * @author     Marco Soldati
 * @created    4. Juni 2003
 * @version    1.0
 */

@SuppressWarnings("serial")
public class InteractionManagerException extends Exception
{

  /**
   *  Constructor for the InteractionManagerException object
   *
   * @param  detail  Detailed Message
   */
  public InteractionManagerException(String detail)
  {
    super(detail);
  }


  /**
   *  Constructor for the InteractionManagerException object
   *
   * @param  detail  Detailed Message
   * @param  ex      Exception that was has to be cascaded
   */
  public InteractionManagerException(String detail, Throwable ex)
  {
    super(detail, ex);
  }
}

