/* #ident	"%W%" */
package eu.heliovo.dpas.ie.services.common.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import org.apache.log4j.Logger;
import eu.heliovo.dpas.ie.services.common.dao.exception.DetailsNotFoundException;
import eu.heliovo.dpas.ie.services.common.dao.interfaces.ShortNameQueryDao;
import eu.heliovo.dpas.ie.services.common.transfer.CommonTO;
import eu.heliovo.dpas.ie.services.common.transfer.ResultTO;
import eu.heliovo.dpas.ie.services.common.utils.CommonUtils;
import eu.heliovo.dpas.ie.services.common.utils.ConnectionManager;
import eu.heliovo.dpas.ie.services.common.utils.VOTableCreator;


public class ShortNameQueryDaoImpl implements ShortNameQueryDao {
		
	public ShortNameQueryDaoImpl(){ 
						
	}
	Connection con = null;
	Statement st = null;
	ResultSet rs=null;
	protected final  Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public void loadProviderAccessTable(String fileName,String tableName) throws DetailsNotFoundException {
		String sUrl=null;
		try{
			System.out.println("  :  -----> Setting uploaded file for provider access table ----->");
			String query="SET TABLE "+tableName+" SOURCE "+"\""+fileName+"\"";
			System.out.println("loadProviderAccessTable() method "+query);
			//Connecting to database.						
			con = ConnectionManager.getConnection();
			st = con.createStatement();
			st.execute(query);
			con.commit();
			System.out.println("  :  -----> Done ----->");
		}catch(Exception e){
			logger.fatal(" Exception occured in loadProviderAccessTable() : ",e);
			throw new DetailsNotFoundException("Exception: ",e);
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
			} catch (Exception e) {
				throw new DetailsNotFoundException("Exception: ",e);
			}
		}
	}
	
	
	@Override
	public ResultTO[] getFtpAccessTableBasedOnInst(String strIns) throws DetailsNotFoundException {
			int count=0;
			ResultTO[] resultTO=null;
			try{
				String query="select * from ftppat where helio_obs_inst='"+strIns+"' order by pvdr_ranking limit 1";
				System.out.println("  :  -----> getFtpAccessTableBasedOnInst() method -----> : Reslut For "+strIns);
				//Connecting to database.						
				con = ConnectionManager.getConnection();
				st = con.createStatement();
				rs=st.executeQuery(query);
				//rs.last();
				resultTO = new ResultTO[1];
				//rs.first();
				while(rs.next()){
					resultTO[count]=new ResultTO();
					//Helio Inst
					if(rs.getString(1)!=null)
						resultTO[count].setHelioInst(rs.getString(1));
					//Provider Type
					if(rs.getString(2)!=null)
						resultTO[count].setProviderType(rs.getString(2));
					//Provider Name
					if(rs.getString(3)!=null)
						resultTO[count].setProviderName(rs.getString(3));
					//Provider Type
					if(rs.getString(4)!=null)
						resultTO[count].setWorkingDir(rs.getString(4));
					//Instrument
					if(rs.getString(5)!=null)
						resultTO[count].setYearPattern(rs.getString(5));
					//Obsevatory Id
					if(rs.getString(6)!=null)
						resultTO[count].setMonthPattern(rs.getString(6));
					//Provider Source
					if(rs.getString(7)!=null)
						resultTO[count].setFtpHost(rs.getString(7));
					//Provider Ack
					if(rs.getString(8)!=null)
						resultTO[count].setFtpUser(rs.getString(8));
					//Provider Ack
					if(rs.getString(9)!=null)
						resultTO[count].setFtpPwd(rs.getString(9));
					//Provider Ack
					if(rs.getString(10)!=null)
						resultTO[count].setFtpPattern(rs.getString(10));
					//Provider Ack
					if(rs.getString(11)!=null)
						resultTO[count].setFtpDatePattern(rs.getString(11));
					//Provider quality
					if(rs.getString(12)!=null)
						resultTO[count].setPvdrRanking(rs.getString(12));
					//Vso Int id
					if(rs.getString(13)!=null)
						resultTO[count].setPvdrQuality(rs.getString(13));
					//
					count++;
                }
				System.out.println("  :  -----> Success -----> Reslut For : "+strIns);
				return resultTO;
			}catch(Exception e){
				e.printStackTrace();
				logger.fatal(" Exception occured in getAccessTableBasedOnInst() : ",e);
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
				} catch (Exception e) {
					
				}
		    }	
			return null;
		}
	
	@Override
	public ResultTO[] getAccessTableBasedOnInst(String strIns) throws DetailsNotFoundException {
			int count=0;
			ResultTO[] resultTO=null;
			try{
				String query="select * from pat where helio_obs_inst='"+strIns+"' order by pvdr_ranking limit 1";
				System.out.println("  :  -----> getAccessTableBasedOnInst() method -----> : Reslut For "+strIns);
				//Connecting to database.						
				con = ConnectionManager.getConnection();
				st = con.createStatement();
				rs=st.executeQuery(query);
				//rs.last();
				resultTO = new ResultTO[1];
				//rs.first();
				while(rs.next()){
					resultTO[count]=new ResultTO();
					//Helio Inst
					if(rs.getString(1)!=null)
						resultTO[count].setHelioInst(rs.getString(1));
					//Provider Name
					if(rs.getString(2)!=null)
						resultTO[count].setProviderName(rs.getString(2));
					//Provider Type
					if(rs.getString(3)!=null)
						resultTO[count].setProviderType(rs.getString(3));
					//Instrument
					if(rs.getString(4)!=null)
						resultTO[count].setInst(rs.getString(4));
					//Obsevatory Id
					if(rs.getString(5)!=null)
						resultTO[count].setObsId(rs.getString(5));
					//Provider Source
					if(rs.getString(6)!=null)
						resultTO[count].setProviderSource(rs.getString(6));
					//Provider Ack
					if(rs.getString(7)!=null)
						resultTO[count].setProviderAck(rs.getString(7));
					//Provider Ranking
					if(rs.getString(8)!=null)
						resultTO[count].setPvdrRanking(rs.getString(8));
					//Provider quality
					if(rs.getString(9)!=null)
						resultTO[count].setPvdrQuality(rs.getString(9));
					//Vso Int id
					if(rs.getString(10)!=null)
						resultTO[count].setPvdrVsoKey(rs.getString(10));
					//
					count++;
                }
				System.out.println("  :  -----> Success -----> Reslut For : "+strIns);
				return resultTO;
			}catch(Exception e){
				e.printStackTrace();
				logger.fatal(" Exception occured in getAccessTableBasedOnInst() : ",e);
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
				} catch (Exception e) {
					
				}
		    }	
			return null;
		}
		
	/**
	 * 	
	 */
	@Override
	public ResultTO[] getAccessTableDetails() throws DetailsNotFoundException {
			int count=0;
			ResultTO[] resultTO=null;
			try{
				String query="select * from pat";
				System.out.println("  :  -----> getAccessTableBasedOnInst() method -----> : Reslut For ");
				//Connecting to database.						
				con = ConnectionManager.getConnection();
				st = con.createStatement();
				rs=st.executeQuery(query);
				//rs.last();
				resultTO = new ResultTO[1];
				//rs.first();
				while(rs.next()){
					resultTO[count]=new ResultTO();
					//Helio Inst
					if(rs.getString(1)!=null)
						resultTO[count].setHelioInst(rs.getString(1));
					//Provider Name
					if(rs.getString(2)!=null)
						resultTO[count].setProviderName(rs.getString(2));
					//Provider Type
					if(rs.getString(3)!=null)
						resultTO[count].setProviderType(rs.getString(3));
					//Instrument
					if(rs.getString(4)!=null)
						resultTO[count].setInst(rs.getString(4));
					//Obsevatory Id
					if(rs.getString(5)!=null)
						resultTO[count].setObsId(rs.getString(5));
					//Provider Source
					if(rs.getString(6)!=null)
						resultTO[count].setProviderSource(rs.getString(6));
					//Provider Ack
					if(rs.getString(7)!=null)
						resultTO[count].setProviderAck(rs.getString(7));
					//Provider Ranking
					if(rs.getString(8)!=null)
						resultTO[count].setPvdrRanking(rs.getString(8));
					//Provider quality
					if(rs.getString(9)!=null)
						resultTO[count].setPvdrQuality(rs.getString(9));
					//Vso Int id
					if(rs.getString(10)!=null)
						resultTO[count].setPvdrVsoKey(rs.getString(10));
					count++;
                }
				System.out.println("  :  -----> Success -----> Reslut For : ");
				return resultTO;
			}catch(Exception e){
				e.printStackTrace();
				logger.fatal(" Exception occured in getAccessTableBasedOnInst() : ",e);
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
				} catch (Exception e) {
					
				}
		    }	
			return null;
		}
		
	/**
	 * @throws Exception 
	 * 
	 */
	public void generateVOTable(CommonTO commonTO) throws Exception
	{
		 String[] startTime =commonTO.getStartTimes();
	     String[] stopTime =commonTO.getStopTimes();
	     String[] instruments =commonTO.getInstruments();
		  //VOTable header
		 VOTableCreator.writeHeaderOfTables(commonTO);
		 //Check both Start and End Time equal.
		 if(startTime.length==stopTime.length){
				 //For loop
		     for(int count=0;count<instruments.length;count++){
			      //Setting Instrument Value
			      commonTO.setParaInstrument(instruments[count]);
			   	  if((startTime.length>1 && stopTime.length>1 && instruments.length==1) || (startTime.length==1 && stopTime.length==1 && instruments.length>1) || (startTime.length==1 && stopTime.length==1 && instruments.length==1)){
				    		 for(int counter=0;counter<startTime.length;counter++){
					    		 commonTO.setDateFrom(startTime[counter]);
							     System.out.println(" : Start Date Contraint : "+startTime[counter]);
							     commonTO.setDateTo(stopTime[counter]);	
						    	 System.out.println(" : Stop Date Contraint : "+stopTime[counter]);
						    	 CommonUtils.genegrateVotableBasedOnCondition(commonTO);
				    	     }
				   }else if(startTime.length==stopTime.length && instruments.length==stopTime.length){
				    		  commonTO.setDateFrom(startTime[count]);
							  System.out.println(" : Start Date Contraint : "+startTime[count]);
							  commonTO.setDateTo(stopTime[count]);	
						      System.out.println(" : Stop Date Contraint : "+stopTime[count]);
						      CommonUtils.genegrateVotableBasedOnCondition(commonTO);
				   }else{
				    		  commonTO.setExceptionStatus("exception");
							  throw new Exception("Please send proper values, request is not succesfull.");
				   }
			  }
		 }else{
			  //commonTO.setExceptionStatus("exception");
		   	  commonTO.setVotableDescription("Query response");
		   	  commonTO.setQuerystatus("ERROR");
		   	  commonTO.setQuerydescription("Start date and End date should have same no of values.");
			  VOTableCreator.writeErrorTables(commonTO);
	    }
	    //VOTable footer.
		VOTableCreator.writeFooterOfTables(commonTO);
	}
		
 }

