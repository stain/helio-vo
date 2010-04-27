package eu.heliovo.dpas.ie.dataProviders;

import java.util.Calendar;
import java.util.TimeZone;

import eu.heliovo.dpas.ch.HTTPFileListProvider;
import eu.heliovo.dpas.ie.internalData.DPASResultItem;

/**
 * Data provider for RHESSI. It returns some quickview PNGs and a FITS file.
 * 
 * Check the documentation of {@link HTTPFileListProvider} for more details about
 * the methods.
 * 
 * @author Simon Felix (de@iru.ch)
 */
public class NewHessi extends SimpleHTTPFileListProvider
{
  @Override
  public DPASResultItem getData(String path)
  {
    //is not a fits-file --> abort
    if(!path.toLowerCase().endsWith(".fits"))
      return null;
    
    //get the filename (without the path)
    String filename=path.substring(path.lastIndexOf("/")+1);
    
    //filename does not match "spec" --> abort
    if(filename.length()!="hsi_20000619_191200_000.fits".length())
      return null;
    
    //parse the time & date of the file
    Calendar fileTime=Calendar.getInstance();
    fileTime.setTimeZone(TimeZone.getTimeZone("GMT"));
    fileTime.set(Calendar.YEAR,Integer.parseInt(filename.substring(4,8)));
    fileTime.set(Calendar.MONTH,Integer.parseInt(filename.substring(8,10))-1); //january == 0 in java
    fileTime.set(Calendar.DAY_OF_MONTH,Integer.parseInt(filename.substring(10,12)));
    fileTime.set(Calendar.HOUR_OF_DAY,Integer.parseInt(filename.substring(13,15)));
    fileTime.set(Calendar.MINUTE,Integer.parseInt(filename.substring(15,17)));
    fileTime.set(Calendar.SECOND,Integer.parseInt(filename.substring(17,19)));
    fileTime.set(Calendar.MILLISECOND,0);
    
    //we have a valid file and have parsed it's date
    //let's create all the file links and populate a
    //map with all the info
    
    DPASResultItem result=new DPASResultItem();
    result.measurementStart=fileTime;
    // http://hesperia.gsfc.nasa.gov/hessidata/2000/06/19/hsi_20000619_191200_000.fits
    String	FitsFileUrl = String.format("http://hesperia.gsfc.nasa.gov/hessidata/%04d/%02d/%02d/hsi_%04d%02d%02d_%02d%02d%02d_000.fits",         
    fileTime.get(Calendar.YEAR),
    fileTime.get(Calendar.MONTH)+1, //january==0 in java
    fileTime.get(Calendar.DAY_OF_MONTH),
    fileTime.get(Calendar.YEAR),
    fileTime.get(Calendar.MONTH)+1, //january==0 in java
    fileTime.get(Calendar.DAY_OF_MONTH),
    fileTime.get(Calendar.HOUR_OF_DAY),
    fileTime.get(Calendar.MINUTE),
    fileTime.get(Calendar.SECOND)
    );
    
    result.urlFITS	=	FitsFileUrl;
    return result;
  }

  @Override
  public String getFileListPath(Calendar _start)
  {
    return "http://hesperia.gsfc.nasa.gov/hessidata/"+String.format("%04d/%02d/%02d",
        _start.get(Calendar.YEAR),
        _start.get(Calendar.MONTH)+1, //january==0 in java
        _start.get(Calendar.DAY_OF_MONTH))+"/";
  }

  @Override
  public int getFileListPer()
  {
    return Calendar.DAY_OF_MONTH;
  }
}
