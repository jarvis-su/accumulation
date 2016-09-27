package com.smj.dbvariable;

import java.util.Properties;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VarString extends DBVar {
	public VarString() {
		super(null);
	}

	public VarString(String s) {
		super(s);
	}

	protected Vector<String> resolveInternal(VarCache cache, Properties env)
			throws Exception {
		Pattern p = Pattern.compile("\\{.*?\\}");
		Matcher ma = p.matcher(getInitialValue());
		Vector ret = new Vector();
		Vector dirty = new Vector();
		ret.add(getInitialValue());
		while (ma.find()) {
			String var = ma.group();
			DBVar child = cache.getVar(var);
			if (child == null)
				throw new Exception("Unresolvable variable " + var);
			if (child.isDirty())
				throw new Exception("Illegal recursive variable " + var);
			Vector cvals = child.resolve(cache, env);
			int size = ret.size();
			for (int i = 0; i < size; i++) {
				String val = (String) ret.get(i);
				for (int j = 0; j < cvals.size(); j++) {
					dirty.add(val.replace(var, (CharSequence) cvals.get(j)));
				}
			}
			ret = dirty;
			dirty = new Vector();
		}
		return ret;
	}

	public static void main(String[] args) throws Exception {
		VarCache cache = VarCache.getInstance();
		cache.putVar("{var2}", new VarString("shawn"));
		cache.putVar("{CAEBT_COUNTIES}", new VarList("01,02"));
		Vector<String> v = VarCache.resolve("CA{CAEBT_COUNTIES}{var2}");
		for (String s : v)
			System.out.println(s);
	}
}
