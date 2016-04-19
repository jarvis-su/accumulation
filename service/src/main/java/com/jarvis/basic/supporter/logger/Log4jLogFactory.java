package com.jarvis.basic.supporter.logger;

public class Log4jLogFactory implements ILogFactory {

	@Override
	public BasicLog getLogger(Class<?> clazz) {
		return new Log4jLog(clazz);
	}

	@Override
	public BasicLog getLogger(String name) {
		return new Log4jLog(name);
	}

}
