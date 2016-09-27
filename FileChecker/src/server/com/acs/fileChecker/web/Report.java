package com.acs.fileChecker.web;

public class Report {
	private String _folder;
	private String _fileName;
	private String _extension;

	public Report() {
	}

	public Report(String folder, String fileName) {
		this._folder = folder;
		this._fileName = fileName;
		this._extension = "";
	}

	public Report(String folder, String fileName, String extension) {
		this._folder = folder;
		this._fileName = fileName;
		this._extension = extension;
	}

	public void setFileName(String fileName) {
		this._fileName = fileName;
		this._folder = "";
		this._extension = "";
	}

	public String getFileName() {
		return this._fileName;
	}

	public void setFolder(String folder) {
		this._folder = folder;
	}

	public String getFolder() {
		return this._folder;
	}

	public String toString() {
		StringBuffer st = new StringBuffer(this._folder);
		st.append(this._fileName);
		if (!this._extension.equals("")) {
			st.append(".");
			st.append(this._extension);
		}
		return st.toString();
	}
}
