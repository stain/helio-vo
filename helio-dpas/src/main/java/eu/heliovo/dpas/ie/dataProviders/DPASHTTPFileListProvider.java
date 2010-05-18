package eu.heliovo.dpas.ie.dataProviders;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit.Parser;
import javax.swing.text.html.HTMLEditorKit.ParserCallback;

import eu.heliovo.dpas.ch.HTMLParserFactory;
import eu.heliovo.dpas.ie.internalData.DPASResultItem;

/**
 * A generic class to query instruments that supply a list of files in HTML format (called
 * "directory page"). Actual implementations of data providers should inherit from this
 * class and implement the abstract methods.
 * 
 * @author Simon Felix (de@iru.ch)
 */
public abstract class DPASHTTPFileListProvider implements DPASDataProvider
{
  /**
   * The class will cache results for the specified number of days, by default one week.
   * It'll check for new results every week.
   */
  private int cacheDuration=7;
  
  public void setCacheDuration(int _days)
  {
    cacheDuration=_days;
  }
  
  /**
   * An entry of the cache, consisting of the System.currentTimeMillis()-time when the
   * result was obtained and a list of results for a complete day/hour/..., depending
   * on getFileListPer().
   * 
   * @author Simon Felix (de@iru.ch)
   */
  class CacheEntry
  {
    long requestSent;
    List<DPASResultItem> results;
  }
  
  /**
   * A cache of results. The key is the Calendar.getTimeInMillis()-value of
   * the beginning of a period and the value is a CacheEntry object containing
   * the actual results and some meta-information about the cache entry.
   */
  HashMap<Long,CacheEntry> cache=new HashMap<Long,CacheEntry>();
  
  
  /**
   * Request a directory page for every <i>n</i>.
   * 
   * Usually an archive is organized with one directory page per day/month/year.
   * Return Calendar.DAY_OF_MONTH, Calendar.HOUR, Calendar.YEAR, ... to configure the
   * HTTPFileListProvider accordingly.
   * 
   * @return A field from {@link java.util.Calendar}
   */
  public abstract int getFileListPer();
  
  /**
   * Creates the URL of a directory page of the specified date.
   * 
   * @param _start This date/time should be contained in the page.
   * @return An URL pointing to the directory page
   */
  public abstract String getFileListPath(Calendar _start);
  
  /**
   * Creates a ResultItem from a single link. This method will
   * be called numerous times per directory page, once for every link.
   * 
   * The method should check if the provided URL is valid. In the case
   * of invalid URLs the method should return NULL. If the result lies
   * outside the given time period it should return NULL.
   * 
   * @param _path URL of a link from the directory page
   * @param _dateFrom Beginning of the searched time period
   * @param _dateTo End of the searched time period
   * @return A result or NULL if no result can be created
   */
  public abstract DPASResultItem getData(String _path);
  
