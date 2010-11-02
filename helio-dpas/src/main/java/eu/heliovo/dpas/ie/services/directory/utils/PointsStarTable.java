package eu.heliovo.dpas.ie.services.directory.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import eu.heliovo.dpas.ie.common.ConstantKeywords;
import uk.ac.starlink.table.ColumnInfo;
import uk.ac.starlink.table.RandomStarTable;

public class PointsStarTable  extends RandomStarTable {

    // Define the metadata object for each of the columns.
    ColumnInfo[] colInfos_ = new ColumnInfo[] {
    	new ColumnInfo( "Instrument Name", String.class, "Instrument Name" ),
    	new ColumnInfo( "URL", String.class, "Fits URL" ),
        new ColumnInfo( "Start Date", String.class, "Measurement Start" ),
        new ColumnInfo( "End Date", String.class, "Measurement End" ),
    };

    // Member variables are arrays holding the actual data.
    List<DPASResultItem> resp_;
    int nRow_;
    String inst;
    String end_date;
    SimpleDateFormat formatter = new SimpleDateFormat(ConstantKeywords.ORGINALDATEFORMAT.getDateFormat());
    DpasUtilities dpasUtils	=	new DpasUtilities();
    /**
     * 
     * @param resp
     * @param url
     * @param provider
     * @param status
     */
    public PointsStarTable( List<DPASResultItem> resp,String instrument,String endDate) {
    	resp_=resp;
     	nRow_=(int) resp.size();
     	inst=instrument;
     	end_date=endDate.replace("T", "");
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
        if(resp_!=null && resp_.get(irow)!=null){
	        switch ( icol ) {
	        	case 0:return inst;
	            case 1:return resp_.get(irow).urlFITS;
	            case 2:return dpasUtils.calendarToHELIOTime((Calendar)resp_.get(irow).measurementStart);
	            case 3:return end_date;
	            default: throw new IllegalArgumentException();
	        }
       }else{
        	return null;
       }
    }
}

