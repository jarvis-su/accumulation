package com.acs.fileChecker.web;

import java.util.ArrayList;

public class ReportList {
	private ArrayList<Report> reports;

	public ReportList() {
		this.reports = new ArrayList(100);
	}

	public Report get(int index) {
		return (Report) this.reports.get(index);
	}

	public boolean add(Report r) {
		return this.reports.add(r);
	}

	public boolean isEmpty() {
		return this.reports.isEmpty();
	}

	public int size() {
		return this.reports.size();
	}

	public String toString() {
		StringBuffer st = new StringBuffer();
		for (int i = 0; i < size(); i++) {
			st.append("\"");
			st.append(get(i).toString());
			st.append("\" ");
		}
		return st.toString();
	}
}
