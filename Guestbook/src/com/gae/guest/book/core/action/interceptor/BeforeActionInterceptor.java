package com.gae.guest.book.core.action.interceptor;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gae.guest.book.core.Action;
import com.gae.guest.book.core.ClassUtil;

@SuppressWarnings("unchecked")
public class BeforeActionInterceptor implements Interceptor {

	public static final Map<Class<? extends Action>, PropertyDescriptor[]> propertyCache = new ConcurrentHashMap<Class<? extends Action>, PropertyDescriptor[]>();

	@Override
	public void intercept(HttpServletRequest request,
			HttpServletResponse response, InterceptorChain interceptorChain)
			throws Exception {
		PropertyDescriptor[] descriptors = getPropertyDescriptors(interceptorChain
				.getAction().getClass());
		setParameters(request, interceptorChain.getAction(), descriptors);
		setAttributes(request, interceptorChain.getAction(), descriptors);
		interceptorChain.doChain(request, response);
	}

	private void setParameters(HttpServletRequest request, Action action,
			PropertyDescriptor[] propertyDescriptors)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		Map parameterMap = request.getParameterMap();
		for (Iterator<?> keyItr = parameterMap.keySet().iterator(); keyItr
				.hasNext();) {
			Object key = keyItr.next();
			String val = (String) ((Object[]) parameterMap.get(key))[0];
			setValue(action, propertyDescriptors, key, val);

		}
	}

	private void setValue(Action action,
			PropertyDescriptor[] propertyDescriptors, Object key, String val)
			throws IllegalAccessException, InvocationTargetException {
		for (PropertyDescriptor descriptor : propertyDescriptors) {
			Method writeMethod = descriptor.getWriteMethod();
			if (writeMethod != null) {
				if (descriptor.getName().equals(key)) {
					ClassUtil.setValue(action, writeMethod, val);
				}
			}
		}
	}

	private void setAttributes(HttpServletRequest request, Action action,
			PropertyDescriptor[] propertyDescriptors)
			throws IllegalAccessException, InvocationTargetException {
		Enumeration<?> attributes = request.getAttributeNames();
		while (attributes.hasMoreElements()) {
			String attribute = (String) attributes.nextElement();
			String value = (String) request.getAttribute(attribute);
			setValue(action, propertyDescriptors, attributes, value);
		}
	}

	private PropertyDescriptor[] getPropertyDescriptors(
			Class<? extends Action> actionClass) throws IntrospectionException {
		PropertyDescriptor[] descriptors = propertyCache.get(actionClass
				.getClass());
		if (descriptors == null) {
			BeanInfo beanInfo = Introspector.getBeanInfo(actionClass);
			descriptors = beanInfo.getPropertyDescriptors();
			propertyCache.put(actionClass, descriptors);
		}
		return descriptors;
	}
}
