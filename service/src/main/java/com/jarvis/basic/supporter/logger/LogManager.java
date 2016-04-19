package com.jarvis.basic.supporter.logger;

public class LogManager {
	private static final LogManager logManager = new LogManager();

	private LogManager() {
	}

	public static LogManager logManager() {
		return logManager;
	}

	public void init() {
		BasicLog.init();
	}

	public void setDefaultLogFactory(ILogFactory defaultLogFactory) {
		BasicLog.setDefaultLogFactory(defaultLogFactory);
		com.jarvis.basic.supporter.kit.LogKit.synchronizeLog();
	}
}
