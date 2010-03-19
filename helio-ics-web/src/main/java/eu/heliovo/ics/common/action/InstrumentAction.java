package eu.heliovo.ics.common.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;


import javax.servlet.http.HttpServletRequest;
import com.opensymphony.xwork2.ActionSupport;
import eu.heliovo.ics.common.dao.CommonDaoFactory;
import eu.heliovo.ics.common.dao.interfaces.CommonDao;
import eu.heliovo.ics.common.dao.interfaces.InstrumentDao;
import eu.heliovo.ics.common.dao.interfaces.ShortNameQueryDao;
import eu.heliovo.ics.common.transfer.InstrumentsTO;
import eu.heliovo.ics.common.transfer.criteriaTO.InstrumentCriteriaTO;
import eu.heliovo.ics.common.util.ConstantKeywords;
import eu.heliovo.ics.common.util.HibernateSessionFactory;

public class InstrumentAction  extends CommonAction
{	
	
    public String getSearchUpdateInstruments(){  
    	try{
    	 System.out.println(" +++++++++ Starting hibernate call ++++++" +getInsName());
    	 InstrumentCriteriaTO insCriteriaTO=new InstrumentCriteriaTO();  
    	 insCriteriaTO.setInsName(getInsName());
    	 int intCmbNoOfPage = new Integer(cmbNoOfPage);     	
    	 System.out.println(" ************** : Instruemnt Query :************"+insCriteriaTO.getQuery());
		 int noOfPage = (intCmbNoOfPage-1) * insCriteriaTO.getIRowsPerPage();
		 insCriteriaTO.setIPageNumber(noOfPage);    	
    	 setNoOfPages(new int[]{1});     	
    	 CommonDao commonNameDao= CommonDaoFactory.getInstance().getCommonDAO();
    	 insCriteriaTO=commonNameDao.getInstrumentDetails(insCriteriaTO);
    	 if(insCriteriaTO.getNoOfPages()!=null)
		 {
				int[] numPage = new int[insCriteriaTO.getNoOfPages()]; 
				for (int i=0;i<insCriteriaTO.getNoOfPages();i++){ 
					numPage[i] = i+1 ;                         
				} 
				this.setNoOfPages(numPage); 
				setInsDetailsTO(insCriteriaTO.getInsDetailsTO());		
		 }else
		 {
				this.setNoOfPages(new int[]{1}); 
				setInsDetailsTO(null);
		 }
    	 System.out.println(" +++++++ End of hibernate call+++++++++");
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return "SUCCESS";
    }
    
    public String getAddInstrumentsPage(){
    	setInsHourList();
    	setInsMinutesList();
    	return "SUCCESS";
    }
    
    public String saveInstrument(){
    	try{
	    	
	    	System.out.println("txtInsName  : "+txtInsName);
	    	System.out.println("txtInsObsName  : "+txtInsObsName);
	    	System.out.println("txtInsDes  : "+txtInsDes);
	    	System.out.println("insStartDate  : "+insStartDate);
	    	System.out.println("insEndDate  : "+insEndDate);
	    	System.out.println("cmbEndInsHour  : "+cmbEndInsHour);
	    	System.out.println("cmbEndInsMinutes  : "+cmbEndInsMinutes);
	    	InstrumentsTO insTO=new InstrumentsTO();
	    	insTO.setInsId(txtInsName);
	    	insTO.setInsName(txtInsDes);
	    	insTO.setInsObs(txtInsObsName);
	    	insTO.setInsStartDate(insStartDate+" "+cmbStartInsHour+":"+cmbStartInsMinutes);
	    	insTO.setInsEndDate(insEndDate+" "+cmbEndInsHour+":"+cmbEndInsMinutes);
	    	InstrumentDao insDao=CommonDaoFactory.getInstance().getInstrumentDao();
	    	insDao.saveInstrumentDetails(insTO);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return getSearchUpdateInstruments();
    }
    
    
    public String editInstrumentDetails()
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
    	InstrumentDao insDao=CommonDaoFactory.getInstance().getInstrumentDao();
    	InstrumentsTO instrumentTo=insDao.editInstrumentDetails(intInsId);
    	System.out.println("   :++++++++ Instruemnt Id +++++++  :  "+instrumentTo.getInsId());
    	System.out.println("   :++++++++ Instruemnt Id +++++++  :  "+instrumentTo.getInsEndDate());
    	System.out.println("   :++++++++ Instruemnt Id +++++++  :  "+instrumentTo.getInsEndHour());
    	System.out.println("   :++++++++ Instruemnt Id +++++++  :  "+instrumentTo.getInsEndMin());
    	setInstrumentTO(instrumentTo);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return "SUCCESS";
    }
    
    
    public String updateInstrument(){
    	try{
	    	System.out.println("ID   : "+insId);
	    	System.out.println("txtInsName  : "+txtInsName);
	    	System.out.println("txtInsObsName  : "+txtInsObsName);
	    	System.out.println("txtInsDes  : "+txtInsDes);
	    	System.out.println("insStartDate  : "+insStartDate);
	    	System.out.println("insEndDate  : "+insEndDate);
	    	System.out.println("cmbEndInsHour  : "+cmbEndInsHour);
	    	System.out.println("cmbEndInsMinutes  : "+cmbEndInsMinutes);
	    	InstrumentsTO insTO=new InstrumentsTO();
	    	if(insId!=null && !insId.equals(""))
	    	insTO.setId(Integer.parseInt(insId));
	    	insTO.setInsId(txtInsName);
	    	insTO.setInsName(txtInsDes);
	    	insTO.setInsObs(txtInsObsName);
	    	insTO.setInsStartDate(insStartDate+" "+cmbStartInsHour+":"+cmbStartInsMinutes);
	    	insTO.setInsEndDate(insEndDate+" "+cmbEndInsHour+":"+cmbEndInsMinutes);
	    	InstrumentDao insDao=CommonDaoFactory.getInstance().getInstrumentDao();
	    	insDao.updateInstrumentDetails(insTO);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return getSearchUpdateInstruments();
    }
    
    private String insName;
    private HashMap<String ,String> insHourList;
    private HashMap<String ,String> insMinutesList;
    private String txtInsName;
    private String txtInsObsName;
    private String txtInsDes;
    private String insStartDate;
    private String insEndDate;
    private String cmbEndInsHour;
    private String cmbEndInsMinutes;
    private String cmbStartInsHour;
    private String cmbStartInsMinutes;
    private InstrumentsTO[] insDetailsTO;
    private InstrumentsTO instrumentTO;
    private String insId;
    private boolean statusEdit;
    
    
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

	public InstrumentsTO getInstrumentTO() {
		return instrumentTO;
	}

	public void setInstrumentTO(InstrumentsTO instrumentTO) {
		this.instrumentTO = instrumentTO;
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

	public String getTxtInsName() {
		return txtInsName;
	}

	public void setTxtInsName(String txtInsName) {
		this.txtInsName = txtInsName;
	}

	public String getTxtInsObsName() {
		return txtInsObsName;
	}

	public void setTxtInsObsName(String txtInsObsName) {
		this.txtInsObsName = txtInsObsName;
	}

	public String getTxtInsDes() {
		return txtInsDes;
	}

	public void setTxtInsDes(String txtInsDes) {
		this.txtInsDes = txtInsDes;
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
	
	public InstrumentsTO[] getInsDetailsTO() {
		return insDetailsTO;
	}
	public void setInsDetailsTO(InstrumentsTO[] insDetailsTO) {
		this.insDetailsTO = insDetailsTO;
	}
	 
	
}
