/* #ident	"%W%" */
package eu.heliovo.dpas.ie.services.vso.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

import eu.heliovo.dpas.ie.services.common.utils.ConstantKeywords;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.vsoi.v0_6.DataContainer;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.vsoi.v0_6.DataItem;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.vsoi.v0_6.DataRequestItem;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.vsoi.v0_6.FileidItem;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.vsoi.v0_6.GetDataItem;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.vsoi.v0_6.GetDataRequest;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.vsoi.v0_6.GetDataResponseItem;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.vsoi.v0_6.MethodItem;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.vsoi.v0_6.VSOGetDataRequest;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.vsoi.v0_6.VSOGetDataResponse;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.vsoi.v0_6.VSOiPort;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.vsoi.v0_6.VSOiService;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.vsoi.v0_6.QueryResponseBlock;




public class VsoUtils {
	
	 protected final  Logger logger = Logger.getLogger(this.getClass());
	
	  /**
	   * Reveals the base URI of the web application.
	   *
	   * @return The URI.
	   */
	  public static String getUrl(HttpServletRequest req) { 
		  String scheme = req.getScheme(); // http 
		  String serverName = req.getServerName(); // hostname.com 
		  int serverPort = req.getServerPort(); // 80 
		  String contextPath = req.getContextPath(); // mywebapp 
		  String url = scheme+"://"+serverName+":"+serverPort+contextPath+"/dpasdownload/results.fits?"; 
		  return url; 
	  }	 
	  
	  /**
	   * Append the parameter or URL.
	   * @param url
	   * @param fileId
	   * @param provider
	   * @param status
	   * @return
	   */
	  public static String appendParamtersForUrl(String url,String fileId,String provider,String status)
	  {
		 if(fileId!=null && !fileId.trim().equals("") && url!=null){
			 url=url.replaceAll("results", fileId.substring(fileId.lastIndexOf("/")+1, fileId.length()));
		 }
		 if(status!=null && !status.trim().equals("")){
			 url=url+"ID="+fileId;
			 if(provider!=null && !provider.trim().equals(""))
				 url=url+"&PROVIDER="+provider;
		 }else{
			 url=url+"ID="+fileId;
			 if(provider!=null && !provider.trim().equals(""))
				 url=url+"&PROVIDER="+provider;
		 }
		 return url;
	  }
	  
	  /**
	   * 
	   * @param date
	   * @return
	   */
	  public static String getDateFormat(String date){
		
		 try
          {
			SimpleDateFormat sdf = new SimpleDateFormat(ConstantKeywords.ORGINALDATEFORMAT.getDateFormat());
			Date dt=sdf.parse(date.replace("T", " "));
            //Converting back the format
            DateFormat dateFormat = new SimpleDateFormat(ConstantKeywords.VSODATEFORMAT.getDateFormat());
   	     	dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));	
   	     	String datetime=dateFormat.format(dt);
   	     	System.out.println(" Date and time "+datetime);
  	     	return datetime;
         } catch (Exception e)
		 {
		    e.printStackTrace();
		 }
		  return null;
	  }
	  
	  /**
	   * 
	   * @param resp
	   * @return
	   */
	/*  public static boolean getProviderResultCount(ProviderQueryResponse[]	resp)
	  {
		  boolean status=false;
		  if(resp[0]!=null && resp[0].getRecord()!=null){
			  status=true;
		  }
		  return status;
	  }*/
	  
	  /**
	   * 
	   * @param strDate
	   * @return
	   */
	  public static String changeFormat(String strDate) 
		{
		 	try
			{
				//create SimpleDateFormat object with source string date format
				SimpleDateFormat sdfSource = new SimpleDateFormat("yyyyMMddHHmmss");
				//parse the string into Date object
				Date date = sdfSource.parse(strDate);
				//create SimpleDateFormat object with desired date format
				SimpleDateFormat sdfDestination = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				//parse the date into another format
				String strDate1 = sdfDestination.format(date);
				//System.out.println("Converted date is : " + strDate1);
				return strDate1;
			}
			catch(ParseException pe)
			{
				System.out.println("Parse Exception : " + pe);
				System.out.println("+++++++++++++++++++++++++++++ Date ++++++++++++++++++++++"+strDate);
				return strDate;
			}
		}
	  
	  
	  
	public static List<DataItem> getVsoProviderUrl(List<QueryResponseBlock> resp,String provider){
	   VSOiService service   = new VSOiService();
	   VSOiPort    port      = service.getNsoVSOi();
       //Now all fileids got saved into the hashmap
       //Build the GetData request     
       
       VSOGetDataRequest vsoGDRequest = new VSOGetDataRequest();
       GetDataRequest  gdRequest = new GetDataRequest();
       //Set desired download method
       MethodItem methodItem = new MethodItem();
       List<String> getDataMethods = methodItem.getMethodtype();
       getDataMethods.add("URL-FILE");
       gdRequest.setMethod(methodItem);
       
       //Set Container information
       DataContainer dataType = new DataContainer();
       
       List<DataRequestItem> dataList = new ArrayList<DataRequestItem>();
       
       List<DataRequestItem> dataRequestList = new ArrayList<DataRequestItem>();
       
       //Go over the getDataMap hashmap and set fileid info
       //for (Object provider: getDataMap.keySet().toArray()) {
       DataRequestItem elem = new DataRequestItem();
       //since provider is being passed as an argument we could use either
       //the argument or the queryresponsebloc.  I will use the block.
       elem.setProvider((String) provider);
      
       dataRequestList.add(elem);
       FileidItem fileIdItem = new FileidItem();
       elem.setFileiditem(fileIdItem);
       List <String> fileIdList = fileIdItem.getFileid();

       for (QueryResponseBlock rec:resp) {
    	   fileIdList.add(rec.getFileid());
       }
       
       //dataList.addAll(dataRequestList); //not sure why the example code had this line.
       
       List<DataRequestItem> dri = dataType.getDatarequestitem();
       dri.addAll(dataRequestList);
       
       gdRequest.setDatacontainer(dataType);

       vsoGDRequest.setRequest(gdRequest);
       vsoGDRequest.setVersion("1");
       //Performs the GetData Request
       VSOGetDataResponse gdResponse = port.getData(vsoGDRequest);
       
       List<GetDataResponseItem> gdRespItem = gdResponse.getGetdataresponseitem();
       List<DataItem> addalldataItem =null;
       //List<DataItem> addalldataItem = new ArrayList<DataItem>();
       int count=0;
       //Read response
       for (GetDataResponseItem item:gdRespItem) {
           String _provider = item.getProvider();
           GetDataItem gdItem = item.getGetdataitem();
           List<DataItem> dataItem = gdItem.getDataitem();
           if(_provider!=null && provider!=null && _provider.trim().equals(provider)){
        	   //if(count==0)
        	   //I think we can just return the dataitem.
        		   addalldataItem=dataItem;
        	   //else
        	   //   addalldataItem.addAll(dataItem);
           }

       }
	  return addalldataItem;
  }
			
}
