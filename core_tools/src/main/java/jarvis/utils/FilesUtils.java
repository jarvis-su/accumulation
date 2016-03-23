package jarvis.utils;

import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

/**
 * FilesUtils
 *
 * @author Jarvis Su
 * @date 1/22/2016
 */
public class FilesUtils {

    public static long copyFile(File file, String destFolder) {
        long beginDateTime = DateUtils.getCurrTimeInMillisecs();
        String sourceFileFolderPrefix = file.getAbsolutePath();
        String destFolderPrefix = destFolder;

        createNewFolder(destFolderPrefix);
        File[] files = {};
        if (file.isDirectory()) {
            files = file.listFiles();
            for (File f : files) {
                if (f.isDirectory()) {
                    String fileSubFolder = StringUtils.removeStart(f.getAbsolutePath(), sourceFileFolderPrefix);
                    String folder = destFolderPrefix + CommonUtils.getSystemFileSeparator() + fileSubFolder;
                    createNewFolder(folder);
                    copyFile(f, folder);
                } else {
                    copyStandaloneFile(f, destFolderPrefix);
                }
            }
        } else {
            copyStandaloneFile(file, destFolder);
        }

        long endDateTime = DateUtils.getCurrTimeInMillisecs();
        return endDateTime - beginDateTime;
    }

    public static long copyStandaloneFile(File file, String destFolder) {
        long beginDateTime = DateUtils.getCurrTimeInMillisecs();
        int bytesum = 0;
        int byteread = 0;
        InputStream inStream = null;
        FileOutputStream fs = null;
        try {
            //File     oldfile     =     new     File(oldPath);
            if (file.exists()) {
                inStream = new FileInputStream(file);
                String newFileName = destFolder + CommonUtils.getSystemFileSeparator() + file.getName();
                File newFile = new File(newFileName);
                if (!newFile.exists()) {
                    fs = new FileOutputStream(newFileName);
                    byte[] buffer = new byte[1444];
                    while ((byteread = inStream.read(buffer)) != -1) {
                        bytesum += byteread;
                        System.out.println(bytesum);
                        fs.write(buffer, 0, byteread);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("error  ");
            e.printStackTrace();
        } finally {
            CommonUtils.releaseResource(null, inStream, fs);
        }
        long endDateTime = DateUtils.getCurrTimeInMillisecs();
        return endDateTime - beginDateTime;
    }

    public static void createNewFolder(String dest) {
        File destFolderFile = new File(dest);
        if (!destFolderFile.exists()) {
            destFolderFile.mkdir();
        }
    }

    public static boolean Move(File srcFile, String destPath) {
        // Destination directory
        File dir = new File(destPath);
        // Move file to new directory
        boolean success = srcFile.renameTo(new File(dir, srcFile.getName()));

        return success;
    }

    public static boolean Move(String srcFile, String destPath) {
        // File (or directory) to be moved
        File file = new File(srcFile);

        // Destination directory
        File dir = new File(destPath);

        // Move file to new directory
        boolean success = file.renameTo(new File(dir, file.getName()));

        return success;
    }

    /**
     * @param args
     */
    private static int filesToBeCopied = -1;
    // 要创建的线程数
    private static int THREAD_COUNT = 4;
    private static int n = 0;
    private static String sourcePath;
    private static String destPath;

    // 递归的方式读取文件
    public static int setCopiedFiles(String path, List<File> pool) {
        File sourceFile = new File(path);
        if (sourceFile.isFile()) {
            // 同步线程池，并唤醒所有等待的线程
            synchronized (pool) {
                pool.add(sourceFile);
                pool.notifyAll();
                n++;
            }
        } else {
            File[] files = sourceFile.listFiles();
            for (int i = 0; i < files.length; i++) {
                String tempString = files[i].getAbsolutePath();
                setCopiedFiles(tempString, pool);
            }
        }
        return n;
    }

    public static int getFilesToBeCopied() {
        return filesToBeCopied;
    }

    // 设置目录
    public static void setFilePath() {
        String temp = JOptionPane
                .showInputDialog("Input Source File Path(like C:\\\\projects) :");
        sourcePath = temp;
        temp = JOptionPane
                .showInputDialog("Input Target File Path(like C:\\\\projects) :");
        destPath = temp;
    }

    public static String getSourceFilePath() {
        return sourcePath;
    }

    public static String getTargetFilePath() {
        return destPath;
    }
}
