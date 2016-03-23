package jarvis.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * CommonUtils
 *
 * @author Jarvis Su
 * @date 1/22/2016
 */
public class CommonUtils {
    public static void releaseResource(Object source, AutoCloseable... closeableResource) {
        if (closeableResource == null || closeableResource.length == 0) {
            return;
        }
        for (AutoCloseable closeable : closeableResource) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (Throwable th) {
                    closeable = null;
                }
            }
        }
    }

    public static String getSystemFileSeparator() {
        return System.getProperty("file.separator");
    }

    public static void propertiesTest() {
        Properties properties = System.getProperties();
        Iterator it = properties.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            System.out.print(entry.getKey() + "=");
            System.out.println(entry.getValue());
        }
    }
}
