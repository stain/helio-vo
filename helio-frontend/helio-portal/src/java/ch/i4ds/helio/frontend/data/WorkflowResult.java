package ch.i4ds.helio.frontend.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author David
 */
public class WorkflowResult {
    private int goes_id;
    private String ntime_start;
    private Date time_start;
    private String time_peak;
    private String time_end;
    private String ntime_end;
    private int nar;
    private float latitude;
    private float longitude;
    private float long_carr;
    private String xray_class;
    private String optical_class;
    private String hessi_measurementStart;
    private String hessi_measurementEnd;
    private String hessi_flareNr;
    private String hessi_measurementPeak;
    private String hessi_peakCS;
    private String hessi_totalCounts;
    private String hessi_energyKeVFrom;
    private String hessi_energyKeVTo;
    private String hessi_xPos;
    private String hessi_yPos;
    private String hessi_radial;
    private String hessi_AR;
    private String phoenix2_measurementStart;
    private String phoenix2_flareNr;
    private String phoenix2_peakCS;
    private String phoenix2_totalCounts;
    private String phoenix2_energyKeVFrom;
    private String phoenix2_energyKeVTo;
    private String phoenix2_xPos;
    private String phoenix2_yPos;
    private String phoenix2_radial;
    private String phoenix2_AR;
    private String phoenix2_urlPhaseFITSGZ;
    private String phoenix2_urlIntensityFITSGZ;
    private String urlPreview;
    private String urlPreviewThumb;


    
 public WorkflowResult(Stack<String> rawData) {
        int i= 0;
        
        this.goes_id = rawData.get(i++).trim().equals("")? 0 : Integer.parseInt(rawData.get(i-1));//789
        this.ntime_start = rawData.get(i++);//789
        
        this.setTime_start(rawData.get(i++));
        this.time_peak = rawData.get(i++);//789
        this.time_end = rawData.get(i++);
        this.ntime_end = rawData.get(i++);
        this.nar = rawData.get(i++).trim().equals("")? 0 : Integer.parseInt(rawData.get(i-1));//789
        this.latitude = rawData.get(i++).trim().equals("")? 0 :Float.parseFloat(rawData.get(i-1));//789
        this.longitude = rawData.get(i++).trim().equals("")? 0 :Float.parseFloat(rawData.get(i-1));//789
        this.long_carr = rawData.get(i++).trim().equals("")? 0 :Float.parseFloat(rawData.get(i-1));
        this.xray_class = rawData.get(i++);//789
        this.optical_class = rawData.get(i++);//789
        this.hessi_measurementStart = rawData.get(i++);
        this.hessi_measurementEnd = rawData.get(i++);
        this.hessi_flareNr = rawData.get(i++);
        this.hessi_measurementPeak = rawData.get(i++);
        this.hessi_peakCS = rawData.get(i++);
        this.hessi_totalCounts = rawData.get(i++);
        this.hessi_energyKeVFrom = rawData.get(i++);
        this.hessi_energyKeVTo = rawData.get(i++);
        this.hessi_xPos = rawData.get(i++);
        this.hessi_yPos = rawData.get(i++);
        this.hessi_radial = rawData.get(i++);
        this.hessi_AR = rawData.get(i++);
        this.phoenix2_measurementStart = rawData.get(i++);
        rawData.get(i++);
        this.phoenix2_flareNr = rawData.get(i++);
        this.phoenix2_peakCS = rawData.get(i++);
        this.phoenix2_totalCounts = rawData.get(i++);
        this.phoenix2_energyKeVFrom = rawData.get(i++);
        this.phoenix2_energyKeVTo = rawData.get(i++);
        this.phoenix2_xPos = rawData.get(i++);
        this.phoenix2_yPos = rawData.get(i++);
        this.phoenix2_radial = rawData.get(i++);
        this.phoenix2_AR = rawData.get(i++);
        this.phoenix2_urlPhaseFITSGZ = rawData.get(i++);
        this.phoenix2_urlIntensityFITSGZ =rawData.get(i++);
        this.urlPreview =rawData.get(i++);
        
        this.urlPreviewThumb = rawData.get(i++);
        
        

    }




