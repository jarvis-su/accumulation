package com.jarvis.basic.supporter;

import java.util.ResourceBundle;

public abstract class ServiceBasic {
	private static final String BUNDLE_NAME = "service.basic.config.sys_basic_config"; 
	private static final String MESSAGE_NAME = "service.basic.config.sys_messages"; 
	protected static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
	protected static final ResourceBundle MESSAGE_BUNDLE = ResourceBundle.getBundle(MESSAGE_NAME);
}
