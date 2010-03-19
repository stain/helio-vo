package eu.heliovo.ics.common.action;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;


import javax.servlet.http.HttpServletRequest;

import com.opensymphony.xwork2.ActionSupport;
import eu.heliovo.ics.common.dao.CommonDaoFactory;
import eu.heliovo.ics.common.dao.interfaces.CommonDao;
import eu.heliovo.ics.common.dao.interfaces.InstrumentDao;
import eu.heliovo.ics.common.dao.interfaces.InstrumentOperationPeriodDao;
import eu.heliovo.ics.common.transfer.CommonTO;
import eu.heliovo.ics.common.transfer.InstrumentOperationPeriodTO;
import eu.heliovo.ics.common.transfer.criteriaTO.InstrumentOperationPeriodCriteriaTO;


public class InstrumentOperationPeriodAction  extends CommonAction
{	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    public String getSearchUpdateInstrumentOperationPeriod(){  
    	try{
    	 System.out.println(" +++++++++ Starting hibernate call ++++++" +getInsName());
    	 InstrumentOperationPeriodCriteriaTO insOpsPeriodCriteriaTO=new InstrumentOperationPeriodCriteriaTO();    	     	
    	 int intCmbNoOfPage = new Integer(cmbNoOfPage); 
    	 insOpsPeriodCriteriaTO.setInsName(getInsName());    	 
    	 System.out.println(" ************** : Instruemnt Operation Period Query :************"+insOpsPeriodCriteriaTO.getQuery());
		 int noOfPage = (intCmbNoOfPage-1) * insOpsPeriodCriteriaTO.getIRowsPerPage();
		 insOpsPeriodCriteriaTO.setIPageNumber(noOfPage);    	
    	 setNoOfPages(new int[]{1});     	
    	 CommonDao commonNameDao= CommonDaoFactory.getInstance().getCommonDAO();
    	 insOpsPeriodCriteriaTO=commonNameDao.getInstrumentOperationPeriodDetails(insOpsPeriodCriteriaTO);
    	 if(insOpsPeriodCriteriaTO.getNoOfPages()!=null)
		 {
				int[] numPage = new int[insOpsPeriodCriteriaTO.getNoOfPages()]; 
				for (int i=0;i<insOpsPeriodCriteriaTO.getNoOfPages();i++){ 
					numPage[i] = i+1 ;                         
				} 
				this.setNoOfPages(numPage); 
				setInsDetailsOpsPeriodTO(insOpsPeriodCriteriaTO.getInsOpsPeriodDetailsTO());		
		 }else
		 {
				this.setNoOfPages(new int[]{1}); 
				setInsDetailsOpsPeriodTO(null);
		 }
    	 System.out.println(" +++++++ End of hibernate call+++++++++");
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return "SUCCESS";
    }
    
    public String getAddInstrumentOperationPeriodPage(){
    	setInsHourList();
    	setInsMinutesList();
    	setInsOpsTypeList();
    	try{
    	   InstrumentOperationPeriodDao insOpsDao=CommonDaoFactory.getInstance().getInstrumentOperationPeriodDao();
    	   setInsOpsNameList(insOpsDao.getInstrumentNames(""));
    	   List<CommonTO> insOpsDesList1=new  ArrayList<CommonTO>();
    	   setInsOpsDesList( insOpsDesList1);
    	}catch(Exception e ){
    		e.printStackTrace();
    	}
    	return "SUCCESS";
    }
    
    public String getInsDescDetails()
    {
    	try {
			String reportXML;
			HttpServletRequest request = ServletActionContext.getRequest();
	    	String insId=request.getParameter("insID");
			System.out.println("  Inst Name "+insId);
			InstrumentOperationPeriodDao insOpsDao=CommonDaoFactory.getInstance().getInstrumentOperationPeriodDao();
			reportXML = generateInstDescXML(insOpsDao.getInstrumentDescription(insId));						
			javax.servlet.http.HttpServletResponse httpResponse = ServletActionContext.getResponse();
			httpResponse.setContentType("text/xml");
			httpResponse.setHeader("Cache-Control", "no-cache");
			PrintWriter out = httpResponse.getWriter();
			out.print(reportXML);
		}catch(Exception e){
			e.printStackTrace();
		}
    	return null;
    }
    
    
    
	private String generateInstDescXML(List<CommonTO> insDecList){
		StringBuffer rptXML = new StringBuffer("<?xml version=\"1.0\"?>\n");
		try{		
			rptXML.append("<instruments>\n");
			
			if(insDecList!=null){
				Iterator<CommonTO> it= insDecList.iterator();
				while (it.hasNext()) {
					CommonTO commonTO=it.next();
					rptXML.append("<instrument>\n");
					rptXML.append("<execN><![CDATA[" + commonTO.getInsDesId() + "]]></execN>\n");
					rptXML.append("<displayN><![CDATA[" + commonTO.getInsDesName() + "]]></displayN>\n");
					rptXML.append("</instrument>\n");
				}
			}
			rptXML.append("</instruments>\n");
			return rptXML.toString();
		}catch(Exception e){							
		}
		return "";
	}
    
  
    public String saveInstrumentOperationPeriod(){
    	try{
	    	
	    	System.out.println("cmbInsOpsName  : "+cmbInsOpsName);
	    	System.out.println("cmbInsOpsType  : "+cmbInsOpsType);
	    	System.out.println("cmbInsDes  : "+cmbInsDes);
	    	System.out.println("insStartDate  : "+insStartDate);
	    	System.out.println("insEndDate  : "+insEndDate);
	    	System.out.println("cmbEndInsHour  : "+cmbEndInsHour);
	    	System.out.println("cmbEndInsMinutes  : "+cmbEndInsMinutes);
	    	InstrumentOperationPeriodTO insTO=new InstrumentOperationPeriodTO();
	    	insTO.setInsId(cmbInsOpsName);
	    	insTO.setInsName(cmbInsDes);
	    	insTO.setInsOperationType(cmbInsOpsType);
	    	insTO.setInsStartDate(insStartDate+" "+cmbStartInsHour+":"+cmbStartInsMinutes);
	    	insTO.setInsEndDate(insEndDate+" "+cmbEndInsHour+":"+cmbEndInsMinutes);
	    	InstrumentOperationPeriodDao insOpsDao=CommonDaoFactory.getInstance().getInstrumentOperationPeriodDao();
	    	insOpsDao.saveInstrumentOperationPeriodDetails(insTO);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return getSearchUpdateInstrumentOperationPeriod();
    }
    
    
    public String editInstrumentOperationPeriodDetails()
    {
    	try{
    	int intInsId=1;
    	HttpServletRequest request = ServletActionContext.getRequest();    	
    	String insId=request.getParameter("insId");
    	if(insId!=null && insId!=""){
    		intInsId=Integer.parseInt(insId);
    		setInsId(insId);
    	}
    	System.out.println("  ++++++++++++ Instrument ID ++++++++++++"+insId);
    	setInsHourList();
    	setInsMinutesList();
    	setStatusEdit(true);
    	setInsOpsTypeList();
    	InstrumentOperationPeriodDao insOpsDao=CommonDaoFactory.getInstance().getInstrumentOperationPeriodDao();
    	setInsOpsNameList(insOpsDao.getInstrumentNames(""));  	        
    	InstrumentOperationPeriodTO insOpsTo=insOpsDao.editInstrumentOperationPeriodDetails(intInsId);
    	List<CommonTO> insOpsDesListAsn=new  ArrayList<CommonTO>();
    	CommonTO insDescTO=new CommonTO();
    	insDescTO.setInsDesId(insOpsTo.getInsName());
    	insDescTO.setInsDesName(insOpsTo.getInsName());
    	insOpsDesListAsn.add(insDescTO);
  	    setInsOpsDesList( insOpsDesListAsn);
    	System.out.println("   :++++++++ Instruemnt Id +++++++  :  "+insOpsTo.getInsId());
    	System.out.println("   :++++++++ Instruemnt Id +++++++  :  "+insOpsTo.getInsEndDate());
    	System.out.println("   :++++++++ Instruemnt Id +++++++  :  "+insOpsTo.getInsEndHour());
    	System.out.println("   :++++++++ Instruemnt Id +++++++  :  "+insOpsTo.getInsEndMin());
    	setInsOpsPeriodTO(insOpsTo);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return "SUCCESS";
    }
    
    
    public String updateInstrumentOperationPeriodDetails(){
    	try{
    		System.out.println("cmbInsOpsName  : "+cmbInsOpsName);
	    	System.out.println("cmbInsOpsType  : "+cmbInsOpsType);
	    	System.out.println("cmbInsDes  : "+cmbInsDes);
	    	System.out.println("insStartDate  : "+insStartDate);
	    	System.out.println("insEndDate  : "+insEndDate);
	    	System.out.println("cmbEndInsHour  : "+cmbEndInsHour);
	    	System.out.println("cmbEndInsMinutes  : "+cmbEndInsMinutes);
	    	InstrumentOperationPeriodTO insTO=new InstrumentOperationPeriodTO();
	    	if(insId!=null && !insId.equals(""))
	    	insTO.setId(Integer.parseInt(insId));
	    	insTO.setInsId(cmbInsOpsName);
	    	insTO.setInsName(cmbInsDes);
	    	insTO.setInsOperationType(cmbInsOpsType);
	    	insTO.setInsStartDate(insStartDate+" "+cmbStartInsHour+":"+cmbStartInsMinutes);
	    	insTO.setInsEndDate(insEndDate+" "+cmbEndInsHour+":"+cmbEndInsMinutes);
	    	InstrumentOperationPeriodDao insOpsDao=CommonDaoFactory.getInstance().getInstrumentOperationPeriodDao();
	    	insOpsDao.updateInstrumentOperationPeriodDetails(insTO);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return getSearchUpdateInstrumentOperationPeriod();
    }
    
    private String insName;
    private String insOpsName;
    private HashMap<String ,String> insHourList;
    private HashMap<String ,String> insMinutesList;
    private String cmbInsOpsName;
    private String cmbInsOpsType;
    private String cmbInsDes;
    private List<CommonTO> insOpsNameList;
    private List<CommonTO> insOpsDesList;
    private HashMap<String ,String> insOpsTypeList;   
    private String insStartDate;
    private String insEndDate;
    private String cmbEndInsHour;
    private String cmbEndInsMinutes;
    private String cmbStartInsHour;
    private String cmbStartInsMinutes;
    private InstrumentOperationPeriodTO[] insDetailsOpsPeriodTO;
    private InstrumentOperationPeriodTO insOpsPeriodTO;
    private String insId;
    private boolean statusEdit;
    
        
    
	public String getInsOpsName() {
		return insOpsName;
	}

	public void setInsOpsName(String insOpsName) {
		this.insOpsName = insOpsName;
	}

	public InstrumentOperationPeriodTO[] getInsDetailsOpsPeriodTO() {
		return insDetailsOpsPeriodTO;
	}

	public void setInsDetailsOpsPeriodTO(
			InstrumentOperationPeriodTO[] insDetailsOpsPeriodTO) {
		this.insDetailsOpsPeriodTO = insDetailsOpsPeriodTO;
	}

	public InstrumentOperationPeriodTO getInsOpsPeriodTO() {
		return insOpsPeriodTO;
	}

	public void setInsOpsPeriodTO(InstrumentOperationPeriodTO insOpsPeriodTO) {
		this.insOpsPeriodTO = insOpsPeriodTO;
	}

	public boolean isStatusEdit() {
		return statusEdit;
	}

	public void setStatusEdit(boolean statusEdit) {
		this.statusEdit = statusEdit;
	}

	public String getInsId() {
		return insId;
	}

	public void setInsId(String insId) {
		this.insId = insId;
	}
	public String getCmbStartInsHour() {
		return cmbStartInsHour;
	}

	public void setCmbStartInsHour(String cmbStartInsHour) {
		this.cmbStartInsHour = cmbStartInsHour;
	}

	public String getCmbStartInsMinutes() {
		return cmbStartInsMinutes;
	}

	public void setCmbStartInsMinutes(String cmbStartInsMinutes) {
		this.cmbStartInsMinutes = cmbStartInsMinutes;
	}

	public String getCmbEndInsHour() {
		return cmbEndInsHour;
	}

	public void setCmbEndInsHour(String cmbEndInsHour) {
		this.cmbEndInsHour = cmbEndInsHour;
	}

	public String getCmbEndInsMinutes() {
		return cmbEndInsMinutes;
	}

	public void setCmbEndInsMinutes(String cmbEndInsMinutes) {
		this.cmbEndInsMinutes = cmbEndInsMinutes;
	}    

	public String getCmbInsOpsName() {
		return cmbInsOpsName;
	}

	public void setCmbInsOpsName(String cmbInsOpsName) {
		this.cmbInsOpsName = cmbInsOpsName;
	}

	public String getCmbInsOpsType() {
		return cmbInsOpsType;
	}

	public void setCmbInsOpsType(String cmbInsOpsType) {
		this.cmbInsOpsType = cmbInsOpsType;
	}

	public String getCmbInsDes() {
		return cmbInsDes;
	}

	public void setCmbInsDes(String cmbInsDes) {
		this.cmbInsDes = cmbInsDes;
	}

	

	public List<CommonTO> getInsOpsNameList() {
		return insOpsNameList;
	}

	public void setInsOpsNameList(List<CommonTO> insOpsNameList) {
		this.insOpsNameList = insOpsNameList;
	}
	

	public void setInsOpsTypeList() {
		HashMap<String, String>  insOpsTypeList=new LinkedHashMap<String, String>();
		insOpsTypeList.put("OP", "In Operation");
		insOpsTypeList.put("NP", "Not In Operation");
		//insOpsTypeList.put("NL", "Not Launched");
		this.insOpsTypeList = insOpsTypeList;
	}

	public HashMap<String, String> getInsOpsTypeList() {
		return insOpsTypeList;
	}	
	
	public List<CommonTO> getInsOpsDesList() {
	
		return insOpsDesList;
	}

	public void setInsOpsDesList(List<CommonTO> insOpsDesList) {
		
		this.insOpsDesList = insOpsDesList;
	}

	public String getInsStartDate() {
		return insStartDate;
	}

	public void setInsStartDate(String insStartDate) {
		this.insStartDate = insStartDate;
	}

	public String getInsEndDate() {
		return insEndDate;
	}

	public void setInsEndDate(String insEndDate) {
		this.insEndDate = insEndDate;
	}

	public void setInsHourList(HashMap<String, String> insHourList) {
		this.insHourList = insHourList;
	}

	public void setInsMinutesList(HashMap<String, String> insMinutesList) {
		this.insMinutesList = insMinutesList;
	}

	public HashMap<String, String> getInsHourList() {
		return insHourList;
	}

	public void setInsHourList() {
		HashMap<String, String>  insHourList=new LinkedHashMap<String, String>();
		insHourList.put("01", "1");
		insHourList.put("02", "2");
		insHourList.put("03", "3");
		insHourList.put("04", "4");
		insHourList.put("05", "5");
		insHourList.put("06", "6");
		insHourList.put("07", "7");
		insHourList.put("08", "8");
		insHourList.put("09", "9");
		insHourList.put("10", "10");
		insHourList.put("11", "11");
		insHourList.put("12", "12");
		insHourList.put("13", "13");
		insHourList.put("14", "14");
		insHourList.put("15", "15");
		insHourList.put("16", "16");
		insHourList.put("17", "17");
		insHourList.put("18", "18");
		insHourList.put("19", "19");
		insHourList.put("20", "20");
		insHourList.put("21", "21");
		insHourList.put("22", "22");
		insHourList.put("23", "23");
		this.insHourList = insHourList;
	}

	public HashMap<String, String> getInsMinutesList() {
		return insMinutesList;
	}

	public void setInsMinutesList() {
		HashMap<String, String> insMinutesList=new LinkedHashMap<String, String>();
		insMinutesList.put("00", "00");
		insMinutesList.put("01", "01");
		insMinutesList.put("02", "02");
		insMinutesList.put("03", "03");
		insMinutesList.put("04", "04");
		insMinutesList.put("05", "05");
		insMinutesList.put("06", "06");
		insMinutesList.put("07", "07");
		insMinutesList.put("08", "08");
		insMinutesList.put("09", "09");
		insMinutesList.put("10", "10");
		insMinutesList.put("11", "11");
		insMinutesList.put("12", "12");
		insMinutesList.put("13", "13");
		insMinutesList.put("14", "14");
		insMinutesList.put("15", "15");
		insMinutesList.put("16", "16");
		insMinutesList.put("17", "17");
		insMinutesList.put("18", "18");
		insMinutesList.put("19", "19");
		insMinutesList.put("20", "20");
		insMinutesList.put("21", "21");
		insMinutesList.put("22", "22");
		insMinutesList.put("23", "23");
		insMinutesList.put("24", "24");
		insMinutesList.put("25", "25");
		insMinutesList.put("26", "26");
		insMinutesList.put("27", "27");
		insMinutesList.put("28", "28");
		insMinutesList.put("29", "29");
		insMinutesList.put("30", "30");
		insMinutesList.put("31", "31");
		insMinutesList.put("32", "32");
		insMinutesList.put("33", "33");
		insMinutesList.put("34", "34");
		insMinutesList.put("35", "35");
		insMinutesList.put("36", "36");
		insMinutesList.put("37", "37");
		insMinutesList.put("38", "38");
		insMinutesList.put("39", "39");
		insMinutesList.put("40", "40");
		insMinutesList.put("41", "41");
		insMinutesList.put("42", "42");
		insMinutesList.put("43", "43");
		insMinutesList.put("44", "44");
		insMinutesList.put("45", "45");
		insMinutesList.put("46", "46");
		insMinutesList.put("47", "47");
		insMinutesList.put("48", "48");
		insMinutesList.put("49", "49");
		insMinutesList.put("50", "50");
		insMinutesList.put("51", "51");
		insMinutesList.put("52", "52");
		insMinutesList.put("53", "53");
		insMinutesList.put("54", "54");
		insMinutesList.put("55", "55");
		insMinutesList.put("56", "56");
		insMinutesList.put("57", "57");
		insMinutesList.put("58", "58");
		insMinutesList.put("59", "59");		
		this.insMinutesList = insMinutesList;
	}

	public String getInsName() {
		return insName;
	}
	public void setInsName(String insName) {
		this.insName = insName;
	}
		
}
