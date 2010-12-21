package eu.heliovo.dpas.ie.services.cdaweb.utils;

import java.text.SimpleDateFormat;

import eu.heliovo.dpas.ie.common.ConstantKeywords;
import eu.heliovo.dpas.ie.services.cdaweb.service.org.ws.cdaw.FileDescription;
import uk.ac.starlink.table.ColumnInfo;
import uk.ac.starlink.table.DescribedValue;
import uk.ac.starlink.table.RandomStarTable;
import uk.ac.starlink.votable.VOStarTable;

public class PointsStarTable  extends RandomStarTable {
	//Start Date
	ColumnInfo colStartDate=new ColumnInfo( "Start Date", String.class, "Start Date" );
	//End date
	ColumnInfo colEndDate=new ColumnInfo( "End Date", String.class, "End Date" );
    // Define the metadata object for each of the columns.
    ColumnInfo[] colInfos_ = new ColumnInfo[] {
        new ColumnInfo( "Instrument Name", String.class, "Instrument Name" ),
        new ColumnInfo( "URL", String.class, "URL for the file" ),
        colStartDate,
        colEndDate,
    };

    // Member variables are arrays holding the actual data.
    FileDescription[]	resp_;
    int nRow_;
    String inst;
    SimpleDateFormat formatter = new SimpleDateFormat(ConstantKeywords.ORGINALDATEFORMAT.getDateFormat());
    /**
     * 
     * @param resp
     * @param url
     * @param provider
     * @param status
     */
    public PointsStarTable( FileDescription[]	resp ,String instruments) {
    	resp_=resp;
    	nRow_=(int) resp.length;
    	inst=instruments;
    	//Start date 
    	colStartDate.setAuxDatum( new DescribedValue( VOStarTable.XTYPE_INFO,"iso8601"));
    	//End date
    	colEndDate.setAuxDatum( new DescribedValue( VOStarTable.XTYPE_INFO,"iso8601"));
    }

    public int getColumnCount() {
        return 4;
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
	        switch ( icol ) {
	        	case 0: return inst;
	            case 1: return resp_[irow].getName();
	            case 2: return CdaWebUtils.convertCalendarToString(resp_[irow].getStartTime());
	            case 3: return CdaWebUtils.convertCalendarToString(resp_[irow].getEndTime());
	            default: throw new IllegalArgumentException();
	        }
       }else{
        	return null;
       }
    }
}

