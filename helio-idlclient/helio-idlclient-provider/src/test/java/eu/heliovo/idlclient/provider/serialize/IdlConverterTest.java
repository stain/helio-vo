package eu.heliovo.idlclient.provider.serialize;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import model.IdlHelioQueryResult;
import model.IdlName;
import net.ivoa.xml.votable.v1.VOTABLE;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import eu.heliovo.clientapi.query.HelioQueryResult;
import eu.heliovo.clientapi.workerservice.JobExecutionException;
import eu.heliovo.shared.util.FileUtil;

public class IdlConverterTest {
	
	private final static IdlConverter idl = IdlConverter.getInstance();
	
	@BeforeClass public static void init() {
		idl.registerSerialisationHandler(HelioQueryResult.class, IdlHelioQueryResult.class);		
	}
	
	@Test public void testSerializeNullBean() {
		Object bean = new NullBean();
		
		String ser = idl.idlserialize(bean);
		assertEquals("FUNCTION HELIOIDLAPI\nreturn, {null:PTR_NEW()}\nEND", ser);
	}
	
	public static class NullBean {
		public Object getNull() {
			return null;
		}
	}
	
	@Test public void testSerializeStringBean() {
		Object bean = new StringBean();
		
		String ser = idl.idlserialize(bean);
		assertEquals("FUNCTION HELIOIDLAPI\nreturn, {name:'hello world'}\nEND", ser);
	}
	
	public static class StringBean {
		public String getName() {
			return "hello world";
		}
	}
	
	@Test public void testSerializeNamedStringBean() {
		Object bean = new NamedStringBean();
		
		String ser = idl.idlserialize(bean);
		assertEquals("FUNCTION HELIOIDLAPI\nreturn, {hello_world, name:'hello_world'}\nEND", ser);
	}
	
	public static class NamedStringBean {
		@IdlName
		public String getName() {
			return "hello_world";
		}
	}
	
	
	@Test public void testSerializeIntBean() {
		Object bean = new IntBean();
		
		String ser = idl.idlserialize(bean);
		assertEquals("FUNCTION HELIOIDLAPI\nreturn, {int:5}\nEND", ser);
	}
	
	public static class IntBean {
		public int getInt() {
			return 5;
		}
	}
	
	
	@Test public void testSerializeFloatBean() {
		Object bean = new FloatBean();
		
		String ser = idl.idlserialize(bean);
		assertEquals("FUNCTION HELIOIDLAPI\nreturn, {float:1.5}\nEND", ser);
	}
	
	public static class FloatBean {
		public float getFloat() {
			return 1.5f;
		}
	}
	
	
	@Test public void testSerializeDoubleBean() {
		Object bean = new DoubleBean();
		
		String ser = idl.idlserialize(bean);
		assertEquals("FUNCTION HELIOIDLAPI\nreturn, {double:6.22}\nEND", ser);
	}
	
	public static class DoubleBean {
		public double getDouble() {
			return 6.22d;
		}
	}
	
	
	@Test public void testSerializeStringArrayBean() {
		Object bean = new StringArrayBean();
		
		String ser = idl.idlserialize(bean);
		assertEquals("FUNCTION HELIOIDLAPI\nreturn, {stringArray:ptr_new(['Hello', 'World'])}\nEND", ser);
	}
	
	public static class StringArrayBean {
		public String[] getStringArray() {
			return new String[]{"Hello","World"};
		}
	}
	
	
	@Test public void testSerializeIntArrayBean() {
		Object bean = new IntArrayBean();
		
		String ser = idl.idlserialize(bean);
		assertEquals("FUNCTION HELIOIDLAPI\nreturn, {intArray:ptr_new([3, 45])}\nEND", ser);
	}
	
	public static class IntArrayBean {
		public int[] getIntArray() {
			return new int[]{3, 45};
		}
	}
	
	@Test public void testSerializeFloatArrayBean() {
		Object bean = new FloatArrayBean();
		
		String ser = idl.idlserialize(bean);
		assertEquals("FUNCTION HELIOIDLAPI\nreturn, {floatArray:ptr_new([1.5, 1.2])}\nEND", ser);
	}
	
	public static class FloatArrayBean {
		public float[] getFloatArray() {
			return new float[]{1.5f,1.2f};
		}
	}
	
	@Test public void testSerializeDoubleArrayBean() {
		Object bean = new DoubleArrayBean();
		
		String ser = idl.idlserialize(bean);
		assertEquals("FUNCTION HELIOIDLAPI\nreturn, {doubleArray:ptr_new([1.5, 1.2])}\nEND", ser);
	}
	
	public static class DoubleArrayBean {
		public double[] getDoubleArray() {
			return new double[]{1.5d,1.2d};
		}
	}
	
	@Test public void testSerializeObjectArrayBean() {
		Object bean = new ObjectArrayBean();
		
		String ser = idl.idlserialize(bean);
		assertEquals("FUNCTION HELIOIDLAPI\nreturn, {objectArray:ptr_new([{null:PTR_NEW()}, {name:'hello world'}, {int:5}, {float:1.5}, {double:6.22}])}\nEND", ser);
	}
	
	public static class ObjectArrayBean {
		public Object[] getObjectArray() {
			return new Object[]{new NullBean(), new StringBean(), new IntBean(), new FloatBean(), new DoubleBean()};
		}
	}
	
	@Test public void testSerializeCollectionBean() {
		Object bean = new CollectionBean();
		
		String ser = idl.idlserialize(bean);
		assertEquals("FUNCTION HELIOIDLAPI\nreturn, {collection:ptr_new([{null:PTR_NEW()}, {name:'hello world'}, {int:5}, {float:1.5}, {double:6.22}])}\nEND", ser);
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
	
	
	@Test public void testHelioQueryResult() {
		HelioQueryResult bean = new HelioQueryResult() {
			
			@Override
			public Phase getPhase() {
				return Phase.COMPLETED;
			}
			
			@Override
			public int getExecutionDuration() {
				return 0;
			}
			
			@Override
			public Date getDestructionTime() {
				return new Date();
			}
			
			@Override
			public LogRecord[] getUserLogs() {
				return new LogRecord[] {new LogRecord(Level.INFO, "test")};
			}
			
			@Override
			public VOTABLE asVOTable(long timeout, TimeUnit unit)
					throws JobExecutionException {
				return null;
			}
			
			@Override
			public VOTABLE asVOTable() throws JobExecutionException {
				return null;
			}
			
			@Override
			public URL asURL(long timeout, TimeUnit unit) throws JobExecutionException {
				try {
					return new URL("http://www.example.com");
				} catch (MalformedURLException e) {
					throw new RuntimeException(e);
				}
			}
			
			@Override
			public URL asURL() throws JobExecutionException {
				return asURL(1000, TimeUnit.MILLISECONDS);
			}
			
			@Override
			public String asString(long timeout, TimeUnit unit)
					throws JobExecutionException {
				return null;
			}
			
			@Override
			public String asString() throws JobExecutionException {
				return null;
			}
		};
		String ser = idl.idlserialize(bean);
	}
}
