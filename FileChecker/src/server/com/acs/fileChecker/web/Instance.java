package com.acs.fileChecker.web;

public class Instance {
	private String name;
	private Product product;
	private String fileserver;
	private boolean disabled = false;

	public Instance(String name, String fileserver, boolean disabled, Product product) {
		this.name = name;
		this.product = product;
		this.fileserver = fileserver;
		this.disabled = disabled;
		product.addChild(this);
	}

	public Product getProduct() {
		return this.product;
	}

	public String getName() {
		return this.name;
	}

	public String getFileServer() {
		return this.fileserver;
	}

	public boolean isDisabled() {
		return this.disabled;
	}
}

