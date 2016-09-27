package my.test;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		IDisplay display = MapperManager.getInstance()
				.getMapper(IDisplay.class);
		System.out.println(display.say(new ArrayList<Object>()));
		Base b = new Base();
		Object o = b.nn(IBase.class, b);
		System.out.println(o);
	}
}
