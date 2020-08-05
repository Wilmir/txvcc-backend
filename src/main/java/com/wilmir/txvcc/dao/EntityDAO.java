package com.wilmir.txvcc.dao;

import java.util.List;

public interface EntityDAO<T> {

	public List<T> findAll();
	
	public T getEntityById(int id);
	
	public void save(T entity);
	
	public void deleteById(int id);
	
}
