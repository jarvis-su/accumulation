package com.gae.guest.book.core.persistence;

import java.lang.reflect.ParameterizedType;

public class BaseDao<T> {

	private Class<T> persistentClass;

	@SuppressWarnings("unchecked")
	public BaseDao() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public void save(T obj) {
		EMF.get().persist(obj);
	}

	public void delete(T obj) {
		EMF.get().remove(obj);
	}

	public T update(T obj) {
		return EMF.get().merge(obj);
	}

	public T find(T obj) {
		return EMF.get().find(this.persistentClass, obj);
	}
}
