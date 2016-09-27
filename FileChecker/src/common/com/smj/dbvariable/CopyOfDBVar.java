package com.smj.dbvariable;

import java.util.Vector;

public abstract class CopyOfDBVar {
	private String _initialVal = null;
	private Vector<String> _values = new Vector<String>();

	private Status _status = Status.NADA;

	public CopyOfDBVar() {
		this(null);
	}

	public CopyOfDBVar(String s) {
		setInitialValue(s);
	}

	public final void setInitialValue(String val) {
		this._initialVal = val;
	}

	public final String getInitialValue() {
		return this._initialVal;
	}

	public final boolean isResolved() {
		return this._status == Status.RESOLVED;
	}

	public final boolean isDirty() {
		return this._status == Status.DIRTY;
	}

	public final Vector<String> getValues() {
		return this._values;
	}

	protected final void addValue(String val) {
		this._values.add(val);
	}

	protected final void deleteValue(String val) {
		this._values.remove(val);
	}

	protected final void updateValue(int loc, String val) {
		this._values.set(loc, val);
	}

	public final Vector<String> resolve(VarCache c) throws Exception {
		this._status = Status.DIRTY;
		Vector ret = resolveInternal(c);
		this._status = Status.RESOLVED;
		return ret;
	}

	protected abstract Vector<String> resolveInternal(VarCache paramVarCache)
			throws Exception;

	private static enum Status {
		NADA, DIRTY, RESOLVED;
	}
}
