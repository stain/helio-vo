/* #ident	"%W%" */
package eu.heliovo.ics.common.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import org.hibernate.Query;
import org.hibernate.Session;

import eu.heliovo.ics.common.dao.exception.DataNotFoundException;
import eu.heliovo.ics.common.dao.exception.ShortNameQueryException;
import eu.heliovo.ics.common.dao.interfaces.ShortNameQueryDao;
import eu.heliovo.ics.common.transfer.CommonResultTO;
import eu.heliovo.ics.common.util.CommonUtils;
import eu.heliovo.ics.common.util.ConnectionManager;
import eu.heliovo.ics.common.util.HibernateSessionFactory;


public class ShortNameQueryDaoImpl implements ShortNameQueryDao {
		
	public ShortNameQueryDaoImpl() { 
						
	}

	
	public CommonResultTO getSNQueryResult(String sSql, HashMap<String,String> hmArgs) throws ShortNameQueryException 
	{
		return getSNQueryResult(sSql, hmArgs,0,-1);
	}

	
	@SuppressWarnings("deprecation")
	public CommonResultTO getSNQueryResult(String sSql, HashMap<String,String> hmArgs, int startRow, int noOfRecords) throws ShortNameQueryException 
	{
		long lStart=System.currentTimeMillis();
		 

		Connection con = null;
		Statement st = null;
		ResultSetMetaData rms =null;
		ResultSet rs=null;
		CommonResultTO result = new CommonResultTO();
 
		try 
		{
			String sRepSql = CommonUtils.replaceParams(sSql, hmArgs);
				
			con = ConnectionManager.getConnection();
			st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs= st.executeQuery(sRepSql);
     		rms = rs.getMetaData();

			 int i=0; // This Is for mySQL

			ArrayList<Object[]> arr = new ArrayList<Object[]>();
			int colCount = rms.getColumnCount();			
			
			rs.last();
			int cnt = rs.getRow();			
			result.setCount(cnt);
			
			
			/*if noOfRecords == -1 means we need complete Result till end*/
			if(noOfRecords==-1)
			{
				startRow =0;
				noOfRecords = cnt;
			}
			
			if(cnt!=0)
			{
				rs.absolute(startRow + 1);
				do {
					i++;
					Object[] X = new Object[colCount];
					for (int g = 0; g < colCount; g++) {
						X[g] = rs.getString(g + 1);
					}
					arr.add(X);
				 }while(rs.next()&& i<noOfRecords); 				
				result.setResult(arr.toArray());
			}
			if(rms!=null)
			{
				rms = null;
			}
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
			throw new ShortNameQueryException("EXCEPTION ", e);
		} 
		finally
		{
			try {
				if(rms!=null)
				{
					rms = null;
				}
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
		long lEnd=System.currentTimeMillis();		

		return result;
	}



	
}
