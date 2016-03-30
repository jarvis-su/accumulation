package jarvis.utils;

import logging.util.CustomLogManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
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

            in = fi.getChannel();//得到对应的文件通道

            out = fo.getChannel();//得到对应的文件通道

            in.transferTo(0, in.size(), out);//连接两个通道，并且从in通道读取，然后写入out通道

        } catch (IOException e) {

            e.printStackTrace();

        } finally {
            CommonUtils.releaseResource(null, fi, in, fo, out);


        }
    }


}
