package eu.heliovo.dpas.ch;

import javax.jws.*;

import eu.heliovo.dpas.astro.ts.sec.dao.impl.SECLocator;

/**
 * The SEC (solar event caltalog) is published as a RPC/encoded (?) web service
 * which Taverna does not support. This class is a proxy to re-publish the web
 * service as document/literal. 
 * 
 * @author Simon Felix (de@iru.ch)
 */


@WebService
public class HEC
{
  /**
   * Calls the countRowns web service method
   * 
   * @param _name The value to be passed to the remote call
   * @return The return value of the remote call
   */
  @WebMethod
  public String countRows(String _name)
  {
    try
    {
      return new SECLocator().getSECwsdlPort().countRows(_name);
    }
    catch(Exception e)
    {
      throw new RuntimeException(e);
    }
  }
  
  /**
   * Calls the sql web service method
   * 
   * @param _name The value to be passed to the remote call
   * @return The return value of the remote call
   */
  @WebMethod
  public String sql(String _name)
  {
    try
    {
      return new SECLocator().getSECwsdlPort().sql(_name);
    }
    catch(Exception e)
    {
      throw new RuntimeException(e);
    }
  }

  /**
   * Calls the describeTable web service method
   * 
   * @param _name The value to be passed to the remote call
   * @return The return value of the remote call
   */
  @WebMethod
  public String describeTable(String _name)
  {
    try
    {
      return new SECLocator().getSECwsdlPort().describeTable(_name);
    }
    catch(Exception e)
    {
      throw new RuntimeException(e);
    }
  }
  
  /**
   * Calls the describeCatalogue web service method
   * 
   * @param _name The value to be passed to the remote call
   * @return The return value of the remote call
   */
  @WebMethod
  public String describeCatalogue(String _name)
  {
    try
    {
      return new SECLocator().getSECwsdlPort().describeCatalogue(_name);
    }
    catch(Exception e)
    {
      throw new RuntimeException(e);
    }
  }
}
