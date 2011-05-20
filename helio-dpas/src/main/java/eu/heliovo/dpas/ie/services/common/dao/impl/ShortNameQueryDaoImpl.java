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
					if(rs.getString("HELIO_OBS_INST")!=null)
						resultTO[count].setHelioInst(rs.getString("HELIO_OBS_INST"));
					//Provider Type
					if(rs.getString("PVDR_NAME")!=null)
						resultTO[count].setProviderType(rs.getString("PVDR_NAME"));
					//Provider Name
					if(rs.getString("PVDR_TYPE")!=null)
						resultTO[count].setProviderName(rs.getString("PVDR_TYPE"));
					//Provider Type
					if(rs.getString("PVDR_DIR_LOC")!=null)
						resultTO[count].setWorkingDir(rs.getString("PVDR_DIR_LOC"));
					//Instrument
					if(rs.getString("PVDR_YR_PATTERN")!=null)
						resultTO[count].setYearPattern(rs.getString("PVDR_YR_PATTERN"));
					//Obsevatory Id
					if(rs.getString("PVDR_MON_PATTERN")!=null)
						resultTO[count].setMonthPattern(rs.getString("PVDR_MON_PATTERN"));
					//Provider Source
					if(rs.getString("PVDR_HOST_NAME")!=null)
						resultTO[count].setFtpHost(rs.getString("PVDR_HOST_NAME"));
					//Provider Ack
					if(rs.getString("PVDR_USER_NAME")!=null)
						resultTO[count].setFtpUser(rs.getString("PVDR_USER_NAME"));
					//Provider Ack
					if(rs.getString("PVDR_PSW")!=null)
						resultTO[count].setFtpPwd(rs.getString("PVDR_PSW"));
					//Provider Ack
					if(rs.getString("PVDR_FTP_PATTERN")!=null)
						resultTO[count].setFtpPattern(rs.getString("PVDR_FTP_PATTERN"));
					//Provider Ack
					if(rs.getString("PVDR_DATE_PATTERN")!=null)
						resultTO[count].setFtpDatePattern(rs.getString("PVDR_DATE_PATTERN"));
					//Provider quality
					if(rs.getString("PVDR_RANKING")!=null)
						resultTO[count].setPvdrRanking(rs.getString("PVDR_RANKING"));
					//Vso Int id
					if(rs.getString("PVDR_QUALITY")!=null)
						resultTO[count].setPvdrQuality(rs.getString("PVDR_QUALITY"));
					//
					count++;
                }
				System.out.println("  :  -----> Success -----> Reslut For : "+strIns);
				return resultTO;
			}catch(Exception e){
				e.printStackTrace();
				logger.fatal(" Exception occured in getFtpAccessTableBasedOnInst() : ",e);
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
	public ResultTO[] getAccessTableBasedOnInst(String strIns,String prvdType) throws DetailsNotFoundException {
			int count=0;
			ResultTO[] resultTO=null;
			try{
				String queryPrvdType="";
				String query="select * from pat where helio_obs_inst='"+strIns+"'";
				if(prvdType!=null && !prvdType.trim().equals(""))
					queryPrvdType=" and pvdr_name='"+prvdType+"'";
				if(queryPrvdType!=null && !queryPrvdType.trim().equals(""))
					query=query+queryPrvdType+" limit 1";
				else
					query=query+" order by pvdr_ranking limit 1";
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
					if(rs.getString("HELIO_OBS_INST")!=null)
						resultTO[count].setHelioInst(rs.getString("HELIO_OBS_INST"));
					//Provider Name
					if(rs.getString("PVDR_NAME")!=null)
						resultTO[count].setProviderName(rs.getString("PVDR_NAME"));
					//Provider Type
					if(rs.getString("PVDR_TYPE")!=null)
						resultTO[count].setProviderType(rs.getString("PVDR_TYPE"));
					//Instrument
					if(rs.getString("PVDR_KEY1")!=null)
						resultTO[count].setInst(rs.getString("PVDR_KEY1"));
					//Obsevatory Id
					if(rs.getString("PVDR_KEY2")!=null)
						resultTO[count].setObsId(rs.getString("PVDR_KEY2"));
					//Provider Source
					if(rs.getString("PVDR_SOURCE")!=null)
						resultTO[count].setProviderSource(rs.getString("PVDR_SOURCE"));
					//Provider Ack
					if(rs.getString("PVDR_ACK")!=null)
						resultTO[count].setProviderAck(rs.getString("PVDR_ACK"));
					//Provider Ranking
					if(rs.getString("PVDR_RANKING")!=null)
						resultTO[count].setPvdrRanking(rs.getString("PVDR_RANKING"));
					//Provider quality
					if(rs.getString("PVDR_QUALITY")!=null)
						resultTO[count].setPvdrQuality(rs.getString("PVDR_QUALITY"));
					//Vso Int id
					if(rs.getString("PVDR_VSO_KEY")!=null)
						resultTO[count].setPvdrVsoKey(rs.getString("PVDR_VSO_KEY"));
					//Detector Id
					if(rs.getString("DETECTOR")!=null) {
						resultTO[count].setDetectiveField(rs.getString("DETECTOR"));
						//System.out.println("DETECTOR FOUND11: " + resultTO[count].getDetectiveField());
					}
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
				System.out.println("  :  -----> getAccessTableDetails() method -----> : Reslut For ");
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
					if(rs.getString("HELIO_OBS_INST")!=null)
						resultTO[count].setHelioInst(rs.getString("HELIO_OBS_INST"));
					//Provider Name
					if(rs.getString("PVDR_NAME")!=null)
						resultTO[count].setProviderName(rs.getString("PVDR_NAME"));
					//Provider Type
					if(rs.getString("PVDR_TYPE")!=null)
						resultTO[count].setProviderType(rs.getString("PVDR_TYPE"));
					//Instrument
					if(rs.getString("PVDR_KEY1")!=null)
						resultTO[count].setInst(rs.getString("PVDR_KEY1"));
					//Obsevatory Id
					if(rs.getString("PVDR_KEY2")!=null)
						resultTO[count].setObsId(rs.getString("PVDR_KEY2"));
					//Provider Source
					if(rs.getString("PVDR_SOURCE")!=null)
						resultTO[count].setProviderSource(rs.getString("PVDR_SOURCE"));
					//Provider Ack
					if(rs.getString("PVDR_ACK")!=null)
						resultTO[count].setProviderAck(rs.getString("PVDR_ACK"));
					//Provider Ranking
					if(rs.getString("PVDR_RANKING")!=null)
						resultTO[count].setPvdrRanking(rs.getString("PVDR_RANKING"));
					//Provider quality
					if(rs.getString("PVDR_QUALITY")!=null)
						resultTO[count].setPvdrQuality(rs.getString("PVDR_QUALITY"));
					//Vso Int id
					if(rs.getString("PVDR_VSO_KEY")!=null)
						resultTO[count].setPvdrVsoKey(rs.getString("PVDR_VSO_KEY"));
					//Vso Int id
					if(rs.getString("DETECTOR")!=null)
						resultTO[count].setDetectiveField(rs.getString("DETECTOR"));
					count++;
                }
				System.out.println("  :  -----> Success -----> Reslut For : ");
				return resultTO;
			}catch(Exception e){
				e.printStackTrace();
				logger.fatal(" Exception occured in getAccessTableDetails() : ",e);
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
	public synchronized void generateVOTable(CommonTO commonTO) throws Exception
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
		    	 try{
			      //Setting Instrument Value
			      commonTO.setParaInstrument(instruments[count]);
			   	  if((startTime.length>1 && stopTime.length>1 && instruments.length==1) || (startTime.length==1 && stopTime.length==1 && instruments.length>1) || (startTime.length==1 && stopTime.length==1 && instruments.length==1)){ 
			    	 	for(int counter=0;counter<startTime.length;counter++){
			    	 		try{
					    		 commonTO.setDateFrom(startTime[counter]);
							     System.out.println(" : Start Date Contraint : "+startTime[counter]);
							     commonTO.setDateTo(stopTime[counter]);	
						    	 System.out.println(" : Stop Date Contraint : "+stopTime[counter]);
						    	 CommonUtils.genegrateVotableBasedOnCondition(commonTO);
			    	 		}catch(Exception e)
					    	{
					    		commonTO.setVotableDescription(" Exception occured while querying  "+commonTO.getWhichProvider()+": "+e.getMessage());
					 			commonTO.setQuerystatus("ERROR");
					 			commonTO.setQuerydescription(e.getMessage());
					 			// 
					 			try{
					 				//Sending error messages
					 				VOTableCreator.writeErrorTables(commonTO);
					 			}catch (Exception e1) {
					 				e1.printStackTrace();
					 			}
					    	 }
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
		    	}catch(Exception e)
		    	{
		    		commonTO.setVotableDescription(" Exception occured while querying  "+commonTO.getWhichProvider()+": "+e.getMessage());
		 			commonTO.setQuerystatus("ERROR");
		 			commonTO.setQuerydescription(e.getMessage());
		 			try{
		 				//Sending error messages
		 				VOTableCreator.writeErrorTables(commonTO);
		 			}catch (Exception e1) {
		 				e1.printStackTrace();
		 			}
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

