package eu.heliovo.dpas.ch.sources;

import java.util.Calendar;
import java.util.TimeZone;

import eu.heliovo.dpas.ch.HTTPFileListProvider;
import eu.heliovo.dpas.ch.ResultItem;

/**
 * Data provider for Phoenix II. It returns two FITS files.
 * 
 * Check the documentation of {@link HTTPFileListProvider} for more details about
 * the methods.
 * 
 * @author Simon Felix (de@iru.ch)
 */
public class SolarMonitorSEIT extends HTTPFileListProvider
{
  @Override
  public ResultItem getData(String path)
  {
    String filename=path.substring(path.lastIndexOf("/")+1);
    
    if(filename.length()!="seit_00171_fd_20070213_185828.png".length())
      return null;
    
    if(!filename.startsWith("seit_00171_fd_") || !filename.endsWith(".png"))
      return null;
    
    
    //parse the time & date of the file
    Calendar fileTime=Calendar.getInstance();
    fileTime.setTimeZone(TimeZone.getTimeZone("GMT"));
    fileTime.set(Calendar.MILLISECOND,0);
    
    fileTime.set(Calendar.YEAR,Integer.parseInt(filename.substring(14,18)));
    fileTime.set(Calendar.MONTH,Integer.parseInt(filename.substring(18,20))-1); //january == 0 in java
    fileTime.set(Calendar.DAY_OF_MONTH,Integer.parseInt(filename.substring(20,22)));
    fileTime.set(Calendar.HOUR_OF_DAY,Integer.parseInt(filename.substring(23,25)));
    fileTime.set(Calendar.MINUTE,Integer.parseInt(filename.substring(25,27)));
    fileTime.set(Calendar.SECOND,Integer.parseInt(filename.substring(27,29)));
      
    ResultItem result=new ResultItem();
    
    result.measurementStart=fileTime;
    result.urlPreview=path;
    result.urlPreviewThumb=String.format("http://solarmonitor.org/data/%04d%02d%02d/pngs/thmb/seit_00171_thumb.png",
        fileTime.get(Calendar.YEAR),
        fileTime.get(Calendar.MONTH)+1, //january==0 in java
        fileTime.get(Calendar.DAY_OF_MONTH));
    
    return result;
  }

  @Override
  public String getFileListPath(Calendar _start)
  {
    return String.format("http://solarmonitor.org/data/%04d%02d%02d/pngs/seit/",
        _start.get(Calendar.YEAR),
        _start.get(Calendar.MONTH)+1, //january==0 in java
        _start.get(Calendar.DAY_OF_MONTH));
  }

  @Override
  public int getFileListPer()
  {
    return Calendar.DAY_OF_MONTH;
  }
}
