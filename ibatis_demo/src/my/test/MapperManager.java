package my.test;

import java.lang.reflect.Proxy;

public class MapperManager {

	private static MapperManager manager = new MapperManager();

	private MapperManager() {
	}

	public static MapperManager getInstance() {
		return manager;
	}

	public <T> T getMapper(Class<T> t) {
		return (T) Proxy.newProxyInstance(t.getClassLoader(),
				new Class[] { t }, new MapperProxy());
	}
}
