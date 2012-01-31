package eu.heliovo.clientapi.linkprovider.impl;

import java.net.URL;
import java.util.Calendar;
import java.util.Date;

import javax.activation.MimeType;

import eu.heliovo.clientapi.linkprovider.LinkProviderService;
import eu.heliovo.registryclient.HelioServiceName;
import eu.heliovo.shared.props.HelioFileUtil;
import eu.heliovo.shared.util.AssertUtil;

/**
 * Abstract base class for provision of daily links.
 * @author MarcoSoldati
 *
 */
abstract class AbstractDailyLinkProvider implements LinkProviderService {
    /**
     * Template to create the URL
     */
    private final String providerTemplate;
    /**
     * The simple title
     */
    private final String title;
    
    /**
     * Template for the context aware title.
     */
    private final String titleTemplate;
    
    /**
     * Name of the service variant. Should start with ivo:// and must be unique.
     */
    private final String serviceVariant;
    
    /**
     * Description
     */
    private final String description;
    
    /**
     * Create the daily provider.
     * @param providerTemplate the template to create the URL, will be passed the start and end time.
     * @param title the simple title
     * @param titleTemplate the context aware title, will be passed the start and end time.
     * @param serviceVariant the unique name of the link provider.
     * @param description a general purpose description.
     */
    public AbstractDailyLinkProvider(String providerTemplate, String title, String titleTemplate, String serviceVariant, String description) {
        this.providerTemplate = providerTemplate;
        this.title = title;
        this.titleTemplate = titleTemplate;
        this.serviceVariant = serviceVariant;
        this.description = description;
    }
    
    @Override
    public URL getLink(Date startTime, Date endTime) {
        AssertUtil.assertArgumentNotNull(startTime, "startTime");
        if (pageExists(startTime, endTime)) {
            return HelioFileUtil.asURL(String.format(providerTemplate, startTime, endTime));
        }
        return null;
    }
    
    @Override
    public String getTitle() {
        return getTitle(null, null);
    }
    
    @Override
    public String getTitle(Date startTime, Date endTime) {
        if (startTime == null) {
            return title;
        }
        if (pageExists(startTime, endTime)) {
            return String.format(titleTemplate, startTime, endTime);
        }
        return null;
    }
    
    @Override
    public String getServiceVariant() {
        return serviceVariant;
    }

    @Override
    public HelioServiceName getServiceName() {
        return HelioServiceName.LPS;
    }

    @Override
    public String getDescription() {
        return description;
    }
    
    @Override
    public MimeType getMimeType() {
        return MimeTypeConstants.TEXT_HTML;
    }

    /**
     * Check if the page exists for the given date range.
     * @param startTime the start time to check for
     * @param endTime the end time to check for.
     * @return true if the page exists.
     */
    protected abstract boolean pageExists(Date startTime, Date endTime);
    
    /**
     * Create a date based on the default calendar instance.
     * Time will be set to 0.
     * @param year the year in four numbers.
     * @param month the month defined by {@link Calendar#JANUARY}, {@link Calendar#FEBRUARY}, ...
     * @param day the day
     * @return a date instance for the current locale.
     */
    protected static Date asDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(year, month, day);
        return cal.getTime();
    }


}