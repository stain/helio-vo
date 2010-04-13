package org.egso.provider.query;

import java.util.Vector;

import org.egso.provider.datamanagement.archives.Field;
import org.egso.provider.datamanagement.archives.Table;


public class SQLQuery extends ArchiveQuery {
	
	private Vector<Object> select = null;
	private Vector<Table> from = null;
	private String where = null;
	
	
	public SQLQuery() {
		super(ArchiveQuery.SQL_ARCHIVE);
	}
	
	public void setSelect(Vector<Object> selectFields) {
		select = selectFields;
	}
	
	public void setFrom(Vector<Table> tables) {
		from = tables;
	}
	
	public void setWhere(String wher) {
		where = wher;
	}
	
	public void addWhere(String w) {
		where += w;
	}
	
	public Vector<Object> getSelect() {
		return (select);
	}
	
	public String getSelectAsString() {
		if (select == null) {
			return (null);
		}
		StringBuffer sb = new StringBuffer();
		Object obj = null;
		for (int i = 0 ; i < (select.size() - 1) ; i++) {
			obj = select.get(i);
			if (obj instanceof String) {
				sb.append((String) obj + ", ");
			} else {
				sb.append(((Field) obj).getCompleteName() + ", ");
			}
		}
		obj = select.lastElement();
		if (obj instanceof String) {
			sb.append((String) obj);
		} else {
			sb.append(((Field) select.lastElement()).getCompleteName());
		}
		return(sb.toString());
	}
	
	public Vector<Table> getFrom() {
		return(from);
	}
	
	public String getFromAsString() {
		if (from == null) {
			return (null);
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0 ; i < (from.size() - 1) ; i++) {
			sb.append(((Table) from.get(i)).getName() + ", ");
		}
		sb.append(((Table) from.lastElement()).getName());
		return(sb.toString());
	}
	
	public String getWhere() {
		return (where);
	}
	
	
	public String toString() {
		return("SELECT DISTINCT " + getSelectAsString() + " FROM " + getFromAsString() + " WHERE " + where);
	}
	
}

