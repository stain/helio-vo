package org.egso.provider.query;

import java.util.Vector;

public class WebServiceQuery extends ArchiveQuery {
	
    //***********************
    // ATTRIBUTES
    //***********************
	private Vector<String[]> calls = null;
  private Vector<String> selectedFields = null;
	
    //***********************
    // CONSTRUCTORS
    //***********************
    public WebServiceQuery() {
    	super(ArchiveQuery.WEB_SERVICE_ARCHIVE);
		calls = new Vector<String[]>();
		selectedFields = new Vector<String>();
    }


	public void setAllCalls(Vector<String[]> v) {
		calls = v;
	}
	
	public Vector<String[]> getAllCalls() {
		return calls;
	}
	
	
    public void setSelectedFields(Vector<String> fields){
        selectedFields=fields;
    }
	
	public void addField(String field) {
		selectedFields.add(field);
	}
    
	public void setFields(Vector<String> v) {
		selectedFields = v;
	}
	
    public Vector<String> getSelectedFields(){
        return selectedFields;
    }
    
    public String getSelectedFieldsAsString(){
        String output="";
        for(int i=0;i<selectedFields.size();i++)
        {
            if(i==0) output=(String)selectedFields.get(i);
            else     output=output+" | "+selectedFields.get(i);
        }
        return (output);
    }

    public String toString() {
		StringBuffer sb = new StringBuffer();
		for (String[] s:calls)
		  for(String x:s)
		    sb.append(x);

		return (sb.toString());
    }
}
