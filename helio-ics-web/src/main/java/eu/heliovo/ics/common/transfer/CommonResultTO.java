/* #ident	"%W%" */
package eu.heliovo.ics.common.transfer;

import java.io.Serializable;

public class CommonResultTO implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	private Object[] result;
	private int count;
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Object[] getResult() {
		return result;
	}
	public void setResult(Object[] result) {
		this.result = result;
	}
	

}
