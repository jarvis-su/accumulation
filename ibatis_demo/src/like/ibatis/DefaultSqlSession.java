package like.ibatis;

import java.lang.reflect.Proxy;

public class DefaultSqlSession implements SqlSession {

	private Configuration configuration;

	public DefaultSqlSession(Configuration configuration) {
		this.configuration = configuration;
	}

	@Override
	public <T> T getMapper(Class<T> clz) {
		return (T) Proxy.newProxyInstance(clz.getClassLoader(), clz
				.getInterfaces(), new MethodProxy());
	}
}
