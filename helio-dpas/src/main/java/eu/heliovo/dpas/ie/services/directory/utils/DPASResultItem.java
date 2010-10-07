package eu.heliovo.dpas.ie.services.directory.utils;

import java.util.Calendar;

import org.apache.tools.ant.util.DateUtils;

/**
 * Data container for the results of the DPAS. Each ResultItem contains data about a single
 * result. A result usually consists of some metadata, like date/time specifications, some
 * FITS data files and often some quicklook images.
 * 
 * @author Simon Felix (de@iru.ch)
 */
public class DPASResultItem
{
	/*
	 * Added to keep track of which query is this result of.
	 */
	public	String	instrument;

	//for all data providers
	public Calendar measurementStart;
	public Calendar measurementEnd;
	public String urlFITS;
  
  //used by hessi
  public String urlCorrectedRate;
  public String urlFrontRate;
  public String urlPartRate;
  public String urlRate;
  public String urlRearRate;
  
  //used by hessi flare list
  public int flareNr;
  public Calendar measurementPeak;
  public int peakCS;
  public int totalCounts;
  public int energyKeVFrom;
  public int energyKeVTo;
  public int xPos;
  public int yPos;
  public int radial;
  public int AR;
  
  //used by phoenix2
  public String urlPhaseFITSGZ;
  public String urlIntensityFITSGZ;
  
  //used by SolarMonitor SEIT
  public String urlPreview;
  public String urlPreviewThumb;

  //used by VSO
  public String fileId;
  public String provider;

  public static final String[] FIELD_NAMES=new String[]{
    "measurementStart",
    "measurementEnd",
    "urlFITS",
    "urlCorrectedRate",
    "urlFrontRate",
    "urlPartRate",
    "urlRate",
    "urlRearRate",
    "flareNr",
    "measurementPeak",
    "peakCS",
    "totalCounts",
    "energyKeVFrom",
    "energyKeVTo",
    "xPos",
    "yPos",
    "radial",
    "AR",
    "urlPhaseFITSGZ",
    "urlIntensityFITSGZ",
    "urlPreview",
    "urlPreviewThumb"
  };
  
  public Object getColumn(int _column)
  {
    switch(_column)
    {
      case  0: return measurementStart;
      case  1: return measurementEnd;
      case  2: return urlFITS;
      case  3: return urlCorrectedRate;
      case  4: return urlFrontRate;
      case  5: return urlPartRate;
      case  6: return urlRate;
      case  7: return urlRearRate;
      case  8: return flareNr;
      case  9: return measurementPeak;
      case 10: return peakCS;
      case 11: return totalCounts;
      case 12: return energyKeVFrom;
      case 13: return energyKeVTo;
      case 14: return xPos;
      case 15: return yPos;
      case 16: return radial;
      case 17: return AR;
      case 18: return urlPhaseFITSGZ;
      case 19: return urlIntensityFITSGZ;
      case 20: return urlPreview;
      case 21: return urlPreviewThumb;
      default: return null;
    }
  }
  
  public String toString(int _column)
  {
    Object o=getColumn(_column);
    
    if(o==null)
      return "";
    
    if(o instanceof String)
      return (String)o;
    
    if(o instanceof Calendar)
      return DateUtils.format(((Calendar)o).getTime(),DateUtils.ISO8601_DATETIME_PATTERN);
    
    if(o instanceof Integer)
      return ((Integer)o).toString();
    
    return o.toString();
  }
  
  public String toCSVEscapedString(int _column)
  {
    String ret=toString(_column);
    
    if(ret.contains("\""))
      return "\""+ret.replace("\"","\"\"") +"\"";
    else if(ret.contains(","))
      return "\""+ret+"\"";
    else
      return ret;
  }
}
