package like.ibatis;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MethodProxy implements InvocationHandler {

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Annotation[] annotations = method.getAnnotations();
		for(Annotation a : annotations) {
			if(a.getClass() == Select.class) {
				
			}else if(a.getClass() == Delete.class) {
				
			}
		}
		return null;
	}

}
