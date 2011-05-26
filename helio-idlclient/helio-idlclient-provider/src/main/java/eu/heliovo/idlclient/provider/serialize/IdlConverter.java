package eu.heliovo.idlclient.provider.serialize;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtilsBean;

/**
 * Utility to convert
 * @author Matthias Meyer at fhnw ch
 * @author Marco Soldati at fhnw ch
 *
 */
public class IdlConverter {
	
	/**
	 * singleton instance
	 */
	private static final IdlConverter instance = new IdlConverter();
	
	/**
	 * Get the singleton instance of the IdlConverter
	 * @return the idl converter
	 */
	public static IdlConverter getInstance() {
		return instance;
	}
	
	/**
	 * Hide constructor
	 */
	private IdlConverter() {
		
	} 
	
	/**
	 * 
	 */
	private final Map<Class<?>, Class<?>> serialisationHandler = new HashMap<Class<?>, Class<?>>();
	
	/**
	 * Register a serialisation handler for a given implementation.
	 * @param baseClass the base class.
	 * @param wrapperInterface the wrapping interface.
	 */
	public void registerSerialisationHandler(Class<?> implementation, Class<?> wrapperInterface) {
		serialisationHandler.put(implementation, wrapperInterface);
		//System.out.println(implementation.getName() + " registered");
	}
	
	/**
	 * IDL serializer. Converts Java beans to idl structs.
	 * @param bean for serialize to idl struct. Null is not allowed.
	 * @return serialized string with idl syntax for struct.
	 */
	public String idlserialize(Object bean)
	{
		StringBuilder out = new StringBuilder();
		
		out.append("var =").append(idlserialize_recursive(bean));
		
		return out.toString();
	}
	
	/**
	 * Recursive IDL serialize method.
	 * @param bean for serialize. Null is not allowed.
	 * @return String with serialized bean object
	 */
	@SuppressWarnings("unchecked")
	private String idlserialize_recursive(Object bean) {

		Collection<Object> collection = null;
	    
	    //Check if value is null
		if(bean == null)
		{
			//add null value to output
			//System.out.println("obj is null");
			return "PTR_NEW()";
		}
		
		//Check if value is a String
		if(bean instanceof String)
		{
			//add value to output
			//System.out.println("obj is string");
			return "'" + escapeQuotes(bean.toString()) + "'";
		}
		
		//Check if value is a Number (int, double, float...)
		if(bean instanceof Number)
		{
			//add value to output
			//System.out.println("obj is number");
			return bean.toString();
		}
				
		//Check if value is an arrays
		if(bean.getClass().isArray())
		{
			//System.out.println("obj is array");
			//Check if array is primitive
			if(bean.getClass().getComponentType().isPrimitive())
			{
				//build collection with boxed value
				collection  = new ArrayList<Object>();
				int len = Array.getLength(bean);
				for (int i = 0; i < len; i++) {
					Object val = Array.get(bean, i);
					collection.add(val);
				}
			}
			else
			{
				//build collection
				collection = Arrays.asList((Object[])bean);
			}
			
		}
		
		//check if value is a collection.
		if(bean instanceof Collection<?>)
		{
			//System.out.println("obj is collection");
			//Cast value to a collection
			collection = (Collection<Object>)bean;
		}
		
		if(collection != null)
		{
		    StringBuilder output = new StringBuilder();
		    
			//open idl systax for arrays (name:[e1,e2])
			output.append("[");
			
			//iterate through all elements.
			
			boolean first = true;
			for (Object item : collection) {
				if (first) {
					first = false;
				} else {
					output.append(", ");
				}
				//Call this method recursive for every element.
				output.append(idlserialize_recursive(item));					
			}
			
			//Close idl array syntax
			output.append("]");
			
			return output.toString();
		}
		
		if(bean instanceof Object)
		{
			//expose beans a proxy if required
			Class<?> wrapperClass = null;
		    for (Map.Entry<Class<?>, Class<?>> handler : serialisationHandler.entrySet()) {
				if (handler.getKey().isAssignableFrom(bean.getClass())) {
					wrapperClass = handler.getValue();
					break;
				}
			}
		    if (wrapperClass != null) {
				try {
					Constructor<?>[] constructors = wrapperClass.getConstructors();
					for (Constructor<?> con : constructors) {
						Class<?>[] paramTypes = con.getParameterTypes();
						if (paramTypes.length != 1) {
							continue;
						}
						if (paramTypes[0].isAssignableFrom(bean.getClass())) {
							bean = con.newInstance(bean);
							break;
						}
					}
				} catch (Exception e) {
					throw new RuntimeException("Unable to create wrapper for bean " + bean + ": " + e.getMessage(), e);
				}
		    }
		    
			PropertyUtilsBean beanutil = new PropertyUtilsBean();
			Map<String, Object> map;
		    StringBuilder output = new StringBuilder();
			//System.out.println("obj is bean");
			
			//Convert the bean object to a map with beanutils
			try {
				map = beanutil.describe(bean);
			} catch (IllegalAccessException e) {
				System.out.println("null");
				return null;
				//throw new RuntimeException("Unable to access bean " + bean + ": " + e.getMessage(), e);
			} catch (InvocationTargetException e) {
				System.out.println("null");
				return null;
				//throw new RuntimeException(e);
			} catch (NoSuchMethodException e) {
				System.out.println("null");
				return null;
				//throw new RuntimeException(e);
			}
			
			if(map.isEmpty()) return null;
			
			//start idl syntax for named structs.
			output.append("{");
			Object beanname = map.get("idlstrucname");
			if(beanname instanceof String)
			{
				String bname = (String)beanname;
				output.append(bname).append(", ");
			}
			
			//iterate through every key/value pair of the map
			boolean first = true;
			for (Map.Entry<String, Object> pairs : map.entrySet()) {
				if(pairs.getKey().compareTo("class") != 0)
				//if(pairs.getValue().getClass() == Class.class)
				{
					if (first) {
						first = false;
					} else {
						output.append(", ");
					}
					output.append(pairs.getKey() + ":" + idlserialize_recursive(pairs.getValue()));
				}
			}
			
			output.append("}");
			
			return output.toString();
		}
		
		return null;
	}
	
	/**
	 * Escape single quotes (') with double single quotes ('')
	 * @param text the text to escape
	 * @return the escaped string
	 */
	private String escapeQuotes(String text) {
	    return text.replaceAll("'", "''");
	}
}
