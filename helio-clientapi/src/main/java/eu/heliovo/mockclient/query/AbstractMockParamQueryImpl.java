package eu.heliovo.mockclient.query;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.LogRecord;

import eu.heliovo.clientapi.model.infrastructure.HelioService;
import eu.heliovo.clientapi.query.HelioParamQuery;
import eu.heliovo.clientapi.result.HelioQueryResult;
import eu.heliovo.clientapi.result.JobExecutionException;
import eu.heliovo.clientapi.result.ResultId;
import eu.heliovo.clientapi.result.ResultIdFactory;
/**
 * Abstract base class for param query mock ups. 
 * @author marco soldati at fhnw ch 
 */
abstract class AbstractMockParamQueryImpl implements HelioParamQuery, HelioService {
	
	/**
	 * Classpath resource path to the VoTable.
	 */
	private final String resourcePath;
	
	public AbstractMockParamQueryImpl(String resourcePath) {
		this.resourcePath = resourcePath;		
	}	

	@Override
	public HelioQueryResult query(Map<String, ? extends Object> params) {
		if (params == null) {
			throw new IllegalArgumentException("Argument 'params' must not be null. Please specify a map.");
		}

		final long delay = getAsLong(params, "delay");
		final float exceptionProbability = getAsFloat(params, "exception-probability");
		
		URL votable = getClass().getResource(resourcePath);
		if (votable == null) {
			throw new RuntimeException("Unable to find resource '" + resourcePath + "'");
		}
		HelioQueryResultImpl queryResultImpl;
		try {
			queryResultImpl = new HelioQueryResultImpl(
				ResultIdFactory.createResultId("marco", ResultIdFactory.newTransactionId()), 
				votable.toURI(), 
				delay,
				exceptionProbability);
		} catch (URISyntaxException e) {
			throw new RuntimeException("Cannot convert URL to URI: " + e.getMessage(), e);
		}
		return queryResultImpl;
	}

	private float getAsFloat(Map<String, ? extends Object> params, String string) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Get object from map and convert to long if possible.
	 * @param params the map to check
	 * @param key the key to search for
	 * @return the value as long or null if not applicable.
	 */
	protected Long getAsLong(Map<String, ? extends Object> params, String key) {
		Long num;
		final Object numObject = params.get(key);
		if (numObject == null) {
			num = Long.valueOf(0);
		} else if (numObject instanceof Long) {
			num = (Long) numObject;
		} else 	if (numObject instanceof Number) {
			num = ((Number)numObject).longValue();
		} else if (numObject instanceof String){
			try {
				num = Long.decode((String)numObject);
			} catch (NumberFormatException e) {
				// silently ignore wrong parameters
				num = Long.valueOf(0);
			}
		} else {
			try {
			num = Long.decode(numObject.toString());
			} catch (NumberFormatException e) {
				// silently ignore wrong parameters
				num = Long.valueOf(0);
			}
		}
		return num;
	}

	/**
	 * Find a value in the map and convert it to a string.
	 * @param params the map to search
	 * @param paramService the name of the value to lookup. The value will be converted to a String.
	 * @return the value a string or null if not applicable.
	 */
	protected String getAsString(Map<String, ? extends Object> params,
			String key) {
		Object value = params.get(key);
		if (value == null) {
			return null;
		} if (value instanceof String) {
			return (String)value;
		} else {
			return value.toString();
		}
	}

	/**
	 * Mock implementation of a Helio Query Result. 
	 * @author marco soldati at fhnw ch
	 *
	 */
	 static class HelioQueryResultImpl implements HelioQueryResult {
		/**
		 * The id of the result
		 */
		private final ResultId id;
		
		/**
		 * URI pointing to the VOTable
		 */
		private final URI voTable;
		
		/**
		 * Default timeout is 10 minutes
		 */
		private final static long DEFAULT_TIMEOUT = 10 * 60000;
		
		/**
		 * Single thread executor for this result instance. 
		 */
		private final ExecutorService executor = Executors.newSingleThreadExecutor();

		/**
		 * sleep factor for long running task.
		 */
		private final long sleep;
		
		/**
		 * Probability that the current thread throws an exception.
		 */
		private final float exceptionProbability;
		
		/**
		 * the current execution phase.
		 */
		private Phase phase = Phase.PENDING;

		/**
		 * Create a Helio query result
		 * @param id the assigned id
		 * @param voTable the assigned vo table.
		 * @param sleep time in milliseconds to delay the thread. Ignored if 0.
		 * @param exceptionProbability probability that an exception is thrown: &lt;0.0 for never, &gt;1.0 for always
		 */
		HelioQueryResultImpl(ResultId id, URI voTable, long sleep, float exceptionProbability) {
			this.id = id;
			this.voTable = voTable;
			this.sleep = sleep;
			this.exceptionProbability = exceptionProbability;
		}

		@Override
		public Object asObjectModel() {
			throw new UnsupportedOperationException("Currently not supported.");
		}

		@Override
		public Object asObjectModel(long timeout, TimeUnit unit)
				throws CancellationException, InterruptedException,
				ExecutionException, TimeoutException {
			throw new UnsupportedOperationException("Currently not supported.");
		}

		@Override
		public InputStream asVOTable() throws JobExecutionException {
			return asVOTable(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
		}

		@Override
		public InputStream asVOTable(long timeout, TimeUnit unit)
				throws JobExecutionException {
			this.phase = Phase.QUEUED;
			Future<InputStream> future = executor.submit(new Callable<InputStream>() {
				@Override
				public InputStream call() throws Exception {					
					// wait a bit
					int repeat = (int)sleep / 100;
					boolean interrupted = false;
					for (int i = 0; i < repeat && !(interrupted = Thread.interrupted()); i++) {
						Thread.sleep(100);
					}
					if (interrupted) {
						throw new InterruptedException("Thread has been interrupted.");
					}
					if (Math.random() < exceptionProbability) {
						throw new RuntimeException("Random exeption occurred. This is used for testing purposes.");
					}
					
					return voTable.toURL().openStream();
				}
			});
			
			try {
				InputStream result = future.get(timeout, unit);
				phase = Phase.COMPLETED;
				return result;
			} catch (InterruptedException e) {
				phase = Phase.ERROR;
				throw new JobExecutionException(e);
			} catch (ExecutionException e) {
				phase = Phase.ERROR;
				throw new JobExecutionException(e);
			} catch (TimeoutException e) {
				phase = Phase.ERROR;
				throw new JobExecutionException(e);
			}
		}

		@Override
		public ResultId getResultId() {
			return id;
		}

		@Override
		public LogRecord[] getUserLogs() {
			throw new UnsupportedOperationException("Currently not supported.");
		}

		@Override
		public int getExecutionDuration() {
			return 0;
		}
		
		@Override
		public Date getDestructionTime() {
			return null;
		}
		
		@Override
		public Phase getPhase() {
			return phase;
		}
	}
}
