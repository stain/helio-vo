package eu.heliovo.dpas.ie.dataProviders;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.TimeZone;

import eu.heliovo.dpas.ch.HTTPFileListProvider;
import eu.heliovo.dpas.ie.internalData.DPASResultItem;

/**
 * Data provider for Phoenix II. It returns two FITS files.
 * 
 * Check the documentation of {@link HTTPFileListProvider} for more details about
 * the methods.
 * 
 * @author Simon Felix (de@iru.ch)
 */
public class NewPhoenix2 extends SimpleHTTPFileListProvider
{
  @Override
  public DPASResultItem getData(String path)
  {
    if(!path.toLowerCase().endsWith("i.fit.gz"))
      return null;
    
    String filename=path.substring(path.lastIndexOf("/")+1);
    
    if(filename.length()!="BLEN7M_20081012_061500i.fit.gz".length()
        && filename.length()!="20051231081500i.fit.gz".length())
      return null;
    
    
    //parse the time & date of the file
    Calendar fileTime=Calendar.getInstance();
    fileTime.setTimeZone(TimeZone.getTimeZone("GMT"));
    fileTime.set(Calendar.MILLISECOND,0);
    
    String filenameFormat="";
    if(filename.length()=="BLEN7M_20081012_061500i.fit.gz".length())
    {
      //parse using modern format
      fileTime.set(Calendar.YEAR,Integer.parseInt(filename.substring(7,11)));
      fileTime.set(Calendar.MONTH,Integer.parseInt(filename.substring(11,13))-1); //january == 0 in java
      fileTime.set(Calendar.DAY_OF_MONTH,Integer.parseInt(filename.substring(13,15)));
      fileTime.set(Calendar.HOUR_OF_DAY,Integer.parseInt(filename.substring(16,18)));
      fileTime.set(Calendar.MINUTE,Integer.parseInt(filename.substring(18,20)));
      fileTime.set(Calendar.SECOND,Integer.parseInt(filename.substring(20,22)));
      
      filenameFormat="BLEN7M_%04d%02d%02d_%02d%02d%02dp.fit.gz";
    }
    if(filename.length()=="20051231081500i.fit.gz".length())
    {
      //parse using old format
      fileTime.set(Calendar.YEAR,Integer.parseInt(filename.substring(0,4)));
      fileTime.set(Calendar.MONTH,Integer.parseInt(filename.substring(4,6))-1); //january == 0 in java
      fileTime.set(Calendar.DAY_OF_MONTH,Integer.parseInt(filename.substring(6,8)));
      fileTime.set(Calendar.HOUR_OF_DAY,Integer.parseInt(filename.substring(8,10)));
      fileTime.set(Calendar.MINUTE,Integer.parseInt(filename.substring(10,12)));
      fileTime.set(Calendar.SECOND,Integer.parseInt(filename.substring(12,14)));
      
      filenameFormat="%04d%02d%02d%02d%02d%02dp.fit.gz";
    }
    
    DPASResultItem result=new DPASResultItem();
    
    result.measurementStart=fileTime;
    Calendar endTime=(Calendar)(fileTime.clone());
    endTime.add(Calendar.MINUTE,15);
    result.measurementEnd=endTime;
    result.urlIntensityFITSGZ=path;
    try
    {
      result.urlPhaseFITSGZ=new URL(new URL(path),String.format(filenameFormat,
          fileTime.get(Calendar.YEAR),
          fileTime.get(Calendar.MONTH)+1, //january==0 in java
          fileTime.get(Calendar.DAY_OF_MONTH),
          fileTime.get(Calendar.HOUR_OF_DAY),
          fileTime.get(Calendar.MINUTE),
          fileTime.get(Calendar.SECOND)
        )).toExternalForm();
    }
    catch(MalformedURLException e)
    {
      e.printStackTrace();
    }
    
    return result;
  }

  @Override
  public String getFileListPath(Calendar _start)
  {
    return "http://helene.ethz.ch/cgi-bin/showdir?dir=observations/"
    +String.format("%04d/%02d/%02d",
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
