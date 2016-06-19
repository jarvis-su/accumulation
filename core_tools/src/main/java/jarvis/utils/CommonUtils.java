package jarvis.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import logging.util.CustomLogManager;

/**
 * CommonUtils
 *
 * @author Jarvis Su
 * @date 1/22/2016
 */
public class CommonUtils {
    private static Logger logger = CustomLogManager.getLogger(CommonUtils.class.getName());

    public static void releaseResource(@SuppressWarnings("unused") Object source, AutoCloseable... closeableResource) {
        if (closeableResource == null || closeableResource.length == 0) {
            return;
        }
        for (AutoCloseable closeable : closeableResource) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (Exception e) {
                    logger.log(Level.FINE,"Throw exception due to ", e);
                }
            }
        }
    }

    public static String getSystemFileSeparator() {
        return System.getProperty("file.separator");
    }

    public static void propertiesTest() {
        Properties properties = System.getProperties();
        Iterator<?> it = properties.entrySet().iterator();
        while (it.hasNext()) {
            @SuppressWarnings("rawtypes")
			Map.Entry entry = (Map.Entry) it.next();
            logger.log(Level.FINEST,entry.getKey() + "=" + entry.getValue());
        }
    }
}
