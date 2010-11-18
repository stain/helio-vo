/* #ident	"%W%" */
package eu.heliovo.dpas.ie.services.common.dao.impl;

import java.io.BufferedWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import org.apache.log4j.Logger;
import eu.heliovo.dpas.ie.services.common.dao.exception.DetailsNotFoundException;
import eu.heliovo.dpas.ie.services.common.dao.interfaces.LongRunningQueryDao;
import eu.heliovo.dpas.ie.services.common.transfer.CommonTO;
import eu.heliovo.dpas.ie.services.common.utils.ConnectionManager;

public class LongRunningQueryDaoImpl implements LongRunningQueryDao { 

	protected final Logger logger = Logger.getLogger(this.getClass());
	Connection con = null;
	Statement st = null;
	ResultSet rs=null;
	Properties prop=null;

	@Override
	public void insertStatusToHsqlDB(String randomUUIDString,String status) throws DetailsNotFoundException {
		try{
     		//Connecting to database.						
			con = ConnectionManager.getConnection();
			st = con.createStatement();
			String query=" insert into job_status_table values('"+randomUUIDString+"','"+status+"')";
			logger.info("   :  Query to execute  :  "+query);
		    st.executeUpdate(query);
			con.commit();
		}catch(Exception e){
			logger.fatal(" Exception occured in insertStatusToHsqlDB() : ",e);
		}
		
		finally
		{
			try {
				
				if(rs!=null)
				{
					rs.close();
					rs=null;
				}
				if(st!=null)
				{
					st.close();
					st=null;
				}
				if(con!=null)
				{
					con.close();
					con=null;
				}
				if(prop!=null)
				{
					prop=null;
				}
			} catch (Exception e) {
				
			}
	    }
	}

	
	@Override
	public void insertURLToHsqlDB(String randomUUIDString,String url) throws DetailsNotFoundException {
	
		try{
			//Connecting to database.						
			con = ConnectionManager.getConnection();
			st = con.createStatement();
			String query="insert into job_url_table values('"+randomUUIDString+"','"+url+"')";
		    st.executeUpdate(query);
		    logger.info("   :  Query to execute  :  "+query);
			con.commit();
		}catch(Exception e){
			logger.fatal(" Exception occured in insertURLToHsqlDB() : ",e);
		}
			
			finally
			{
				try {
					
					if(rs!=null)
					{
						rs.close();
						rs=null;
					}
					if(st!=null)
					{
						st.close();
						st=null;
					}
					if(con!=null)
					{
						con.close();
						con=null;
					}
					if(prop!=null)
					{
						prop=null;
					}
				} catch (Exception e) {
					
				}
		}
	}
	
	
	@Override
	public String getStatusFromHsqlDB(String randomUUIDString) throws DetailsNotFoundException {
	     String sStatus="";
		try{
			//Connecting to database.						
			con = ConnectionManager.getConnection();
			st = con.createStatement();
		    rs=st.executeQuery("select * from job_status_table where job_id='"+randomUUIDString+"'");
			con.commit();
			while(rs.next()){
				sStatus=rs.getString(2);
			}
			}catch(Exception e){
				logger.fatal(" Exception occured in getStatusFromHsqlDB() : ",e);
			}
			
			finally
			{
				try {
					
					if(rs!=null)
					{
						rs.close();
						rs=null;
					}
					if(st!=null)
					{
						st.close();
						st=null;
					}
					if(con!=null)
					{
						con.close();
						con=null;
					}
					if(prop!=null)
					{
						prop=null;
					}
				} catch (Exception e) {
					
				}
		}
			
			return sStatus;
	}
	
	
	@Override
	public String getUrlFromHsqlDB(String randomUUIDString) throws DetailsNotFoundException {
		String sUrl=null;
		try{
			//Connecting to database.						
			con = ConnectionManager.getConnection();
			st = con.createStatement();
		    rs=st.executeQuery("select * from job_url_table where job_id='"+randomUUIDString+"'");
			con.commit();
			
			while(rs.next()){
				sUrl=rs.getString(2);
			}
			
			}catch(Exception e){
				logger.fatal(" Exception occured in getUrlFromHsqlDB() : ",e);
			}
			
			finally
			{
				try {
					
					if(rs!=null)
					{
						rs.close();
						rs=null;
					}
					if(st!=null)
					{
						st.close();
						st=null;
					}
					if(con!=null)
					{
						con.close();
						con=null;
					}
					if(prop!=null)
					{
						prop=null;
					}
				} catch (Exception e) {
					
				}
		}
			
		return sUrl;
	}
	
	/**
	 * 
	 */
	public void generatelongRunningQueryXML(CommonTO commonTO) throws Exception
	{
		BufferedWriter out =null;
		try{
			String status=commonTO.getStatus();
			out =commonTO.getBufferOutput();
			//Adding response header start for WebService VOTABLE.
			if(status!=null && !status.equals("")){
				 out.write("<helio:resultResponse xmlns:helio=\"http://helio-vo.eu/xml/LongQueryService/v0.1\">");
			}
			out.write(commonTO.getDataXml());
			//Adding response header start for WebService VOTABLE.
			if(status!=null && !status.equals("")){
				 out.write("</helio:resultResponse>");
			}
		}catch(Exception pe) {
        	pe.printStackTrace();
        	logger.fatal("   : Exception in CommonDaoImpl:generatelongRunningQueryXML : ", pe);
    		throw new Exception("Couldn't create Long running response XML");
        }		
		
		out.flush();
        out.close();
	}
	
}