package com.gae.guest.book.service;

import com.gae.guest.book.core.annotation.Transactional;
import com.gae.guest.book.persistence.GuestBook;
import com.gae.guest.book.persistence.IGuestBookDao;
import com.google.inject.Inject;

public class GuestBookService implements IGuestBookService {

	private IGuestBookDao bookDao;

	@Inject
	public void setBookDao(IGuestBookDao bookDao) {
		this.bookDao = bookDao;
	}

	@Override
	@Transactional
	public void saveGuestBook(GuestBook book) {
		this.bookDao.save(book);
	}

}
