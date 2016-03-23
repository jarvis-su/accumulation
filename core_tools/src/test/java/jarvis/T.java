package jarvis;

import jarvis.utils.DesUtil;
import logging.util.*;

import java.util.logging.Logger;

/**
 * Created by jarvis on 1/22/2016.
 */
public class T {
    static Logger logger = CustomLogManager.getLogger(T.class.getName());

    public static void main(String[] args) {
//        logger.fine("warning test!");

        logger.info("1111 = " + DesUtil.sha512Hex("1111"));
        logger.info("global = " + DesUtil.sha512Hex("global"));
    }
}
