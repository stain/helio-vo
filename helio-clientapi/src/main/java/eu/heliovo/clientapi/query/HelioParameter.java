package eu.heliovo.clientapi.query;

/**
 * Description of a helio parameter .
 * @author marco soldati at fhnw ch
 *
 */
public class HelioParameter {
	/**
	 * Name of the parameter.
	 */
	private final String paramName;
	
	/**
	 * General description of the parameter.
	 */
	private final String description;
	
	/**
	 * The type of the param as XSD type.
	 */
	private final String xsdType;
	
	/**
	 * Enumeration of allowed values or null if not applicable.
	 */
	private Object[] valueDomain;
	
	public HelioParameter(String paramName, String description, String xsdType, Object[] valueDomain) {
		this.paramName = paramName;
		this.description = description;
		this.xsdType = xsdType;
		this.valueDomain = valueDomain;
	}

	/**
	 * Get the name of the parameter. Case sensitive. Must not be null.
	 * @return the name
	 */
	public String getParamName() {
		return paramName;
	}

	/**
	 * Get the description of the parameter. Any string is applicable.
	 * @return 
	 */
	public String getDescription() {
		return description;
	}

	public String getXsdType() {
		return xsdType;
	}


	public Object[] getValueDomain() {
		return valueDomain;
	}

	public void setValueDomain(Object[] valueDomain) {
		this.valueDomain = valueDomain;
	}
	
}
