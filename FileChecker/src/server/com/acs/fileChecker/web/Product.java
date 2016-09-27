package com.acs.fileChecker.web;

import java.util.LinkedHashMap;
import java.util.Map;

public class Product {
	private String name;
	private String monitorhost;
	private LinkedHashMap<String, Instance> children;

	public Product(String name, String monitorhost) {
		this.name = name;
		this.monitorhost = monitorhost;
		this.children = new LinkedHashMap();
	}

	public String getName() {
		return this.name;
	}

	public String getMonitorHost() {
		return this.monitorhost;
	}

	public void addChild(Instance instance) {
		this.children.put(instance.getName(), instance);
	}

	public Map<String, Instance> getInstances() {
		return this.children;
	}
}
