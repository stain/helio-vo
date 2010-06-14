package eu.heliovo.mockclient.query.test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import eu.heliovo.clientapi.query.HelioParameter;
import eu.heliovo.clientapi.result.HelioQueryResult;
import eu.heliovo.mockclient.query.IlsMockParamQueryImpl;

/**
 * Test the {@link IlsMockParamQueryImpl}.
 * @author marco soldati at fhnw ch
 *
 */
public class IlsMockParamQueryImplTest {

	
	@Test
	public void testIlsQuery() {
		IlsMockParamQueryImpl mockParamQueryImpl = new IlsMockParamQueryImpl();
		
		HelioParameter<?>[] params = mockParamQueryImpl.getParameterDescription(new HashMap<String, Object>());
		//System.out.println(Arrays.toString(params));
		Assert.assertEquals(4, params.length);
		
		HelioParameter<?> param = params[0];
		Assert.assertEquals("startdate", param.getParamName());
		Assert.assertEquals("the start date", param.getDescription());
		Assert.assertEquals("xsd:dateTime", param.getXsdType());
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("service", "ils");
		map.put("startdate", new Date()); // will be ignored by mock
		map.put("enddate", new Date());	// will be ignored by mock.			
		HelioQueryResult result = mockParamQueryImpl.query(map);
				
		System.out.println(result.getResultId());
		System.out.println(result.asVOTable());
	}
}
