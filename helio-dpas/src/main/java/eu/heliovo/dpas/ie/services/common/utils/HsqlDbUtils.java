package eu.heliovo.dpas.ie.services.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import org.apache.log4j.Logger;

import eu.heliovo.dpas.ie.services.CommonDaoFactory;
import eu.heliovo.dpas.ie.services.common.dao.exception.DetailsNotFoundException;
import eu.heliovo.dpas.ie.services.common.dao.interfaces.ShortNameQueryDao;
import eu.heliovo.dpas.ie.services.common.transfer.ResultTO;


public class HsqlDbUtils {
	
	 protected final  Logger logger = Logger.getLogger(this.getClass());
	 private static ClassLoader loader;
	 
	 
	 private HsqlDbUtils(){
	 }
	 
	 public static HsqlDbUtils getInstance() {
			return HsqlProfilerHolder.instance;
	 }

	 private static class HsqlProfilerHolder {
		private static HsqlDbUtils instance = new HsqlDbUtils();
	 }
	 
	 /*
	  * 
	  */
	 public synchronized void  loadProviderAccessTable(String fileName) throws DetailsNotFoundException
	 {
		 ShortNameQueryDao shortNameDao= CommonDaoFactory.getInstance().getShortNameQueryDao();
		 shortNameDao.loadProviderAccessTable(fileName);
	 }
	 
	 /*
	  * 
	  */
	 public synchronized ResultTO[]  getAccessTableBasedOnInst(String instName) throws DetailsNotFoundException
	 {
		 ShortNameQueryDao shortNameDao= CommonDaoFactory.getInstance().getShortNameQueryDao();
		 return shortNameDao.getAccessTableBasedOnInst(instName);
	 }
	
	 
	 /*
	 public void dropTable(){
		 try{
		 Connection con = null;
		 Statement st = null;
		 ResultSet rs=null;
		 Properties prop=HsqlDbUtils.getInstance().loadPropertyValues();
		 //Connecting to database.						
		 con = ConnectionManager.getConnectionLongRunningQuery(prop);
		 st = con.createStatement();
	     st.executeUpdate("drop table job_url_table ");
		 con.commit();
		 prop=null;
		 }catch(Exception e ){
			 e.printStackTrace();
		 }
	 }
    */
}