package eu.heliovo.dpas.ie.services.vso.utils;

import java.util.List;

import eu.heliovo.dpas.ie.services.vso.provider.VSOProvider;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.vsoi.v0_6.DataItem;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.vsoi.v0_6.QueryResponseBlock;
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
    List<QueryResponseBlock>	resp_;
    int nRow_;
    String provider_;
    String status_;
    String inst;
    List<DataItem> urllist;
    public PointsStarTable( List<QueryResponseBlock> resp,String url,String provider,String status,String helio_inst ) {
    	resp_=resp;
    	url_=url;
    	inst=helio_inst;
    	provider_=provider;
    	
    	
    	if(provider!=null)
    		provider_=provider;
    	status_=status;
    	//VSO Provider
    	VSOProvider vsoPvr=new VSOProvider();
    	//Getting list of File Id
    	//urlList=vsoPvr.getVsoURL(resp, provider);
    	
    	urllist=VsoUtils.getVsoProviderUrl(resp,provider_);
    	if( resp_.size()>0) {
    		nRow_ = urllist.size();
    		//nRow_=resp_.size();
    	}
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
        if(resp_!=null && resp_.get(irow)!=null){
	        switch ( icol ) {
	        	case 0: return inst;
	        	case 1: return resp_.get(irow).getInstrument();
	            case 2: return urllist.get(irow).getUrl();
	            case 3: return "VSO:"+resp_.get(irow).getProvider();
	            case 4: return VsoUtils.changeFormat(resp_.get(irow).getTime().getStart());
	            case 5: return VsoUtils.changeFormat(resp_.get(irow).getTime().getEnd());
	            default: throw new IllegalArgumentException();
	        }
       }else{
        	return null;
       }
    }
}

