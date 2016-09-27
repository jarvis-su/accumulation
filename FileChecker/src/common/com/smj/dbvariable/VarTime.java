package com.smj.dbvariable;

import java.io.PrintStream;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

public class VarTime extends DBVar {
	private Properties _runtimeEnv;

	protected Vector<String> resolveInternal(VarCache c, Properties env)
			throws Exception {
		this._runtimeEnv = env;
		Vector today = new Vector();
		Vector yest = new Vector();
		Vector v = new Vector();
		StringTokenizer st = new StringTokenizer(getInitialValue(), ",");
		int cal_hour = getCalendar().get(11);
		int cal_minute = getCalendar().get(12);

		while (st.hasMoreTokens()) {
			VarString vs = new VarString(st.nextToken());
			for (String s : vs.resolve(c, env)) {
				boolean add = false;
				if (s.length() >= 2) {
					int hour = Integer.parseInt(s.substring(0, 2));

					if ((hour < 24) && (cal_hour >= hour)) {
						if (s.length() >= 4) {
							int minute = Integer.parseInt(s.substring(2, 4));

							if (minute < 60)
								if ((cal_hour == hour)
										&& (cal_minute >= minute))
									today.add(s);
								else if (cal_hour > hour)
									today.add(s);
								else
									yest.add(s);
						} else {
							today.add(s);
						}
					} else
						yest.add(s);
				}
			}
		}
		boolean first = true;
		Enumeration e = yest.elements();
		StringBuffer sb = new StringBuffer("(");
		while (e.hasMoreElements()) {
			if (first)
				sb.append("{-yyyyMMdd}(");
			if (!first)
				sb.append("|");
			sb.append(e.nextElement());
			first = false;
		}
		if (!yest.isEmpty())
			sb.append(")");
		e = today.elements();
		first = true;
		while (e.hasMoreElements()) {
			if (first) {
				sb.append("|");
				sb.append(VarCache.resolve("{yyyyMMdd}", env));
				sb.append("(");
			}
			if (!first)
				sb.append("|");
			sb.append(e.nextElement());
			first = false;
		}
		if (!today.isEmpty())
			sb.append(")");
		v.add(")");
		return v;
	}

	public Calendar getCalendar() {
		Calendar c1 = (Calendar) this._runtimeEnv.get("DATEFOR");
		Calendar c;
		if (c1 == null)
			c = Calendar.getInstance();
		else
			c = (Calendar) c1.clone();
		return c;
	}

	public static void main(String[] args) throws Exception {
		VarTime v = new VarTime();
		VarDate d = new VarDate();
		d.setInitialValue("yyyyMMdd");
		v.setInitialValue("01,02,03,04,05,1625,17,18,19,20");
		VarCache.getInstance().putVar("{TODAY}", v);
		VarCache.getInstance().putVar("{yyyyMMdd}", d);
		VarCache.getInstance().putVar("{-yyyyMMdd}", d);
		System.out.print(VarCache.resolve("{TODAY}"));
	}
}

/*
 * Location: D:\DEV\workspace_kepler\FileChecker\lib\monitorUtil.jar Qualified
 * Name: com.smj.dbvariable.VarTime JD-Core Version: 0.6.0
 */