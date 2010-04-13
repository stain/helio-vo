package eu.heliovo.dpas.ch.sources;

import java.io.*;
import java.net.*;
import java.util.*;

import eu.heliovo.dpas.ch.DataProvider;
import eu.heliovo.dpas.ch.ResultItem;
import eu.heliovo.dpas.ch.sources.*;

/***
 * Data provider for the RHESSI flare event catalogue.
 * 
 * It downloads http://www.hedc.ethz.ch/data/dbase/hessi_flare_list.txt, parses it
 * and then returns sublists from the cached list.
 * 
 * @author Simon Felix (de@iru.ch)
 */
public class HessiEC implements DataProvider
{
  private static String HESSI_FLARE_LIST_URL="http://hesperia.gsfc.nasa.gov/hessidata/dbase/hessi_flare_list.txt";
  
  /**
   * The age of the data in milliseconds (from System.currentTimeMillis())
   */
  private long lastUpdate;
  
  /**
   * The maximum age of the data in days, by default a week
   */
  private int cacheDuration=7;
  
  /**
   * This list contains ALL events.
   * 
   * It is used as a cache to speed up requests. It will be filled when
   * the request is done.
   */
  private LinkedList<ResultItem> events=new LinkedList<ResultItem>();
  
  /**
   * Set the maximum age of the cache in days
   * 
   * @param _days
   */
  public void setCacheDuration(int _days)
  {
    cacheDuration=_days;
  }
  
  /**
   * Downloads hessi_flare_list.txt and parses the file.
   * 
   * @throws Exception
   */
  private synchronized void downloadAndParseFlareList() throws Exception
  {
    //open a connection to the file
    final URLConnection c=new URL(HESSI_FLARE_LIST_URL).openConnection();
    BufferedReader in=new BufferedReader(new InputStreamReader(c.getInputStream()));
    
    //empty the in-memory list
    events.clear();
    
    //read the text file line by line
    String line;
    
    //read over header
    for(int i=0;i<6;i++)
      in.readLine();
    
    while((line=in.readLine())!=null)
    {
      //check if the current line matches the format
      if(line.length()>=20)
      {
        //if yes, split the line by spaces
        String[] items=line.trim().split("\\s+");
        
        //check if the current line contains at least 13 space-separated items 
        if(items.length>=13)
          try
          {
            //if everything's ok, let's parse the line and store the result in a ResultItem-object
            ResultItem ri=new ResultItem();
            ri.flareNr=Integer.parseInt(items[0]);
            ri.measurementStart=parseDate(items[1],items[2]);
            ri.measurementEnd=parseDate(items[1],items[4]);
            ri.measurementPeak=parseDate(items[1],items[3]);
            ri.peakCS=Integer.parseInt(items[6]);
            ri.totalCounts=Integer.parseInt(items[7]);
            ri.energyKeVFrom=Integer.parseInt(items[8].split("\\-")[0]);
            ri.energyKeVTo=Integer.parseInt(items[8].split("\\-")[1]);
            ri.xPos=Integer.parseInt(items[9]);
            ri.yPos=Integer.parseInt(items[10]);
            ri.radial=Integer.parseInt(items[11]);
            ri.AR=Integer.parseInt(items[12]);
            events.add(ri);
          }
          catch(NumberFormatException _nfe)
          {
            //couldn't parse some numbers --> invalid line
          }
      }
    }
    
    //close the connection afterwards
    in.close();
    
    //using linear search later on, therefore sorting is not neccessary
    /*Collections.sort(events,new Comparator<ResultItem>(){
      public int compare(ResultItem _a,ResultItem _b)
      {
        return _a.measurementStart.compareTo(_b.measurementStart);
      }});*/
    
    //store the age of the in-memory list
    lastUpdate=System.currentTimeMillis();
  }
  
