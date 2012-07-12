package eu.heliovo.idlclient.provider.serialize;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import net.ivoa.xml.votable.v1.VOTABLE;

import org.junit.BeforeClass;
import org.junit.Test;

import eu.heliovo.clientapi.query.HelioQueryResult;
import eu.heliovo.clientapi.workerservice.JobExecutionException;
import eu.heliovo.idlclient.model.IdlHelioQueryResult;

public class IdlObjConverterTest {
	
	private final static IdlObjConverter idl = IdlObjConverter.getInstance();
	
	@BeforeClass public static void init() {
		idl.registerSerialisationHandler(HelioQueryResult.class, IdlHelioQueryResult.class);		
	}

	@Test public void testSerializeNull() {
		
		String ser = idl.idlSerialize(null);
		assertEquals("function HELIOIDLAPI\nreturn, PTR_NEW()\nend", ser);
	}
	
	@Test public void testSerializeString() {
		
		String ser = idl.idlSerialize("Hello World");
		assertEquals("function HELIOIDLAPI\nreturn, 'Hello World'\nend", ser);
	}
	
	@Test public void testSerializeInt() {
		
		String ser = idl.idlSerialize(5);
		assertEquals("function HELIOIDLAPI\nreturn, 5\nend", ser);
	}
	
	@Test public void testSerializeFloat() {
		
		String ser = idl.idlSerialize(4.22f);
		assertEquals("function HELIOIDLAPI\nreturn, 4.22\nend", ser);
	}
	
	@Test public void testSerializeDouble() {
		
		String ser = idl.idlSerialize(8.123);
		assertEquals("function HELIOIDLAPI\nreturn, 8.123\nend", ser);
	}
	
	@Test public void testSerializeStringArray() {
		
		String ser = idl.idlSerialize(new String[]{"Hello", "World"});
		assertEquals("function HELIOIDLAPI\nreturn, [ 'Hello', 'World' ]\nend", ser);
	}
	
	@Test public void testSerializeNullBean() {
		Object bean = new NullBean();
		
		String ser = idl.idlSerialize(bean);
		assertEquals("function NullBean::init, null\nreturn_value = self->heliovo_framework::INIT()\nself.null = null\nreturn, 1\nend\n\n" +
				"pro NullBean__define\nself = { NullBean, null : PTR_NEW(), inherits heliovo_framework }\nend\n\n" +
				"function HELIOIDLAPI\nreturn, obj_new('NullBean', PTR_NEW())\nend", ser);
	}
	
	public static class NullBean {
		public Object getNull() {
			return null;
		}
	}
	
	@Test public void testSerializeStringBean() {
		Object bean = new StringBean();
		
		String ser = idl.idlSerialize(bean);
		assertEquals("function StringBean::init, name\nreturn_value = self->heliovo_framework::INIT()\nself.name = name\nreturn, 1\nend\n\n" +
				"pro StringBean__define\nself = { StringBean, name : '', inherits heliovo_framework }\nend\n\n" +
				"function HELIOIDLAPI\nreturn, obj_new('StringBean', 'hello world')\nend", ser);
	}
	
	public static class StringBean {
		public String getName() {
			return "hello world";
		}
	}
	
	
	@Test public void testSerializeIntBean() {
		Object bean = new IntBean();
		
		String ser = idl.idlSerialize(bean);
		assertEquals("function IntBean::init, int\nreturn_value = self->heliovo_framework::INIT()\nself.int = int\nreturn, 1\nend\n\n" +
				"pro IntBean__define\nself = { IntBean, int : 0L, inherits heliovo_framework }\nend\n\n" +
				"function HELIOIDLAPI\nreturn, obj_new('IntBean', 5)\nend", ser);
	}
	
	public static class IntBean {
		public int getInt() {
			return 5;
		}
	}
	
	
	@Test public void testSerializeFloatBean() {
		Object bean = new FloatBean();
		
		String ser = idl.idlSerialize(bean);
		assertEquals("function FloatBean::init, float\nreturn_value = self->heliovo_framework::INIT()\nself.float = float\nreturn, 1\nend\n\n" +
				"pro FloatBean__define\nself = { FloatBean, float : 0., inherits heliovo_framework }\nend\n\n" +
				"function HELIOIDLAPI\nreturn, obj_new('FloatBean', 1.5)\nend", ser);
	}
	
