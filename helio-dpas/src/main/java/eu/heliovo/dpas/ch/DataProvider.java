package eu.heliovo.dpas.ch;

import java.util.Calendar;
import java.util.List;

/**
 * Interface that all data providers must implement.
 * 
 * @author de
 */
public interface DataProvider
{
  /**
   * Return a list of {@link ResultItem}s that lie in the given time period.
   * 
   * @param _dateFrom Beginning of time period to search
   * @param _dateTo End of time period to search
   * @param _maxResults Try not to return more than <i>n</i> results.
   * @return
   */
  public List<ResultItem> query(Calendar _dateFrom,Calendar _dateTo,int _maxResults) throws Exception;
}
