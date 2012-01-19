package eu.heliovo.cis.service.repository;

import java.io.Serializable;
import java.util.HashMap;

public class DefaultPreferences implements Serializable
{
	/*
	 * The preferences
	 */
	private HashMap<String, HashMap<String, String>>	
					preferences			=	new HashMap<String, HashMap<String, String>>();
	/*
	 * The default preferences
	 */
	static	String	dpas_service	=	"dpas";
	static	String	dpas_field_1	=	"dpas_field_1";
	static	String	dpas_value_1	=	"dpas_value_1";
	static	String	dpas_field_2	=	"dpas_field_2";
	static	String	dpas_value_2	=	"dpas_value_2";
	static	String	dpas_field_3	=	"dpas_field_3";
	static	String	dpas_value_3	=	"dpas_value_3";
	static	String	hfe_service		=	"hfe";
	static	String	hfe_field_1		=	"hfe_field_1";
	static	String	hfe_value_1		=	"hfe_value_1";
	static	String	hfe_field_2		=	"hfe_field_2";
	static	String	hfe_value_2		=	"hfe_value_2";
	static	String	hfe_field_3		=	"hfe_field_3";
	static	String	hfe_value_3		=	"hfe_value_3";
	
	public DefaultPreferences() 
	{
		super();
		HashMap<String, String>	p	=	new HashMap<String, String>();		
		p.put(dpas_field_1, dpas_value_1);
		p.put(dpas_field_2, dpas_value_2);
		p.put(dpas_field_3, dpas_value_3);
		preferences.put(dpas_service, p);				
		p	=	new HashMap<String, String>();		
		p.put(hfe_field_1, hfe_value_1);
		p.put(hfe_field_2, hfe_value_2);
		p.put(hfe_field_3, hfe_value_3);
		preferences.put(hfe_service, p);
	}

	public HashMap<String, HashMap<String, String>> getPreferences()
	{
		return preferences;
	}
}
