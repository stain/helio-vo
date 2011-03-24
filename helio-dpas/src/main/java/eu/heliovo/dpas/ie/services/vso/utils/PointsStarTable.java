package eu.heliovo.dpas.ie.services.vso.utils;

import eu.heliovo.dpas.ie.services.vso.provider.VSOProvider;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.VSO.VSOi.ProviderQueryResponse;
import uk.ac.starlink.table.ColumnInfo;
import uk.ac.starlink.table.DescribedValue;
import uk.ac.starlink.table.RandomStarTable;
import uk.ac.starlink.votable.VOStarTable;

public class PointsStarTable  extends RandomStarTable {
	//Start Date
	ColumnInfo colStartDate=new ColumnInfo( "time_start", String.class, "Start date" );
	//End date
	ColumnInfo colEndDate=new ColumnInfo( "time_end", String.class, "End date" );
    // Define the metadata object for each of the columns.
    ColumnInfo[] colInfos_ = new ColumnInfo[] {
        new ColumnInfo( "instrument_name", String.class, "Instrument name" ),
        new ColumnInfo( "provider_instrument", String.class, "Provider Instrument name" ),
        new ColumnInfo( "url", String.class, "URL for the file" ),
        new ColumnInfo( "provider", String.class, "Provider Name" ),
        colStartDate,colEndDate,
    };

    // Member variables are arrays holding the actual data.
    String url_;
    ProviderQueryResponse	resp_;
    int nRow_;
    String provider_;
    String status_;
    String[] urlList;
    String inst;
    public PointsStarTable( ProviderQueryResponse	resp,String url,String provider,String status,String helio_inst ) {
    	resp_=resp;
    	url_=url;
    	inst=helio_inst;
    	if(resp.getNo_of_records_returned()!=null)
    		nRow_=resp.getNo_of_records_returned();
    	if(provider!=null)
    		provider_=provider;
    	status_=status;
    	//VSO Provider
    	VSOProvider vsoPvr=new VSOProvider();
    	//Getting list of File Id
    	urlList=vsoPvr.getVsoURL(resp, provider);
    	//
    	colStartDate.setAuxDatum( new DescribedValue( VOStarTable.XTYPE_INFO,"iso8601"));
    	//
    	colEndDate.setAuxDatum( new DescribedValue( VOStarTable.XTYPE_INFO,"iso8601"));
    }

    public int getColumnCount() {
        return 6;
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
	        	case 0: return inst;
	        	case 1: return resp_.getRecord()[irow].getInstrument();
	            case 2: return urlList[irow];
	            case 3: return "VSO:"+resp_.getRecord()[irow].getProvider();
	            case 4: return VsoUtils.changeFormat(resp_.getRecord()[irow].getTime().getStart());
	            case 5: return VsoUtils.changeFormat(resp_.getRecord()[irow].getTime().getEnd());
	            default: throw new IllegalArgumentException();
	        }
       }else{
        	return null;
       }
    }
}

