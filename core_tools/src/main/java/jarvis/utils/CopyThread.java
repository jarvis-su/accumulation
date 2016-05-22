package jarvis.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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

	@Override
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
				input = pool.remove(pool.size() - 1);
				copiedFilesIncrement();
			}
			// 文件复制
			FileInputStream in = null;
			BufferedInputStream bi = null;
			FileOutputStream out = null;
			BufferedOutputStream bo = null;
			try {
				in = new FileInputStream(input);
				bi = new BufferedInputStream(in);
				String targetPath = FilesUtils.getTargetFilePath();
				File destFile = new File(targetPath, input.getName());
				out = new FileOutputStream(destFile);
				bo = new BufferedOutputStream(out);
				int b;
				while ((b = bi.read()) != -1) {
					bo.write(b);
				}
				bo.flush();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				CommonUtils.releaseResource(this, bi);
				CommonUtils.releaseResource(this, in);
				CommonUtils.releaseResource(this, out);
				CommonUtils.releaseResource(this, bo);
			}
		}
		System.exit(0);
	}

	private static synchronized void copiedFilesIncrement() {
		copiedFiles++;
	}
}
