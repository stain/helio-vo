package eu.heliovo.dpas.ie.sensors.archives;

import java.util.Date;

public interface NewPathFragment
{
	public abstract Date fragmentToDate(String fragment)
			throws NewPathFragmentException;

	public abstract Date fragmentToDate(String fragment, Date d)
		throws NewPathFragmentException;

	public abstract String dateToFragment(Date d)
			throws NewPathFragmentException;

	public abstract boolean isFixed();

	public abstract int numberOfInternalSeparators(char separator);
	
	public abstract	String getExpression();
}