package jarvis.samples.callback.sam2;

public class Test {
	public static void main(String[] args) {
		/**
		 * new 一个小李
		 */
		XiaoLi li = new XiaoLi();

		/**
		 * new 一个小王
		 */
		XiaoWang wang = new XiaoWang(li);

		/**
		 * 小王问小李问题
		 */
		wang.askQuestion("1 + 1 = ?");
	}
}
