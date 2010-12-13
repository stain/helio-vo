/* #ident	"%W%" */
package eu.heliovo.dpas.ie.services.common.utils;

import java.util.TimerTask;

public abstract class DeleteStatusTimer extends TimerTask {

	public DeleteStatusTimer(  ) {
		
	}

	public final void run() {
		execute();
	}

	protected abstract void execute();
}

