package com.jarvis.supporter.logger;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

/**
 * Created by Jarvis on 4/18/16.
 */
public class Log4jAdapter extends Logger {

    protected Log4jAdapter(String name) {
        super(name);
    }
    private static final LoggerFactory ADAPTER_FACTORY = new LoggerFactory() {
        @Override

        public org.apache.log4j.Logger makeNewLoggerInstance(String name) {
            return new Log4jAdapter(name);
        }
    };

    public static Log4jAdapter getLog4jAdapter(Class<?> clazz) {
        return getLog4jAdapter(clazz.getName());
    }

    public static Log4jAdapter getLog4jAdapter(String name) {
        Log4jAdapter adapter = (Log4jAdapter) LogManager.getLogger(name,
                ADAPTER_FACTORY);
        return adapter;
    }
}
