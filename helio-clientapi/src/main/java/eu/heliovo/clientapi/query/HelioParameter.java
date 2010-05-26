package eu.heliovo.clientapi.query;

import java.util.Arrays;

/**
 * Description of a helio parameter.
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
	
	/**
	 * Create the helio parameter
	 * @param paramName the name of the param. must not be null.
	 * @param description the description of the parameter in user friendly format. May be null.
	 * @param xsdType the type of the param in XML Schema definition. Must not be null
	 * @param valueDomain the value domain. Will be ignored if null. 
	 */
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
	 * Get the description of the parameter. Any string is applicable. May be null.
	 * @return 
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * The data type as XML Schema type. Must not be null. 
	 * @return the type
	 */
	public String getXsdType() {
		return xsdType;
	}

	/**
	 * The domain of allowed values. May be null.
	 * @return the value domain.
	 */
	public Object[] getValueDomain() {
		return valueDomain;
	}

	/**
	 * The domain of allowed values. May be null.
	 * @param valueDomain the value domain.
	 */
	public void setValueDomain(Object[] valueDomain) {
		this.valueDomain = valueDomain;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(paramName).append("::").append(xsdType);
		if (valueDomain != null) 
			sb.append(" [domain:").append(Arrays.toString(valueDomain)).append("]");
		if (description != null)
			sb.append(" - ").append(description);		
		return sb.toString();
	}
	
	/**
	 * Mark values of the {@link HelioParameter#getValueDomain()} as being annotated. 
	 * This allows clients to provide additional information about a specific value in the domain.  
	 * @author marco soldati at fhnw ch
	 *
	 */
	public static interface AnnotatedDomainValue<T extends Object> {
		/**
		 * Get the actual value.
		 * @return the value.
		 */
		public T getValue();
		
		/**
		 * The description of this value.
		 * @return the description.
		 */
		public String getDescription();
	}
	
	/**
	 * Container for a value of the value domain
	 * @author marco soldati at fhnw ch
	 *
	 */
	public static class AnnotatedObject<T extends Object> implements AnnotatedDomainValue<T> {
		/**
		 * The value store in this object.
		 */
		private final T value;
		
		/**
		 * A user readable description of the value.
		 */
		private final String desc;

		/**
		 * Create an annotated value.
		 * @param value the value. 
		 * @param description the description of the value.
		 */
		public AnnotatedObject(T value, String description) {
			this.value = value;
			this.desc = description;			
		}
		
		/**
		 * Return the actual value.
		 * @return the actual value.
		 */
		public T getValue() {
			return value;
		}
		
		@Override
		public String getDescription() {
			return desc;
		}		

		@Override
		public String toString() {
			return value.toString();
		}
	}
}
