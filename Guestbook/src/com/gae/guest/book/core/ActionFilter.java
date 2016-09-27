package com.gae.guest.book.core;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gae.guest.book.core.action.interceptor.AfterActionInterceptor;
import com.gae.guest.book.core.action.interceptor.AfterInvokeInterceptor;
import com.gae.guest.book.core.action.interceptor.BeforeActionInterceptor;
import com.gae.guest.book.core.action.interceptor.BeforeInvokeInterceptor;
import com.gae.guest.book.core.action.interceptor.CharsetUTF8Interceptor;
import com.gae.guest.book.core.action.interceptor.Interceptor;
import com.gae.guest.book.core.action.interceptor.InterceptorChain;
import com.gae.guest.book.core.annotation.URLPattern;

public class ActionFilter implements Filter {

	private ActionFactory actionFactory;
	private final Map<String, Class<? extends Action>> actionCache = new ConcurrentHashMap<String, Class<? extends Action>>();
	private List<Interceptor> interceptors = new ArrayList<Interceptor>();

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		final String uri = httpServletRequest.getRequestURI();
		Class<? extends Action> actionClass = actionCache.get(uri);
		if (actionClass == null) {
			actionClass = actionFactory.getAction(new URLPattern() {

				@Override
				public Class<? extends Annotation> annotationType() {
					return URLPattern.class;
				}

				@Override
				public String url() {
					return uri;
				}
			});
			if (actionClass == null) {
				return;
			}
			actionCache.put(uri, actionClass);
		}
		Action action = ClassUtil.newInstance(actionClass);
		InterceptorChain interceptorChain = new InterceptorChain(action,
				interceptors);
		try {
			interceptorChain.doChain(httpServletRequest, httpServletResponse);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	private void initInterceptors() {
		interceptors.add(new CharsetUTF8Interceptor());
		interceptors.add(new BeforeActionInterceptor());
		interceptors.add(new BeforeInvokeInterceptor());
		interceptors.add(new AfterInvokeInterceptor());
		interceptors.add(new AfterActionInterceptor());
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		String actionPackage = config.getInitParameter("actionPackage");
		actionFactory = new ActionFactory(actionPackage);
		initInterceptors();
	}
}
