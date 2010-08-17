package eu.heliovo.dpas.ie.dataProviders;

import java.util.*;

import eu.heliovo.dpas.ie.internalData.DPASResultItem;

/**
 * Interface that all data providers must implement.
 * 
 * @author de
 */
public interface DPASDataProvider
{
  /**
   * Return a list of {@link DPASResultItem}s that lie in the given time period.
   * 
   * @param _dateFrom Beginning of time period to search
   * @param _dateTo End of time period to search
   * @param _maxResults Try not to return more than <i>n</i> results.
   * @return
   */
//  public 	List<DPASResultItem> query(Calendar _dateFrom,Calendar _dateTo,int _maxResults) throws Exception;
  public	List<DPASResultItem> query(String instrument, Calendar dateFrom, Calendar dateTo, int maxResults) throws Exception;
  //public	void query(String instrument, String dateFrom, String dateTo, int maxResults) throws Exception;
}
