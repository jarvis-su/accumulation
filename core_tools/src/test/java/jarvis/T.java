package jarvis;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Logger;

import org.apache.commons.lang3.time.FastDateFormat;

import jarvis.utils.DesUtil;
import logging.util.CustomLogManager;

/**
 * Created by jarvis on 1/22/2016.
 */
public class T {
    static Logger logger = CustomLogManager.getLogger(T.class.getName());

    public static void main(String[] args) {
//        logger.fine("warning test!");

        logger.info("1111 = " + DesUtil.sha512HexEncrypt("1111"));
        logger.info("global = " + DesUtil.sha512HexEncrypt("global"));

        GregorianCalendar cal = new GregorianCalendar();
//        cal.setTime((Date) date.clone());

        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, cal.getMaximum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getMaximum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, 0);
//        cal.set(Calendar.HOUR, 23);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd H:m:s.S");
        logger.info(sdf.format(cal.getTime()));

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
        logger.info(sdf1.format(cal.getTime()));

        FastDateFormat fdf = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
        logger.info(fdf.format(cal));

    }
}
