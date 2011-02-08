package ch.i4ds.helio.frontend.query;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ricardodavid.guevara
 *
 * TODO: not in use check for delete
 */
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FlareQuery {

    private Date minDate;
    private Date maxDate;
    private String goes;
    private String test;

    public Date getDateTest() {
        return dateTest;
    }

    public void setDateTest(String dateTest) {
         DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date today = df.parse(dateTest);
        this.minDate = today;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.minDate = new Date();
    }
    private Date dateTest;

    public String getTest() {
         return this.test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getGoes() {
        return this.goes;
    }

    public void setGoes(String goes) {
        this.goes = goes;
    }

    public Date getMaxDate() {
        return maxDate;
    }

    public void setMinDate(Date minDate) {

      
        this.minDate = minDate;
    }

    public Date getMinDate() {
        
        return minDate;
    }

    public void setMaxDate(Date maxDate) {
        
        this.maxDate = maxDate;
    }
}
