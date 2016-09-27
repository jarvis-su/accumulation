package cn.com.my.test;

import junit.framework.TestCase;

public class FailTest extends TestCase {

	public void testG() {
		assertEquals(true, true);
	}
	public void testF() {
		fail();
	}
}
