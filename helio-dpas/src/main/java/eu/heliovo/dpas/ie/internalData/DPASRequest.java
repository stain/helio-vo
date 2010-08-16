package eu.heliovo.dpas.ie.internalData;


import java.util.Date;
import java.util.Map;

import org.w3c.dom.Document;

// TODO: Auto-generated Javadoc
/**
 * The Class DPASRequest.
 */
public class DPASRequest
{
	
	/** The requestor. */
	private	Identity					requestor			=	null;
	
	/** The request type. */
	private	String						requestType			=	null;
	
	/** The request argument. */
	private	DPASQueryArgument			requestArgument		=	null;
	
	/** The request start. */
	private	Date						requestStart		=	null;
	
	/** The request stop. */
	private	Date						requestStop			=	null;
	
	/** The request successful. */
	private	boolean						requestSuccessful	=	false;

	/** The request type. */
	private	String						failureReason		=	null;

	/** The request results (as an object) */
	/*
	 * TODO : Now it is a generic object to cope with the legacy results
	 */
	private	Object						requestResult		=	null;
	/** The request results (as xml) */
	/*
	 * TODO : Now it is a generic object to cope with the legacy results
	 */
	private	Document 					xmlOutput			=	null;
	/**
	 * Instantiates a new dPAS request.
	 * 
	 * @param requestor the requestor
	 * @param requestType the request type
	 */
	public DPASRequest(Identity requestor, String requestType, DPASQueryArgument argument)
	{
		super();
		this.requestor 			= 	requestor;
		this.requestType 		= 	requestType;
		this.requestStart		=	new Date();
		this.requestArgument	=	argument;
	}
	/**
	 * Fail request.
	 * 
	 * @param failureReason the failure reason
	 */
	public	void	failRequest(String failureReason)
	{
		this.requestSuccessful	=	false;
		this.requestStop		=	new Date();
		this.failureReason		=	failureReason;
	}
	/*
	 * Getters and setters
	 */	
	
	
	/**
	 * Gets the requestor.
	 * 
	 * @return the requestor
	 */
	public Identity getRequestor()
	{
		return requestor;
	}


	public Document getXmlOutput() {
		return xmlOutput;
	}
	public void setXmlOutput(Document xmlOutput) {
		this.xmlOutput = xmlOutput;
	}
	/**
	 * Sets the requestor.
	 * 
	 * @param requestor the new requestor
	 */
	public void setRequestor(Identity requestor)
	{
		this.requestor = requestor;
	}


	/**
	 * Gets the request type.
	 * 
	 * @return the request type
	 */
	public String getRequestType()
	{
		return requestType;
	}


	/**
	 * Sets the request type.
	 * 
	 * @param requestType the new request type
	 */
	public void setRequestType(String requestType)
	{
		this.requestType = requestType;
	}


	/**
	 * Gets the request start.
	 * 
	 * @return the request start
	 */
	public Date getRequestStart()
	{
		return requestStart;
	}


	/**
	 * Gets the request stop.
	 * 
	 * @return the request stop
	 */
	public Date getRequestStop()
	{
		return requestStop;
	}


	/**
	 * Checks if is request successful.
	 * 
	 * @return true, if is request successful
	 */
	public boolean isRequestSuccessful()
	{
		return requestSuccessful;
	}

	/**
	 * Gets the request argument.
	 * 
	 * @return the request argument
	 */
	public DPASQueryArgument getRequestArgument()
	{
		return requestArgument;
	}

	/**
	 * Sets the request argument.
	 * 
	 * @param requestArgument the new request argument
	 */
	public void setRequestArgument(DPASQueryArgument requestArgument)
	{
		this.requestArgument = requestArgument;
	}

	/**
	 * Gets the request result.
	 * 
	 * @return the request result
	 */
	public Object getRequestResult()
	{
		return requestResult;
	}

	/**
	 * Sets the request result.
	 * 
	 * @param requestResult the new request result
	 */
	public void setRequestResult(Map<String, DPASResultItem> requestResult)
	{
		this.requestResult = requestResult;
	}
}
