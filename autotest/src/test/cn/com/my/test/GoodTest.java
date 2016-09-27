package cn.com.my.test;

import cn.com.my.MyClass;
import junit.framework.TestCase;

public class GoodTest extends TestCase {

	public void testG() {
		MyClass myClass = new MyClass();
		assertEquals(true, myClass.say(13));
	}
}
