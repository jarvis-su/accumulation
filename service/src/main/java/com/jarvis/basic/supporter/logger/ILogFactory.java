package com.jarvis.basic.supporter.logger;

public interface ILogFactory {
	BasicLog getLogger(Class<?> clazz);

	BasicLog getLogger(String name);
}
