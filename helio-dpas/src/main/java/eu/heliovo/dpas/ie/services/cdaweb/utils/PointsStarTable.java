package eu.heliovo.dpas.ie.services.cdaweb.utils;

import java.text.SimpleDateFormat;
import eu.heliovo.dpas.ie.services.cdaweb.service.org.ws.cdaw.FileDescription;
import eu.heliovo.dpas.ie.services.common.utils.ConstantKeywords;
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
        new ColumnInfo( "instrument_name", String.class, "Instrument Name" ),
        new ColumnInfo( "provider_instrument", String.class, "Provider Instrument name" ),
        new ColumnInfo( "url", String.class, "URL for the file" ),
        new ColumnInfo( "provider", String.class, "Provider Name" ),
        colStartDate,
        colEndDate,
    };

    // Member variables are arrays holding the actual data.
    FileDescription[]	resp_;
    int nRow_;
    String inst;
    String provider_ins;
    String[] dataSetIdArray;
    String detective_feild;
    SimpleDateFormat formatter = new SimpleDateFormat(ConstantKeywords.ORGINALDATEFORMAT.getDateFormat());
    /**
     * 
     * @param resp
     * @param url
     * @param provider
     * @param status
     */
    public PointsStarTable( FileDescription[] resp ,String helio_instrument,String instrument,String[] dataSetIdArray,String detective) {
    	this.resp_=resp;
    	this.nRow_=(int) resp.length;
    	this.inst=helio_instrument;
    	this.provider_ins=instrument;
    	this.dataSetIdArray=dataSetIdArray;
    	//
    	detective_feild=detective;
    	//Start date 
    	colStartDate.setAuxDatum( new DescribedValue( VOStarTable.XTYPE_INFO,"iso8601"));
    	//End date
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
        if(resp_!=null && resp_[irow]!=null){
        	//System.out.println(" ++++++++++++++++ Dective Filed ++++++++++++++++"+detective_feild);
	        switch ( icol ) {
	        	case 0: return inst;
	        	case 1:return dataSetIdArray[irow];
	            case 2: return resp_[irow].getName();
	            case 3: return "CDAWEB";
	            case 4: return CdaWebUtils.convertCalendarToString(resp_[irow].getStartTime());
	            case 5: return CdaWebUtils.convertCalendarToString(resp_[irow].getEndTime());
	            default: throw new IllegalArgumentException();
	        }
       }else{
        	return null;
       }
    }
}

