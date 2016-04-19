package jarvis.utils.other;

import java.io.File;

public class CallExe {
	public static void main(String[] args) {
		Runtime rt = Runtime.getRuntime();
		Process p = null;
		String fileLac = "";
		try {
			fileLac = "D:" + File.separator + "Program Files (x86)"
					+ File.separator + "QQ.exe";// 所调用的程序路径
			p = rt.exec(fileLac);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("open failure");
		}
	}

}