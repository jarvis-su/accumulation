package com.gae.guest.book.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target( { ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ActionResult {

	public static enum ResultType {
		FORWARD, REDIRECT;
	}

	ResultType type();

	String path();

}