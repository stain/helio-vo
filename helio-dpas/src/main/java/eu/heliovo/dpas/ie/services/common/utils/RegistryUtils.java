package eu.heliovo.dpas.ie.services.common.utils;

import java.util.Enumeration;
import java.util.HashMap;

import eu.heliovo.dpas.ie.services.common.transfer.ResultTO;

public class RegistryUtils {
		
	private RegistryUtils()
	{
		
	}
		
	public static RegistryUtils getInstance() {
		return RegistryUtilsHolder.instance;
	}

	private static class RegistryUtilsHolder {
		private static RegistryUtils instance = new RegistryUtils();
	}
	
	
	/** Generates the table descriptions for the CatalogService registration
	    * for the specified catalog.
	    */
	   public String getTableDescriptions()  throws Exception {

	      StringBuffer tables = new StringBuffer();
	      String[] catalogNames=null;
	      ResultTO[] resultTo=HsqlDbUtils.getInstance().getAccessTableDetails();
	      for (int t = 0; t < resultTo.length; t++) {
	         tables.append(
	           "  <table>\n" +
	           "    <name>"+catalogNames[t]+"</name>\n" 
	          );
	          HashMap<String, String> dataTypeLookup=null;
	          String columns[]=null;
	    	  String columnDesc[]=null;
	    	  String ucd[]=null;
	    	  String utypes[]=null;	  
	         
	         for (int c = 0; c < columns.length; c++) {
	            tables.append(
	               "    <column>\n"+
	               "    <name>"+columns[c]+"</name>\n"+
	               "    <description>"+columnDesc[c]+"</description>\n"+
	               "    <datatype>"+dataTypeLookup.get(columns[c].toLowerCase())+"</datatype>\n"
	            );
	            //UTypes
	            if(utypes!=null && utypes.length>0 && utypes.length==columns.length){
		            if ((utypes[c] != null) && (utypes[c].trim().length()>0)) {
		            tables.append(
		                  "      <utypes>"+utypes[c]+"</utypes>\n"
		            );
		            }
	            }
	            //UCD'S
	            if(ucd!=null && ucd.length>0 && ucd.length==columns.length){
		            if ((ucd[c] != null) && (ucd[c].trim().length()>0)) {
		               tables.append(
		                  "      <ucd>"+ucd[c].trim()+"</ucd>\n"
		               );
		            }
	            }
	            //
	            // Patch fix - Moved the xml fragment generation to VoTypes.
	            // KONA TOFIX look at the points below
	            // Todo - Need access to the column size for strings.
	            // Todo - Need to create a VoType format for dates.
	            //tables.append("    "+
	            //    VoTypes.getVoTypeXml(columns[c].getJavaType()) + "\n"
	            //);
	            tables.append("    </column>\n");
	         }
	         tables.append("  </table>\n");
	      }
	      return tables.toString();
	   }
	

}


