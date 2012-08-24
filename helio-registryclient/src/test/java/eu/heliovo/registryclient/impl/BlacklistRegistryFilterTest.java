package eu.heliovo.registryclient.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import eu.heliovo.registryclient.RegistryProperties;

public class BlacklistRegistryFilterTest {
    /**
     * In memory properties for testing only.
     * @author MarcoSoldati
     */
    class RegistryPropertiesMock extends HashMap<String, String> implements RegistryProperties {
        @Override
        public String getProperty(String property) {
            return getProperty(property, null);
        }
        
        @Override
        public String getProperty(String property, String defaultValue) {
            String val = get(property);
            return val == null ? defaultValue : val;
        }
    }
    
    private BlacklistRegistryFilter  blacklistRegistryFilter;

    @Before public void setup() {
        blacklistRegistryFilter = new BlacklistRegistryFilter();
        RegistryProperties registryProperties = new RegistryPropertiesMock();
        blacklistRegistryFilter.setRegistryProperties(registryProperties);
    }
    
    
    @Test public void testUnsetFilter() {
        assertFalse(blacklistRegistryFilter.filterAccessUrl("http://test.ch"));
    }
    
    @Test public void testEmptyFilter() {
        String filterExpressions = "";
        setFilterExpressions(filterExpressions);
        assertFalse(blacklistRegistryFilter.filterAccessUrl("http://test.ch"));        
    }
    
    @Test public void testOneFilterExpression() {
        String filterExpressions = ".*test.*";
        setFilterExpressions(filterExpressions);
        assertFalse(blacklistRegistryFilter.filterAccessUrl("http://foo.ch"));        
        assertTrue(blacklistRegistryFilter.filterAccessUrl("http://test.ch"));        
        assertTrue(blacklistRegistryFilter.filterAccessUrl("http://TEST.ch"));        
    }
    
    @Test public void testMultiFilterExpression() {
        String filterExpressions = ".*test.*,http\\://foo.ch.*";
        setFilterExpressions(filterExpressions);
        assertTrue(blacklistRegistryFilter.filterAccessUrl("http://TEST.ch"));        
        assertTrue(blacklistRegistryFilter.filterAccessUrl("http://test.ch"));        
        assertFalse(blacklistRegistryFilter.filterAccessUrl("http://bar.ch"));        
        assertTrue(blacklistRegistryFilter.filterAccessUrl("http://foo.ch"));        
    }


    private void setFilterExpressions(String filterExpressions) {
        blacklistRegistryFilter.getRegistryProperties().put(BlacklistRegistryFilter.ACCESS_URL_BLACKLIST, filterExpressions);
        blacklistRegistryFilter.init();
    } 
}
