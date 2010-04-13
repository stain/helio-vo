package org.egso.common.utils.uniqueid;

/**
 *  Util class to generate uniqueIds based on the current system time. The
 *  methods make sure that the id is unique for the current JVM.
 *
 * @author     Marco Soldati
 * @created    10. November 2003
 * @version    $id$
 */
public class UniqueId
{
  private static long lastId = 0;


  /**
   *  Gets a uniqueID based on the current system time. The method makes sure
   *  that the id is unique for the current JVM. <br>
   *
   *
   * @return    A new unique String value
   */
  public static String getUniqueId()
  {
    // get the last part of the current system time
    long id = System.currentTimeMillis();
    if(lastId >= id)
    {
      lastId++;
      id = lastId;
    }
    else
    {
      lastId = id;
    }

    // convert to String
    String s = Long.toHexString(id);
    return s;
  }


  /**
   *  Make sure the id is of a certain length.
   *
   * @param  len                           Length of the Parameter (8&nbsp;
   *      <=&nbsp;len&nbsp;<=&nbsp;16)
   * @return                               The uniqueId value of a certain
   *      length
   * @exception  IllegalArgumentException  If len is not 8&nbsp;
   *      <=&nbsp;len&nbsp;<=&nbsp;16
   */
  public static String getUniqueId(int len)
    throws IllegalArgumentException
  {
    if(len < 8 || len > 16)
    {
      throw new IllegalArgumentException("Illegal value: len=" + len + " (8 <= len <= 16)");
    }
    String s = getUniqueId();

    int l = s.length();
    if(l < len)
    {
      return "0000000000000000".substring(len - l) + s;
    }
    else
    {
      return s.substring(l - len);
    }
  }
}

