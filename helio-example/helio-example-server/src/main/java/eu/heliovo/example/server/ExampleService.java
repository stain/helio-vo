package eu.heliovo.example.server; 

import java.util.Collection;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public interface ExampleService 
{
	/**
	 * Test
	 *
	 * @param a string parameter
	 */
	@WebMethod
	@WebResult(name="testResult")
	public	TestResult	testOK(@WebParam(name="testParameter") String testParameter);
	/**
	 * Test
	 *
	 * @param a string parameter
	 */
	@WebMethod
	@WebResult(name="testResult")
	public	TestResult	testKO(@WebParam(name="testParameter") String testParameter) throws TestException;
}
