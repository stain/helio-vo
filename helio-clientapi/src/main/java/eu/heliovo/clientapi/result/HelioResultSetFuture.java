package eu.heliovo.clientapi.result;

import java.io.InputStream;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.LogRecord;

/**
 * Future that provides control over asynchronous calls to Helio. 
 * @author marco soldati at fhnw ch
 */
public interface HelioResultSetFuture extends Future<HelioResultSet>, HelioResultSet {
	
	/**
	 * Download the data as VOTable and return it as InputStream.
	 * Implementations may use lazy loading for the stream. 
	 * @param timeout the maximum time to wait
     * @param unit the time unit of the timeout argument
     * @return the data as XML VOTable stream.
     * @throws CancellationException if the computation was canceled
     * @throws ExecutionException if the computation threw an exception
     * @throws InterruptedException if the current thread was interrupted while waiting
     * @throws TimeoutException if the wait timed out 
	 */
	public InputStream asVOTable(long timeout, TimeUnit unit)    
    	throws CancellationException, InterruptedException, ExecutionException, TimeoutException;
	
	/**
	 * Download the data as VOTable and convert it to the Helio object model.
	 * @param timeout the maximum time to wait
     * @param unit the time unit of the timeout argument
	 * @return the data as object model. TODO: change return type to object model root. 
     * @throws CancellationException if the computation was canceled
     * @throws ExecutionException if the computation threw an exception
     * @throws InterruptedException if the current thread was interrupted while waiting
     * @throws TimeoutException if the wait timed out 
	 */
	public Object asObjectModel(long timeout, TimeUnit unit)    
		throws CancellationException, InterruptedException, ExecutionException, TimeoutException;
	
	/**
	 * Get log messages that are of particular interest for the user.
	 * This information is not intended for tracing and debugging and should be kept small and clean. 
	 * @return the user log messages.
	 */
	public LogRecord[] getUserLogs();
}
