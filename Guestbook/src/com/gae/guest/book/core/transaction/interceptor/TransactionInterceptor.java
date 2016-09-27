package com.gae.guest.book.core.transaction.interceptor;

import javax.persistence.EntityTransaction;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.gae.guest.book.core.annotation.Transactional;
import com.gae.guest.book.core.persistence.EMF;

public class TransactionInterceptor implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object object = null;
		Transactional transactional = invocation.getMethod().getAnnotation(
				Transactional.class);
		if (transactional != null) {
			EntityTransaction entityTransaction = EMF.get().getTransaction();
			entityTransaction.begin();
			try {
				object = invocation.proceed();
				entityTransaction.commit();
			} catch (Exception exception) {
				entityTransaction.rollback();
				throw exception;
			}
		}
		return object;
	}

}
