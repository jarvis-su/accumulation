package jarvis.samples.callback;

public class XiaoLi {
	public void executeMessage(CallBack callBack, String question) {
		System.out.println("小王问的问题--->" + question);
		for (int i = 0; i < 10000; i++) {

		}
		String result = "答案是2";
		callBack.solve(result);
	}
}
