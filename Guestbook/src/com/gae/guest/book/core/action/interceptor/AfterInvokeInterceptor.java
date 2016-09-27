package com.gae.guest.book.core.action.interceptor;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AfterInvokeInterceptor implements Interceptor {

	@Override
	public void intercept(HttpServletRequest request,
			HttpServletResponse response, InterceptorChain interceptorChain)
			throws Exception {

		PropertyDescriptor[] propertyDescriptors = BeforeActionInterceptor.propertyCache
				.get(interceptorChain.getAction().getClass());
		for (PropertyDescriptor descriptor : propertyDescriptors) {
			Method readMethod = descriptor.getReadMethod();
			if (readMethod != null) {
				Object obj = readMethod.invoke(interceptorChain.getAction(),
						new Object[] {});
				request.setAttribute(descriptor.getName(), obj);
			}
		}
		interceptorChain.doChain(request, response);
	}

}
