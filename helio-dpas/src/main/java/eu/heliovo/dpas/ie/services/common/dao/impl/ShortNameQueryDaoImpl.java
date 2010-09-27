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
			String query="SET TABLE pat SOURCE "+"\""+fileName+"\"";
			System.out.println("loadProviderAccessTable() method "+query);
			//Connecting to database.						
			con = ConnectionManager.getConnection();
			st = con.createStatement();
			st.execute(query);
			con.commit();
			
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
			} catch (Exception e) {
				
			}
		}
	}
	
	
		@Override
	public ResultTO[] getAccessTableBasedOnInst(String strIns) throws DetailsNotFoundException {
			int count=0;
			ResultTO[] resultTO=null;
			try{
				String query="select * from pat where helio_obs_inst='"+strIns+"'";
				System.out.println("  :  getAccessTableBasedOnInst() method :  "+strIns);
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
					//Instrument
					if(rs.getString(3)!=null)
						resultTO[count].setInst(rs.getString(3));
					//Obsevatory Id
					if(rs.getString(4)!=null)
						resultTO[count].setObsId(rs.getString(4));
					//Provider Ranking
					if(rs.getString(5)!=null)
						resultTO[count].setPvdrRanking(rs.getString(5));
					//Provider quality
					if(rs.getString(6)!=null)
						resultTO[count].setPvdrQuality(rs.getString(6));
					//Vso Int id
					if(rs.getString(7)!=null)
						resultTO[count].setPvdrVsoKey(rs.getString(7));
					count++;
                }
	
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

