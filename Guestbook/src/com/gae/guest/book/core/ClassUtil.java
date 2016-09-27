package com.gae.guest.book.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.gae.guest.book.core.module.InterceptorModule;
import com.gae.guest.book.core.module.ServiceModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public class ClassUtil {

	private static Injector injector;

	static {
		Module[] modules = new Module[] { new ServiceModule(),
				new InterceptorModule() };
		injector = Guice.createInjector(modules);
	}

	public static <T> T newInstance(Class<T> clazz) {
		return injector.getInstance(clazz);
	}

	public static void setValue(Action action, Method writeMethod, String value)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		Class<?> propertyType = writeMethod.getParameterTypes()[0].getClass();
		writeMethod.invoke(action, parse(propertyType, value));
	}

	private static Object parse(Class<?> propertyType, String value) {
		if (propertyType == int.class || propertyType == Integer.class) {
			return Integer.parseInt(value);
		} else if (propertyType == double.class || propertyType == Double.class) {
			return Double.parseDouble(value);
		} else if (propertyType == float.class || propertyType == Float.class) {
			return Float.parseFloat(value);
		} else if (propertyType == long.class || propertyType == Long.class) {
			return Long.parseLong(value);
		} else {
			return value;
		}
	}
}
