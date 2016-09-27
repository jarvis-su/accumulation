package com.acs.fileChecker.web;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

import com.acs.fileChecker.common.PropertyFileReader;

public class CmdExec {
	private Calendar cal;
	private Calendar cal2;
	private String _today;
	protected ResultSet rs;
	private String _username = "monitor";
	private String _keyFile = "/home/sjohnson/workspace/FileChecker/WebContent/WEB-INF/id_rsa";

	private String _remoteCommand = "/home/monitor/fileChecker2/checkFiles3.sh";
	private String _sidePanel = "";
	private int _numGroups;
	private HashMap<String, Instance> _states = new HashMap();
	private LinkedHashMap<String, Product> _products = new LinkedHashMap();
	private String[] _frequencies = new String[20];

	public static void main(String[] argv) {
		CmdExec x = new CmdExec();
		System.out.println(x.checkFiles(x.getFSHost("VAEBT"), "VAEBT",
				"07/01/2010"));
	}

	public void loadProperties(String filename) {
		PropertyFileReader props = new PropertyFileReader(filename, null);
		this._username = props.retrieveValueInstance("remote.username");
		this._keyFile = props.retrieveValueInstance("keyfile");
		this._remoteCommand = props.retrieveValueInstance("remote.command");

		this._numGroups = Integer.valueOf(
				props.retrieveValueInstance("products.numgroups")).intValue();

		for (int i = 0; i < this._numGroups; i++) {
			String pname = props.retrieveValueInstance("products." + i);
			String monitorhost = props.retrieveValueInstance("products." + i
					+ ".monitorhost");
			int numinstances = Integer.valueOf(
					props.retrieveValueInstance("products." + i
							+ ".numInstances")).intValue();
			Product product = new Product(pname, monitorhost);
			this._products.put(product.getName(), product);
			for (int j = 0; j < numinstances; j++) {
				String name = props.retrieveValueInstance("products." + i + "."
						+ j + ".name");
				String fileserver = props.retrieveValueInstance("products." + i
						+ "." + j + ".fileserver");
				String dis = props.getProperties().getProperty(
						"products." + i + "." + j + ".disabled", "false");
				boolean disabled = Boolean.valueOf(dis).booleanValue();
				if (disabled)
					System.out.println("disabled " + name);
				Instance instance = new Instance(name, fileserver, disabled,
						product);
				this._states.put(name, instance);
			}
		}
	}

	public String getFrequencies() {
		StringBuffer freqs = new StringBuffer();
		freqs.append("<select name=\"freqs\">");
		int i = 1;

		while (this._frequencies[i] != null) {
			String desc = this._frequencies[i];
			freqs.append("<option value=\"");
			freqs.append(i++);
			freqs.append("\">");
			freqs.append(desc);
		}
		return freqs.toString();
	}

	public String getSidePanel() {
		return getSidePanel("");
	}

	public String getSidePanel(String extra) {
		StringBuffer side = new StringBuffer();

		for (Product product : this._products.values()) {
			side.append("<tr><td><H3>&nbsp;&nbsp;.:");
			side.append(product.getName());
			side.append("</H3></td></tr>\r\n");
			int port = 8080;
			for (Instance i : product.getInstances().values()) {
				String name = i.getName();
				side.append("<tr><td background=\"images/buttons/blankbutton1.jpg\">\r\n");
				if (i.isDisabled()) {
					side.append("<del>" + name + "</del>");
				} else {
					side.append("<a href=\"");
					side.append("http://" + product.getMonitorHost() + ":"
							+ port + "/FileChecker/FileChecker?state=" + name
							+ extra);
					side.append("\">" + name + "</a>");
				}
				side.append("\r\n</td></tr>\r\n");
			}
		}
		this._sidePanel = side.toString();
		return this._sidePanel;
	}

	public CmdExec(String properties) {
		this();
		loadProperties(properties);
	}

	public CmdExec() {
		StringBuffer tmp = new StringBuffer();
		this.cal = Calendar.getInstance();

		tmp.append(this.cal.get(2));
		tmp.append("/");
		tmp.append(this.cal.get(5));
		tmp.append("/");
		tmp.append(this.cal.get(1));

		this._today = tmp.toString();
	}

	public String getFSHost(String state) {
		Instance i = (Instance) this._states.get(state.toUpperCase());
		if (i == null)
			return null;
		return i.getFileServer();
	}

	public String checkFiles(String host, String state, String date) {
		StringBuffer output = new StringBuffer(
				"<table width=\"1000\" cellspacing=\"0\" cellpadding=\"0\" border=\"1\" style=\"margin: 0px\">");
		try {
			Connection conn = new Connection(host);
			conn.connect();
			boolean isAuthenticated = conn.authenticateWithPublicKey(
					this._username, new File(this._keyFile), null);
			if (!isAuthenticated) {
				System.out.println("Could not authenticate");
				conn.close();
				return null;
			}
			Session sess = conn.openSession();
			sess.execCommand(this._remoteCommand + " " + state + " " + date);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new StreamGobbler(sess.getStdout())));
			String line;
			while (!(line = br.readLine()).equals("Session Closed.")) {
				output.append("<tr>");
				output.append("<td>" + line.replaceAll("\t", "</td><td>")
						+ "</td>");
				output.append("</tr>");
			}
			sess.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			return "Error reading monitor output";
		}
		return output.toString();
	}

	public int getMonth(String date) {
		if (validDate(date)) {
			return Integer.parseInt(date.substring(0, 2));
		}
		return 0;
	}

	public int getDay(String date) {
		if (validDate(date)) {
			return Integer.parseInt(date.substring(3, 5));
		}
		return 0;
	}

	public int getDayOfWeek(String date) {
		if (validDate(date)) {
			return this.cal2.get(7);
		}
		return -1;
	}

	public Calendar strToCal(String d) {
		int yr = 0;
		int mo = 0;
		int dt = 0;
		if (validDate(d)) {
			mo = Integer.parseInt(d.substring(0, 2));
			dt = Integer.parseInt(d.substring(3, 5));
			yr = Integer.parseInt(d.substring(6, 10));
			Calendar c = Calendar.getInstance();
			c.set(yr, mo - 1, dt);
			return c;
		}
		return null;
	}

	public boolean validDate(String date) {
		return (date.equals("Never Checked"))
				|| (Pattern.matches("[0-9]+/[0-9]+/2[0-9]+", date));
	}

	public String replaceDate(String source) {
		char first = '{';
		char last = '}';
		long dayInMillis = 86400000L;
		long nowInMillis = this.cal2.getTimeInMillis();

		if (source != null) {
			int found = -1;
			int found2 = -1;
			int cur = 0;
			StringBuffer sb = new StringBuffer();
			while ((found = source.indexOf(first, cur)) != -1)
				if ((found2 = source.indexOf(last, cur)) != -1) {
					int subLen = found2 - found - 1;
					String tmp = source
							.substring(found + 1, found + subLen + 1);
					sb.append(source.substring(cur, found));
					if (tmp.startsWith("-")) {
						nowInMillis -= dayInMillis;
						tmp = tmp.substring(1, tmp.length());
					}
					SimpleDateFormat sdf = new SimpleDateFormat(tmp);

					sb.append(sdf.format(new Date(nowInMillis)));
					cur = found2 + 1;
				} else {
					return null;
				}
			sb.append(source.substring(cur));
			return sb.toString();
		}
		return null;
	}
}
