package eu.heliovo.idlclient.model;

import java.util.logging.LogRecord;

public class IdlLogRecord {
	
	private final LogRecord logRecord;
	
	public IdlLogRecord(LogRecord logRecord)
	{
		this.logRecord = logRecord;
	}
	
	public String getMessage()
	{
		return logRecord.getMessage();	
	}
	
	public long getMillis()
	{
		return logRecord.getMillis();
	}
	
	public long getSequenceNumber()
	{
		return logRecord.getSequenceNumber();
	}
	
	public int getThreadID()
	{
		return logRecord.getThreadID();
	}

}