	public static class FloatBean {
		public float getFloat() {
			return 1.5f;
		}
	}
	
	
	@Test public void testSerializeDoubleBean() {
		Object bean = new DoubleBean();
		
		String ser = idl.idlSerialize(bean);
		assertEquals("function DoubleBean::init, double\nreturn_value = self->heliovo_framework::INIT()\nself.double = double\nreturn, 1\nend\n\n" +
				"pro DoubleBean__define\nself = { DoubleBean, double : 0D, inherits heliovo_framework }\nend\n\n" +
				"function HELIOIDLAPI\nreturn, obj_new('DoubleBean', 6.22)\nend", ser);
	}
	
	public static class DoubleBean {
		public double getDouble() {
			return 6.22d;
		}
	}
	
	
	@Test public void testSerializeStringArrayBean() {
		Object bean = new StringArrayBean();
		
		String ser = idl.idlSerialize(bean);
		assertEquals("function StringArrayBean::init, stringArray\nreturn_value = self->heliovo_framework::INIT()\nself.stringArray = stringArray\nreturn, 1\nend\n\n" +
				"pro StringArrayBean__define\nself = { StringArrayBean, stringArray : ptr_new(), inherits heliovo_framework }\nend\n\n" +
				"function HELIOIDLAPI\nreturn, obj_new('StringArrayBean', ptr_new([ 'Hello', 'World' ]))\nend", ser);
	}
	
	public static class StringArrayBean {
		public String[] getStringArray() {
			return new String[]{"Hello","World"};
		}
	}
	
	
	@Test public void testSerializeIntArrayBean() {
		Object bean = new IntArrayBean();
		
		String ser = idl.idlSerialize(bean);
		assertEquals("function IntArrayBean::init, intArray\nreturn_value = self->heliovo_framework::INIT()\nself.intArray = intArray\nreturn, 1\nend\n\n" +
				"pro IntArrayBean__define\nself = { IntArrayBean, intArray : ptr_new(), inherits heliovo_framework }\nend\n\n" +
				"function HELIOIDLAPI\nreturn, obj_new('IntArrayBean', ptr_new([ 3, 45 ]))\nend", ser);
	}
	
	public static class IntArrayBean {
		public int[] getIntArray() {
			return new int[]{3, 45};
		}
	}
	
	@Test public void testSerializeFloatArrayBean() {
		Object bean = new FloatArrayBean();
		
		String ser = idl.idlSerialize(bean);
		assertEquals("function FloatArrayBean::init, floatArray\nreturn_value = self->heliovo_framework::INIT()\nself.floatArray = floatArray\nreturn, 1\nend\n\n" +
				"pro FloatArrayBean__define\nself = { FloatArrayBean, floatArray : ptr_new(), inherits heliovo_framework }\nend\n\n" +
				"function HELIOIDLAPI\nreturn, obj_new('FloatArrayBean', ptr_new([ 1.5, 1.2 ]))\nend", ser);
	}
	
	public static class FloatArrayBean {
		public float[] getFloatArray() {
			return new float[]{1.5f,1.2f};
		}
	}
	
	@Test public void testSerializeDoubleArrayBean() {
		Object bean = new DoubleArrayBean();
		
		String ser = idl.idlSerialize(bean);
		assertEquals("function DoubleArrayBean::init, doubleArray\nreturn_value = self->heliovo_framework::INIT()\nself.doubleArray = doubleArray\nreturn, 1\nend\n\n" +
				"pro DoubleArrayBean__define\nself = { DoubleArrayBean, doubleArray : ptr_new(), inherits heliovo_framework }\nend\n\n" +
				"function HELIOIDLAPI\nreturn, obj_new('DoubleArrayBean', ptr_new([ 1.5, 1.2 ]))\nend", ser);
	}
	
	public static class DoubleArrayBean {
		public double[] getDoubleArray() {
			return new double[]{1.5d,1.2d};
		}
	}
	
