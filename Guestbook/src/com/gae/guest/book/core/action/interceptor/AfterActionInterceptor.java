package com.gae.guest.book.core.action.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gae.guest.book.core.annotation.ActionResult;
import com.gae.guest.book.core.annotation.ActionResult.ResultType;

public class AfterActionInterceptor implements Interceptor {

	@Override
	public void intercept(HttpServletRequest request,
			HttpServletResponse response, InterceptorChain interceptorChain)
			throws Exception {
		ActionResult actionResult = interceptorChain.getActionResult();
		if (actionResult != null) {
			ResultType resultType = actionResult.type();
			String path = actionResult.path();
			if (resultType == ResultType.FORWARD) {
				request.getRequestDispatcher(path).forward(request, response);
			} else {
				response.sendRedirect(path);
			}
		}
		interceptorChain.doChain(request, response);
	}

}
