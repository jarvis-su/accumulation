package com.gae.guest.book.core.action.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gae.guest.book.core.Action;
import com.gae.guest.book.core.annotation.ActionResult;
import com.gae.guest.book.core.annotation.DefaultHandler;

public class BeforeInvokeInterceptor implements Interceptor {

	@Override
	public void intercept(HttpServletRequest request,
			HttpServletResponse response, InterceptorChain interceptorChain)
			throws Exception {
		Action action = interceptorChain.getAction();
		Method[] methods = action.getClass().getMethods();
		for (Method method : methods) {
			if (method.getAnnotation(DefaultHandler.class) != null) {
				method.invoke(action, new Object[] {});
				ActionResult actionResult = method
						.getAnnotation(ActionResult.class);
				interceptorChain.setActionResult(actionResult);
				break;
			}
		}
		interceptorChain.doChain(request, response);
	}

}
