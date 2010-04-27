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
public class EIS extends SimpleHTTPFileListProvider
{
	/*
	 * http://www.sdc.uio.no/vol/fits/eis/level0/2007/10/17/eis_l0_20071017_231341.fits.gz
	 */
	
  @Override
  public DPASResultItem getData(String path)
  {
    //is not a fits-file --> abort
    if(!path.toLowerCase().endsWith(".fits.gz"))
      return null;
    
    //get the filename (without the path)
    String filename=path.substring(path.lastIndexOf("/")+1);
    
    //filename does not match "spec" --> abort
    if(filename.length()!="eis_l0_20071017_231341.fits.gz".length())
      return null;
    
    //parse the time & date of the file
    Calendar fileTime=Calendar.getInstance();
    fileTime.setTimeZone(TimeZone.getTimeZone("GMT"));
    fileTime.set(Calendar.YEAR,Integer.parseInt(filename.substring(7,11)));
    fileTime.set(Calendar.MONTH,Integer.parseInt(filename.substring(11,13))-1); //january == 0 in java
    fileTime.set(Calendar.DAY_OF_MONTH,Integer.parseInt(filename.substring(13,15)));
    fileTime.set(Calendar.HOUR_OF_DAY,Integer.parseInt(filename.substring(16,18)));
    fileTime.set(Calendar.MINUTE,Integer.parseInt(filename.substring(18,20)));
    fileTime.set(Calendar.SECOND,Integer.parseInt(filename.substring(20,22)));
    fileTime.set(Calendar.MILLISECOND,0);
    
    //we have a valid file and have parsed it's date
    //let's create all the file links and populate a
    //map with all the info

	/*
	 * http://www.sdc.uio.no/vol/fits/eis/level0/2007/10/17/eis_l0_20071017_231341.fits.gz
	 */

    DPASResultItem result=new DPASResultItem();
    result.measurementStart=fileTime;
    // http://hesperia.gsfc.nasa.gov/hessidata/2000/06/19/hsi_20000619_191200_000.fits
    String	FitsFileUrl = String.format("http://www.sdc.uio.no/vol/fits/eis/level0/%04d/%02d/%02d/eis_l0_%04d%02d%02d_%02d%02d%02d.fits.gz",         
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
    return "http://www.sdc.uio.no/vol/fits/eis/level0/"+String.format("%04d/%02d/%02d",
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
