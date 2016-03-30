package jarvis.utils;

import logging.util.CustomLogManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.channels.FileChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Jarvis on 3/30/16.
 */
public class FilesUtilsEnhance extends FilesUtils {

    private static Logger logger = CustomLogManager.getLogger(FilesUtils.class.getName());


    public static void fileChannelCopy(File sFile, File dFile) {
        FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;
        try {
            fi = new FileInputStream(sFile);
            fo = new FileOutputStream(dFile);
            logger.log(Level.FINEST, "得到原始文件对应的文件通道");
            in = fi.getChannel();
            logger.log(Level.FINEST, "得到目标文件对应的文件通道");
            out = fo.getChannel();

            logger.log(Level.FINEST, "连接两个通道，并且从in通道读取，然后写入out通道");
            logger.log(Level.FINE, "Begin to copy the file at " + DateUtils.getCurrentDatetime());
            BigDecimal beginTimeInLong = BigDecimal.valueOf(DateUtils.getCurrTimeInMillisecs());
            in.transferTo(0, in.size(), out);
            BigDecimal endTimeInLong = BigDecimal.valueOf(DateUtils.getCurrTimeInMillisecs());
            logger.log(Level.FINE, "Copy the file has been finished at " + DateUtils.getCurrentDatetime());
            logger.log(Level.FINE, " The copy process took about " + (endTimeInLong.subtract(beginTimeInLong)));

        } catch (IOException e) {
            logger.log(Level.FINE, " Exception happened ", e);
        } finally {
            CommonUtils.releaseResource(null, fi, in, fo, out);
        }
    }

}