  /**
   * Queries an instrument. This method gets all directory pages of the supplied
   * time period and parses the links.
   * 
   * @param _dateFrom Beginning of the time period to search
   * @param _dateTo End of the time period to search
   * @param _maxResults Will not return many more results than this
   * @return A list of the results
   */
  public List<DPASResultItem> query(final Calendar _dateFrom,final Calendar _dateTo,int _maxResults) throws Exception
  {
    //the results of this method are collected in this list
    final LinkedList<DPASResultItem> results=new LinkedList<DPASResultItem>();
    
    //start the iteration over the time range at a getFileListPer()-boundary
    final Calendar currentDay=(Calendar)_dateFrom.clone();
    switch(getFileListPer())
    {
      case Calendar.YEAR:
        currentDay.set(Calendar.MONTH,0);
      case Calendar.MONTH:
        currentDay.set(Calendar.DAY_OF_MONTH,1);
      case Calendar.DAY_OF_MONTH:
      case Calendar.DAY_OF_WEEK:
      case Calendar.DAY_OF_YEAR:
        currentDay.set(Calendar.HOUR,0);
      case Calendar.HOUR:
        currentDay.set(Calendar.MINUTE,0);
      case Calendar.MINUTE:
        currentDay.set(Calendar.SECOND,0);
      case Calendar.SECOND:
        currentDay.set(Calendar.MILLISECOND,0);
      case Calendar.MILLISECOND:
        break;
      case Calendar.WEEK_OF_MONTH:
      case Calendar.WEEK_OF_YEAR:
        throw new RuntimeException("Directory pages per week are not supported");
    }
    
    //iterate over the complete time range
    for(;currentDay.before(_dateTo);currentDay.add(getFileListPer(),1))
    {
      //check if the cache already contains results for the current time period 
      CacheEntry ce=cache.get(currentDay.getTimeInMillis());
      if(ce!=null && System.currentTimeMillis()-ce.requestSent<1000l*60*60*24*cacheDuration)
      {
        //yes, we found an entry in the cache --> use the data from the cache
      }
      else
      {
        //no, there's no entry in the cache --> fetch the data
        
        //the results of the period are collected in this list
        final LinkedList<DPASResultItem> resultsOfThisDirectoryPage=new LinkedList<DPASResultItem>();
        
        try
        {
          //read the directory index site
          final URLConnection c=new URL(getFileListPath(currentDay)).openConnection();
          BufferedReader in=new BufferedReader(new InputStreamReader(c.getInputStream()));
          
          //parse the site and collect all links. this has to be synchronized
          //because the swing html-parser is not thread safe
          synchronized(Parser.class)
          {
            Parser p=HTMLParserFactory.createParser();
            p.parse(in,new ParserCallback(){
              public void handleStartTag(HTML.Tag _t, MutableAttributeSet  _a,int _pos)
              {
                //did we find an <a> tag?
                if(_t==HTML.Tag.A)
                {
                  //look for the href attribute
                  Enumeration<?> attribs=_a.getAttributeNames();
                  while(attribs.hasMoreElements())
                  {
                    Object currentAttrib=attribs.nextElement();
                    if("href".equals(currentAttrib.toString()))
                    {
                      //we found <a href="...">, add this to the list of links
                      String filepath=null;
                      try
                      {
                        filepath=new URL(c.getURL(),(String)_a.getAttribute(currentAttrib)).toExternalForm();
                      }
                      catch(MalformedURLException e)
                      {
                        e.printStackTrace();
                      }
                      
                      //check if the referenced file has a valid file name
                      DPASResultItem result=getData(filepath);
                      
                      //it does --> we found a result
                      if(result!=null)
                      {
                        //find out end of measurement (use start instead if end is not available)
                        Calendar measurementEnd=result.measurementEnd;
                        if(measurementEnd==null)
                          measurementEnd=result.measurementStart;
                        
                        //check if measurement lies within the current day
                        Calendar nextDay=(Calendar)currentDay.clone();
                        nextDay.add(getFileListPer(),1);

                        if((!measurementEnd.before(currentDay) && measurementEnd.before(nextDay))
                            || (!result.measurementStart.before(currentDay) && result.measurementStart.before(nextDay))
                            || (result.measurementStart.before(currentDay) && !measurementEnd.before(nextDay)))
                          resultsOfThisDirectoryPage.add(result);
                      }
                    }
                  }
                }
              }
            },true);
          }
          
          //close connection
          in.close();
        }
        catch(FileNotFoundException _fnfe)
        {
          /*
           * 404 or similar error occured while accessing data. that usually
           * means that there's just no data of this period. --> ignore the
           * exception
           */
        }
        
        //cache the page for future use
        ce=new CacheEntry();
        ce.requestSent=System.currentTimeMillis();
        ce.results=resultsOfThisDirectoryPage;
        cache.put(currentDay.getTimeInMillis(),ce);
      }
      
      //add all valid results of this (maybe cached) index page to the result list
      for(DPASResultItem result:ce.results)
      {
        //find out end of measurement. if there's none provided, just use the
        //start of measurement instead
        Calendar measurementEnd=result.measurementEnd;
        if(measurementEnd==null)
          measurementEnd=result.measurementStart;
        
        //check if the result's time period overlaps the specified time period
        if((!measurementEnd.before(_dateFrom) && measurementEnd.before(_dateTo))
            || (!result.measurementStart.before(_dateFrom) && result.measurementStart.before(_dateTo))
            || (result.measurementStart.before(_dateFrom) && !measurementEnd.before(_dateTo)))
          results.add(result);
      }
      
      //abort early if we have enough results
      if(results.size()>_maxResults)
        break;
    }
    return results;
  }
}
