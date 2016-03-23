public class T {
	public static boolean isPlalindrome(String s) {
		for (int i = 0; i <= s.length() / 2; i++) {
			if (s.charAt(i) != s.charAt(s.length() - 1 - i)) {
				return false;
			}
		}
		return true;
	}

	public static void method(double min, double max) {
		min = Math.sqrt(min);
		max = Math.sqrt(max);
		double y = 0;
		for (double x = Math.ceil(min); x < max; x++) {
			if (x % 10 > 0) {
				y = x * x;
				if (y % 10 > 0) {
					String xStr = String.valueOf((long) x);
					if (isPlalindrome(xStr)) {
						String yStr = String.valueOf((long) y);
						if (isPlalindrome(yStr)) {
							System.out.println(yStr + '(' + xStr + ')');
						}
					}
				}
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long t = System.currentTimeMillis();
		method(1d, 100000000000000d);
		System.out.println(System.currentTimeMillis() - t);

	}

}
