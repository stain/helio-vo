package eu.heliovo.mockclient.query.test;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import eu.heliovo.clientapi.query.HelioParameter;
import eu.heliovo.mockclient.query.MockParamQueryImpl;


public class MockParamQueryImplTest {

	@Test
	public void testDefaultQuery() {
		MockParamQueryImpl mockParamQueryImpl = new MockParamQueryImpl();
		HelioParameter[] params = mockParamQueryImpl.getParameterDescription(null);
		System.out.println(Arrays.toString(params));
		Assert.assertEquals(1, params.length);
		
		HelioParameter param = params[0];
		Assert.assertEquals("service", param.getParamName());
		Assert.assertEquals("Available query parameter services.", param.getDescription());
		Assert.assertEquals("xsd:string", param.getXsdType());		
	}
	
	@Test
	public void testHecQuery() {
		MockParamQueryImpl mockParamQueryImpl = new MockParamQueryImpl();
		HelioParameter[] params = mockParamQueryImpl.getParameterDescription(Collections.singletonMap("service", "hec"));
		System.out.println(Arrays.toString(params));
		Assert.assertEquals(3, params.length);
		
		HelioParameter param = params[0];
		Assert.assertEquals("startdate", param.getParamName());
		Assert.assertEquals("the start date", param.getDescription());
		Assert.assertEquals("xsd:dateTime", param.getXsdType());		
		
	}
}
