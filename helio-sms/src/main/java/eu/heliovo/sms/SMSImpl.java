
package eu.heliovo.sms;

import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;

import org.apache.log4j.Logger;

import eu.heliovo.sms.ontology.QueryOntology;



@WebService (endpointInterface="eu.heliovo.sms.SMS")
public class SMSImpl implements SMS {
	QueryOntology ont;
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * General constructor 
	 */
	public SMSImpl(){
		ont = new QueryOntology();
	}


    /**
     * 
     * @param phenomenon term from the ontology; use getKnownPhenomena to acquire known terms
     * @return
     *     returns java.util.List<java.lang.String> a list of string terms which consists of 
     *     semantically equivalent terms
     * @throws SmsFault_Exception
     */
    public List<String> getEquivalents(String phenomenon)
        throws SmsFault_Exception {
    	try {
    		return ont.getEquivalents(phenomenon);
    	}
    	catch(Exception ex){
    		logger.error(ex.getMessage());
    		SmsFault fault = new SmsFault();
    		fault.setFaultInfo(ex.getMessage());
    		fault.setFaultMessage(ex.getMessage());
    		throw new SmsFault_Exception(ex.getMessage(),fault);
    	}
    }

    /**
     * 
     * @param phenomenon term from the ontology; use getKnownPhenomena to acquire known terms
     * @return
     *     returns java.util.List<java.lang.String> a list of string terms which are either equivalent, 
     *     child terms or the parent terms
     * @throws SmsFault_Exception
     */
    public List<String> getRelated(String phenomenon)
        throws SmsFault_Exception {
	    try {
			return ont.getRelated(phenomenon);
		}
		catch(Exception ex){
			logger.error(ex.getMessage());
			SmsFault fault = new SmsFault();
			fault.setFaultInfo(ex.getMessage());
			fault.setFaultMessage(ex.getMessage());
			throw new SmsFault_Exception(ex.getMessage(),fault);
		}
    }

    /**
     * 
     * @param phenomenon term from the ontology; use getKnownPhenomena to acquire known terms
     * @return
     *     returns java.util.List<java.lang.String> returns the name of HEC tables; use these names to 
     *     query HEC for data to input phenomenon
     * @throws SmsFault_Exception
     */
    public List<String> getHECListNames(String phenomenon)
        throws SmsFault_Exception {
    	ArrayList<String> list = new ArrayList<String>();
    	list.add(phenomenon);
    	return list;
    }

    /**
     * 
     * @return
     *     returns java.util.List<java.lang.String> returns a list of sting terms which are child 
     *     concepts to 'phenomenon' these terms can be used with the other functions of this web service
     * @throws SmsFault_Exception
     */
    public List<String> getKnownPhenomena()
        throws SmsFault_Exception {
    	try {
    		return ont.getOwlClass("Phenomenon");
    	}
    	catch(Exception ex){
    		logger.error(ex.getMessage());
    		SmsFault fault = new SmsFault();
    		fault.setFaultInfo(ex.getMessage());
    		fault.setFaultMessage(ex.getMessage());
    		throw new SmsFault_Exception(ex.getMessage(),fault);
    	}
    }
}
