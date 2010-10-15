/* #ident	"%W%" */
package eu.heliovo.dpas.ie.services.common.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import org.apache.log4j.Logger;
import eu.heliovo.dpas.ie.services.common.dao.exception.DetailsNotFoundException;
import eu.heliovo.dpas.ie.services.common.dao.interfaces.ShortNameQueryDao;
import eu.heliovo.dpas.ie.services.common.transfer.ResultTO;
import eu.heliovo.dpas.ie.services.common.utils.ConnectionManager;


public class ShortNameQueryDaoImpl implements ShortNameQueryDao {
		
	public ShortNameQueryDaoImpl(){ 
						
	}
	Connection con = null;
	Statement st = null;
	ResultSet rs=null;
	protected final  Logger logger = Logger.getLogger(this.getClass());
	
	
	@Override
	public void loadProviderAccessTable(String fileName) throws DetailsNotFoundException {
		String sUrl=null;
		try{
			System.out.println("  :  -----> Setting uploaded file for provider access table ----->");
			String query="SET TABLE pat SOURCE "+"\""+fileName+"\"";
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
	}

