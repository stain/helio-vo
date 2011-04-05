package eu.heliovo.idlclient.provider.serialize;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

public class IdlConverterTest {
	
	@Test public void testSerializeNullBean() {
		Object bean = new NullBean();
		
		String ser = IdlConverter.idlserialize(bean);
		assertEquals("str ={null:PTR_NEW()}", ser);
	}
	
	public static class NullBean {
		public Object getNull() {
			return null;
		}
	}
	
	@Test public void testSerializeStringBean() {
		Object bean = new StringBean();
		
		String ser = IdlConverter.idlserialize(bean);
		assertEquals("str ={hello world,name:'hello world'}", ser);
	}
	
	public static class StringBean {
		public String getName() {
			return "hello world";
		}
	}
	
	
	@Test public void testSerializeIntBean() {
		Object bean = new IntBean();
		
		String ser = IdlConverter.idlserialize(bean);
		assertEquals("str ={int:5}", ser);
	}
	
	public static class IntBean {
		public int getInt() {
			return 5;
		}
	}
	
	
	@Test public void testSerializeFloatBean() {
		Object bean = new FloatBean();
		
		String ser = IdlConverter.idlserialize(bean);
		assertEquals("str ={float:1.5}", ser);
	}
	
	public static class FloatBean {
		public float getFloat() {
			return 1.5f;
		}
	}
	
	
	@Test public void testSerializeDoubleBean() {
		Object bean = new DoubleBean();
		
		String ser = IdlConverter.idlserialize(bean);
		assertEquals("str ={double:6.22}", ser);
	}
	
	public static class DoubleBean {
		public double getDouble() {
			return 6.22d;
		}
	}
	
	
	@Test public void testSerializeStringArrayBean() {
		Object bean = new StringArrayBean();
		
		String ser = IdlConverter.idlserialize(bean);
		assertEquals("str ={stringArray:['Hello', 'World']}", ser);
	}
	
	public static class StringArrayBean {
		public String[] getStringArray() {
			return new String[]{"Hello","World"};
		}
	}
	
	
	@Test public void testSerializeIntArrayBean() {
		Object bean = new IntArrayBean();
		
		String ser = IdlConverter.idlserialize(bean);
		assertEquals("str ={intArray:[3, 45]}", ser);
	}
	
	public static class IntArrayBean {
		public int[] getIntArray() {
			return new int[]{3, 45};
		}
	}
	
	@Test public void testSerializeFloatArrayBean() {
		Object bean = new FloatArrayBean();
		
		String ser = IdlConverter.idlserialize(bean);
		assertEquals("str ={floatArray:[1.5, 1.2]}", ser);
	}
	
	public static class FloatArrayBean {
		public float[] getFloatArray() {
			return new float[]{1.5f,1.2f};
		}
	}
	
	@Test public void testSerializeDoubleArrayBean() {
		Object bean = new DoubleArrayBean();
		
		String ser = IdlConverter.idlserialize(bean);
		assertEquals("str ={doubleArray:[1.5, 1.2]}", ser);
	}
	
	public static class DoubleArrayBean {
		public double[] getDoubleArray() {
			return new double[]{1.5d,1.2d};
		}
	}
	
	@Test public void testSerializeObjectArrayBean() {
		Object bean = new ObjectArrayBean();
		
		String ser = IdlConverter.idlserialize(bean);
		assertEquals("str ={objectArray:[{null:PTR_NEW()}, {hello world,name:'hello world'}, {int:5}, {float:1.5}, {double:6.22}]}", ser);
	}
	
	public static class ObjectArrayBean {
		public Object[] getObjectArray() {
			return new Object[]{new NullBean(), new StringBean(), new IntBean(), new FloatBean(), new DoubleBean()};
		}
	}
	
	@Test public void testSerializeCollectionBean() {
		Object bean = new CollectionBean();
		
		String ser = IdlConverter.idlserialize(bean);
		assertEquals("str ={collection:[{null:PTR_NEW()}, {hello world,name:'hello world'}, {int:5}, {float:1.5}, {double:6.22}]}", ser);
	}
	
	public static class CollectionBean {
		public ArrayList<Object> getCollection() {
			ArrayList<Object> collection = new ArrayList<Object>();
			collection.add(new NullBean());
			collection.add(new StringBean());
			collection.add(new IntBean());
			collection.add(new FloatBean());
			collection.add(new DoubleBean());
			return collection;}
	}	
}
