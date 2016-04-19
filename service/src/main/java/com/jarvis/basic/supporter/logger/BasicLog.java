package com.jarvis.basic.supporter.logger;

import com.jarvis.basic.supporter.ServiceBasic;

public abstract class BasicLog extends ServiceBasic {

	private static ILogFactory defaultLogFactory = null;

	static {
		init();
	}

	static void init() {
		if (defaultLogFactory == null) {
			try {
				String defaultLogFactoryClassName = RESOURCE_BUNDLE.getString("default.log.factory.class.name");
				Class.forName("org.apache.log4j.Logger");
				Class<?> log4jLogFactoryClass = Class.forName(defaultLogFactoryClassName);
				defaultLogFactory = (ILogFactory) log4jLogFactoryClass.newInstance();
			} catch (@SuppressWarnings("unused") Exception e) {
				defaultLogFactory = new JdkLogFactory();
			}
		}
	}

	static void setDefaultLogFactory(ILogFactory defaultLogFactory) {
		if (defaultLogFactory == null) {
			String error = MESSAGE_BUNDLE.getString("error.default.can.not.be.null");
			throw new IllegalArgumentException(error);
		}
		BasicLog.defaultLogFactory = defaultLogFactory;
	}

	public static BasicLog getLog(Class<?> clazz) {
		return defaultLogFactory.getLogger(clazz);
	}

	public static BasicLog getLog(String name) {
		return defaultLogFactory.getLogger(name);
	}

	public abstract void debug(String message);

	public abstract void debug(String message, Throwable t);

	public abstract void info(String message);

	public abstract void info(String message, Throwable t);

	public abstract void warn(String message);

	public abstract void warn(String message, Throwable t);

	public abstract void error(String message);

	public abstract void error(String message, Throwable t);

	public abstract void fatal(String message);

	public abstract void fatal(String message, Throwable t);

	public abstract boolean isDebugEnabled();

	public abstract boolean isInfoEnabled();

	public abstract boolean isWarnEnabled();

	public abstract boolean isErrorEnabled();

	public abstract boolean isFatalEnabled();

}
