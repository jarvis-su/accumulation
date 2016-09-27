package com.gae.guest.book.core.module;

import com.gae.guest.book.core.annotation.Transactional;
import com.gae.guest.book.core.transaction.interceptor.TransactionInterceptor;
import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

public class InterceptorModule extends AbstractModule {

	@Override
	protected void configure() {
		bindInterceptor(Matchers.any(), Matchers
				.annotatedWith(Transactional.class),
				new TransactionInterceptor());
	}
}
