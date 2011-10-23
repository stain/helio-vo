package eu.heliovo.shared.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.GenericConversionService;

import eu.heliovo.shared.util.AssertUtil;

/**
 * Simple parser to read data from a delimited file.
 * @author MarcoSoldati
 *
 */
public class DataReaderUtil {
    
    /**
     * Separator character between values
     */
    private static final String VALUE_SEPARATOR = "\\|";
    
    /**
     * Separator for list values.
     */
    private static final String LIST_SEPARATOR = "\\,";
    
    /**
     *  Character to mark a commend
     */
    private static final String COMMENT = "#";

    /**
     * A conversion service for data type conversion.
     */
    private final ConversionService conversionService = new GenericConversionService();
    
    /**
     * The parsed test data.
     */
    private final Collection<Object[]> dataCollection;
    
    /**
     * create the util for a given test file
     * @param dataStream
     */
    public DataReaderUtil(InputStream dataStream) {
        AssertUtil.assertArgumentNotNull(dataStream, "dataStream");
        dataCollection = parse(dataStream);
    }
    
    /**
     * parse the test data into a collection of Object[].
     * @param dataStream the datastream
     * @return the collection of data.
     */
    private Collection<Object[]> parse(InputStream dataStream) {
        List<Class<?>> types = new ArrayList<Class<?>>();
        List<Object[]> ret = new ArrayList<Object[]>();
        try {
            LineIterator it = IOUtils.lineIterator(dataStream, "UTF-8");
            int lineNumber = 0;
            while (it.hasNext()) {
                lineNumber++;
                String line = it.nextLine();
                
                // remove trailing comment
                int comment = line.indexOf(COMMENT);
                if (comment >= 0) {
                    line = line.substring(0, comment);
                }
                
                // trim line
                line = line.trim();
                
                if (line.isEmpty()) {
                    // ignore
                } else {
                    String[] cells = line.split(VALUE_SEPARATOR);
                    //System.out.println(Arrays.toString(cells));
                    // first row must be the type definition
                    if (types.isEmpty()) {
                        for (int i = 0; i < cells.length; i++) {
                            String cell = cells[i];
                            Class<?> cellType;
                            try {
                                cellType = Class.forName(cell.trim());
                            } catch (ClassNotFoundException e) {
                                throw new RuntimeException("Unknown type at position [" + lineNumber + "," + i + "]: " + cell.trim());
                            }
                            types.add(cellType);
                        }
                    } else {
                        Object[] currentLine = new Object[types.size()];
                        for (int i = 0; i < cells.length; i++) {
                            String cell = cells[i];
                            Class<?> type = types.get(i);
                            //System.out.println(type + " - " + type.getComponentType());
                            if (type.isArray()) {
                                String[] values = cell.split(LIST_SEPARATOR);
                                Object[] arr = (Object[])Array.newInstance(type.getComponentType(), values.length);
                                for (int j = 0; j < values.length; j++) {
                                    String val = values[j];
                                    Object valObj = conversionService.convert(val.trim(), type.getComponentType());
                                    arr[j] = valObj;
                                }
                                currentLine[i] = arr;
                            } else if (type.isEnum()) {
                                try {
                                Method method = type.getMethod("valueOf", new Class[] {String.class});
                                currentLine[i] = method.invoke(null, cell.trim());
                                } catch (Exception e) {
                                    throw new RuntimeException("Unable to create enum of type " + type + ": " + e.getMessage(), e);
                                }
                            } else {
                                Object obj = conversionService.convert(cell.trim(), type);
                                currentLine[i] = obj;
                            }
                        }
                        ret.add(currentLine);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to read configuration from " + dataStream + ": " + e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(dataStream);
        }
        return ret;
    }
    
    /**
     * Get the data collection
     * @return the data collection
     */
    public Collection<Object[]> getDataCollection() {
        return dataCollection;
    }
}
