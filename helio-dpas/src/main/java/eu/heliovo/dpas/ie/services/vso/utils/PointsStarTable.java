package eu.heliovo.dpas.ie.services.vso.utils;

import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.VSO.VSOi.ProviderQueryResponse;
import uk.ac.starlink.table.ColumnInfo;
import uk.ac.starlink.table.RandomStarTable;

public class PointsStarTable  extends RandomStarTable {

    // Define the metadata object for each of the columns.
    ColumnInfo[] colInfos_ = new ColumnInfo[] {
        new ColumnInfo( "Instrument", String.class, "Instrument name" ),
        new ColumnInfo( "URL", String.class, "Dummy URL to fits file" ),
        new ColumnInfo( "Provider", String.class, "Provider Name" ),
        new ColumnInfo( "Start Date", String.class, "Measurement Start Date" ),
        new ColumnInfo( "End Date", String.class, "Measurement End Date" ),
    };

    // Member variables are arrays holding the actual data.
    String url_;
    ProviderQueryResponse	resp_;
    int nRow_;
    public PointsStarTable( ProviderQueryResponse	resp,String url ) {
    	resp_=resp;
    	url_=url;
    	nRow_=resp.getNo_of_records_returned();
    }

    public int getColumnCount() {
        return 5;
    }
      
    public long getRowCount() {
        return nRow_;
    }

    public ColumnInfo getColumnInfo( int icol ) {
        return colInfos_[ icol ];
    }
    
  
    public Object getCell( long lrow, int icol ) {
        int irow = checkedLongToInt( lrow );
        if(resp_!=null && resp_.getRecord()!=null){
	        switch ( icol ) {
	        	case 0: return resp_.getRecord()[irow].getInstrument().toString();
	            case 1: return url_+resp_.getRecord()[irow].getFileid();
	            case 2: return resp_.getRecord()[irow].getProvider();
	            case 3: return resp_.getRecord()[irow].getTime().getStart();
	            case 4: return resp_.getRecord()[irow].getTime().getEnd();
	           
	            default: throw new IllegalArgumentException();
	        }
	       
        }else{
        	
        	throw new IllegalArgumentException("");
        	
        	
        }
    }
}

