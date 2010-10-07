package eu.heliovo.dpas.ie.services.directory.archives;

import java.util.Date;
import eu.heliovo.dpas.ie.services.directory.dao.exception.NewPathFragmentException;

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