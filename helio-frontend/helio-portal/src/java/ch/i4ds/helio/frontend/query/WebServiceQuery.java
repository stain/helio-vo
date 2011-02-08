/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.i4ds.helio.frontend.query;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author David
 *
 * TODO: not in use check for delete
 */
public class WebServiceQuery {

    private String minDate;
    private String maxDate;
    private String instrument;
    private String sqlQuery;
    private String catalogue;
    private String extra;
    private String serviceName;
 private String data;

    public String getData(){
        return this.data;
    }
    public void setData(String data){
        this.data=data;
    }
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

     public String getCatalogue(){
        return catalogue;
    }
    public void setCatalogue(String catalogue){
        this.catalogue=catalogue;
    }
    public String getSqlQuery(){
        return sqlQuery;
    }
    public void setSqlQuery(String sqlQuery){
        this.sqlQuery=sqlQuery;
    }

   
    public String getMinDate() {

        return minDate;
    }
    public String getMaxDate() {
        return maxDate;
    }

    public void setMinDate(String minDate) {
        //04/11/03
       /*
        String temp = minDate.substring(6,minDate.length());
        String result = minDate.substring(0,6);
        if(temp.length()>2){
            System.out.println("es mayor a 2 "+ temp);
        this.minDate = minDate;

        }else{
            System.out.println("no es mayor a 2 "+temp);
             int tempnum = Integer.parseInt(temp);
            if(tempnum>50)result+="19"+temp;
            else result+="20"+temp;
             this.minDate = result;
        }
**/
        this.minDate = minDate;
        
    }
    public void setMaxDate(String maxDate) {

        this.maxDate = maxDate;
    }
    public void setInstrument(String instrument){
        this.instrument = instrument;
    }
    public String getInstrument(){
        return this.instrument;
    }
}
