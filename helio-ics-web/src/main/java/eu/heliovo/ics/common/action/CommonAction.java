package eu.heliovo.ics.common.action;

import org.hibernate.Session;


import com.opensymphony.xwork2.ActionSupport;
import eu.heliovo.ics.common.util.HibernateSessionFactory;
 
public class CommonAction  extends ActionSupport
{	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public String display(){ 
    
    	return "SUCCESS";
    }
    
    protected String cmbNoOfPage="1";
	private int[] noOfPages;

  
	public String getCmbNoOfPage() {
		return cmbNoOfPage;
	}
	public void setCmbNoOfPage(String cmbNoOfPage) {
		this.cmbNoOfPage = cmbNoOfPage;
	}
	public int[] getNoOfPages() {
		return noOfPages;
	}
	public void setNoOfPages(int[] noOfPages) {
		this.noOfPages = noOfPages;
	}


    
	
}
