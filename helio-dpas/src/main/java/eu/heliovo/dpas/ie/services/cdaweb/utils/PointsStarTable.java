package eu.heliovo.dpas.ie.services.cdaweb.utils;

import java.text.SimpleDateFormat;

import eu.heliovo.dpas.ie.common.ConstantKeywords;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.VSO.VSOi.ProviderQueryResponse;
import uk.ac.starlink.table.ColumnInfo;
import uk.ac.starlink.table.RandomStarTable;

public class PointsStarTable  extends RandomStarTable {

    // Define the metadata object for each of the columns.
    ColumnInfo[] colInfos_ = new ColumnInfo[] {
        new ColumnInfo( "Data Id", String.class, "Data Id" ),
        new ColumnInfo( "Instrument Name", String.class, "Instrument Name" ),
        new ColumnInfo( "Start Time", String.class, "Start Time" ),
        new ColumnInfo( "End Time", String.class, "End Time" ),
        new ColumnInfo( "Description", String.class, "Description" ),
    };

    // Member variables are arrays holding the actual data.
    String url_;
    ProviderQueryResponse	resp_;
    int nRow_;
    String provider_;
    String status_;
    SimpleDateFormat formatter = new SimpleDateFormat(ConstantKeywords.ORGINALDATEFORMAT.getDateFormat());
    /**
     * 
     * @param resp
     * @param url
     * @param provider
     * @param status
     */
    public PointsStarTable( ProviderQueryResponse	resp,String url,String provider,String status ) {
    	resp_=resp;
    	url_=url;
    	nRow_=resp.getNo_of_records_returned();
    	provider_=provider;
    	status_=status;
    	
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
	        	case 0: return resp_.getRecord()[irow].getInstrument();
	            case 1: return null;
	            case 2: return resp_.getRecord()[irow].getProvider();
	            case 3: return resp_.getRecord()[irow].getTime().getStart();
	            case 4: return resp_.getRecord()[irow].getTime().getEnd();
	            default: throw new IllegalArgumentException();
	        }
       }else{
        	return null;
       }
    }
}

