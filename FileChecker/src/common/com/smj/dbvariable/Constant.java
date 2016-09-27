package com.smj.dbvariable;

import java.util.Properties;
import java.util.Vector;

public class Constant extends DBVar {
	public Constant() {
	}

	public Constant(String s) {
		super(s);
	}

	protected Vector<String> resolveInternal(VarCache c, Properties env)
			throws Exception {
		Vector v = new Vector();
		v.add(getInitialValue());
		return v;
	}
}
