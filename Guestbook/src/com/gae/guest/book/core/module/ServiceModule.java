package com.gae.guest.book.core.module;

import com.gae.guest.book.persistence.GuestBookDao;
import com.gae.guest.book.persistence.IGuestBookDao;
import com.gae.guest.book.service.GuestBookService;
import com.gae.guest.book.service.IGuestBookService;
import com.google.inject.AbstractModule;

public class ServiceModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(IGuestBookService.class).to(GuestBookService.class);
		bind(IGuestBookDao.class).to(GuestBookDao.class);
	}

}
