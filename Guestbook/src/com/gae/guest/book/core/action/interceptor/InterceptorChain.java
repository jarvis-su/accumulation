package com.gae.guest.book.core.action.interceptor;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gae.guest.book.core.Action;
import com.gae.guest.book.core.annotation.ActionResult;

public class InterceptorChain {

	public Action getAction() {
		return action;
	}

	private Iterator<Interceptor> iterator;
	private Action action;
	private ActionResult actionResult;

	public ActionResult getActionResult() {
		return actionResult;
	}

	public void setActionResult(ActionResult actionResult) {
		this.actionResult = actionResult;
	}

	public InterceptorChain(Action action, List<Interceptor> interceptors) {
		this.iterator = interceptors.iterator();
		this.action = action;
	}

	public void doChain(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Interceptor interceptor = next();
		if (interceptor != null) {
			interceptor.intercept(request, response, this);
		}
	}

	private Interceptor next() {
		if (iterator.hasNext()) {
			return iterator.next();
		} else {
			return null;
		}
	}
}