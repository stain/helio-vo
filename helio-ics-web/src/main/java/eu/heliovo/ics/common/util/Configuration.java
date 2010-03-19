
package eu.heliovo.ics.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServletRequest;


public class Configuration {
	
	Properties configProperties = new Properties();
	ResourceBundle rsbundle=  ResourceBundle.getBundle( "helio" );
     

	private final String CSVDIR = "csvpath";
	
	
	public Configuration getInstance(HttpServletRequest request)
	{
		
		
		 ResourceBundle rsbundle=null;        
         rsbundle = ResourceBundle.getBundle( "helio" );          
         
        return new Configuration(); 
	}
	
	/**
	 * @param propertyFile
	 * The method searches for the property file 
	 */
	public void configure(String propertyFile)
	{ 
		
		File file = new File(propertyFile);
		FileInputStream fs;
		try {
			fs = new FileInputStream(file);
			configProperties.load(fs);
		} catch (FileNotFoundException e) {						
			
			e.printStackTrace();
		} catch (IOException e) {						
			
			e.printStackTrace();
		}
	}
	
	/**
	 * @param strKey
	 * @return
	 */
	public String getProperty(String strKey)
	{
		return (String)configProperties.get(strKey);
	}	
	
	/**
	 * @param strKey
	 * @param strValue
	 */
	public void setProperty(String strKey, String strValue)
	{
		configProperties.put(strKey,strValue);
	}
	
	/**
	 * @return
	 */
	public Properties getConfigProperties() {
		return configProperties;
	}
	
	
	public String getCSVDIR() {
		return rsbundle.getString(CSVDIR);
	}
	
		
}

