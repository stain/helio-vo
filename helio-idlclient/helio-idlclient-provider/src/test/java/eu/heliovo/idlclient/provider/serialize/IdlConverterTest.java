package eu.heliovo.idlclient.provider.serialize;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class IdlConverterTest {
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
		
}
