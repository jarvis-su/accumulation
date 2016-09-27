package com.gae.guest.book.action;

import java.util.Date;

import com.gae.guest.book.core.Action;
import com.gae.guest.book.core.annotation.ActionResult;
import com.gae.guest.book.core.annotation.DefaultHandler;
import com.gae.guest.book.core.annotation.URLPattern;
import com.gae.guest.book.core.annotation.ActionResult.ResultType;
import com.gae.guest.book.persistence.GuestBook;
import com.gae.guest.book.service.IGuestBookService;
import com.google.inject.Inject;

@URLPattern(url = "/savebook.gae")
public class GuestBookAction implements Action {

	private String name;
	private IGuestBookService service;

	@Inject
	public GuestBookAction(IGuestBookService service) {
		this.service = service;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@DefaultHandler
	@ActionResult(type = ResultType.REDIRECT, path = "/index.html")
	public void execute() {
		GuestBook book = new GuestBook();
		book.setInsertDate(new Date());
		book.setModifyDate(new Date());
		book.setName(name);
		service.saveGuestBook(book);
	}

}