    /**
     * @return the goes_id
     */
    public int getGoes_id() {
        return goes_id;
    }

    /**
     * @param goes_id the goes_id to set
     */
    public void setGoes_id(int goes_id) {
        this.goes_id = goes_id;
    }

    /**
     * @return the ntime_start
     */
    public String getNtime_start() {
        return ntime_start;
    }

    /**
     * @param ntime_start the ntime_start to set
     */
    public void setNtime_start(String ntime_start) {
        this.ntime_start = ntime_start;
    }

    /**
     * @return the time_start
     */
    public Date getTime_start() {
        return time_start;
    }

    /**
     * @param time_start the time_start to set
     */
    public void setTime_start(String time_start) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//2004-11-01 07:06:00
         Date d = null;
        try {
            d = sdf.parse(time_start);
        } catch (ParseException ex) {
            Logger.getLogger(WorkflowResult.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.time_start = d;
    }

    /**
     * @return the time_peak
     */
    public String getTime_peak() {
        return time_peak;
    }

    /**
     * @param time_peak the time_peak to set
     */
    public void setTime_peak(String time_peak) {
        this.time_peak = time_peak;
    }

    /**
     * @return the time_end
     */
    public String getTime_end() {
        return time_end;
    }

    /**
     * @param time_end the time_end to set
     */
    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    /**
     * @return the ntime_end
     */
    public String getNtime_end() {
        return ntime_end;
    }

    /**
     * @param ntime_end the ntime_end to set
     */
    public void setNtime_end(String ntime_end) {
        this.ntime_end = ntime_end;
    }

    /**
     * @return the nar
     */
    public int getNar() {
        return nar;
    }

    /**
     * @param nar the nar to set
     */
    public void setNar(int nar) {
        this.nar = nar;
    }

    /**
     * @return the latitude
     */
    public float getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public float getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    /**
     * @return the long_carr
     */
    public float getLong_carr() {
        return long_carr;
    }

    /**
     * @param long_carr the long_carr to set
     */
    public void setLong_carr(float long_carr) {
        this.long_carr = long_carr;
    }

    /**
     * @return the xray_class
     */
    public String getXray_class() {
        return xray_class;
    }

    /**
     * @param xray_class the xray_class to set
     */
    public void setXray_class(String xray_class) {
        this.xray_class = xray_class;
    }

    /**
     * @return the optical_class
     */
    public String getOptical_class() {
        return optical_class;
    }

    /**
     * @param optical_class the optical_class to set
     */
    public void setOptical_class(String optical_class) {
        this.optical_class = optical_class;
    }

    /**
     * @return the hessi_measurementStart
     */
    public String getHessi_measurementStart() {
        return hessi_measurementStart;
    }

    /**
     * @param hessi_measurementStart the hessi_measurementStart to set
     */
    public void setHessi_measurementStart(String hessi_measurementStart) {
        this.hessi_measurementStart = hessi_measurementStart;
    }

    /**
     * @return the hessi_measurementEnd
     */
    public String getHessi_measurementEnd() {
        return hessi_measurementEnd;
    }

    /**
     * @param hessi_measurementEnd the hessi_measurementEnd to set
     */
    public void setHessi_measurementEnd(String hessi_measurementEnd) {
        this.hessi_measurementEnd = hessi_measurementEnd;
    }

    /**
     * @return the hessi_flareNr
     */
    public String getHessi_flareNr() {
        return hessi_flareNr;
    }

    /**
     * @param hessi_flareNr the hessi_flareNr to set
     */
    public void setHessi_flareNr(String hessi_flareNr) {
        this.hessi_flareNr = hessi_flareNr;
    }

    /**
     * @return the hessi_measurementPeak
     */
    public String getHessi_measurementPeak() {
        return hessi_measurementPeak;
    }

    /**
     * @param hessi_measurementPeak the hessi_measurementPeak to set
     */
    public void setHessi_measurementPeak(String hessi_measurementPeak) {
        this.hessi_measurementPeak = hessi_measurementPeak;
    }

    /**
     * @return the hessi_peakCS
     */
    public String getHessi_peakCS() {
        return hessi_peakCS;
    }

    /**
     * @param hessi_peakCS the hessi_peakCS to set
     */
    public void setHessi_peakCS(String hessi_peakCS) {
        this.hessi_peakCS = hessi_peakCS;
    }

    /**
     * @return the hessi_totalCounts
     */
    public String getHessi_totalCounts() {
        return hessi_totalCounts;
    }

    /**
     * @param hessi_totalCounts the hessi_totalCounts to set
     */
    public void setHessi_totalCounts(String hessi_totalCounts) {
        this.hessi_totalCounts = hessi_totalCounts;
    }

    /**
     * @return the hessi_energyKeVFrom
     */
    public String getHessi_energyKeVFrom() {
        return hessi_energyKeVFrom;
    }

    /**
     * @param hessi_energyKeVFrom the hessi_energyKeVFrom to set
     */
    public void setHessi_energyKeVFrom(String hessi_energyKeVFrom) {
        this.hessi_energyKeVFrom = hessi_energyKeVFrom;
    }

    /**
     * @return the hessi_energyKeVTo
     */
    public String getHessi_energyKeVTo() {
        return hessi_energyKeVTo;
    }

    /**
     * @param hessi_energyKeVTo the hessi_energyKeVTo to set
     */
    public void setHessi_energyKeVTo(String hessi_energyKeVTo) {
        this.hessi_energyKeVTo = hessi_energyKeVTo;
    }

    /**
     * @return the hessi_xPos
     */
    public String getHessi_xPos() {
        return hessi_xPos;
    }

    /**
     * @param hessi_xPos the hessi_xPos to set
     */
    public void setHessi_xPos(String hessi_xPos) {
        this.hessi_xPos = hessi_xPos;
    }

    /**
     * @return the hessi_yPos
     */
    public String getHessi_yPos() {
        return hessi_yPos;
    }

    /**
     * @param hessi_yPos the hessi_yPos to set
     */
    public void setHessi_yPos(String hessi_yPos) {
        this.hessi_yPos = hessi_yPos;
    }

    /**
     * @return the hessi_radial
     */
    public String getHessi_radial() {
        return hessi_radial;
    }

    /**
     * @param hessi_radial the hessi_radial to set
     */
    public void setHessi_radial(String hessi_radial) {
        this.hessi_radial = hessi_radial;
    }

    /**
     * @return the hessi_AR
     */
    public String getHessi_AR() {
        return hessi_AR;
    }

    /**
     * @param hessi_AR the hessi_AR to set
     */
    public void setHessi_AR(String hessi_AR) {
        this.hessi_AR = hessi_AR;
    }

    /**
     * @return the phoenix2_measurementStart
     */
    public String getPhoenix2_measurementStart() {
        return phoenix2_measurementStart;
    }

    /**
     * @param phoenix2_measurementStart the phoenix2_measurementStart to set
     */
    public void setPhoenix2_measurementStart(String phoenix2_measurementStart) {
        this.phoenix2_measurementStart = phoenix2_measurementStart;
    }

    /**
     * @return the phoenix2_flareNr
     */
    public String getPhoenix2_flareNr() {
        return phoenix2_flareNr;
    }

    /**
     * @param phoenix2_flareNr the phoenix2_flareNr to set
     */
    public void setPhoenix2_flareNr(String phoenix2_flareNr) {
        this.phoenix2_flareNr = phoenix2_flareNr;
    }

    /**
     * @return the phoenix2_peakCS
     */
    public String getPhoenix2_peakCS() {
        return phoenix2_peakCS;
    }

    /**
     * @param phoenix2_peakCS the phoenix2_peakCS to set
     */
    public void setPhoenix2_peakCS(String phoenix2_peakCS) {
        this.phoenix2_peakCS = phoenix2_peakCS;
    }

    /**
     * @return the phoenix2_totalCounts
     */
    public String getPhoenix2_totalCounts() {
        return phoenix2_totalCounts;
    }

    /**
     * @param phoenix2_totalCounts the phoenix2_totalCounts to set
     */
    public void setPhoenix2_totalCounts(String phoenix2_totalCounts) {
        this.phoenix2_totalCounts = phoenix2_totalCounts;
    }

    /**
     * @return the phoenix2_energyKeVFrom
     */
    public String getPhoenix2_energyKeVFrom() {
        return phoenix2_energyKeVFrom;
    }

    /**
     * @param phoenix2_energyKeVFrom the phoenix2_energyKeVFrom to set
     */
    public void setPhoenix2_energyKeVFrom(String phoenix2_energyKeVFrom) {
        this.phoenix2_energyKeVFrom = phoenix2_energyKeVFrom;
    }

    /**
     * @return the phoenix2_energyKeVTo
     */
    public String getPhoenix2_energyKeVTo() {
        return phoenix2_energyKeVTo;
    }

    /**
     * @param phoenix2_energyKeVTo the phoenix2_energyKeVTo to set
     */
    public void setPhoenix2_energyKeVTo(String phoenix2_energyKeVTo) {
        this.phoenix2_energyKeVTo = phoenix2_energyKeVTo;
    }

    /**
     * @return the phoenix2_xPos
     */
    public String getPhoenix2_xPos() {
        return phoenix2_xPos;
    }

    /**
     * @param phoenix2_xPos the phoenix2_xPos to set
     */
    public void setPhoenix2_xPos(String phoenix2_xPos) {
        this.phoenix2_xPos = phoenix2_xPos;
    }

    /**
     * @return the phoenix2_yPos
     */
    public String getPhoenix2_yPos() {
        return phoenix2_yPos;
    }

    /**
     * @param phoenix2_yPos the phoenix2_yPos to set
     */
    public void setPhoenix2_yPos(String phoenix2_yPos) {
        this.phoenix2_yPos = phoenix2_yPos;
    }

    /**
     * @return the phoenix2_radial
     */
    public String getPhoenix2_radial() {
        return phoenix2_radial;
    }

    /**
     * @param phoenix2_radial the phoenix2_radial to set
     */
    public void setPhoenix2_radial(String phoenix2_radial) {
        this.phoenix2_radial = phoenix2_radial;
    }

    /**
     * @return the phoenix2_AR
     */
    public String getPhoenix2_AR() {
        return phoenix2_AR;
    }

    /**
     * @param phoenix2_AR the phoenix2_AR to set
     */
    public void setPhoenix2_AR(String phoenix2_AR) {
        this.phoenix2_AR = phoenix2_AR;
    }

    /**
     * @return the phoenix2_urlPhaseFITSGZ
     */
    public String getPhoenix2_urlPhaseFITSGZ() {
        return phoenix2_urlPhaseFITSGZ;
    }

    /**
     * @param phoenix2_urlPhaseFITSGZ the phoenix2_urlPhaseFITSGZ to set
     */
    public void setPhoenix2_urlPhaseFITSGZ(String phoenix2_urlPhaseFITSGZ) {
        this.phoenix2_urlPhaseFITSGZ = phoenix2_urlPhaseFITSGZ;
    }

    /**
     * @return the phoenix2_urlIntensityFITSGZ
     */
    public String getPhoenix2_urlIntensityFITSGZ() {
        return phoenix2_urlIntensityFITSGZ;
    }

    /**
     * @param phoenix2_urlIntensityFITSGZ the phoenix2_urlIntensityFITSGZ to set
     */
    public void setPhoenix2_urlIntensityFITSGZ(String phoenix2_urlIntensityFITSGZ) {
        this.phoenix2_urlIntensityFITSGZ = phoenix2_urlIntensityFITSGZ;
    }

    /**
     * @return the urlPreview
     */
    public String getUrlPreview() {
        return urlPreview;
    }

    /**
     * @param urlPreview the urlPreview to set
     */
    public void setUrlPreview(String urlPreview) {
        this.urlPreview = urlPreview;
    }

    /**
     * @return the urlPreviewThumb
     */
    public String getUrlPreviewThumb() {
        return urlPreviewThumb;
    }

    /**
     * @param urlPreviewThumb the urlPreviewThumb to set
     */
    public void setUrlPreviewThumb(String urlPreviewThumb) {
        this.urlPreviewThumb = urlPreviewThumb;
    }
}
