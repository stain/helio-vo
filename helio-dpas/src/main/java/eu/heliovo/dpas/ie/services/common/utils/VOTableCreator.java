package eu.heliovo.dpas.ie.services.common.utils;

import java.io.BufferedWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.apache.log4j.Logger; 

import eu.heliovo.dpas.ie.services.common.transfer.CommonTO;
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
 	         out.write( "<INFO name=\"HELIO_INSTRUMENT_NAME\" value=\""+comCriteriaTO.getHelioInstrument()+"\"/>");
 	         if(tables!=null && tables.length>0){
	 	         for(int count=0;count<tables.length;count++){
			         //VoTable Creator
		    		 VOSerializer vos = VOSerializer.makeSerializer( DataFormat.TABLEDATA, tables[count]);
		    	     vos.writeInlineTableElement(out);
	 	         }
 	         }else{
 	        	 out.write( "<INFO name=\"DATA_STATUS\" value=\"No data found, please try with some other date\"/>");
 	         }
			 out.write( "</RESOURCE>\n" );
			 
	          
    	}catch (Exception e) {
    		e.printStackTrace();
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
    	String expStatus=comCriteriaTO.getExceptionStatus();
    	out = comCriteriaTO.getBufferOutput();
    	//If any exception occurs print error votable.
    	if(expStatus!=null && !expStatus.trim().equals("")){
	    	if(status!=null && !status.equals("")){
				 out.write("<helio:queryResponse xmlns:helio=\"http://helio-vo.eu/xml/QueryService/v0.1\">");
			}
        	out.write( "<VOTABLE version='1.1' xmlns=\"http://www.ivoa.net/xml/VOTable/v1.1\">\n" );
    	}
		//Error resource
    	out.write( "<RESOURCE>\n" );
	        out.write( "<DESCRIPTION>"+comCriteriaTO.getVotableDescription()+"</DESCRIPTION>\n" );
	        out.write( "<INFO name=\"QUERY_STATUS\" value=\""+comCriteriaTO.getQuerystatus()+"\"/>");
	        out.write( "<INFO name=\"EXECUTED_AT\" value=\""+now()+"\"/>");
	        out.write( "<INFO name=\"HELIO_INSTRUMENT_NAME\" value=\""+comCriteriaTO.getHelioInstrument()+"\"/>");
	        if(comCriteriaTO.getQuerystatus().equals("ERROR")){
	        	 out.write( "<INFO name=\"QUERY_ERROR\" value=\""+comCriteriaTO.getQuerydescription()+"\"/>");
	        }
	    out.write( "</RESOURCE>\n" );
	    //If any exception occurs print error votable.
	    if(expStatus!=null && !expStatus.trim().equals("")){
			out.write( "</VOTABLE>\n" );
	        //Adding response header start for WebService VOTABLE.
	        if(status!=null && !status.equals("") ){
				 out.write("</helio:queryResponse>");
			}
	        out.flush();
	        out.close();
		}
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
