package com.acs.fileChecker.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConfigFileManagmentServlet extends HttpServlet {
	public static final long serialVersionUID = 1L;
	private String _target = "/main.jsp";
	private String _csvOut = "/output.jsp";

	private String _frontpage = "/frontpage.jsp";
	private String _propFile = "/WEB-INF/filechecker.properties";
	private Hashtable<String, String> _ht = new Hashtable();
	private Hashtable<String, String> _dates = new Hashtable();
	private CmdExec cmd;

	int maxinstanceId;
	Map<String, String> eccStates = new HashMap<String, String>();
	Map<String, String> ebtStates = new HashMap<String, String>();
	Map<String, String> epcStates = new HashMap<String, String>();
	Map<String, String> otherStates = new HashMap<String, String>();

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
		this.cmd = new CmdExec(getServletContext().getRealPath(this._propFile));
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		MysqlUtil ts = MysqlUtil.getInstance();
		String state = request.getParameter("state");
		String date = request.getParameter("dateFor");
		String checkNow = request.getParameter("checknow");
		String config = request.getParameter("config");
		String group = request.getParameter("group");
		String stateName = request.getParameter("stateName");
		String deleteState = request.getParameter("deleteState");
		String instanceId = request.getParameter("instanceID");
		String deleteInstanceId = request.getParameter("deleteInstanceId");
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
			if (config != null) {
				request.setAttribute("ALERT", "");
				try {
					ts.getStateByGroup();
					request.getSession().setAttribute("ebtStates", ts.getEbtStates());
					request.getSession().setAttribute("eccStates", ts.getEccStates());
					request.getSession().setAttribute("epcStates", ts.getEpcStates());
					request.getSession().setAttribute("otherStates", ts.getOtherStates());
				} catch (SQLException e) {
					e.printStackTrace();
				}
				target = "/config.jsp";
			}
			if ((group != null) && (stateName != null) && (stateName != "")) {
				request.setAttribute("ALERT", "");
				//writePropertiesFile(request, group, stateName);
				//loadPropertiesFile(request);
				target = "/config.jsp";
			}
			if ((deleteState != null) && (deleteState != "") && (group != null)
					&& (deleteInstanceId != "0")) {
				request.setAttribute("ALERT", "");
				deleteState(request, deleteState, deleteInstanceId);
				//loadPropertiesFile(request);
				target = "/config.jsp";
			} 
			if((instanceId!=null)&&(instanceId!="")){
				request.setAttribute("ALERT", "");
				Set<String> filePath = ts.getPathByState(instanceId); 
				for(Iterator<String> it=filePath.iterator();it.hasNext();){
				    System.out.println(it.next());
				}

				target = "/config.jsp";
			}
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

	public void deleteState(HttpServletRequest request, String deleteState,
			String deleteInstanceId) {
		try {
			Properties pro = new Properties();
			String realpath = request.getRealPath("/WEB-INF/");
			File file = new File(realpath + "/filechecker.properties");
			if (!file.exists())
				file.createNewFile();
			FileInputStream in = new FileInputStream(file);
			pro.load(in);
			in.close();
			FileOutputStream fos = new FileOutputStream(realpath
					+ "/filechecker.properties");
			maxinstanceId -= 1;
			Enumeration enu = pro.propertyNames();
			while (enu.hasMoreElements()) {
				String key = (String) enu.nextElement();
				if (deleteState.equals(pro.getProperty(key))) {
					pro.setProperty(key, "#");
				}
			}
			System.out.println(deleteState + ", " + deleteInstanceId);

			pro.store(fos, "");
			fos.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void destroy() {
	}
}
