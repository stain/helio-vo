package eu.heliovo.dpas.ch;

import java.util.*;

/**
 * Central dispatcher for queries. A user can query a single instrument at once for
 * a time period.
 * 
 * The QueryService contains a list of {@link DataProvider}s which will be called to
 * query an instrument.
 * 
 * @author Simon Felix (de@iru.ch)
 */
public class QueryService
{
  /**
   * The list of known instruments (key=instrument name, value=data provider object)
   */
  private Map<String,DataProvider> dataProviders;
  
  /**
   * Can be used to supply a list of instrument names and accompanying data provider objects.
   * This method is usually called by the Spring container (the configuration is done
   * in the applicationContext.xml) 
   * 
   * @param _dataProviders Map of data providers (Key=Instrument name, Value={@link DataProvider} object)
   */
  public void setDataProviders(Map<String,DataProvider> _dataProviders)
  {
    dataProviders=_dataProviders;
  }
  
  /**
   * Returns a list of all known instruments
   * 
   * @return List of strings
   */
  public String[] getInstruments()
  {
    return dataProviders.keySet().toArray(new String[0]);
  }
  
  /**
   * Queries a known instrument. All results will be sorted by date (ascending).
   * 
   * @param _instrument Name of the instrument
   * @param _dateFrom Beginning of time period to search (2003-02-31 23:59:59)
   * @param _dateTo End of time period to search (2003-02-31 23:59:59)
   * @param _maxResults Maximum number of results
   * @return
   * @throws Exception
   */
  public ResultItem[] query(String _instrument,String _dateFrom,String _dateTo,int _maxResults) throws Exception
  {
    if(_dateFrom.length()!="2001.01.01 00:00:00".length())
      throw new RuntimeException("Invalid time range (dateFrom)");
    if(_dateTo.length()!="2001.01.01 00:00:00".length())
      throw new RuntimeException("Invalid time range (dateTo)");
    
    final Calendar from=Calendar.getInstance();
    from.set(
          Integer.parseInt(_dateFrom.substring(0,4)),
          Integer.parseInt(_dateFrom.substring(5,7))-1,
          Integer.parseInt(_dateFrom.substring(8,10)),
          Integer.parseInt(_dateFrom.substring(11,13)),
          Integer.parseInt(_dateFrom.substring(14,16)),
          Integer.parseInt(_dateFrom.substring(17,19))
        );
    from.set(Calendar.MILLISECOND,0);
    from.setTimeZone(TimeZone.getTimeZone("GMT"));

    final Calendar to=Calendar.getInstance();
    to.set(
        Integer.parseInt(_dateTo.substring(0,4)),
        Integer.parseInt(_dateTo.substring(5,7))-1,
        Integer.parseInt(_dateTo.substring(8,10)),
        Integer.parseInt(_dateTo.substring(11,13)),
        Integer.parseInt(_dateTo.substring(14,16)),
        Integer.parseInt(_dateTo.substring(17,19))
      );
    to.set(Calendar.MILLISECOND,0);
    to.setTimeZone(TimeZone.getTimeZone("GMT"));
    
    return query(_instrument,from,to,_maxResults);
  }
  
  /**
   * Queries a known instrument. All results will be sorted by date (ascending).
   * 
   * @param _instrument Name of the instrument
   * @param _dateFrom Beginning of time period to search
   * @param _dateTo End of time period to search
   * @param _maxResults Maximum number of results
   * @return
   * @throws Exception
   */
  public ResultItem[] query(String _instrument,Calendar _from,Calendar _to,int _maxResults) throws Exception
  {
    /*
     * checking parameters
     */
    if(!dataProviders.containsKey(_instrument))
      throw new RuntimeException("Instrument \""+_instrument+"\" not found.");
    
    if(_maxResults<=0)
      _maxResults=Integer.MAX_VALUE;
    
    if(_from.after(_to))
      throw new RuntimeException("Invalid time range (from > to)");
    
    if(_to.getTimeInMillis()-_from.getTimeInMillis()>1000l*60*60*24*365*5)
      throw new RuntimeException("Invalid time range (>5 years)");
    
    /*
     * querying the instrument
     */
    List<ResultItem> results=dataProviders.get(_instrument).query(_from,_to,_maxResults);
    
    //sort the results
    Collections.sort(results,new Comparator<ResultItem>(){
      public int compare(ResultItem _a,ResultItem _b)
      {
        return _a.measurementStart.compareTo(_b.measurementStart);
      }});
    
    //if more results were returned than the specified maximum, remove the rest
    if(results.size()>_maxResults)
      results.subList(_maxResults,results.size()).clear();
    
    //return the results
    return results.toArray(new ResultItem[0]);
  }
}
