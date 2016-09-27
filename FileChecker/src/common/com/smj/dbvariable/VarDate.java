package com.smj.dbvariable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.Vector;

public class VarDate extends DBVar {
	private Properties _runtimeEnv;

	protected Vector<String> resolveInternal(VarCache c, Properties env)
			throws Exception {
		this._runtimeEnv = env;
		Vector v = new Vector();
		String dateFormat = getInitialValue();
		Calendar cal = getCalendar();

		if (dateFormat.endsWith("MM")) {
			cal.set(5, 1);
		}
		while ((dateFormat.length() > 0) && (dateFormat.charAt(0) == '-')) {
			cal.add(5, -1);
			dateFormat = dateFormat.substring(1);
		}
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		v.add(sdf.format(cal.getTime()));
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
		VarDate v = new VarDate();
		v.setInitialValue("---yyyyMMdd");
		VarCache.getInstance().putVar("{TODAY}", v);
		System.out.print(VarCache.resolve("{TODAY}"));
	}
}
