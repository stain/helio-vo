package eu.heliovo.dpas.ie.common;

public enum ConstantKeywords {
	
	VSODATEFORMAT("yyyyMMddHHmmss"),
	ORGINALDATEFORMAT("yyyy-MM-dd HH:mm:ss");
	
	
    private String dateformat;
 
    private ConstantKeywords(String dateformat) {
    	this.dateformat = dateformat;
    }

    public String getDateFormat() {
    	return dateformat;
    }

};
