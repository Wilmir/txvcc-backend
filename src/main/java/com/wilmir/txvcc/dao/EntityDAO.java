package com.wilmir.txvcc.dao;

import com.wilmir.txvcc.model.Network;

public interface EntityDAO<T> {

	public T getEntityById(int id);
	
	public void save(T entity);
	
	public void deleteById(int id);
	
}