	@Test public void testSerializeObjectArrayBean() {
		Object bean = new ObjectArrayBean();
		
		String ser = idl.idlSerialize(bean);
		assertEquals("function NullBean::init, null\nreturn_value = self->heliovo_framework::INIT()\nself.null = null\nreturn, 1\nend\n\n" +
				"pro NullBean__define\nself = { NullBean, null : PTR_NEW(), inherits heliovo_framework }\nend\n\n" +
				"function StringBean::init, name\nreturn_value = self->heliovo_framework::INIT()\nself.name = name\nreturn, 1\nend\n\n" +
				"pro StringBean__define\nself = { StringBean, name : '', inherits heliovo_framework }\nend\n\n" +
				"function IntBean::init, int\nreturn_value = self->heliovo_framework::INIT()\nself.int = int\nreturn, 1\nend\n\n" +
				"pro IntBean__define\nself = { IntBean, int : 0L, inherits heliovo_framework }\nend\n\n" +
				"function FloatBean::init, float\nreturn_value = self->heliovo_framework::INIT()\nself.float = float\nreturn, 1\nend\n\n" +
				"pro FloatBean__define\nself = { FloatBean, float : 0., inherits heliovo_framework }\nend\n\n" +
				"function DoubleBean::init, double\nreturn_value = self->heliovo_framework::INIT()\nself.double = double\nreturn, 1\nend\n\n" +
				"pro DoubleBean__define\nself = { DoubleBean, double : 0D, inherits heliovo_framework }\nend\n\n" +
				"function ObjectArrayBean::init, objectArray\nreturn_value = self->heliovo_framework::INIT()\nself.objectArray = objectArray\nreturn, 1\nend\n\n" +
				"pro ObjectArrayBean__define\nself = { ObjectArrayBean, objectArray : ptr_new(), inherits heliovo_framework }\nend\n\n" +
				"function HELIOIDLAPI\nreturn, obj_new('ObjectArrayBean', ptr_new([ obj_new('NullBean', PTR_NEW()), obj_new('StringBean', 'hello world'), obj_new('IntBean', 5), obj_new('FloatBean', 1.5), obj_new('DoubleBean', 6.22) ]))\nend", ser);
	}
	
	public static class ObjectArrayBean {
		public Object[] getObjectArray() {
			return new Object[]{new NullBean(), new StringBean(), new IntBean(), new FloatBean(), new DoubleBean()};
		}
	}
	
	@Test public void testSerializeCollectionBean() {
		Object bean = new CollectionBean();
		
		String ser = idl.idlSerialize(bean);
		assertEquals("function NullBean::init, null\nreturn_value = self->heliovo_framework::INIT()\nself.null = null\nreturn, 1\nend\n\n" +
				"pro NullBean__define\nself = { NullBean, null : PTR_NEW(), inherits heliovo_framework }\nend\n\n" +
				"function StringBean::init, name\nreturn_value = self->heliovo_framework::INIT()\nself.name = name\nreturn, 1\nend\n\n" +
				"pro StringBean__define\nself = { StringBean, name : '', inherits heliovo_framework }\nend\n\n" +
				"function IntBean::init, int\nreturn_value = self->heliovo_framework::INIT()\nself.int = int\nreturn, 1\nend\n\n" +
				"pro IntBean__define\nself = { IntBean, int : 0L, inherits heliovo_framework }\nend\n\n" +
				"function FloatBean::init, float\nreturn_value = self->heliovo_framework::INIT()\nself.float = float\nreturn, 1\nend\n\n" +
				"pro FloatBean__define\nself = { FloatBean, float : 0., inherits heliovo_framework }\nend\n\n" +
				"function DoubleBean::init, double\nreturn_value = self->heliovo_framework::INIT()\nself.double = double\nreturn, 1\nend\n\n" +
				"pro DoubleBean__define\nself = { DoubleBean, double : 0D, inherits heliovo_framework }\nend\n\n" +
				"function CollectionBean::init, collection\nreturn_value = self->heliovo_framework::INIT()\nself.collection = collection\nreturn, 1\nend\n\n" +
				"pro CollectionBean__define\nself = { CollectionBean, collection : ptr_new(), inherits heliovo_framework }\nend\n\n" +
				"function HELIOIDLAPI\nreturn, obj_new('CollectionBean', ptr_new([ obj_new('NullBean', PTR_NEW()), obj_new('StringBean', 'hello world'), obj_new('IntBean', 5), obj_new('FloatBean', 1.5), obj_new('DoubleBean', 6.22) ]))\nend", ser);
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
		String ser = idl.idlSerialize(bean);
	}
}
