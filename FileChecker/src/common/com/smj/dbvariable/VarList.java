package com.smj.dbvariable;

import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

public class VarList extends DBVar {
	public VarList() {
	}

	public VarList(String s) {
		super(s);
	}

	protected Vector<String> resolveInternal(VarCache c, Properties env)
			throws Exception {
		Vector v = new Vector();
		StringTokenizer st = new StringTokenizer(getInitialValue(), ",");
		while (st.hasMoreTokens()) {
			VarString vs = new VarString(st.nextToken());
			for (String s : vs.resolve(c, env))
				v.add(s);
		}
		return v;
	}
}

