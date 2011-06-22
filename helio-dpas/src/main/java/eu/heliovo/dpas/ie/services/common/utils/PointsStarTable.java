package eu.heliovo.dpas.ie.services.common.utils;

import eu.heliovo.dpas.ie.services.cdaweb.service.org.ws.cdaw.FileDescription;
import eu.heliovo.dpas.ie.services.common.transfer.ResultTO;
import uk.ac.starlink.table.ColumnInfo;
import uk.ac.starlink.table.RandomStarTable;

public class PointsStarTable  extends RandomStarTable {
	//Start Date
	ColumnInfo colStartDate=new ColumnInfo( "time_start", String.class, "Start date" );
	//End date
	ColumnInfo colEndDate=new ColumnInfo( "time_end", String.class, "End date" );
    // Define the metadata object for each of the columns.
    ColumnInfo[] colInfos_ = new ColumnInfo[] {
        new ColumnInfo( "HELIO_OBS_INST", String.class, "Helio instrument Name" ),
        new ColumnInfo( "PVDR_NAME", String.class, "Provider name" ),
        new ColumnInfo( "PVDR_RANKING", String.class, "Provider ranking" ),
    };

    // Member variables are arrays holding the actual data.
    FileDescription[]	resp_;
    int nRow_;
    String inst;
    ResultTO[] res=null;
    /**
     * 
     * @param resp
     * @param url
     * @param provider
     * @param status
     */
    public PointsStarTable(ResultTO[] resTO) {
    	String pat_count=InstanceHolders.getInstance().getProperty("PATCOUNT");
    	if(pat_count!=null && !pat_count.trim().equals(""))
    		this.nRow_=Integer.parseInt(pat_count);
    	res=resTO;
    }

    public int getColumnCount() {
        return 3;
    }
      
    public long getRowCount() {
        return nRow_;
    }

    public ColumnInfo getColumnInfo( int icol ) {
        return colInfos_[ icol ];
    }
    
  
    public Object getCell( long lrow, int icol ) {
        int irow = checkedLongToInt( lrow );
        if(res!=null && res[irow]!=null){
        	//System.out.println(" ++++++++++++++++ Dective Filed ++++++++++++++++"+detective_feild);
	        switch ( icol ) {
	        	case 0: return res[irow].getHelioInst();
	        	case 1:return res[irow].getProviderName();
	            case 2: return res[irow].getPvdrRanking();
	            default: throw new IllegalArgumentException();
	        }
       }else{
        	return null;
       }
    }
}

