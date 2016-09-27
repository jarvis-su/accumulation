package com.acs.fileChecker.web;

import java.io.IOException;
import java.util.Hashtable;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.acs.fileChecker.common.Constant;


public class FileCheckerServlet extends HttpServlet {
	public static final long serialVersionUID = 1L;
	private String _target = "/main.jsp";
	private String _csvOut = "/output.jsp";

	private String _frontpage = "/frontpage.jsp";
	private String _propFile = "/WEB-INF/filechecker.properties";
	private Hashtable<String, String> _ht = new Hashtable();
	private Hashtable<String, String> _dates = new Hashtable();
	private CmdExec cmd;

	private String getOutput(String state, String date) {
		if (state == null) {
			return "";
		}
		String tmp = (String) this._ht.get(state);
		if (tmp == null) {
			return "";
		}
		return tmp;
	}

	private String updateOutput(String state, String date) {
		String out = this.cmd
				.checkFiles(this.cmd.getFSHost(state), state, date);
		this._ht.put(state, out == null ? "Error logging in" : out);
		this._dates.put(state, date);
		return getOutput(state, date);
	}

	private String getCSV(String state, String date) {
		String tmp = getOutput(state, date);
		tmp = tmp.replaceAll("<\\s*td[\\sa-zA-Z0-9=:%\" ]*>", ",");
		tmp = tmp.replaceAll("</tr>", "\n");
		tmp = tmp.replaceAll("</?[a-zA-Z0-9=:%\" ]*>", "");
		return tmp;
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		this.cmd = new CmdExec(Constant.SERVER_CONFIG_FILE);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String state = request.getParameter("state");
		String date = request.getParameter("dateFor");
		String checkNow = request.getParameter("checknow");

		String op = request.getParameter("op");
		String target = this._target;

		request.setAttribute("SIDEPANEL", this.cmd.getSidePanel());

		if (state != null) {
			request.setAttribute("STATE", state);
			if (date == null) {
				String tmp = (String) this._dates.get(state);
				if (tmp != null)
					request.setAttribute("DATE", tmp);
				else {
					request.setAttribute("DATE", "");
				}
				request.setAttribute("ALERT", "");
			}
		}
		if ((op != null) && (op.equals("getCSV"))) {
			request.setAttribute("OUTPUT", getCSV(state, date));
			target = this._csvOut;
		} else if (date == null) {
			if (state == null)
				target = this._frontpage;
			else
				request.setAttribute("OUTPUT", getOutput(state, date));
		} else if (validDate(date)) {
			request.setAttribute("ALERT", "");
			request.setAttribute("DATE", date);

			if (checkNow != null) {
				request.setAttribute("OUTPUT", updateOutput(state, date));
			} else {
				request.setAttribute("OUTPUT", getOutput(state, date));
			}
		} else {
			request.setAttribute("ALERT",
					"<script language=\"Javascript\">alert(\"Invalid Date\")</script>");
			request.setAttribute("OUTPUT", "");
		}

		ServletContext context = getServletContext();

		RequestDispatcher dispatcher = context.getRequestDispatcher(target);
		dispatcher.forward(request, response);
	}

	public boolean validDate(String date) {
		return (date != null)
				&& (Pattern.matches("[0-9]+/[0-9]+/2[0-9]+", date));
	}

	public void destroy() {
	}
}
