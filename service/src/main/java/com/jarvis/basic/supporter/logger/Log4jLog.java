package com.jarvis.basic.supporter.logger;

import org.apache.log4j.Level;

public class Log4jLog extends BasicLog {

	private org.apache.log4j.Logger log;
	private static final String callerFQCN = Log4jLog.class.getName();

	Log4jLog(Class<?> clazz) {
		log = org.apache.log4j.Logger.getLogger(clazz);
	}

	Log4jLog(String name) {
		log = org.apache.log4j.Logger.getLogger(name);
	}

	public static Log4jLog getLog(Class<?> clazz) {
		return new Log4jLog(clazz);
	}

	public static Log4jLog getLog(String name) {
		return new Log4jLog(name);
	}

	@Override
	public void info(String message) {
		log.log(callerFQCN, Level.INFO, message, null);
	}

	@Override
	public void info(String message, Throwable t) {
		log.log(callerFQCN, Level.INFO, message, t);
	}

	@Override
	public void debug(String message) {
		log.log(callerFQCN, Level.DEBUG, message, null);
	}

	@Override
	public void debug(String message, Throwable t) {
		log.log(callerFQCN, Level.DEBUG, message, t);
	}

	@Override
	public void warn(String message) {
		log.log(callerFQCN, Level.WARN, message, null);
	}

	@Override
	public void warn(String message, Throwable t) {
		log.log(callerFQCN, Level.WARN, message, t);
	}

	@Override
	public void error(String message) {
		log.log(callerFQCN, Level.ERROR, message, null);
	}

	@Override
	public void error(String message, Throwable t) {
		log.log(callerFQCN, Level.ERROR, message, t);
	}

	@Override
	public void fatal(String message) {
		log.log(callerFQCN, Level.FATAL, message, null);
	}

	@Override
	public void fatal(String message, Throwable t) {
		log.log(callerFQCN, Level.FATAL, message, t);
	}

	@Override
	public boolean isDebugEnabled() {
		return log.isDebugEnabled();
	}

	@Override
	public boolean isInfoEnabled() {
		return log.isInfoEnabled();
	}

	@Override
	public boolean isWarnEnabled() {
		return log.isEnabledFor(Level.WARN);
	}

	@Override
	public boolean isErrorEnabled() {
		return log.isEnabledFor(Level.ERROR);
	}

	@Override
	public boolean isFatalEnabled() {
		return log.isEnabledFor(Level.FATAL);
	}

}
