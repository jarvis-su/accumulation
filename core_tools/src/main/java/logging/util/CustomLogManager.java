package logging.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import logging.formatter.MySimpleFormatter;
import logging.handler.DefaultStreamHandler;

/**
 * CustomLogManager
 *
 * @author Jarvis Su
 * @date 2/17/2016
 */

public class CustomLogManager {
    static {
        LogManager.getLogManager().reset(); // reset the log manager
    }

    // read the log configuration from outside property file
    public static void resetFromPropertyFile(String filePath) {
        LogManager logManager = LogManager.getLogManager();
        InputStream inputStream = CustomLogManager.class.getClassLoader().getResourceAsStream(filePath);
        try {
            logManager.readConfiguration(inputStream);
        } catch (Exception e) {
            throw new RuntimeException("reload log configuration fail: " + e.toString());
        }
    }


    public static Logger getLogger(String name) {
        Logger logger = Logger.getLogger(name);
        logger.setLevel(Level.ALL);

        // add console handler
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.FINE);
        consoleHandler.setFormatter(new MySimpleFormatter());
        logger.addHandler(consoleHandler);

        // add file handler
        FileHandler fileHandler = null;
        try {
            fileHandler = new FileHandler("log.log", true);
            fileHandler.setLevel(Level.FINE);
            fileHandler.setFormatter(new MySimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // add default stream handler
        DefaultStreamHandler defaultStreamHandler = new DefaultStreamHandler();
        defaultStreamHandler.setLevel(Level.ALL);
        defaultStreamHandler.setFormatter(new MySimpleFormatter());
        defaultStreamHandler.setOutputStream(System.out);
        logger.addHandler(defaultStreamHandler);

        return logger;
    }
}

