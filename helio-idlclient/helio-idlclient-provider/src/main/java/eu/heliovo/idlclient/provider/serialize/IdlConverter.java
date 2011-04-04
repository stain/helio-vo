package eu.heliovo.idlclient.provider.serialize;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtilsBean;

public class IdlConverter {
	
	/**
	 * IDL serializer. Converts Java beans to idl structs.
	 * @param bean for serialize to idl struct. Null is not allowed.
	 * @return serialized string with idl syntax for struct.
	 */
	public static String idlserialize(Object bean)
	{
		StringBuilder out = new StringBuilder();
		
		out.append("str =").append(idlserialize_recursive(bean));
		
		return out.toString();
	}
	
	/**
	 * Recursive IDL serialize method.
	 * @param bean for serialize. Null is not allowed.
	 * @return String with serialized bean object
	 */
	private static String idlserialize_recursive(Object bean) {
		
		PropertyUtilsBean beanutil = new PropertyUtilsBean();
		Map map;
		StringBuilder output = new StringBuilder();
		
		//Convert the bean object to a map with beanutils
		try {
			map = beanutil.describe(bean);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Unable to access bean " + bean + ": " + e.getMessage(), e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
		
		//start idl syntax for named structs.
		output.append("{");
		Object beanname = map.get("name");
		if(beanname instanceof String)
		{
			String bname = (String)beanname;
			output.append(bname).append(",");
		}

		
		//iterate through every key/value pair of the map
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry)it.next();
			
			//Check if value is null
			if(pairs.getValue() == null)
			{
				//add key and null value to output
				output.append(pairs.getKey() + ":_FillValue, ");
			}
	
			//Check if value is a Number (int, double, float...)
			else if(pairs.getValue() instanceof Number)
			{
				//add key and value with idl syntax to output (key:value, )
				output.append(pairs.getKey() + ":" + pairs.getValue() + ", ");
			}
			
			//Check if value is a String
			else if(pairs.getValue() instanceof String)
			{
				//add key and value with idl syntax to output (key:'value', )
				output.append(pairs.getKey() + ":'" + pairs.getValue() + "', ");
			}
			
			//Check if value is an arrays
			else if(pairs.getValue().getClass().isArray())
			{
				//open idl systax for arrays (name:[e1,e2])
				output.append(pairs.getKey() + ":[");
				
				//Check if array is float[]
				if(pairs.getValue() instanceof float[])
				{
					System.out.println(pairs.getKey() + " is float array");
					float[] array = (float[])(pairs.getValue());
					boolean first = true;
					for (float item : array)
					{
						if (first) {
							first = false;
						} else {
							output.append(", ");
						}
						
						output.append(item);
					}
				}
				//Check if array is int[]
				else if(pairs.getValue() instanceof int[] )
				{
					System.out.println(pairs.getKey() + " is int array");
					float[] array = (float[])(pairs.getValue());
					boolean first = true;
					for (float item : array)
					{
						if (first) {
							first = false;
						} else {
							output.append(", ");
						}
						
						output.append(item);
					}
				}
				//Check if array is double[]
				else if(pairs.getValue() instanceof double[] )
				{
					System.out.println(pairs.getKey() + " is double array");
					float[] array = (float[])(pairs.getValue());
					boolean first = true;
					for (float item : array)
					{
						if (first) {
							first = false;
						} else {
							output.append(", ");
						}
						
						output.append(item);
					}
				}
				//Check if array is String[]
				else if(pairs.getValue() instanceof String[] )
				{
					System.out.println(pairs.getKey() + " is object array");
					
					//Cast value to object array.
					String[] array = (String[])(pairs.getValue());
					boolean first = true;
					for (String item : array)
					{
						if (first) {
							first = false;
						} else {
							output.append(", ");
						}
						//Call this method recursive for every element.
						output.append("'" + item + "'");	
					}
				}
				//Check if array is Object[]
				else if(pairs.getValue() instanceof Object[] )
				{
					System.out.println(pairs.getKey() + " is object array");
					
					//Cast value to object array.
					Object[] array = (Object[])(pairs.getValue());
					boolean first = true;
					for (Object item : array)
					{
						if (first) {
							first = false;
						} else {
							output.append(", ");
						}
						//Call this method recursive for every element.
						output.append(idlserialize_recursive(item));	
					}
				}
				else
				{
					throw new RuntimeException("Arraytype '" + pairs.getValue().getClass() + "' is not supported");
				}
				
				//Close idl array syntax
				output.append("], ");
			}
			
			//check if value is a collection.
			else if(pairs.getValue() instanceof Collection<?>)
			{
				//open idl systax for arrays (name:[e1,e2])
				output.append(pairs.getKey() + ":[");
				
				//Cast value to a collection and iterate through all elements.
				Collection<?> collection = (Collection<?>)pairs.getValue();
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
				output.append("], ");
			}
		}
		//delete separator after last element
		output.delete(output.length()-2, output.length());
		
		//close idl syntax
		output.append("}");
		
		return output.toString();
	}
}
