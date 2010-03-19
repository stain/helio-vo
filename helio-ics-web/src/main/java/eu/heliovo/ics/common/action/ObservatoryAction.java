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
import eu.heliovo.ics.common.dao.interfaces.ObservatoryDao;
import eu.heliovo.ics.common.dao.interfaces.ShortNameQueryDao;
import eu.heliovo.ics.common.transfer.InstrumentsTO;
import eu.heliovo.ics.common.transfer.ObservatoryTO;
import eu.heliovo.ics.common.transfer.criteriaTO.InstrumentCriteriaTO;
import eu.heliovo.ics.common.transfer.criteriaTO.ObservatoryCriteriaTO;
import eu.heliovo.ics.common.util.ConstantKeywords;
import eu.heliovo.ics.common.util.HibernateSessionFactory;

public class ObservatoryAction  extends CommonAction
{	
	public String getSearchUpdateObservatory(){  
      try{
    	 System.out.println(" +++++++++ Starting hibernate call ++++++" +getObsName());
    	 ObservatoryCriteriaTO ObsCriTO=new ObservatoryCriteriaTO();   
    	 ObsCriTO.setObsName(getObsName());
    	 int intCmbNoOfPage = new Integer(cmbNoOfPage);     	 
    	 System.out.println(" ************** : Instruemnt Query :************"+ObsCriTO.getQuery());
		 int noOfPage = (intCmbNoOfPage-1) * ObsCriTO.getIRowsPerPage();
		 ObsCriTO.setIPageNumber(noOfPage);    	
    	 setNoOfPages(new int[]{1});     	
    	 CommonDao commonNameDao= CommonDaoFactory.getInstance().getCommonDAO();
    	 ObsCriTO=commonNameDao.getObservatoryDetails(ObsCriTO);
    	 if(ObsCriTO.getNoOfPages()!=null)
		 {
				int[] numPage = new int[ObsCriTO.getNoOfPages()]; 
				for (int i=0;i<ObsCriTO.getNoOfPages();i++){ 
					numPage[i] = i+1 ;                         
				} 
				this.setNoOfPages(numPage); 
				setObsDetailsTO(ObsCriTO.getObsDetailsTO());		
		 }else
		 {
				this.setNoOfPages(new int[]{1}); 
				setObsDetailsTO(null);
		 }
    	 System.out.println(" +++++++ End of hibernate call+++++++++");
       }catch(Exception e){
    		e.printStackTrace();
       }
    	return "SUCCESS";
    }
    
    public String getAddObservatoryPage(){
    	setObsHourList();
    	setObsMinutesList();
    	setObsOpsTypeList();
    	return "SUCCESS";
    }
     
