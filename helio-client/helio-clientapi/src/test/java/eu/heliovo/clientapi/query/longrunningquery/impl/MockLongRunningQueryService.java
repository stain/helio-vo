package eu.heliovo.clientapi.query.longrunningquery.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import eu.helio_vo.xml.longqueryservice.v0.LongHelioQueryService;
import eu.helio_vo.xml.longqueryservice.v0.ResultInfo;
import eu.helio_vo.xml.longqueryservice.v0.Status;
import eu.helio_vo.xml.longqueryservice.v0.StatusValue;
import eu.heliovo.clientapi.workerservice.JobExecutionException;

/**
 * A local mock implementation of a long running query service. For testing purposes only
 * @author marco soldati at fhnw ch
 *
 */
class MockLongRunningQueryService extends AbstractLongRunningQueryService {

	private static final URL wsdlLocation = asURL("http://localhost/test/LongRunningQuery.wsdl");
	private static final String name = "test_service";
	private static final String description = "a dummy test service";

	public MockLongRunningQueryService(MockPort port) {	
		super(port, wsdlLocation, name, description);
	}

	private static URL asURL(String url) {
		try {
			return new URL(url);
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException("Unable to parse URL: '" + url + "'. Cause: " + e.getMessage(), e);
		}
	}

	/**
	 * Mock implementation of a long helio query service. For testing purposes.
	 * @author marco soldati at fhnw ch
	 *
	 */
	public static class MockPort implements LongHelioQueryService {
		
		/**
		 * An constant id for testing
		 */
		private final String id;
		
		/**
		 * Count the returned status values
		 */
		private int statusCounter = 0;
		
		/**
		 * the sequence of status.
		 */
		private final StatusValue[] statusSequence;
		
		/**
		 * URL pointing to the result file.
		 */
		private final URL resultFile;

		/**
		 * Delay for the query methods before returning
		 */
		private final long queryDelay;

		/**
		 * Delay for the status method before returning
		 */
		private final long statusDelay;
		
		/**
		 * Delay for the result method beofre returning
		 */
		private final long resultDelay;
		
		public MockPort(String id, URL resultFile, StatusValue[] statusSequence, long queryDelay, long statusDelay, long resultDelay) {
			this.id = id;
			this.resultFile = resultFile;
			this.statusSequence = statusSequence;
			this.queryDelay = queryDelay;
			this.statusDelay = statusDelay;
			this.resultDelay = resultDelay;
		}
		
		@Override
		public String longQuery(List<String> starttime, List<String> endtime, List<String> from, String where,
				String instrument, Integer maxrecords, Integer startindex) {
			delay(queryDelay);
			return id;
		}
		

		@Override
		public String longTimeQuery(List<String> starttime, List<String> endtime, List<String> from,
				Integer maxrecords, Integer startindex) {
			delay(queryDelay);
			return id;
		}
		
		@Override
		public Status getStatus(String id) {
			if (!id.equals(this.id)) {
				throw new JobExecutionException("Unknown ID " + id);
			}
			Status status = new Status();
			status.setDescription("testing the long running query");
			status.setID(id);
			status.setStatus(statusSequence[statusCounter]);
			if (statusCounter < statusSequence.length-1) {
				statusCounter++;
			}
			delay(statusDelay);
			return status;
		}
		
		@Override
		public ResultInfo getResult(String id) {
			if (statusSequence[statusCounter] == StatusValue.COMPLETED) {
				ResultInfo resultInfo = new ResultInfo();
				resultInfo.setDescription("a test result has been generated");
				resultInfo.setFileInfo("test.xml");
				resultInfo.setResultURI(resultFile.toString());
				resultInfo.setID(id);
				resultInfo.setStatus(StatusValue.COMPLETED);
				delay(resultDelay);
				return resultInfo;
			}
			else {
				throw new JobExecutionException("Result not available at status: " + statusSequence[statusCounter]);
			}
		}

		/**
		 * Sleep for delay milliseconds
		 * @param delay the delay in ms. Will be ignored if &lt;=0.
		 */
		private void delay(long delay) {
			try {
				if (delay > 0)
					Thread.sleep(delay);
			} catch (InterruptedException e) {
				// ignore
			}
		}

		
	}
}
