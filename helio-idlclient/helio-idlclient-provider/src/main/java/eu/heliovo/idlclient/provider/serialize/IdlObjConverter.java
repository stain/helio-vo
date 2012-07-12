package eu.heliovo.idlclient.provider.serialize;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.commons.beanutils.PropertyUtilsBean;

import eu.heliovo.idlclient.model.IdlName;

/**
 * Utility to convert
 * @author Matthias Meyer at fhnw ch
 * @author Marco Soldati at fhnw ch
 *
 */
public class IdlObjConverter {
	
	/**
	 * singleton instance
	 */
	private static final IdlObjConverter instance = new IdlObjConverter();
	
	/**
	 * Get the singleton instance of the IdlConverter
	 * @return the idl converter
	 */
	public static IdlObjConverter getInstance() {
		return instance;
	}
	
	/**
	 * Hide constructor
	 */
	private IdlObjConverter() {
		
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
	public String idlSerialize(Object bean)
	{
		StringBuilder out = new StringBuilder();
		StringBuilder _define = new StringBuilder();
		StringBuilder _data = new StringBuilder();
		
		ArrayList<String> objects = new ArrayList<String>();
		
		idlserialize_recursive(bean, null, _data, out, null, objects);
		
		out.append(_define).append("function HELIOIDLAPI\nreturn, ").append(_data).append("\nend");

		int index = out.lastIndexOf("return, ptr_new(");
		if(index > 0)
		{
			index += 8;
			out.delete(index, index+8);
			out.delete(out.length()-5, out.length()-4);
		}
	
		//System.out.println(out);
		
		return out.toString();
	}
	
	/**
	 * Recursive IDL serialize method.
	 * @param bean for serialize. Null is not allowed.
	 * @return String with serialized bean object
	 */
	@SuppressWarnings("unchecked")
	private String idlserialize_recursive(Object bean, String key, StringBuilder data, StringBuilder retval, StringBuilder define, ArrayList<String> objects) {

		//Check if value is null
		if(bean == null)
		{
			//add null value to output
			//System.out.println("obj is null");
			data.append("PTR_NEW()");
			
			if(define != null)
			{
				define.append(", ").append(key).append(" : PTR_NEW()");
				//init.append(bean.toString()).append(", ");
			}
			
			return null;
		}
		
		Collection<Object> collection = null;
		
		//Check if value is a String
		if(bean instanceof String)
		{
			//add value to output
			//System.out.println("obj is string");
			data.append("'").append(this.escapeQuotes(bean.toString())).append("'");
			
			if(define != null)
			{
				define.append(", ").append(key).append(" : ''");
				//init.append(bean.toString()).append(", ");
			}
			return null;
		}
		

		//Check if value is Boolean
		if(bean instanceof Boolean)
		{
			//add value to output
			//System.out.println("obj is Boolean");
			if((Boolean) bean) data.append("'").append("1b").append("'");
			else data.append("'").append("0b").append("'");
			
			if(define != null)
			{
				define.append(", ").append(key).append(" : ''");
				//init.append(bean.toString()).append(", ");
			}
			return null;
		}
		
		
		//Check if value is a Number (int, double, float...)
		if(bean instanceof Number)
		{
			//add value to output
			//System.out.println("obj is number");
			data.append(bean.toString());

			if(define != null)
			{
				//init.append(bean.toString()).append(", ");
				define.append(", ").append(key).append(" : ");
				
				define.append("0");
				
				if(bean instanceof Integer)
				{
					define.append("L");
				}
					
				if(bean instanceof Float)
				{
					define.append(".");
				}
					
				if(bean instanceof Double)
				{
					define.append("D");
				}
			}
			return null;
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
			//open idl systax for arrays (name : ptr_new([e1,e2]))
			if(define != null)
			{
				define.append(", ").append(key).append(" : ptr_new()");
			}
			data.append("ptr_new([ ");
			
			//iterate through all elements.			
			boolean first = true;
			for (Object item : collection) {
				if (first) {
					first = false;
				} else {
					data.append(", ");
				}
				//Call this method recursive for every element with define=null.
				idlserialize_recursive(item, null, data, retval, null, objects);					
			}
			
			//Close idl array syntax
			data.append(" ])");
			
			return null;
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
			
			//Convert the bean object to a map with beanutils
			try {
				map = beanutil.describe(bean);
			} catch (IllegalAccessException e) {
				throw new RuntimeException("Unable to access bean " + bean + ": " + e.getMessage(), e);
			} catch (InvocationTargetException e) {
				throw new RuntimeException("Unable to access bean " + bean + ": " + e.getMessage(), e);
			} catch (NoSuchMethodException e) {
				throw new RuntimeException("Unable to access bean " + bean + ": " + e.getMessage(), e);
			}
			
			if(map.isEmpty()) return null;
			
			//Obsolete???
			/*for(Method met : bean.getClass().getDeclaredMethods())
			{
				if(met.getAnnotation(IdlName.class) != null)
				{
					Class<?>[] paramTypes = met.getParameterTypes();
					if(met.getReturnType() == String.class && paramTypes.length == 0)
					{
						try {
							Object idlname = met.invoke(bean, (Object[])null);
						} catch (IllegalArgumentException e) {
							throw new RuntimeException("Unable to access method " + met + " on " + bean + ": " + e.getMessage(), e);
						} catch (IllegalAccessException e) {
							throw new RuntimeException("Unable to access method " + met + " on "  + bean + ": " + e.getMessage(), e);
						} catch (InvocationTargetException e) {
							throw new RuntimeException("Unable to access method " + met + " on "  + bean + ": " + e.getMessage(), e);
						}
					}
				}
			}*/
			
			StringBuilder _initHead = null;
			StringBuilder _init = null;
			StringBuilder _define = null;
			

			boolean objExists = true;
			if(Collections.binarySearch(objects, bean.getClass().getSimpleName()) < 0)
			{
				objExists = false;
				objects.add(bean.getClass().getSimpleName());
				Collections.sort(objects);
			}
			
			//Initialize new StringBuilder vars for this object
			if(objExists == false)
			{
				_initHead = new StringBuilder();
				_init = new StringBuilder();
				_define = new StringBuilder();
			}
			
			//System.out.println("obj is bean");
			//start idl syntax for objects
			data.append("obj_new('").append(bean.getClass().getSimpleName()).append("', ");
			if(define != null)
			{
				define.append(", ").append(key).append(" : obj_new()");
			}

			if(objExists == false)
			{
				_initHead.append("function ").append(bean.getClass().getSimpleName()).append("::init");
				_define.append("pro ").append(bean.getClass().getSimpleName()).append("__define\nself = { ").append(bean.getClass().getSimpleName());
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
						data.append(", ");
					}
					if(objExists == false)
					{
						_initHead.append(", ").append(pairs.getKey());
						_init.append("\nself.").append(pairs.getKey()).append(" = ").append(pairs.getKey());
					}
					idlserialize_recursive(pairs.getValue(), pairs.getKey(), data, retval, _define, objects);
				}
			}
			
			//close idl object syntax
			data.append(")");
			if(objExists == false)
			{
				_init.append("\nreturn, 1\nend\n\n");
				_define.append(", inherits heliovo_framework }\nend\n\n");
				//merge the idl code from initHead, init and define to retval 
				retval = retval.append(_initHead).append("\nreturn_value = self->heliovo_framework::INIT()").append(_init).append(_define);
			}
			
			return null;
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