    public String saveObservatory(){
    	try{
	    	System.out.println("txtObsName  : "+txtObsName);	    	
	    	System.out.println("txtObsDes  : "+txtObsDes);
	    	System.out.println("obsStartDate  : "+obsStartDate);
	    	System.out.println("obsEndDate  : "+obsEndDate);
	    	System.out.println("cmbEndObsHour  : "+cmbEndObsHour);
	    	System.out.println("cmbEndObsMinutes  : "+cmbEndObsMinutes);
	    	ObservatoryTO obsTO=new ObservatoryTO();
	    	obsTO.setObsId(txtObsName);
	    	obsTO.setObsName(txtObsDes);
	    	obsTO.setObsType(txtObsType);
	    	obsTO.setObsFirstPosition(txtObsFirstPos);
	    	obsTO.setObsSecondPosition(txtObsSecondtPos);
	    	obsTO.setObsStartDate(obsStartDate+" "+cmbStartObsHour+":"+cmbStartObsMinutes);
	    	obsTO.setObsEndDate(obsEndDate+" "+cmbEndObsHour+":"+cmbEndObsMinutes);
	    	obsTO.setObsOperationType(cmbObsOpsType);
	    	ObservatoryDao obsDao=CommonDaoFactory.getInstance().getObservatoryDao();
	    	obsDao.saveObservatoryDetails(obsTO);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return getSearchUpdateObservatory();
    }
    
    
    public String editObservatorytDetails()
    {
      try{
    	int intInsId=1;
    	HttpServletRequest request = ServletActionContext.getRequest();
    	String obsId=request.getParameter("obsId");
    	if(obsId!=null && obsId!=""){
    		intInsId=Integer.parseInt(obsId);
    		setObsId(obsId);
    	}
    	System.out.println("  ++++++++++++ Observatory ID ++++++++++++"+obsId);
    	setObsHourList();
    	setObsMinutesList();
    	setObsOpsTypeList();
    	setStatusEdit(true);
    	ObservatoryDao obsDao=CommonDaoFactory.getInstance().getObservatoryDao();
    	ObservatoryTO observatoryTO=obsDao.editObservatoryDetails(intInsId);
    	System.out.println("   :++++++++ Observatory Id +++++++  :  "+observatoryTO.getObsId());    	
    	setObservatoryTO(observatoryTO);
     }catch(Exception e){
    		e.printStackTrace();
       }
    	return "SUCCESS";
    }           
      
   
    public String updateObservatoryDetails(){
    	try{
	    	System.out.println("ID   : "+obsId);
	    	System.out.println("txtObsName  : "+txtObsName);	    	
	    	System.out.println("txtObsDes  : "+txtObsDes);
	    	System.out.println("obsStartDate  : "+obsStartDate);
	    	System.out.println("obsEndDate  : "+obsEndDate);
	    	System.out.println("cmbEndObsHour  : "+cmbEndObsHour);
	    	System.out.println("cmbEndObsMinutes  : "+cmbEndObsMinutes);
	    	
	    	ObservatoryTO obsTO=new ObservatoryTO();
	    	if(obsId!=null && !obsId.equals(""))
	    	obsTO.setId(Integer.parseInt(obsId));
	    	obsTO.setObsId(txtObsName);
	    	obsTO.setObsName(txtObsDes);
	    	obsTO.setObsType(txtObsType);
	    	obsTO.setObsFirstPosition(txtObsFirstPos);
	    	obsTO.setObsSecondPosition(txtObsSecondtPos);
	    	obsTO.setObsStartDate(obsStartDate+" "+cmbStartObsHour+":"+cmbStartObsMinutes);
	    	obsTO.setObsEndDate(obsEndDate+" "+cmbEndObsHour+":"+cmbEndObsMinutes);
	    	obsTO.setObsOperationType(cmbObsOpsType);
	    	ObservatoryDao obsDao=CommonDaoFactory.getInstance().getObservatoryDao();
	    	obsDao.updateObservatoryDetails(obsTO);
	    	
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return getSearchUpdateObservatory();
    }
    
    private String obsName;
    private HashMap<String ,String> obsHourList;
    private HashMap<String ,String> obsMinutesList;
    private String txtObsName;   
    private String txtObsDes;
    private String txtObsType;
    private String obsStartDate;
    private String obsEndDate;
    private String cmbEndObsHour;
    private String cmbEndObsMinutes;
    private String cmbStartObsHour;
    private String cmbStartObsMinutes;
    private ObservatoryTO[] obsDetailsTO;
    private ObservatoryTO observatoryTO;
    private String obsId;
    private boolean statusEdit;
    private HashMap<String ,String> obsOpsTypeList;
    private String txtObsFirstPos;
    private String txtObsSecondtPos;
    private String cmbObsOpsType;
    
    
	public String getCmbObsOpsType() {
		return cmbObsOpsType;
	}

	public void setCmbObsOpsType(String cmbObsOpsType) {
		this.cmbObsOpsType = cmbObsOpsType;
	}

	public String getTxtObsFirstPos() {
		return txtObsFirstPos;
	}

	public void setTxtObsFirstPos(String txtObsFirstPos) {
		this.txtObsFirstPos = txtObsFirstPos;
	}

	public String getTxtObsSecondtPos() {
		return txtObsSecondtPos;
	}

	public void setTxtObsSecondtPos(String txtObsSecondtPos) {
		this.txtObsSecondtPos = txtObsSecondtPos;
	}

	public String getTxtObsType() {
		return txtObsType;
	}

	public void setTxtObsType(String txtObsType) {
		this.txtObsType = txtObsType;
	}

	public ObservatoryTO[] getObsDetailsTO() {
		return obsDetailsTO;
	}

	public void setObsDetailsTO(ObservatoryTO[] obsDetailsTO) {
		this.obsDetailsTO = obsDetailsTO;
	}

	public String getObsName() {
		return obsName;
	}

	public void setObsName(String obsName) {
		this.obsName = obsName;
	}

	public boolean isStatusEdit() {
		return statusEdit;
	}

	public void setStatusEdit(boolean statusEdit) {
		this.statusEdit = statusEdit;
	}	

	public String getObsId() {
		return obsId;
	}

	public void setObsId(String obsId) {
		this.obsId = obsId;
	}

	public String getTxtObsName() {
		return txtObsName;
	}

	public void setTxtObsName(String txtObsName) {
		this.txtObsName = txtObsName;
	}

	public String getTxtObsDes() {
		return txtObsDes;
	}

	public void setTxtObsDes(String txtObsDes) {
		this.txtObsDes = txtObsDes;
	}

	public String getObsStartDate() {
		return obsStartDate;
	}

	public void setObsStartDate(String obsStartDate) {
		this.obsStartDate = obsStartDate;
	}

	public String getObsEndDate() {
		return obsEndDate;
	}

	public void setObsEndDate(String obsEndDate) {
		this.obsEndDate = obsEndDate;
	}

	public String getCmbEndObsHour() {
		return cmbEndObsHour;
	}

	public void setCmbEndObsHour(String cmbEndObsHour) {
		this.cmbEndObsHour = cmbEndObsHour;
	}

	public String getCmbEndObsMinutes() {
		return cmbEndObsMinutes;
	}

	public void setCmbEndObsMinutes(String cmbEndObsMinutes) {
		this.cmbEndObsMinutes = cmbEndObsMinutes;
	}

	public String getCmbStartObsHour() {
		return cmbStartObsHour;
	}

	public void setCmbStartObsHour(String cmbStartObsHour) {
		this.cmbStartObsHour = cmbStartObsHour;
	}

	public String getCmbStartObsMinutes() {
		return cmbStartObsMinutes;
	}

	public void setCmbStartObsMinutes(String cmbStartObsMinutes) {
		this.cmbStartObsMinutes = cmbStartObsMinutes;
	}

	public ObservatoryTO getObservatoryTO() {
		return observatoryTO;
	}

	public void setObservatoryTO(ObservatoryTO observatoryTO) {
		this.observatoryTO = observatoryTO;
	}

	public HashMap<String, String> getObsOpsTypeList() {
		return obsOpsTypeList;
	}

	public void setObsOpsTypeList() {
		HashMap<String, String>  obsOpsTypeList=new LinkedHashMap<String, String>();
		obsOpsTypeList.put("OP", "In Operation");
		obsOpsTypeList.put("NP", "Not In Operation");
		obsOpsTypeList.put("NL", "Not Launched");
		this.obsOpsTypeList = obsOpsTypeList;
	}

	public HashMap<String, String> getObsHourList() {
		return obsHourList;
	}

	public void setObsHourList() {
		HashMap<String, String>  obsHourList=new LinkedHashMap<String, String>();
		obsHourList.put("01", "1");
		obsHourList.put("02", "2");
		obsHourList.put("03", "3");
		obsHourList.put("04", "4");
		obsHourList.put("05", "5");
		obsHourList.put("06", "6");
		obsHourList.put("07", "7");
		obsHourList.put("08", "8");
		obsHourList.put("09", "9");
		obsHourList.put("10", "10");
		obsHourList.put("11", "11");
		obsHourList.put("12", "12");
		obsHourList.put("13", "13");
		obsHourList.put("14", "14");
		obsHourList.put("15", "15");
		obsHourList.put("16", "16");
		obsHourList.put("17", "17");
		obsHourList.put("18", "18");
		obsHourList.put("19", "19");
		obsHourList.put("20", "20");
		obsHourList.put("21", "21");
		obsHourList.put("22", "22");
		obsHourList.put("23", "23");
		this.obsHourList = obsHourList;
	}

	public HashMap<String, String> getObsMinutesList() {
		return obsMinutesList;
	}

	public void setObsMinutesList() {
		HashMap<String, String> obsMinutesList=new LinkedHashMap<String, String>();
		obsMinutesList.put("00", "00");
		obsMinutesList.put("01", "01");
		obsMinutesList.put("02", "02");
		obsMinutesList.put("03", "03");
		obsMinutesList.put("04", "04");
		obsMinutesList.put("05", "05");
		obsMinutesList.put("06", "06");
		obsMinutesList.put("07", "07");
		obsMinutesList.put("08", "08");
		obsMinutesList.put("09", "09");
		obsMinutesList.put("10", "10");
		obsMinutesList.put("11", "11");
		obsMinutesList.put("12", "12");
		obsMinutesList.put("13", "13");
		obsMinutesList.put("14", "14");
		obsMinutesList.put("15", "15");
		obsMinutesList.put("16", "16");
		obsMinutesList.put("17", "17");
		obsMinutesList.put("18", "18");
		obsMinutesList.put("19", "19");
		obsMinutesList.put("20", "20");
		obsMinutesList.put("21", "21");
		obsMinutesList.put("22", "22");
		obsMinutesList.put("23", "23");
		obsMinutesList.put("24", "24");
		obsMinutesList.put("25", "25");
		obsMinutesList.put("26", "26");
		obsMinutesList.put("27", "27");
		obsMinutesList.put("28", "28");
		obsMinutesList.put("29", "29");
		obsMinutesList.put("30", "30");
		obsMinutesList.put("31", "31");
		obsMinutesList.put("32", "32");
		obsMinutesList.put("33", "33");
		obsMinutesList.put("34", "34");
		obsMinutesList.put("35", "35");
		obsMinutesList.put("36", "36");
		obsMinutesList.put("37", "37");
		obsMinutesList.put("38", "38");
		obsMinutesList.put("39", "39");
		obsMinutesList.put("40", "40");
		obsMinutesList.put("41", "41");
		obsMinutesList.put("42", "42");
		obsMinutesList.put("43", "43");
		obsMinutesList.put("44", "44");
		obsMinutesList.put("45", "45");
		obsMinutesList.put("46", "46");
		obsMinutesList.put("47", "47");
		obsMinutesList.put("48", "48");
		obsMinutesList.put("49", "49");
		obsMinutesList.put("50", "50");
		obsMinutesList.put("51", "51");
		obsMinutesList.put("52", "52");
		obsMinutesList.put("53", "53");
		obsMinutesList.put("54", "54");
		obsMinutesList.put("55", "55");
		obsMinutesList.put("56", "56");
		obsMinutesList.put("57", "57");
		obsMinutesList.put("58", "58");
		obsMinutesList.put("59", "59");		
		this.obsMinutesList = obsMinutesList;
	}

	

	 
	
}
