package eu.heliovo.dpas.ie.services.common.utils;

public enum ConstantKeywords {
	
	VSODATEFORMAT("yyyyMMddHHmmss"),
	ORGINALDATEFORMAT("yyyy-MM-dd HH:mm:ss");
	
	public static final String SQLFORMAT = "yyyy-MM-dd HH:mm:ss" ;
    
	private String dateformat;
 
    private ConstantKeywords(String dateformat) {
    	this.dateformat = dateformat;
    }

    public String getDateFormat() {
    	return dateformat;
    }

};
