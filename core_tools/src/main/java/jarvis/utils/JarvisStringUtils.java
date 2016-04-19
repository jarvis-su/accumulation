package jarvis.utils;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import logging.util.CustomLogManager;

public class JarvisStringUtils {
    private static Logger logger = CustomLogManager.getLogger(JarvisStringUtils.class.getName());

    public static boolean isMatched(String sourceStr, Pattern pattern) {
        Matcher match = pattern.matcher(sourceStr);
        if (match.matches()) {
            logger.info(sourceStr + " is matched ");
            return true;
        } else {
            logger.info(sourceStr + " is not matched ");
            return false;
        }
    }

    public static boolean isMatched(String sourceStr, String patternStr) {
        Pattern pattern = Pattern.compile(patternStr);
        return isMatched(sourceStr, pattern);
    }
}
