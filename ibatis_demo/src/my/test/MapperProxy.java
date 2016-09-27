package my.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Date;

public class MapperProxy implements InvocationHandler {

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Text text = method.getAnnotation(Text.class);
		return text.text() + new Date();
	}
}
