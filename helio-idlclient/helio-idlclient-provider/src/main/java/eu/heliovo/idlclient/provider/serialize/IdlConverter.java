package eu.heliovo.idlclient.provider.serialize;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtilsBean;

//Serialisierung nach idl mit Rahmen für Zuweisung einer Variabel in idl.
public class IdlConverter {
	public static String idl(Object bean)
	{
		String out = new String();
		
		try {
			//String erzeugen mit der Metgode doit
			out = "str =" + doit(bean);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return out;
	}
	
	//Serialisierungsmethode für Javabeans nach idl syntax.
	private static String doit(Object bean) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		
		//initialisieren der Objekte
		PropertyUtilsBean beanutil = new PropertyUtilsBean();
		Map map;
		StringBuffer sb = new StringBuffer();
		
		//Bean Objekt mit beanutils in eine map konvertieren
		map = beanutil.describe(bean);
		
		//Beginn des idl struct Rahmen
		sb.append("{");
		
		//Iteration durch alle key/value Paare in der Map
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry)it.next();
			
			//überprüfen ob Value ein einfacher datentyp ist (int, double, float, string)
			if(pairs.getValue() instanceof Integer || pairs.getValue() instanceof String || pairs.getValue() instanceof Double || pairs.getValue() instanceof Float)
			{
				//Key und Value mit idl syntax anfügen;
				sb.append(pairs.getKey() + ":'" + pairs.getValue() + "', ");
			}
			
			//überprüfung ob Value eine ArrayList ist
			if(pairs.getValue() instanceof ArrayList)
			{
				//anfügen der idl systax für array
				sb.append(pairs.getKey() + ":[");
				
				//value in eine ArrayList casten und durch alle Elemente iterieren.
				ArrayList aList = (ArrayList)pairs.getValue();
				for(int i=0; i < aList.size(); ++i)
				{
					//Rekursierer Methodenaufruf für alle Objekte in der Liste
					sb.append(doit(aList.get(i))+ ", ");
				}
				
				//löschen des Trennzeichens nach dem letzten Element
				sb.delete(sb.length()-2, sb.length());
				
				//Idl Array Systax schliessen
				sb.append("], ");
			}
		}
		//löschen des Trennzeichens nach dem letzen Element
		sb.delete(sb.length()-2, sb.length());
		
		//Idl Struct systax schliessen
		sb.append("}");
		
		return sb.toString();
	}
}
