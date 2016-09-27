package com.gae.guest.book.core.action.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CharsetUTF8Interceptor implements Interceptor {

	@Override
	public void intercept(HttpServletRequest request,
			HttpServletResponse response, InterceptorChain interceptorChain)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		interceptorChain.doChain(request, response);
	}

}
