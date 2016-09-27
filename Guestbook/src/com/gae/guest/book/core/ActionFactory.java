package com.gae.guest.book.core;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.gae.guest.book.core.annotation.URLPattern;

@SuppressWarnings("unchecked")
public class ActionFactory {

	private List<Class<? extends Action>> actions = new ArrayList<Class<? extends Action>>();

	public ActionFactory(String actionPackage) {
		String packagePath = "/" + actionPackage.replace('.', '/');
		URL url = this.getClass().getResource(packagePath);
		File directory = new File(url.getFile());
		if (directory.isDirectory()) {
			for (File file : directory.listFiles()) {
				addAction(actionPackage, file.getName());
			}
		}
	}

	private void addAction(String packageName, String actionName) {
		try {
			Class<?> actionClass = this.getClass().getClassLoader().loadClass(
					packageName + "." + actionName.split("\\.")[0]);
			if (Action.class.isAssignableFrom(actionClass)) {
				actions.add((Class<? extends Action>) actionClass);
			}
		} catch (ClassNotFoundException exception) {
			exception.printStackTrace();
		}
	}

	public Class<? extends Action> getAction(URLPattern pattern) {
		for (Class<? extends Action> actionClass : actions) {
			URLPattern urlPattern = actionClass.getAnnotation(URLPattern.class);
			if (pattern.url().equals(urlPattern.url())) {
				return actionClass;
			}
		}
		return null;
	}

}
