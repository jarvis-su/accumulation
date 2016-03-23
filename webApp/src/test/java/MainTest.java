import jarvis.utils.date.DateUtils;

public class MainTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(DateUtils.getDefaultDateFormat().format(
				DateUtils.pareseDate("2012/01/06")));
	}

}
