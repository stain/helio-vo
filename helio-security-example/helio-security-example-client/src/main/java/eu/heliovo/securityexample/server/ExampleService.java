package eu.heliovo.securityexample.server;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface ExampleService 
{
	/**
	 * Method A
	 *
	 * @param a string parameter
	 */
	@WebMethod
	public	String	method_a(String arg);
	/**
	 * Method B
	 *
	 * @param a string parameter
	 */
	@WebMethod
	public	String	method_b(String arg);
	/**
	 * Method C
	 *
	 * @param a string parameter
	 */
	@WebMethod
	public	String	method_c(String arg);
	/**
	 * Method D
	 *
	 * @param a string parameter
	 */
	@WebMethod
	public	String	method_d(String arg);
}
