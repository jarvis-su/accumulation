package com.jarvis.basic.supporter.logger;

public class JdkLogFactory implements ILogFactory {

	@Override
	public BasicLog getLogger(Class<?> clazz) {
		return new JdkLog(clazz);
	}

	@Override
	public BasicLog getLogger(String name) {
		return new JdkLog(name);
	}
}