  /**
   * This method parses the a date/time-string from the list and creates a {@link java.util.Calendar}
   * object from it.
   * 
   * The date format is as follows: 3-Mar-2002
   * The time format is as follows: 17:59:59
   * 
   * @param _day
   * @param _time
   * @return The {@link java.util.Calendar} or null if parsing failed
   */
  private Calendar parseDate(String _day,String _time)
  {
    //split the date parts into day, month and year
    String[] dateParts=_day.split("\\-");
    
    //validate that the date-part consits of three parts separated by a -
    if(dateParts.length!=3)
      return null;
    
    //create a calendar object
    Calendar c=Calendar.getInstance();
    c.set(
          Integer.parseInt(dateParts[2]),
          0,
          Integer.parseInt(dateParts[0]),
          Integer.parseInt(_time.substring(0,2)),
          Integer.parseInt(_time.substring(3,5)),
          Integer.parseInt(_time.substring(6,8))
        );
    c.set(Calendar.MILLISECOND,0);
    c.setTimeZone(TimeZone.getTimeZone("GMT"));
    
    //fill in the month (conversion from "Jan" to Calendar.JANUARY)
    if(dateParts[1].equals("Jan"))
      c.set(Calendar.MONTH,Calendar.JANUARY);
    if(dateParts[1].equals("Feb"))
      c.set(Calendar.MONTH,Calendar.FEBRUARY);
    if(dateParts[1].equals("Mar"))
      c.set(Calendar.MONTH,Calendar.MARCH);
    if(dateParts[1].equals("Apr"))
      c.set(Calendar.MONTH,Calendar.APRIL);
    if(dateParts[1].equals("May"))
      c.set(Calendar.MONTH,Calendar.MAY);
    if(dateParts[1].equals("Jun"))
      c.set(Calendar.MONTH,Calendar.JUNE);
    if(dateParts[1].equals("Jul"))
      c.set(Calendar.MONTH,Calendar.JULY);
    if(dateParts[1].equals("Aug"))
      c.set(Calendar.MONTH,Calendar.AUGUST);
    if(dateParts[1].equals("Sep"))
      c.set(Calendar.MONTH,Calendar.SEPTEMBER);
    if(dateParts[1].equals("Oct"))
      c.set(Calendar.MONTH,Calendar.OCTOBER);
    if(dateParts[1].equals("Nov"))
      c.set(Calendar.MONTH,Calendar.NOVEMBER);
    if(dateParts[1].equals("Dec"))
      c.set(Calendar.MONTH,Calendar.DECEMBER);
    
    return c;
  }
  
  /**
   * Checks if the in-memory copy of the flare list is out of date.
   * 
   * @return true if the in-memory list is out of date
   */
  private boolean flareListNeedsUpdate()
  {
    if(events.size()<10)
      return true;
    
    return System.currentTimeMillis()>lastUpdate+1000l*60*60*24*cacheDuration;
  }
  
  /**
   * Returns a sublist from the RHESSI flare event catalogue.
   * 
   * The first request can take some time as the service first creates an
   * in-memory copy of the 6+ MB list.
   */
  public List<ResultItem> query(Calendar dateFrom,Calendar dateTo,int maxResults) throws Exception
  {
    //download the flare list if it is out of date
    //this has to be synchronized so that two concurrent (first) requests won't
    //trigger two downloads
    synchronized(this)
    {
      if(flareListNeedsUpdate())
        downloadAndParseFlareList();
      
      for(int retries=0;retries<3;retries++)
        if(events.size()<10)
        {
          Thread.sleep(2000);
          downloadAndParseFlareList();
        }
    }
    
    // linear search trough the complete list, should be fast enough
    LinkedList<ResultItem> results=new LinkedList<ResultItem>();
    for(ResultItem ri:events)
      if(ri.measurementEnd.after(dateFrom) && ri.measurementStart.before(dateTo))
      {
        results.add(ri);
        if(results.size()>maxResults)
          break;
      }
    
    //return results
    return results;
  }
  
}
