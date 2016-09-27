package my.test;

import java.util.List;

public class Base implements IBase {

	@Override
	public String say(List<?> o) {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> T nn(Class<T> t, Object o) {
		return (T) o;
	}
}
