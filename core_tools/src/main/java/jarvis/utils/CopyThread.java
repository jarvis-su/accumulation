package jarvis.utils;

import java.io.*;
import java.util.List;

/**
 * CopyThread
 *
 * @author Jarvis Su
 * @date 1/22/2016
 */
public class CopyThread extends Thread {
    private static int copiedFiles = 0;
    private List<File> pool;

    public CopyThread(List<File> pool) {
        this.pool = pool;
    }

    public void run() {
        File input = null;
        while (copiedFiles != FilesUtils.getFilesToBeCopied()) {
            // 当线程池为空时，等待
            synchronized (pool) {
                while (pool.isEmpty()) {
                    if (copiedFiles == FilesUtils.getFilesToBeCopied())
                        return;
                    try {
                        pool.wait();
                    } catch (InterruptedException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }
                }
                input = (File) pool.remove(pool.size() - 1);
                copiedFilesIncrement();
            }
            // 文件复制
            try {
                FileInputStream in = new FileInputStream(input);
                BufferedInputStream bi = new BufferedInputStream(in);
                String targetPath = FilesUtils.getTargetFilePath();
                File destFile = new File(targetPath, input.getName());
                FileOutputStream out = new FileOutputStream(destFile);
                BufferedOutputStream bo = new BufferedOutputStream(out);
                int b;
                while ((b = bi.read()) != -1) {
                    bo.write(b);
                }
                bo.flush();
                bi.close();
                in.close();
                out.close();
                bo.close();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
        System.exit(0);
    }

    private static synchronized void copiedFilesIncrement() {
        copiedFiles++;
    }
}
