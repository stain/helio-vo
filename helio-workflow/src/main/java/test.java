import eu.heliovo.workflow.clients.dpas.QueryServiceService;
import eu.heliovo.workflow.clients.hec.HECService;


public class test
{

  /**
   * @param args
   */
  public static void main(String[] args)
  {
    // TODO Auto-generated method stub
    String a=new HECService().getHECPort().sql("SELECT * FROM goes_xray_flare WHERE  time_start>='2006-03-01 00:00:00' AND time_start<'2006-03-15 00:00:00'  ORDER BY ntime_start;");
    
    String b=new QueryServiceService().getQueryService().simpleQuery("","","");
    
    System.out.println(b);
  }

}
