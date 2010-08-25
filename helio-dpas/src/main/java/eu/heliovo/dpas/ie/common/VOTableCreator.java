package eu.heliovo.dpas.ie.common;

import java.io.BufferedWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.apache.log4j.Logger;
import uk.ac.starlink.table.StarTable;
import uk.ac.starlink.votable.DataFormat;
import uk.ac.starlink.votable.VOSerializer;

public class VOTableCreator {
  
    protected final  Logger logger = Logger.getLogger(this.getClass());
    public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";

    public VOTableCreator(){
    	
    }
    /**
     * Not in use now...
     * @param comCriteriaTO
     * @throws Exception
     */
    public static void writeHeaderOfTables( CommonTO comCriteriaTO ) throws Exception {
    	String status=comCriteriaTO.getStatus();
    	BufferedWriter out =null;
    	
    	out = comCriteriaTO.getBufferOutput();
    	//Adding response header start for WebService VOTABLE.
		if(status!=null && !status.equals("")){
			 out.write("<helio:queryResponse xmlns:helio=\"http://helio-vo.eu/xml/QueryService/v0.1\">");
		}
		 out.write( "<VOTABLE version='1.1' xmlns=\"http://www.ivoa.net/xml/VOTable/v1.1\">\n" );
        
    }
    
    /**
     * //Writing all the details into VOtable.
     * @param comCriteriaTO
     * @throws Exception
     */
    public static void writeTables( CommonTO comCriteriaTO ) throws Exception {
    	BufferedWriter out =null;
     	out = comCriteriaTO.getBufferOutput();
    	StarTable[] tables=comCriteriaTO.getStarTableArray();
    	String status=comCriteriaTO.getStatus();
    	try{    		
       		 //Writing VOTABLE Resource
    		 out.write( "<RESOURCE>\n" );
 	         out.write( "<DESCRIPTION>"+comCriteriaTO.getVotableDescription()+"</DESCRIPTION>\n" );
 	         out.write( "<INFO name=\"QUERY_STATUS\" value=\""+comCriteriaTO.getQuerystatus()+"\"/>");
 	         out.write( "<INFO name=\"EXECUTED_AT\" value=\""+now()+"\"/>");
 	         for(int count=0;count<tables.length;count++){
		         //VoTable Creator
	    		 VOSerializer vos = VOSerializer.makeSerializer( DataFormat.TABLEDATA, tables[count]);
	    	     vos.writeInlineTableElement(out);
 	         }
			 out.write( "</RESOURCE>\n" );
			 
	          
    	}catch (Exception e) {
    		System.out.println(" Exception occured writeTables():While creating VOTable...!!! "+e.getMessage());
    		writeErrorTables(comCriteriaTO);
    		throw new Exception("Couldn't create VO TABLE!!! : Reason : "+e.getMessage());
		}
        
    }
    /**
     * 
     * @param comCriteriaTO
     * @throws Exception
     */
    public static void writeErrorTables( CommonTO comCriteriaTO ) throws Exception {
    	BufferedWriter out =null;
    	String status=comCriteriaTO.getStatus();
    	out = comCriteriaTO.getBufferOutput();
    	if(status!=null && !status.equals("")){
			 out.write("<helio:queryResponse xmlns:helio=\"http://helio-vo.eu/xml/QueryService/v0.1\">");
		}
        out.write( "<VOTABLE version='1.1' xmlns=\"http://www.ivoa.net/xml/VOTable/v1.1\">\n" );
		//Error resource
    	out.write( "<RESOURCE>\n" );
	        out.write( "<DESCRIPTION>"+comCriteriaTO.getVotableDescription()+"</DESCRIPTION>\n" );
	        out.write( "<INFO name=\"QUERY_STATUS\" value=\""+comCriteriaTO.getQuerystatus()+"\"/>");
	        out.write( "<INFO name=\"EXECUTED_AT\" value=\""+now()+"\"/>");
	        if(comCriteriaTO.getQuerystatus().equals("ERROR")){
	        	 out.write( "<INFO name=\"QUERY_ERROR\" value=\""+comCriteriaTO.getQuerydescription()+"\"/>");
	        }
	    out.write( "</RESOURCE>\n" );
		out.write( "</VOTABLE>\n" );
        //Adding response header start for WebService VOTABLE.
        if(status!=null && !status.equals("") ){
			 out.write("</helio:queryResponse>");
		}
        out.flush();
        out.close();
    }
    
    /**
     * Not in use now..
     * @param comCriteriaTO
     * @throws Exception
     */
    public static void writeFooterOfTables( CommonTO comCriteriaTO ) throws Exception {
    	String status=comCriteriaTO.getStatus();
    	BufferedWriter out =null;	
    	out = comCriteriaTO.getBufferOutput();
    	out.write( "</VOTABLE>\n" );
        //Adding response header start for WebService VOTABLE.
        if(status!=null && !status.equals("") ){
			 out.write("</helio:queryResponse>");
		}
        out.flush();
        out.close();
    }
    
    /**
     * 
     * @return
     */
    private static String now() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(ConstantKeywords.ORGINALDATEFORMAT.getDateFormat());
        return sdf.format(cal.getTime());

      }
 
}
